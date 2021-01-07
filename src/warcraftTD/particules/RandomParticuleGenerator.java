package warcraftTD.particules;

import warcraftTD.utils.Position;

public class RandomParticuleGenerator extends ParticuleGenerator {
  private final double radius;

  public RandomParticuleGenerator(Position position, double duration, double spawnSpeed, double radius) {
    super(position, duration, spawnSpeed);
    this.radius = radius;
  }

  public RandomParticuleGenerator(Position position, double duration, double spawnSpeed, double radius, Particule particle) {
    super(position, duration, spawnSpeed, particle);
    this.radius = radius;
  }

  public static Position randomPositionInCircle(Position center, double radius) {
    double r = radius * Math.sqrt(Math.random());
    double theta = Math.random() * 2 * Math.PI;
    return new Position(center.getX() + r * Math.cos(theta), center.getY() + r * Math.sin(theta));
  }

  public void generateParticle(Particule particule) {
    particule.setStartingPosition(randomPositionInCircle(this.getPosition(), this.radius));
    this.addParticle(particule);
  }
}
