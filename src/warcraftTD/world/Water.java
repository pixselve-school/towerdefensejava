package warcraftTD.world;

import warcraftTD.libs.StdDraw;
import warcraftTD.particules.CircleParticule;
import warcraftTD.particules.EntityParticules;
import warcraftTD.particules.RandomParticuleGenerator;
import warcraftTD.utils.Position;
import warcraftTD.utils.Sound;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.awt.*;
import java.io.IOException;

/**
 * Tuile d'eau
 */
public class Water extends Tile {
  /**
   * Son produit un fois cliqué
   */
  Sound clickSound;


  /**
   * Créer une tuile d'eau
   *
   * @param position La position de la tuile
   * @param height   La hauteur de la tuile
   * @param width    La largeur de la tuile
   */
  public Water(Position position, double height, double width) {
    super(new Position(position.getX() * width + width / 2, position.getY() * height + height / 2), position, height, width);
    try {
      this.clickSound = new Sound("music/water-splatch.wav", false);
    } catch (LineUnavailableException | IOException | UnsupportedAudioFileException e) {
      e.printStackTrace();
    }

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
        StdDraw.picture(topLeft.getX(), topLeft.getY(), "images/tiles/water/top-left.png", HALF_WIDTH, HALF_HEIGHT);
        StdDraw.picture(topRight.getX(), topRight.getY(), "images/tiles/water/top-right.png", HALF_WIDTH, HALF_HEIGHT);
        StdDraw.picture(bottomLeft.getX(), bottomLeft.getY(), "images/tiles/water/bottom-left.png", HALF_WIDTH, HALF_HEIGHT);
        StdDraw.picture(bottomRight.getX(), bottomRight.getY(), "images/tiles/water/bottom-right.png", HALF_WIDTH, HALF_HEIGHT);
        break;
      case 1:
        StdDraw.picture(topLeft.getX(), topLeft.getY(), "images/tiles/water/left.png", HALF_WIDTH, HALF_HEIGHT);
        StdDraw.picture(topRight.getX(), topRight.getY(), "images/tiles/water/right.png", HALF_WIDTH, HALF_HEIGHT);
        StdDraw.picture(bottomLeft.getX(), bottomLeft.getY(), "images/tiles/water/bottom-left.png", HALF_WIDTH, HALF_HEIGHT);
        StdDraw.picture(bottomRight.getX(), bottomRight.getY(), "images/tiles/water/bottom-right.png", HALF_WIDTH, HALF_HEIGHT);
        break;
      case 2:
        StdDraw.picture(topLeft.getX(), topLeft.getY(), "images/tiles/water/top.png", HALF_WIDTH, HALF_HEIGHT);
        StdDraw.picture(topRight.getX(), topRight.getY(), "images/tiles/water/top-right.png", HALF_WIDTH, HALF_HEIGHT);
        StdDraw.picture(bottomLeft.getX(), bottomLeft.getY(), "images/tiles/water/bottom.png", HALF_WIDTH, HALF_HEIGHT);
        StdDraw.picture(bottomRight.getX(), bottomRight.getY(), "images/tiles/water/bottom-right.png", HALF_WIDTH, HALF_HEIGHT);
        break;
      case 3:
        StdDraw.picture(topLeft.getX(), topLeft.getY(), "images/tiles/water/top-left-corner.png", HALF_WIDTH, HALF_HEIGHT);
        StdDraw.picture(topRight.getX(), topRight.getY(), "images/tiles/water/right.png", HALF_WIDTH, HALF_HEIGHT);
        StdDraw.picture(bottomLeft.getX(), bottomLeft.getY(), "images/tiles/water/bottom.png", HALF_WIDTH, HALF_HEIGHT);
        StdDraw.picture(bottomRight.getX(), bottomRight.getY(), "images/tiles/water/bottom-right.png", HALF_WIDTH, HALF_HEIGHT);
        break;
      case 4:
        StdDraw.picture(topLeft.getX(), topLeft.getY(), "images/tiles/water/top-left.png", HALF_WIDTH, HALF_HEIGHT);
        StdDraw.picture(topRight.getX(), topRight.getY(), "images/tiles/water/top.png", HALF_WIDTH, HALF_HEIGHT);
        StdDraw.picture(bottomLeft.getX(), bottomLeft.getY(), "images/tiles/water/bottom-left.png", HALF_WIDTH, HALF_HEIGHT);
        StdDraw.picture(bottomRight.getX(), bottomRight.getY(), "images/tiles/water/bottom.png", HALF_WIDTH, HALF_HEIGHT);
        break;
      case 5:
        StdDraw.picture(topLeft.getX(), topLeft.getY(), "images/tiles/water/left.png", HALF_WIDTH, HALF_HEIGHT);
        StdDraw.picture(topRight.getX(), topRight.getY(), "images/tiles/water/top-right-corner.png", HALF_WIDTH, HALF_HEIGHT);
        StdDraw.picture(bottomLeft.getX(), bottomLeft.getY(), "images/tiles/water/bottom-left.png", HALF_WIDTH, HALF_HEIGHT);
        StdDraw.picture(bottomRight.getX(), bottomRight.getY(), "images/tiles/water/bottom.png", HALF_WIDTH, HALF_HEIGHT);
        break;
      case 6:
        StdDraw.picture(topLeft.getX(), topLeft.getY(), "images/tiles/water/top.png", HALF_WIDTH, HALF_HEIGHT);
        StdDraw.picture(topRight.getX(), topRight.getY(), "images/tiles/water/top.png", HALF_WIDTH, HALF_HEIGHT);
        StdDraw.picture(bottomLeft.getX(), bottomLeft.getY(), "images/tiles/water/bottom.png", HALF_WIDTH, HALF_HEIGHT);
        StdDraw.picture(bottomRight.getX(), bottomRight.getY(), "images/tiles/water/bottom.png", HALF_WIDTH, HALF_HEIGHT);
        break;
      case 7:
        StdDraw.picture(topLeft.getX(), topLeft.getY(), "images/tiles/water/top-left-corner.png", HALF_WIDTH, HALF_HEIGHT);
        StdDraw.picture(topRight.getX(), topRight.getY(), "images/tiles/water/top-right-corner.png", HALF_WIDTH, HALF_HEIGHT);
        StdDraw.picture(bottomLeft.getX(), bottomLeft.getY(), "images/tiles/water/bottom.png", HALF_WIDTH, HALF_HEIGHT);
        StdDraw.picture(bottomRight.getX(), bottomRight.getY(), "images/tiles/water/bottom.png", HALF_WIDTH, HALF_HEIGHT);
        break;
      case 8:
        StdDraw.picture(topLeft.getX(), topLeft.getY(), "images/tiles/water/top-left.png", HALF_WIDTH, HALF_HEIGHT);
        StdDraw.picture(topRight.getX(), topRight.getY(), "images/tiles/water/top-right.png", HALF_WIDTH, HALF_HEIGHT);
        StdDraw.picture(bottomLeft.getX(), bottomLeft.getY(), "images/tiles/water/left.png", HALF_WIDTH, HALF_HEIGHT);
        StdDraw.picture(bottomRight.getX(), bottomRight.getY(), "images/tiles/water/right.png", HALF_WIDTH, HALF_HEIGHT);
        break;
      case 9:
        StdDraw.picture(topLeft.getX(), topLeft.getY(), "images/tiles/water/left.png", HALF_WIDTH, HALF_HEIGHT);
        StdDraw.picture(topRight.getX(), topRight.getY(), "images/tiles/water/right.png", HALF_WIDTH, HALF_HEIGHT);
        StdDraw.picture(bottomLeft.getX(), bottomLeft.getY(), "images/tiles/water/left.png", HALF_WIDTH, HALF_HEIGHT);
        StdDraw.picture(bottomRight.getX(), bottomRight.getY(), "images/tiles/water/right.png", HALF_WIDTH, HALF_HEIGHT);
        break;
      case 10:
        StdDraw.picture(topLeft.getX(), topLeft.getY(), "images/tiles/water/top.png", HALF_WIDTH, HALF_HEIGHT);
        StdDraw.picture(topRight.getX(), topRight.getY(), "images/tiles/water/top-right.png", HALF_WIDTH, HALF_HEIGHT);
        StdDraw.picture(bottomLeft.getX(), bottomLeft.getY(), "images/tiles/water/bottom-left-corner.png", HALF_WIDTH, HALF_HEIGHT);
        StdDraw.picture(bottomRight.getX(), bottomRight.getY(), "images/tiles/water/right.png", HALF_WIDTH, HALF_HEIGHT);
        break;
      case 11:
        StdDraw.picture(topLeft.getX(), topLeft.getY(), "images/tiles/water/top-left-corner.png", HALF_WIDTH, HALF_HEIGHT);
        StdDraw.picture(topRight.getX(), topRight.getY(), "images/tiles/water/right.png", HALF_WIDTH, HALF_HEIGHT);
        StdDraw.picture(bottomLeft.getX(), bottomLeft.getY(), "images/tiles/water/bottom-left-corner.png", HALF_WIDTH, HALF_HEIGHT);
        StdDraw.picture(bottomRight.getX(), bottomRight.getY(), "images/tiles/water/right.png", HALF_WIDTH, HALF_HEIGHT);
        break;
      case 12:
        StdDraw.picture(topLeft.getX(), topLeft.getY(), "images/tiles/water/top-left.png", HALF_WIDTH, HALF_HEIGHT);
        StdDraw.picture(topRight.getX(), topRight.getY(), "images/tiles/water/top.png", HALF_WIDTH, HALF_HEIGHT);
        StdDraw.picture(bottomLeft.getX(), bottomLeft.getY(), "images/tiles/water/left.png", HALF_WIDTH, HALF_HEIGHT);
        StdDraw.picture(bottomRight.getX(), bottomRight.getY(), "images/tiles/water/bottom-right-corner.png", HALF_WIDTH, HALF_HEIGHT);
        break;
      case 13:
        StdDraw.picture(topLeft.getX(), topLeft.getY(), "images/tiles/water/left.png", HALF_WIDTH, HALF_HEIGHT);
        StdDraw.picture(topRight.getX(), topRight.getY(), "images/tiles/water/top-right-corner.png", HALF_WIDTH, HALF_HEIGHT);
        StdDraw.picture(bottomLeft.getX(), bottomLeft.getY(), "images/tiles/water/left.png", HALF_WIDTH, HALF_HEIGHT);
        StdDraw.picture(bottomRight.getX(), bottomRight.getY(), "images/tiles/water/bottom-right-corner.png", HALF_WIDTH, HALF_HEIGHT);
        break;
      case 14:
        StdDraw.picture(topLeft.getX(), topLeft.getY(), "images/tiles/water/top.png", HALF_WIDTH, HALF_HEIGHT);
        StdDraw.picture(topRight.getX(), topRight.getY(), "images/tiles/water/top.png", HALF_WIDTH, HALF_HEIGHT);
        StdDraw.picture(bottomLeft.getX(), bottomLeft.getY(), "images/tiles/water/bottom-left-corner.png", HALF_WIDTH, HALF_HEIGHT);
        StdDraw.picture(bottomRight.getX(), bottomRight.getY(), "images/tiles/water/bottom-right-corner.png", HALF_WIDTH, HALF_HEIGHT);
        break;
      case 15:
        StdDraw.picture(topLeft.getX(), topLeft.getY(), "images/tiles/water/empty.png", HALF_WIDTH, HALF_HEIGHT);
        StdDraw.picture(topRight.getX(), topRight.getY(), "images/tiles/water/empty.png", HALF_WIDTH, HALF_HEIGHT);
        StdDraw.picture(bottomLeft.getX(), bottomLeft.getY(), "images/tiles/water/empty.png", HALF_WIDTH, HALF_HEIGHT);
        StdDraw.picture(bottomRight.getX(), bottomRight.getY(), "images/tiles/water/empty.png", HALF_WIDTH, HALF_HEIGHT);
        break;
    }
  }


  /**
   * Exécuté lorsque la tuile est cliquée
   * @param x la position x de la souris
   * @param y la position y de la souris
   */
  public void onClick(double x, double y) {
    try {
      this.getTileParticules().addGenerator(new RandomParticuleGenerator(new Position(x, y), 0.5, 0.05, 0.01, new CircleParticule(1, 0.01, 0.05, new Color(64, 130, 189))));
      this.clickSound.play(0.05);
    } catch (LineUnavailableException | IOException | UnsupportedAudioFileException e) {
      e.printStackTrace();
    }
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
