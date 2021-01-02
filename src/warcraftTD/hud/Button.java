package warcraftTD.hud;

import warcraftTD.libs.StdDraw;
import warcraftTD.utils.Position;

public class Button extends Element {
  protected double minX;
  protected double minY;
  protected double maxX;
  protected double maxY;
  protected final String sprite;
  protected final String sprite_hover;
  private final String action;
  protected final boolean canClick;


  public Button(Position pos, double width, double height, String sprite, String sprite_hover, String action, Interface Parent) {
    super(pos, width, height, Parent);

    this.minX = this.position.getX() - (width / 2);
    this.maxX = this.position.getX() + (width / 2);
    this.minY = this.position.getY() - (height / 2);
    this.maxY = this.position.getY() + (height / 2);

    this.action = action;

    this.sprite = sprite;
    this.sprite_hover = sprite_hover;

    this.canClick = true;
  }


  @Override
  public void setPosition(Position position) {
    super.setPosition(position);
    this.minX = position.getX() - (this.width / 2);
    this.maxX = position.getX() + (this.width / 2);
    this.minY = position.getY() - (this.height / 2);
    this.maxY = position.getY() + (this.height / 2);
  }

  @Override
  public void Update(double MouseX, double MouseY, double delta_time) {
    if (this.visible) {
      if (MouseX > this.minX && MouseX < this.maxX && MouseY > this.minY && MouseY < this.maxY && this.enabled) {
        StdDraw.picture(this.position.getX(), this.position.getY(), this.sprite_hover, this.width, this.height);
      } else {
        StdDraw.picture(this.position.getX(), this.position.getY(), this.sprite, this.width, this.height);
        if (!this.enabled)
          StdDraw.picture(this.position.getX(), this.position.getY(), "images/disable_buttonshop.png", this.width, this.height);
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
