package warcraftTD;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;

abstract public class Tower {
    protected String sprite;
    protected String sprite_hover;
    protected Position position;
    protected double width;
    protected double height;
    protected double animationy;
    protected double animationymax;
    protected double range;
    protected double attackspeed; // nombre de tirs par secondes
    protected boolean canAttack;
    protected double delayAttack;
    private World world;
    Monster targetMonster;
    ArrayList<Projectile> list_projectile;

    public Tower(Position p, double width, double height, World world){
        this.position = p;
        this.width = width;
        this.height = height;
        this.animationy = 0.2;
        this.animationymax = this.animationy;
        this.canAttack = true;
        this.targetMonster = null;
        this.list_projectile = new ArrayList<Projectile>();
        this.world = world;
    }

    public void Update(double delta_time, boolean hovered){
        if(animationy>0.0){
            StdDraw.picture(this.position.x, this.position.y, "images/black_hover.png", this.width/(3+this.animationy*(1/this.animationymax)), this.height/(1.3+this.animationy*(1/this.animationymax)));
            StdDraw.picture(this.position.x, this.position.y+this.animationy, this.sprite, this.width, this.height);
            this.animationy -= 0.8*delta_time;
        } else {
            if(hovered) {
                StdDraw.setPenColor(new Color(0,161,255,90));
                StdDraw.filledCircle(this.position.x, this.position.y,this.range);
                StdDraw.picture(this.position.x, this.position.y, this.sprite_hover, this.width, this.height);
            }
            else StdDraw.picture(this.position.x, this.position.y, this.sprite, this.width, this.height);

            ProjectilesManagement(delta_time);
            AttackManagement(delta_time);
        }
    }

    public void AttackManagement(double delta_time){
        if(canAttack){
            if(targetMonster!=null){
                if(targetMonster.p.dist(this.position)<=this.range){
                    shootTargetMonster();
                    return;
                }
                targetMonster = null;
            }
            for(int i = 0;i<world.monsters.size();i++){
                if(world.monsters.get(i).p.dist(this.position)<=this.range){
                    this.targetMonster = world.monsters.get(i);
                    break;
                }
            }
            if(targetMonster!=null) shootTargetMonster();
        } else {
            this.delayAttack -= delta_time;
            if(this.delayAttack<=0.0) this.canAttack = true;
        }
    }

    public void shootTargetMonster(){
        Vector dir = new Vector(this.position, targetMonster.p).normal();
        shootProjectile(dir);
        this.canAttack = false;
        this.delayAttack = 1/attackspeed;
    }

    public void ProjectilesManagement(double delta_time){
        ArrayList<Projectile> removeP = new ArrayList<Projectile>();
        Iterator<Projectile> i = list_projectile.iterator();
        Projectile proj;
		 while (i.hasNext()) {
             proj = i.next();
             if(!proj.Update(delta_time)) removeP.add(proj);
		 }
        for (Projectile p :removeP) {
            list_projectile.remove(p);
        }
    }

    public abstract void shootProjectile(Vector Direction);
}
