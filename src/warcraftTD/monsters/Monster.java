package warcraftTD.monsters;

import warcraftTD.WorldGame;
import warcraftTD.particules.EntityParticules;
import warcraftTD.particules.ImageParticule;
import warcraftTD.particules.RandomParticuleGenerator;
import warcraftTD.utils.Position;
import warcraftTD.utils.Vector;

import java.awt.*;
import java.util.List;
import java.util.*;
import java.util.stream.Collectors;

public abstract class Monster {
  // Position du monstre à l'instant t
  private Position position;
  // Vitesse du monstre: point / s
  private double speed;

  // Compteur de déplacement pour savoir si le monstre à atteint le chateau du joueur
  private Vector vector;
  private double previousLength;
  private List<Position> path;
  private int health;
  private final Map<String, Effect> undergoingEffects;
  public boolean isReadyToBeRemoved() {
    return this.isReadyToBeRemoved;
  }
  public void setReadyToBeRemoved(boolean readyToBeRemoved) {
    this.isReadyToBeRemoved = readyToBeRemoved;
  }
  private boolean isReadyToBeRemoved;
  private final int goldWhenDead;
  private final EntityParticules entityParticules;

  public Monster(Position p, WorldGame world, int health, int goldWhenDead, double speed) {
    this.position = p;
    this.path = new LinkedList<>(world.getPaths());
    this.path = this.path.stream().map(position -> new Position(position.getX() / world.getNbSquareX() + (world.getSquareWidth() / 2), position.getY() / world.getNbSquareY() + (world.getSquareHeight() / 2))).collect(Collectors.toList());
    this.vector = new Vector(this.position, this.path.get(0));
    this.previousLength = this.vector.length();
    this.health = health;
    this.speed = speed;
    this.undergoingEffects = new HashMap<>();
    this.isReadyToBeRemoved = false;
    this.goldWhenDead = goldWhenDead;
    this.entityParticules = new EntityParticules();
  }

  public Monster(int health, int goldWhenDead, double speed, List<Position> path) {
    this.path = new LinkedList<>(path);
    this.position = this.path.get(0);
    this.vector = new Vector(this.position, this.path.get(0));
    this.previousLength = this.vector.length();
    this.health = health;
    this.speed = speed;
    this.undergoingEffects = new HashMap<>();
    this.isReadyToBeRemoved = false;
    this.goldWhenDead = goldWhenDead;
    this.entityParticules = new EntityParticules();
  }

  /**
   * Déplace le monstre en fonction de sa vitesse sur l'axe des x et des y et de sa prochaine position.
   */
  public void move(double delta_time) {
    if (this.isDead()) {
      return;
    }


    double speedModifier = 1.0;
    for (Effect effect : this.undergoingEffects.values()) {
      speedModifier *= effect.getSpeedMultiplier();
    }

    Effect poisonEffect = this.undergoingEffects.get("poison");
    if (poisonEffect != null) {
      this.entityParticules.addGenerator(new RandomParticuleGenerator(this.position, 1, 0.3, 0.03, new ImageParticule(1, 0.01, 0.1, "images/poison.png")));
      if (poisonEffect.getTimeTracking() >= 1.0) {
        this.health += poisonEffect.getHealthAdd();
        poisonEffect.resetTimeTracking();
      }
    }

    Position newPosition = new Position(this.position.getX() + this.speed * speedModifier * delta_time * this.vector.normal().getX(), this.position.getY() + this.speed * speedModifier * delta_time * this.vector.normal().getY());

    if (this.path.size() > 0) {
      if (this.previousLength > new Vector(newPosition, this.path.get(0)).length()) {
        this.position = newPosition;
        this.previousLength = new Vector(newPosition, this.path.get(0)).length();
      } else {
        this.path.remove(0);
        if (this.path.size() > 0) {
          this.vector = new Vector(this.position, this.path.get(0));
          this.previousLength = this.vector.length();
        }

      }
    }
  }

  public boolean hasFinishedPath() {
    return this.path.size() == 0;
  }


  public void update(double deltaTime) {
    this.updateEffectsDuration(deltaTime);
    this.move(deltaTime);
    this.draw(deltaTime);
    this.entityParticules.updateGenerators(deltaTime);
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
      effectEntry.getValue().addTimeToTimeTracking(deltaTime);
      if (effectEntry.getValue().getDuration() <= 0) {
        entryIterator.remove();
      }
    }

  }

  public void takeDamage(int damage, WorldGame world, Color colordamage) {
    world.getHUD().addNotifText(this.position, new Font("Arial", Font.BOLD, 20), -0.1, "" + damage, colordamage);
    this.health -= damage;
  }

  public boolean isDead() {
    return this.health <= 0;
  }

  public void applyPoisonEffect(int duration, int damage) {
    this.undergoingEffects.computeIfAbsent("poison", (s) -> new Effect(duration, 1.0, -damage, 1.0)).setDurationIfGreater(duration);
  }

  public void applySlowEffect(int duration, int slowPercent) {
    this.undergoingEffects.computeIfAbsent("slow", (s) -> new Effect(duration, 1.0, 0, slowPercent / 100.0)).setDurationIfGreater(duration);
  }

  public abstract boolean isFlying();

  /**
   * Fonction abstraite qui sera instanciée dans les classes filles pour afficher le monstre sur le plateau de jeu.
   */
  public abstract void draw(double deltaTime);

  public Position getPosition() {
    return this.position;
  }

  public void setPosition(Position position) {
    this.position = position;
  }

  public double getSpeed() {
    return this.speed;
  }

  public void setSpeed(double speed) {
    this.speed = speed;
  }


  public Vector getVector() {
    return this.vector;
  }

  public void setVector(Vector vector) {
    this.vector = vector;
  }


  public List<Position> getPath() {
    return this.path;
  }

  public void setPath(List<Position> path) {
    this.path = path;
  }


  public int getGoldWhenDead() {
    return this.goldWhenDead;
  }

}

