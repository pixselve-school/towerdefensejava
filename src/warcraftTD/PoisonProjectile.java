package warcraftTD;

public class PoisonProjectile extends Projectile {
    public PoisonProjectile(Position initialPosition, Vector direction) {
        super(initialPosition, direction);
        this.sprite = "images/poison.png";
        this.rotating = true;
        this.hitrange = 0.05;
        this.size = 0.03;
        this.speed = 0.7;
    }
}
