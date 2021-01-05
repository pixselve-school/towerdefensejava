package warcraftTD.hud;

import warcraftTD.libs.StdDraw;
import warcraftTD.utils.Position;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class HorizontalGroupBox extends ClickableElement {
  private final List<RelativeHUD_Element> list_HUD_Elements = new ArrayList<RelativeHUD_Element>();
  private double deltax;
  private double deltay;
  private double fromy;
  private double fromx;
  private boolean forward_anim;
  private final double speed;

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

  public HorizontalGroupBox(Position position, double width, double height, Interface parent, String background) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
    super(position, width, height, parent);
    this.initialPos = position;
    this.deltax = 0.0;
    this.deltay = 0.0;
    this.speed = 0.5;
    this.background = background;
    this.setVisible(false);
  }

  public void addHUDElement(Element element) {
    if (!this.list_HUD_Elements.contains(element)) {
      RelativeHUD_Element el = new RelativeHUD_Element(element, element.getPosition());
      this.list_HUD_Elements.add(el);
    }
  }

  @Override
  public void update(double MouseX, double MouseY, double delta_time) {
    if (this.isVisible()) {

      if (this.deltay > 0.0) this.deltay -= this.speed * delta_time;
      if (this.deltax > 0.0) this.deltax -= this.speed * delta_time;

      if (this.forward_anim)
        this.setPosition(new Position(this.initialPos.getX() - this.deltax, this.initialPos.getY() - this.deltay));
      else {
        this.setPosition(new Position(this.initialPos.getX() - (this.fromx - this.deltax), this.initialPos.getY() - (this.fromy - this.deltay)));
        if (this.deltax <= 0.0 && this.deltay <= 0.0) this.setVisible(false);
      }
      if(!this.background.equals("")) StdDraw.picture(this.getPosition().getX(), this.getPosition().getY(), this.background, this.getWidth(), this.getHeight());

      Iterator<RelativeHUD_Element> i = this.list_HUD_Elements.iterator();
      RelativeHUD_Element el;
      while (i.hasNext()) {
        el = i.next();
        if(this.deltay > 0.0 || this.deltax > 0.0) el.element.setPosition(new Position((this.getPosition().getX() - (this.getWidth() / 2) + el.relativepos.getX() * this.getWidth()), (this.getPosition().getY() - (this.getHeight() / 2) + el.relativepos.getY() * this.getHeight())));
        el.element.update(MouseX, MouseY, delta_time);
      }
    }
  }

  @Override
  public String onClick(double MouseX, double MouseY) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
    if (this.isVisible()) {
      Iterator<RelativeHUD_Element> i = this.list_HUD_Elements.iterator();
      RelativeHUD_Element el;
      String action = "";
      while (i.hasNext()) {
        el = i.next();
        if(el.getElement() instanceof ClickableElement) action = ((ClickableElement) el.getElement()).onClick(MouseX, MouseY);
        if (!action.equals("")) break;
      }
      if(action.equals("")){
        return (this.getHitBox().isHit(MouseX, MouseY) ? "cancel" : "");
      }
      return action;
    }
    return "";
  }

  public void initialUpdateRelativePosition(){
    Iterator<RelativeHUD_Element> i = this.list_HUD_Elements.iterator();
    RelativeHUD_Element el;
    while (i.hasNext()) {
      el = i.next();
      el.element.setPosition(new Position((this.getPosition().getX() - (this.getWidth() / 2) + el.relativepos.getX() * this.getWidth()), (this.getPosition().getY() - (this.getHeight() / 2) + el.relativepos.getY() * this.getHeight())));
    }
  }

  public void ShowBox(double fromy, double fromx) {
    this.deltax = fromx;
    this.deltay = fromy;
    this.fromy = fromy;
    this.fromx = fromx;
    this.setVisible(true);
    this.forward_anim = true;
    if(deltax==0.0 && deltay==0.0) initialUpdateRelativePosition();
  }

  public void HideBox() {
    this.forward_anim = false;
    this.deltax = this.fromx;
    this.deltay = this.fromy;
  }
}
