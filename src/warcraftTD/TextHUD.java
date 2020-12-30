package warcraftTD;

import java.awt.*;

public class TextHUD extends HUD_Element {
  protected String text;
  protected final Font font;

  public String getText() {
    return this.text;
  }

  public void setText(String text) {
    this.text = text;
  }

  public TextHUD(Position position, double width, double height, Font font, Interface parent, String text) {
    super(position, width, height, parent);
    this.font = font;
    this.text = text;
  }

  public TextHUD(Position position, double width, double height, Interface parent, String text) {
    super(position, width, height, parent);
    this.font = new Font("Arial", Font.BOLD, 50);
    this.text = text;
  }

  @Override
  public void Update(double MouseX, double MouseY, double delta_time) {
    if(visible){
      StdDraw.setPenColor(StdDraw.BLACK);
      StdDraw.setFont(this.font);
      StdDraw.text(this.position.x, this.position.y, this.text);
    }
  }

  @Override
  public String onClick(double MouseX, double MouseY) {
    return "";
  }


}
