package warcraftTD.monsters.entities;

import warcraftTD.monsters.BaseMonster;
import warcraftTD.utils.Animation;
import warcraftTD.utils.Position;

import java.util.List;

/**
 * Ogre bleu
 */
public class BlueOgre extends BaseMonster {

  /**
   * Creation d'un ogre bleu
   *
   * @param path Le chemin que le montre va emprunter
   */
  public BlueOgre(List<Position> path) {
    super(path,
        100,
        50,
        0.2,
        0.02,
        new Animation(new String[]{
            "images/enemies/7/walk-0.png",
            "images/enemies/7/walk-1.png",
            "images/enemies/7/walk-2.png",
            "images/enemies/7/walk-3.png",
            "images/enemies/7/walk-4.png",
            "images/enemies/7/walk-5.png",
            "images/enemies/7/walk-6.png",
            "images/enemies/7/walk-7.png",
            "images/enemies/7/walk-8.png",
            "images/enemies/7/walk-9.png",
            "images/enemies/7/walk-10.png",
            "images/enemies/7/walk-11.png",
            "images/enemies/7/walk-12.png",
            "images/enemies/7/walk-13.png",
            "images/enemies/7/walk-14.png",
            "images/enemies/7/walk-15.png",
            "images/enemies/7/walk-16.png",
            "images/enemies/7/walk-17.png",
            "images/enemies/7/walk-18.png",
            "images/enemies/7/walk-19.png",
        }, 1.0, 1.0, null, 60, true),
        new Animation(new String[]{
            "images/enemies/7/die-0.png",
            "images/enemies/7/die-1.png",
            "images/enemies/7/die-2.png",
            "images/enemies/7/die-3.png",
            "images/enemies/7/die-4.png",
            "images/enemies/7/die-5.png",
            "images/enemies/7/die-6.png",
            "images/enemies/7/die-7.png",
            "images/enemies/7/die-8.png",
            "images/enemies/7/die-9.png",
            "images/enemies/7/die-10.png",
            "images/enemies/7/die-11.png",
            "images/enemies/7/die-12.png",
            "images/enemies/7/die-13.png",
            "images/enemies/7/die-14.png",
            "images/enemies/7/die-15.png",
            "images/enemies/7/die-16.png",
            "images/enemies/7/die-17.png",
            "images/enemies/7/die-18.png",
            "images/enemies/7/die-19.png",
        }, 1.0, 1.0, null, 20, false));
    this.setScaleHeight(0.1);
    this.setScaleWidth(0.1);
    this.setShiftX(-0.25);
    this.setShiftY(-0.35);
  }
}


