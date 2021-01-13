package warcraftTD.hud;

import warcraftTD.libs.StdDraw;
import warcraftTD.utils.Position;

import java.awt.*;

/**
 * Un élément de texte à afficher sur une interface utilisateur
 */
public class Text extends Element {
  /** le texte à afficher */
  private String text;
  /** la police d'écriture du texte */
  private Font font;
  /** la couleur du texte */
  private Color color;

  /**
   * Récupère la police d'écriture du texte
   * @return la police d'écriture du texte
   */
  public Font getFont() {
    return this.font;
  }

  /**
   * Modifie la police d'écriture du texte
   * @return la police d'écriture du texte
   */
  public void setFont(Font font) {
    this.font = font;
  }

  /**
   * Récupère la couleur du texte
   * @return la couleur du texte
   */
  public Color getColor() {
    return this.color;
  }

  /**
   * Modifie la couleur du texte
   * @param color la couleur du texte
   */
  public void setColor(Color color) {
    this.color = color;
  }

  /**
   * Récupère le texte à afficher
   * @return le texte à afficher
   */
  public String getText() {
    return this.text;
  }

  /**
   * Modifie le texte à afficher
   * @param text le texte à afficher
   */
  public void setText(String text) {
    this.text = text;
  }

  /**
   * Initialise un texte d'interface
   * @param position la position de l'élément
   * @param width la largeur de l'élément
   * @param height la hauteur de l'élément
   * @param font la police d'écriture
   * @param parent l'interface mère de l'élément
   * @param text le texte à afficher
   */
  public Text(Position position, double width, double height, Font font, Interface parent, String text) {
    super(position, width, height, parent);
    this.font = font;
    this.text = text;
    this.color = new Color(0, 0, 0);
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
      StdDraw.setPenColor(this.color);
      StdDraw.setFont(this.font);
      StdDraw.text(this.getPosition().getX(), this.getPosition().getY(), this.text);
    }
  }

}
