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
  /** vitesse de l'animation d'apparition de la GroupBox en seconde */
  private double speed;
  /** Chemin vers l'image de fond de la box */
  private String background = "";
  /** Cancel le click si on clique dans la surface de la box */
  private boolean consumeClick;
  /** Position de destination quand on affiche la groupbox */
  private Position destinationAnimation;
  /** Position de départ quand on affiche la groupbox */
  private Position startAnimation;
  /** Spécifie si la groupBox est en train d'être caché */
  private boolean hiding;

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
    this.startAnimation = position;
    this.destinationAnimation = position;
    this.speed = 0.5;
    this.background = background;
    this.setVisible(false);
    this.listHUDElements = new ArrayList<RelativeHUD_Element>();
    this.garbage = new ArrayList<RelativeHUD_Element>();
    this.consumeClick = false;
  }

  /**
   * Initialise une GroupBox
   * @param position la position de l'élément
   * @param width la largeur de l'élément
   * @param height la hauteur de l'élément
   * @param parent l'interface mère de l'élément
   * @param background le chemin vers l'image de fond
   * @param consumeClick spécifie si il cancel le clique si on clique sur sa surface
   * @throws UnsupportedAudioFileException
   * @throws IOException
   * @throws LineUnavailableException
   */
  public GroupBox(Position position, double width, double height, Interface parent, String background, boolean consumeClick) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
    super(position, width, height, parent);
    this.startAnimation = position;
    this.destinationAnimation = position;
    this.speed = 0.5;
    this.background = background;
    this.setVisible(false);
    this.listHUDElements = new ArrayList<RelativeHUD_Element>();
    this.garbage = new ArrayList<RelativeHUD_Element>();
    this.consumeClick = consumeClick;
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
   * Spécifie si la groupBox est en mouvement
   * @param deltaTime le temps d'éxecution d'un tick en seconde
   * @return la groupBox est en mouvement
   */
  public boolean isMoving(double deltaTime){
    return this.destinationAnimation.dist(this.getPosition())>this.speed*deltaTime;
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

      boolean needNewPosition = false;

      if(isMoving(deltaTime)){
        double x = (this.getPosition().getX()==this.destinationAnimation.getX() ? this.getPosition().getX() :(this.getPosition().getX()<this.destinationAnimation.getX() ? this.getPosition().getX()+deltaTime*this.speed : this.getPosition().getX()-deltaTime*this.speed));
        double y = (this.getPosition().getY()==this.destinationAnimation.getY() ? this.getPosition().getY() :(this.getPosition().getY()<this.destinationAnimation.getY() ? this.getPosition().getY()+deltaTime*this.speed : this.getPosition().getY()-deltaTime*this.speed));
        this.setPosition(new Position(x, y));
        if(!isMoving(deltaTime)) {
          needNewPosition = true;
          this.setPosition(this.destinationAnimation);
        }
      } else if(this.hiding){
        this.hiding = false;
        this.setVisible(false);
      }

      if(!this.background.equals("")) StdDraw.picture(this.getPosition().getX(), this.getPosition().getY(), this.background, this.getWidth(), this.getHeight());

      Iterator<RelativeHUD_Element> i = this.listHUDElements.iterator();
      RelativeHUD_Element el;
      while (i.hasNext()) {
        el = i.next();
        if(isMoving(deltaTime) || needNewPosition) el.element.setPosition(new Position((this.getPosition().getX() - (this.getWidth() / 2) + el.relativepos.getX() * this.getWidth()), (this.getPosition().getY() - (this.getHeight() / 2) + el.relativepos.getY() * this.getHeight())));
        el.element.update(mouseX, mouseY, deltaTime);
      }

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
    if (this.isVisible() && !this.hiding) {
      Iterator<RelativeHUD_Element> i = this.listHUDElements.iterator();
      RelativeHUD_Element el;
      ActionElement action = null;
      while (i.hasNext()) {
        el = i.next();
        if(el.getElement() instanceof ClickableElement) action = ((ClickableElement) el.getElement()).onClick(mouseX, mouseY);
        if (action!=null) break;
      }
      if(action==null){
        return (this.consumeClick && this.getHitBox().isHit(mouseX, mouseY) ? new ActionElement(this, "cancel") : null);
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
   * Affiche le groupement d'éléments avec une animation et une vitesse spécifique
   */
  public void showBox(){
    this.setVisible(true);
    this.hiding = false;
    initialUpdateRelativePosition();
  }

  /**
   * Affiche le groupement d'éléments avec une animation et une vitesse spécifique
   * @param from position de départ de l'animation
   * @param destinationAnimation position de fin d'animation
   * @param speed vitesse d'animation
   */
  public void showBox(Position from, Position destinationAnimation, double speed){
    this.startAnimation = from;
    this.destinationAnimation = destinationAnimation;
    this.setPosition(from);
    this.speed = speed;
    this.setVisible(true);
    this.hiding = false;
  }

  /**
   * Cahce la GroupBox
   */
  public void HideBox() {
    this.hiding = true;
    this.destinationAnimation = this.startAnimation;
  }
}
