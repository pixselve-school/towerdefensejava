package warcraftTD.monsters.entities;

import warcraftTD.monsters.BaseMonster;
import warcraftTD.utils.Animation;
import warcraftTD.utils.Position;

import java.util.List;

/**
 * Géant de pierre
 */
public class StoneGiant extends BaseMonster {

  /**
   * Création d'un géant de pierre
   *
   * @param path Le chemin que le montre va emprunter
   */
  public StoneGiant(List<Position> path) {
    super(path,
        300,
        100,
        0.08,
        0.04,
        new Animation(new String[]{
            "images/enemies/10/walk-0.png",
            "images/enemies/10/walk-1.png",
            "images/enemies/10/walk-2.png",
            "images/enemies/10/walk-3.png",
            "images/enemies/10/walk-4.png",
            "images/enemies/10/walk-5.png",
            "images/enemies/10/walk-6.png",
            "images/enemies/10/walk-7.png",
            "images/enemies/10/walk-8.png",
            "images/enemies/10/walk-9.png",
            "images/enemies/10/walk-10.png",
            "images/enemies/10/walk-11.png",
            "images/enemies/10/walk-12.png",
            "images/enemies/10/walk-13.png",
            "images/enemies/10/walk-14.png",
            "images/enemies/10/walk-15.png",
            "images/enemies/10/walk-16.png",
            "images/enemies/10/walk-17.png",
            "images/enemies/10/walk-18.png",
            "images/enemies/10/walk-19.png",
        }, 1.0, 1.0, null, 10, true),
        new Animation(new String[]{
            "images/enemies/10/die-0.png",
            "images/enemies/10/die-1.png",
            "images/enemies/10/die-2.png",
            "images/enemies/10/die-3.png",
            "images/enemies/10/die-4.png",
            "images/enemies/10/die-5.png",
            "images/enemies/10/die-6.png",
            "images/enemies/10/die-7.png",
            "images/enemies/10/die-8.png",
            "images/enemies/10/die-9.png",
            "images/enemies/10/die-10.png",
            "images/enemies/10/die-11.png",
            "images/enemies/10/die-12.png",
            "images/enemies/10/die-13.png",
            "images/enemies/10/die-14.png",
            "images/enemies/10/die-15.png",
            "images/enemies/10/die-16.png",
            "images/enemies/10/die-17.png",
            "images/enemies/10/die-18.png",
            "images/enemies/10/die-19.png",
        }, 1.0, 1.0, null, 10, false)
    );
    this.setScaleHeight(0.2);
    this.setScaleWidth(0.25);
    this.setShiftX(-0.15);
    this.setShiftY(-0.35);
  }
}
