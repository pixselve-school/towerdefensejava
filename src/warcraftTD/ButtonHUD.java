package warcraftTD;

public class ButtonHUD extends HUD_Element{
    private double minX;
    private double minY;
    private double maxX;
    private double maxY;
    private String sprite;
    private String sprite_hover;
    private String action;
    private boolean canClick;


    public ButtonHUD (Position pos, double width, double height, String sprite, String sprite_hover, String action, Interface Parent){
        super(pos, width, height, Parent);

        this.minX = position.x - (width/2);
        this.maxX = position.x + (width/2);
        this.minY = position.y - (height/2);
        this.maxY = position.y + (height/2);

        this.action = action;

        this.sprite = sprite;
        this.sprite_hover = sprite_hover;

        this.canClick = true;
    }


    @Override
    public void setPosition(Position position) {
        super.setPosition(position);
        this.minX = position.x - (width/2);
        this.maxX = position.x + (width/2);
        this.minY = position.y - (height/2);
        this.maxY = position.y + (height/2);
    }

    @Override
    public void Update(double MouseX, double MouseY, double delta_time) {
        if(visible){
            if(MouseX>minX && MouseX<maxX && MouseY>minY && MouseY<maxY && enabled){
                StdDraw.picture(position.x, position.y, sprite_hover, width, height);
            } else {
                StdDraw.picture(position.x, position.y, sprite, width, height);
                if(!enabled) StdDraw.picture(position.x, position.y, "images/black_hover.png" , width, height);
            }
        }
    }
    @Override
    public void onClick(double MouseX, double MouseY){
        if(visible && canClick && MouseX>minX && MouseX<maxX && MouseY>minY && MouseY<maxY) {
            parent.makeAction(this.action, this);
        }
    }
}
