package warcraftTD;

import java.awt.*;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public abstract class Monster {
  // Position du monstre à l'instant t
  Position p;
  // Vitesse du monstre: point / s
  double speed;
  // Boolean pour savoir si le monstre à atteint le chateau du joueur
  boolean reached;
  // Compteur de déplacement pour savoir si le monstre à atteint le chateau du joueur
  int checkpoint = 0;
  // Position du monstre à l'instant t+1
  private Position nextP;

  Vector vector;

  double previousLength;

  List<Position> path;


  public Monster(Position p, List<Position> path, int nbSquareX, int nbSquareY, double squareWidth, double squareHeight) {
    this.p = p;
    this.nextP = new Position(0.5, 0.5);
    this.path = new LinkedList<>(path);
    this.path = this.path.stream().map(position -> new Position(position.x / nbSquareX + (squareWidth / 2), position.y / nbSquareY + (squareHeight / 2))).collect(Collectors.toList());
    this.vector = new Vector(this.p, this.path.get(0));
    this.previousLength = this.vector.length();

  }

  /**
   * Déplace le monstre en fonction de sa vitesse sur l'axe des x et des y et de sa prochaine position.
   */
  public void move(double delta_time) {
    Position newPosition = new Position(this.p.x + this.speed * delta_time* this.vector.normal().getX(), this.p.y + this.speed * delta_time * this.vector.normal().getY());

    if (this.path.size() > 0) {
      if (this.previousLength > new Vector(newPosition, this.path.get(0)).length()) {
        this.p = newPosition;
        this.previousLength = new Vector(newPosition, this.path.get(0)).length();
      } else {
        this.p = this.path.get(0);
        this.path.remove(0);
        System.out.println("heure");
        if (this.path.size() > 0) {
          this.vector = new Vector(this.p, this.path.get(0));
          this.previousLength = this.vector.length();
        }

      }
    }


  }

  /**
   * Debug only
   */
  private void debug() {
    if (this.path.size() > 0) {
      StdDraw.setPenColor(StdDraw.RED);
      StdDraw.filledCircle(this.path.get(0).x, this.path.get(0).y, 0.01);
    }
  }

  public void update(double delta_time) {
    this.move(delta_time);
    this.draw();

    this.checkpoint++;
  }

  public void takeDamage(int damage, World world){
    world.HUD.addNotifText(this.p, new Font("Arial", Font.BOLD, 20), -0.1, damage+"");
    // à compléter merci merci ;)
  }

  public void applyPoisonEffect(double duration, int damage){
    // damage toutes les demis secondes pendant duration secondes stp ;)
  }

  public void applySlowEffect(double duration, int slowPercent){
    // slow de slowPercent % pendant duration secondes ;)
  }

  /**
   * Fonction abstraite qui sera instanciée dans les classes filles pour afficher le monstre sur le plateau de jeu.
   */
  public abstract void draw();
}
