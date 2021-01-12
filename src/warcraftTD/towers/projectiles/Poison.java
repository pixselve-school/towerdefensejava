package warcraftTD.towers.projectiles;

import warcraftTD.WorldGame;
import warcraftTD.monsters.Monster;
import warcraftTD.utils.Position;
import warcraftTD.utils.Vector;

import java.awt.*;

/**
 * Une boule de poison
 */
public class Poison extends Projectile {
  /** Durée en secondes de l'empiosennement */
  int duration;
  /** Dégat du poison toute les demis seondes */
  int poisondamage;

  /**
   * Initialise le projectile de poison
   * @param initialPosition position initiale
   * @param direction direction du projectile
   * @param world référence vers le monde de jeu
   * @param damage les degats
   * @param poisondamage Dégat du poison
   */
  public Poison(Position initialPosition, Vector direction, WorldGame world, int damage, int poisondamage) {
    super(initialPosition, direction, world, damage);
    this.sprite = "images/poison.png";
    this.rotating = true;
    this.hitrange = 0.02;
    this.width = 0.025;
    this.height = 0.025;
    this.speed = 0.7;
    this.duration = 3;
    this.poisondamage = poisondamage;
    this.colordamage = new Color(0, 105, 30);
  }

  /**
   * Inflige des degats au monstre et applique la capacité spécial, quand il a collision
   * @param m le monstre
   * @return la collision avec le monstre est validé
   */
  @Override
  public boolean onCollideMonster(Monster m) {
    m.takeDamage(this.damage, this.world, this.colordamage);
    m.applyPoisonEffect(this.duration, this.poisondamage);
    return false;
  }
}
