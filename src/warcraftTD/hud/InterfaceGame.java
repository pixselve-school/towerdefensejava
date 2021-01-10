package warcraftTD.hud;

import warcraftTD.WorldGame;
import warcraftTD.towers.*;
import warcraftTD.utils.Position;
import warcraftTD.utils.Sound;
import warcraftTD.utils.TowerDataStruct;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

/**
 * L'interface affichée pendant une partie de jeu
 */
public class InterfaceGame extends Interface{
  /** Référence vers le monde de jeu */
  private final WorldGame world;
  /** Spécifie si le joueur est en train de construire des tours ou non */
  private boolean building;
  /** Spécifie si on est en mode developpement */
  private final boolean devMode = true;
  /** Tour actuellement séléctionné à améliorer */
  private Tower upgradingTower;

  /**
   *  ##############   Elements de l'affichage de Base
   */

  /** Bouton pour afficher le magasin de tours */
  private final Button shopBtn;
  /** Barre de progression de la vague actuelle */
  private final ProgressBar waveEnnemyBar;
  /** Texte affichant les FPS (visible uniquement avec devMode=true) */
  private final Text fpsText;
  /** Texte affichant le montant d'argent du joueur */
  private final Text walletHUD;
  /** Texte affichant le nombre de vies du joueurs */
  private final Text lifeText;

  /**
   *  ##############   Elements de l'affichage du magasin
   */

  /** GroupBox stockant les boutons d'achats de tours */
  private final GroupBox shopBox;

  /**
   *  ##############   Element de l'affichage quand on construit
   */

  /** Texte indiquant comment quitter le mode construction */
  private final Text buildingText;

  /**
   *  ##############   Element de l'affichage quand on améliore une tour
   */

  /** GroupBox qui stocke tous les boutons et images du pannel d'amélioration */
  private final GroupBox upgradeBox;
  /** Image Affichant le niveau d'amélioration des dégats */
  private Image upgradeDamageIm;
  /** Image Affichant le niveau d'amélioration de la portée */
  private Image upgradeRangeIm;
  /** Image Affichant le niveau d'amélioration de la vitesse d'attaque */
  private Image upgradeAttackSpeedIm;
  /** Image Affichant le niveau d'amélioration de la capacité spéciale */
  private Image upgradeSpecialIm;
  /** Image icone de l'amélioration de la capacité spéciale */
  private Image upgradeSpecialImIcon;
  /** Texte affichant le prix de la prochaine amélioration des dégats */
  private Text upgradeDamagePrice;
  /** Texte affichant le prix de la prochaine amélioration de la portée */
  private Text upgradeRangePrice;
  /** Texte affichant le prix de la prochaine amélioration de la vitesse d'attaque */
  private Text upgradeAttackSpeedPrice;
  /** Texte affichant le prix de la prochaine amélioration de la capacité spéciale */
  private Text upgradeSpecialPrice;

  /**
   * Initialise une interface de jeu
   * @param world le monde de jeu
   * @param listTowerData une liste des data de toutes les tours
   * @throws UnsupportedAudioFileException
   * @throws IOException
   * @throws LineUnavailableException
   */
  public InterfaceGame(WorldGame world, ArrayList<TowerDataStruct> listTowerData) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
    super();
    this.world = world;
    this.shopBtn = new Button(new Position(0.9, 0.1), 0.1, 0.13, "images/shop_button.png", "images/shop_button_hover.png", "Shopping", this);
    this.getListElements().add(this.shopBtn);
    this.waveEnnemyBar = new ProgressBar(new Position(0.5, 0.94), 0.3, 0.1, "images/waveprogressbar.png", "images/bar_fill.png", this, 0.062);
    this.getListElements().add(this.waveEnnemyBar);

    this.shopBox = new GroupBox(new Position(0.5, 0.1), 0.75, 0.15, this, "images/background_hor_box.png");
    this.getListElements().add(this.shopBox);
    Button closeshop_btn = new Button(new Position(0.97, 0.88), 0.06, 0.06, "images/close_button.png", "images/close_button_hover.png", "ClosingBox", this);
    this.shopBox.addHUDElement(closeshop_btn);

    double posX = 0.17;
    for(TowerDataStruct td : listTowerData){
      TowerBuyButton tower = new TowerBuyButton(new Position(posX, 0.7), 0.12, 0.15, td.buttonSprite, td.buttonSpriteHover, td.buttonAction, this, td.towerClass, td.price);
      this.shopBox.addHUDElement(tower);
      posX+=0.2;
    }

    this.upgradeBox = new GroupBox(new Position(0.5, 0.13), 0.75, 0.25, this, "images/box_upgrade.png");
    this.getListElements().add(this.upgradeBox);
    Button closeupgrade_btn = new Button(new Position(0.97, 0.85), 0.06, 0.06, "images/close_button.png", "images/close_button_hover.png", "ClosingUpgrade", this);
    this.upgradeBox.addHUDElement(closeupgrade_btn);
    this.upgradeDamageIm = new Image(new Position(0.2, 0.52), 0.1, 0.04, this, "images/level1.png");
    this.upgradeBox.addHUDElement(this.upgradeDamageIm);
    this.upgradeRangeIm = new Image(new Position(0.2, 0.25), 0.1, 0.04, this, "images/level1.png");
    this.upgradeBox.addHUDElement(this.upgradeRangeIm);
    this.upgradeAttackSpeedIm = new Image(new Position(0.67, 0.55), 0.1, 0.04, this, "images/level1.png");
    this.upgradeBox.addHUDElement(this.upgradeAttackSpeedIm);
    this.upgradeSpecialIm = new Image(new Position(0.67, 0.25), 0.1, 0.04, this, "images/level1.png");
    this.upgradeBox.addHUDElement(this.upgradeSpecialIm);
    Button upgradebtn = new Button(new Position(0.32, 0.52), 0.055, 0.055, "images/button_upgrade.png", "images/button_upgrade_hover.png", "UpgradeDamage", this);
    this.upgradeBox.addHUDElement(upgradebtn);
    upgradebtn = new Button(new Position(0.32, 0.25), 0.055, 0.055, "images/button_upgrade.png", "images/button_upgrade_hover.png", "UpgradeRange", this);
    this.upgradeBox.addHUDElement(upgradebtn);
    upgradebtn = new Button(new Position(0.79, 0.56), 0.055, 0.055, "images/button_upgrade.png", "images/button_upgrade_hover.png", "UpgradeAttackSpeed", this);
    this.upgradeBox.addHUDElement(upgradebtn);
    upgradebtn = new Button(new Position(0.79, 0.27), 0.055, 0.055, "images/button_upgrade.png", "images/button_upgrade_hover.png", "UpgradeSpecial", this);
    this.upgradeBox.addHUDElement(upgradebtn);
    this.upgradeDamagePrice = new Text(new Position(0.415, 0.52), 0.0, 0.0, new Font("Arial", Font.BOLD, 40), this, "100");
    this.upgradeBox.addHUDElement(this.upgradeDamagePrice);
    this.upgradeRangePrice = new Text(new Position(0.415, 0.25), 0.0, 0.0, new Font("Arial", Font.BOLD, 40), this, "100");
    this.upgradeBox.addHUDElement(this.upgradeRangePrice);
    this.upgradeBox.addHUDElement(upgradebtn);
    this.upgradeAttackSpeedPrice = new Text(new Position(0.875, 0.52), 0.0, 0.0, new Font("Arial", Font.BOLD, 40), this, "100");
    this.upgradeBox.addHUDElement(this.upgradeAttackSpeedPrice);
    this.upgradeSpecialPrice = new Text(new Position(0.875, 0.25), 0.0, 0.0, new Font("Arial", Font.BOLD, 40), this, "100");
    this.upgradeBox.addHUDElement(this.upgradeSpecialPrice);
    this.upgradeSpecialImIcon = new Image(new Position(0.55, 0.25), 0.05, 0.05, this, "images/poison_upgrade.png");
    this.upgradeBox.addHUDElement(this.upgradeSpecialImIcon);

    this.fpsText = new Text(new Position(0.08, 0.85), 0.0, 0.0, new Font("Arial", Font.BOLD, 40), this, "");
    this.getListElements().add(this.fpsText);

    Image imageMoney = new Image(new Position(0.92, 0.95), 0.15, 0.08, this, "images/moneybox.png");
    this.getListElements().add(imageMoney);
    this.walletHUD = new Text(new Position(0.94, 0.947), 0.0, 0.0, new Font("Arial", Font.BOLD, 40), this, "0");
    this.getListElements().add(this.walletHUD);
    this.walletHUD.setColor(new Color(255, 246, 51));

    Image imageLife = new Image(new Position(0.08, 0.95), 0.15, 0.08, this, "images/lifebar.png");
    this.getListElements().add(imageLife);
    this.lifeText = new Text(new Position(0.1, 0.947), 0.0, 0.0, new Font("Arial", Font.BOLD, 40), this, "0");
    this.getListElements().add(this.lifeText);
    this.lifeText.setColor(new Color(194, 4, 105));

    this.buildingText = new Text(new Position(0.5, 0.07), 0.0, 0.0, new Font("Arial", Font.BOLD, 40), this, "Right click to cancel !");
    this.buildingText.setVisible(false);
    this.getListElements().add(this.buildingText);

    this.upgradingTower = null;
    this.building = false;
  }

  /**
   * Actualise la logique de l'interface et affiche son apparence
   * @param mouseX la position horizontale de la souris
   * @param mouseY la position verticale de la souris
   * @param deltaTime le temps d'un tick en seconde
   */
  @Override
  public void updateInterface(double mouseX, double mouseY, double deltaTime) {
    if (this.devMode) this.fpsText.setText("FPS : " + (int) (1 / deltaTime));
    this.walletHUD.setText(this.world.getPlayer_wallet().getMoney() + "");
    this.lifeText.setText(this.world.getLife() + "");

    super.updateInterface(mouseX, mouseY, deltaTime);
  }

  /**
   * Méthode appelé par le world quand la souris est préssée
   * @param mouseX la position horizontale de la souris
   * @param mouseY la position verticale de la souris
   * @param mouseButton le bouton de la souris préssée
   * @return le clique sur l'interface à réaliser une action
   * @throws UnsupportedAudioFileException
   * @throws IOException
   * @throws LineUnavailableException
   */
  @Override
  public Boolean onClick(double mouseX, double mouseY, int mouseButton) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
    if (mouseButton == 3 && this.building) {
      this.stopBuilding();
      return true;
    }

    return super.onClick(mouseX, mouseY, mouseButton);
  }

  /**
   * Réalise une action sur l'interface
   * @param action l'action à réaliser
   * @param from l'élément d'où vient l'action à réaliser
   * @throws IOException
   * @throws LineUnavailableException
   * @throws UnsupportedAudioFileException
   */
  @Override
  public void makeAction(String action, Element from) {
    switch (action) {
      case "Shopping":
        this.shopBox.showBox(0.3, 0.0);
        this.world.setNeedReleaseMouse(true);
        this.shopBtn.setVisible(false);
        break;
      case "ClosingBox":
        this.shopBox.HideBox();
        this.shopBtn.setVisible(true);
        this.world.setNeedReleaseMouse(true);
        break;
      case "ClosingUpgrade":
        this.upgradingTower = null;
        this.upgradeBox.HideBox();
        this.world.setNeedReleaseMouse(true);
        this.shopBtn.setVisible(true);
        break;
      case "UpgradeDamage":
        if (this.upgradingTower != null && this.upgradingTower.getDamage_u().getLevel() != this.upgradingTower.getDamage_u().getMax_level()) {
          if (this.world.getPlayer_wallet().pay(this.upgradingTower.getDamage_u().getLevel_price()[this.upgradingTower.getDamage_u().getLevel()])) {
            this.upgradingTower.upgradeDamage();
            this.upgradeDamageIm.setSprite("images/level" + this.upgradingTower.getDamage_u().getLevel() + ".png");
            if (this.upgradingTower.getDamage_u().getLevel() != this.upgradingTower.getDamage_u().getMax_level())
              this.upgradeDamagePrice.setText(this.upgradingTower.getDamage_u().getLevel_price()[this.upgradingTower.getDamage_u().getLevel()] + "");
            else this.upgradeDamagePrice.setText("---");
          }
        }
        this.world.setNeedReleaseMouse(true);
        break;
      case "UpgradeRange":
        if (this.upgradingTower != null && this.upgradingTower.getRange_u().getLevel() != this.upgradingTower.getRange_u().getMax_level()) {
          if (this.world.getPlayer_wallet().pay(this.upgradingTower.getRange_u().getLevel_price()[this.upgradingTower.getRange_u().getLevel()])) {
            this.upgradingTower.upgradeRange();
            this.upgradeRangeIm.setSprite("images/level" + this.upgradingTower.getRange_u().getLevel() + ".png");
            if (this.upgradingTower.getRange_u().getLevel() != this.upgradingTower.getRange_u().getMax_level())
              this.upgradeRangePrice.setText(this.upgradingTower.getRange_u().getLevel_price()[this.upgradingTower.getRange_u().getLevel()] + "");
            else this.upgradeRangePrice.setText("---");
          }
        }
        this.world.setNeedReleaseMouse(true);
        break;
      case "UpgradeAttackSpeed":
        if (this.upgradingTower != null && this.upgradingTower.getAttackspeed_u().getLevel() != this.upgradingTower.getAttackspeed_u().getMax_level()) {
          if (this.world.getPlayer_wallet().pay(this.upgradingTower.getAttackspeed_u().getLevel_price()[this.upgradingTower.getAttackspeed_u().getLevel()])) {
            this.upgradingTower.upgradeAttackSpeed();
            this.upgradeAttackSpeedIm.setSprite("images/level" + this.upgradingTower.getAttackspeed_u().getLevel() + ".png");
            if (this.upgradingTower.getAttackspeed_u().getLevel() != this.upgradingTower.getAttackspeed_u().getMax_level())
              this.upgradeAttackSpeedPrice.setText(this.upgradingTower.getAttackspeed_u().getLevel_price()[this.upgradingTower.getAttackspeed_u().getLevel()] + "");
            else this.upgradeAttackSpeedPrice.setText("---");
          }
        }
        this.world.setNeedReleaseMouse(true);
        break;
      case "UpgradeSpecial":
        if (this.upgradingTower != null && this.upgradingTower.getSpecial_u().getLevel() != this.upgradingTower.getSpecial_u().getMax_level()) {
          if (this.world.getPlayer_wallet().pay(this.upgradingTower.getSpecial_u().getLevel_price()[this.upgradingTower.getSpecial_u().getLevel()])) {
            this.upgradingTower.upgradeSpecial();
            this.upgradeSpecialIm.setSprite("images/level" + this.upgradingTower.getSpecial_u().getLevel() + ".png");
            if (this.upgradingTower.getSpecial_u().getLevel() != this.upgradingTower.getSpecial_u().getMax_level())
              this.upgradeSpecialPrice.setText(this.upgradingTower.getSpecial_u().getLevel_price()[this.upgradingTower.getSpecial_u().getLevel()] + "");
            else this.upgradeSpecialPrice.setText("---");
          }
        }
        this.world.setNeedReleaseMouse(true);
        break;
    }
  }

  /**
   * Modifie l'interface pour lancer le mode construction
   * @param towerClass la class de tour à construire
   */
  public void startBuilding(Class towerClass) {
    this.building = true;
    this.shopBox.setVisible(false);
    this.buildingText.setVisible(true);
    this.world.startBuilding(towerClass);
  }

  /**
   * Quitte le mode construction, l'interface se remets en normal
   * @throws UnsupportedAudioFileException
   * @throws IOException
   * @throws LineUnavailableException
   */
  public void stopBuilding() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
    Sound soundTower = new Sound("music/click.wav", false);
    soundTower.play(0.5);
    this.building = false;
    this.shopBox.setVisible(true);
    this.buildingText.setVisible(false);
    this.world.stopBuilding();
  }

  /**
   * Affiche le pannel d'amélioration de tour
   * @param tower la tour à améliorer
   * @throws UnsupportedAudioFileException
   * @throws IOException
   * @throws LineUnavailableException
   */
  public void showUpgradeTowerBox(Tower tower) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
    if (this.upgradingTower != tower) {
      if (this.shopBox.isVisible()) this.shopBox.HideBox();
      Sound soundTower = new Sound("music/click.wav", false);
      soundTower.play(0.5);
      this.upgradingTower = tower;
      this.world.setNeedReleaseMouse(true);
      this.upgradeBox.showBox(0.3, 0.0);
      this.shopBtn.setVisible(false);

      this.upgradeSpecialImIcon.setSprite(tower.getSprite_HUD_special());

      this.upgradeDamageIm.setSprite("images/level" + this.upgradingTower.getDamage_u().getLevel() + ".png");
      if (tower.getDamage_u().getLevel() != tower.getDamage_u().getMax_level())
        this.upgradeDamagePrice.setText(tower.getDamage_u().getLevel_price()[tower.getDamage_u().getLevel()] + "");
      else this.upgradeDamagePrice.setText("---");

      this.upgradeRangeIm.setSprite("images/level" + this.upgradingTower.getRange_u().getLevel() + ".png");
      if (tower.getRange_u().getLevel() != tower.getRange_u().getMax_level())
        this.upgradeRangePrice.setText(tower.getRange_u().getLevel_price()[tower.getRange_u().getLevel()] + "");
      else this.upgradeRangePrice.setText("---");

      this.upgradeAttackSpeedIm.setSprite("images/level" + this.upgradingTower.getAttackspeed_u().getLevel() + ".png");
      if (tower.getAttackspeed_u().getLevel() != tower.getAttackspeed_u().getMax_level())
        this.upgradeAttackSpeedPrice.setText(tower.getAttackspeed_u().getLevel_price()[tower.getAttackspeed_u().getLevel()] + "");
      else this.upgradeAttackSpeedPrice.setText("---");

      this.upgradeSpecialIm.setSprite("images/level" + this.upgradingTower.getSpecial_u().getLevel() + ".png");
      if (tower.getSpecial_u().getLevel() != tower.getSpecial_u().getMax_level())
        this.upgradeSpecialPrice.setText(tower.getSpecial_u().getLevel_price()[tower.getSpecial_u().getLevel()] + "");
      else this.upgradeSpecialPrice.setText("---");
    }
  }

  /**
   * Modifie la progression de la barre de progression des vague
   * @param progressPercent le nouveau pourcentage de remplissage
   */
  public void setWaveEnemyProgress(double progressPercent) {
    this.waveEnnemyBar.setProgressPercent(progressPercent);
  }

  /**
   * Récupère la tour en amélioration actuellement
   * @return la tour en amélioration actuellement
   */
  public Tower getUpgradingTower() {
    return this.upgradingTower;
  }

  /**
   * Récupère le monde de jeu
   * @return le monde de jeu
   */
  public WorldGame getWorld() {
    return this.world;
  }

}
