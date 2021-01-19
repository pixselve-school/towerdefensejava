package warcraftTD.particules;

import warcraftTD.utils.Position;

/**
 * Générateur de particules aléatoire
 */
public class RandomParticuleGenerator extends ParticuleGenerator {
  /**
   * Rayon d'apparition des particules
   */
  private final double radius;

  /**
   * Création d'un générateur de particules aléatoire
   *
   * @param position   La position du générateur de particules
   * @param duration   La durée de vie du générateur de particules
   * @param spawnSpeed Vitesse d'apparition
   * @param radius     Rayon d'apparition des particules
   */
  public RandomParticuleGenerator(Position position, double duration, double spawnSpeed, double radius) {
    super(position, duration, spawnSpeed);
    this.radius = radius;
  }


  /**
   * Création d'un générateur de particules aléatoire
   *
   * @param position   La position du générateur de particules
   * @param duration   La durée de vie du générateur de particules
   * @param spawnSpeed Vitesse d'apparition
   * @param radius     Rayon d'apparition des particules
   * @param particle   La particule qui va être générée
   */
  public RandomParticuleGenerator(Position position, double duration, double spawnSpeed, double radius, Particule particle) {
    super(position, duration, spawnSpeed, particle);
    this.radius = radius;
  }

  /**
   * Création d'un générateur de particules aléatoire infini
   *
   * @param position   La position du générateur de particules
   * @param spawnSpeed Vitesse d'apparition
   * @param radius     Rayon d'apparition des particules
   * @param particle   La particule qui va être générée
   */
  public RandomParticuleGenerator(Position position, double spawnSpeed, double radius, Particule particle) {
    super(position, spawnSpeed, particle);
    this.radius = radius;
  }

  /**
   * Génère une position aléatoire dans une cercle
   *
   * @param center Position du centre du cercle
   * @param radius Rayon du cercle
   * @return Une position aléatoire dans le cercle
   */
  public static Position randomPositionInCircle(Position center, double radius) {
    double r = radius * Math.sqrt(Math.random());
    double theta = Math.random() * 2 * Math.PI;
    return new Position(center.getX() + r * Math.cos(theta), center.getY() + r * Math.sin(theta));
  }

  /**
   * Génère une particule
   *
   * @param particule Particule générée
   */
  public void generateParticle(Particule particule) {
    particule.setStartingPosition(randomPositionInCircle(this.getPosition(), this.radius));
    this.addParticle(particule);
  }
}
