package warcraftTD;

public class ArrowProjectile extends Projectile {
    public ArrowProjectile(Position initialPosition, Vector direction, World world) {
        super(initialPosition, direction, world);
        this.sprite = "images/arrow.png";
        this.rotating = true;
        this.hitrange = 0.02;
        this.width = 0.02;
        this.height = 0.04;
        this.speed = 0.8;
        this.damage = 2;
    }

    @Override
    public boolean onCollideMonster(Monster m) {
        m.takeDamage(this.damage, this.world);
        return false;
    }
}
