package warcraftTD.particules;

import warcraftTD.libs.StdDraw;
import warcraftTD.utils.Position;

public class ImageParticule extends Particule {
  private final String imagePath;
  public ImageParticule(double lifeDuration, double height, double movementLength, String imagePath) {
    super(lifeDuration, height, movementLength);
    this.imagePath = imagePath;
  }

  protected void draw(Position position, double height) {
    StdDraw.pictureHeight(position.getX(), position.getY(), this.imagePath, height);
  }

  /**
   * Deep clone the current particule
   *
   * @return The cloned particule
   */
  public Particule clone() {
    return new ImageParticule(this.getLifeDuration(), this.getHeight(), this.getMovementLength(), this.imagePath);
  }
}
