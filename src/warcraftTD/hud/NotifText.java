package warcraftTD.hud;

import warcraftTD.libs.StdDraw;
import warcraftTD.utils.Position;

import java.awt.*;

public class NotifText extends Text {
  private double deltaY;
  private final double maxDeltaY;
  private boolean upDirection = true;
  private final Position initPos;
  private final double speed;

  public NotifText(Position position, double width, double height, Font font, Interface parent, String text, double deltaY, Color color) {
    super(position, width, height, font, parent, text);
    this.initPos = position;
    this.speed = 0.5;
    if (deltaY > 0) {
      this.deltaY = deltaY;
      this.maxDeltaY = this.deltaY;
    } else {
      this.deltaY = -deltaY;
      this.maxDeltaY = this.deltaY;
      this.upDirection = false;
    }
    this.setColor(color);
  }

  @Override
  public void update(double mouseX, double mouseY, double deltaTime) {
    if (this.isVisible()) {
      if (this.deltaY > 0.0) this.deltaY -= this.speed * deltaTime;
      else this.getParent().removeNotif(this);

      StdDraw.setPenColor(this.getColor());
      StdDraw.setFont(this.getFont());
      StdDraw.text(this.getPosition().getX(), (this.upDirection ? this.initPos.getY() + this.deltaY : this.initPos.getY() + (this.maxDeltaY - this.deltaY)), this.getText());
    }
  }

}
