package warcraftTD.monsters.entities;

import warcraftTD.WorldGame;
import warcraftTD.monsters.BaseMonster;
import warcraftTD.utils.Animation;
import warcraftTD.utils.Position;

public class StoneGiant extends BaseMonster {
  public static double scaledHeight = 0.3;
  public static double scaledWidth = 0.25;

  public StoneGiant(Position position, WorldGame world) {
    super(position, world, 100, 100, 0.05,
        new Animation(new String[]{
            "images/enemies/10/10_enemies_1_walk_000.png",
            "images/enemies/10/10_enemies_1_walk_001.png",
            "images/enemies/10/10_enemies_1_walk_002.png",
            "images/enemies/10/10_enemies_1_walk_003.png",
            "images/enemies/10/10_enemies_1_walk_004.png",
            "images/enemies/10/10_enemies_1_walk_005.png",
            "images/enemies/10/10_enemies_1_walk_006.png",
            "images/enemies/10/10_enemies_1_walk_007.png",
            "images/enemies/10/10_enemies_1_walk_008.png",
            "images/enemies/10/10_enemies_1_walk_009.png",
            "images/enemies/10/10_enemies_1_walk_010.png",
            "images/enemies/10/10_enemies_1_walk_011.png",
            "images/enemies/10/10_enemies_1_walk_012.png",
            "images/enemies/10/10_enemies_1_walk_013.png",
            "images/enemies/10/10_enemies_1_walk_014.png",
            "images/enemies/10/10_enemies_1_walk_015.png",
            "images/enemies/10/10_enemies_1_walk_016.png",
            "images/enemies/10/10_enemies_1_walk_017.png",
            "images/enemies/10/10_enemies_1_walk_018.png",
            "images/enemies/10/10_enemies_1_walk_019.png",
        }, scaledHeight, scaledWidth, new Position(position.getX(), position.getY()), 10, true),
        new Animation(new String[]{
            "images/enemies/10/10_enemies_1_die_000.png",
            "images/enemies/10/10_enemies_1_die_001.png",
            "images/enemies/10/10_enemies_1_die_002.png",
            "images/enemies/10/10_enemies_1_die_003.png",
            "images/enemies/10/10_enemies_1_die_004.png",
            "images/enemies/10/10_enemies_1_die_005.png",
            "images/enemies/10/10_enemies_1_die_006.png",
            "images/enemies/10/10_enemies_1_die_007.png",
            "images/enemies/10/10_enemies_1_die_008.png",
            "images/enemies/10/10_enemies_1_die_009.png",
            "images/enemies/10/10_enemies_1_die_010.png",
            "images/enemies/10/10_enemies_1_die_011.png",
            "images/enemies/10/10_enemies_1_die_012.png",
            "images/enemies/10/10_enemies_1_die_013.png",
            "images/enemies/10/10_enemies_1_die_014.png",
            "images/enemies/10/10_enemies_1_die_015.png",
            "images/enemies/10/10_enemies_1_die_016.png",
            "images/enemies/10/10_enemies_1_die_017.png",
            "images/enemies/10/10_enemies_1_die_018.png",
            "images/enemies/10/10_enemies_1_die_019.png",
        }, scaledHeight, scaledWidth, position, 10, false)
    );
  }

  public double getScaledHeight() {
    return scaledHeight;
  }

  public double getScaledWidth() {
    return scaledWidth;
  }
}
