package warcraftTD.world;

import warcraftTD.libs.Align;
import warcraftTD.libs.StdDraw;

/**
 * Entité arbre
 */
public class Tree extends Entity {
  /**
   * Création d'un nouvel arbre
   */
  public Tree() {
    super(EntityBuildable.PAYING);
  }

  /**
   * Actualise la logique de l'entité et affiche son apparence
   *
   * @param deltaTime le temps d'un tick en seconde
   * @param tile      La Tile attaché à l'entité
   */
  public void update(double deltaTime, Tile tile) {
    StdDraw.pictureHeight(tile.getPosition().getX(), tile.getPosition().getY(), "images/tiles/flowers/tree.png", tile.getHeight() * 1.5, Align.BOTTOM);
  }
}
