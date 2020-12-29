package warcraftTD;

public class ImageHUD extends HUD_Element {
    private String sprite;

    public ImageHUD(Position position, double width, double height, Interface parent, String sprite) {
        super(position, width, height, parent);
        this.sprite = sprite;
    }

    @Override
    public void Update(double MouseX, double MouseY, double delta_time) {
        StdDraw.picture(this.position.x, this.position.y, sprite, this.width, this.height);
    }

    @Override
    public void onClick(double MouseX, double MouseY) {

    }
}
