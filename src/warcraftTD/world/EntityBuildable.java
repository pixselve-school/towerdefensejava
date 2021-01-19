package warcraftTD.world;

/**
 * Indique s'il est possible de construire par dessus l'entité
 */
public enum EntityBuildable {
  /**
   * Constructible
   */
  BUILDABLE,
  /**
   * Constructible mais payant
   */
  PAYING,
  /**
   * Non constructible
   */
  NOTBUILDABLE
}
