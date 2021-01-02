package warcraftTD.hud;

import warcraftTD.utils.Position;

abstract public class Element {
  private Position position;
  private boolean visible;
  private double width;
  private double height;
  private Interface parent;
  private boolean enabled;

  public Position getPosition() {
    return this.position;
  }

  public void setPosition(Position position) {
    this.position = position;
  }

  public double getWidth() {
    return this.width;
  }

  public void setWidth(double width) {
    this.width = width;
  }

  public double getHeight() {
    return this.height;
  }

  public void setHeight(double height) {
    this.height = height;
  }

  public Interface getParent() {
    return this.parent;
  }

  public void setParent(Interface parent) {
    this.parent = parent;
  }

  public boolean isEnabled() {
    return this.enabled;
  }

  public void setEnabled(boolean enabled) {
    this.enabled = enabled;
  }

  public boolean isVisible() {
    return this.visible;
  }

  public void setVisible(boolean visible) {
    this.visible = visible;
  }

  public Element(Position position, double width, double height, Interface parent) {
    this.parent = parent;
    this.position = position;
    this.width = width;
    this.height = height;

    this.enabled = true;
    this.visible = true;
  }

  abstract public void update(double MouseX, double MouseY, double delta_time);
}
