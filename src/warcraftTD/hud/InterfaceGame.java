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

public class InterfaceGame extends Interface{
  private final WorldGame world;
  private final Button shopBtn;
  private final ProgressBar waveEnnemyBar;
  private final GroupBox shopBox;
  private final Text fpsText;
  private final Text buildingText;
  private final boolean devMode = false;

  public WorldGame getWorld() {
    return this.world;
  }

  private final Text walletHUD;
  private final Text lifeText;

  private final GroupBox upgradeBox;

  private boolean building;
  private Tower upgradingTower;

  private Image upgradeDamageIm;
  private Image upgradeRangeIm;
  private Image upgradeAttackSpeedIm;
  private Image upgradeSpecialIm;
  private Image upgradeSpecialImIcon;
  private Text upgradeDamagePrice;
  private Text upgradeRangePrice;
  private Text upgradeAttackSpeedPrice;
  private Text upgradeSpecialPrice;

  public InterfaceGame(WorldGame parent, ArrayList<TowerDataStruct> listTowerData) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
    super();
    this.world = parent;
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

  @Override
  public void updateInterface(double mouseX, double mouseY, double deltaTime) {
    if (this.devMode) this.fpsText.setText("FPS : " + (int) (1 / deltaTime));
    this.walletHUD.setText(this.world.getPlayer_wallet().getMoney() + "");
    this.lifeText.setText(this.world.getLife() + "");

    super.updateInterface(mouseX, mouseY, deltaTime);
  }

  @Override
  public Boolean onClick(double mouseX, double mouseY, int mouseButton) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
    if (mouseButton == 3 && this.building) {
      this.stopBuilding();
      return true;
    }

    return super.onClick(mouseX, mouseY, mouseButton);
  }

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

  public void startBuilding(Class towerClass) {
    this.building = true;
    this.shopBox.setVisible(false);
    this.buildingText.setVisible(true);
    this.world.startBuilding(towerClass);
  }

  public void stopBuilding() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
    Sound soundTower = new Sound("music/click.wav", false);
    soundTower.play(0.5);
    this.building = false;
    this.shopBox.setVisible(true);
    this.buildingText.setVisible(false);
    this.world.stopBuilding();
  }

  public void setWaveEnemyProgress(double progressPercent) {
    this.waveEnnemyBar.setProgressPercent(progressPercent);
  }

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

  public Button getShopBtn() {
    return this.shopBtn;
  }

  public ProgressBar getWaveEnnemyBar() {
    return this.waveEnnemyBar;
  }

  public GroupBox getShopBox() {
    return this.shopBox;
  }

  public Text getFpsText() {
    return this.fpsText;
  }

  public Text getBuildingText() {
    return this.buildingText;
  }

  public boolean isDevMode() {
    return this.devMode;
  }

  public Text getWalletHUD() {
    return this.walletHUD;
  }

  public Text getLifeText() {
    return this.lifeText;
  }

  public GroupBox getUpgradeBox() {
    return this.upgradeBox;
  }

  public boolean isBuilding() {
    return this.building;
  }

  public void setBuilding(boolean building) {
    this.building = building;
  }

  public Tower getUpgradingTower() {
    return this.upgradingTower;
  }

  public void setUpgradingTower(Tower upgradingTower) {
    this.upgradingTower = upgradingTower;
  }

  public Image getUpgradeDamageIm() {
    return this.upgradeDamageIm;
  }

  public void setUpgradeDamageIm(Image upgradeDamageIm) {
    this.upgradeDamageIm = upgradeDamageIm;
  }

  public Image getUpgradeRangeIm() {
    return this.upgradeRangeIm;
  }

  public void setUpgradeRangeIm(Image upgradeRangeIm) {
    this.upgradeRangeIm = upgradeRangeIm;
  }

  public Image getUpgradeAttackSpeedIm() {
    return this.upgradeAttackSpeedIm;
  }

  public void setUpgradeAttackSpeedIm(Image upgradeAttackSpeedIm) {
    this.upgradeAttackSpeedIm = upgradeAttackSpeedIm;
  }

  public Image getUpgradeSpecialIm() {
    return this.upgradeSpecialIm;
  }

  public void setUpgradeSpecialIm(Image upgradeSpecialIm) {
    this.upgradeSpecialIm = upgradeSpecialIm;
  }

  public Image getUpgradeSpecialImIcon() {
    return this.upgradeSpecialImIcon;
  }

  public void setUpgradeSpecialImIcon(Image upgradeSpecialImIcon) {
    this.upgradeSpecialImIcon = upgradeSpecialImIcon;
  }

  public Text getUpgradeDamagePrice() {
    return this.upgradeDamagePrice;
  }

  public void setUpgradeDamagePrice(Text upgradeDamagePrice) {
    this.upgradeDamagePrice = upgradeDamagePrice;
  }

  public Text getUpgradeRangePrice() {
    return this.upgradeRangePrice;
  }

  public void setUpgradeRangePrice(Text upgradeRangePrice) {
    this.upgradeRangePrice = upgradeRangePrice;
  }

  public Text getUpgradeAttackSpeedPrice() {
    return this.upgradeAttackSpeedPrice;
  }

  public void setUpgradeAttackSpeedPrice(Text upgradeAttackSpeedPrice) {
    this.upgradeAttackSpeedPrice = upgradeAttackSpeedPrice;
  }

  public Text getUpgradeSpecialPrice() {
    return this.upgradeSpecialPrice;
  }

  public void setUpgradeSpecialPrice(Text upgradeSpecialPrice) {
    this.upgradeSpecialPrice = upgradeSpecialPrice;
  }
}
