package warcraftTD.particules;

import warcraftTD.libs.StdDraw;
import warcraftTD.utils.Position;

import java.awt.*;

public class CircleParticule extends Particule {
  private final Color color;

  public CircleParticule(double lifeDuration, double height, double movementLength, Color color) {
    super(lifeDuration, height, movementLength);
    this.color = color;
  }

  public CircleParticule(double lifeDuration, double height, double movementLength) {
    super(lifeDuration, height, movementLength);
    this.color = Color.RED;
  }

  protected void draw(Position position, double height) {
    StdDraw.setPenColor(this.color);
    StdDraw.circle(position.getX(), position.getY(), height / 2);
  }

  /**
   * Deep clone the current particule
   *
   * @return The cloned particule
   */
  public Particule clone() {
    return new CircleParticule(this.getLifeDuration(), this.getHeight(), this.getMovementLength(), this.color);
  }
}
