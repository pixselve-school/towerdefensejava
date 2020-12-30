package warcraftTD;

public class StatUpgrade {
    private int max_level;
    private int level;
    private int[] level_price;
    private double[] level_stat;

    public int getMax_level() {
        return max_level;
    }

    public StatUpgrade(int max_level, int[] prices, double[] stats){
        this.level = 1;
        this.max_level = max_level;
        this.level_price = prices;
        this.level_stat = stats;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int[] getLevel_price() {
        return level_price;
    }

    public double[] getLevel_stat() {
        return level_stat;
    }
}

