package warcraftTD;

import java.awt.*;

public abstract class Projectile {
    private Vector direction;
    protected Position position;
    protected double speed;
    protected int damage;
    protected String sprite;
    protected boolean rotating;
    protected double hitrange;
    protected double width;
    protected double height;
    protected World world;

    public Projectile(Position initialPosition, Vector direction, World world) {
        this.position = new Position(initialPosition);
        this.direction = direction;
        this.world = world;
    }

    public boolean Update(double delta_time){
        if(this.position.x<0.0 || this.position.x>1.0 || this.position.y<0.0 || this.position.y>1.0) return false;
        this.position.x += direction.getX()*speed*delta_time;
        this.position.y += direction.getY()*speed*delta_time;
        StdDraw.setPenColor(new Color(255,0,0));
        if(this.rotating){
            double angle = 0.0;
            if(this.direction.getX()<0.0) angle = Math.acos(this.direction.getY())/Math.PI*180;
            else angle = 360 - Math.acos(this.direction.getY())/Math.PI*180;
            StdDraw.picture(this.position.x, this.position.y, sprite, this.width , this.height, angle);
        } else {
            StdDraw.picture(this.position.x, this.position.y, sprite, this.width , this.height);
        }

        for (Monster m:this.world.monsters) {
            if(m.p.dist(this.position)<hitrange) return onCollideMonster(m);
        }

        return true;
    }

    public abstract boolean onCollideMonster(Monster m);
}
