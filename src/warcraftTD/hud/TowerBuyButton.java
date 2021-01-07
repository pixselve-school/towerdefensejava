package warcraftTD.hud;


import warcraftTD.utils.Position;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;

public class TowerBuyButton extends Button {
  private Class towerClass;
  private int price;

  public TowerBuyButton(Position pos, double width, double height, String sprite, String sprite_hover, String action, InterfaceGame parent, Class towerClass, int price) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
    super(pos, width, height, sprite, sprite_hover, action, parent);
    this.towerClass = towerClass;
    this.price = price;
  }

  @Override
  public void update(double MouseX, double MouseY, double deltaTime) {
    if (this.isVisible()) {
      this.setEnabled(((InterfaceGame)this.getParent()).getWorld().getPlayer_wallet().getMoney() >= this.price);

      super.update(MouseX, MouseY, deltaTime);
    }
  }

  @Override
  public ActionElement onClick(double MouseX, double MouseY) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
    if (this.isVisible() && this.isEnabled() && this.isClickable() && this.getHitBox().isHit(MouseX, MouseY)) {
      ((InterfaceGame)this.getParent()).startBuilding(this.towerClass);
      this.getClickSound().play(0.6);
      return new ActionElement(this, "cancel");
    }
    return null;
  }
}
