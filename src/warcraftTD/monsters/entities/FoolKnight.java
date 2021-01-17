package warcraftTD.monsters.entities;

import warcraftTD.monsters.BaseMonster;
import warcraftTD.utils.Animation;
import warcraftTD.utils.Position;

import java.util.List;

public class FoolKnight extends BaseMonster {
  public static double scaledHeight = 0.1;
  public static double scaledWidth = 0.1;


  public FoolKnight(List<Position> path) {
    super(path,
        60,
        17,
        0.12,
        0.02,
        new Animation(new String[]{
            "images/enemies/6/walk-0.png",
            "images/enemies/6/walk-1.png",
            "images/enemies/6/walk-2.png",
            "images/enemies/6/walk-3.png",
            "images/enemies/6/walk-4.png",
            "images/enemies/6/walk-5.png",
            "images/enemies/6/walk-6.png",
            "images/enemies/6/walk-7.png",
            "images/enemies/6/walk-8.png",
            "images/enemies/6/walk-9.png",
            "images/enemies/6/walk-10.png",
            "images/enemies/6/walk-11.png",
            "images/enemies/6/walk-12.png",
            "images/enemies/6/walk-13.png",
            "images/enemies/6/walk-14.png",
            "images/enemies/6/walk-15.png",
            "images/enemies/6/walk-16.png",
            "images/enemies/6/walk-17.png",
            "images/enemies/6/walk-18.png",
            "images/enemies/6/walk-19.png",
        }, scaledHeight, scaledWidth, null, 60, true),
        new Animation(new String[]{
            "images/enemies/6/die-0.png",
            "images/enemies/6/die-1.png",
            "images/enemies/6/die-2.png",
            "images/enemies/6/die-3.png",
            "images/enemies/6/die-4.png",
            "images/enemies/6/die-5.png",
            "images/enemies/6/die-6.png",
            "images/enemies/6/die-7.png",
            "images/enemies/6/die-8.png",
            "images/enemies/6/die-9.png",
            "images/enemies/6/die-10.png",
            "images/enemies/6/die-11.png",
            "images/enemies/6/die-12.png",
            "images/enemies/6/die-13.png",
            "images/enemies/6/die-14.png",
            "images/enemies/6/die-15.png",
            "images/enemies/6/die-16.png",
            "images/enemies/6/die-17.png",
            "images/enemies/6/die-18.png",
            "images/enemies/6/die-19.png",
        }, scaledHeight, scaledWidth, null, 20, false)
    );
  }

  public double getScaledHeight() {
    return scaledHeight;
  }

  public double getScaledWidth() {
    return scaledWidth;
  }
}
