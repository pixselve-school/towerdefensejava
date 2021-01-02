package warcraftTD.hud;

import warcraftTD.libs.StdDraw;
import warcraftTD.utils.Position;

import java.awt.*;

public class NotifText extends Text {
  private double deltay;
  private final double maxdeltay;
  private boolean UpDirection = true;
  private final Position initpos;
  private final double speed;

  public NotifText(Position position, double width, double height, Font font, Interface parent, String text, double deltay, Color color) {
    super(position, width, height, font, parent, text);
    this.initpos = position;
    this.speed = 0.5;
    if (deltay > 0) {
      this.deltay = deltay;
      this.maxdeltay = this.deltay;
    } else {
      this.deltay = -deltay;
      this.maxdeltay = this.deltay;
      this.UpDirection = false;
    }
    this.setColor(color);
  }

  @Override
  public void update(double MouseX, double MouseY, double delta_time) {
    if (this.isVisible()) {
      if (this.deltay > 0.0) this.deltay -= this.speed * delta_time;
      else this.getParent().removeNotif(this);

      StdDraw.setPenColor(this.getColor());
      StdDraw.setFont(this.getFont());
      StdDraw.text(this.getPosition().getX(), (this.UpDirection ? this.initpos.getY() + this.deltay : this.initpos.getY() + (this.maxdeltay - this.deltay)), this.getText());
    }
  }

}
