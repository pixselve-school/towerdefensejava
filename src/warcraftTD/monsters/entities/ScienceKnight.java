package warcraftTD.monsters.entities;

import warcraftTD.monsters.BaseMonster;
import warcraftTD.utils.Animation;
import warcraftTD.utils.Position;

import java.util.List;

/**
 * Chevalier de science fiction
 */
public class ScienceKnight extends BaseMonster {
  /**
   * Création d'un chevalier de science fiction
   *
   * @param path Le chemin que le montre va emprunter
   */
  public ScienceKnight(List<Position> path) {
    super(path,
        12,
        8,
        0.25,
        0.02,
        new Animation(new String[]{
            "images/enemies/4/walk-0.png",
            "images/enemies/4/walk-1.png",
            "images/enemies/4/walk-2.png",
            "images/enemies/4/walk-3.png",
            "images/enemies/4/walk-4.png",
            "images/enemies/4/walk-5.png",
            "images/enemies/4/walk-6.png",
            "images/enemies/4/walk-7.png",
            "images/enemies/4/walk-8.png",
            "images/enemies/4/walk-9.png",
            "images/enemies/4/walk-10.png",
            "images/enemies/4/walk-11.png",
            "images/enemies/4/walk-12.png",
            "images/enemies/4/walk-13.png",
            "images/enemies/4/walk-14.png",
            "images/enemies/4/walk-15.png",
            "images/enemies/4/walk-16.png",
            "images/enemies/4/walk-17.png",
            "images/enemies/4/walk-18.png",
            "images/enemies/4/walk-19.png",
        }, 1.0, null, 60, true),
        new Animation(new String[]{
            "images/enemies/4/die-0.png",
            "images/enemies/4/die-1.png",
            "images/enemies/4/die-2.png",
            "images/enemies/4/die-3.png",
            "images/enemies/4/die-4.png",
            "images/enemies/4/die-5.png",
            "images/enemies/4/die-6.png",
            "images/enemies/4/die-7.png",
            "images/enemies/4/die-8.png",
            "images/enemies/4/die-9.png",
            "images/enemies/4/die-10.png",
            "images/enemies/4/die-11.png",
            "images/enemies/4/die-12.png",
            "images/enemies/4/die-13.png",
            "images/enemies/4/die-14.png",
            "images/enemies/4/die-15.png",
            "images/enemies/4/die-16.png",
            "images/enemies/4/die-17.png",
            "images/enemies/4/die-18.png",
            "images/enemies/4/die-19.png",
        }, 1.0, null, 20, false)
    );
    this.setScaleHeight(0.1);
    this.setScaleWidth(0.1);
    this.setShiftX(-0.15);
    this.setShiftY(-0.28);
  }
}
