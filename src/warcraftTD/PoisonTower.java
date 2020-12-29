package warcraftTD;

public class PoisonTower extends Tower{
    public PoisonTower(Position p, double width, double height, World world) {
        super(p, width, height, world);
        this.sprite = "images/TowerPoison.png";
        this.sprite_hover = "images/TowerPoison_Hover.png";
        this.range = 0.25;
        this.attackspeed = 2.5;
    }

    @Override
    public void shootProjectile(Vector Direction) {
        PoisonProjectile pr = new PoisonProjectile(this.position, Direction);
        list_projectile.add(pr);
    }
}
