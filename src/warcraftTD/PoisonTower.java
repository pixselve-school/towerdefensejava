package warcraftTD;

public class PoisonTower extends Tower{
    public PoisonTower(Position p, double width, double height, World world) {
        super(p, width, height, world);
        this.sprite = "images/poison_tower.png";
        this.sprite_hover = "images/poison_tower_hover.png";
        this.range = 0.15;
        this.attackspeed = 2.5;
    }

    @Override
    public void shootProjectile(Vector Direction) {
        PoisonProjectile pr = new PoisonProjectile(this.position, Direction, this.world);
        list_projectile.add(pr);
    }
}
