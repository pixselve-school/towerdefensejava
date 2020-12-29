package warcraftTD;

public class ArrowTower extends Tower {

    public ArrowTower(Position p, double width, double height) {
        super(p, width, height);
        this.sprite = "images/TowerArrow.png";
        this.sprite_hover = "images/TowerArrow_Hover.png";
        this.range = 0.3;
    }
}
