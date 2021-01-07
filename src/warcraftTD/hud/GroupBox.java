package warcraftTD.hud;

import warcraftTD.libs.StdDraw;
import warcraftTD.utils.Position;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GroupBox extends ClickableElement {
  private final List<RelativeHUD_Element> listHUDElements;
  private List<RelativeHUD_Element> garbage;
  private double deltax;
  private double deltay;
  private double fromy;
  private double fromx;
  private boolean forward_anim;
  private double speed;
  private boolean moving;

  private final Position initialPos;
  private String background = "";


  class RelativeHUD_Element {
    private Element element;
    private Position relativepos;

    public Element getElement() {
      return this.element;
    }

    public void setElement(Element element) {
      this.element = element;
    }

    public Position getRelativepos() {
      return this.relativepos;
    }

    public void setRelativepos(Position relativepos) {
      this.relativepos = relativepos;
    }

    public RelativeHUD_Element(Element element, Position relativepos) {
      this.element = element;
      this.relativepos = relativepos;
    }
  }

  public GroupBox(Position position, double width, double height, Interface parent, String background) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
    super(position, width, height, parent);
    this.initialPos = position;
    this.deltax = 0.0;
    this.deltay = 0.0;
    this.speed = 0.5;
    this.background = background;
    this.setVisible(false);
    this.listHUDElements = new ArrayList<RelativeHUD_Element>();
    this.garbage = new ArrayList<RelativeHUD_Element>();

  }

  public void addHUDElement(Element element) {
    if (!this.listHUDElements.contains(element)) {
      RelativeHUD_Element el = new RelativeHUD_Element(element, element.getPosition());
      this.listHUDElements.add(el);
    }
  }

  public void removeHUDElement(Element element) {
    Iterator<RelativeHUD_Element> i = this.listHUDElements.iterator();
    RelativeHUD_Element el;
    while (i.hasNext()) {
      el = i.next();
      if(el.getElement().equals(element)) this.garbage.add(el);
    }
  }

  @Override
  public void update(double mouseX, double mouseY, double deltaTime) {
    if (this.isVisible()) {

      if (this.deltay > 0.0) {
        this.deltay -= this.speed * deltaTime;
        if(this.deltay<0.0) this.deltay = 0.0;
      }
      if (this.deltax > 0.0){
        this.deltax -= this.speed * deltaTime;
        if(this.deltax<0.0) this.deltax = 0.0;
      }

      if (this.forward_anim)
        this.setPosition(new Position(this.initialPos.getX() - this.deltax, this.initialPos.getY() - this.deltay));
      else {
        this.setPosition(new Position(this.initialPos.getX() - (this.fromx - this.deltax), this.initialPos.getY() - (this.fromy - this.deltay)));
        if (this.deltax <= 0.0 && this.deltay <= 0.0) this.setVisible(false);
      }
      if(!this.background.equals("")) StdDraw.picture(this.getPosition().getX(), this.getPosition().getY(), this.background, this.getWidth(), this.getHeight());

      Iterator<RelativeHUD_Element> i = this.listHUDElements.iterator();
      RelativeHUD_Element el;
      while (i.hasNext()) {
        el = i.next();
        if(this.deltay > 0.0 || this.deltax > 0.0) el.element.setPosition(new Position((this.getPosition().getX() - (this.getWidth() / 2) + el.relativepos.getX() * this.getWidth()), (this.getPosition().getY() - (this.getHeight() / 2) + el.relativepos.getY() * this.getHeight())));
        else if(this.moving) {
          el.element.setPosition(new Position((this.getPosition().getX() - (this.getWidth() / 2) + el.relativepos.getX() * this.getWidth()), (this.getPosition().getY() - (this.getHeight() / 2) + el.relativepos.getY() * this.getHeight())));
        }
        el.element.update(mouseX, mouseY, deltaTime);
      }

      if(this.moving && !(this.deltay > 0.0 || this.deltax > 0.0)) this.moving = false;

      if(this.garbage.size()>0){
        i = this.garbage.iterator();
        while (i.hasNext()) {
          el = i.next();
          this.listHUDElements.remove(el);
        }
        this.garbage = new ArrayList<RelativeHUD_Element>();
      }

    }
  }

  @Override
  public ActionElement onClick(double mouseX, double mouseY) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
    if (this.isVisible()) {
      Iterator<RelativeHUD_Element> i = this.listHUDElements.iterator();
      RelativeHUD_Element el;
      ActionElement action = null;
      while (i.hasNext()) {
        el = i.next();
        if(el.getElement() instanceof ClickableElement) action = ((ClickableElement) el.getElement()).onClick(mouseX, mouseY);
        if (action!=null) break;
      }
      if(action==null){
        return (this.getHitBox().isHit(mouseX, mouseY) ? new ActionElement(this, "cancel") : null);
      }
      return action;
    }
    return null;
  }

  public void initialUpdateRelativePosition(){
    Iterator<RelativeHUD_Element> i = this.listHUDElements.iterator();
    RelativeHUD_Element el;
    while (i.hasNext()) {
      el = i.next();
      el.element.setPosition(new Position((this.getPosition().getX() - (this.getWidth() / 2) + el.relativepos.getX() * this.getWidth()), (this.getPosition().getY() - (this.getHeight() / 2) + el.relativepos.getY() * this.getHeight())));
    }
  }

  public void showBox(double fromy, double fromx) {
    this.deltax = fromx;
    this.deltay = fromy;
    this.fromy = fromy;
    this.fromx = fromx;
    this.setVisible(true);
    this.forward_anim = true;
    if(deltax==0.0 && deltay==0.0) initialUpdateRelativePosition();
    else this.moving = true;
  }

  public void showBox(double fromy, double fromx, double speed) {
    this.speed = speed;
    this.deltax = fromx;
    this.deltay = fromy;
    this.fromy = fromy;
    this.fromx = fromx;
    this.setVisible(true);
    this.forward_anim = true;
    if(deltax==0.0 && deltay==0.0) initialUpdateRelativePosition();
    else this.moving = true;
  }

  public void HideBox() {
    this.forward_anim = false;
    this.deltax = this.fromx;
    this.deltay = this.fromy;
  }
}
