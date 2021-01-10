package warcraftTD.particules;

import warcraftTD.libs.StdDraw;
import warcraftTD.utils.Position;

import java.awt.*;

public class SquareParticule extends Particule {
  private final Color color;

  public SquareParticule(double lifeDuration, double height, double movementLength) {
    super(lifeDuration, height, movementLength);
    this.color = Color.RED;
  }

  public SquareParticule(double lifeDuration, double height, double movementLength, Color color) {
    super(lifeDuration, height, movementLength);
    this.color = color;
  }

  protected void draw(Position position, double height) {
    StdDraw.setPenColor(this.color);
    StdDraw.rectangle(position.getX(), position.getY(), height / 2, height / 2);
  }

  /**
   * Deep clone the current particule
   *
   * @return The cloned particule
   */
  public Particule clone() {
    return new SquareParticule(this.getLifeDuration(), this.getHeight(), this.getMovementLength(), this.color);
  }
}
