package warcraftTD.hud;


import warcraftTD.utils.Position;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;

/**
 * Un bouton d'interface spécifique à l'achat d'une tour
 */
public class TowerBuyButton extends Button {
  /** la classe de la tour à acheter */
  private Class towerClass;
  /** le prix de la tour à acheter */
  private int price;

  /**
   * Initialise le bouton
   * @param pos la position du bouton
   * @param width la largueur du bouton
   * @param height la hauteur du bouton
   * @param sprite l'apparence du bouton
   * @param spriteHover l'apparence du bouton quand il est survolé par la souris
   * @param action l'action du bouton quand on clique dessus
   * @param parent l'interface à laquelle le bouton appartient
   * @param towerClass la classe de la tour à acheter
   * @param price le prix de la tour à acheter
   * @throws UnsupportedAudioFileException
   * @throws IOException
   * @throws LineUnavailableException
   */
  public TowerBuyButton(Position pos, double width, double height, String sprite, String spriteHover, String action, InterfaceGame parent, Class towerClass, int price) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
    super(pos, width, height, sprite, spriteHover, action, parent);
    this.towerClass = towerClass;
    this.price = price;
  }

  /**
   * Actualise la logique de l'élément et affiche son apparence
   * @param mouseX la position horizontale de la souris
   * @param mouseY la position verticale de la souris
   * @param deltaTime le temps d'un tick en seconde
   */
  @Override
  public void update(double mouseX, double mouseY, double deltaTime) {
    if (this.isVisible()) {
      this.setEnabled(((InterfaceGame)this.getParent()).getWorld().getPlayerWallet().getMoney() >= this.price);

      super.update(mouseX, mouseY, deltaTime);
    }
  }

  /**
   * Méthode appelé par le world quand la souris est préssée
   * @param mouseX la position horizontale de la souris
   * @param mouseY la position verticale de la souris
   * @return un ActionElement spécifiant si l'élément à consumer le clique et l'action à réaliser
   * @throws UnsupportedAudioFileException
   * @throws IOException
   * @throws LineUnavailableException
   */
  @Override
  public ActionElement onClick(double MouseX, double MouseY) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
    if (this.isVisible() && this.isEnabled() && this.getHitBox().isHit(MouseX, MouseY)) {
      ((InterfaceGame)this.getParent()).startBuilding(this.towerClass);
      this.getClickSound().play(0.6);
      return new ActionElement(this, "cancel");
    }
    return null;
  }
}
