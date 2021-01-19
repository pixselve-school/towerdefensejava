package warcraftTD.utils;

/**
 * Décalage du monde
 */
public class Padding {
  /**
   * Décalage en haut
   */
  private final int top;
  /**
   * Décalage en bas
   */
  private final int bottom;
  /**
   * Décalage à gauche
   */
  private final int left;
  /**
   * Décalage à droite
   */
  private final int right;

  /**
   * Création d'un nouveau paramètre de décalage
   *
   * @param top    Décalage en haut
   * @param bottom Décalage en bas
   * @param left   Décalage à gauche
   * @param right  Décalage à droite
   */
  public Padding(int top, int bottom, int left, int right) {
    this.top = top;
    this.bottom = bottom;
    this.left = left;
    this.right = right;
  }

  /**
   * Récupère le décalage en haut
   *
   * @return Le décalage en haut
   */
  public int getTop() {
    return this.top;
  }

  /**
   * Récupère le décalage en bas
   *
   * @return Le décalage en bas
   */
  public int getBottom() {
    return this.bottom;
  }

  /**
   * Récupère le décalage à gauche
   *
   * @return Le décalage à gauche
   */
  public int getLeft() {
    return this.left;
  }

  /**
   * Récupère le décalage à droite
   *
   * @return Le décalage à droite
   */
  public int getRight() {
    return this.right;
  }

  public String toString() {
    return "Padding{" +
        "top=" + this.top +
        ", bottom=" + this.bottom +
        ", left=" + this.left +
        ", right=" + this.right +
        '}';
  }
}
