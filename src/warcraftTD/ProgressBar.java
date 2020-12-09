package warcraftTD;

public class ProgressBar extends HUD_Element {
    private double progressPercent;
    private String sprite;
    private String sprite_fill;

    public ProgressBar(Position position, double width, double height, String sprite_bar, String sprite_fill, Interface parent) {
        super(position, width, height, parent);
        this.sprite = sprite_bar;
        this.sprite_fill = sprite_fill;
    }

    public double getProgressPercent() {
        return this.progressPercent;
    }

    public void setProgressPercent(double progressPercent) {
        if(progressPercent>100.0){
            this.progressPercent = 100.0;
        } else if(progressPercent<0.0){
            this.progressPercent = 0.0;
        } else {
            this.progressPercent = progressPercent;
        }
    }

    public void ProgressBar(Interface parent){
        this.parent = parent;
    }

    @Override
    public void Update(double MouseX, double MouseY) {
        StdDraw.picture(position.x, position.y, sprite, width, height);
        StdDraw.picture(position.x - (width/2) + (progressPercent/100)*(width/2), position.y, sprite_fill, (progressPercent/100)*width, height);
    }
}
