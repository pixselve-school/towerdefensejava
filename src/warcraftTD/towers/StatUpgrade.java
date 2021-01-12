package warcraftTD.towers;

/**
 * Objet représentant l'avancement d'amélioration d'une Stat de tour
 */
public class StatUpgrade {
  /** Niveau maximal de la stat */
  private final int max_level;
  /** Niveau actuel de la stat */
  private int level;
  /** Le prix de chaque amélioration en fonction de niveau */
  private final int[] level_price;
  /** La valeur de la stat en fonction du niveau */
  private final double[] level_stat;

  /**
   * Récupère le niveau max de la stat
   * @return le niveau max de la stat
   */
  public int getMax_level() {
    return this.max_level;
  }

  /**
   * Récupère le niveau actuel de la stat
   * @return le niveau actuel de la stat
   */
  public int getLevel() {
    return this.level;
  }

  /**
   * Modifie le niveau actuel de la stat
   * @param level le niveau actuel de la stat
   */
  public void setLevel(int level) {
    this.level = level;
  }

  /**
   * Récupère les prix d'améliorations par niveau
   * @return les prix d'améliorations par niveau
   */
  public int[] getLevel_price() {
    return this.level_price;
  }

  /**
   * Récupère les valeurs de la stat par niveau
   * @return les valeurs de la stat par niveau
   */
  public double[] getLevel_stat() {
    return this.level_stat;
  }

  /**
   * Initialise un StatUpgrade
   * @param max_level le niveau max de la stat
   * @param prices les prix d'améliorations par niveau
   * @param stats les valeurs de la stat par niveau
   */
  public StatUpgrade(int max_level, int[] prices, double[] stats) {
    this.level = 1;
    this.max_level = max_level;
    this.level_price = prices;
    this.level_stat = stats;
  }
}

