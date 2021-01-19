package warcraftTD.particules;

import warcraftTD.libs.StdDraw;
import warcraftTD.utils.Position;

import java.awt.*;

/**
 * Particules en forme de cercle
 */
public class CircleParticule extends Particule {
  /**
   * La couleur de la particule
   */
  private final Color color;

  /**
   * Création d'une particule en forme de cercle
   *
   * @param lifeDuration   La durée de vie de la particule
   * @param height         Le diamètre de la particule
   * @param movementLength La distance que la particule va parcourir
   * @param color          La couleur de la particule
   */
  public CircleParticule(double lifeDuration, double height, double movementLength, Color color) {
    super(lifeDuration, height, movementLength);
    this.color = color;
  }

  /**
   * Création d'une particule en forme de cercle
   *
   * @param lifeDuration   La durée de vie de la particule
   * @param height         Le diamètre de la particule
   * @param movementLength La distance que la particule va parcourir
   * @implSpec La couleur par défaut sera le rouge
   */
  public CircleParticule(double lifeDuration, double height, double movementLength) {
    super(lifeDuration, height, movementLength);
    this.color = Color.RED;
  }

  /**
   * Dessine la particule
   *
   * @param position la position de la particule
   * @param height   le diamètre de la particule
   */
  protected void draw(Position position, double height) {
    StdDraw.setPenColor(this.color);
    StdDraw.circle(position.getX(), position.getY(), height / 2);
  }

  /**
   * Clone la particule
   *
   * @return La particule clonée
   */
  public Particule clone() {
    return new CircleParticule(this.getLifeDuration(), this.getHeight(), this.getMovementLength(), this.color);
  }
}
