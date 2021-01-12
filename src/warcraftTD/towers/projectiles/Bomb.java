package warcraftTD.towers.projectiles;

import warcraftTD.WorldGame;
import warcraftTD.monsters.Monster;
import warcraftTD.utils.Position;
import warcraftTD.utils.Vector;

import java.awt.*;

/**
 * Une bombe
 */
public class Bomb extends Projectile {
  /** Rayon de la zone d'explosion */
  double rangeExplosion;

  /**
   * Initialise la bombe
   * @param initialPosition position initiale
   * @param direction direction du projectile
   * @param world référence vers le monde de jeu
   * @param damage les degats
   * @param rangeExplosion Rayon de la zone d'explosion
   */
  public Bomb(Position initialPosition, Vector direction, WorldGame world, int damage, double rangeExplosion) {
    super(initialPosition, direction, world, damage);
    this.sprite = "images/bomb.png";
    this.rotating = false;
    this.hitrange = 0.02;
    this.width = 0.02;
    this.height = 0.02;
    this.speed = 0.4;
    this.rangeExplosion = rangeExplosion;
    this.colordamage = new Color(0, 0, 0);
  }

  /**
   * Inflige des degats au monstre et applique la capacité spécial, quand il a collision
   * @param m le monstre
   * @return la collision avec le monstre est validé
   */
  @Override
  public boolean onCollideMonster(Monster m) {
    if(!m.isFlying()){
      m.takeDamage(this.damage, this.world, this.colordamage);
      for (Monster mo : this.world.getMonsters()) {
        if (mo != m && mo.getPosition().dist(this.position) < this.rangeExplosion)
          mo.takeDamage(this.damage / 2, this.world, this.colordamage);
      }
      return false;
    }
    return true;
  }

}
