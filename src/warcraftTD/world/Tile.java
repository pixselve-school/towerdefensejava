package warcraftTD.world;

import warcraftTD.libs.StdDraw;
import warcraftTD.utils.Position;

abstract public class Tile {
  private final Position position;
  private final double height;
  private final double width;
  private boolean selected;
  private boolean debug;
  private int directionValue;

  /**
   * Create a tile
   *
   * @param position The tile position with x and y between 0 and 1
   * @param height   The tile height between 0 and 1
   * @param width    The tile width between 0 and 1
   */
  public Tile(Position position, double height, double width) {
    if (position.getX() > 1 || position.getX() < 0)
      throw new IllegalArgumentException("Position X should be between 0 and 1");
    if (position.getY() > 1 || position.getY() < 0)
      throw new IllegalArgumentException("Position Y should be between 0 and 1");
    if (height > 1 || height < 0) throw new IllegalArgumentException("Height should be between 0 and 1");
    if (width > 1 || width < 0) throw new IllegalArgumentException("Width should be between 0 and 1");
    this.position = position;
    this.height = height;
    this.width = width;
    this.selected = false;
    this.debug = false;
  }


  /**
   * Update the tile
   *
   * @param deltaTime Game delta time
   */
  public abstract void update(double deltaTime);

  /**
   * Update the tile contains entity if exists
   *
   * @param deltaTime The game delta time
   */
  public abstract void updateContainsEntity(double deltaTime);

  /**
   * Get the tile position
   *
   * @return The tile position
   */
  public Position getPosition() {
    return this.position;
  }

  /**
   * Get the tile height
   *
   * @return The tile height
   */
  public double getHeight() {
    return this.height;
  }

  /**
   * Get the tile width
   *
   * @return The tile width
   */
  public double getWidth() {
    return this.width;
  }

  public boolean isSelected() {
    return this.selected;
  }

  public void setSelected(boolean selected) {
    this.selected = selected;
  }

  public boolean isDebug() {
    return this.debug;
  }

  public void setDebug(boolean debug) {
    this.debug = debug;
  }

  public int getDirectionValue() {
    return this.directionValue;
  }

  public void setDirectionValue(int directionValue) {
    this.directionValue = directionValue;
  }

  /**
   * Check if a tile can be build on
   *
   * @return True if the tile can be build on
   */
  public abstract boolean isBuildable();


  public static void main(String[] args) {
    double deltaTime = 0;
    Tile tile = new Pathway(new Position(0.4, 0.4), 0.1, 0.1);
    Tile tile2 = new Pathway(new Position(0.6, 0.4), 0.1, 0.1);
    Tile tile3 = new Pathway(new Position(0.4, 0.6), 0.1, 0.1);
    Tile tile4 = new Pathway(new Position(0.6, 0.6), 0.1, 0.1);
    tile.setDirectionValue(2);
    tile2.setDirectionValue(2);
    tile3.setDirectionValue(2);
    tile4.setDirectionValue(2);


    StdDraw.setCanvasSize(1200, 800);
    StdDraw.enableDoubleBuffering();

    while (true) {
      long time_nano = System.nanoTime();

      StdDraw.clear();
      tile.update(deltaTime);
      tile2.update(deltaTime);
      tile3.update(deltaTime);
      tile4.update(deltaTime);
      StdDraw.show();
      StdDraw.pause(20);

      int ms = (int) (System.nanoTime() - time_nano) / 1000000;
      int fps = 1000 / ms;
      deltaTime = 1.0 / fps;
    }
  }
}
