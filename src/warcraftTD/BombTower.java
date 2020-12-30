package warcraftTD;

public class BombTower extends Tower{
    public BombTower(Position p, double width, double height, World world) {
        super(p, width, height, world);
        this.sprite = "images/bomb_tower.png";
        this.sprite_hover = "images/bomb_tower_hover.png";
        this.sprite_HUD_special = "images/bomb_upgrade.png";
        this.range = 0.2;
        this.attackspeed = 2.5;
        this.damage_u = new StatUpgrade(6, new int[]{0,30,50,80,100,120}, new double[]{8,9,10,11,12,14});
        this.attackspeed_u = new StatUpgrade(6, new int[]{0,30,50,80,100,120}, new double[]{2.5,2.7,2.9,3.1,3.2,3.4});
        this.range_u = new StatUpgrade(6, new int[]{0,30,50,80,100,120}, new double[]{0.2,0.22,0.24,0.26,0.28,0.3});
        this.special_u = new StatUpgrade(6, new int[]{0,30,50,80,120,150}, new double[]{0.0,0.03,0.05,0.08,0.01,0.15});
    }

    @Override
    public void refreshSpecialUpgrade() {

    }

    @Override
    public void shootProjectile(Vector Direction) {
        BombProjectile pr = new BombProjectile(this.position, Direction, this.world, (int)this.damage_u.getLevel_stat()[this.damage_u.getLevel()-1], this.special_u.getLevel_stat()[this.special_u.getLevel()-1]);
        list_projectile.add(pr);
    }
}
