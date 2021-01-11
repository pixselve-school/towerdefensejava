package warcraftTD.towers;

import warcraftTD.WorldGame;
import warcraftTD.utils.Position;
import warcraftTD.utils.Vector;

import javax.sound.sampled.*;
import java.io.IOException;

/**
 * Une tour d'archer.
 * Capacité spéciale : Flèche percante : pour chaque niveau de cpaacité spéciale,
 * les flèches peuvent traverser un ennemis de plus avant de disparaitre
 */
public class Arrow extends Tower {
  /**
   * Initialise la tour d'archer
   * @param p la position
   * @param width la largeur
   * @param height la hauteur
   * @param world le monde de jeu
   * @throws UnsupportedAudioFileException
   * @throws IOException
   * @throws LineUnavailableException
   */
  public Arrow(Position p, double width, double height, WorldGame world) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
    super(p, width, height, world, "music/arrow.wav");

    this.setSprite("images/tower_arrow.png");
    this.setSpriteHover("images/tower_arrow_hover.png");
    this.setSpriteHUDSpecial("images/arrow_upgrade.png");
    this.setRange(0.3);
    this.setAttackspeed(3.3);
    this.setDamage_u(new StatUpgrade(6, new int[]{0, 30, 50, 80, 100, 120}, new double[]{2, 3, 4, 5, 6, 7}));
    this.setAttackspeed_u(new StatUpgrade(6, new int[]{0, 30, 50, 80, 100, 120}, new double[]{3.3, 3.4, 3.6, 3.7, 3.8, 4.0}));
    this.setRange_u(new StatUpgrade(6, new int[]{0, 30, 50, 80, 100, 120}, new double[]{0.3, 0.32, 0.34, 0.36, 0.38, 0.4}));
    this.setSpecial_u(new StatUpgrade(6, new int[]{0, 50, 80, 100, 120, 150}, new double[]{0, 1, 2, 3, 4, 5}));
  }

  /**
   * Spawn une flèche dans la Direction donné
   * @param direction la direction
   */
  @Override
  public void shootProjectile(Vector direction) {
    warcraftTD.towers.projectiles.Arrow pr = new warcraftTD.towers.projectiles.Arrow(this.getPosition(), direction, this.getWorld(), (int) this.getDamage_u().getLevel_stat()[this.getDamage_u().getLevel() - 1], (int) this.getSpecial_u().getLevel_stat()[this.getSpecial_u().getLevel() - 1]);
    this.getList_projectile().add(pr);
    try {
      this.getShootingSound().play(0.15);
    } catch (IOException | LineUnavailableException | UnsupportedAudioFileException e) {
      e.printStackTrace();
    }

  }
}
