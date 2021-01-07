package warcraftTD.hud;

import warcraftTD.utils.Position;

/**
 * Element d'interface utilisateur
 */
abstract public class Element {
  /** La position de l'élement sur l'écran */
  private Position position;
  /** Spécifie si l'élément est visible */
  private boolean visible;
  /** la largeur de l'élément */
  private double width;
  /** la hauteur de l'élément */
  private double height;
  /** l'interface mère de l'élément */
  private Interface parent;
  /** Spécifie si l'élément est activé ou pas */
  private boolean enabled;

  /**
   * Récupère la position de l'élément
   * @return la position de l'élément
   */
  public Position getPosition() {
    return this.position;
  }

  /**
   * Modifie la position de l'élément
   * @param position la nouvelle position
   */
  public void setPosition(Position position) {
    this.position = position;
  }

  /**
   * Récupère la largeur de l'élément
   * @return la largeur de l'élément
   */
  public double getWidth() {
    return this.width;
  }

  /**
   * Modifie la largeur de l'élément
   * @param width la nouvelle largeur
   */
  public void setWidth(double width) {
    this.width = width;
  }

  /**
   * Récupère la hauteur de l'élément
   * @return la hauteur de l'élément
   */
  public double getHeight() {
    return this.height;
  }

  /**
   * Modifie la hauteur de l'élément
   * @param height la nouvelle hauteur
   */
  public void setHeight(double height) {
    this.height = height;
  }

  /**
   * Récupère l'interface mère de l'élément
   * @return l'interface mère de l'élément
   */
  public Interface getParent() {
    return this.parent;
  }

  /**
   * Change l'interface mère de l'élément
   * @param parent la nouvelle interface mère
   */
  public void setParent(Interface parent) {
    this.parent = parent;
  }

  /**
   * Indique si l'élément est activé ou non
   * @return l'élément est activé
   */
  public boolean isEnabled() {
    return this.enabled;
  }

  /**
   * Modifie l'état activé/désactivé de l'élément
   * @param enabled l'élément est activé
   */
  public void setEnabled(boolean enabled) {
    this.enabled = enabled;
  }

  /**
   * Indique si l'élément est visible
   * @return l'élément est visible
   */
  public boolean isVisible() {
    return this.visible;
  }

  /**
   * Modifie la visibilité de l'élément
   * @param visible l'élément est visible
   */
  public void setVisible(boolean visible) {
    this.visible = visible;
  }

  /**
   * Initialistion d'un élément d'interface
   * @param position la position de l'élément
   * @param width la largeur de l'élément
   * @param height la hauteur de l'élément
   * @param parent l'interface mère de l'élément
   */
  public Element(Position position, double width, double height, Interface parent) {
    this.parent = parent;
    this.position = position;
    this.width = width;
    this.height = height;

    this.enabled = true;
    this.visible = true;
  }

  /**
   * Actualise la logique de l'élément et affiche son apparence
   * @param mouseX la position horizontale de la souris
   * @param mouseY la position verticale de la souris
   * @param deltaTime le temps d'un tick en seconde
   */
  abstract public void update(double mouseX, double mouseY, double deltaTime);
}
