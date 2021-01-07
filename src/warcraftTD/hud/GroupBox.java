package warcraftTD.hud;

import warcraftTD.libs.StdDraw;
import warcraftTD.utils.Position;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Un élément d'interface capable de grouper plusieurs autre éléments d'interface
 * les éléments appartenant au GroupBox se positionne alors relativement à celui-ci
 */
public class GroupBox extends ClickableElement {
  /** liste des éléments appartenant au groupe */
  private final List<RelativeHUD_Element> listHUDElements;
  /** liste des éléments à supprimer du groupe à la prochaine update */
  private List<RelativeHUD_Element> garbage;
  /** décalage horizontal actuel de position */
  private double deltaX;
  /** décalage vertical actuel de position */
  private double deltaY;
  /** décalage vertical initial d'appartion de la box */
  private double fromY;
  /** décalage horizontal initial d'appartion de la box */
  private double fromX;
  /** Spéicie si l'animation d'apparition est dans le sens normal */
  private boolean forwardAnim;
  /** vitesse de l'animation d'apparition de la GroupBox */
  private double speed;
  /** Spécifie si la box est mouvement */
  private boolean moving;
  /** Position initiale de la GroupBox */
  private final Position initialPos;
  /** Chemin vers l'image de fond de la box */
  private String background = "";

  /**
   *
   */
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
    this.deltaX = 0.0;
    this.deltaY = 0.0;
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

      if (this.deltaY > 0.0) {
        this.deltaY -= this.speed * deltaTime;
        if(this.deltaY <0.0) this.deltaY = 0.0;
      }
      if (this.deltaX > 0.0){
        this.deltaX -= this.speed * deltaTime;
        if(this.deltaX <0.0) this.deltaX = 0.0;
      }

      if (this.forwardAnim)
        this.setPosition(new Position(this.initialPos.getX() - this.deltaX, this.initialPos.getY() - this.deltaY));
      else {
        this.setPosition(new Position(this.initialPos.getX() - (this.fromX - this.deltaX), this.initialPos.getY() - (this.fromY - this.deltaY)));
        if (this.deltaX <= 0.0 && this.deltaY <= 0.0) this.setVisible(false);
      }
      if(!this.background.equals("")) StdDraw.picture(this.getPosition().getX(), this.getPosition().getY(), this.background, this.getWidth(), this.getHeight());

      Iterator<RelativeHUD_Element> i = this.listHUDElements.iterator();
      RelativeHUD_Element el;
      while (i.hasNext()) {
        el = i.next();
        if(this.deltaY > 0.0 || this.deltaX > 0.0) el.element.setPosition(new Position((this.getPosition().getX() - (this.getWidth() / 2) + el.relativepos.getX() * this.getWidth()), (this.getPosition().getY() - (this.getHeight() / 2) + el.relativepos.getY() * this.getHeight())));
        else if(this.moving) {
          el.element.setPosition(new Position((this.getPosition().getX() - (this.getWidth() / 2) + el.relativepos.getX() * this.getWidth()), (this.getPosition().getY() - (this.getHeight() / 2) + el.relativepos.getY() * this.getHeight())));
        }
        el.element.update(mouseX, mouseY, deltaTime);
      }

      if(this.moving && !(this.deltaY > 0.0 || this.deltaX > 0.0)) this.moving = false;

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
    this.deltaX = fromx;
    this.deltaY = fromy;
    this.fromY = fromy;
    this.fromX = fromx;
    this.setVisible(true);
    this.forwardAnim = true;
    if(deltaX ==0.0 && deltaY ==0.0) initialUpdateRelativePosition();
    else this.moving = true;
  }

  public void showBox(double fromy, double fromx, double speed) {
    this.speed = speed;
    this.deltaX = fromx;
    this.deltaY = fromy;
    this.fromY = fromy;
    this.fromX = fromx;
    this.setVisible(true);
    this.forwardAnim = true;
    if(deltaX ==0.0 && deltaY ==0.0) initialUpdateRelativePosition();
    else this.moving = true;
  }

  public void HideBox() {
    this.forwardAnim = false;
    this.deltaX = this.fromX;
    this.deltaY = this.fromY;
  }
}
