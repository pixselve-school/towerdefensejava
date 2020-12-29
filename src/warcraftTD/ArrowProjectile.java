package warcraftTD;

public class ArrowProjectile extends Projectile {
    public ArrowProjectile(Position initialPosition, Vector direction) {
        super(initialPosition, direction);
        this.sprite = "images/arrow.png";
        this.rotating = true;
        this.hitrange = 0.05;
        this.size = 0.05;
        this.speed = 0.8;
    }
}
