package warcraftTD.hud;

import warcraftTD.libs.StdDraw;
import warcraftTD.utils.Position;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;

public class Button extends ClickableElement {
  private final String sprite;
  private final String spriteHover;
  private final String action;
  private final boolean clickable;

  public String getSprite() {
    return this.sprite;
  }

  public String getSpriteHover() {
    return this.spriteHover;
  }

  public String getAction() {
    return this.action;
  }

  public boolean isClickable() {
    return this.clickable;
  }

  public Button(Position pos, double width, double height, String sprite, String spriteHover, String action, Interface parent) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
    super(pos, width, height, parent);
    this.action = action;
    this.sprite = sprite;
    this.spriteHover = spriteHover;
    this.clickable = true;
  }

  @Override
  public void setPosition(Position position) {
    super.setPosition(position);
  }

  @Override
  public void update(double mouseX, double mouseY, double deltaTime) {
    if (this.isVisible()) {
      if (this.getHitBox().isHit(mouseX, mouseY) && this.isEnabled()) {
        StdDraw.picture(this.getPosition().getX(), this.getPosition().getY(), this.spriteHover, this.getWidth(), this.getHeight());
      } else {
        StdDraw.picture(this.getPosition().getX(), this.getPosition().getY(), this.sprite, this.getWidth(), this.getHeight());
        if (!this.isEnabled())
          StdDraw.picture(this.getPosition().getX(), this.getPosition().getY(), "images/disable_buttonshop.png", this.getWidth(), this.getHeight());
      }
    }
  }

  @Override
  public ActionElement onClick(double mouseX, double mouseY) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
    if(this.isVisible() && this.isEnabled() && this.clickable && this.getHitBox().isHit(mouseX, mouseY)){
      this.getClickSound().play(0.6);
      return new ActionElement(this, this.action);
    } else {
      return null;
    }
  }
}
