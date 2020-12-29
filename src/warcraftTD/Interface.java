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

  private final Wallet player_wallet;
  private final TextHUD walletHUD;
  private final TextHUD life_text;

  private boolean building;

  private HUD_Element current_Disabled;

  public Interface(Wallet wallet, World parent) {
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

    this.fps_text = new TextHUD(new Position(0.08, 0.85), 0.0, 0.0, new Font("Arial", Font.BOLD, 40), this, "");
    this.list_HUD_Elements.add(this.fps_text);
    this.player_wallet = wallet;

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
        if (this.current_Disabled != null && this.current_Disabled != from) this.current_Disabled.enabled = true;
        break;
      case "autres":
        // TODO Ajouter d'autres actions
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
    Iterator<HUD_Element> i = this.list_HUD_Elements.iterator();
    HUD_Element el;
    while (i.hasNext()) {
      el = i.next();
      el.onClick(MouseX, MouseY);
    }
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

}
