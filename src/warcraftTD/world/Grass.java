package warcraftTD.world;

import warcraftTD.libs.StdDraw;
import warcraftTD.utils.Position;

import java.awt.*;

/**
 * Tuile de type herbe
 */
public class Grass extends Tile {

  /**
   * Créer une tuile d'herbe
   *
   * @param position La position de la tuile
   * @param height   La hauteur de la tuile
   * @param width    La largeur de la tuile
   */
  public Grass(Position position, double height, double width) {
    super(new Position(position.getX() * width + width / 2, position.getY() * height + height / 2), position, height, width);
  }


  /**
   * Actualiser la tuile
   *
   * @param deltaTime Le delta temps du jeu
   */
  public void update(double deltaTime) {
  }


  /**
   * Dessiner la partie statique de la tuile
   */
  public void drawStaticPart() {
    StdDraw.picture(this.getPosition().getX(), this.getPosition().getY(), "images/tiles/grass.png", this.getWidth() + 0.001, this.getHeight() + 0.001);
  }


  /**
   * Exécuté lorsque la tuile est cliquée
   * @param x la position x de la souris
   * @param y la position y de la souris
   */
  public void onClick(double x, double y) {

  }

  /**
   * Vérifie si l'on peut construire sur la tuile
   *
   * @return true si on peut construire sur la tuile
   */
  public boolean isBuildable() {
    return this.getContains() == null || !this.getContains().getBuildable().equals(EntityBuildable.NOTBUILDABLE);
  }
}
