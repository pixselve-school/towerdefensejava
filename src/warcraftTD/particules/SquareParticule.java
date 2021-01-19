package warcraftTD.particules;

import warcraftTD.libs.StdDraw;
import warcraftTD.utils.Position;

import java.awt.*;

/**
 * Particules en forme de rectangle
 */
public class SquareParticule extends Particule {
  /**
   * La couleur de la particule
   */
  private final Color color;

  /**
   * Création d'une particule en forme de rectangle
   *
   * @param lifeDuration   La durée de vie de la particule
   * @param height         La hauteur de la particule
   * @param movementLength La distance que la particule va parcourir
   * @implSpec La couleur par défaut sera le rouge
   */
  public SquareParticule(double lifeDuration, double height, double movementLength) {
    super(lifeDuration, height, movementLength);
    this.color = Color.RED;
  }

  /**
   * Création d'une particule en forme de rectangle
   *
   * @param lifeDuration   La durée de vie de la particule
   * @param height         La hauteur de la particule
   * @param movementLength La distance que la particule va parcourir
   * @param color          La couleur de la particule
   */
  public SquareParticule(double lifeDuration, double height, double movementLength, Color color) {
    super(lifeDuration, height, movementLength);
    this.color = color;
  }

  /**
   * Dessine la particule
   *
   * @param position la position de la particule
   * @param height   la hauteur de la particule
   */
  protected void draw(Position position, double height) {
    StdDraw.setPenColor(this.color);
    StdDraw.rectangle(position.getX(), position.getY(), height / 2, height / 2);
  }

  /**
   * Clone la particule
   *
   * @return La particule clonée
   */
  public Particule clone() {
    return new SquareParticule(this.getLifeDuration(), this.getHeight(), this.getMovementLength(), this.color);
  }
}
