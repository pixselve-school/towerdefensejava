package warcraftTD.utils;

/**
 * Vecteur
 */
public class Vector {
  /**
   * Composante X
   */
  private final double x;
  /**
   * Composante Y
   */
  private final double y;

  /**
   * Création d'un nouveau vecteur
   *
   * @param x Composante X
   * @param y Composante Y
   */
  public Vector(double x, double y) {
    this.x = x;
    this.y = y;
  }

  /**
   * Création d'un nouveau vecteur
   *
   * @param start  position de départ
   * @param finish position d'arrivée
   */
  public Vector(Position start, Position finish) {
    this.x = finish.getX() - start.getX();
    this.y = finish.getY() - start.getY();
  }

  /**
   * Récupère la norme du vecteur
   *
   * @return La norme du vecteur
   */
  public double length() {
    return Math.sqrt(Math.pow(this.x, 2) + Math.pow(this.y, 2));
  }

  /**
   * Récupère le vecteur normalisé
   *
   * @return Le vecteur normalisé
   */
  public Vector normal() {
    return new Vector(this.x / this.length(), this.y / this.length());
  }

  /**
   * Récupère la composante X
   *
   * @return composante X
   */
  public double getX() {
    return this.x;
  }

  /**
   * Récupère la composante Y
   *
   * @return composante Y
   */
  public double getY() {
    return this.y;
  }
}
