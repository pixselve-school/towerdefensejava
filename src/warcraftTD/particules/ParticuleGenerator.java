package warcraftTD.particules;

import warcraftTD.utils.Position;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Générateur de particules
 */
public abstract class ParticuleGenerator {
  /**
   * La position du générateur de particules
   */
  private final Position position;
  /**
   * La durée de vie du générateur de particules
   */
  private final double duration;
  /**
   * Vitesse d'apparition
   */
  private final double spawnSpeed;
  /**
   * Particules en vie dans le générateur
   */
  private final List<Particule> particles;
  /**
   * Particule générée
   */
  private final Particule particule;
  /**
   * Indique si le générateur à une durée de vie infinie
   */
  private final boolean infinite;
  /**
   * Suivi du temps
   */
  private double timeTracking;
  /**
   * Suivi du temps d'apparition
   */
  private double spawnTracking;
  /**
   * Création d'un générateur de particule
   *
   * @param position   La position du générateur de particules
   * @param duration   La durée de vie du générateur de particules
   * @param spawnSpeed Vitesse d'apparition
   */
  protected ParticuleGenerator(Position position, double duration, double spawnSpeed) {
    this.position = position;
    this.duration = duration;
    this.spawnSpeed = spawnSpeed;
    this.particles = new LinkedList<>();
    this.timeTracking = 0;
    this.spawnTracking = 0;
    this.particule = null;
    this.infinite = false;
  }

  /**
   * Création d'un générateur de particule
   *
   * @param position   La position du générateur de particules
   * @param duration   La durée de vie du générateur de particules
   * @param spawnSpeed Vitesse d'apparition
   * @param particule  La particule qui va être générée
   */
  protected ParticuleGenerator(Position position, double duration, double spawnSpeed, Particule particule) {
    this.position = position;
    this.duration = duration;
    this.spawnSpeed = spawnSpeed;
    this.particles = new LinkedList<>();
    this.timeTracking = 0;
    this.spawnTracking = 0;
    this.particule = particule;
    this.infinite = false;
  }

  /**
   * Création d'un générateur de particule infini
   *
   * @param position   La position du générateur de particules
   * @param spawnSpeed Vitesse d'apparition
   * @param particule  La particule qui va être générée
   */
  protected ParticuleGenerator(Position position, double spawnSpeed, Particule particule) {
    this.position = position;
    this.duration = 0;
    this.spawnSpeed = spawnSpeed;
    this.particles = new LinkedList<>();
    this.timeTracking = 0;
    this.spawnTracking = 0;
    this.particule = particule;
    this.infinite = true;
  }

  /**
   * Récupère la position du générateur de particules
   *
   * @return La position du générateur de particules
   */
  public Position getPosition() {
    return this.position;
  }

  /**
   * Ajoute du temps de vie
   *
   * @param time Le temps qui s'est écoulé
   */
  public void addToTimeAlive(double time) {
    this.timeTracking += time;
  }

  /**
   * Ajoute une particule
   *
   * @param particule Particule
   */
  public void addParticle(Particule particule) {
    this.particles.add(particule);
  }

  /**
   * Génération de la particule
   *
   * @param particule Particule générée
   */
  public abstract void generateParticle(Particule particule);

  /**
   * Vérifie si le générateur est en vie
   *
   * @return true si le générateur est en vie
   */
  public boolean isAlive() {
    return this.infinite || this.duration - this.timeTracking > 0;
  }

  /**
   * Récupère le nombre de particules en vie
   *
   * @return Le nombre de particules en vie
   */
  public int particuleAmount() {
    return this.particles.size();
  }

  /**
   * Génère et dessine les particules
   *
   * @param deltaTime Le delta temps du jeu
   * @param particule la particule que le générateur doit générer
   */
  public void generateAndDrawParticules(double deltaTime, Particule particule) {
    if (particule != null) {

      this.timeTracking += deltaTime;
      if (this.isAlive() || this.particles.size() > 0) {
        this.spawnTracking += deltaTime;
        if (this.isAlive() && this.spawnTracking >= this.spawnSpeed) {
          this.generateParticle(particule);
          this.spawnTracking = 0;
        }

        Iterator<Particule> particuleIterator = this.particles.iterator();

        while (particuleIterator.hasNext()) {
          Particule particuleElement = particuleIterator.next();
          particuleElement.addToTimeAlive(deltaTime);
          if (particuleElement.isAlive()) {
            particuleElement.draw(deltaTime);
          } else {
            particuleIterator.remove();
          }
        }


      }
    }
  }

  /**
   * Génère et dessine les particules
   *
   * @param deltaTime Le delta temps du jeu
   * @implSpec la particule générée sera celle stockée dans "particule". Elle a été placée par un des constructeurs
   */
  public void generateAndDrawParticules(double deltaTime) {
    assert this.particule != null;
    this.generateAndDrawParticules(deltaTime, this.particule.clone());
  }
}
