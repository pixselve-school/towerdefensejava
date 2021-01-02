package warcraftTD.towers;

import warcraftTD.World;
import warcraftTD.utils.Position;
import warcraftTD.utils.Vector;

public class Arrow extends Tower {

  public Arrow(Position p, double width, double height, World world) {
    super(p, width, height, world);

    this.setSprite("images/tower_arrow.png");
    this.setSprite_hover("images/tower_arrow_hover.png");
    this.setSprite_HUD_special("images/arrow_upgrade.png");
    this.setRange(0.3);
    this.setAttackspeed(3.3);
    this.setDamage_u(new StatUpgrade(6, new int[]{0, 30, 50, 80, 100, 120}, new double[]{2, 3, 4, 5, 6, 7}));
    this.setAttackspeed_u(new StatUpgrade(6, new int[]{0, 30, 50, 80, 100, 120}, new double[]{3.3, 3.4, 3.6, 3.7, 3.8, 4.0}));
    this.setRange_u(new StatUpgrade(6, new int[]{0, 30, 50, 80, 100, 120}, new double[]{0.3, 0.32, 0.34, 0.36, 0.38, 0.4}));
    this.setSpecial_u(new StatUpgrade(6, new int[]{0, 50, 80, 100, 120, 150}, new double[]{0, 1, 2, 3, 4, 5}));
  }

  @Override
  public void refreshSpecialUpgrade() {

  }

  @Override
  public void shootProjectile(Vector Direction) {
    warcraftTD.towers.projectiles.Arrow pr = new warcraftTD.towers.projectiles.Arrow(this.getPosition(), Direction, this.getWorld(), (int) this.getDamage_u().getLevel_stat()[this.getDamage_u().getLevel() - 1], (int) this.getSpecial_u().getLevel_stat()[this.getSpecial_u().getLevel() - 1]);
    this.getList_projectile().add(pr);
  }
}
