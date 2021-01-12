package warcraftTD.towers.projectiles;

import warcraftTD.WorldGame;
import warcraftTD.monsters.Monster;
import warcraftTD.utils.Position;
import warcraftTD.utils.Vector;

import java.awt.*;

/**
 * Une boule de glace
 */
public class Ice extends Projectile {
  /** Durée en seconde du ralentissement */
  int duration;
  /** Pourcentage de ralentissement */
  int iceSlow;

  /**
   *
   * @param initialPosition position initiale
   * @param direction direction du projectile
   * @param world référence vers le monde de jeu
   * @param damage les degats
   * @param iceSlow Pourcentage de ralentissement
   */
  public Ice(Position initialPosition, Vector direction, WorldGame world, int damage, int iceSlow) {
    super(initialPosition, direction, world, damage);
    this.sprite = "images/ice.png";
    this.rotating = false;
    this.hitrange = 0.02;
    this.width = 0.025;
    this.height = 0.025;
    this.speed = 0.6;
    this.duration = 5;
    this.iceSlow = iceSlow;
    this.colordamage = new Color(135, 253, 255);
  }

  /**
   * Inflige des degats au monstre et applique la capacité spécial, quand il a collision
   * @param m le monstre
   * @return la collision avec le monstre est validé
   */
  @Override
  public boolean onCollideMonster(Monster m) {
    m.takeDamage(this.damage, this.world, this.colordamage);
    m.applySlowEffect(this.duration, this.iceSlow);
    return false;
  }
}
