package warcraftTD.particules;

import warcraftTD.utils.Position;

public class SquareParticule extends Particule {
  public SquareParticule(double lifeDuration, double height, double movementLength) {
    super(lifeDuration, height, movementLength);
  }

  protected void draw(Position position, double height) {

  }

  /**
   * Deep clone the current particule
   *
   * @return The cloned particule
   */
  public Particule clone() {
    return new SquareParticule(this.getLifeDuration(), this.getHeight(), this.getMovementLength());
  }
}
