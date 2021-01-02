package warcraftTD.hud;

import warcraftTD.libs.StdDraw;
import warcraftTD.utils.Position;

public class Image extends Element {
  private String sprite;

  public void setSprite(String sprite) {
    this.sprite = sprite;
  }

  public Image(Position position, double width, double height, Interface parent, String sprite) {
    super(position, width, height, parent);
    this.sprite = sprite;
  }

  @Override
  public void Update(double MouseX, double MouseY, double delta_time) {
    StdDraw.picture(this.position.getX(), this.position.getY(), this.sprite, this.width, this.height);
  }

  @Override
  public String onClick(double MouseX, double MouseY) {
    return "";
  }
}
