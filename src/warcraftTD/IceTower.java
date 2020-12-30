package warcraftTD;

public class IceTower extends Tower {
    public IceTower(Position p, double width, double height, World world) {
        super(p, width, height, world);
        this.sprite = "images/ice_tower.png";
        this.sprite_hover = "images/ice_tower_hover.png";
        this.sprite_HUD_special = "images/ice_upgrade.png";
        this.range = 0.15;
        this.attackspeed = 2.5;
        this.damage_u = new StatUpgrade(6, new int[]{0,30,50,80,100,120}, new double[]{5,6,7,8,9,10});
        this.attackspeed_u = new StatUpgrade(6, new int[]{0,30,50,80,100,120}, new double[]{2.5,2.6,2.7,2.8,3.0,3.2});
        this.range_u = new StatUpgrade(6, new int[]{0,30,50,80,100,120}, new double[]{0.15,0.17,0.19,0.21,0.23,0.25});
        this.special_u = new StatUpgrade(6, new int[]{0,50,80,100,120,150}, new double[]{40,45,50,55,60,65});
    }

    @Override
    public void refreshSpecialUpgrade() {

    }

    @Override
    public void shootProjectile(Vector Direction) {
        IceProjectile pr = new IceProjectile(this.position, Direction, this.world, (int)this.damage_u.getLevel_stat()[this.damage_u.getLevel()-1], (int)this.special_u.getLevel_stat()[this.special_u.getLevel()-1]);
        list_projectile.add(pr);
    }
}
