package warcraftTD;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Interface {
  private final World world;
  private final List<HUD_Element> list_HUD_Elements;
  private final ButtonHUD shop_btn;
  private final ProgressBar waveEnnemyBar;
  private final HorizontalGroupBox shopBox;
  private final TextHUD fps_text;
  private final TextHUD building_text;
  private final List<HUD_Element> garbage;
  private final boolean dev_mode = true;

  public World getWorld() {
    return world;
  }

  private final TextHUD walletHUD;
  private final TextHUD life_text;

  private final HorizontalGroupBox upgradeBox;

  private boolean building;
  private Tower upgradingTower;

  protected HUD_Element current_Disabled;

  private ImageHUD upgradeDamageIm;
  private ImageHUD upgradeRangeIm;
  private ImageHUD upgradeAttackSpeedIm;
  private ImageHUD upgradeSpecialIm;
  private ImageHUD upgradeSpecialIm_icon;
  private TextHUD upgradeDamagePrice;
  private TextHUD upgradeRangePrice;
  private TextHUD upgradeAttackSpeedPrice;
  private TextHUD upgradeSpecialPrice;

  public Interface(World parent) {
    this.world = parent;
    this.list_HUD_Elements = new ArrayList<HUD_Element>();
    this.garbage = new ArrayList<HUD_Element>();
    this.shop_btn = new ButtonHUD(new Position(0.9, 0.1), 0.1, 0.13, "images/shop_button.png", "images/shop_button_hover.png", "Shopping", this);
    this.list_HUD_Elements.add(this.shop_btn);
    this.waveEnnemyBar = new ProgressBar(new Position(0.5, 0.94), 0.3, 0.1, "images/waveprogressbar.png", "images/bar_fill.png", this,0.062);
    this.list_HUD_Elements.add(this.waveEnnemyBar);

    this.shopBox = new HorizontalGroupBox(new Position(0.5, 0.1), 0.75, 0.15, this, "images/background_hor_box.png");
    this.list_HUD_Elements.add(this.shopBox);
    ButtonHUD closeshop_btn = new ButtonHUD(new Position(0.97, 0.88), 0.06, 0.06, "images/close_button.png", "images/close_button_hover.png", "ClosingBox", this);
    this.shopBox.addHUDElement(closeshop_btn);
    TowerBuyButtonHUD turret_arrow = new TowerBuyButtonHUD(new Position(0.17, 0.7), 0.12, 0.15, "images/shop_arrowtower.png", "images/shop_arrowtower_hover.png", "turret_arrow", this, ArrowTower.class);
    this.shopBox.addHUDElement(turret_arrow);
    TowerBuyButtonHUD turret_bomb = new TowerBuyButtonHUD(new Position(0.37, 0.7), 0.12, 0.15, "images/shop_bombtower.png", "images/shop_bombtower_hover.png", "turret_bomb", this, BombTower.class);
    this.shopBox.addHUDElement(turret_bomb);
    TowerBuyButtonHUD turret_ice = new TowerBuyButtonHUD(new Position(0.57, 0.7), 0.12, 0.15, "images/shop_icetower.png", "images/shop_icetower_hover.png", "turret_ice", this, IceTower.class);
    this.shopBox.addHUDElement(turret_ice);
    TowerBuyButtonHUD turret_poison = new TowerBuyButtonHUD(new Position(0.77, 0.7), 0.12, 0.15, "images/shop_poisontower.png", "images/shop_poisontower_hover.png", "turret_poison", this, PoisonTower.class);
    this.shopBox.addHUDElement(turret_poison);

    this.upgradeBox = new HorizontalGroupBox(new Position(0.5, 0.13), 0.75, 0.25, this, "images/box_upgrade.png");
    this.list_HUD_Elements.add(this.upgradeBox);
    ButtonHUD closeupgrade_btn = new ButtonHUD(new Position(0.97, 0.85), 0.06, 0.06, "images/close_button.png", "images/close_button_hover.png", "ClosingUpgrade", this);
    this.upgradeBox.addHUDElement(closeupgrade_btn);
    this.upgradeDamageIm = new ImageHUD(new Position(0.2,0.52), 0.1, 0.04, this, "images/level1.png");
    this.upgradeBox.addHUDElement(this.upgradeDamageIm);
    this.upgradeRangeIm = new ImageHUD(new Position(0.2,0.25), 0.1, 0.04, this, "images/level1.png");
    this.upgradeBox.addHUDElement(this.upgradeRangeIm);
    this.upgradeAttackSpeedIm = new ImageHUD(new Position(0.67,0.55), 0.1, 0.04, this, "images/level1.png");
    this.upgradeBox.addHUDElement(this.upgradeAttackSpeedIm);
    this.upgradeSpecialIm = new ImageHUD(new Position(0.67,0.25), 0.1, 0.04, this, "images/level1.png");
    this.upgradeBox.addHUDElement(this.upgradeSpecialIm);
    ButtonHUD upgradebtn = new ButtonHUD(new Position(0.32, 0.52), 0.055, 0.055, "images/button_upgrade.png", "images/button_upgrade_hover.png", "UpgradeDamage", this);
    this.upgradeBox.addHUDElement(upgradebtn);
    upgradebtn = new ButtonHUD(new Position(0.32, 0.25), 0.055, 0.055, "images/button_upgrade.png", "images/button_upgrade_hover.png", "UpgradeRange", this);
    this.upgradeBox.addHUDElement(upgradebtn);
    upgradebtn = new ButtonHUD(new Position(0.79, 0.56), 0.055, 0.055, "images/button_upgrade.png", "images/button_upgrade_hover.png", "UpgradeAttackSpeed", this);
    this.upgradeBox.addHUDElement(upgradebtn);
    upgradebtn = new ButtonHUD(new Position(0.79, 0.27), 0.055, 0.055, "images/button_upgrade.png", "images/button_upgrade_hover.png", "UpgradeSpecial", this);
    this.upgradeBox.addHUDElement(upgradebtn);
    this.upgradeDamagePrice = new TextHUD(new Position(0.415, 0.52), 0.0, 0.0, new Font("Arial", Font.BOLD, 40), this, "100");
    this.upgradeBox.addHUDElement(upgradeDamagePrice);
    this.upgradeRangePrice = new TextHUD(new Position(0.415, 0.25), 0.0, 0.0, new Font("Arial", Font.BOLD, 40), this, "100");
    this.upgradeBox.addHUDElement(upgradeRangePrice);
    this.upgradeBox.addHUDElement(upgradebtn);
    this.upgradeAttackSpeedPrice = new TextHUD(new Position(0.875, 0.52), 0.0, 0.0, new Font("Arial", Font.BOLD, 40), this, "100");
    this.upgradeBox.addHUDElement(upgradeAttackSpeedPrice);
    this.upgradeSpecialPrice = new TextHUD(new Position(0.875, 0.25), 0.0, 0.0, new Font("Arial", Font.BOLD, 40), this, "100");
    this.upgradeBox.addHUDElement(upgradeSpecialPrice);
    this.upgradeSpecialIm_icon = new ImageHUD(new Position(0.55,0.25), 0.05, 0.05, this, "images/poison_upgrade.png");
    this.upgradeBox.addHUDElement(upgradeSpecialIm_icon);

    this.fps_text = new TextHUD(new Position(0.08, 0.85), 0.0, 0.0, new Font("Arial", Font.BOLD, 40), this, "");
    this.list_HUD_Elements.add(this.fps_text);

    ImageHUD imageMoney = new ImageHUD(new Position(0.92,0.95), 0.15, 0.08, this, "images/moneybox.png");
    this.list_HUD_Elements.add(imageMoney);
    this.walletHUD = new TextHUD(new Position(0.94, 0.947), 0.0, 0.0, new Font("Arial", Font.BOLD, 40), this, "0");
    this.list_HUD_Elements.add(this.walletHUD);

    ImageHUD imageLife = new ImageHUD(new Position(0.08,0.95), 0.15, 0.08, this, "images/lifebar.png");
    this.list_HUD_Elements.add(imageLife);
    this.life_text = new TextHUD(new Position(0.1, 0.947), 0.0, 0.0, new Font("Arial", Font.BOLD, 40), this, "0");
    this.list_HUD_Elements.add(this.life_text);

    this.building_text = new TextHUD(new Position(0.5, 0.07), 0.0, 0.0, new Font("Arial", Font.BOLD, 40), this, "Right click to cancel !");
    this.building_text.setVisible(false);
    this.list_HUD_Elements.add(this.building_text);

    this.upgradingTower = null;
    this.building = false;
  }

  public void UpdateInterface(double MouseX, double MouseY, double delta_time) {
    if(dev_mode) this.fps_text.setText("FPS : " + (int) (1 / delta_time));
    this.walletHUD.setText(this.world.player_wallet.getMoney()+"");
    this.life_text.setText(this.world.life+"");

    Iterator<HUD_Element> i = this.list_HUD_Elements.iterator();
    HUD_Element el;
    while (i.hasNext()) {
      el = i.next();
      el.Update(MouseX, MouseY, delta_time);
    }
    i = this.garbage.iterator();
    while (i.hasNext()) {
      el = i.next();
      this.list_HUD_Elements.remove(el);
    }
  }

  public void makeAction(String action, HUD_Element from) {
    switch (action) {
      case "Shopping":
        this.shopBox.ShowBox(0.3, 0.0);
        this.shop_btn.setVisible(false);
        break;
      case "ClosingBox":
        this.shopBox.HideBox();
        this.shop_btn.visible = true;
        this.world.needReleaseMouse = true;
        if (this.current_Disabled != null && this.current_Disabled != from) this.current_Disabled.enabled = true;
        break;
      case "ClosingUpgrade":
        this.upgradingTower = null;
        this.upgradeBox.HideBox();
        this.world.needReleaseMouse = true;
        this.shop_btn.setVisible(true);
        break;
      case "UpgradeDamage":
        if(this.upgradingTower!=null && this.upgradingTower.damage_u.getLevel() != this.upgradingTower.damage_u.getMax_level()){
          if(this.world.player_wallet.pay(this.upgradingTower.damage_u.getLevel_price()[this.upgradingTower.damage_u.getLevel()])){
            this.upgradingTower.upgradeDamage();
            this.world.needReleaseMouse = true;
            this.upgradeDamageIm.setSprite("images/level"+this.upgradingTower.damage_u.getLevel()+".png");
            if(this.upgradingTower.damage_u.getLevel() != this.upgradingTower.damage_u.getMax_level()) this.upgradeDamagePrice.setText(this.upgradingTower.damage_u.getLevel_price()[this.upgradingTower.damage_u.getLevel()]+"");
            else this.upgradeDamagePrice.setText("---");
          }
        }
        break;
      case "UpgradeRange":
        if(this.upgradingTower!=null && this.upgradingTower.range_u.getLevel() != this.upgradingTower.range_u.getMax_level()){
          if(this.world.player_wallet.pay(this.upgradingTower.range_u.getLevel_price()[this.upgradingTower.range_u.getLevel()])){
            this.upgradingTower.upgradeRange();
            this.world.needReleaseMouse = true;
            this.upgradeRangeIm.setSprite("images/level"+this.upgradingTower.range_u.getLevel()+".png");
            if(this.upgradingTower.range_u.getLevel() != this.upgradingTower.range_u.getMax_level()) this.upgradeRangePrice.setText(this.upgradingTower.range_u.getLevel_price()[this.upgradingTower.range_u.getLevel()]+"");
            else this.upgradeRangePrice.setText("---");
          }
        }
        break;
      case "UpgradeAttackSpeed":
        if(this.upgradingTower!=null && this.upgradingTower.attackspeed_u.getLevel() != this.upgradingTower.attackspeed_u.getMax_level()){
          if(this.world.player_wallet.pay(this.upgradingTower.attackspeed_u.getLevel_price()[this.upgradingTower.attackspeed_u.getLevel()])){
            this.upgradingTower.upgradeAttackSpeed();
            this.world.needReleaseMouse = true;
            this.upgradeAttackSpeedIm.setSprite("images/level"+this.upgradingTower.attackspeed_u.getLevel()+".png");
            if(this.upgradingTower.attackspeed_u.getLevel() != this.upgradingTower.attackspeed_u.getMax_level()) this.upgradeAttackSpeedPrice.setText(this.upgradingTower.attackspeed_u.getLevel_price()[this.upgradingTower.attackspeed_u.getLevel()]+"");
            else this.upgradeAttackSpeedPrice.setText("---");
          }
        }
        break;
      case "UpgradeSpecial":
        if(this.upgradingTower!=null && this.upgradingTower.special_u.getLevel() != this.upgradingTower.special_u.getMax_level()){
          if(this.world.player_wallet.pay(this.upgradingTower.special_u.getLevel_price()[this.upgradingTower.special_u.getLevel()])){
            this.upgradingTower.upgradeSpecial();
            this.world.needReleaseMouse = true;
            this.upgradeSpecialIm.setSprite("images/level"+this.upgradingTower.special_u.getLevel()+".png");
            if(this.upgradingTower.special_u.getLevel() != this.upgradingTower.special_u.getMax_level()) this.upgradeSpecialPrice.setText(this.upgradingTower.special_u.getLevel_price()[this.upgradingTower.special_u.getLevel()]+"");
            else this.upgradeSpecialPrice.setText("---");
          }
        }
        break;
    }
  }

  public void startBuilding(Class towerClass){
    building = true;
    shopBox.setVisible(false);
    this.building_text.setVisible(true);
    world.startBuilding(towerClass);
  }

  public void onClick(double MouseX, double MouseY, int mouseButton) {
    if(mouseButton==3 && building){
        this.building = false;
        shopBox.setVisible(true);
        this.building_text.setVisible(false);
        world.stopBuilding();
        return;
    }
    HUD_Element elc = null;
    String action = "";
    for (HUD_Element el: this.list_HUD_Elements) {
      action = el.onClick(MouseX, MouseY);
      if(!action.equals("")) {
        elc = el;
        break;
      }
    }
    if(!action.equals("")) makeAction(action, elc);
  }

  public void setWaveEnemyProgress(double ProgressPercent) {
    this.waveEnnemyBar.setProgressPercent(ProgressPercent);
  }

  public void addNotifText(Position p, Font font, double deltay, String text) {
    NotifTextHUD notif = new NotifTextHUD(p, 0.0, 0.0, font, this, text,deltay);
    list_HUD_Elements.add(notif);
  }

  public void removeNotif(NotifTextHUD text){
    garbage.add(text);
    //list_HUD_Elements.remove(text);
  }

  public void showUpgradeTowerBox(Tower tower){
    if(this.upgradingTower!=tower){
      if(shopBox.isVisible()) shopBox.HideBox();
      this.upgradingTower = tower;
      this.upgradeBox.ShowBox(0.3,0.0);
      this.shop_btn.setVisible(false);

      this.upgradeSpecialIm_icon.setSprite(tower.sprite_HUD_special);

      this.upgradeDamageIm.setSprite("images/level"+this.upgradingTower.damage_u.getLevel()+".png");
      if(tower.damage_u.getLevel() != tower.damage_u.getMax_level()) this.upgradeDamagePrice.setText(tower.damage_u.getLevel_price()[tower.damage_u.getLevel()]+"");
      else this.upgradeDamagePrice.setText("---");

      this.upgradeRangeIm.setSprite("images/level"+this.upgradingTower.range_u.getLevel()+".png");
      if(tower.range_u.getLevel() != tower.range_u.getMax_level()) this.upgradeRangePrice.setText(tower.range_u.getLevel_price()[tower.range_u.getLevel()]+"");
      else this.upgradeRangePrice.setText("---");

      this.upgradeAttackSpeedIm.setSprite("images/level"+this.upgradingTower.attackspeed_u.getLevel()+".png");
      if(tower.attackspeed_u.getLevel() != tower.attackspeed_u.getMax_level()) this.upgradeAttackSpeedPrice.setText(tower.attackspeed_u.getLevel_price()[tower.attackspeed_u.getLevel()]+"");
      else this.upgradeAttackSpeedPrice.setText("---");

      this.upgradeSpecialIm.setSprite("images/level"+this.upgradingTower.special_u.getLevel()+".png");
      if(tower.special_u.getLevel() != tower.special_u.getMax_level()) this.upgradeSpecialPrice.setText(tower.special_u.getLevel_price()[tower.special_u.getLevel()]+"");
      else this.upgradeSpecialPrice.setText("---");
    }
  }

}
