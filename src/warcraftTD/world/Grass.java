package warcraftTD.world;

import warcraftTD.libs.StdDraw;
import warcraftTD.utils.Position;

import java.awt.*;

public class Grass extends Tile {

  /**
   * Create a grass tile
   *
   * @param position The tile position
   * @param height   The tile height
   * @param width    The tile width
   */
  public Grass(Position position, double height, double width) {
    super(position, height, width);
  }

  /**
   * Update the tile
   *
   * @param deltaTime Game delta time
   */
  public void update(double deltaTime) {
    StdDraw.picture(this.getPosition().getX(), this.getPosition().getY(), "images/tiles/grass.png", this.getWidth(), this.getHeight());


    if (this.isDebug()) {
      StdDraw.setPenColor(new Color(0, 0, 0, (float) 0.5));
      StdDraw.filledRectangle(this.getPosition().getX(), this.getPosition().getY(), this.getWidth() / 2, this.getHeight() / 2);
      StdDraw.setPenColor(Color.white);
      StdDraw.setFont(new Font("Trebuchet MS", Font.BOLD, 12));
      StdDraw.text(this.getPosition().getX(), this.getPosition().getY(), "Grass" + (this.isSelected() ? " - Selected" : ""));
    }
    if (this.isSelected()) {
      StdDraw.setPenColor(Color.red);
      StdDraw.setPenRadius(0.005);
      StdDraw.rectangle(this.getPosition().getX(), this.getPosition().getY(), this.getWidth() / 2, this.getHeight() / 2);
    }
  }


  /**
   * Executed when a tile is clicked
   */
  public void onClick(double x, double y) {

  }

  /**
   * Check if a tile can be build on
   *
   * @return True if the tile can be build on
   */
  public boolean isBuildable() {
    return this.getContains() == null;
  }
}
