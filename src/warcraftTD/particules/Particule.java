package warcraftTD.particules;

import warcraftTD.utils.Position;

/**
 * Particule
 */
public abstract class Particule {


  /**
   * La durée de vie de la particule
   */
  private final double lifeDuration;
  /**
   * La hauteur de la particule
   */
  private final double height;
  /**
   * La distance que la particule va parcourir
   */
  private final double movementLength;
  /**
   * Le temps depuis lequel la particule est en vie
   */
  private double timeAlive;
  /**
   * La position de départ de la particule
   */
  private Position startingPosition;

  /**
   * Création d'une nouvelle particule
   *
   * @param lifeDuration   La durée de vie de la particule
   * @param height         La hauteur de la particule
   * @param movementLength La distance que la particule va parcourir
   */
  public Particule(double lifeDuration, double height, double movementLength) {
    this.lifeDuration = lifeDuration;
    this.height = height;
    this.startingPosition = new Position(0, 0);
    this.movementLength = movementLength;
    this.timeAlive = 0;
  }

  /**
   * Récupère la distance que la particule va parcourir
   *
   * @return La distance que la particule va parcourir
   */
  public double getMovementLength() {
    return this.movementLength;
  }

  /**
   * Modifie la position de départ de la particule
   *
   * @param startingPosition La nouvelle position de départ de la particule
   */
  public void setStartingPosition(Position startingPosition) {
    this.startingPosition = startingPosition;
  }

  /**
   * Récupère la durée de vie de la particule
   *
   * @return La durée de vie de la particule
   */
  public double getLifeDuration() {
    return this.lifeDuration;
  }

  /**
   * Récupère la hauteur de la particule
   *
   * @return La hauteur de la particule
   */
  public double getHeight() {
    return this.height;
  }

  /**
   * Vérifie si la particule est en vie
   *
   * @return true si la particule est en vie
   */
  public boolean isAlive() {
    return this.lifeDuration - this.timeAlive >= 0;
  }

  /**
   * Récupère le temps depuis lequel la particule est en vie
   *
   * @return Le temps depuis lequel la particule est en vie
   */
  public double timeAliveMaxedByDuration() {
    return Math.min(this.lifeDuration, this.timeAlive);
  }

  /**
   * Récupère la hauteur actuelle de la particule
   *
   * @return La hauteur actuelle de la particule
   */
  public double currentHeight() {
    return this.height - (this.timeAliveMaxedByDuration() * this.height) / this.lifeDuration;
  }

  /**
   * Récupère la position actuelle de la particule
   *
   * @return La position actuelle de la particule
   */
  public Position currentPosition() {
    return new Position(this.startingPosition.getX(), this.startingPosition.getY() + ((this.movementLength) * this.timeAliveMaxedByDuration()) / this.lifeDuration);
  }

  /**
   * Ajoute du temps de vie
   *
   * @param time Le temps qui s'est écoulé
   */
  public void addToTimeAlive(double time) {
    this.timeAlive += time;
  }

  /**
   * Dessine la particule
   *
   * @param deltaTime Le delta temps du jeu
   */
  public void draw(double deltaTime) {
    this.draw(this.currentPosition(), this.currentHeight());
  }

  /**
   * Dessine la particule
   *
   * @param position la position de la particule
   * @param height   la hauteur de la particule
   */
  protected abstract void draw(Position position, double height);

  /**
   * Clone la particule
   *
   * @return La particule clonée
   */
  public abstract Particule clone();
}
