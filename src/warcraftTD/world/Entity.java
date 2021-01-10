package warcraftTD.world;


import warcraftTD.particules.EntityParticules;

public abstract class Entity {
  public EntityParticules getEntityParticules() {
    return this.entityParticules;
  }
  private EntityBuildable buildable;
  private final EntityParticules entityParticules;

  protected Entity(EntityBuildable buildable) {
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
}
