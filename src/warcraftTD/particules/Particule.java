package warcraftTD.particules;

import warcraftTD.utils.Position;

public abstract class Particule {


  private final double lifeDuration;
  private double timeAlive;
  private final double height;
  private Position startingPosition;

  public double getMovementLength() {
    return this.movementLength;
  }

  private final double movementLength;

  public void setStartingPosition(Position startingPosition) {
    this.startingPosition = startingPosition;
  }
  public double getLifeDuration() {
    return this.lifeDuration;
  }

  public double getHeight() {
    return this.height;
  }


  public Particule(double lifeDuration, double height, Position startingPosition, double movementLength) {
    if (startingPosition.getX() > 1)
      throw new IllegalArgumentException("The starting position X should be less than 1. Normalize your positions before creating a Particle");
    if (startingPosition.getY() > 1)
      throw new IllegalArgumentException("The starting position Y should be less than 1. Normalize your positions before creating a Particle");
    this.lifeDuration = lifeDuration;
    this.height = height;
    this.startingPosition = startingPosition;
    this.movementLength = movementLength;
    this.timeAlive = 0;
  }

  public Particule(double lifeDuration, double height, double movementLength) {
    this.lifeDuration = lifeDuration;
    this.height = height;
    this.startingPosition = new Position(0, 0);
    this.movementLength = movementLength;
    this.timeAlive = 0;
  }

  public boolean isAlive() {
    return this.lifeDuration - this.timeAlive >= 0;
  }

  public double timeAliveMaxedByDuration() {
    return Math.min(this.lifeDuration, this.timeAlive);
  }

  public double currentHeight() {
    return this.height - (this.timeAliveMaxedByDuration() * this.height) / this.lifeDuration;
  }

  public Position currentPosition() {
    return new Position(this.startingPosition.getX(), this.startingPosition.getY() + ((this.movementLength) * this.timeAliveMaxedByDuration()) / this.lifeDuration);
  }

  public void addToTimeAlive(double time) {
    this.timeAlive += time;
  }

  public void draw(double deltaTime) {
    this.draw(this.currentPosition(), this.currentHeight());
  }

  protected abstract void draw(Position position, double height);

  /**
   * Deep clone the current particule
   *
   * @return The cloned particule
   */
  public abstract Particule clone();
}
