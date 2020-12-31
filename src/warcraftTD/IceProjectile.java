package warcraftTD;

import java.awt.*;

public class IceProjectile extends Projectile {
    int duration;
    int iceSlow;

    public IceProjectile(Position initialPosition, Vector direction, World world, int damage, int iceSlow) {
        super(initialPosition, direction, world, damage);
        this.sprite = "images/ice.png";
        this.rotating = false;
        this.hitrange = 0.02;
        this.width = 0.025;
        this.height = 0.025;
        this.speed = 0.6;
        this.duration = 5;
        this.iceSlow = iceSlow;
        this.colordamage = new Color(135,253,255);
    }

    @Override
    public boolean onCollideMonster(Monster m) {
        m.takeDamage(this.damage, this.world, this.colordamage);
        m.applySlowEffect(duration, iceSlow);
        return false;
    }
}
