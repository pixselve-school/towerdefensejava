package warcraftTD;

public class ButtonHUD extends HUD_Element{
    private double minX;
    private double minY;
    private double maxX;
    private double maxY;
    private String sprite;
    private String sprite_hover;
    private String action;
    private boolean canClick = true;

    public ButtonHUD (Position pos, double width, double height, String sprite, String sprite_hover, String action, Interface Parent){
        super(pos, width, height, Parent);

        this.minX = position.x - (width/2);
        this.maxX = position.x + (width/2);
        this.minY = position.y - (height/2);
        this.maxY = position.y + (height/2);

        this.action = action;

        this.sprite = sprite;
        this.sprite_hover = sprite_hover;
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
    public void Update(double MouseX, double MouseY) {
        if(visible){
            if(MouseX>minX && MouseX<maxX && MouseY>minY && MouseY<maxY){
                StdDraw.picture(position.x, position.y, sprite_hover, width, height);
            } else {
                StdDraw.picture(position.x, position.y, sprite, width, height);
            }
        }
    }
    @Override
    public void onClick(double MouseX, double MouseY){
        if(visible && canClick && MouseX>minX && MouseX<maxX && MouseY>minY && MouseY<maxY) {
            parent.makeAction(this.action);
        }
    }
}
