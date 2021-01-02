package warcraftTD.hud;


import warcraftTD.utils.Position;

public class TowerBuyButton extends Button {
  private Class towerClass;

  public TowerBuyButton(Position pos, double width, double height, String sprite, String sprite_hover, String action, InterfaceJeu parent, Class towerClass) {
    super(pos, width, height, sprite, sprite_hover, action, parent);
    this.towerClass = towerClass;
  }

  @Override
  public void update(double MouseX, double MouseY, double delta_time) {
    if (this.isVisible()) {
      this.setEnabled(((InterfaceJeu)this.getParent()).getWorld().getPlayer_wallet().getMoney() >= ((InterfaceJeu)this.getParent()).getWorld().getPrice_tower().get(this.towerClass));

      super.update(MouseX, MouseY, delta_time);
    }
  }

  @Override
  public String onClick(double MouseX, double MouseY) {
    if (this.isVisible() && this.isEnabled() && this.isClickable() && this.getHitBox().isHit(MouseX, MouseY)) {
      ((InterfaceJeu)this.getParent()).startBuilding(this.towerClass);
      return "cancel";
    }
    return "";
  }
}
