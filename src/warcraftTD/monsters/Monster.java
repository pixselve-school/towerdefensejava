package warcraftTD.monsters;

import warcraftTD.WorldGame;
import warcraftTD.libs.StdDraw;
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
 * Un monstre
 */
public abstract class Monster extends DrawableEntity {

  /**
   * La vitesse du monstre
   */
  private final double speed;
  /**
   * Le chemin que suivra le monstre
   */
  private final List<Position> path;
  /**
   * Les effets auxquels le monstre est soumis
   */
  private final Map<String, Effect> undergoingEffects;
  /**
   * La quantité d'or que le monstre va rapporter au joueur lorsqu'il sera tué
   */
  private final int goldWhenDead;
  /**
   * Les particules que le monstre génère
   */
  private final EntityParticules entityParticules;
  /**
   * Le rayon de la hit box du monstre
   */
  private final double hitBoxRadius;
  /**
   * La position du monstre
   */
  private Position position;
  /**
   * Un conteneur à vecteur
   */
  private Vector vector;
  /**
   * Longueur du vecteur précédent
   */
  private double previousLength;
  /**
   * La santé du monstre
   */
  private int health;
  /**
   * Indique si le monstre peut être supprimer
   */
  private boolean isReadyToBeRemoved;
  /**
   * Décalage horizontal pour l'affichage du monstre
   */
  private double shiftX;
  /**
   * Décalage vertical pour l'affichage du monstre
   */
  private double shiftY;

  /**
   * Largeur du monstre
   */
  private double scaleWidth;

  /**
   * Hauteur du monstre
   */
  private double scaleHeight;

  /**
   * @param health       La vie de base du monstre
   * @param goldWhenDead La quantité d'or que le monstre va rapporter au joueur lorsqu'il sera tué
   * @param speed        La vitesse de base du monstre
   * @param path         Le chemin que suivra le monstre
   * @param hitBoxRadius Le rayon de la hit box du monstre
   */
  public Monster(int health, int goldWhenDead, double speed, double hitBoxRadius, List<Position> path) {
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
    this.hitBoxRadius = hitBoxRadius;
  }

  /**
   * Récupère la largeur du monstre
   *
   * @return Largeur du monstre
   */
  public double getScaleWidth() {
    return this.scaleWidth;
  }

  /**
   * Modifie la largeur du monstre
   *
   * @param scaleWidth La nouvelle largeur
   */
  public void setScaleWidth(double scaleWidth) {
    this.scaleWidth = scaleWidth;
  }

  /**
   * Modifie la hauteur du monstre
   *
   * @return La hauteur du monstre
   */
  public double getScaleHeight() {
    return this.scaleHeight;
  }

  /**
   * Modifie la hauteur du monstre
   *
   * @param scaleHeight La nouvelle hauteur du monstre
   */
  public void setScaleHeight(double scaleHeight) {
    this.scaleHeight = scaleHeight;
  }

  /**
   * Récupère le décalage horizontal d'affichage du monstre
   *
   * @return le décalage horizontal pour l'affichage du monstre
   */
  public double getShiftX() {
    return this.shiftX;
  }

  /**
   * Modifie décalage horizontal d'affichage du monstre
   *
   * @param shiftX le décalage horizontal pour l'affichage du monstre
   */
  public void setShiftX(double shiftX) {
    this.shiftX = shiftX;
  }

  /**
   * Récupère le décalage vertical d'affichage du monstre
   *
   * @return le décalage vertical pour l'affichage du monstre
   */
  public double getShiftY() {
    return this.shiftY;
  }

  /**
   * Modifie décalage vertical d'affichage du monstre
   *
   * @param shiftY le décalage vertical pour l'affichage du monstre
   */
  public void setShiftY(double shiftY) {
    this.shiftY = shiftY;
  }

  /**
   * Récupère l'indication sur la suppression du monstre
   *
   * @return true si le monstre peut être supprimer
   */
  public boolean isReadyToBeRemoved() {
    return this.isReadyToBeRemoved;
  }

  /**
   * Modifie l'indication sur la suppression du monstre
   *
   * @param readyToBeRemoved La nouvelle indication sur la suppression du monstre
   */
  public void setReadyToBeRemoved(boolean readyToBeRemoved) {
    this.isReadyToBeRemoved = readyToBeRemoved;
  }

  /**
   * Déplace le monstre
   *
   * @param deltaTime La delta temps du jeu
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
   * Vérifie si le monstre a terminé son parcours
   *
   * @return true si le monstre a terminé son parcours
   */
  public boolean hasFinishedPath() {
    return this.path.size() == 0;
  }


  public void drawHitBox() {
    StdDraw.setPenColor(new Color(0, 255, 217, 35));
    StdDraw.filledCircle(this.position.getX(), this.position.getY(), this.hitBoxRadius);
    StdDraw.setPenColor(new Color(0, 255, 217));
    StdDraw.circle(this.position.getX(), this.position.getY(), this.hitBoxRadius);
  }

  /**
   * Actualisez les effets du monstre, déterminez sa prochaine position et dessine le monstre
   *
   * @param deltaTime Le delta temps du jeu
   */
  public void update(double deltaTime) {
    this.updateEffectsDuration(deltaTime);
    this.move(deltaTime);
    this.draw(deltaTime);
    this.entityParticules.updateGenerators(deltaTime);
  }


  /**
   * Mise à jour de toutes les durées d'effets en cours
   *
   * @param deltaTime Le delta temps du jeu
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
   * Inflige des dommages au monstre
   *
   * @param damage            La quantité de santé que le monstre va perdre
   * @param world             Le monde actuel
   * @param notificationColor La couleur de la notification des dégâts
   */
  public void takeDamage(int damage, WorldGame world, Color notificationColor) {
    world.getHud().addNotifText(this.position, new Font("Arial", Font.BOLD, 20), -0.1, "" + damage, notificationColor);
    this.health -= damage;
  }

  /**
   * Vérifier si le monstre est vivant ou non
   *
   * @return true si le monstre est vivant
   */
  public boolean isDead() {
    return this.health <= 0;
  }

  /**
   * Appliquer l'effet de poison au monstre
   *
   * @param duration La durée de l'effet
   * @param damage   La quantité de dégâts que l'effet inflige chaque seconde
   */
  public void applyPoisonEffect(int duration, int damage) {
    this.undergoingEffects.computeIfAbsent("poison", (s) -> new Effect(duration, 1.0, -damage, 1.0)).setDurationIfGreater(duration);
  }

  /**
   * Appliquer un effet lent au monstre
   *
   * @param duration    La durée de l'effet
   * @param slowPercent Le facteur de lenteur appliqué à la vitesse
   */
  public void applySlowEffect(int duration, int slowPercent) {
    this.undergoingEffects.computeIfAbsent("slow", (s) -> new Effect(duration, 1.0, 0, slowPercent / 100.0)).setDurationIfGreater(duration);
  }

  /**
   * Vérifiez si le monstre vole
   *
   * @return true si le monstre vole
   */
  public abstract boolean isFlying();

  /**
   * Dessine le monstre
   *
   * @param deltaTime Le delta temps du jeu
   */
  public abstract void draw(double deltaTime);

  /**
   * Récupère la position du monstre
   *
   * @return la position du monstre
   */
  public Position getPosition() {
    return this.position;
  }

  /**
   * Modifie la position du monstre
   *
   * @param position la nouvelle position du monstre
   */
  public void setPosition(Position position) {
    this.position = position;
  }

  /**
   * Récupère la quantité d'or que le monstre va rapporter au joueur lorsqu'il sera tué
   *
   * @return la quantité d'or que le monstre va rapporter au joueur lorsqu'il sera tué
   */
  public int getGoldWhenDead() {
    return this.goldWhenDead;
  }

  /**
   * Récupère le rayon de la hit box du monstre
   *
   * @return Le rayon de la hit box du monstre
   */
  public double getHitBoxRadius() {
    return this.hitBoxRadius;
  }

  /**
   * Redimensionne l'apparence du monstre en fonction de la taille de la carte
   *
   * @param nbSquareX nombre de tuiles horizontales
   * @param nbSquareY nombre de tuiles verticales
   */
  public void resizeMonster(int nbSquareX, int nbSquareY) {
    this.scaleHeight = this.scaleHeight / nbSquareY * 11;
    this.scaleWidth = this.scaleWidth / nbSquareX * 11;
    this.setShiftX(this.shiftX * this.scaleWidth);
    this.setShiftY(this.shiftY * this.scaleHeight);
  }

}

