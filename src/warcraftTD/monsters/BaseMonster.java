package warcraftTD.monsters;

import warcraftTD.libs.StdDraw;
import warcraftTD.utils.Animation;
import warcraftTD.utils.Position;

import java.awt.*;
import java.util.List;

/**
 * Fondation de monstre de base
 */
public abstract class BaseMonster extends Monster {
  /**
   * L'animation de marche
   */
  private Animation walkingAnimation;
  /**
   * L'animation de mort
   */
  private Animation dieAnimation;
  /**
   * Indique si le monstre est un monstre volant
   */
  private final boolean isFlying;

  /**
   * Création d'une fondation de monstre de base
   *
   * @param path             Le chemin que le monstre va emprunter
   * @param health           Le nombre de point de vie du monstre
   * @param goldWhenDead     Le quantité d'or qu'il va rapporter au jouer une fois tué
   * @param speed            La vitesse de base du monstre
   * @param hitBoxRadius     Le rayon de la hit box du monstre
   * @param walkingAnimation L'animation de marche du monstre
   * @param dieAnimation     L'animation de mort du monstre
   */
  public BaseMonster(List<Position> path, int health, int goldWhenDead, double speed, double hitBoxRadius, Animation walkingAnimation, Animation dieAnimation) {
    super(health, goldWhenDead, speed, hitBoxRadius, path);
    this.walkingAnimation = walkingAnimation;
    this.dieAnimation = dieAnimation;
    this.dieAnimation.setCallback(() -> this.setReadyToBeRemoved(true));
    this.isFlying = false;
  }

  /**
   * Création d'une fondation de monstre de base
   *
   * @param path             Le chemin que le monstre va emprunter
   * @param health           Le nombre de point de vie du monstre
   * @param goldWhenDead     Le quantité d'or qu'il va rapporter au jouer une fois tué
   * @param speed            La vitesse de base du monstre
   * @param hitBoxRadius     Le rayon de la hit box du monstre
   * @param isFlying         Indique si le monstre est un monstre volant
   * @param walkingAnimation L'animation de marche du monstre
   * @param dieAnimation     L'animation de mort du monstre
   */
  public BaseMonster(List<Position> path, int health, int goldWhenDead, double speed, double hitBoxRadius, boolean isFlying, Animation walkingAnimation, Animation dieAnimation) {
    super(health, goldWhenDead, speed, hitBoxRadius, path);
    this.walkingAnimation = walkingAnimation;
    this.dieAnimation = dieAnimation;
    this.dieAnimation.setCallback(() -> this.setReadyToBeRemoved(true));
    this.isFlying = isFlying;
  }


  /**
   * Dessine le monstre
   *
   * @param deltaTime Le delta temps du jeu
   */
  public void draw(double deltaTime) {
    if (this.isDead()) {
      this.dieAnimation.draw(deltaTime, this.getShiftX(), this.getShiftY());
    } else {
      double ratio = StdDraw.getPictureRatio(this.walkingAnimation.getCurrentFrame());
      //Position positionAnimation = new Position(this.getPosition().getX() + (this.getScaledHeight() * ratio) / 5, this.getPosition().getY() + this.getScaledHeight() / 3);
      Position positionAnimation = new Position(this.getPosition().getX(), this.getPosition().getY());

      if (this.isFlying) {
        StdDraw.setPenColor(Color.gray);
        StdDraw.filledEllipse(this.getPosition().getX(), this.getPosition().getY(), 0.15 * this.getScaleWidth(), 0.05 * this.getScaleHeight());
      }

      this.walkingAnimation.setPosition(positionAnimation);
      this.dieAnimation.setPosition(positionAnimation);
      this.walkingAnimation.draw(deltaTime, this.getShiftX(), this.getShiftY());

    }
  }

  /**
   * Vérifie si le monstre est un monstre volant
   *
   * @return true si le monstre est un monstre volant
   */
  public boolean isFlying() {
    return this.isFlying;
  }


  /**
   * Récupère l'animation de marche du monstre
   *
   * @return l'animation de marche du monstre
   */
  public Animation getWalkingAnimation() {
    return this.walkingAnimation;
  }

  /**
   * Récupère l'animation de mort du monstre
   *
   * @return l'animation de mort du monstre
   */
  public Animation getDieAnimation() {
    return this.dieAnimation;
  }

  /**
   * @param nbSquareX nombre de tuiles horizontales
   * @param nbSquareY nombre de tuiles verticales
   */
  @Override
  public void resizeMonster(int nbSquareX, int nbSquareY) {
    super.resizeMonster(nbSquareX, nbSquareY);
    this.getDieAnimation().setScaledHeight(this.getScaleHeight());
    this.getWalkingAnimation().setScaledHeight(this.getScaleHeight());
  }
}
