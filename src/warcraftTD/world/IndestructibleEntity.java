package warcraftTD.world;

import warcraftTD.libs.Align;
import warcraftTD.libs.StdDraw;

public class IndestructibleEntity extends Entity {
  String assetPath;
  double heightScaleFactor;

  public IndestructibleEntity(String assetPath, double heightScaleFactor) {
    super(EntityBuildable.NOTBUILDABLE);
    this.assetPath = assetPath;
    this.heightScaleFactor = heightScaleFactor;

  }


  /**
   * Update and draw the entity
   *
   * @param deltaTime The game delta time
   * @param tile      The tile the entity is attached to
   */
  public void update(double deltaTime, Tile tile) {
    StdDraw.pictureHeight(tile.getPosition().getX(), tile.getPosition().getY(), this.assetPath, this.getParentTile().getHeight() * this.heightScaleFactor, Align.BOTTOM);
  }
}
