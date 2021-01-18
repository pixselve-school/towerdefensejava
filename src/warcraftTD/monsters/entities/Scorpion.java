package warcraftTD.monsters.entities;

import warcraftTD.monsters.BaseMonster;
import warcraftTD.utils.Animation;
import warcraftTD.utils.Position;

import java.util.List;

public class Scorpion extends BaseMonster {

  public Scorpion(List<Position> path) {
    super(path,
        5,
        5,
        0.1,
        0.015,
        new Animation(new String[]{
            "images/enemies/1/walk-0.png",
            "images/enemies/1/walk-1.png",
            "images/enemies/1/walk-2.png",
            "images/enemies/1/walk-3.png",
            "images/enemies/1/walk-4.png",
            "images/enemies/1/walk-5.png",
            "images/enemies/1/walk-6.png",
            "images/enemies/1/walk-7.png",
            "images/enemies/1/walk-8.png",
            "images/enemies/1/walk-9.png",
            "images/enemies/1/walk-10.png",
            "images/enemies/1/walk-11.png",
            "images/enemies/1/walk-12.png",
            "images/enemies/1/walk-13.png",
            "images/enemies/1/walk-14.png",
            "images/enemies/1/walk-15.png",
            "images/enemies/1/walk-16.png",
            "images/enemies/1/walk-17.png",
            "images/enemies/1/walk-18.png",
            "images/enemies/1/walk-19.png",
        }, 1.0, 1.0, null, 60, true),
        new Animation(new String[]{
            "images/enemies/1/die-0.png",
            "images/enemies/1/die-1.png",
            "images/enemies/1/die-2.png",
            "images/enemies/1/die-3.png",
            "images/enemies/1/die-4.png",
            "images/enemies/1/die-5.png",
            "images/enemies/1/die-6.png",
            "images/enemies/1/die-7.png",
            "images/enemies/1/die-8.png",
            "images/enemies/1/die-9.png",
            "images/enemies/1/die-10.png",
            "images/enemies/1/die-11.png",
            "images/enemies/1/die-12.png",
            "images/enemies/1/die-13.png",
            "images/enemies/1/die-14.png",
            "images/enemies/1/die-15.png",
            "images/enemies/1/die-16.png",
            "images/enemies/1/die-17.png",
            "images/enemies/1/die-18.png",
            "images/enemies/1/die-19.png",
        }, 1.0, 1.0, null, 20, false));
    this.setScaleHeight(0.07);
    this.setScaleWidth(0.09);
    this.setShiftX(-0.05);
    this.setShiftY(-0.25);
  }
}
