package warcraftTD.particules;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Particules d'une entité
 */
public class EntityParticules {
  /**
   * Générateurs de particules portés
   */
  List<ParticuleGenerator> generators;

  /**
   * Création de particules d'une entité
   */
  public EntityParticules() {
    this.generators = new LinkedList<>();
  }

  /**
   * Ajoute un générateur de particules
   *
   * @param particuleGenerator Générateur de particules
   */
  public void addGenerator(ParticuleGenerator particuleGenerator) {
    this.generators.add(particuleGenerator);
  }

  /**
   * Rafraichi les générateurs de particules
   *
   * @param deltaTime Le delta temps du jeu
   */
  public void updateGenerators(double deltaTime) {
    Iterator<ParticuleGenerator> particuleGeneratorIterator = this.generators.iterator();

    while (particuleGeneratorIterator.hasNext()) {
      ParticuleGenerator particuleGenerator = particuleGeneratorIterator.next();

      if (particuleGenerator.isAlive() || particuleGenerator.particuleAmount() > 0) {
        particuleGenerator.generateAndDrawParticules(deltaTime);
        particuleGenerator.addToTimeAlive(deltaTime);
      } else {

        particuleGeneratorIterator.remove();

      }

    }
  }
}
