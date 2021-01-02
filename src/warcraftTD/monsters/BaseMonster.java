package warcraftTD.monsters;

import warcraftTD.World;
import warcraftTD.utils.Animation;
import warcraftTD.utils.Position;

public abstract class BaseMonster extends Monster {
  private final Animation walkingAnimation;
  private final Animation dieAnimation;

  public BaseMonster(Position position, World world, int health, int goldWhenDead, double speed, Animation walkingAnimation, Animation dieAnimation) {
    super(position, world, health, goldWhenDead, speed);
    this.walkingAnimation = walkingAnimation;
    this.dieAnimation = dieAnimation;
    this.dieAnimation.setCallback(() -> this.setReadyToBeRemoved(true));
  }


  /**
   * Affiche un monstre qui change de couleur au cours du temps
   * Le monstre est représenté par un cercle de couleur bleue ou grise
   */
  public void draw(double deltaTime) {
    if (this.isDead()) {
      this.dieAnimation.draw(deltaTime);
    } else {
      this.walkingAnimation.setPosition(new Position(this.getPosition().getX() + this.getScaledWidth() / 4, this.getPosition().getY() + this.getScaledHeight() / 4));
      this.dieAnimation.setPosition(new Position(this.getPosition().getX() + this.getScaledWidth() / 4, this.getPosition().getY() + this.getScaledHeight() / 4));
      this.walkingAnimation.draw(deltaTime);

    }
  }

  public abstract double getScaledHeight();

  public abstract double getScaledWidth();
}
