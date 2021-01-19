package warcraftTD.utils;

import java.util.Objects;

/**
 * Position
 */
public class Position implements Comparable<Position> {
  /**
   * Coordonnée X
   */
  private double x;
  /**
   * Coordonnée Y
   */
  private double y;

  /**
   * Classe qui permet d'avoir la position sur l'axe des x et des y des monstres et des tours
   *
   * @param x Coordonnée X
   * @param y Coordonnée Y
   */
  public Position(double x, double y) {
    this.x = x;
    this.y = y;
  }

  /**
   * Création d'une nouvelle position à partir d'une position existante
   *
   * @param p Une position
   */
  public Position(Position p) {
    this.x = p.x;
    this.y = p.y;
  }

  /**
   * Récupère la coordonnée X
   *
   * @return La coordonnée X
   */
  public double getX() {
    return this.x;
  }

  /**
   * Modifie la coordonnée X
   *
   * @param x La nouvelle coordonnée X
   */
  public void setX(double x) {
    this.x = x;
  }

  /**
   * Récupère la coordonnée Y
   *
   * @return La coordonnée Y
   */
  public double getY() {
    return this.y;
  }

  /**
   * Modifie la coordonnée Y
   *
   * @param y La nouvelle coordonnée Y
   */
  public void setY(double y) {
    this.y = y;
  }

  /**
   * Récupère la position appliquée à une monde
   *
   * @param squareHeight La hauteur des tuiles
   * @param squareWidth  La largeur des tuiles
   * @return La position appliquée à un monde
   */
  public Position getWorldPosition(double squareHeight, double squareWidth) {
    return new Position(this.x * squareWidth + squareWidth / 2, this.y * squareHeight + squareHeight / 2);
  }

  /**
   * Vérifie l'égalité avec une autre position
   *
   * @param p Une position
   * @return true si p est égale à la position
   */
  public boolean equals(Position p) {
    return this.x == p.x && this.y == p.y;
  }

  /**
   * Mesure la distance euclidienne entre 2 positions.
   *
   * @param p Une position
   * @return La distance euclidienne entre 2 positions
   */
  public double dist(Position p) {
    return Math.sqrt(Math.pow(this.x - p.x, 2) + Math.pow(this.y - p.y, 2));
  }

  /**
   * Retourne la position du point sur l'axe des x et des y
   */
  public String toString() {
    return "(" + this.x + "," + this.y + ")";
  }

  @Override
  public boolean equals(Object o) {
    if (o == this) return true;
    if (!(o instanceof Position)) return false;
    Position p = (Position) o;
    return (p.x == this.x && p.y == this.y);
  }

  @Override
  public int compareTo(Position p) {
    if (this.x < p.x) return -1;
    else if (this.x > p.x) return 1;
    else {
      if (this.y < p.y) return -1;
      else if (this.y > p.y) return 1;
      else {
        return 0;
      }
    }
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.getX(), this.getY());
  }
}
