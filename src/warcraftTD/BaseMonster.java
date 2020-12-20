package warcraftTD;

public class BaseMonster extends Monster {

  public BaseMonster(Position p) {
    super(p);
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
