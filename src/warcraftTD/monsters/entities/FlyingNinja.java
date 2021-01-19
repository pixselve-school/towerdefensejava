package warcraftTD.monsters.entities;

import warcraftTD.monsters.BaseMonster;
import warcraftTD.utils.Animation;
import warcraftTD.utils.Position;

import java.util.List;

/**
 * Ninja volant
 */
public class FlyingNinja extends BaseMonster {

  /**
   * Creation d'un ninja volant
   *
   * @param path Le chemin que le montre va emprunter
   */
  public FlyingNinja(List<Position> path) {
    super(path,
        15,
        10,
        0.2,
        0.02,
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
        }, 1.0, null, 30, true),
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
        }, 1.0, null, 20, false)
    );
    this.setScaleHeight(0.1);
    this.setScaleWidth(0.1);
    this.setShiftX(-0.35);
    this.setShiftY(-0.35);
  }
}
