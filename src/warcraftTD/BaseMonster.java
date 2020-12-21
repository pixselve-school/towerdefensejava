package warcraftTD;

import java.util.List;

public class BaseMonster extends Monster {

  public BaseMonster(Position p, List<Position> path, int nbSquareX, int nbSquareY, double squareWidth, double squareHeight) {
    super(p, path, nbSquareX, nbSquareY, squareWidth, squareHeight);
  }

  /**
   * Affiche un monstre qui change de couleur au cours du temps
   * Le monstre est représenté par un cercle de couleur bleue ou grise
   */
  public void draw() {
    StdDraw.setPenColor(StdDraw.BLUE);
    StdDraw.filledCircle(this.p.x, this.p.y, 0.01);
  }
}
