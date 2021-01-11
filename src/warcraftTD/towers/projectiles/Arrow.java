package warcraftTD.towers.projectiles;

import warcraftTD.WorldGame;
import warcraftTD.monsters.Monster;
import warcraftTD.utils.Position;
import warcraftTD.utils.Vector;

import java.awt.*;
import java.util.ArrayList;

/**
 * Une flèche
 */
public class Arrow extends Projectile {
  /** Nombre de monstres qui peuvent être perçés */
  int piercingCount;
  /** Spécifie si la flèche a déja touché un monstre */
  boolean touched;
  /** Liste contenant les monstres ayant deja reçu des degats venant de la flèche */
  ArrayList<Monster> listCollidMonsters;

  /**
   * Initialise la flèche
   * @param initialPosition position initiale
   * @param direction direction du projectile
   * @param world référence vers le monde de jeu
   * @param damage les degats
   * @param piercingCount Nombre de monstres qui peuvent être perçés
   */
  public Arrow(Position initialPosition, Vector direction, WorldGame world, int damage, int piercingCount) {
    super(initialPosition, direction, world, damage);
    this.sprite = "images/arrow.png";
    this.rotating = true;
    this.hitrange = 0.02;
    this.width = 0.02;
    this.height = 0.04;
    this.speed = 0.8;
    this.piercingCount = piercingCount;
    this.touched = false;
    this.listCollidMonsters = new ArrayList<>();
    this.colordamage = new Color(99, 99, 99);
  }

  /**
   * Inflige des degats au monstre et applique la capacité spécial, quand il a collision
   * @param m le monstre
   * @return la collision avec le monstre est validé
   */
  @Override
  public boolean onCollideMonster(Monster m) {
    if (!this.listCollidMonsters.contains(m)) {
      m.takeDamage((this.touched ? this.damage / 2 : this.damage), this.world, this.colordamage);
      if (this.piercingCount == 0) return false;
      this.piercingCount--;
      this.listCollidMonsters.add(m);
      this.touched = true;
    }
    return true;
  }
}
