package warcraftTD;

public class ButtonHUD extends HUD_Element {
  private double minX;
  private double minY;
  private double maxX;
  private double maxY;
  private final String sprite;
  private final String sprite_hover;
  private final String action;
  private final boolean canClick;


  public ButtonHUD(Position pos, double width, double height, String sprite, String sprite_hover, String action, Interface Parent) {
    super(pos, width, height, Parent);

    this.minX = this.position.x - (width / 2);
    this.maxX = this.position.x + (width / 2);
    this.minY = this.position.y - (height / 2);
    this.maxY = this.position.y + (height / 2);

    this.action = action;

    this.sprite = sprite;
    this.sprite_hover = sprite_hover;

    this.canClick = true;
  }


  @Override
  public void setPosition(Position position) {
    super.setPosition(position);
    this.minX = position.x - (this.width / 2);
    this.maxX = position.x + (this.width / 2);
    this.minY = position.y - (this.height / 2);
    this.maxY = position.y + (this.height / 2);
  }

  @Override
  public void Update(double MouseX, double MouseY, double delta_time) {
    if (this.visible) {
      if (MouseX > this.minX && MouseX < this.maxX && MouseY > this.minY && MouseY < this.maxY && this.enabled) {
        StdDraw.picture(this.position.x, this.position.y, this.sprite_hover, this.width, this.height);
      } else {
        StdDraw.picture(this.position.x, this.position.y, this.sprite, this.width, this.height);
        if (!this.enabled) StdDraw.picture(this.position.x, this.position.y, "images/black_hover.png", this.width, this.height);
      }
    }
  }

  @Override
  public void onClick(double MouseX, double MouseY) {
    if (this.visible && this.canClick && MouseX > this.minX && MouseX < this.maxX && MouseY > this.minY && MouseY < this.maxY) {
      this.parent.makeAction(this.action, this);
    }
  }
}
