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
   * Actualise la logique de l'entité et affiche son apparence
   *
   * @param deltaTime le temps d'un tick en seconde
   * @param tile      La Tile attaché à l'entité
   */
  public abstract void update(double deltaTime, Tile tile);

  public EntityBuildable getBuildable() {
    return this.buildable;
  }
}
