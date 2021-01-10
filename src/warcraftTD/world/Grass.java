package warcraftTD.world;

import warcraftTD.libs.StdDraw;
import warcraftTD.utils.Position;

import java.awt.*;

public class Grass extends Tile {

  /**
   * Create a grass tile
   *
   * @param position The tile position
   * @param height   The tile height
   * @param width    The tile width
   */
  public Grass(Position position, double height, double width) {
    super(position, height, width);
  }

  /**
   * Update the tile
   *
   * @param deltaTime Game delta time
   */
  public void update(double deltaTime) {
  }

  /**
   * Draw the static part of the tile
   */
  public void drawStaticPart() {
    StdDraw.picture(this.getPosition().getX(), this.getPosition().getY(), "images/tiles/grass.png", this.getWidth(), this.getHeight());
  }


  /**
   * Executed when a tile is clicked
   */
  public void onClick(double x, double y) {

  }

  /**
   * Check if a tile can be build on
   *
   * @return True if the tile can be build on
   */
  public boolean isBuildable() {
    return this.getContains() == null;
  }
}
