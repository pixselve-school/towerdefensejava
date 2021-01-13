package warcraftTD.world;

import warcraftTD.libs.StdDraw;
import warcraftTD.particules.EntityParticules;
import warcraftTD.particules.RandomParticuleGenerator;
import warcraftTD.particules.SquareParticule;
import warcraftTD.utils.DrawableEntity;
import warcraftTD.utils.Position;

import java.awt.*;

abstract public class Tile extends DrawableEntity {
  private final Position position;
  private final double height;
  private final double width;
  private boolean selected;
  private boolean debug;
  private int directionValue;
  private Entity contains;
  private final EntityParticules tileParticules;

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
    this.contains = null;
    this.tileParticules = new EntityParticules();
  }

  public EntityParticules getTileParticules() {
    return this.tileParticules;
  }

  /**
   * Update the tile
   *
   * @param deltaTime Game delta time
   */
  public abstract void update(double deltaTime);


  /**
   * Draw the static part of the tile
   */
  public abstract void drawStaticPart();

  /**
   * Draw the animated part of the tile
   *
   * @param deltaTime The game delta time
   */
  public void drawAnimatedPart(double deltaTime) {
    this.tileParticules.updateGenerators(deltaTime);
  }


  public void drawSettings() {
    this.drawDebug();
    if (this.selected) {
      //this.drawSelected();
    }

  }

  public void drawDebug() {
    if (this.debug) {
      this.drawOverlay(new Color(0, 0, 0, (float) 0.5));
      StdDraw.setPenColor(Color.white);
      StdDraw.setFont(new Font("Trebuchet MS", Font.BOLD, 12));
      StdDraw.text(this.getPosition().getX(), this.getPosition().getY(), this.getClass().getSimpleName() + " - " + this.getDirectionValue() + (this.isSelected() ? " - Selected" : ""));
    }
  }

  public void drawSelected() {

    this.drawSelected(Color.darkGray, 0.003);
  }

  public void drawSelected(Color color, double radius) {

    StdDraw.setPenColor(color);
    StdDraw.setPenRadius(radius);
    StdDraw.rectangle(this.getPosition().getX(), this.getPosition().getY(), this.getWidth() / 2, this.getHeight() / 2);

  }

  public void drawOverlay(Color color) {
    StdDraw.setPenColor(color);
    StdDraw.filledRectangle(this.getPosition().getX(), this.getPosition().getY(), this.getWidth() / 2, this.getHeight() / 2);
  }

  /**
   * Update the tile contains entity if exists
   *
   * @param deltaTime The game delta time
   */
  public void updateContainsEntity(double deltaTime) {
    if (this.contains != null) {
      this.contains.update(deltaTime, this);
      this.contains.getEntityParticules().updateGenerators(deltaTime);
    }
  }


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
   * Executed when a tile is clicked
   */
  public abstract void onClick(double x, double y);

  public void onHover(double x, double y) {
    this.selected = true;
  }

  public void onHoverLeave() {
    this.selected = false;
  }

  /**
   * Check if a tile can be build on
   *
   * @return True if the tile can be build on
   */
  public abstract boolean isBuildable();

  public Entity getContains() {
    return this.contains;
  }

  public void replaceContains(Entity entity) {
    this.replaceContains(entity, false, null);
  }

  public void replaceContains(Entity entity, boolean particules, Color colorParticle) {
    if (particules && colorParticle != null) {
      this.tileParticules.addGenerator(new RandomParticuleGenerator(this.getPosition(), 1, 0.01, this.getHeight() / 2, new SquareParticule(1, 0.01, 0.1, colorParticle)));
    }
    this.contains = entity;
    this.contains.setParentTile(this);

  }
}
