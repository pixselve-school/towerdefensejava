package warcraftTD.towers.projectiles;

import warcraftTD.WorldGame;
import warcraftTD.monsters.Monster;
import warcraftTD.utils.Position;
import warcraftTD.utils.Vector;

import java.awt.*;

public class Bomb extends Projectile {
  double rangeExplosion;

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

  @Override
  public boolean onCollideMonster(Monster m) {
    m.takeDamage(this.damage, this.world, this.colordamage);
    for (Monster mo : this.world.getMonsters()) {
      if (mo != m && mo.getPosition().dist(this.position) < this.rangeExplosion)
        mo.takeDamage(this.damage / 2, this.world, this.colordamage);
    }
    return false;
  }
}
