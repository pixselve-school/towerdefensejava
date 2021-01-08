package warcraftTD.world;

import warcraftTD.libs.StdDraw;

public class Tree extends Entity {
  /**
   * Update and draw the entity
   *
   * @param deltaTime The game delta time
   * @param tile      The tile the entity is attached to
   */
  public void update(double deltaTime, Tile tile) {
    StdDraw.pictureHeight(tile.getPosition().getX(), tile.getPosition().getY(), "images/tiles/flowers/tree.png", tile.getHeight() * 1.5);
  }
}
