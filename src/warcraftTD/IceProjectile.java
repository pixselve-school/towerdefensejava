package warcraftTD;

public class IceProjectile extends Projectile {
    public IceProjectile(Position initialPosition, Vector direction) {
        super(initialPosition, direction);
        this.sprite = "images/ice.png";
        this.rotating = false;
        this.hitrange = 0.05;
        this.width = 0.025;
        this.height = 0.025;
        this.speed = 0.6;
    }
}
