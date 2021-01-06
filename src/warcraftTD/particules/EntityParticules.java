package warcraftTD.particules;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class EntityParticules {
  List<ParticuleGenerator> generators;

  public EntityParticules() {
    this.generators = new LinkedList<>();
  }

  public void addGenerator(ParticuleGenerator particuleGenerator) {
    this.generators.add(particuleGenerator);
  }

  public void updateGenerators(double deltaTime) {
    Iterator<ParticuleGenerator> particuleGeneratorIterator = this.generators.iterator();
    while (particuleGeneratorIterator.hasNext()) {
      ParticuleGenerator particuleGenerator = particuleGeneratorIterator.next();
      if (!particuleGenerator.isAlive()) {
        particuleGeneratorIterator.remove();
      }
      particuleGenerator.generateAndDrawParticules(deltaTime);
      particuleGenerator.addToTimeAlive(deltaTime);
    }
  }
}
