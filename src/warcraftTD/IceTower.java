package warcraftTD;

public class IceTower extends Tower {
    public IceTower(Position p, double width, double height) {
        super(p, width, height);
        this.sprite = "images/TowerIce.png";
        this.sprite_hover = "images/TowerIce_Hover.png";
    }
}
