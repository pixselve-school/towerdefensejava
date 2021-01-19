package warcraftTD.world;

import warcraftTD.libs.Align;
import warcraftTD.libs.StdDraw;

/**
 * Entité indestructible
 */
public class IndestructibleEntity extends Entity {
  /**
   * Chemin vers l'image
   */
  String assetPath;
  /**
   * Hauteur de l'image par rapport à la taille de la tuile parent
   */
  double heightScaleFactor;

  /**
   * Création d'une nouvelle entité indestructible
   *
   * @param assetPath         Chemin vers l'image
   * @param heightScaleFactor Hauteur de l'image par rapport à la taille de la tuile parent
   */
  public IndestructibleEntity(String assetPath, double heightScaleFactor) {
    super(EntityBuildable.NOTBUILDABLE);
    this.assetPath = assetPath;
    this.heightScaleFactor = heightScaleFactor;

  }


  /**
   * Actualise la logique de l'entité et affiche son apparence
   *
   * @param deltaTime le temps d'un tick en seconde
   * @param tile      La Tile attaché à l'entité
   */
  public void update(double deltaTime, Tile tile) {
    StdDraw.pictureHeight(tile.getPosition().getX(), tile.getPosition().getY(), this.assetPath, this.getParentTile().getHeight() * this.heightScaleFactor, Align.BOTTOM);
  }
}
