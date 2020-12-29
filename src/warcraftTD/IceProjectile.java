package warcraftTD;

public class IceProjectile extends Projectile {
    public IceProjectile(Position initialPosition, Vector direction) {
        super(initialPosition, direction);
        this.sprite = "images/ice.png";
        this.rotating = true;
        this.hitrange = 0.05;
        this.size = 0.03;
        this.speed = 0.6;
    }
}
