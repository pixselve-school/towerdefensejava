package warcraftTD.hud;

import warcraftTD.utils.Position;

abstract public class ClickableElement extends Element{
    private HitBox hitBox;

    public HitBox getHitBox() {
        return this.hitBox;
    }

    public void setHitBox(HitBox hitBox) {
        this.hitBox = hitBox;
    }

    public ClickableElement(Position position, double width, double height, Interface parent) {
        super(position, width, height, parent);
        this.hitBox = new HitBox(position, width, height);
    }

    @Override
    public void setPosition(Position position) {
        super.setPosition(position);
        this.hitBox.refresh(this.getPosition());
    }

    abstract public String onClick(double MouseX, double MouseY);
}
