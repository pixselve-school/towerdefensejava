package warcraftTD.monsters;

import warcraftTD.WorldGame;
import warcraftTD.particules.EntityParticules;
import warcraftTD.particules.ImageParticule;
import warcraftTD.particules.RandomParticuleGenerator;
import warcraftTD.utils.DrawableEntity;
import warcraftTD.utils.Position;
import warcraftTD.utils.Vector;

import java.awt.*;
import java.util.List;
import java.util.*;

/**
 * Monster class
 */
public abstract class Monster extends DrawableEntity {

  /**
   * The monster speed
   */
  private final double speed;
  /**
   * The path the monster will follow
   */
  private final List<Position> path;
  /**
   * The effects the monster is subjected to
   */
  private final Map<String, Effect> undergoingEffects;
  /**
   * The amount of gold the monster will drop when killed
   */
  private final int goldWhenDead;
  /**
   * The particules the monster is generating
   */
  private final EntityParticules entityParticules;
  /**
   * The monster position
   */
  private Position position;
  /**
   * A vector container
   */
  private Vector vector;
  /**
   * Previous vector length
   */
  private double previousLength;
  /**
   * The monster health
   */
  private int health;
  /**
   * The current remove status of the monster
   */

  /**
   * Le rayon de la hit box du monstre
   */
  private double hitBoxRadius;

  private boolean isReadyToBeRemoved;

  /**
   * @param health       The monster base health
   * @param goldWhenDead The amount of gold the monster will drop when killed
   * @param speed        The monster base speed
   * @param path         The path the monster will follow
   */
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
   * Get the current remove status of the monster
   *
   * @return true if the monster is ready to be removed
   */
  public boolean isReadyToBeRemoved() {
    return this.isReadyToBeRemoved;
  }

  /**
   * Set the current remove status of the monster
   *
   * @param readyToBeRemoved The new remove status of the monster
   */
  public void setReadyToBeRemoved(boolean readyToBeRemoved) {
    this.isReadyToBeRemoved = readyToBeRemoved;
  }

  /**
   * Move the monster
   *
   * @param deltaTime The game deltaTime
   */
  public void move(double deltaTime) {
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

    Position newPosition = new Position(this.position.getX() + this.speed * speedModifier * deltaTime * this.vector.normal().getX(), this.position.getY() + this.speed * speedModifier * deltaTime * this.vector.normal().getY());

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

  /**
   * Check if the monster finished its path
   *
   * @return true if the monster finished its path
   */
  public boolean hasFinishedPath() {
    return this.path.size() == 0;
  }


  /**
   * Update the monster effects, figure out its next position and draw it
   *
   * @param deltaTime The game delta time
   */
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

  /**
   * Inflict damage to the monster
   *
   * @param damage            The amount of health the monster will loose
   * @param world             The current world
   * @param notificationColor The color of the damage notification
   */
  public void takeDamage(int damage, WorldGame world, Color notificationColor) {
    world.getHud().addNotifText(this.position, new Font("Arial", Font.BOLD, 20), -0.1, "" + damage, notificationColor);
    this.health -= damage;
  }

  /**
   * Check if the monster is alive or not
   *
   * @return true if the monster is alive
   */
  public boolean isDead() {
    return this.health <= 0;
  }

  /**
   * Apply poison effect to the monster
   *
   * @param duration The effect duration
   * @param damage   The amount of health the effect with inflict every seconds
   */
  public void applyPoisonEffect(int duration, int damage) {
    this.undergoingEffects.computeIfAbsent("poison", (s) -> new Effect(duration, 1.0, -damage, 1.0)).setDurationIfGreater(duration);
  }

  /**
   * Apply slow effect to the monster
   *
   * @param duration    The effect duration
   * @param slowPercent The percentage the speed of the monster will be multiplied to
   */
  public void applySlowEffect(int duration, int slowPercent) {
    this.undergoingEffects.computeIfAbsent("slow", (s) -> new Effect(duration, 1.0, 0, slowPercent / 100.0)).setDurationIfGreater(duration);
  }

  /**
   * Check if the monster is flying
   *
   * @return True if the monster is flying
   */
  public abstract boolean isFlying();

  /**
   * Draw the monster
   *
   * @param deltaTime The game delta time
   */
  public abstract void draw(double deltaTime);

  /**
   * Get the monster position
   *
   * @return The monster position
   */
  public Position getPosition() {
    return this.position;
  }

  /**
   * Set the monster position
   *
   * @param position The new position
   */
  public void setPosition(Position position) {
    this.position = position;
  }

  /**
   * Get the amount of gold the monster drop when killed
   *
   * @return The amount of gold the monster drop when killed
   */
  public int getGoldWhenDead() {
    return this.goldWhenDead;
  }

  /**
   * Récupère le rayon de la hit box du monstre
   * @return Le rayon de la hit box du monstre
   */
  public double getHitBoxRadius() {
    return this.hitBoxRadius;
  }

}

