package warcraftTD.world;

import warcraftTD.libs.StdDraw;
import warcraftTD.particules.CircleParticule;
import warcraftTD.particules.ParticuleGenerator;
import warcraftTD.particules.RandomParticuleGenerator;

import java.awt.*;

public class Flower extends Entity {
  private final String path;
  private final ParticuleGenerator particuleGenerator;

  public Flower(FlowerType type, Tile tile) {
    switch (type) {
      case BLUE:
        this.particuleGenerator = new RandomParticuleGenerator(tile.getPosition(), 99999, 0.5, tile.getHeight() * 0.40, new CircleParticule(1, 0.01, 0.05, new Color(73, 109, 227)));
        this.path = "images/tiles/flowers/blue.png";
        break;
      case RED:
        this.particuleGenerator = new RandomParticuleGenerator(tile.getPosition(), 99999, 0.5, tile.getHeight() * 0.40, new CircleParticule(1, 0.01, 0.05, new Color(176, 84, 105)));
        this.path = "images/tiles/flowers/red.png";
        break;
      case WHITE:
        this.particuleGenerator = new RandomParticuleGenerator(tile.getPosition(), 99999, 0.5, tile.getHeight() * 0.40, new CircleParticule(1, 0.01, 0.05, new Color(216, 208, 224)));
        this.path = "images/tiles/flowers/white.png";
        break;
      case YELLOW:
        this.particuleGenerator = new RandomParticuleGenerator(tile.getPosition(), 99999, 0.5, tile.getHeight() * 0.40, new CircleParticule(1, 0.01, 0.05, new Color(242, 178, 43)));
        this.path = "images/tiles/flowers/yellow.png";
        break;
      default:
        this.particuleGenerator = null;
        this.path = "images/tiles/flowers/bush.png";
        break;

    }


  }

  /**
   * Update and draw the flower
   *
   * @param deltaTime The game delta time
   * @param tile      The tile the flower is attached to
   */
  public void update(double deltaTime, Tile tile) {
    if (this.particuleGenerator != null) {
      this.particuleGenerator.generateAndDrawParticules(deltaTime);
    }
    StdDraw.pictureHeight(tile.getPosition().getX(), tile.getPosition().getY(), this.path, tile.getHeight() * 0.40);
  }
}
