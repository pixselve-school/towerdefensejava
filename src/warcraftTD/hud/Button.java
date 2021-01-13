package warcraftTD.hud;

import warcraftTD.libs.StdDraw;
import warcraftTD.utils.Position;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;

/**
 * Boutons pour les interfaces utilisateurs.
 */
public class Button extends ClickableElement {
  /** Le chemin vers l'image d'apparence du bouton */
  private String sprite;
  /** Le chemin vers l'image d'apparence quand il est survolé du bouton */
  private String spriteHover;
  /** L'action du bouton quand on clique dessus */
  private final String action;

  /**
   * Récupère le sprite (l'image apparente) du bouton
   * @return le chemin vers le sprite du bouton
   */
  public String getSprite() {
    return this.sprite;
  }

  /**
   * Récupère l'action du bouton quand on clique dessus
   * @return l'action du bouton quand on clique dessus
   */
  public String getAction() {
    return this.action;
  }

  /**
   * Modifie Le chemin vers l'image d'apparence du bouton
   * @param sprite Le chemin vers l'image d'apparence du bouton
   */
  public void setSprite(String sprite) {
    this.sprite = sprite;
  }

  /**
   * Modifie Le chemin vers l'image d'apparence quand il est survolé du bouton
   * @param spriteHover Le chemin vers l'image d'apparence quand il est survolé du bouton
   */
  public void setSpriteHover(String spriteHover) {
    this.spriteHover = spriteHover;
  }

  /**
   * Initialisation du bouton
   * @param pos la position du bouton
   * @param width la largueur du bouton
   * @param height la hauteur du bouton
   * @param sprite l'apparence du bouton
   * @param spriteHover l'apparence du bouton quand il est survolé par la souris
   * @param action l'action du bouton quand on clique dessus
   * @param parent l'interface à laquelle le bouton appartient
   * @throws UnsupportedAudioFileException
   * @throws IOException
   * @throws LineUnavailableException
   */
  public Button(Position pos, double width, double height, String sprite, String spriteHover, String action, Interface parent) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
    super(pos, width, height, parent);
    this.action = action;
    this.sprite = sprite;
    this.spriteHover = spriteHover;
  }

  /**
   * Modifie la position de l'élément
   * @param position la nouvelle position
   */
  @Override
  public void setPosition(Position position) {
    super.setPosition(position);
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
      if (this.getHitBox().isHit(mouseX, mouseY) && this.isEnabled()) {
        StdDraw.picture(this.getPosition().getX(), this.getPosition().getY(), this.spriteHover, this.getWidth(), this.getHeight());
      } else {
        StdDraw.picture(this.getPosition().getX(), this.getPosition().getY(), this.sprite, this.getWidth(), this.getHeight());
        if (!this.isEnabled())
          StdDraw.picture(this.getPosition().getX(), this.getPosition().getY(), "images/disable_buttonshop.png", this.getWidth(), this.getHeight());
      }
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
  public ActionElement onClick(double mouseX, double mouseY) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
    if(this.isVisible() && this.isEnabled() && this.getHitBox().isHit(mouseX, mouseY)){
      this.getClickSound().play(0.6);
      return new ActionElement(this, this.action);
    } else {
      return null;
    }
  }
}
