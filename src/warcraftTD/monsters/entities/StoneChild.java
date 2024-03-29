package warcraftTD.monsters.entities;

import warcraftTD.monsters.BaseMonster;
import warcraftTD.utils.Animation;
import warcraftTD.utils.Position;

import java.util.List;

/**
 * Enfant de pierre
 */
public class StoneChild extends BaseMonster {

  /**
   * Création d'un enfant de pierre
   *
   * @param path Le chemin que le montre va emprunter
   */
  public StoneChild(List<Position> path) {
    super(path,
        15,
        15,
        0.1,
        0.02,
        new Animation(new String[]{
            "images/enemies/8/walk-0.png",
            "images/enemies/8/walk-1.png",
            "images/enemies/8/walk-2.png",
            "images/enemies/8/walk-3.png",
            "images/enemies/8/walk-4.png",
            "images/enemies/8/walk-5.png",
            "images/enemies/8/walk-6.png",
            "images/enemies/8/walk-7.png",
            "images/enemies/8/walk-8.png",
            "images/enemies/8/walk-9.png",
            "images/enemies/8/walk-10.png",
            "images/enemies/8/walk-11.png",
            "images/enemies/8/walk-12.png",
            "images/enemies/8/walk-13.png",
            "images/enemies/8/walk-14.png",
            "images/enemies/8/walk-15.png",
            "images/enemies/8/walk-16.png",
            "images/enemies/8/walk-17.png",
            "images/enemies/8/walk-18.png",
            "images/enemies/8/walk-19.png",
        }, 1.0, null, 60, true),
        new Animation(new String[]{
            "images/enemies/8/die-0.png",
            "images/enemies/8/die-1.png",
            "images/enemies/8/die-2.png",
            "images/enemies/8/die-3.png",
            "images/enemies/8/die-4.png",
            "images/enemies/8/die-5.png",
            "images/enemies/8/die-6.png",
            "images/enemies/8/die-7.png",
            "images/enemies/8/die-8.png",
            "images/enemies/8/die-9.png",
            "images/enemies/8/die-10.png",
            "images/enemies/8/die-11.png",
            "images/enemies/8/die-12.png",
            "images/enemies/8/die-13.png",
            "images/enemies/8/die-14.png",
            "images/enemies/8/die-15.png",
            "images/enemies/8/die-16.png",
            "images/enemies/8/die-17.png",
            "images/enemies/8/die-18.png",
            "images/enemies/8/die-19.png",
        }, 1.0, null, 20, false)
    );
    this.setScaleHeight(0.09);
    this.setScaleWidth(0.09);
    this.setShiftX(-0.15);
    this.setShiftY(-0.3);
  }
}
