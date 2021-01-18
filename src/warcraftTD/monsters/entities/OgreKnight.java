package warcraftTD.monsters.entities;

import warcraftTD.monsters.BaseMonster;
import warcraftTD.utils.Animation;
import warcraftTD.utils.Position;

import java.util.List;

public class OgreKnight extends BaseMonster {

  public OgreKnight(List<Position> path) {
    super(path,
        40,
        15,
        0.15,
        0.02,
        new Animation(new String[]{
            "images/enemies/5/walk-0.png",
            "images/enemies/5/walk-1.png",
            "images/enemies/5/walk-2.png",
            "images/enemies/5/walk-3.png",
            "images/enemies/5/walk-4.png",
            "images/enemies/5/walk-5.png",
            "images/enemies/5/walk-6.png",
            "images/enemies/5/walk-7.png",
            "images/enemies/5/walk-8.png",
            "images/enemies/5/walk-9.png",
            "images/enemies/5/walk-10.png",
            "images/enemies/5/walk-11.png",
            "images/enemies/5/walk-12.png",
            "images/enemies/5/walk-13.png",
            "images/enemies/5/walk-14.png",
            "images/enemies/5/walk-15.png",
            "images/enemies/5/walk-16.png",
            "images/enemies/5/walk-17.png",
            "images/enemies/5/walk-18.png",
            "images/enemies/5/walk-19.png",
        }, 1.0, 1.0, null, 60, true),
        new Animation(new String[]{
            "images/enemies/5/die-0.png",
            "images/enemies/5/die-1.png",
            "images/enemies/5/die-2.png",
            "images/enemies/5/die-3.png",
            "images/enemies/5/die-4.png",
            "images/enemies/5/die-5.png",
            "images/enemies/5/die-6.png",
            "images/enemies/5/die-7.png",
            "images/enemies/5/die-8.png",
            "images/enemies/5/die-9.png",
            "images/enemies/5/die-10.png",
            "images/enemies/5/die-11.png",
            "images/enemies/5/die-12.png",
            "images/enemies/5/die-13.png",
            "images/enemies/5/die-14.png",
            "images/enemies/5/die-15.png",
            "images/enemies/5/die-16.png",
            "images/enemies/5/die-17.png",
            "images/enemies/5/die-18.png",
            "images/enemies/5/die-19.png",
        }, 1.0, 1.0, null, 20, false)
    );
    this.setScaleHeight(0.1);
    this.setScaleWidth(0.1);
    this.setShiftX(-0.15);
    this.setShiftY(-0.35);
  }
}
