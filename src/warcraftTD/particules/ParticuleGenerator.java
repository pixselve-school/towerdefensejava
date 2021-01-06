package warcraftTD.particules;

import warcraftTD.utils.Position;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public abstract class ParticuleGenerator {
  public Position getPosition() {
    return this.position;
  }

  private final Position position;
  private final double duration;
  private double timeTracking;
  private double spawnTracking;
  private final double spawnSpeed;
  private final List<Particule> particles;
  private final Particule particule;

  protected ParticuleGenerator(Position position, double duration, double spawnSpeed) {
    this.position = position;
    this.duration = duration;
    this.spawnSpeed = spawnSpeed;
    this.particles = new LinkedList<>();
    this.timeTracking = 0;
    this.spawnTracking = 0;
    this.particule = null;
  }

  protected ParticuleGenerator(Position position, double duration, double spawnSpeed, Particule particule) {
    this.position = position;
    this.duration = duration;
    this.spawnSpeed = spawnSpeed;
    this.particles = new LinkedList<>();
    this.timeTracking = 0;
    this.spawnTracking = 0;
    this.particule = particule;
  }

  public void addParticle(Particule particule) {
    this.particles.add(particule);
  }

  public abstract void generateParticle(Particule particule);

  public boolean isAlive() {
    return this.duration - this.timeTracking > 0;
  }

  public void generateAndDrawParticules(double deltaTime, Particule particule) {
    if (particule != null) {

      this.timeTracking += deltaTime;
      if (this.isAlive()) {
        this.spawnTracking += deltaTime;
        if (this.spawnTracking >= this.spawnSpeed) {
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

  public void generateAndDrawParticules(double deltaTime) {
    assert this.particule != null;
    this.generateAndDrawParticules(deltaTime, this.particule.clone());
  }
}
