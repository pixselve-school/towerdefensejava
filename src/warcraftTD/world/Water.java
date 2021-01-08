package warcraftTD.world;

import warcraftTD.libs.StdDraw;
import warcraftTD.utils.Position;

import java.awt.*;

public class Water extends Tile {
  /**
   * Create a water tile
   *
   * @param position The tile position
   * @param height   The tile height
   * @param width    The tile width
   */
  public Water(Position position, double height, double width) {
    super(position, height, width);
  }

  /**
   * Update the tile
   *
   * @param deltaTime Game delta time
   */
  public void update(double deltaTime) {

    Position topLeft = new Position(this.getPosition().getX() - this.getWidth() / 4, this.getPosition().getY() + this.getHeight() / 4);
    Position topRight = new Position(this.getPosition().getX() + this.getWidth() / 4, this.getPosition().getY() + this.getHeight() / 4);
    Position bottomLeft = new Position(this.getPosition().getX() - this.getWidth() / 4, this.getPosition().getY() - this.getHeight() / 4);
    Position bottomRight = new Position(this.getPosition().getX() + this.getWidth() / 4, this.getPosition().getY() - this.getHeight() / 4);

    final double HALF_WIDTH = this.getWidth() / 2;
    final double HALF_HEIGHT = this.getHeight() / 2;

    switch (this.getDirectionValue()) {
      case 0:
        StdDraw.picture(topLeft.getX(), topLeft.getY(), "images/tiles/water/top-left.png", HALF_WIDTH, HALF_HEIGHT);
        StdDraw.picture(topRight.getX(), topRight.getY(), "images/tiles/water/top-right.png", HALF_WIDTH, HALF_HEIGHT);
        StdDraw.picture(bottomLeft.getX(), bottomLeft.getY(), "images/tiles/water/bottom-left.png", HALF_WIDTH, HALF_HEIGHT);
        StdDraw.picture(bottomRight.getX(), bottomRight.getY(), "images/tiles/water/bottom-right.png", HALF_WIDTH, HALF_HEIGHT);
        break;
      case 1:
        StdDraw.picture(topLeft.getX(), topLeft.getY(), "images/tiles/water/left.png", HALF_WIDTH, HALF_HEIGHT);
        StdDraw.picture(topRight.getX(), topRight.getY(), "images/tiles/water/right.png", HALF_WIDTH, HALF_HEIGHT);
        StdDraw.picture(bottomLeft.getX(), bottomLeft.getY(), "images/tiles/water/bottom-left.png", HALF_WIDTH, HALF_HEIGHT);
        StdDraw.picture(bottomRight.getX(), bottomRight.getY(), "images/tiles/water/bottom-right.png", HALF_WIDTH, HALF_HEIGHT);
        break;
      case 2:
        StdDraw.picture(topLeft.getX(), topLeft.getY(), "images/tiles/water/top.png", HALF_WIDTH, HALF_HEIGHT);
        StdDraw.picture(topRight.getX(), topRight.getY(), "images/tiles/water/top-right.png", HALF_WIDTH, HALF_HEIGHT);
        StdDraw.picture(bottomLeft.getX(), bottomLeft.getY(), "images/tiles/water/bottom.png", HALF_WIDTH, HALF_HEIGHT);
        StdDraw.picture(bottomRight.getX(), bottomRight.getY(), "images/tiles/water/bottom-right.png", HALF_WIDTH, HALF_HEIGHT);
        break;
      case 3:
        StdDraw.picture(topLeft.getX(), topLeft.getY(), "images/tiles/water/top-left-corner.png", HALF_WIDTH, HALF_HEIGHT);
        StdDraw.picture(topRight.getX(), topRight.getY(), "images/tiles/water/right.png", HALF_WIDTH, HALF_HEIGHT);
        StdDraw.picture(bottomLeft.getX(), bottomLeft.getY(), "images/tiles/water/bottom.png", HALF_WIDTH, HALF_HEIGHT);
        StdDraw.picture(bottomRight.getX(), bottomRight.getY(), "images/tiles/water/bottom-right.png", HALF_WIDTH, HALF_HEIGHT);
        break;
      case 4:
        StdDraw.picture(topLeft.getX(), topLeft.getY(), "images/tiles/water/top-left.png", HALF_WIDTH, HALF_HEIGHT);
        StdDraw.picture(topRight.getX(), topRight.getY(), "images/tiles/water/top.png", HALF_WIDTH, HALF_HEIGHT);
        StdDraw.picture(bottomLeft.getX(), bottomLeft.getY(), "images/tiles/water/bottom-left.png", HALF_WIDTH, HALF_HEIGHT);
        StdDraw.picture(bottomRight.getX(), bottomRight.getY(), "images/tiles/water/bottom.png", HALF_WIDTH, HALF_HEIGHT);
        break;
      case 5:
        StdDraw.picture(topLeft.getX(), topLeft.getY(), "images/tiles/water/left.png", HALF_WIDTH, HALF_HEIGHT);
        StdDraw.picture(topRight.getX(), topRight.getY(), "images/tiles/water/top-right-corner.png", HALF_WIDTH, HALF_HEIGHT);
        StdDraw.picture(bottomLeft.getX(), bottomLeft.getY(), "images/tiles/water/bottom-left.png", HALF_WIDTH, HALF_HEIGHT);
        StdDraw.picture(bottomRight.getX(), bottomRight.getY(), "images/tiles/water/bottom.png", HALF_WIDTH, HALF_HEIGHT);
        break;
      case 6:
        StdDraw.picture(topLeft.getX(), topLeft.getY(), "images/tiles/water/top.png", HALF_WIDTH, HALF_HEIGHT);
        StdDraw.picture(topRight.getX(), topRight.getY(), "images/tiles/water/top.png", HALF_WIDTH, HALF_HEIGHT);
        StdDraw.picture(bottomLeft.getX(), bottomLeft.getY(), "images/tiles/water/bottom.png", HALF_WIDTH, HALF_HEIGHT);
        StdDraw.picture(bottomRight.getX(), bottomRight.getY(), "images/tiles/water/bottom.png", HALF_WIDTH, HALF_HEIGHT);
        break;
      case 7:
        StdDraw.picture(topLeft.getX(), topLeft.getY(), "images/tiles/water/top-left-corner.png", HALF_WIDTH, HALF_HEIGHT);
        StdDraw.picture(topRight.getX(), topRight.getY(), "images/tiles/water/top-right-corner.png", HALF_WIDTH, HALF_HEIGHT);
        StdDraw.picture(bottomLeft.getX(), bottomLeft.getY(), "images/tiles/water/bottom.png", HALF_WIDTH, HALF_HEIGHT);
        StdDraw.picture(bottomRight.getX(), bottomRight.getY(), "images/tiles/water/bottom.png", HALF_WIDTH, HALF_HEIGHT);
        break;
      case 8:
        StdDraw.picture(topLeft.getX(), topLeft.getY(), "images/tiles/water/top-left.png", HALF_WIDTH, HALF_HEIGHT);
        StdDraw.picture(topRight.getX(), topRight.getY(), "images/tiles/water/top-right.png", HALF_WIDTH, HALF_HEIGHT);
        StdDraw.picture(bottomLeft.getX(), bottomLeft.getY(), "images/tiles/water/left.png", HALF_WIDTH, HALF_HEIGHT);
        StdDraw.picture(bottomRight.getX(), bottomRight.getY(), "images/tiles/water/right.png", HALF_WIDTH, HALF_HEIGHT);
        break;
      case 9:
        StdDraw.picture(topLeft.getX(), topLeft.getY(), "images/tiles/water/left.png", HALF_WIDTH, HALF_HEIGHT);
        StdDraw.picture(topRight.getX(), topRight.getY(), "images/tiles/water/right.png", HALF_WIDTH, HALF_HEIGHT);
        StdDraw.picture(bottomLeft.getX(), bottomLeft.getY(), "images/tiles/water/left.png", HALF_WIDTH, HALF_HEIGHT);
        StdDraw.picture(bottomRight.getX(), bottomRight.getY(), "images/tiles/water/right.png", HALF_WIDTH, HALF_HEIGHT);
        break;
      case 10:
        StdDraw.picture(topLeft.getX(), topLeft.getY(), "images/tiles/water/top.png", HALF_WIDTH, HALF_HEIGHT);
        StdDraw.picture(topRight.getX(), topRight.getY(), "images/tiles/water/top-right.png", HALF_WIDTH, HALF_HEIGHT);
        StdDraw.picture(bottomLeft.getX(), bottomLeft.getY(), "images/tiles/water/bottom-left-corner.png", HALF_WIDTH, HALF_HEIGHT);
        StdDraw.picture(bottomRight.getX(), bottomRight.getY(), "images/tiles/water/right.png", HALF_WIDTH, HALF_HEIGHT);
        break;
      case 11:
        StdDraw.picture(topLeft.getX(), topLeft.getY(), "images/tiles/water/top-left-corner.png", HALF_WIDTH, HALF_HEIGHT);
        StdDraw.picture(topRight.getX(), topRight.getY(), "images/tiles/water/right.png", HALF_WIDTH, HALF_HEIGHT);
        StdDraw.picture(bottomLeft.getX(), bottomLeft.getY(), "images/tiles/water/bottom-left-corner.png", HALF_WIDTH, HALF_HEIGHT);
        StdDraw.picture(bottomRight.getX(), bottomRight.getY(), "images/tiles/water/right.png", HALF_WIDTH, HALF_HEIGHT);
        break;
      case 12:
        StdDraw.picture(topLeft.getX(), topLeft.getY(), "images/tiles/water/top-left.png", HALF_WIDTH, HALF_HEIGHT);
        StdDraw.picture(topRight.getX(), topRight.getY(), "images/tiles/water/top.png", HALF_WIDTH, HALF_HEIGHT);
        StdDraw.picture(bottomLeft.getX(), bottomLeft.getY(), "images/tiles/water/left.png", HALF_WIDTH, HALF_HEIGHT);
        StdDraw.picture(bottomRight.getX(), bottomRight.getY(), "images/tiles/water/bottom-right-corner.png", HALF_WIDTH, HALF_HEIGHT);
        break;
      case 13:
        StdDraw.picture(topLeft.getX(), topLeft.getY(), "images/tiles/water/left.png", HALF_WIDTH, HALF_HEIGHT);
        StdDraw.picture(topRight.getX(), topRight.getY(), "images/tiles/water/top-right-corner.png", HALF_WIDTH, HALF_HEIGHT);
        StdDraw.picture(bottomLeft.getX(), bottomLeft.getY(), "images/tiles/water/left.png", HALF_WIDTH, HALF_HEIGHT);
        StdDraw.picture(bottomRight.getX(), bottomRight.getY(), "images/tiles/water/bottom-right-corner.png", HALF_WIDTH, HALF_HEIGHT);
        break;
      case 14:
        StdDraw.picture(topLeft.getX(), topLeft.getY(), "images/tiles/water/top.png", HALF_WIDTH, HALF_HEIGHT);
        StdDraw.picture(topRight.getX(), topRight.getY(), "images/tiles/water/top.png", HALF_WIDTH, HALF_HEIGHT);
        StdDraw.picture(bottomLeft.getX(), bottomLeft.getY(), "images/tiles/water/bottom-left-corner.png", HALF_WIDTH, HALF_HEIGHT);
        StdDraw.picture(bottomRight.getX(), bottomRight.getY(), "images/tiles/water/bottom-right-corner.png", HALF_WIDTH, HALF_HEIGHT);
        break;
      case 15:
        StdDraw.picture(topLeft.getX(), topLeft.getY(), "images/tiles/water/empty.png", HALF_WIDTH, HALF_HEIGHT);
        StdDraw.picture(topRight.getX(), topRight.getY(), "images/tiles/water/empty.png", HALF_WIDTH, HALF_HEIGHT);
        StdDraw.picture(bottomLeft.getX(), bottomLeft.getY(), "images/tiles/water/empty.png", HALF_WIDTH, HALF_HEIGHT);
        StdDraw.picture(bottomRight.getX(), bottomRight.getY(), "images/tiles/water/empty.png", HALF_WIDTH, HALF_HEIGHT);
        break;
    }

    if (this.isDebug()) {
      StdDraw.setPenColor(new Color(0, 0, 0, (float) 0.5));
      StdDraw.filledRectangle(this.getPosition().getX(), this.getPosition().getY(), this.getWidth() / 2, this.getHeight() / 2);
      StdDraw.setPenColor(Color.white);
      StdDraw.setFont(new Font("Trebuchet MS", Font.BOLD, 12));
      StdDraw.text(this.getPosition().getX(), this.getPosition().getY(), "Path" + " - " + this.getDirectionValue() + (this.isSelected() ? " - Selected" : ""));
    }
    if (this.isSelected()) {
      StdDraw.setPenColor(Color.red);
      StdDraw.setPenRadius(0.005);
      StdDraw.rectangle(this.getPosition().getX(), this.getPosition().getY(), this.getWidth() / 2, this.getHeight() / 2);
    }

  }

  /**
   * Update the tile contains entity if exists
   *
   * @param deltaTime The game delta time
   */
  public void updateContainsEntity(double deltaTime) {

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
