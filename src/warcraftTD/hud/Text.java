package warcraftTD.hud;

import warcraftTD.libs.StdDraw;
import warcraftTD.utils.Position;

import java.awt.*;

public class Text extends Element {
  private String text;
  private final Font font;
  private Color color;

  public Font getFont() {
    return this.font;
  }

  public Color getColor() {
    return this.color;
  }

  public void setColor(Color color) {
    this.color = color;
  }

  public String getText() {
    return this.text;
  }

  public void setText(String text) {
    this.text = text;
  }

  public Text(Position position, double width, double height, Font font, Interface parent, String text) {
    super(position, width, height, parent);
    this.font = font;
    this.text = text;
    this.color = new Color(0, 0, 0);
  }

  public Text(Position position, double width, double height, Interface parent, String text) {
    super(position, width, height, parent);
    this.font = new Font("Arial", Font.BOLD, 50);
    this.text = text;
    this.color = new Color(0, 0, 0);
  }

  @Override
  public void update(double MouseX, double MouseY, double deltaTime) {
    if (this.isVisible()) {
      StdDraw.setPenColor(this.color);
      StdDraw.setFont(this.font);
      StdDraw.text(this.getPosition().getX(), this.getPosition().getY(), this.text);
    }
  }

}
