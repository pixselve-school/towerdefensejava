package warcraftTD;

public class ButtonHUD extends HUD_Element {
  protected double minX;
  protected double minY;
  protected double maxX;
  protected double maxY;
  protected final String sprite;
  protected final String sprite_hover;
  private final String action;
  protected final boolean canClick;


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
        if (!this.enabled) StdDraw.picture(this.position.x, this.position.y, "images/disable_buttonshop.png", this.width, this.height);
      }
    }
  }

  @Override
  public String onClick(double MouseX, double MouseY) {
    if (this.visible && this.enabled && this.canClick && MouseX > this.minX && MouseX < this.maxX && MouseY > this.minY && MouseY < this.maxY) {
      return this.action;
    } else {
      return "";
    }
  }
}
