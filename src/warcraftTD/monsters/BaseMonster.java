package warcraftTD.monsters;

import warcraftTD.WorldGame;
import warcraftTD.libs.StdDraw;
import warcraftTD.utils.Animation;
import warcraftTD.utils.Position;

import java.awt.*;
import java.util.List;

public abstract class BaseMonster extends Monster {
  private final Animation walkingAnimation;
  private final Animation dieAnimation;
  private final boolean isFlying;

  public BaseMonster(Position position, WorldGame world, int health, int goldWhenDead, double speed, Animation walkingAnimation, Animation dieAnimation) {
    super(position, world, health, goldWhenDead, speed);
    this.walkingAnimation = walkingAnimation;
    this.dieAnimation = dieAnimation;
    this.dieAnimation.setCallback(() -> this.setReadyToBeRemoved(true));
    this.isFlying = false;

  }

  public BaseMonster(List<Position> path, int health, int goldWhenDead, double speed, Animation walkingAnimation, Animation dieAnimation) {
    super(health, goldWhenDead, speed, path);
    this.walkingAnimation = walkingAnimation;
    this.dieAnimation = dieAnimation;
    this.dieAnimation.setCallback(() -> this.setReadyToBeRemoved(true));
    this.isFlying = false;
  }

  public BaseMonster(List<Position> path, int health, int goldWhenDead, double speed, boolean isFlying, Animation walkingAnimation, Animation dieAnimation) {
    super(health, goldWhenDead, speed, path);
    this.walkingAnimation = walkingAnimation;
    this.dieAnimation = dieAnimation;
    this.dieAnimation.setCallback(() -> this.setReadyToBeRemoved(true));
    this.isFlying = isFlying;
  }


  /**
   * Affiche un monstre qui change de couleur au cours du temps
   * Le monstre est représenté par un cercle de couleur bleue ou grise
   */
  public void draw(double deltaTime) {
    if (this.isDead()) {
      this.dieAnimation.draw(deltaTime);
    } else {
      double ratio = StdDraw.getPictureRatio(this.walkingAnimation.getCurrentFrame());
      Position positionAnimation = new Position(this.getPosition().getX() + (this.getScaledHeight() * ratio) / 5, this.getPosition().getY() + this.getScaledHeight() / 3);

      if (this.isFlying) {
        StdDraw.setPenColor(Color.gray);
        StdDraw.filledEllipse(this.getPosition().getX() - 0.01, this.getPosition().getY(), 0.015, 0.005);
      }

      this.walkingAnimation.setPosition(positionAnimation);
      this.dieAnimation.setPosition(positionAnimation);
      this.walkingAnimation.draw(deltaTime);

    }
  }

  public abstract double getScaledHeight();

  public abstract double getScaledWidth();

  public boolean isFlying() {
    return this.isFlying;
  }
}
