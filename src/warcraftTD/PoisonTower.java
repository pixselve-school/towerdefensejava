package warcraftTD;

public class PoisonTower extends Tower{
    public PoisonTower(Position p, double width, double height, World world) {
        super(p, width, height, world);
        this.sprite = "images/poison_tower.png";
        this.sprite_hover = "images/poison_tower_hover.png";
        this.sprite_HUD_special = "images/poison_upgrade.png";
        this.range = 0.15;
        this.attackspeed = 2.5;
        this.damage_u = new StatUpgrade(6, new int[]{0,30,50,80,100,120}, new double[]{6,7,8,9,10,12});
        this.attackspeed_u = new StatUpgrade(6, new int[]{0,30,50,80,100,120}, new double[]{2.5,2.6,2.7,2.8,3.0,3.2});
        this.range_u = new StatUpgrade(6, new int[]{0,30,50,80,100,120}, new double[]{0.15,0.17,0.19,0.21,0.23,0.25});
        this.special_u = new StatUpgrade(6, new int[]{0,50,80,100,120,150}, new double[]{2,3,4,5,6,8});
    }

    @Override
    public void refreshSpecialUpgrade() {

    }

    @Override
    public void shootProjectile(Vector Direction) {
        PoisonProjectile pr = new PoisonProjectile(this.position, Direction, this.world, (int)this.damage_u.getLevel_stat()[this.damage_u.getLevel()-1], (int)this.special_u.getLevel_stat()[this.special_u.getLevel()-1]);
        list_projectile.add(pr);
    }
}
