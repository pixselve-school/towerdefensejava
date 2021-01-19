package warcraftTD.world;


import warcraftTD.particules.EntityParticules;

/**
 * Entité de tuile
 */
public abstract class Entity {
  /**
   * Indique s'il est possible de construire par dessus l'entité
   */
  private final EntityBuildable buildable;
  /**
   * Les particules produites par l'entité
   */
  private final EntityParticules entityParticules;
  /**
   * Tuile parent
   */
  private Tile parentTile;

  /**
   * Création d'une nouvelle entiée
   *
   * @param buildable Indique s'il est possible de construire par dessus l'entité
   */
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

  /**
   * Récupère l'indication sur la constructibilité de la tuile
   *
   * @return L'indication sur la constructibilité de la tuile
   */
  public EntityBuildable getBuildable() {
    return this.buildable;
  }


  /**
   * Récupère les particules de l'entité
   *
   * @return Les particules de l'entité
   */
  public EntityParticules getEntityParticules() {
    return this.entityParticules;
  }

  /**
   * Modifie la tuile parent de l'entité
   *
   * @param parentTile La tuile parent de l'entité
   */
  public void setParentTile(Tile parentTile) {
    this.parentTile = parentTile;
  }

  /**
   * Récupère la tuile parent de l'entité
   *
   * @return La tuile parent de l'entité
   */
  public Tile getParentTile() {
    return this.parentTile;
  }
}
