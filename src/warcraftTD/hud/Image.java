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
  public void update(double MouseX, double MouseY, double delta_time) {
    if(this.isVisible()) StdDraw.picture(this.getPosition().getX(), this.getPosition().getY(), this.sprite, this.getWidth(), this.getHeight());
  }
}
