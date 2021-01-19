package warcraftTD.world;

import warcraftTD.libs.StdDraw;
import warcraftTD.utils.Position;

import java.awt.*;

/**
 * Tuile chemin
 */
public class Pathway extends Tile {
  /**
   * Créer une tuile chemin
   *
   * @param position La position de la tuile
   * @param height   La hauteur de la tuile
   * @param width    La largeur de la tuile
   */
  public Pathway(Position position, double height, double width) {
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
    Position topLeft = new Position(this.getPosition().getX() - this.getWidth() / 4, this.getPosition().getY() + this.getHeight() / 4);
    Position topRight = new Position(this.getPosition().getX() + this.getWidth() / 4, this.getPosition().getY() + this.getHeight() / 4);
    Position bottomLeft = new Position(this.getPosition().getX() - this.getWidth() / 4, this.getPosition().getY() - this.getHeight() / 4);
    Position bottomRight = new Position(this.getPosition().getX() + this.getWidth() / 4, this.getPosition().getY() - this.getHeight() / 4);

    final double HALF_WIDTH = this.getWidth() / 2 + 0.001;
    final double HALF_HEIGHT = this.getHeight() / 2 + 0.001;

    switch (this.getDirectionValue()) {
      case 0:
        StdDraw.picture(topLeft.getX(), topLeft.getY(), "images/tiles/top-left.png", HALF_WIDTH, HALF_HEIGHT);
        StdDraw.picture(topRight.getX(), topRight.getY(), "images/tiles/top-right.png", HALF_WIDTH, HALF_HEIGHT);
        StdDraw.picture(bottomLeft.getX(), bottomLeft.getY(), "images/tiles/bottom-left.png", HALF_WIDTH, HALF_HEIGHT);
        StdDraw.picture(bottomRight.getX(), bottomRight.getY(), "images/tiles/bottom-right.png", HALF_WIDTH, HALF_HEIGHT);
        break;
      case 1:
        StdDraw.picture(topLeft.getX(), topLeft.getY(), "images/tiles/left.png", HALF_WIDTH, HALF_HEIGHT);
        StdDraw.picture(topRight.getX(), topRight.getY(), "images/tiles/right.png", HALF_WIDTH, HALF_HEIGHT);
        StdDraw.picture(bottomLeft.getX(), bottomLeft.getY(), "images/tiles/bottom-left.png", HALF_WIDTH, HALF_HEIGHT);
        StdDraw.picture(bottomRight.getX(), bottomRight.getY(), "images/tiles/bottom-right.png", HALF_WIDTH, HALF_HEIGHT);
        break;
      case 2:
        StdDraw.picture(topLeft.getX(), topLeft.getY(), "images/tiles/top.png", HALF_WIDTH, HALF_HEIGHT);
        StdDraw.picture(topRight.getX(), topRight.getY(), "images/tiles/top-right.png", HALF_WIDTH, HALF_HEIGHT);
        StdDraw.picture(bottomLeft.getX(), bottomLeft.getY(), "images/tiles/bottom.png", HALF_WIDTH, HALF_HEIGHT);
        StdDraw.picture(bottomRight.getX(), bottomRight.getY(), "images/tiles/bottom-right.png", HALF_WIDTH, HALF_HEIGHT);
        break;
      case 3:
        StdDraw.picture(topLeft.getX(), topLeft.getY(), "images/tiles/top-left-corner.png", HALF_WIDTH, HALF_HEIGHT);
        StdDraw.picture(topRight.getX(), topRight.getY(), "images/tiles/right.png", HALF_WIDTH, HALF_HEIGHT);
        StdDraw.picture(bottomLeft.getX(), bottomLeft.getY(), "images/tiles/bottom.png", HALF_WIDTH, HALF_HEIGHT);
        StdDraw.picture(bottomRight.getX(), bottomRight.getY(), "images/tiles/bottom-right.png", HALF_WIDTH, HALF_HEIGHT);
        break;
      case 4:
        StdDraw.picture(topLeft.getX(), topLeft.getY(), "images/tiles/top-left.png", HALF_WIDTH, HALF_HEIGHT);
        StdDraw.picture(topRight.getX(), topRight.getY(), "images/tiles/top.png", HALF_WIDTH, HALF_HEIGHT);
        StdDraw.picture(bottomLeft.getX(), bottomLeft.getY(), "images/tiles/bottom-left.png", HALF_WIDTH, HALF_HEIGHT);
        StdDraw.picture(bottomRight.getX(), bottomRight.getY(), "images/tiles/bottom.png", HALF_WIDTH, HALF_HEIGHT);
        break;
      case 5:
        StdDraw.picture(topLeft.getX(), topLeft.getY(), "images/tiles/left.png", HALF_WIDTH, HALF_HEIGHT);
        StdDraw.picture(topRight.getX(), topRight.getY(), "images/tiles/top-right-corner.png", HALF_WIDTH, HALF_HEIGHT);
        StdDraw.picture(bottomLeft.getX(), bottomLeft.getY(), "images/tiles/bottom-left.png", HALF_WIDTH, HALF_HEIGHT);
        StdDraw.picture(bottomRight.getX(), bottomRight.getY(), "images/tiles/bottom.png", HALF_WIDTH, HALF_HEIGHT);
        break;
      case 6:
        StdDraw.picture(topLeft.getX(), topLeft.getY(), "images/tiles/top.png", HALF_WIDTH, HALF_HEIGHT);
        StdDraw.picture(topRight.getX(), topRight.getY(), "images/tiles/top.png", HALF_WIDTH, HALF_HEIGHT);
        StdDraw.picture(bottomLeft.getX(), bottomLeft.getY(), "images/tiles/bottom.png", HALF_WIDTH, HALF_HEIGHT);
        StdDraw.picture(bottomRight.getX(), bottomRight.getY(), "images/tiles/bottom.png", HALF_WIDTH, HALF_HEIGHT);
        break;
      case 7:
        StdDraw.picture(topLeft.getX(), topLeft.getY(), "images/tiles/top-left-corner.png", HALF_WIDTH, HALF_HEIGHT);
        StdDraw.picture(topRight.getX(), topRight.getY(), "images/tiles/top-right-corner.png", HALF_WIDTH, HALF_HEIGHT);
        StdDraw.picture(bottomLeft.getX(), bottomLeft.getY(), "images/tiles/bottom.png", HALF_WIDTH, HALF_HEIGHT);
        StdDraw.picture(bottomRight.getX(), bottomRight.getY(), "images/tiles/bottom.png", HALF_WIDTH, HALF_HEIGHT);
        break;
      case 8:
        StdDraw.picture(topLeft.getX(), topLeft.getY(), "images/tiles/top-left.png", HALF_WIDTH, HALF_HEIGHT);
        StdDraw.picture(topRight.getX(), topRight.getY(), "images/tiles/top-right.png", HALF_WIDTH, HALF_HEIGHT);
        StdDraw.picture(bottomLeft.getX(), bottomLeft.getY(), "images/tiles/left.png", HALF_WIDTH, HALF_HEIGHT);
        StdDraw.picture(bottomRight.getX(), bottomRight.getY(), "images/tiles/right.png", HALF_WIDTH, HALF_HEIGHT);
        break;
      case 9:
        StdDraw.picture(topLeft.getX(), topLeft.getY(), "images/tiles/left.png", HALF_WIDTH, HALF_HEIGHT);
        StdDraw.picture(topRight.getX(), topRight.getY(), "images/tiles/right.png", HALF_WIDTH, HALF_HEIGHT);
        StdDraw.picture(bottomLeft.getX(), bottomLeft.getY(), "images/tiles/left.png", HALF_WIDTH, HALF_HEIGHT);
        StdDraw.picture(bottomRight.getX(), bottomRight.getY(), "images/tiles/right.png", HALF_WIDTH, HALF_HEIGHT);
        break;
      case 10:
        StdDraw.picture(topLeft.getX(), topLeft.getY(), "images/tiles/top.png", HALF_WIDTH, HALF_HEIGHT);
        StdDraw.picture(topRight.getX(), topRight.getY(), "images/tiles/top-right.png", HALF_WIDTH, HALF_HEIGHT);
        StdDraw.picture(bottomLeft.getX(), bottomLeft.getY(), "images/tiles/bottom-left-corner.png", HALF_WIDTH, HALF_HEIGHT);
        StdDraw.picture(bottomRight.getX(), bottomRight.getY(), "images/tiles/right.png", HALF_WIDTH, HALF_HEIGHT);
        break;
      case 11:
        StdDraw.picture(topLeft.getX(), topLeft.getY(), "images/tiles/top-left-corner.png", HALF_WIDTH, HALF_HEIGHT);
        StdDraw.picture(topRight.getX(), topRight.getY(), "images/tiles/right.png", HALF_WIDTH, HALF_HEIGHT);
        StdDraw.picture(bottomLeft.getX(), bottomLeft.getY(), "images/tiles/bottom-left-corner.png", HALF_WIDTH, HALF_HEIGHT);
        StdDraw.picture(bottomRight.getX(), bottomRight.getY(), "images/tiles/right.png", HALF_WIDTH, HALF_HEIGHT);
        break;
      case 12:
        StdDraw.picture(topLeft.getX(), topLeft.getY(), "images/tiles/top-left.png", HALF_WIDTH, HALF_HEIGHT);
        StdDraw.picture(topRight.getX(), topRight.getY(), "images/tiles/top.png", HALF_WIDTH, HALF_HEIGHT);
        StdDraw.picture(bottomLeft.getX(), bottomLeft.getY(), "images/tiles/left.png", HALF_WIDTH, HALF_HEIGHT);
        StdDraw.picture(bottomRight.getX(), bottomRight.getY(), "images/tiles/bottom-right-corner.png", HALF_WIDTH, HALF_HEIGHT);
        break;
      case 13:
        StdDraw.picture(topLeft.getX(), topLeft.getY(), "images/tiles/left.png", HALF_WIDTH, HALF_HEIGHT);
        StdDraw.picture(topRight.getX(), topRight.getY(), "images/tiles/top-right-corner.png", HALF_WIDTH, HALF_HEIGHT);
        StdDraw.picture(bottomLeft.getX(), bottomLeft.getY(), "images/tiles/left.png", HALF_WIDTH, HALF_HEIGHT);
        StdDraw.picture(bottomRight.getX(), bottomRight.getY(), "images/tiles/bottom-right-corner.png", HALF_WIDTH, HALF_HEIGHT);
        break;
      case 14:
        StdDraw.picture(topLeft.getX(), topLeft.getY(), "images/tiles/top.png", HALF_WIDTH, HALF_HEIGHT);
        StdDraw.picture(topRight.getX(), topRight.getY(), "images/tiles/top.png", HALF_WIDTH, HALF_HEIGHT);
        StdDraw.picture(bottomLeft.getX(), bottomLeft.getY(), "images/tiles/bottom-left-corner.png", HALF_WIDTH, HALF_HEIGHT);
        StdDraw.picture(bottomRight.getX(), bottomRight.getY(), "images/tiles/bottom-right-corner.png", HALF_WIDTH, HALF_HEIGHT);
        break;
      case 15:
        StdDraw.picture(topLeft.getX(), topLeft.getY(), "images/tiles/empty.png", HALF_WIDTH, HALF_HEIGHT);
        StdDraw.picture(topRight.getX(), topRight.getY(), "images/tiles/empty.png", HALF_WIDTH, HALF_HEIGHT);
        StdDraw.picture(bottomLeft.getX(), bottomLeft.getY(), "images/tiles/empty.png", HALF_WIDTH, HALF_HEIGHT);
        StdDraw.picture(bottomRight.getX(), bottomRight.getY(), "images/tiles/empty.png", HALF_WIDTH, HALF_HEIGHT);
        break;
    }

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
    return false;
  }
}
