package warcraftTD.monsters.entities;

import warcraftTD.World;
import warcraftTD.monsters.BaseMonster;
import warcraftTD.utils.Animation;
import warcraftTD.utils.Position;

import java.util.List;

public class Scorpion extends BaseMonster {
  public static double scaledHeight = 0.09;
  public static double scaledWidth = 0.09;

  public Scorpion(List<Position> path) {
    super(path,
        5,
        5,
        0.1,
        new Animation(new String[]{
            "images/enemies/1/1_enemies_1_walk_000.png",
            "images/enemies/1/1_enemies_1_walk_001.png",
            "images/enemies/1/1_enemies_1_walk_002.png",
            "images/enemies/1/1_enemies_1_walk_003.png",
            "images/enemies/1/1_enemies_1_walk_004.png",
            "images/enemies/1/1_enemies_1_walk_005.png",
            "images/enemies/1/1_enemies_1_walk_006.png",
            "images/enemies/1/1_enemies_1_walk_007.png",
            "images/enemies/1/1_enemies_1_walk_008.png",
            "images/enemies/1/1_enemies_1_walk_009.png",
            "images/enemies/1/1_enemies_1_walk_010.png",
            "images/enemies/1/1_enemies_1_walk_011.png",
            "images/enemies/1/1_enemies_1_walk_012.png",
            "images/enemies/1/1_enemies_1_walk_013.png",
            "images/enemies/1/1_enemies_1_walk_014.png",
            "images/enemies/1/1_enemies_1_walk_015.png",
            "images/enemies/1/1_enemies_1_walk_016.png",
            "images/enemies/1/1_enemies_1_walk_017.png",
            "images/enemies/1/1_enemies_1_walk_018.png",
            "images/enemies/1/1_enemies_1_walk_019.png",
        }, scaledHeight, scaledWidth, null, 60, true),
        new Animation(new String[]{
            "images/enemies/1/1_enemies_1_die_000.png",
            "images/enemies/1/1_enemies_1_die_001.png",
            "images/enemies/1/1_enemies_1_die_002.png",
            "images/enemies/1/1_enemies_1_die_003.png",
            "images/enemies/1/1_enemies_1_die_004.png",
            "images/enemies/1/1_enemies_1_die_005.png",
            "images/enemies/1/1_enemies_1_die_006.png",
            "images/enemies/1/1_enemies_1_die_007.png",
            "images/enemies/1/1_enemies_1_die_008.png",
            "images/enemies/1/1_enemies_1_die_009.png",
            "images/enemies/1/1_enemies_1_die_010.png",
            "images/enemies/1/1_enemies_1_die_011.png",
            "images/enemies/1/1_enemies_1_die_012.png",
            "images/enemies/1/1_enemies_1_die_013.png",
            "images/enemies/1/1_enemies_1_die_014.png",
            "images/enemies/1/1_enemies_1_die_015.png",
            "images/enemies/1/1_enemies_1_die_016.png",
            "images/enemies/1/1_enemies_1_die_017.png",
            "images/enemies/1/1_enemies_1_die_018.png",
            "images/enemies/1/1_enemies_1_die_019.png",
        }, scaledHeight, scaledWidth, null, 20, false));
  }

  public double getScaledHeight() {
    return scaledHeight;
  }

  public double getScaledWidth() {
    return scaledWidth;
  }
}
