package warcraftTD;

public class BombTower extends Tower{
    public BombTower(Position p, double width, double height) {
        super(p, width, height);
        this.sprite = "images/TowerBomb.png";
        this.sprite_hover = "images/TowerBomb_Hover.png";
    }
}
