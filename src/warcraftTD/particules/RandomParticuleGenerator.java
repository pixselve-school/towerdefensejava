package warcraftTD.particules;

import warcraftTD.utils.Position;

public class RandomParticuleGenerator extends ParticuleGenerator {
  private final double radius;

  public RandomParticuleGenerator(Position position, double duration, double spawnSpeed, double radius) {
    super(position, duration, spawnSpeed);
    this.radius = radius;
  }


  /**
   * Create a random particule generator
   *
   * @param position   The generator center position
   * @param duration   The generator duration life in seconds
   * @param spawnSpeed The generator spawn speed (seconds / particules)
   * @param radius     The generator spawning radius
   * @param particle   The particule the generator will always spawn (if not change)
   */
  public RandomParticuleGenerator(Position position, double duration, double spawnSpeed, double radius, Particule particle) {
    super(position, duration, spawnSpeed, particle);
    this.radius = radius;
  }

  /**
   * Create a random particule generator
   *
   * @param position   The generator center position
   * @param spawnSpeed The generator spawn speed (seconds / particules)
   * @param radius     The generator spawning radius
   * @param particle   The particule the generator will always spawn (if not change)
   */
  public RandomParticuleGenerator(Position position, double spawnSpeed, double radius, Particule particle) {
    super(position, spawnSpeed, particle);
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
