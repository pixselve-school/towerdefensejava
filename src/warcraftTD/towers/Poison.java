package warcraftTD.towers;

import warcraftTD.World;
import warcraftTD.utils.Position;
import warcraftTD.utils.Vector;

public class Poison extends Tower {
  public Poison(Position p, double width, double height, World world) {
    super(p, width, height, world);
    this.setSprite("images/poison_tower.png");
    this.setSprite_hover("images/poison_tower_hover.png");
    this.setSprite_HUD_special("images/poison_upgrade.png");
    this.setRange(0.15);
    this.setAttackspeed(2.5);
    this.setDamage_u(new StatUpgrade(6, new int[]{0, 30, 50, 80, 100, 120}, new double[]{6, 7, 8, 9, 10, 12}));
    this.setAttackspeed_u(new StatUpgrade(6, new int[]{0, 30, 50, 80, 100, 120}, new double[]{2.5, 2.6, 2.7, 2.8, 3.0, 3.2}));
    this.setRange_u(new StatUpgrade(6, new int[]{0, 30, 50, 80, 100, 120}, new double[]{0.15, 0.17, 0.19, 0.21, 0.23, 0.25}));
    this.setSpecial_u(new StatUpgrade(6, new int[]{0, 50, 80, 100, 120, 150}, new double[]{2, 3, 4, 5, 6, 8}));
  }

  @Override
  public void refreshSpecialUpgrade() {

  }

  @Override
  public void shootProjectile(Vector Direction) {
    warcraftTD.towers.projectiles.Poison pr = new warcraftTD.towers.projectiles.Poison(this.getPosition(), Direction, this.getWorld(), (int) this.getDamage_u().getLevel_stat()[this.getDamage_u().getLevel() - 1], (int) this.getSpecial_u().getLevel_stat()[this.getSpecial_u().getLevel() - 1]);
    this.getList_projectile().add(pr);
  }
}
