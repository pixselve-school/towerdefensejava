package warcraftTD;


public class TowerBuyButtonHUD extends ButtonHUD {
    Class towerClass;

    public TowerBuyButtonHUD(Position pos, double width, double height, String sprite, String sprite_hover, String action, Interface parent, Class towerClass) {
        super(pos, width, height, sprite, sprite_hover, action, parent);
        this.towerClass = towerClass;
    }

    @Override
    public void Update(double MouseX, double MouseY, double delta_time) {
        if (this.visible) {
            if(parent.getWorld().player_wallet.getMoney()>=parent.getWorld().price_tower.get(towerClass)) this.enabled = true;
            else this.enabled = false;

            super.Update(MouseX, MouseY, delta_time);
        }
    }

    @Override
    public String onClick(double MouseX, double MouseY) {
        if (this.visible && this.enabled && this.canClick && MouseX > this.minX && MouseX < this.maxX && MouseY > this.minY && MouseY < this.maxY) {
            parent.startBuilding(towerClass);
            return "cancel";
        }
        return "";
    }
}
