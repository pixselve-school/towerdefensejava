package warcraftTD.world;


import warcraftTD.particules.EntityParticules;

public abstract class Entity {
  public EntityParticules getEntityParticules() {
    return this.entityParticules;
  }

  private final EntityParticules entityParticules;

  protected Entity() {
    this.entityParticules = new EntityParticules();
  }

  /**
   * Update and draw the entity
   *
   * @param deltaTime The game delta time
   * @param tile      The tile the entity is attached to
   */
  public abstract void update(double deltaTime, Tile tile);


}
