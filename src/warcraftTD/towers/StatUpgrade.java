package warcraftTD.towers;

public class StatUpgrade {
  private final int max_level;
  private int level;
  private final int[] level_price;
  private final double[] level_stat;

  public int getMax_level() {
    return this.max_level;
  }

  public StatUpgrade(int max_level, int[] prices, double[] stats) {
    this.level = 1;
    this.max_level = max_level;
    this.level_price = prices;
    this.level_stat = stats;
  }

  public int getLevel() {
    return this.level;
  }

  public void setLevel(int level) {
    this.level = level;
  }

  public int[] getLevel_price() {
    return this.level_price;
  }

  public double[] getLevel_stat() {
    return this.level_stat;
  }
}

