package warcraftTD;

public class BombTower extends Tower{
    public BombTower(Position p, double width, double height, World world) {
        super(p, width, height, world);
        this.sprite = "images/bomb_tower.png";
        this.sprite_hover = "images/bomb_tower_hover.png";
        this.range = 0.2;
        this.attackspeed = 2.5;
    }

    @Override
    public void shootProjectile(Vector Direction) {
        BombProjectile pr = new BombProjectile(this.position, Direction);
        list_projectile.add(pr);
    }
}
