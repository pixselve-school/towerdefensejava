package warcraftTD.world;

import warcraftTD.libs.StdDraw;
import warcraftTD.utils.Position;

import java.awt.*;

public class Pathway extends Tile {
  /**
   * Create a pathway tile
   *
   * @param position The tile position
   * @param height   The tile height
   * @param width    The tile width
   */
  public Pathway(Position position, double height, double width) {
    super(new Position(position.getX() * width + width / 2, position.getY() * height + height / 2), position, height, width);
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
    Position topLeft = new Position(this.getPosition().getX() - this.getWidth() / 4, this.getPosition().getY() + this.getHeight() / 4);
    Position topRight = new Position(this.getPosition().getX() + this.getWidth() / 4, this.getPosition().getY() + this.getHeight() / 4);
    Position bottomLeft = new Position(this.getPosition().getX() - this.getWidth() / 4, this.getPosition().getY() - this.getHeight() / 4);
    Position bottomRight = new Position(this.getPosition().getX() + this.getWidth() / 4, this.getPosition().getY() - this.getHeight() / 4);

    final double HALF_WIDTH = this.getWidth() / 2 + 0.001;
    final double HALF_HEIGHT = this.getHeight() / 2 + 0.001;

    switch (this.getDirectionValue()) {
      case 0:
        StdDraw.picture(topLeft.getX(), topLeft.getY(), "images/tiles/top-left.png", HALF_WIDTH, HALF_HEIGHT);
        StdDraw.picture(topRight.getX(), topRight.getY(), "images/tiles/top-right.png", HALF_WIDTH, HALF_HEIGHT);
        StdDraw.picture(bottomLeft.getX(), bottomLeft.getY(), "images/tiles/bottom-left.png", HALF_WIDTH, HALF_HEIGHT);
        StdDraw.picture(bottomRight.getX(), bottomRight.getY(), "images/tiles/bottom-right.png", HALF_WIDTH, HALF_HEIGHT);
        break;
      case 1:
        StdDraw.picture(topLeft.getX(), topLeft.getY(), "images/tiles/left.png", HALF_WIDTH, HALF_HEIGHT);
        StdDraw.picture(topRight.getX(), topRight.getY(), "images/tiles/right.png", HALF_WIDTH, HALF_HEIGHT);
        StdDraw.picture(bottomLeft.getX(), bottomLeft.getY(), "images/tiles/bottom-left.png", HALF_WIDTH, HALF_HEIGHT);
        StdDraw.picture(bottomRight.getX(), bottomRight.getY(), "images/tiles/bottom-right.png", HALF_WIDTH, HALF_HEIGHT);
        break;
      case 2:
        StdDraw.picture(topLeft.getX(), topLeft.getY(), "images/tiles/top.png", HALF_WIDTH, HALF_HEIGHT);
        StdDraw.picture(topRight.getX(), topRight.getY(), "images/tiles/top-right.png", HALF_WIDTH, HALF_HEIGHT);
        StdDraw.picture(bottomLeft.getX(), bottomLeft.getY(), "images/tiles/bottom.png", HALF_WIDTH, HALF_HEIGHT);
        StdDraw.picture(bottomRight.getX(), bottomRight.getY(), "images/tiles/bottom-right.png", HALF_WIDTH, HALF_HEIGHT);
        break;
      case 3:
        StdDraw.picture(topLeft.getX(), topLeft.getY(), "images/tiles/top-left-corner.png", HALF_WIDTH, HALF_HEIGHT);
        StdDraw.picture(topRight.getX(), topRight.getY(), "images/tiles/right.png", HALF_WIDTH, HALF_HEIGHT);
        StdDraw.picture(bottomLeft.getX(), bottomLeft.getY(), "images/tiles/bottom.png", HALF_WIDTH, HALF_HEIGHT);
        StdDraw.picture(bottomRight.getX(), bottomRight.getY(), "images/tiles/bottom-right.png", HALF_WIDTH, HALF_HEIGHT);
        break;
      case 4:
        StdDraw.picture(topLeft.getX(), topLeft.getY(), "images/tiles/top-left.png", HALF_WIDTH, HALF_HEIGHT);
        StdDraw.picture(topRight.getX(), topRight.getY(), "images/tiles/top.png", HALF_WIDTH, HALF_HEIGHT);
        StdDraw.picture(bottomLeft.getX(), bottomLeft.getY(), "images/tiles/bottom-left.png", HALF_WIDTH, HALF_HEIGHT);
        StdDraw.picture(bottomRight.getX(), bottomRight.getY(), "images/tiles/bottom.png", HALF_WIDTH, HALF_HEIGHT);
        break;
      case 5:
        StdDraw.picture(topLeft.getX(), topLeft.getY(), "images/tiles/left.png", HALF_WIDTH, HALF_HEIGHT);
        StdDraw.picture(topRight.getX(), topRight.getY(), "images/tiles/top-right-corner.png", HALF_WIDTH, HALF_HEIGHT);
        StdDraw.picture(bottomLeft.getX(), bottomLeft.getY(), "images/tiles/bottom-left.png", HALF_WIDTH, HALF_HEIGHT);
        StdDraw.picture(bottomRight.getX(), bottomRight.getY(), "images/tiles/bottom.png", HALF_WIDTH, HALF_HEIGHT);
        break;
      case 6:
        StdDraw.picture(topLeft.getX(), topLeft.getY(), "images/tiles/top.png", HALF_WIDTH, HALF_HEIGHT);
        StdDraw.picture(topRight.getX(), topRight.getY(), "images/tiles/top.png", HALF_WIDTH, HALF_HEIGHT);
        StdDraw.picture(bottomLeft.getX(), bottomLeft.getY(), "images/tiles/bottom.png", HALF_WIDTH, HALF_HEIGHT);
        StdDraw.picture(bottomRight.getX(), bottomRight.getY(), "images/tiles/bottom.png", HALF_WIDTH, HALF_HEIGHT);
        break;
      case 7:
        StdDraw.picture(topLeft.getX(), topLeft.getY(), "images/tiles/top-left-corner.png", HALF_WIDTH, HALF_HEIGHT);
        StdDraw.picture(topRight.getX(), topRight.getY(), "images/tiles/top-right-corner.png", HALF_WIDTH, HALF_HEIGHT);
        StdDraw.picture(bottomLeft.getX(), bottomLeft.getY(), "images/tiles/bottom.png", HALF_WIDTH, HALF_HEIGHT);
        StdDraw.picture(bottomRight.getX(), bottomRight.getY(), "images/tiles/bottom.png", HALF_WIDTH, HALF_HEIGHT);
        break;
      case 8:
        StdDraw.picture(topLeft.getX(), topLeft.getY(), "images/tiles/top-left.png", HALF_WIDTH, HALF_HEIGHT);
        StdDraw.picture(topRight.getX(), topRight.getY(), "images/tiles/top-right.png", HALF_WIDTH, HALF_HEIGHT);
        StdDraw.picture(bottomLeft.getX(), bottomLeft.getY(), "images/tiles/left.png", HALF_WIDTH, HALF_HEIGHT);
        StdDraw.picture(bottomRight.getX(), bottomRight.getY(), "images/tiles/right.png", HALF_WIDTH, HALF_HEIGHT);
        break;
      case 9:
        StdDraw.picture(topLeft.getX(), topLeft.getY(), "images/tiles/left.png", HALF_WIDTH, HALF_HEIGHT);
        StdDraw.picture(topRight.getX(), topRight.getY(), "images/tiles/right.png", HALF_WIDTH, HALF_HEIGHT);
        StdDraw.picture(bottomLeft.getX(), bottomLeft.getY(), "images/tiles/left.png", HALF_WIDTH, HALF_HEIGHT);
        StdDraw.picture(bottomRight.getX(), bottomRight.getY(), "images/tiles/right.png", HALF_WIDTH, HALF_HEIGHT);
        break;
      case 10:
        StdDraw.picture(topLeft.getX(), topLeft.getY(), "images/tiles/top.png", HALF_WIDTH, HALF_HEIGHT);
        StdDraw.picture(topRight.getX(), topRight.getY(), "images/tiles/top-right.png", HALF_WIDTH, HALF_HEIGHT);
        StdDraw.picture(bottomLeft.getX(), bottomLeft.getY(), "images/tiles/bottom-left-corner.png", HALF_WIDTH, HALF_HEIGHT);
        StdDraw.picture(bottomRight.getX(), bottomRight.getY(), "images/tiles/right.png", HALF_WIDTH, HALF_HEIGHT);
        break;
      case 11:
        StdDraw.picture(topLeft.getX(), topLeft.getY(), "images/tiles/top-left-corner.png", HALF_WIDTH, HALF_HEIGHT);
        StdDraw.picture(topRight.getX(), topRight.getY(), "images/tiles/right.png", HALF_WIDTH, HALF_HEIGHT);
        StdDraw.picture(bottomLeft.getX(), bottomLeft.getY(), "images/tiles/bottom-left-corner.png", HALF_WIDTH, HALF_HEIGHT);
        StdDraw.picture(bottomRight.getX(), bottomRight.getY(), "images/tiles/right.png", HALF_WIDTH, HALF_HEIGHT);
        break;
      case 12:
        StdDraw.picture(topLeft.getX(), topLeft.getY(), "images/tiles/top-left.png", HALF_WIDTH, HALF_HEIGHT);
        StdDraw.picture(topRight.getX(), topRight.getY(), "images/tiles/top.png", HALF_WIDTH, HALF_HEIGHT);
        StdDraw.picture(bottomLeft.getX(), bottomLeft.getY(), "images/tiles/left.png", HALF_WIDTH, HALF_HEIGHT);
        StdDraw.picture(bottomRight.getX(), bottomRight.getY(), "images/tiles/bottom-right-corner.png", HALF_WIDTH, HALF_HEIGHT);
        break;
      case 13:
        StdDraw.picture(topLeft.getX(), topLeft.getY(), "images/tiles/left.png", HALF_WIDTH, HALF_HEIGHT);
        StdDraw.picture(topRight.getX(), topRight.getY(), "images/tiles/top-right-corner.png", HALF_WIDTH, HALF_HEIGHT);
        StdDraw.picture(bottomLeft.getX(), bottomLeft.getY(), "images/tiles/left.png", HALF_WIDTH, HALF_HEIGHT);
        StdDraw.picture(bottomRight.getX(), bottomRight.getY(), "images/tiles/bottom-right-corner.png", HALF_WIDTH, HALF_HEIGHT);
        break;
      case 14:
        StdDraw.picture(topLeft.getX(), topLeft.getY(), "images/tiles/top.png", HALF_WIDTH, HALF_HEIGHT);
        StdDraw.picture(topRight.getX(), topRight.getY(), "images/tiles/top.png", HALF_WIDTH, HALF_HEIGHT);
        StdDraw.picture(bottomLeft.getX(), bottomLeft.getY(), "images/tiles/bottom-left-corner.png", HALF_WIDTH, HALF_HEIGHT);
        StdDraw.picture(bottomRight.getX(), bottomRight.getY(), "images/tiles/bottom-right-corner.png", HALF_WIDTH, HALF_HEIGHT);
        break;
      case 15:
        StdDraw.picture(topLeft.getX(), topLeft.getY(), "images/tiles/empty.png", HALF_WIDTH, HALF_HEIGHT);
        StdDraw.picture(topRight.getX(), topRight.getY(), "images/tiles/empty.png", HALF_WIDTH, HALF_HEIGHT);
        StdDraw.picture(bottomLeft.getX(), bottomLeft.getY(), "images/tiles/empty.png", HALF_WIDTH, HALF_HEIGHT);
        StdDraw.picture(bottomRight.getX(), bottomRight.getY(), "images/tiles/empty.png", HALF_WIDTH, HALF_HEIGHT);
        break;
    }

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
    return false;
  }
}
