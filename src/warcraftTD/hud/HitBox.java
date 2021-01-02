package warcraftTD.hud;

import warcraftTD.utils.Position;

public class HitBox {
    private double width;
    private double height;
    private double minX;
    private double minY;
    private double maxX;
    private double maxY;

    public HitBox(Position pos, double width, double height){
        this.width = width;
        this.height = height;
        refresh(pos);
    }

    public void refresh(Position pos){
        this.minX = pos.getX() - (this.width / 2);
        this.maxX = pos.getX() + (this.width / 2);
        this.minY = pos.getY() - (this.height / 2);
        this.maxY = pos.getY() + (this.height / 2);
    }

    public Boolean isHit(double mouseX, double mouseY){
        return mouseX > this.minX && mouseX < this.maxX && mouseY > this.minY && mouseY < this.maxY;
    }
}
