package warcraftTD.world;


import warcraftTD.particules.EntityParticules;

public abstract class Entity {

  private final EntityBuildable buildable;
  private final EntityParticules entityParticules;
  private Tile parentTile;

  public Entity(EntityBuildable buildable) {
    this.entityParticules = new EntityParticules();
    this.buildable = buildable;
  }

  /**
   * Update and draw the entity
   *
   * @param deltaTime The game delta time
   * @param tile      The tile the entity is attached to
   */
  public abstract void update(double deltaTime, Tile tile);

  public EntityBuildable getBuildable() {
    return this.buildable;
  }


  public EntityParticules getEntityParticules() {
    return this.entityParticules;
  }

  public void setParentTile(Tile parentTile) {
    this.parentTile = parentTile;
  }

  public Tile getParentTile() {
    return this.parentTile;
  }
}
