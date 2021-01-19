package warcraftTD.utils;

import warcraftTD.libs.StdDraw;


/**
 * Outil pour les animations
 */
public class Animation {
  /**
   * Chemins d'accès vers les images de l'animation
   */
  private final String[] imagesPaths;
  /**
   * Hauteur de l'animation
   */
  private double scaledHeight;
  /**
   * Position de l'animation
   */
  private Position position;
  /**
   * Images par seconde
   */
  private final int fps;
  /**
   * Suivi du temps
   */
  private double timeTracking;
  /**
   * Image actuelle de l'animation
   */
  private int currentFrame;
  /**
   * Indique si l'animation doit se répéter indéfiniment
   */
  private final boolean isLoop;

  /**
   * Modifie la fonction qui s'exécutera une fois l'animation terminée
   *
   * @param callback La fonction qui s'exécutera une fois l'animation terminée
   */
  public void setCallback(MonsterDieCallback callback) {
    this.callback = callback;
  }

  /**
   * La fonction qui s'exécutera une fois l'animation terminée
   */
  private MonsterDieCallback callback;

  /**
   * Création d'une nouvelle animation
   *
   * @param imagesPaths  Chemins d'accès vers les images de l'animation
   * @param scaledHeight Hauteur de l'animation
   * @param scaledWidth  Largeur de l'animation (NON APPLICABLE)
   * @param position     Position de l'animation
   * @param fps          Images par seconde
   * @param isLoop       Indique si l'animation doit se répéter indéfiniment
   */
  public Animation(String[] imagesPaths, double scaledHeight, double scaledWidth, Position position, int fps, boolean isLoop) {
    this.imagesPaths = imagesPaths;
    this.scaledHeight = scaledHeight;
    this.position = position;
    this.fps = fps;
    this.timeTracking = 0;
    this.currentFrame = 0;
    this.isLoop = isLoop;
    this.callback = null;
  }

  /**
   * Création d'une nouvelle animation
   *
   * @param imagesPaths  Chemins d'accès vers les images de l'animation
   * @param scaledHeight Hauteur de l'animation
   * @param scaledWidth  Largeur de l'animation (NON APPLICABLE)
   * @param position     Position de l'animation
   * @param fps          Images par seconde
   * @param callback     La fonction qui s'exécutera une fois l'animation terminée
   */
  public Animation(String[] imagesPaths, double scaledHeight, double scaledWidth, Position position, int fps, MonsterDieCallback callback) {
    this.imagesPaths = imagesPaths;
    this.scaledHeight = scaledHeight;
    this.position = position;
    this.fps = fps;
    this.timeTracking = 0;
    this.currentFrame = 0;
    this.isLoop = false;
    this.callback = callback;
  }

  /**
   * Dessine l'animation
   *
   * @param deltaTime Le temps
   */
  public void draw(double deltaTime) {
    this.timeTracking += deltaTime;
    if (this.timeTracking >= 1.0 / this.fps) {
      this.currentFrame++;
      this.timeTracking = 0;
      if (this.currentFrame >= this.imagesPaths.length) {
        if (this.isLoop) {
          this.currentFrame = 0;
        } else {
          assert this.callback != null;
          this.callback.die();
          return;
        }
      }
    }
    if (this.position != null)
      StdDraw.pictureHeight(this.position.getX(), this.position.getY(), this.getCurrentFrame(), this.scaledHeight);
  }
  /**
   * Modifie la position de l'animation
   *
   * @param position La nouvelle position
   */
  public void setPosition(Position position) {
    this.position = position;
  }

  /**
   * Récupère le chemin d'accès vers l'image actuelle
   *
   * @return Le chemin d'accès vers l'image actuelle
   */
  public String getCurrentFrame() {
    return this.imagesPaths[this.currentFrame];
  }

  /**
   * Modifie la hauteur de l'animation
   *
   * @param scaledHeight La nouvelle hauteur de l'animation
   */
  public void setScaledHeight(double scaledHeight) {
    this.scaledHeight = scaledHeight;
  }
}
