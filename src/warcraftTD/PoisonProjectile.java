package warcraftTD;

public class PoisonProjectile extends Projectile {
    int duration;
    int poisondamage;

    public PoisonProjectile(Position initialPosition, Vector direction, World world, int damage, int poisondamage) {
        super(initialPosition, direction, world, damage);
        this.sprite = "images/poison.png";
        this.rotating = true;
        this.hitrange = 0.02;
        this.width = 0.025;
        this.height = 0.025;
        this.speed = 0.7;
        this.duration = 3;
        this.poisondamage = poisondamage;
    }

    @Override
    public boolean onCollideMonster(Monster m) {
        m.takeDamage(this.damage, this.world);
        m.applyPoisonEffect(duration, poisondamage);
        return false;
    }
}
