package warcraftTD.monsters.entities;

import warcraftTD.monsters.BaseMonster;
import warcraftTD.utils.Animation;
import warcraftTD.utils.Position;

import java.util.List;

public class FlyingNinja extends BaseMonster {
  public static double scaledHeight = 0.1;
  public static double scaledWidth = 0.1;


  public FlyingNinja(List<Position> path) {
    super(path,
        15,
        10,
        0.2,
        true,
        new Animation(new String[]{
            "images/enemies/9/jump-0.png",
            "images/enemies/9/jump-1.png",
            "images/enemies/9/jump-2.png",
            "images/enemies/9/jump-3.png",
            "images/enemies/9/jump-4.png",
            "images/enemies/9/jump-5.png",
            "images/enemies/9/jump-6.png",
            "images/enemies/9/jump-7.png",
            "images/enemies/9/jump-8.png",
            "images/enemies/9/jump-9.png",
            "images/enemies/9/jump-10.png",
            "images/enemies/9/jump-11.png",
            "images/enemies/9/jump-12.png",
            "images/enemies/9/jump-13.png",
            "images/enemies/9/jump-14.png",
            "images/enemies/9/jump-15.png",
            "images/enemies/9/jump-16.png",
            "images/enemies/9/jump-17.png",
            "images/enemies/9/jump-18.png",
            "images/enemies/9/jump-19.png",
        }, scaledHeight, scaledWidth, null, 30, true),
        new Animation(new String[]{
            "images/enemies/9/die-0.png",
            "images/enemies/9/die-1.png",
            "images/enemies/9/die-2.png",
            "images/enemies/9/die-3.png",
            "images/enemies/9/die-4.png",
            "images/enemies/9/die-5.png",
            "images/enemies/9/die-6.png",
            "images/enemies/9/die-7.png",
            "images/enemies/9/die-8.png",
            "images/enemies/9/die-9.png",
            "images/enemies/9/die-10.png",
            "images/enemies/9/die-11.png",
            "images/enemies/9/die-12.png",
            "images/enemies/9/die-13.png",
            "images/enemies/9/die-14.png",
            "images/enemies/9/die-15.png",
            "images/enemies/9/die-16.png",
            "images/enemies/9/die-17.png",
            "images/enemies/9/die-18.png",
            "images/enemies/9/die-19.png",
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
