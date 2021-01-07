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
 * les éléments appartenant au GroupBox sont visibles uniquement si la box l'est
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
  /** vitesse de l'animation d'apparition de la GroupBox en seconde */
  private double speed;
  /** Spécifie si la box est mouvement */
  private boolean moving;
  /** Position initiale de la GroupBox */
  private final Position initialPos;
  /** Chemin vers l'image de fond de la box */
  private String background = "";

  /**
   * Classe associant un élément d'interface à sa position relative par rapport à la groupBox
   */
  class RelativeHUD_Element {
    /** L'élément d'interface */
    private Element element;
    /** la position relative à la groupBox */
    private Position relativepos;

    /**
     * Récupère l'élément d'interface
     * @return l'élément d'interface
     */
    public Element getElement() {
      return this.element;
    }

    /**
     * Modifie l'élément d'interface
     * @param element le nouvel élément d'interface
     */
    public void setElement(Element element) {
      this.element = element;
    }

    /**
     * Initialise un élément avec sa position relative
     * @param element l'élément d'interface
     * @param relativepos sa position relative
     */
    public RelativeHUD_Element(Element element, Position relativepos) {
      this.element = element;
      this.relativepos = relativepos;
    }
  }

  /**
   * Initialise une GroupBox
   * @param position la position de l'élément
   * @param width la largeur de l'élément
   * @param height la hauteur de l'élément
   * @param parent l'interface mère de l'élément
   * @param background le chemin vers l'image de fond
   * @throws UnsupportedAudioFileException
   * @throws IOException
   * @throws LineUnavailableException
   */
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

  /**
   * Ajoute un élément d'interface au groupe
   * @param element un élément d'interface
   */
  public void addHUDElement(Element element) {
    if (!this.listHUDElements.contains(element)) {
      RelativeHUD_Element el = new RelativeHUD_Element(element, element.getPosition());
      this.listHUDElements.add(el);
    }
  }

  /**
   * Retire un élément d'interface du groupe
   * @param element un élément d'interface
   */
  public void removeHUDElement(Element element) {
    Iterator<RelativeHUD_Element> i = this.listHUDElements.iterator();
    RelativeHUD_Element el;
    while (i.hasNext()) {
      el = i.next();
      if(el.getElement().equals(element)) this.garbage.add(el);
    }
  }

  /**
   * Actualise la logique de l'élément et affiche son apparence
   * @param mouseX la position horizontale de la souris
   * @param mouseY la position verticale de la souris
   * @param deltaTime le temps d'un tick en seconde
   */
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

  /**
   * Méthode appelé par le world quand la souris est préssée
   * @param mouseX la position horizontale de la souris
   * @param mouseY la position verticale de la souris
   * @return un ActionElement spécifiant si l'élément à consumer le clique et l'action à réaliser
   * @throws UnsupportedAudioFileException
   * @throws IOException
   * @throws LineUnavailableException
   */
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

  /**
   * Positionne tous les éléments apaprtenant au Groupe relativement à celui-ci
   */
  public void initialUpdateRelativePosition(){
    Iterator<RelativeHUD_Element> i = this.listHUDElements.iterator();
    RelativeHUD_Element el;
    while (i.hasNext()) {
      el = i.next();
      el.element.setPosition(new Position((this.getPosition().getX() - (this.getWidth() / 2) + el.relativepos.getX() * this.getWidth()), (this.getPosition().getY() - (this.getHeight() / 2) + el.relativepos.getY() * this.getHeight())));
    }
  }

  /**
   * Affiche le groupement d'éléments avec une animation
   * @param fromy le décalage vertical d'où part l'animation (0 = pas d'animation verticale)
   * @param fromx le décalage horizontal d'où part l'animation (0 = pas d'animation horizontale)
   */
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

  /**
   * Affiche le groupement d'éléments avec une animation et une vitesse spécifique
   * @param fromy le décalage vertical d'où part l'animation (0 = pas d'animation verticale)
   * @param fromx le décalage horizontal d'où part l'animation (0 = pas d'animation horizontale)
   * @param speed la vitesse d'animation
   */
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

  /**
   * Cahce la GroupBox
   */
  public void HideBox() {
    this.forwardAnim = false;
    this.deltaX = this.fromX;
    this.deltaY = this.fromY;
  }
}
