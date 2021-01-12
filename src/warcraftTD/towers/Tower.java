package warcraftTD.towers;

import warcraftTD.WorldGame;
import warcraftTD.libs.Align;
import warcraftTD.libs.StdDraw;
import warcraftTD.monsters.Monster;
import warcraftTD.towers.projectiles.Projectile;
import warcraftTD.utils.Position;
import warcraftTD.utils.Sound;
import warcraftTD.utils.Vector;
import warcraftTD.world.Entity;
import warcraftTD.world.EntityBuildable;
import warcraftTD.world.Tile;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

abstract public class Tower extends Entity {

  public boolean isTargetFlyingMonster() {
    return this.targetFlyingMonster;
  }

  public void setTargetFlyingMonster(boolean targetFlyingMonster) {
    this.targetFlyingMonster = targetFlyingMonster;
  }

  public String getSprite() {
    return this.sprite;
  }

  public void setSprite(String sprite) {
    this.sprite = sprite;
  }

  public String getSprite_hover() {
    return this.sprite_hover;
  }

  public void setSprite_hover(String sprite_hover) {
    this.sprite_hover = sprite_hover;
  }

  public String getSprite_HUD_special() {
    return this.sprite_HUD_special;
  }

  public void setSprite_HUD_special(String sprite_HUD_special) {
    this.sprite_HUD_special = sprite_HUD_special;
  }


  public double getWidth() {
    return this.width;
  }

  public void setWidth(double width) {
    this.width = width;
  }

  public double getHeight() {
    return this.height;
  }

  public void setHeight(double height) {
    this.height = height;
  }

  public double getAnimationy() {
    return this.animationy;
  }

  public void setAnimationy(double animationy) {
    this.animationy = animationy;
  }

  public double getAnimationymax() {
    return this.animationymax;
  }

  public void setAnimationymax(double animationymax) {
    this.animationymax = animationymax;
  }

  public double getRange() {
    return this.range;
  }

  public void setRange(double range) {
    this.range = range;
  }

  public double getAttackspeed() {
    return this.attackspeed;
  }

  public void setAttackspeed(double attackspeed) {
    this.attackspeed = attackspeed;
  }

  public boolean isCanAttack() {
    return this.canAttack;
  }

  public void setCanAttack(boolean canAttack) {
    this.canAttack = canAttack;
  }

  public double getDelayAttack() {
    return this.delayAttack;
  }

  public void setDelayAttack(double delayAttack) {
    this.delayAttack = delayAttack;
  }

  public WorldGame getWorld() {
    return this.world;
  }

  public void setWorld(WorldGame world) {
    this.world = world;
  }

  public Monster getTargetMonster() {
    return this.targetMonster;
  }

  public void setTargetMonster(Monster targetMonster) {
    this.targetMonster = targetMonster;
  }

  public ArrayList<Projectile> getList_projectile() {
    return this.list_projectile;
  }

  public void setList_projectile(ArrayList<Projectile> list_projectile) {
    this.list_projectile = list_projectile;
  }

  public StatUpgrade getDamage_u() {
    return this.damage_u;
  }

  public void setDamage_u(StatUpgrade damage_u) {
    this.damage_u = damage_u;
  }

  public StatUpgrade getRange_u() {
    return this.range_u;
  }

  public void setRange_u(StatUpgrade range_u) {
    this.range_u = range_u;
  }

  public StatUpgrade getAttackspeed_u() {
    return this.attackspeed_u;
  }

  public void setAttackspeed_u(StatUpgrade attackspeed_u) {
    this.attackspeed_u = attackspeed_u;
  }

  public StatUpgrade getSpecial_u() {
    return this.special_u;
  }

  public void setSpecial_u(StatUpgrade special_u) {
    this.special_u = special_u;
  }

  private String sprite;
  private String sprite_hover;
  private String sprite_HUD_special;
  private double width;
  private double height;
  private double animationy;
  private double animationymax;
  private double range;
  private double attackspeed; // nombre de tirs par secondes
  private boolean canAttack;
  private double delayAttack;
  private WorldGame world;
  private Monster targetMonster;
  private ArrayList<Projectile> list_projectile;
  private boolean targetFlyingMonster;

  private StatUpgrade damage_u;
  private StatUpgrade range_u;
  private StatUpgrade attackspeed_u;
  private StatUpgrade special_u;

  public Sound getShootingSound() {
    return this.shootingSound;
  }

  private final Sound shootingSound;



  public Tower(double width, double height, WorldGame world, String soundFilePath) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
    super(EntityBuildable.NOTBUILDABLE);
    this.width = width;
    this.height = height;
    this.animationy = 0.2;
    this.animationymax = this.animationy;
    this.canAttack = true;
    this.targetMonster = null;
    this.list_projectile = new ArrayList<Projectile>();
    this.world = world;
    this.shootingSound = new Sound(soundFilePath, false);
    this.targetFlyingMonster = true;

  }




  public void update(double deltaTime, Tile tile) {
    Position position = tile.getPosition();
    if (this.animationy > 0.0) {
      StdDraw.pictureHeight(position.getX(), position.getY()-0.02, "images/black_hover.png", this.width / (2 + this.animationy * (1 / this.animationymax)), Align.BOTTOM, 45);
      StdDraw.pictureHeight(position.getX(), position.getY() + this.animationy, this.sprite, this.height * 1.25, Align.BOTTOM);
      this.animationy -= 0.8 * deltaTime;
    } else {
      if (this.getParentTile().isSelected()) {
        StdDraw.setPenColor(new Color(0, 161, 255, 90));
        StdDraw.filledCircle(this.getParentTile().getPosition().getX(), this.getParentTile().getPosition().getY(), this.range);
        StdDraw.pictureHeight(position.getX(), position.getY(), this.sprite_hover, this.height * 1.25, Align.BOTTOM);
      } else {
        StdDraw.pictureHeight(position.getX(), position.getY(), this.sprite, this.height * 1.25, Align.BOTTOM);
      }


      this.ProjectilesManagement(deltaTime);
      this.AttackManagement(deltaTime);
    }
  }

  public void AttackManagement(double delta_time) {
    if (this.canAttack) {
      if (this.targetMonster != null) {
        if (!this.targetMonster.isDead() && this.targetMonster.getPosition().dist(this.getParentTile().getPosition()) <= this.range) {
          this.shootTargetMonster();
          return;
        }
        this.targetMonster = null;
      }
      for (int i = 0; i < this.world.getMonsters().size(); i++) {
        if (!this.world.getMonsters().get(i).isDead() && this.world.getMonsters().get(i).getPosition().dist(this.getParentTile().getPosition()) <= this.range && (!this.world.getMonsters().get(i).isFlying() || this.targetFlyingMonster)) {
          this.targetMonster = this.world.getMonsters().get(i);
          break;
        }
      }
      if (this.targetMonster != null) this.shootTargetMonster();
    } else {
      this.delayAttack -= delta_time;
      if (this.delayAttack <= 0.0) this.canAttack = true;
    }
  }

  public void shootTargetMonster() {
    Vector dir = new Vector(this.getParentTile().getPosition(), this.targetMonster.getPosition()).normal();
    this.shootProjectile(dir);
    this.canAttack = false;
    this.delayAttack = 1 / this.attackspeed;
  }

  public void ProjectilesManagement(double delta_time) {
    ArrayList<Projectile> removeP = new ArrayList<Projectile>();
    Iterator<Projectile> i = this.list_projectile.iterator();
    Projectile proj;
    while (i.hasNext()) {
      proj = i.next();
      if (!proj.Update(delta_time)) removeP.add(proj);
    }
    for (Projectile p : removeP) {
      this.list_projectile.remove(p);
    }
  }

  public void upgradeDamage() {
    this.damage_u.setLevel(this.damage_u.getLevel() + 1);
  }

  public void upgradeAttackSpeed() {
    this.attackspeed_u.setLevel(this.attackspeed_u.getLevel() + 1);
    this.attackspeed = this.attackspeed_u.getLevel_stat()[this.attackspeed_u.getLevel() - 1];
  }

  public void upgradeRange() {
    this.range_u.setLevel(this.range_u.getLevel() + 1);
    this.range = this.range_u.getLevel_stat()[this.range_u.getLevel() - 1];
  }

  public void upgradeSpecial() {
    this.special_u.setLevel(this.special_u.getLevel() + 1);
    this.refreshSpecialUpgrade();
  }

  public abstract void refreshSpecialUpgrade();

  public abstract void shootProjectile(Vector Direction);
}
