package warcraftTD;

public class ArrowTower extends Tower {

    public ArrowTower(Position p, double width, double height, World world) {
        super(p, width, height, world);
        this.sprite = "images/tower_arrow.png";
        this.sprite_hover = "images/tower_arrow_hover.png";
        this.sprite_HUD_special = "images/arrow_upgrade.png";
        this.range = 0.3;
        this.attackspeed = 3.3;
        this.damage_u = new StatUpgrade(6, new int[]{0,30,50,80,100,120}, new double[]{2,3,4,5,6,7});
        this.attackspeed_u = new StatUpgrade(6, new int[]{0,30,50,80,100,120}, new double[]{3.3,3.4,3.6,3.7,3.8,4.0});
        this.range_u = new StatUpgrade(6, new int[]{0,30,50,80,100,120}, new double[]{0.3,0.32,0.34,0.36,0.38,0.4});
        this.special_u = new StatUpgrade(6, new int[]{0,50,80,100,120,150}, new double[]{0,1,2,3,4,5});
    }

    @Override
    public void refreshSpecialUpgrade() {

    }

    @Override
    public void shootProjectile(Vector Direction) {
        ArrowProjectile pr = new ArrowProjectile(this.position, Direction, this.world, (int)this.damage_u.getLevel_stat()[this.damage_u.getLevel()-1], (int)this.special_u.getLevel_stat()[this.special_u.getLevel()-1]);
        list_projectile.add(pr);
    }
}
