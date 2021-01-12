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
   * Actualise la logique de l'entité et affiche son apparence
   *
   * @param deltaTime le temps d'un tick en seconde
   * @param tile      La Tile attaché à l'entité
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
