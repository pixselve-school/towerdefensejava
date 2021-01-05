package warcraftTD.towers.projectiles;

import warcraftTD.WorldGame;
import warcraftTD.monsters.Monster;
import warcraftTD.utils.Position;
import warcraftTD.utils.Vector;

import java.awt.*;

public class Poison extends Projectile {
  int duration;
  int poisondamage;

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

  @Override
  public boolean onCollideMonster(Monster m) {
    m.takeDamage(this.damage, this.world, this.colordamage);
    m.applyPoisonEffect(this.duration, this.poisondamage);
    return false;
  }
}
