package warcraftTD.hud;


import warcraftTD.utils.Position;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;

public class TowerBuyButton extends Button {
  private Class towerClass;

  public TowerBuyButton(Position pos, double width, double height, String sprite, String sprite_hover, String action, InterfaceGame parent, Class towerClass) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
    super(pos, width, height, sprite, sprite_hover, action, parent);
    this.towerClass = towerClass;
  }

  @Override
  public void update(double MouseX, double MouseY, double delta_time) {
    if (this.isVisible()) {
      this.setEnabled(((InterfaceGame)this.getParent()).getWorld().getPlayer_wallet().getMoney() >= ((InterfaceGame)this.getParent()).getWorld().getPrice_tower().get(this.towerClass));

      super.update(MouseX, MouseY, delta_time);
    }
  }

  @Override
  public String onClick(double MouseX, double MouseY) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
    if (this.isVisible() && this.isEnabled() && this.isClickable() && this.getHitBox().isHit(MouseX, MouseY)) {
      ((InterfaceGame)this.getParent()).startBuilding(this.towerClass);
      this.getClickSound().play(0.6);
      return "cancel";
    }
    return "";
  }
}
