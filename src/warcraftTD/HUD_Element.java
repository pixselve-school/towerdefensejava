package warcraftTD;

abstract public class HUD_Element {
  protected Position position;
  protected boolean visible;
  protected double width;
  protected double height;
  protected Interface parent;
  protected boolean enabled;

  public HUD_Element(Position position, double width, double height, Interface parent) {
    this.parent = parent;
    this.position = position;
    this.width = width;
    this.height = height;

    this.enabled = true;
    this.visible = true;
  }

  public void setPosition(Position position) {
    this.position = position;
  }

  public boolean isVisible() {
    return this.visible;
  }

  public void setVisible(boolean visible) {
    this.visible = visible;
  }

  abstract public void Update(double MouseX, double MouseY, double delta_time);

  abstract public void onClick(double MouseX, double MouseY);
}
