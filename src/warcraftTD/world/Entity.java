package warcraftTD.world;


public abstract class Entity {
  /**
   * Update and draw the entity
   *
   * @param deltaTime The game delta time
   * @param tile      The tile the entity is attached to
   */
  public abstract void update(double deltaTime, Tile tile);
}
