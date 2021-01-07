package warcraftTD.hud;

import warcraftTD.libs.StdDraw;
import warcraftTD.utils.Position;

public class ProgressBar extends Element {
  private double progressPercent;
  private final String sprite;
  private final String spriteFill;
  private final double shiftx;

  public ProgressBar(Position position, double width, double height, String sprite_bar, String spriteFill, Interface parent) {
    super(position, width, height, parent);
    this.sprite = sprite_bar;
    this.spriteFill = spriteFill;
    this.shiftx = 0.0;
  }

  public ProgressBar(Position position, double width, double height, String sprite_bar, String spriteFill, Interface parent, double shiftx) {
    super(position, width, height, parent);
    this.sprite = sprite_bar;
    this.spriteFill = spriteFill;
    this.shiftx = shiftx;
  }

  public double getProgressPercent() {
    return this.progressPercent;
  }

  public void setProgressPercent(double progressPercent) {
    if (progressPercent > 100.0) {
      this.progressPercent = 100.0;
    } else if (progressPercent < 0.0) {
      this.progressPercent = 0.0;
    } else {
      this.progressPercent = progressPercent;
    }
  }

  public void ProgressBar(InterfaceGame parent) {
    this.setParent(parent);
  }

  @Override
  public void update(double mouseX, double mouseY, double deltaTime) {
    StdDraw.picture(this.getPosition().getX(), this.getPosition().getY(), this.sprite, this.getWidth(), this.getHeight());
    StdDraw.picture(this.shiftx - this.shiftx * this.progressPercent / 100 + this.getPosition().getX() - (this.getWidth() / 2) + (this.progressPercent / 100) * (this.getWidth() / 2), this.getPosition().getY(), this.spriteFill, (this.progressPercent / 100) * this.getWidth(), this.getHeight());
  }

}
