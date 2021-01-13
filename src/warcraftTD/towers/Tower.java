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

/**
 * Une tour placable sur la carte de jeu, et qui attaque les monstres
 */
abstract public class Tower extends Entity {
  /** le chemin vers l'image d'apparence de la tour */
  private String sprite;
  /** le chemin vers l'image d'apparence de la tour quand la souris est dessus */
  private String spriteHover;
  /** le chemin vers l'image de l'icon de la capacité spéciale de la tour */
  private String spriteHUDSpecial;
  /** La position de la tour */
  private Position position;
  /** Largeur de la tour */
  private double width;
  /** Hauteur de la tour */
  private double height;
  /** Décalage de hauteur d'animation actuellement */
  private double animationy;
  /** Décalage de hauteur d'animation maximale */
  private double animationymax;
  /** Distance à laquelle la tour tire sur les ennemis (les détecte) */
  private double range;
  /** vitesse de tir de la tour (nombre de tirs par secondes) */
  private double attackspeed;
  /** spéciife si la tour peut attaquer */
  private boolean canAttack;
  /** Comptabilise le delay avant que la tour ne puisse à nouveau attaquer */
  private double delayAttack;
  /** Référence vers le monde de jeu */
  private WorldGame world;
  /** Le monstre ciblé par la tour actuellement */
  private Monster targetMonster;
  /** Liste des projectiles encore sur le terrain que cette tour a tiré */
  private ArrayList<Projectile> list_projectile;
  /** Spécifie si la tour peut attaquer les monstres volants */
  private boolean targetFlyingMonster;
  /** Stat des Upgrades pour les dégats */
  private StatUpgrade damage_u;
  /** Stat des Upgrades pour la distance d'attaque */
  private StatUpgrade range_u;
  /** Stat des Upgrades pour la vitesse d'attaque */
  private StatUpgrade attackspeed_u;
  /** Stat des Upgrades pour la capacité spéciale */
  private StatUpgrade special_u;
  /** Son produit quand la tour tir */
  private final Sound shootingSound;

  /**
   * Récupère le son produit quand la tour tir
   * @return le son produit quand la tour tir
   */
  public Sound getShootingSound() {
    return this.shootingSound;
  }

  /**
   * Modifie la capacité de la tour à tirer sur les monstres volants
   * @param targetFlyingMonster la capacité de la tour à tirer sur les monstres volants
   */
  public void setTargetFlyingMonster(boolean targetFlyingMonster) {
    this.targetFlyingMonster = targetFlyingMonster;
  }

  /**
   * Récupère le chemin vers l'image d'apparence de la tour
   * @return le chemin vers l'image d'apparence de la tour
   */
  public String getSprite() {
    return this.sprite;
  }

  /**
   * Change le chemin vers l'image d'apparence de la tour
   * @param sprite le chemin vers l'image d'apparence de la tour
   */
  public void setSprite(String sprite) {
    this.sprite = sprite;
  }

  /**
   * Modifie le chemin vers l'image d'apparence de la tour quand la souris est dessus
   * @param spriteHover le chemin vers l'image d'apparence de la tour quand la souris est dessus
   */
  public void setSpriteHover(String spriteHover) {
    this.spriteHover = spriteHover;
  }

  /**
   * Récupère le chemin vers l'image de l'icon de la capacité spéciale de la tour
   * @return le chemin vers l'image de l'icon de la capacité spéciale de la tour
   */
  public String getSpriteHUDSpecial() {
    return this.spriteHUDSpecial;
  }

  /**
   * Modifie le chemin vers l'image de l'icon de la capacité spéciale de la tour
   * @param spriteHUDSpecial le chemin vers l'image de l'icon de la capacité spéciale de la tour
   */
  public void setSpriteHUDSpecial(String spriteHUDSpecial) {
    this.spriteHUDSpecial = spriteHUDSpecial;
  }


  /**
   * Récupère la largeur de la tour
   * @return la largeur de la tour
   */
  public double getWidth() {
    return this.width;
  }

  /**
   * Change la largeur de la tour
   * @param width la largeur de la tour
   */
  public void setWidth(double width) {
    this.width = width;
  }

  /**
   * Récuoère la hauteur de la tour
   * @return la hauteur de la tour
   */
  public double getHeight() {
    return this.height;
  }

  /**
   * Modifie la hauteur de la tour
   * @param height la hauteur de la tour
   */
  public void setHeight(double height) {
    this.height = height;
  }

  /**
   * Modifie la distance à laquelle la tour tire sur les ennemis
   * @param range la distance à laquelle la tour tire sur les ennemis
   */
  public void setRange(double range) {
    this.range = range;
  }

  /**
   * Modifie la vitesse d'attaque de la tour
   * @param attackspeed la vitesse d'attaque de la tour
   */
  public void setAttackspeed(double attackspeed) {
    this.attackspeed = attackspeed;
  }

  /**
   * Récupère le monde de jeu
   * @return le monde de jeu
   */
  public WorldGame getWorld() {
    return this.world;
  }

  /**
   * Modifie la référence vers le monde de jeu
   * @param world la référence vers le monde de jeu
   */
  public void setWorld(WorldGame world) {
    this.world = world;
  }

  /**
   * Récupère la liste des projectiles sur le terrain tiré par la tour
   * @return la liste des projectiles sur le terrain tiré par la tour
   */
  public ArrayList<Projectile> getList_projectile() {
    return this.list_projectile;
  }

  /**
   * Récupère les Stats d'améliorations des dégats
   * @return les Stats d'améliorations des dégats
   */
  public StatUpgrade getDamage_u() {
    return this.damage_u;
  }

  /**
   * Modifie les Stats d'améliorations des dégats
   * @param damage_u les Stats d'améliorations des dégats
   */
  public void setDamage_u(StatUpgrade damage_u) {
    this.damage_u = damage_u;
  }

  /**
   * Récupère les Stats d'améliorations de la distance d'attaque de la tour
   * @return les Stats d'améliorations de la distance d'attaque de la tour
   */
  public StatUpgrade getRange_u() {
    return this.range_u;
  }

  /**
   * Modifie les Stats d'améliorations de la distance d'attaque de la tour
   * @param range_u les Stats d'améliorations de la distance d'attaque de la tour
   */
  public void setRange_u(StatUpgrade range_u) {
    this.range_u = range_u;
  }

  /**
   * Récupère les Stats d'améliorations de la vitesse d'attaque
   * @return les Stats d'améliorations de la vitesse d'attaque
   */
  public StatUpgrade getAttackspeed_u() {
    return this.attackspeed_u;
  }

  /**
   * Modifie les Stats d'améliorations de la vitesse d'attaque
   * @param attackspeed_u les Stats d'améliorations de la vitesse d'attaque
   */
  public void setAttackspeed_u(StatUpgrade attackspeed_u) {
    this.attackspeed_u = attackspeed_u;
  }

  /**
   * Récupère les Stats d'améliorations de la capacité spéciale
   * @return les Stats d'améliorations de la capacité spéciale
   */
  public StatUpgrade getSpecial_u() {
    return this.special_u;
  }

  /**
   * Modifie les Stats d'améliorations de la capacité spéciale
   * @param special_u les Stats d'améliorations de la capacité spéciale
   */
  public void setSpecial_u(StatUpgrade special_u) {
    this.special_u = special_u;
  }
  /**
   * Initialise une tour
   * @param width la largeur
   * @param height la hauteur
   * @param world le monde de jeu
   * @param soundFilePath le chemin vers le son de tir
   * @throws UnsupportedAudioFileException
   * @throws IOException
   * @throws LineUnavailableException
   */
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


  /**
   * Actualise la logique de l'entité et affiche son apparence
   *
   * @param deltaTime le temps d'un tick en seconde
   * @param tile      La Tile attaché à l'entité
   */

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
        StdDraw.pictureHeight(position.getX(), position.getY(), this.spriteHover, this.height * 1.25, Align.BOTTOM);
      } else {
        StdDraw.pictureHeight(position.getX(), position.getY(), this.sprite, this.height * 1.25, Align.BOTTOM);
      }


      this.ProjectilesManagement(deltaTime);
      this.AttackManagement(deltaTime);
    }
  }


  /**
   * Gère la logique de tir de la tour pour chaque tick
   * @param delta_time le temps d'un tick en seconde
   */

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

  /**
   * Calcul la trajectoire du projectile, mets un delay avant la prochaine attaque, tir le projectile
   */
  public void shootTargetMonster() {
    Vector dir = new Vector(this.getParentTile().getPosition(), this.targetMonster.getPosition()).normal();
    this.shootProjectile(dir);
    this.canAttack = false;
    this.delayAttack = 1 / this.attackspeed;
  }

  /**
   * Actualise le positionnement de tous ses projectiles en jeu (les supprime si ils sortent du terrain)
   * @param delta_time le temps d'un tick en seconde
   */
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

  /**
   * Augmente le niveau d'amélioration des dégats
   */
  public void upgradeDamage() {
    this.damage_u.setLevel(this.damage_u.getLevel() + 1);
  }

  /**
   * Augmente le niveau d'amélioration de la vitesse d'attaque
   */
  public void upgradeAttackSpeed() {
    this.attackspeed_u.setLevel(this.attackspeed_u.getLevel() + 1);
    this.attackspeed = this.attackspeed_u.getLevel_stat()[this.attackspeed_u.getLevel() - 1];
  }

  /**
   * Augmente le niveau d'amélioration de la distance d'attaque
   */
  public void upgradeRange() {
    this.range_u.setLevel(this.range_u.getLevel() + 1);
    this.range = this.range_u.getLevel_stat()[this.range_u.getLevel() - 1];
  }

  /**
   * Augmente le niveau d'amélioration de la capacité spéciale
   */
  public void upgradeSpecial() {
    this.special_u.setLevel(this.special_u.getLevel() + 1);
  }

  /**
   * Spawn un projectile dans la Direction donné
   * @param direction la direction
   */
  public abstract void shootProjectile(Vector direction);
}
