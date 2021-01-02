package warcraftTD.towers;

import warcraftTD.World;
import warcraftTD.utils.Position;
import warcraftTD.utils.Vector;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;

public class Bomb extends Tower {
  public Bomb(Position p, double width, double height, World world) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
    super(p, width, height, world, "music/bomb.wav");
    this.setSprite("images/bomb_tower.png");
    this.setSprite_hover("images/bomb_tower_hover.png");
    this.setSprite_HUD_special("images/bomb_upgrade.png");
    this.setRange(0.2);
    this.setAttackspeed(2.5);
    this.setDamage_u(new StatUpgrade(6, new int[]{0, 30, 50, 80, 100, 120}, new double[]{8, 9, 10, 11, 12, 14}));
    this.setAttackspeed_u(new StatUpgrade(6, new int[]{0, 30, 50, 80, 100, 120}, new double[]{2.5, 2.7, 2.9, 3.1, 3.2, 3.4}));
    this.setRange_u(new StatUpgrade(6, new int[]{0, 30, 50, 80, 100, 120}, new double[]{0.2, 0.22, 0.24, 0.26, 0.28, 0.3}));
    this.setSpecial_u(new StatUpgrade(6, new int[]{0, 30, 50, 80, 120, 150}, new double[]{0.0, 0.03, 0.05, 0.08, 0.01, 0.15}));
  }

  @Override
  public void refreshSpecialUpgrade() {

  }

  @Override
  public void shootProjectile(Vector Direction) {
    warcraftTD.towers.projectiles.Bomb pr = new warcraftTD.towers.projectiles.Bomb(this.getPosition(), Direction, this.getWorld(), (int) this.getDamage_u().getLevel_stat()[this.getDamage_u().getLevel() - 1], this.getSpecial_u().getLevel_stat()[this.getSpecial_u().getLevel() - 1]);
    this.getList_projectile().add(pr);
    try {
      this.getShootingSound().play(0.15);
    } catch (IOException | LineUnavailableException | UnsupportedAudioFileException e) {
      e.printStackTrace();
    }
  }
}
