package warcraftTD.hud;

import warcraftTD.libs.StdDraw;
import warcraftTD.utils.Position;

/**
 * Une barre de progression pour une interface utilisateur
 */
public class ProgressBar extends Element {
  /** Pourcentage de la barre remplis */
  private double progressPercent;
  /** Chemin vers l'image de la barre vide */
  private final String sprite;
  /** Chemin vers l'image du remplissage de la barre */
  private final String spriteFill;
  /** Le décalage horizontal de l'image de remplissage par rapport à l'image vide */
  private final double shiftx;

  /**
   * Initialise la barre de progression
   * @param position la position de l'élément
   * @param width la largeur de l'élément
   * @param height la hauteur de l'élément
   * @param sprite_bar Chemin vers l'image de la barre vide
   * @param spriteFill Chemin vers l'image du remplissage de la barre
   * @param parent l'interface mère de l'élément
   * @param shiftx décalage horizontal de l'image de remplissage par rapport à l'image vide
   */
  public ProgressBar(Position position, double width, double height, String sprite_bar, String spriteFill, Interface parent, double shiftx) {
    super(position, width, height, parent);
    this.sprite = sprite_bar;
    this.spriteFill = spriteFill;
    this.shiftx = shiftx;
  }

  /**
   * Modifie le pourcentage de progression de la barre
   * @param progressPercent nouveau pourcentage de remplissage
   */
  public void setProgressPercent(double progressPercent) {
    if (progressPercent > 100.0) {
      this.progressPercent = 100.0;
    } else if (progressPercent < 0.0) {
      this.progressPercent = 0.0;
    } else {
      this.progressPercent = progressPercent;
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
    StdDraw.picture(this.getPosition().getX(), this.getPosition().getY(), this.sprite, this.getWidth(), this.getHeight());
    StdDraw.picture(this.shiftx - this.shiftx * this.progressPercent / 100 + this.getPosition().getX() - (this.getWidth() / 2) + (this.progressPercent / 100) * (this.getWidth() / 2), this.getPosition().getY(), this.spriteFill, (this.progressPercent / 100) * this.getWidth(), this.getHeight());
  }

}
