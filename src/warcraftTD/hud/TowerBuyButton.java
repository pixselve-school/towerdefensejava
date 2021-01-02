package warcraftTD.hud;


import warcraftTD.utils.Position;

public class TowerBuyButton extends Button {
  Class towerClass;

  public TowerBuyButton(Position pos, double width, double height, String sprite, String sprite_hover, String action, Interface parent, Class towerClass) {
    super(pos, width, height, sprite, sprite_hover, action, parent);
    this.towerClass = towerClass;
  }

  @Override
  public void Update(double MouseX, double MouseY, double delta_time) {
    if (this.visible) {
      this.enabled = this.parent.getWorld().getPlayer_wallet().getMoney() >= this.parent.getWorld().getPrice_tower().get(this.towerClass);

      super.Update(MouseX, MouseY, delta_time);
    }
  }

  @Override
  public String onClick(double MouseX, double MouseY) {
    if (this.visible && this.enabled && this.canClick && MouseX > this.minX && MouseX < this.maxX && MouseY > this.minY && MouseY < this.maxY) {
      this.parent.startBuilding(this.towerClass);
      return "cancel";
    }
    return "";
  }
}
