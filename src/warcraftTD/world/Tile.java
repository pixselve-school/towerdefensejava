package warcraftTD.world;

import warcraftTD.libs.StdDraw;
import warcraftTD.particules.EntityParticules;
import warcraftTD.particules.RandomParticuleGenerator;
import warcraftTD.particules.SquareParticule;
import warcraftTD.utils.DrawableEntity;
import warcraftTD.utils.Position;

import java.awt.*;
import java.util.Map;
import java.util.Random;

/**
 * Tuile
 */
abstract public class Tile extends DrawableEntity {
  /**
   * La position de la tuile avec x et y entre 0 et 1
   */
  private final Position position;
  /**
   * La position de la tuile (non normalisée)
   */
  private final Position plainPosition;
  /**
   * La hauteur de la tuile entre 0 et 1
   */
  private final double height;
  /**
   * La largeur de la tuile entre 0 et 1
   */
  private final double width;
  /**
   * Particules de la tuile
   */
  private final EntityParticules tileParticules;
  /**
   * Indique si la tuile est sélectionnée
   */
  private boolean selected;
  /**
   * Indique si la tuile est en mode debug
   */
  private final boolean debug;
  /**
   * Valeur de direction
   */
  private int directionValue;
  /**
   * Entité de la tuile
   */
  private Entity contains;

  /**
   * Créer une tuile
   *
   * @param position      La position de la tuile avec x et y entre 0 et 1
   * @param plainPosition La position de la tuile (non normalisée)
   * @param height        La hauteur de la tuile entre 0 et 1
   * @param width         La largeur de la tuile entre 0 et 1
   */
  public Tile(Position position, Position plainPosition, double height, double width) {
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
    this.plainPosition = plainPosition;
  }

  /**
   * Vérifie si une position est visible
   *
   * @param position Une position
   * @return true si la position est visible
   */
  public static boolean isPositionInView(Position position) {
    return position.getX() >= 0 && position.getY() >= 0 && position.getX() < 1 && position.getY() < 1;
  }

  /**
   * Récupère les particules de la tuile
   *
   * @return Les particules de la tuile
   */
  public EntityParticules getTileParticules() {
    return this.tileParticules;
  }

  /**
   * Actualiser la tuile
   *
   * @param deltaTime Le delta temps du jeu
   */
  public abstract void update(double deltaTime);

  /**
   * Dessiner la partie statique de la tuile
   */
  public abstract void drawStaticPart();

  /**
   * Dessine la partie animée de la tuile
   *
   * @param deltaTime Le delta temps du jeu
   */
  public void drawAnimatedPart(double deltaTime) {
    this.tileParticules.updateGenerators(deltaTime);
  }

  /**
   * Actualise la valeur de direction
   *
   * @param globalTiles Les tuiles du monde
   * @param cascade     Si la vérification doit s'effectuer sur les tuiles adjacentes
   */
  public void updateDirectionValue(Map<Position, Tile> globalTiles, boolean cascade) {
    final Position topPosition = new Position(this.plainPosition.getX(), this.plainPosition.getY() + 1);
    Tile topTile = globalTiles.get(topPosition);
    final Position bottomPosition = new Position(this.plainPosition.getX(), this.plainPosition.getY() - 1);
    Tile bottomTile = globalTiles.get(bottomPosition);
    final Position leftPosition = new Position(this.plainPosition.getX() - 1, this.plainPosition.getY());
    Tile leftTile = globalTiles.get(leftPosition);
    final Position rightPosition = new Position(this.plainPosition.getX() + 1, this.plainPosition.getY());
    Tile rightTile = globalTiles.get(rightPosition);

    this.directionValue = 0;

    if (!isPositionInView(topPosition.getWorldPosition(this.height, this.width)) || topTile != null && topTile.getClass() == this.getClass()) {
      this.directionValue += 1;
      if (cascade && topTile != null) {
        topTile.updateDirectionValue(globalTiles, false);
      }
    }
    if (!isPositionInView(bottomPosition.getWorldPosition(this.height, this.width)) || bottomTile != null && bottomTile.getClass() == this.getClass()) {
      this.directionValue += 8;
      if (cascade && bottomTile != null) {
        bottomTile.updateDirectionValue(globalTiles, false);
      }
    }
    if (!isPositionInView(rightPosition.getWorldPosition(this.height, this.width)) || rightTile != null && rightTile.getClass() == this.getClass()) {
      this.directionValue += 4;
      if (cascade && rightTile != null) {
        rightTile.updateDirectionValue(globalTiles, false);
      }
    }
    if (!isPositionInView(leftPosition.getWorldPosition(this.height, this.width)) || leftTile != null && leftTile.getClass() == this.getClass()) {
      this.directionValue += 2;
      if (cascade && leftTile != null) {
        leftTile.updateDirectionValue(globalTiles, false);
      }
    }
  }

  /**
   * Dessine les paramètres de la tuile
   */
  public void drawSettings() {
    this.drawDebug();
    if (this.selected) {
      this.drawSelected();
    }

  }

  /**
   * Dessine l'affichage debug de la tuile
   */
  public void drawDebug() {
    if (this.debug) {
      this.drawOverlay(new Color(0, 0, 0, (float) 0.5));
      StdDraw.setPenColor(Color.white);
      StdDraw.setFont(new Font("Trebuchet MS", Font.BOLD, 12));
      StdDraw.text(this.getPosition().getX(), this.getPosition().getY(), this.getClass().getSimpleName() + " - " + this.getDirectionValue() + (this.isSelected() ? " - Selected" : ""));
    }
  }

  /**
   * Dessine l'affichage selection de la tuile
   */
  public void drawSelected() {
//    this.drawSelected(Color.darkGray, 0.003);
  }

  /**
   * Sélectionne la tuile
   *
   * @param color  La couleur de la sélection
   * @param radius L'épaisseur du trait de sélection
   */
  public void drawSelected(Color color, double radius) {

    StdDraw.setPenColor(color);
    StdDraw.setPenRadius(radius);
    StdDraw.rectangle(this.getPosition().getX(), this.getPosition().getY(), this.getWidth() / 2, this.getHeight() / 2);

  }

  /**
   * Dessine un overlay sur la tuile
   *
   * @param color La couleur de l'overlay
   */
  public void drawOverlay(Color color) {
    StdDraw.setPenColor(color);
    StdDraw.filledRectangle(this.getPosition().getX(), this.getPosition().getY(), this.getWidth() / 2, this.getHeight() / 2);
  }

  /**
   * Génère de la petite végétation de manière aléatoire
   */
  public void putRandomSmallVegetation() {
    FlowerType[] flowerTypes = new FlowerType[]{FlowerType.RED, FlowerType.BLUE, FlowerType.BUSH, FlowerType.WHITE};
    int rnd = new Random().nextInt(flowerTypes.length);
    if (this.isBuildable()) {
      this.replaceContains(new Flower(flowerTypes[rnd], this));
    }
  }

  /**
   * Ajoute un arbre
   */
  public void putRandomTree() {
    if (this.isBuildable()) {
      this.replaceContains(new Tree());
    }
  }

  /**
   * Met à jour l'entité de la tuile
   *
   * @param deltaTime Le delta temps du jeu
   */
  public void updateContainsEntity(double deltaTime) {
    if (this.contains != null) {
      this.contains.update(deltaTime, this);
      this.contains.getEntityParticules().updateGenerators(deltaTime);
    }
  }


  /**
   * Récupère la position de la tuile
   *
   * @return La position de la tuile
   */
  public Position getPosition() {
    return this.position;
  }

  /**
   * Récupère la hauteur de la tuile
   *
   * @return La hauteur de la tuile
   */
  public double getHeight() {
    return this.height;
  }

  /**
   * Récupère la largeur de la tuile
   *
   * @return La largeur de la tuile
   */
  public double getWidth() {
    return this.width;
  }

  /**
   * Récupère le statut de sélection de la tuile
   *
   * @return true si la tuile est sélectionnée
   */
  public boolean isSelected() {
    return this.selected;
  }

  /**
   * Récupère la valeur de la direction de la tuile
   *
   * @return La valeur de la direction de la tuile
   */
  public int getDirectionValue() {
    return this.directionValue;
  }


  /**
   * Exécuté lorsque la tuile est cliquée
   *
   * @param x la position x de la souris
   * @param y la position y de la souris
   */
  public abstract void onClick(double x, double y);

  /**
   * Exécuté lorsque la tuile est survolée
   *
   * @param x la position x de la souris
   * @param y la position y de la souris
   */
  public void onHover(double x, double y) {
    this.selected = true;
  }

  /**
   * Exécuté lorsque la tuile n'est plus survolée
   */
  public void onHoverLeave() {
    this.selected = false;
  }

  /**
   * Vérifie si l'on peut construire sur la tuile
   *
   * @return true si on peut construire sur la tuile
   */
  public abstract boolean isBuildable();

  /**
   * Récupère l'entité de la tuile
   *
   * @return L'entité de la tuile
   */
  public Entity getContains() {
    return this.contains;
  }

  /**
   * Remplace l'entité de la tuile
   *
   * @param entity la nouvelle entité
   */
  public void replaceContains(Entity entity) {
    this.replaceContains(entity, false, null);
  }

  /**
   * Remplace l'entité de la tuile
   *
   * @param entity        la nouvelle entité
   * @param particules    indique si des particules vont être produite lors de la construction
   * @param colorParticle la couleur des particules
   */
  public void replaceContains(Entity entity, boolean particules, Color colorParticle) {
    if (particules && colorParticle != null) {
      this.tileParticules.addGenerator(new RandomParticuleGenerator(this.getPosition(), 1, 0.01, this.getHeight() / 2, new SquareParticule(1, 0.01, 0.1, colorParticle)));
    }
    this.contains = entity;
    this.contains.setParentTile(this);

  }
}
