package warcraftTD.towers.projectiles;

import warcraftTD.World;
import warcraftTD.libs.StdDraw;
import warcraftTD.monsters.Monster;
import warcraftTD.utils.Position;
import warcraftTD.utils.Vector;

import java.awt.*;

public abstract class Projectile {
  private final Vector direction;
  protected Position position;
  protected double speed;
  protected int damage;
  protected String sprite;
  protected boolean rotating;
  protected double hitrange;
  protected double width;
  protected double height;
  protected World world;
  protected Color colordamage;

  public Projectile(Position initialPosition, Vector direction, World world, int damage) {
    this.position = new Position(initialPosition);
    this.direction = direction;
    this.world = world;
    this.damage = damage;
  }

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

  public abstract boolean onCollideMonster(Monster m);
}
