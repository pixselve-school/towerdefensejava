package warcraftTD;

import java.awt.*;

public class Projectile {
    private Vector direction;
    private Position position;
    protected double speed;
    protected int damage;
    protected String sprite;
    protected boolean rotating;
    protected double hitrange;
    protected double size;

    public Projectile(Position initialPosition, Vector direction) {
        this.position = new Position(initialPosition);
        this.direction = direction;
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
            StdDraw.picture(this.position.x, this.position.y, sprite, this.size , this.size, angle);
        } else {
            StdDraw.picture(this.position.x, this.position.y, sprite, this.size , this.size);
        }
        return true;
    }


}
