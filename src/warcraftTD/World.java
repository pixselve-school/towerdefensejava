package warcraftTD;

import warcraftTD.hud.InterfaceJeu;
import warcraftTD.libs.StdDraw;
import warcraftTD.monsters.Monster;
import warcraftTD.monsters.Wave;
import warcraftTD.towers.*;
import warcraftTD.utils.Position;
import warcraftTD.utils.Sound;
import warcraftTD.utils.Wallet;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.stream.Collectors;

public class World {
  // l'ensemble des monstres, pour gerer (notamment) l'affichage
  private List<Monster> monsters = new ArrayList<Monster>();
  private int totalMonsterAmount;


  // l'ensemble des cases du chemin
  private List<Position> paths;

  // L'interface du jeu
  private InterfaceJeu HUD;

  // le porte monnaie du joueur
  private Wallet player_wallet;

  private Hashtable<Class, Integer> price_tower;

  private TreeMap<Position, Tower> list_tower;

  private boolean needReleaseMouse = false;

  // le nom de la tour en construction
  private Class building_class = null;

  // représente le temps pour chaque tick en s
  private double delta_time;

  // Position par laquelle les monstres vont venir
  private Position spawn;

  // Information sur la taille du plateau de jeu
  private int width;
  private int height;
  private int nbSquareX;
  private int nbSquareY;
  private double squareWidth;
  private double squareHeight;

  // Nombre de points de vie du joueur
  int life = 20;

  // Commande sur laquelle le joueur appuie (sur le clavier)
  char key;

  // Condition pour terminer la partie
  boolean end = false;

  boolean isMonsterActing = false;

  boolean displayWater;

  List<Wave> waves;

  String musicPath;

  /**
   * Initialisation du monde en fonction de la largeur, la hauteur et le nombre de cases données
   *
   * @param width
   * @param height
   * @param nbSquareX
   * @param nbSquareY
   * @param startSquareX
   * @param startSquareY
   */
  public World(int width, int height, int nbSquareX, int nbSquareY, int startSquareX, int startSquareY) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
    this.width = width;
    this.height = height;
    this.nbSquareX = nbSquareX;
    this.nbSquareY = nbSquareY;
    this.squareWidth = (double) 1 / nbSquareX;
    this.squareHeight = (double) 1 / nbSquareY;
    this.spawn = new Position(startSquareX * this.squareWidth + this.squareWidth / 2, startSquareY * this.squareHeight + this.squareHeight / 2);
    StdDraw.setCanvasSize(width, height);
    StdDraw.enableDoubleBuffering();
    this.delta_time = 0.0;

    this.player_wallet = new Wallet(this);
    this.player_wallet.addMoney(9999);
    this.HUD = new InterfaceJeu(this);

    this.list_tower = new TreeMap<>();

    this.price_tower = new Hashtable<>();
    this.price_tower.put(Arrow.class, 50);
    this.price_tower.put(Bomb.class, 60);
    this.price_tower.put(Ice.class, 70);
    this.price_tower.put(Poison.class, 80);

    // Chemin temporaire
    this.paths.add(new Position(1, 10));
    this.paths.add(new Position(1, 9));
    this.paths.add(new Position(1, 8));
    this.paths.add(new Position(1, 7));
    this.paths.add(new Position(1, 6));
    this.paths.add(new Position(1, 5));
    this.paths.add(new Position(1, 4));
    this.paths.add(new Position(1, 3));
    this.paths.add(new Position(1, 2));
    this.paths.add(new Position(2, 2));
    this.paths.add(new Position(3, 2));
    this.paths.add(new Position(4, 2));
    this.paths.add(new Position(4, 3));
    this.paths.add(new Position(4, 4));
    this.paths.add(new Position(4, 5));
    this.paths.add(new Position(4, 6));
    this.paths.add(new Position(4, 7));
    this.paths.add(new Position(4, 8));
    this.paths.add(new Position(4, 9));
    this.paths.add(new Position(5, 9));
    this.paths.add(new Position(6, 9));
    this.paths.add(new Position(7, 9));
    this.paths.add(new Position(8, 9));
    this.paths.add(new Position(8, 8));
    this.paths.add(new Position(8, 7));
    this.paths.add(new Position(8, 6));
    this.paths.add(new Position(8, 5));
    this.paths.add(new Position(8, 4));
    this.paths.add(new Position(8, 3));
    this.paths.add(new Position(8, 2));
    this.paths.add(new Position(8, 1));
    this.paths.add(new Position(8, 0));
  }

  public World(int nbSquareX, int nbSquareY, int money, int health, boolean displayWater, String musicPath, List<Position> path, List<Wave> waves) {
    this.width = 1200;
    this.height = 800;
    this.nbSquareX = nbSquareX;
    this.nbSquareY = nbSquareY;
    this.squareWidth = (double) 1 / nbSquareX;
    this.squareHeight = (double) 1 / nbSquareY;
    this.spawn = new Position(path.get(0).getX() * this.squareWidth + this.squareWidth / 2, path.get(0).getY() * this.squareHeight + this.squareHeight / 2);
    StdDraw.setCanvasSize(this.width, this.height);
    StdDraw.enableDoubleBuffering();
    this.delta_time = 0.0;
    this.life = health;
    this.displayWater = displayWater;

    this.player_wallet = new Wallet(this);
    this.player_wallet.addMoney(money);
    this.HUD = new InterfaceJeu(this);

    this.list_tower = new TreeMap<>();

    this.price_tower = new Hashtable<>();
    this.price_tower.put(Arrow.class, 50);
    this.price_tower.put(Bomb.class, 60);
    this.price_tower.put(Ice.class, 70);
    this.price_tower.put(Poison.class, 80);
    this.paths = path;
    this.waves = waves;
    this.musicPath = musicPath;
    if (this.waves.size() > 0) {
      this.HUD.setWaveEnemyProgress(100);
      this.totalMonsterAmount = this.waves.get(0).monsterAmount();
    }

  }

  /**
   * Définit le décors du plateau de jeu.
   */
  public void drawBackground() {
    //StdDraw.setPenColor(StdDraw.LIGHT_GREEN);
//    StdDraw.picture(0.5, 0.5, "images/fondtest_complet.jpg", 1.0, 1.0);

    for (int i = 0; i < this.nbSquareX; i++) {
      for (int j = 0; j < this.nbSquareY; j++) {
        StdDraw.picture(i * this.squareWidth + this.squareWidth / 2, j * this.squareHeight + this.squareHeight / 2, "images/tiles/grass.png", this.squareWidth + 0.001, this.squareHeight + 0.001);
      }
    }


  }

  /**
   * Initialise le chemin sur la position du point de départ des monstres. Cette fonction permet d'afficher une route qui sera différente du décors.
   */
  public void drawPath() {
    List<Position> positions = this.paths;
    for (int i = 0, positionsSize = positions.size(); i < positionsSize; i++) {
      Position path = positions.get(i).getWorldPosition(this);

      double coorX = path.getX();
      double coorY = path.getY();
//      Divide the tile by 4 smaller tiles
      Position topLeft = new Position(coorX - this.squareWidth / 4, coorY + this.squareHeight / 4);
      Position topRight = new Position(coorX + this.squareWidth / 4, coorY + this.squareHeight / 4);
      Position bottomLeft = new Position(coorX - this.squareWidth / 4, coorY - this.squareHeight / 4);
      Position bottomRight = new Position(coorX + this.squareWidth / 4, coorY - this.squareHeight / 4);

      if (i - 1 >= 0 && i + 1 < positionsSize) {
        Position previousPath = positions.get(i - 1).getWorldPosition(this);
        Position nextPath = positions.get(i + 1).getWorldPosition(this);

        if (previousPath.getX() == path.getX() && path.getX() == nextPath.getX()) {
//        Aligned vertically
          StdDraw.picture(topLeft.getX(), topLeft.getY(), "images/tiles/left.png", this.squareWidth / 2, this.squareHeight / 2);
          StdDraw.picture(topRight.getX(), topRight.getY(), "images/tiles/right.png", this.squareWidth / 2, this.squareHeight / 2);
          StdDraw.picture(bottomLeft.getX(), bottomLeft.getY(), "images/tiles/left.png", this.squareWidth / 2, this.squareHeight / 2);
          StdDraw.picture(bottomRight.getX(), bottomRight.getY(), "images/tiles/right.png", this.squareWidth / 2, this.squareHeight / 2);
        } else if (previousPath.getY() == path.getY() && path.getY() == nextPath.getY()) {
//        Aligned horizontally
          StdDraw.picture(topLeft.getX(), topLeft.getY(), "images/tiles/top.png", this.squareWidth / 2, this.squareHeight / 2);
          StdDraw.picture(topRight.getX(), topRight.getY(), "images/tiles/top.png", this.squareWidth / 2, this.squareHeight / 2);
          StdDraw.picture(bottomLeft.getX(), bottomLeft.getY(), "images/tiles/bottom.png", this.squareWidth / 2, this.squareHeight / 2);
          StdDraw.picture(bottomRight.getX(), bottomRight.getY(), "images/tiles/bottom.png", this.squareWidth / 2, this.squareHeight / 2);
        } else {
          if (path.getX() < previousPath.getX() && path.getY() > nextPath.getY() || path.getX() < nextPath.getX() && path.getY() > previousPath.getY()) {
              /*
              Corner type:

              * --
              |

               */
            StdDraw.picture(topLeft.getX(), topLeft.getY(), "images/tiles/top-left.png", this.squareWidth / 2, this.squareHeight / 2);
            StdDraw.picture(topRight.getX(), topRight.getY(), "images/tiles/top.png", this.squareWidth / 2, this.squareHeight / 2);
            StdDraw.picture(bottomLeft.getX(), bottomLeft.getY(), "images/tiles/left.png", this.squareWidth / 2, this.squareHeight / 2);
            StdDraw.picture(bottomRight.getX(), bottomRight.getY(), "images/tiles/bottom-right-corner.png", this.squareWidth / 2, this.squareHeight / 2);
          } else if (path.getX() > previousPath.getX() && path.getY() > nextPath.getY() || path.getX() > nextPath.getX() && path.getY() > previousPath.getY()) {
              /*
              Corner type:

              -- *
                 |

               */
            StdDraw.picture(topLeft.getX(), topLeft.getY(), "images/tiles/top.png", this.squareWidth / 2, this.squareHeight / 2);
            StdDraw.picture(topRight.getX(), topRight.getY(), "images/tiles/top-right.png", this.squareWidth / 2, this.squareHeight / 2);
            StdDraw.picture(bottomLeft.getX(), bottomLeft.getY(), "images/tiles/bottom-left-corner.png", this.squareWidth / 2, this.squareHeight / 2);
            StdDraw.picture(bottomRight.getX(), bottomRight.getY(), "images/tiles/right.png", this.squareWidth / 2, this.squareHeight / 2);
          } else if (path.getX() < previousPath.getX() && path.getY() < nextPath.getY() || path.getX() < nextPath.getX() && path.getY() < previousPath.getY()) {
              /*
              Corner type:

              |
              * --

               */
            StdDraw.picture(topLeft.getX(), topLeft.getY(), "images/tiles/left.png", this.squareWidth / 2, this.squareHeight / 2);
            StdDraw.picture(topRight.getX(), topRight.getY(), "images/tiles/top-right-corner.png", this.squareWidth / 2, this.squareHeight / 2);
            StdDraw.picture(bottomLeft.getX(), bottomLeft.getY(), "images/tiles/bottom-left.png", this.squareWidth / 2, this.squareHeight / 2);
            StdDraw.picture(bottomRight.getX(), bottomRight.getY(), "images/tiles/bottom.png", this.squareWidth / 2, this.squareHeight / 2);
          } else {
              /*
              Corner type:

                 |
              -- *

               */
            StdDraw.picture(topLeft.getX(), topLeft.getY(), "images/tiles/top-left-corner.png", this.squareWidth / 2, this.squareHeight / 2);
            StdDraw.picture(topRight.getX(), topRight.getY(), "images/tiles/right.png", this.squareWidth / 2, this.squareHeight / 2);
            StdDraw.picture(bottomLeft.getX(), bottomLeft.getY(), "images/tiles/bottom.png", this.squareWidth / 2, this.squareHeight / 2);
            StdDraw.picture(bottomRight.getX(), bottomRight.getY(), "images/tiles/bottom-right.png", this.squareWidth / 2, this.squareHeight / 2);
          }
        }

      } else {
        Position previousOrNext = null;
        if (i - 1 >= 0) {
          previousOrNext = positions.get(i - 1).getWorldPosition(this);
        } else if (i + 1 < positionsSize) {
          previousOrNext = positions.get(i + 1).getWorldPosition(this);
        }

        if (previousOrNext == null) {
//          Non connected path. Should not happen
          StdDraw.picture(topLeft.getX(), topLeft.getY(), "images/tiles/top-left.png", this.squareWidth / 2, this.squareHeight / 2);
          StdDraw.picture(topRight.getX(), topRight.getY(), "images/tiles/top-right.png", this.squareWidth / 2, this.squareHeight / 2);
          StdDraw.picture(bottomLeft.getX(), bottomLeft.getY(), "images/tiles/bottom-left.png", this.squareWidth / 2, this.squareHeight / 2);
          StdDraw.picture(bottomRight.getX(), bottomRight.getY(), "images/tiles/bottom-right.png", this.squareWidth / 2, this.squareHeight / 2);
        } else {
          if (previousOrNext.getY() == path.getY()) {
            if (previousOrNext.getX() > path.getX()) {
              StdDraw.picture(topLeft.getX(), topLeft.getY(), "images/tiles/top-left.png", this.squareWidth / 2, this.squareHeight / 2);
              StdDraw.picture(topRight.getX(), topRight.getY(), "images/tiles/top.png", this.squareWidth / 2, this.squareHeight / 2);
              StdDraw.picture(bottomLeft.getX(), bottomLeft.getY(), "images/tiles/bottom-left.png", this.squareWidth / 2, this.squareHeight / 2);
              StdDraw.picture(bottomRight.getX(), bottomRight.getY(), "images/tiles/bottom.png", this.squareWidth / 2, this.squareHeight / 2);
            } else {
              StdDraw.picture(topLeft.getX(), topLeft.getY(), "images/tiles/top.png", this.squareWidth / 2, this.squareHeight / 2);
              StdDraw.picture(topRight.getX(), topRight.getY(), "images/tiles/top-right.png", this.squareWidth / 2, this.squareHeight / 2);
              StdDraw.picture(bottomLeft.getX(), bottomLeft.getY(), "images/tiles/bottom.png", this.squareWidth / 2, this.squareHeight / 2);
              StdDraw.picture(bottomRight.getX(), bottomRight.getY(), "images/tiles/bottom-right.png", this.squareWidth / 2, this.squareHeight / 2);
            }
          } else {
            if (previousOrNext.getY() > path.getY()) {
              StdDraw.picture(topLeft.getX(), topLeft.getY(), "images/tiles/left.png", this.squareWidth / 2, this.squareHeight / 2);
              StdDraw.picture(topRight.getX(), topRight.getY(), "images/tiles/right.png", this.squareWidth / 2, this.squareHeight / 2);
              StdDraw.picture(bottomLeft.getX(), bottomLeft.getY(), "images/tiles/bottom-left.png", this.squareWidth / 2, this.squareHeight / 2);
              StdDraw.picture(bottomRight.getX(), bottomRight.getY(), "images/tiles/bottom-right.png", this.squareWidth / 2, this.squareHeight / 2);
            } else {
              StdDraw.picture(topLeft.getX(), topLeft.getY(), "images/tiles/top-left.png", this.squareWidth / 2, this.squareHeight / 2);
              StdDraw.picture(topRight.getX(), topRight.getY(), "images/tiles/top-right.png", this.squareWidth / 2, this.squareHeight / 2);
              StdDraw.picture(bottomLeft.getX(), bottomLeft.getY(), "images/tiles/left.png", this.squareWidth / 2, this.squareHeight / 2);
              StdDraw.picture(bottomRight.getX(), bottomRight.getY(), "images/tiles/right.png", this.squareWidth / 2, this.squareHeight / 2);
            }
          }
        }
      }
    }


  }

  /**
   * Affiche un contour d'eau
   */
  public void drawWater() {
    for (int x = 0; x < this.nbSquareX; x++) {
      for (int y = 0; y < this.nbSquareY; y++) {
        if (y == 0) {
          if (x == 0) {
            StdDraw.picture(x * this.squareWidth + this.squareWidth / 2, y * this.squareHeight + this.squareHeight / 2, "images/tiles/water/bottom-left.png", this.squareWidth + 0.001, this.squareHeight + 0.001);
          } else if (x == this.nbSquareX - 1) {
            StdDraw.picture(x * this.squareWidth + this.squareWidth / 2, y * this.squareHeight + this.squareHeight / 2, "images/tiles/water/bottom-right.png", this.squareWidth + 0.001, this.squareHeight + 0.001);
          } else {
            StdDraw.picture(x * this.squareWidth + this.squareWidth / 2, y * this.squareHeight + this.squareHeight / 2, "images/tiles/water/bottom.png", this.squareWidth + 0.001, this.squareHeight + 0.001);
          }


        } else if (y == this.nbSquareY - 1) {
          if (x == 0) {
            StdDraw.picture(x * this.squareWidth + this.squareWidth / 2, y * this.squareHeight + this.squareHeight / 2, "images/tiles/water/top-left.png", this.squareWidth + 0.001, this.squareHeight + 0.001);
          } else if (x == this.nbSquareX - 1) {
            StdDraw.picture(x * this.squareWidth + this.squareWidth / 2, y * this.squareHeight + this.squareHeight / 2, "images/tiles/water/top-right.png", this.squareWidth + 0.001, this.squareHeight + 0.001);
          } else {
            StdDraw.picture(x * this.squareWidth + this.squareWidth / 2, y * this.squareHeight + this.squareHeight / 2, "images/tiles/water/top.png", this.squareWidth + 0.001, this.squareHeight + 0.001);
          }
        } else {
          if (x == 0) {
            StdDraw.picture(x * this.squareWidth + this.squareWidth / 2, y * this.squareHeight + this.squareHeight / 2, "images/tiles/water/left.png", this.squareWidth + 0.001, this.squareHeight + 0.001);
          } else if (x == this.nbSquareX - 1) {
            StdDraw.picture(x * this.squareWidth + this.squareWidth / 2, y * this.squareHeight + this.squareHeight / 2, "images/tiles/water/right.png", this.squareWidth + 0.001, this.squareHeight + 0.001);
          }
        }

      }

    }
  }

  /**
   * Affiche certaines informations sur l'écran telles que les points de vie du joueur ou son or
   */
  public void drawInfos() {
    this.HUD.updateInterface(StdDraw.mouseX(), StdDraw.mouseY(), this.delta_time);
  }

  /**
   * Fonction qui récupère le positionnement de la souris et permet d'afficher une image de tour en temps réél
   * lorsque le joueur appuie sur une des touches permettant la construction d'une tour.
   */
  public void drawMouse() {
    double normalizedX = (int) (StdDraw.mouseX() / this.squareWidth) * this.squareWidth + this.squareWidth / 2;
    double normalizedY = (int) (StdDraw.mouseY() / this.squareHeight) * this.squareHeight + this.squareHeight / 2;
    if (this.building_class != null) {
      Position mousep = new Position((int) ((normalizedX * this.nbSquareX)), (int) ((normalizedY * this.nbSquareY)));
      if (this.paths.contains(mousep) || this.list_tower.containsKey(mousep)) {
        StdDraw.picture(normalizedX, normalizedY, "images/Select_tile_unavailable.png", this.squareWidth, this.squareHeight);
      } else {
        StdDraw.picture(normalizedX, normalizedY, "images/Select_tile.png", this.squareWidth, this.squareHeight);
      }
    }
  }

  /**
   * Pour chaque monstre de la liste de monstres de la vague, utilise la fonction update() qui appelle les fonctions run() et draw() de Monster.
   * Modifie la position du monstre au cours du temps à l'aide du paramètre nextP.
   */
  public void updateMonsters() {
    Iterator<Monster> i = this.monsters.iterator();
    Monster m;
    while (i.hasNext()) {
      m = i.next();
      m.update(this.delta_time);

      if (m.hasFinishedPath()) {
        this.life -= 1;
        i.remove();
      }
      if (m.isDead()) {
        this.HUD.setWaveEnemyProgress(((100 * this.amountAliveMonsters()) / (double) this.totalMonsterAmount));
      }
      if (m.isReadyToBeRemoved()) {
        i.remove();
        this.player_wallet.addMoney(m.getGoldWhenDead());
      }
    }


  }

  private int amountAliveMonsters() {
    return this.monsters.stream().filter(monster -> !monster.isDead()).collect(Collectors.toCollection(ArrayList::new)).size();
  }

  public void updateTowers() {
    double normalizedX = (int) (StdDraw.mouseX() / this.squareWidth) * this.squareWidth + this.squareWidth / 2;
    double normalizedY = (int) (StdDraw.mouseY() / this.squareHeight) * this.squareHeight + this.squareHeight / 2;
    Position mousep = new Position((int) ((normalizedX * this.nbSquareX)), (int) ((normalizedY * this.nbSquareY)));
    Tower towerUnderMouse = this.list_tower.get(mousep);

    if (this.getHUD().getUpgradingTower() != null) {
      this.getHUD().getUpgradingTower().upgradingVisual();
    }
    if (towerUnderMouse != null) {
      towerUnderMouse.hoveredVisual();
    }

    for (Map.Entry<Position, Tower> entry : this.list_tower.entrySet()) {
      Tower value = entry.getValue();
      value.Update(this.delta_time);
    }
  }


  public void updateWave() {
    if (this.waves.size() > 0) {
      Wave currentWave = this.waves.get(0);
      if (currentWave.getTimeBeforeStartingSpawn() > 0) {
        currentWave.subtractTimeBeforeStartingSpawn(this.delta_time);
      } else if (currentWave.finishedSpawning() && this.monsters.size() <= 0) {
        this.waves.remove(0);
        if (this.waves.size() > 0) {
          this.HUD.setWaveEnemyProgress(100);
          this.totalMonsterAmount = currentWave.monsterAmount();
        }
      } else {
        currentWave.spawn(this, this.delta_time);
      }
    }
  }

  /**
   * Met à jour toutes les informations du plateau de jeu ainsi que les déplacements des monstres et les attaques des tours.
   *
   * @return les points de vie restants du joueur
   */
  public int update() {
    this.drawBackground();
    if (this.displayWater) {
      this.drawWater();
    }
    this.drawPath();
    this.updateWave();
    this.updateMonsters();
    this.updateTowers();
    this.drawMouse();
    this.drawInfos();
    return this.life;
  }


  /**
   * Vérifie lorsque l'utilisateur clique sur sa souris qu'il peut:
   * - Ajouter une tour à la position indiquée par la souris.
   * - Améliorer une tour existante.
   * Puis l'ajouter à la liste des tours
   *
   * @param x
   * @param y
   */
  public void mouseClick(double x, double y, int mouseButton) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
    double normalizedX = (int) (x / this.squareWidth) * this.squareWidth + this.squareWidth / 2;
    double normalizedY = (int) (y / this.squareHeight) * this.squareHeight + this.squareHeight / 2;
    Position p = new Position(normalizedX, normalizedY);
    Position mousep = new Position((int) ((normalizedX * this.nbSquareX)), (int) ((normalizedY * this.nbSquareY)));

    if (this.HUD.onClick(x, y, mouseButton)) return;

    if (this.building_class != null && !this.needReleaseMouse) {
      if (!(this.paths.contains(mousep) || this.list_tower.containsKey(mousep))) {
        int price = this.price_tower.get(this.building_class);
        if (this.player_wallet.pay(price)) {
          try {
            Constructor cons = this.building_class.getConstructor(Position.class, double.class, double.class, World.class);
            Tower t = (Tower) cons.newInstance(new Position(normalizedX, normalizedY), this.squareWidth, this.squareHeight, this);
            this.list_tower.put(new Position((int) ((normalizedX * this.nbSquareX)), (int) ((normalizedY * this.nbSquareY))), t);
            Sound soundTower = new Sound("music/putTower.wav", false);
            soundTower.play(0.5);
          } catch (NoSuchMethodException e) {
            e.printStackTrace();
          } catch (IllegalAccessException e) {
            e.printStackTrace();
          } catch (InstantiationException e) {
            e.printStackTrace();
          } catch (InvocationTargetException e) {

            e.printStackTrace();
          }
        }
      }
    } else if (!this.needReleaseMouse) {
      Tower towerUnderMouse = this.list_tower.get(mousep);
      if (towerUnderMouse != null) {
        this.HUD.showUpgradeTowerBox(towerUnderMouse);
      }
    }
  }

  public void startBuilding(Class c) {
    this.building_class = c;
    this.needReleaseMouse = true;
  }

  public void stopBuilding() {
    this.building_class = null;
    this.needReleaseMouse = true;
  }

  /**
   * Récupère la touche entrée au clavier ainsi que la position de la souris et met à jour le plateau en fonction de ces interractions
   */
  public void run() {

    try {
      Sound gameMusic = new Sound(this.musicPath, true);
      gameMusic.play(0.25);
    } catch (LineUnavailableException | IOException | UnsupportedAudioFileException e) {
      e.printStackTrace();
    }

    while (!this.end) {
      long time_nano = System.nanoTime();
      StdDraw.clear();

      if (StdDraw.isMousePressed()) {
        if (!this.needReleaseMouse) {
          this.mouseClick(StdDraw.mouseX(), StdDraw.mouseY(), StdDraw.mouseButtonPressed());
        }
      } else if (this.needReleaseMouse) {
        this.needReleaseMouse = false;
      }

      this.update();
      StdDraw.show();


      int ms = (int) (System.nanoTime() - time_nano) / 1000000;
      int fps = 1000 / ms;
      this.delta_time = 1.0 / fps;


    }
  }

  public List<Monster> getMonsters() {
    return this.monsters;
  }

  public void setMonsters(List<Monster> monsters) {
    this.monsters = monsters;
  }

  public int getTotalMonsterAmount() {
    return this.totalMonsterAmount;
  }

  public void setTotalMonsterAmount(int totalMonsterAmount) {
    this.totalMonsterAmount = totalMonsterAmount;
  }

  public List<Position> getPaths() {
    return this.paths;
  }

  public void setPaths(List<Position> paths) {
    this.paths = paths;
  }

  public InterfaceJeu getHUD() {
    return this.HUD;
  }

  public void setHUD(InterfaceJeu HUD) {
    this.HUD = HUD;
  }

  public Wallet getPlayer_wallet() {
    return this.player_wallet;
  }

  public void setPlayer_wallet(Wallet player_wallet) {
    this.player_wallet = player_wallet;
  }

  public Hashtable<Class, Integer> getPrice_tower() {
    return this.price_tower;
  }

  public void setPrice_tower(Hashtable<Class, Integer> price_tower) {
    this.price_tower = price_tower;
  }

  public TreeMap<Position, Tower> getList_tower() {
    return this.list_tower;
  }

  public void setList_tower(TreeMap<Position, Tower> list_tower) {
    this.list_tower = list_tower;
  }

  public boolean isNeedReleaseMouse() {
    return this.needReleaseMouse;
  }

  public void setNeedReleaseMouse(boolean needReleaseMouse) {
    this.needReleaseMouse = needReleaseMouse;
  }

  public Class getBuilding_class() {
    return this.building_class;
  }

  public void setBuilding_class(Class building_class) {
    this.building_class = building_class;
  }

  public double getDelta_time() {
    return this.delta_time;
  }

  public void setDelta_time(double delta_time) {
    this.delta_time = delta_time;
  }

  public Position getSpawn() {
    return this.spawn;
  }

  public void setSpawn(Position spawn) {
    this.spawn = spawn;
  }

  public int getWidth() {
    return this.width;
  }

  public void setWidth(int width) {
    this.width = width;
  }

  public int getHeight() {
    return this.height;
  }

  public void setHeight(int height) {
    this.height = height;
  }

  public int getNbSquareX() {
    return this.nbSquareX;
  }

  public void setNbSquareX(int nbSquareX) {
    this.nbSquareX = nbSquareX;
  }

  public int getNbSquareY() {
    return this.nbSquareY;
  }

  public void setNbSquareY(int nbSquareY) {
    this.nbSquareY = nbSquareY;
  }

  public double getSquareWidth() {
    return this.squareWidth;
  }

  public void setSquareWidth(double squareWidth) {
    this.squareWidth = squareWidth;
  }

  public double getSquareHeight() {
    return this.squareHeight;
  }

  public void setSquareHeight(double squareHeight) {
    this.squareHeight = squareHeight;
  }

  public int getLife() {
    return this.life;
  }

  public void setLife(int life) {
    this.life = life;
  }

  public char getKey() {
    return this.key;
  }

  public void setKey(char key) {
    this.key = key;
  }

  public boolean isEnd() {
    return this.end;
  }

  public void setEnd(boolean end) {
    this.end = end;
  }

  public boolean isMonsterActing() {
    return this.isMonsterActing;
  }

  public void setMonsterActing(boolean monsterActing) {
    this.isMonsterActing = monsterActing;
  }


  public void addMonster(Monster monster) {
    this.monsters.add(monster);
  }
}
