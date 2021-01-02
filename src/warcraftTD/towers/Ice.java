package warcraftTD.towers;

import warcraftTD.World;
import warcraftTD.utils.Position;
import warcraftTD.utils.Vector;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;

public class Ice extends Tower {
  public Ice(Position p, double width, double height, World world) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
    super(p, width, height, world, "music/snow.wav");
    this.setSprite("images/ice_tower.png");
    this.setSprite_hover("images/ice_tower_hover.png");
    this.setSprite_HUD_special("images/ice_upgrade.png");
    this.setRange(0.15);
    this.setAttackspeed(2.5);
    this.setDamage_u(new StatUpgrade(6, new int[]{0, 30, 50, 80, 100, 120}, new double[]{5, 6, 7, 8, 9, 10}));
    this.setAttackspeed_u(new StatUpgrade(6, new int[]{0, 30, 50, 80, 100, 120}, new double[]{2.5, 2.6, 2.7, 2.8, 3.0, 3.2}));
    this.setRange_u(new StatUpgrade(6, new int[]{0, 30, 50, 80, 100, 120}, new double[]{0.15, 0.17, 0.19, 0.21, 0.23, 0.25}));
    this.setSpecial_u(new StatUpgrade(6, new int[]{0, 50, 80, 100, 120, 150}, new double[]{40, 45, 50, 55, 60, 65}));
  }

  @Override
  public void refreshSpecialUpgrade() {

  }

  @Override
  public void shootProjectile(Vector Direction) {
    warcraftTD.towers.projectiles.Ice pr = new warcraftTD.towers.projectiles.Ice(this.getPosition(), Direction, this.getWorld(), (int) this.getDamage_u().getLevel_stat()[this.getDamage_u().getLevel() - 1], (int) this.getSpecial_u().getLevel_stat()[this.getSpecial_u().getLevel() - 1]);
    this.getList_projectile().add(pr);
    try {
      this.getShootingSound().play(0.15);
    } catch (IOException | LineUnavailableException | UnsupportedAudioFileException e) {
      e.printStackTrace();
    }
  }
}
