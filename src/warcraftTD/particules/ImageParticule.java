package warcraftTD.particules;

import warcraftTD.libs.StdDraw;
import warcraftTD.utils.Position;

/**
 * Particule image
 */
public class ImageParticule extends Particule {
  /**
   * Chemin vers l'image
   */
  private final String imagePath;

  /**
   * Création d'une nouvelle particule image
   *
   * @param lifeDuration   La durée de vie de la particule
   * @param height         La hauteur de la particule
   * @param movementLength La distance que la particule va parcourir
   * @param imagePath      Le chemin vers l'image
   */
  public ImageParticule(double lifeDuration, double height, double movementLength, String imagePath) {
    super(lifeDuration, height, movementLength);
    this.imagePath = imagePath;
  }

  /**
   * Dessine la particule
   *
   * @param position la position de la particule
   * @param height   la hauteur de la particule
   */
  protected void draw(Position position, double height) {
    StdDraw.pictureHeight(position.getX(), position.getY(), this.imagePath, height);
  }

  /**
   * Clone la particule
   *
   * @return La particule clonée
   */
  public Particule clone() {
    return new ImageParticule(this.getLifeDuration(), this.getHeight(), this.getMovementLength(), this.imagePath);
  }
}
