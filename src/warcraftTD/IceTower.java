package warcraftTD;

public class IceTower extends Tower {
    public IceTower(Position p, double width, double height, World world) {
        super(p, width, height, world);
        this.sprite = "images/TowerIce.png";
        this.sprite_hover = "images/TowerIce_Hover.png";
        this.range = 0.25;
        this.attackspeed = 2.5;
    }

    @Override
    public void shootProjectile(Vector Direction) {
        IceProjectile pr = new IceProjectile(this.position, Direction);
        list_projectile.add(pr);
    }
}
