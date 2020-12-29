package warcraftTD;

public class ProgressBar extends HUD_Element {
  private double progressPercent;
  private final String sprite;
  private final String sprite_fill;
  private double shiftx;

  public ProgressBar(Position position, double width, double height, String sprite_bar, String sprite_fill, Interface parent) {
    super(position, width, height, parent);
    this.sprite = sprite_bar;
    this.sprite_fill = sprite_fill;
    this.shiftx = 0.0;
  }

  public ProgressBar(Position position, double width, double height, String sprite_bar, String sprite_fill, Interface parent, double shiftx) {
    super(position, width, height, parent);
    this.sprite = sprite_bar;
    this.sprite_fill = sprite_fill;
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

  public void ProgressBar(Interface parent) {
    this.parent = parent;
  }

  @Override
  public void Update(double MouseX, double MouseY, double delta_time) {
    StdDraw.picture(this.position.x, this.position.y, this.sprite, this.width, this.height);
    StdDraw.picture(shiftx-shiftx*this.progressPercent/100+this.position.x - (this.width / 2) + (this.progressPercent / 100) * (this.width / 2), this.position.y, this.sprite_fill, (this.progressPercent / 100) * this.width, this.height);
  }

  @Override
  public void onClick(double MouseX, double MouseY) {

  }


}
