package warcraftTD;

public class IceProjectile extends Projectile {
    double duration;
    int iceSlow;

    public IceProjectile(Position initialPosition, Vector direction, World world) {
        super(initialPosition, direction, world);
        this.sprite = "images/ice.png";
        this.rotating = false;
        this.hitrange = 0.02;
        this.width = 0.025;
        this.height = 0.025;
        this.speed = 0.6;
        this.damage = 5;
        this.duration = 5;
        this.iceSlow = 40;
    }

    @Override
    public boolean onCollideMonster(Monster m) {
        m.takeDamage(this.damage, this.world);
        m.applySlowEffect(duration, iceSlow);
        return false;
    }
}
