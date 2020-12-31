package warcraftTD;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.Timer;

public abstract class Monster {
  // Position du monstre à l'instant t
  Position p;
  // Vitesse du monstre: point / s
  double speed;
  // Boolean pour savoir si le monstre à atteint le chateau du joueur
  boolean reached;
  // Compteur de déplacement pour savoir si le monstre à atteint le chateau du joueur
  int checkpoint = 0;

  Vector vector;

  double previousLength;

  List<Position> path;

  private int health;


  Map<String, Effect> undergoingEffects;


  public Monster(Position p, List<Position> path, int nbSquareX, int nbSquareY, double squareWidth, double squareHeight, int health) {
    this.p = p;
    this.path = new LinkedList<>(path);
    this.path = this.path.stream().map(position -> new Position(position.x / nbSquareX + (squareWidth / 2), position.y / nbSquareY + (squareHeight / 2))).collect(Collectors.toList());
    this.vector = new Vector(this.p, this.path.get(0));
    this.previousLength = this.vector.length();
    this.health = health;
    this.speed = 0.1;
    this.undergoingEffects = new HashMap<>();

  }

  /**
   * Déplace le monstre en fonction de sa vitesse sur l'axe des x et des y et de sa prochaine position.
   */
  public void move(double delta_time) {
    double speedModifier = 1.0;
    for (Effect effect : this.undergoingEffects.values()) {
      speedModifier *= effect.getSpeedMultiplier();
    }


    Position newPosition = new Position(this.p.x + this.speed * speedModifier * delta_time * this.vector.normal().getX(), this.p.y + this.speed * speedModifier * delta_time * this.vector.normal().getY());

    if (this.path.size() > 0) {
      if (this.previousLength > new Vector(newPosition, this.path.get(0)).length()) {
        this.p = newPosition;
        this.previousLength = new Vector(newPosition, this.path.get(0)).length();
      } else {
        this.path.remove(0);
        if (this.path.size() > 0) {
          this.vector = new Vector(this.p, this.path.get(0));
          this.previousLength = this.vector.length();
        }

      }
    }
  }

  public boolean hasFinishedPath() {
    return this.path.size() == 0;
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

  public void update(double deltaTime) {
    this.updateEffectsDuration(deltaTime);
    this.move(deltaTime);
    this.draw();

    this.checkpoint++;
  }


  /**
   * Update all the undergoing effects durations
   *
   * @param deltaTime World delta time
   */
  private void updateEffectsDuration(double deltaTime) {
    Iterator<Map.Entry<String, Effect>> entryIterator = this.undergoingEffects.entrySet().iterator();
    while (entryIterator.hasNext()) {
      Map.Entry<String, Effect> effectEntry = entryIterator.next();
      effectEntry.getValue().setDuration(effectEntry.getValue().getDuration() - deltaTime);
      if (effectEntry.getValue().getDuration() <= 0) {
        entryIterator.remove();
      }
    }

  }

  public void takeDamage(int damage, World world, Color colordamage) {
    world.HUD.addNotifText(this.p, new Font("Arial", Font.BOLD, 20), -0.1, "" + damage, colordamage);
    this.health -= damage;
  }

  public boolean isDead() {
    return this.health <= 0;
  }

  public void applyPoisonEffect(int duration, int damage) {
    this.undergoingEffects.computeIfAbsent("poison", (s) -> new Effect(duration, 1.0, damage, 1.0)).setDurationIfGreater(duration);
  }

  public void applySlowEffect(int duration, int slowPercent) {
    this.undergoingEffects.computeIfAbsent("slow", (s) -> new Effect(duration, 1.0, 0, 1.0 / slowPercent)).setDurationIfGreater(duration);
  }

  /**
   * Fonction abstraite qui sera instanciée dans les classes filles pour afficher le monstre sur le plateau de jeu.
   */
  public abstract void draw();
}
