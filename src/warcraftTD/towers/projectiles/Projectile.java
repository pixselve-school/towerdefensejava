package warcraftTD.towers.projectiles;

import warcraftTD.WorldGame;
import warcraftTD.libs.StdDraw;
import warcraftTD.monsters.Monster;
import warcraftTD.utils.Position;
import warcraftTD.utils.Vector;

import java.awt.*;

/**
 * Projectile tiré par les tours, visant les monstres
 */
public abstract class Projectile {
  /** Direction du projectile */
  private final Vector direction;
  /** Position actuelle */
  protected Position position;
  /** Vitesse du projectile */
  protected double speed;
  /** Degat du projectile */
  protected int damage;
  /** Chemin vers l'image d'apparence du projectile */
  protected String sprite;
  /** Spécifie si l'apparence du projectile doit etre tournée dans la direction vers laquelle il se dirige */
  protected boolean rotating;
  /** La distance à laquelle le projectile touche le monstre */
  protected double hitrange;
  /** Largeur du projectile */
  protected double width;
  /** Hauteur du projectile */
  protected double height;
  /** Référence du monde de jeu */
  protected WorldGame world;
  /** Couleur du texte de dégats en cas de collsion avec un monstre */
  protected Color colordamage;

  /**
   * Initialise le projectile
   * @param initialPosition position initiale
   * @param direction direction du projectile
   * @param world référence vers le monde de jeu
   * @param damage les degats
   */
  public Projectile(Position initialPosition, Vector direction, WorldGame world, int damage) {
    this.position = new Position(initialPosition);
    this.direction = direction;
    this.world = world;
    this.damage = damage;
  }

  /**
   * Actualise le visuel et la logique de collision du projectile
   * @param delta_time le temps d'un tick en seconde
   * @return le projectile est encore sur le terrain (pas sorti de l'écran)
   */
  public boolean Update(double delta_time) {
    if (this.position.getX() < 0.0 || this.position.getX() > 1.0 || this.position.getY() < 0.0 || this.position.getY() > 1.0)
      return false;
    this.position.setX(this.position.getX() + this.direction.getX() * this.speed * delta_time);
    this.position.setY(this.position.getY() + this.direction.getY() * this.speed * delta_time);
    StdDraw.setPenColor(new Color(255, 0, 0));
    if (this.rotating) {
      double angle = 0.0;
      if (this.direction.getX() < 0.0) angle = Math.acos(this.direction.getY()) / Math.PI * 180;
      else angle = 360 - Math.acos(this.direction.getY()) / Math.PI * 180;
      StdDraw.picture(this.position.getX(), this.position.getY(), this.sprite, this.width, this.height, angle);
    } else {
      StdDraw.picture(this.position.getX(), this.position.getY(), this.sprite, this.width, this.height);
    }

    for (Monster m : this.world.getMonsters()) {
      if (m.getPosition().dist(this.position) < this.hitrange) return this.onCollideMonster(m);
    }

    return true;
  }

  /**
   * Inflige des degats au monstre et applique la capacité spécial, quand il a collision
   * @param m le monstre
   * @return la collision avec le monstre est validé
   */
  public abstract boolean onCollideMonster(Monster m);
}
