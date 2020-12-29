package warcraftTD;

public class PoisonTower extends Tower{
    public PoisonTower(Position p, double width, double height) {
        super(p, width, height);
        this.sprite = "images/TowerPoison.png";
        this.sprite_hover = "images/TowerPoison_Hover.png";
        this.range = 0.25;
    }
}
