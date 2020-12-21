package warcraftTD;

public class Vector {
  private double x;
  private double y;

  public Vector(double x, double y) {
    this.x = x;
    this.y = y;
  }

  public Vector(Position start, Position finish) {
    this.x = finish.x - start.x;
    this.y = finish.y - start.y;
  }

  public double length() {
    return Math.sqrt(Math.pow(this.x, 2) + Math.pow(this.y, 2));
  }

  public Vector normal() {
    return new Vector(this.x / this.length(), this.y / this.length());
  }

  public double getX() {
    return this.x;
  }

  public double getY() {
    return this.y;
  }
}
