package warcraftTD.hud;

import warcraftTD.libs.StdDraw;
import warcraftTD.utils.Position;

import java.awt.*;

/**
 * Une notification de Texte sur l'interface utilisateur
 * Elle se déplace vers le haut ou vers le bas avant de se supprimer
 */
public class NotifText extends Text {
  /** La distance actuelle verticale à parcourir */
  private double deltaY;
  /** La distance totale verticale à parcourir */
  private final double maxDeltaY;
  /** Spécifie si la direction de l'animation est vers le haut */
  private boolean upDirection = true;
  /** Position intiale de la notification */
  private final Position initPos;
  /** Vitesse de déplacement de la notification en seconde*/
  private final double speed;

  /**
   * Initialise une notification
   * @param position la position de l'élément
   * @param width la largeur de l'élément
   * @param height la hauteur de l'élément
   * @param font la police d'écriture
   * @param parent l'interface mère de l'élément
   * @param text le texte à afficher
   * @param deltaY la hauteur de l'animation
   * @param color la couleur de la notification
   */
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

  /**
   * Actualise la logique de l'élément et affiche son apparence
   * @param mouseX la position horizontale de la souris
   * @param mouseY la position verticale de la souris
   * @param deltaTime le temps d'un tick en seconde
   */
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
