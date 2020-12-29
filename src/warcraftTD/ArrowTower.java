package warcraftTD;

public class ArrowTower extends Tower {

    public ArrowTower(Position p, double width, double height, World world) {
        super(p, width, height, world);
        this.sprite = "images/tower_arrow.png";
        this.sprite_hover = "images/tower_arrow_hover.png";
        this.range = 0.3;
        this.attackspeed = 3.3;
    }

    @Override
    public void shootProjectile(Vector Direction) {
        ArrowProjectile pr = new ArrowProjectile(this.position, Direction);
        list_projectile.add(pr);
    }
}
