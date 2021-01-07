package warcraftTD.hud;

import warcraftTD.libs.StdDraw;
import warcraftTD.utils.Position;

/**
 * Image d'interface utilisateur
 */
public class Image extends Element {
  /** Chemin vers l'image à afficher*/
  private String sprite;

  /**
   * Modifie le chemin vers l'image à afficher
   * @param sprite le chemin vers l'image à afficher
   */
  public void setSprite(String sprite) {
    this.sprite = sprite;
  }

  /**
   * Initialise une nouvelle Image d'interface
   * @param position la position de l'élément
   * @param width la largeur de l'élément
   * @param height la hauteur de l'élément
   * @param parent l'interface mère de l'élément
   * @param sprite le chemin vers l'image à afficher
   */
  public Image(Position position, double width, double height, Interface parent, String sprite) {
    super(position, width, height, parent);
    this.sprite = sprite;
  }

  /**
   * Actualise la logique de l'élément et affiche son apparence
   * @param mouseX la position horizontale de la souris
   * @param mouseY la position verticale de la souris
   * @param deltaTime le temps d'un tick en seconde
   */
  @Override
  public void update(double mouseX, double mouseY, double deltaTime) {
    if(this.isVisible()) StdDraw.picture(this.getPosition().getX(), this.getPosition().getY(), this.sprite, this.getWidth(), this.getHeight());
  }
}
