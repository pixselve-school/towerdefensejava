package warcraftTD.world;

import warcraftTD.libs.Align;
import warcraftTD.libs.StdDraw;

public class IndestructibleEntity extends Entity {
  String assetPath;
  double assetHeight;

  public IndestructibleEntity(String assetPath, double assetHeight) {
    super(EntityBuildable.NOTBUILDABLE);
    this.assetPath = assetPath;
    this.assetHeight = assetHeight;

  }


  /**
   * Update and draw the entity
   *
   * @param deltaTime The game delta time
   * @param tile      The tile the entity is attached to
   */
  public void update(double deltaTime, Tile tile) {
    StdDraw.pictureHeight(tile.getPosition().getX(), tile.getPosition().getY(), this.assetPath, this.assetHeight, Align.BOTTOM);
  }
}
