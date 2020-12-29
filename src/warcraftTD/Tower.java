package warcraftTD;

abstract public class Tower {
    protected String sprite;
    protected String sprite_hover;
    protected Position position;
    protected double width;
    protected double height;
    protected double animationy;
    protected double animationymax;

    public Tower(Position p, double width, double height){
        this.position = p;
        this.width = width;
        this.height = height;
        this.animationy = 0.2;
        this.animationymax = this.animationy;
    }

    public void Update(double delta_time, boolean hovered){
        if(animationy>0.0){
            StdDraw.picture(this.position.x, this.position.y, "images/black_hover.png", this.width/(1.3+this.animationy*(1/this.animationymax)), this.height/(1.3+this.animationy*(1/this.animationymax)));
            StdDraw.picture(this.position.x, this.position.y+this.animationy, this.sprite, this.width, this.height);
            this.animationy -= 0.8*delta_time;
        } else {
            if(hovered) StdDraw.picture(this.position.x, this.position.y, this.sprite_hover, this.width, this.height);
            else StdDraw.picture(this.position.x, this.position.y, this.sprite, this.width, this.height);
        }
    }
}
