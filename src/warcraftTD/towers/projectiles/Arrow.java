package warcraftTD.towers.projectiles;

import warcraftTD.World;
import warcraftTD.monsters.Monster;
import warcraftTD.utils.Position;
import warcraftTD.utils.Vector;

import java.awt.*;
import java.util.ArrayList;

public class Arrow extends Projectile {
  int percent_count;
  boolean touched;
  ArrayList<Monster> list_collid_monsters;

  public Arrow(Position initialPosition, Vector direction, World world, int damage, int percent_count) {
    super(initialPosition, direction, world, damage);
    this.sprite = "images/arrow.png";
    this.rotating = true;
    this.hitrange = 0.02;
    this.width = 0.02;
    this.height = 0.04;
    this.speed = 0.8;
    this.percent_count = percent_count;
    this.touched = false;
    this.list_collid_monsters = new ArrayList<>();
    this.colordamage = new Color(99, 99, 99);
  }

  @Override
  public boolean onCollideMonster(Monster m) {
    if (!this.list_collid_monsters.contains(m)) {
      m.takeDamage((this.touched ? this.damage / 2 : this.damage), this.world, this.colordamage);
      if (this.percent_count == 0) return false;
      this.percent_count--;
      this.list_collid_monsters.add(m);
      this.touched = true;
    }
    return true;
  }
}
