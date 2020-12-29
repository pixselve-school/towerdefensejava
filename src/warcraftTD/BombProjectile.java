package warcraftTD;

public class BombProjectile extends Projectile{

    public BombProjectile(Position initialPosition, Vector direction) {
        super(initialPosition, direction);
        this.sprite = "images/bomb.png";
        this.rotating = false;
        this.hitrange = 0.05;
        this.size = 0.03;
        this.speed = 0.4;
    }
}
