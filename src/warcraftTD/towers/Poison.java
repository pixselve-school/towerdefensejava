package warcraftTD.towers;

import warcraftTD.WorldGame;
import warcraftTD.utils.Position;
import warcraftTD.utils.Vector;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;

/**
 * Une tour tirant du poison
 * Capacité spéciale : Empoisennement : l'ennemi touché par le projectile est empoisonné.
 * Les degats du poison sont augmentés avec le niveau d'amélioration de la capacité.
 */
public class Poison extends Tower {

    /**
   * Initialise la tour de poison
   * @param width la largeur
   * @param height la hauteur
   * @param world le monde de jeu
   * @throws UnsupportedAudioFileException
   * @throws IOException
   * @throws LineUnavailableException
   */
  public Poison(double width, double height, WorldGame world) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
    super(width, height, world, "music/poison.wav");


    this.setSprite("images/poison_tower.png");
    this.setSpriteHover("images/poison_tower_hover.png");
    this.setSpriteHUDSpecial("images/poison_upgrade.png");
    this.setRange(0.15);
    this.setAttackspeed(2.5);
    this.setDamage_u(new StatUpgrade(6, new int[]{0, 30, 50, 80, 100, 120}, new double[]{6, 7, 8, 9, 10, 12}));
    this.setAttackspeed_u(new StatUpgrade(6, new int[]{0, 30, 50, 80, 100, 120}, new double[]{2.5, 2.6, 2.7, 2.8, 3.0, 3.2}));
    this.setRange_u(new StatUpgrade(6, new int[]{0, 30, 50, 80, 100, 120}, new double[]{0.15, 0.17, 0.19, 0.21, 0.23, 0.25}));
    this.setSpecial_u(new StatUpgrade(6, new int[]{0, 50, 80, 100, 120, 150}, new double[]{2, 3, 4, 5, 6, 8}));
  }

  /**
   * Spawn une bombe dans la Direction donné
   * @param direction la direction
   */
  @Override
  public void shootProjectile(Vector direction) {
    warcraftTD.towers.projectiles.Poison pr = new warcraftTD.towers.projectiles.Poison(this.getParentTile().getPosition(), direction, this.getWorld(), (int) this.getDamage_u().getLevel_stat()[this.getDamage_u().getLevel() - 1], (int) this.getSpecial_u().getLevel_stat()[this.getSpecial_u().getLevel() - 1]);
    this.getList_projectile().add(pr);
    try {
      this.getShootingSound().play(0.10);
    } catch (IOException | LineUnavailableException | UnsupportedAudioFileException e) {
      e.printStackTrace();
    }
  }
}
