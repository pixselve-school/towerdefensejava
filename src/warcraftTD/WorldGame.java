package warcraftTD;

import warcraftTD.hud.InterfaceEndGame;
import warcraftTD.hud.InterfaceGame;
import warcraftTD.hud.InterfacePause;
import warcraftTD.hud.MainMenu;
import warcraftTD.libs.StdDraw;
import warcraftTD.monsters.Monster;
import warcraftTD.monsters.Wave;
import warcraftTD.towers.*;
import warcraftTD.utils.Position;
import warcraftTD.utils.Sound;
import warcraftTD.utils.TowerDataStruct;
import warcraftTD.utils.Wallet;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.awt.*;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class WorldGame extends World {
  // l'ensemble des monstres, pour gerer (notamment) l'affichage
  private List<Monster> monsters = new ArrayList<Monster>();
  private int totalMonsterAmount;

  private boolean needKeyRelease;
  private boolean pause;
  // l'ensemble des cases du chemin
  private List<Position> paths;

  private Image pauseBackground;
  private Image levelBackground;

  // L'interface du jeu
  private InterfaceGame HUD;
  private InterfacePause hudPause;
  private InterfaceEndGame hudEnd;

  // le porte monnaie du joueur
  private Wallet player_wallet;

  private ArrayList<TowerDataStruct> listTowerData;

  private TreeMap<Position, Tower> list_tower;

  // le nom de la tour en construction
  private Class building_class = null;

  // Position par laquelle les monstres vont venir
  private Position spawn;

  // Nombre de points de vie du joueur
  int life = 20;

  // Commande sur laquelle le joueur appuie (sur le clavier)
  char key;

  boolean isMonsterActing = false;

  boolean displayWater;

  List<Wave> waves;

  String musicPath;

  private StateGame currentStateGame;

  private enum StateGame{
    Game, Pause, End
  }

  public WorldGame(int nbSquareX, int nbSquareY, int money, int health, boolean displayWater, String musicPath, List<Position> path, List<Wave> waves, MainMenu menu) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
    super(1200, 800, menu);
    this.setNbSquareX(nbSquareX);
    this.setNbSquareY(nbSquareY);
    this.setSquareWidth((double) 1.006 / nbSquareX);
    this.setSquareHeight((double) 1.006 / nbSquareY);
    this.spawn = new Position(path.get(0).getX() * this.getSquareWidth() + this.getSquareWidth() / 2, path.get(0).getY() * this.getSquareHeight() + this.getSquareHeight() / 2);

    this.life = health;
    this.displayWater = displayWater;

    this.player_wallet = new Wallet(this);
    this.player_wallet.addMoney(money);

    this.listTowerData = new ArrayList<TowerDataStruct>();
    this.listTowerData.add(new TowerDataStruct("images/shop_arrowtower.png", "images/shop_arrowtower_hover.png", "turret_arrow", 50, Arrow.class));
    this.listTowerData.add(new TowerDataStruct("images/shop_bombtower.png", "images/shop_bombtower_hover.png", "turret_bomb", 60, Bomb.class));
    this.listTowerData.add(new TowerDataStruct("images/shop_icetower.png", "images/shop_icetower_hover.png", "turret_ice", 70, Ice.class));
    this.listTowerData.add(new TowerDataStruct("images/shop_poisontower.png", "images/shop_poisontower_hover.png", "turret_poison", 80, Poison.class));

    this.HUD = new InterfaceGame(this, this.listTowerData);

    this.list_tower = new TreeMap<>();

    this.paths = path;

    this.waves = waves;
    this.musicPath = musicPath;
    if (this.waves.size() > 0) {
      this.HUD.setWaveEnemyProgress(100);
      this.totalMonsterAmount = this.waves.get(0).monsterAmount();
    }

    this.needKeyRelease = false;
    this.pause = false;
    this.initTerrain();
    this.currentStateGame = StateGame.Game;
  }

  public void initTerrain(){
    this.drawBackground();
    if (this.displayWater) {
      this.drawWater();
    }
    this.drawPath();
    StdDraw.show();
    StdDraw.save("images/currentlevel.png");
    this.levelBackground = StdDraw.pictureNget(0.5,0.5,"images/currentlevel.png",1.0,1.0);
  }

  /**
   * Définit le décors du plateau de jeu.
   */
  @Override
  public void drawBackground() {
    //StdDraw.setPenColor(StdDraw.LIGHT_GREEN);
//    StdDraw.picture(0.5, 0.5, "images/fondtest_complet.jpg", 1.0, 1.0);

    for (int i = 0; i < this.getNbSquareX(); i++) {
      for (int j = 0; j < this.getNbSquareY(); j++) {
        StdDraw.picture(i * this.getSquareWidth() + this.getSquareWidth() / 2, j * this.getSquareHeight() + this.getSquareHeight() / 2, "images/tiles/grass.png", this.getSquareWidth() + 0.001, this.getSquareHeight() + 0.001);
      }
    }


  }

  /**
   * Initialise le chemin sur la position du point de départ des monstres. Cette fonction permet d'afficher une route qui sera différente du décors.
   */
  @Override
  public void drawPath() {
    List<Position> positions = this.paths;
    for (int i = 0, positionsSize = positions.size(); i < positionsSize; i++) {
      Position path = positions.get(i).getWorldPosition(this);

      double coorX = path.getX();
      double coorY = path.getY();
//      Divide the tile by 4 smaller tiles
      Position topLeft = new Position(coorX - this.getSquareWidth() / 4, coorY + this.getSquareHeight() / 4);
      Position topRight = new Position(coorX + this.getSquareWidth() / 4, coorY + this.getSquareHeight() / 4);
      Position bottomLeft = new Position(coorX - this.getSquareWidth() / 4, coorY - this.getSquareHeight() / 4);
      Position bottomRight = new Position(coorX + this.getSquareWidth() / 4, coorY - this.getSquareHeight() / 4);

      if (i - 1 >= 0 && i + 1 < positionsSize) {
        Position previousPath = positions.get(i - 1).getWorldPosition(this);
        Position nextPath = positions.get(i + 1).getWorldPosition(this);

        if (previousPath.getX() == path.getX() && path.getX() == nextPath.getX()) {
//        Aligned vertically
          StdDraw.picture(topLeft.getX(), topLeft.getY(), "images/tiles/left.png", this.getSquareWidth() / 2 , this.getSquareHeight() / 2);
          StdDraw.picture(topRight.getX(), topRight.getY(), "images/tiles/right.png", this.getSquareWidth() / 2, this.getSquareHeight() / 2);
          StdDraw.picture(bottomLeft.getX(), bottomLeft.getY(), "images/tiles/left.png", this.getSquareWidth() / 2, this.getSquareHeight() / 2);
          StdDraw.picture(bottomRight.getX(), bottomRight.getY(), "images/tiles/right.png", this.getSquareWidth() / 2, this.getSquareHeight() / 2);
        } else if (previousPath.getY() == path.getY() && path.getY() == nextPath.getY()) {
//        Aligned horizontally
          StdDraw.picture(topLeft.getX(), topLeft.getY(), "images/tiles/top.png", this.getSquareWidth() / 2, this.getSquareHeight() / 2);
          StdDraw.picture(topRight.getX(), topRight.getY(), "images/tiles/top.png", this.getSquareWidth() / 2, this.getSquareHeight() / 2);
          StdDraw.picture(bottomLeft.getX(), bottomLeft.getY(), "images/tiles/bottom.png", this.getSquareWidth() / 2, this.getSquareHeight() / 2);
          StdDraw.picture(bottomRight.getX(), bottomRight.getY(), "images/tiles/bottom.png", this.getSquareWidth() / 2, this.getSquareHeight() / 2);
        } else {
          if (path.getX() < previousPath.getX() && path.getY() > nextPath.getY() || path.getX() < nextPath.getX() && path.getY() > previousPath.getY()) {
              /*
              Corner type:

              * --
              |

               */
            StdDraw.picture(topLeft.getX(), topLeft.getY(), "images/tiles/top-left.png", this.getSquareWidth() / 2, this.getSquareHeight() / 2);
            StdDraw.picture(topRight.getX(), topRight.getY(), "images/tiles/top.png", this.getSquareWidth() / 2, this.getSquareHeight() / 2);
            StdDraw.picture(bottomLeft.getX(), bottomLeft.getY(), "images/tiles/left.png", this.getSquareWidth() / 2, this.getSquareHeight() / 2);
            StdDraw.picture(bottomRight.getX(), bottomRight.getY(), "images/tiles/bottom-right-corner.png", this.getSquareWidth() / 2, this.getSquareHeight() / 2);
          } else if (path.getX() > previousPath.getX() && path.getY() > nextPath.getY() || path.getX() > nextPath.getX() && path.getY() > previousPath.getY()) {
              /*
              Corner type:

              -- *
                 |

               */
            StdDraw.picture(topLeft.getX(), topLeft.getY(), "images/tiles/top.png", this.getSquareWidth() / 2, this.getSquareHeight() / 2);
            StdDraw.picture(topRight.getX(), topRight.getY(), "images/tiles/top-right.png", this.getSquareWidth() / 2, this.getSquareHeight() / 2);
            StdDraw.picture(bottomLeft.getX(), bottomLeft.getY(), "images/tiles/bottom-left-corner.png", this.getSquareWidth() / 2, this.getSquareHeight() / 2);
            StdDraw.picture(bottomRight.getX(), bottomRight.getY(), "images/tiles/right.png", this.getSquareWidth() / 2, this.getSquareHeight() / 2);
          } else if (path.getX() < previousPath.getX() && path.getY() < nextPath.getY() || path.getX() < nextPath.getX() && path.getY() < previousPath.getY()) {
              /*
              Corner type:

              |
              * --

               */
            StdDraw.picture(topLeft.getX(), topLeft.getY(), "images/tiles/left.png", this.getSquareWidth() / 2, this.getSquareHeight() / 2);
            StdDraw.picture(topRight.getX(), topRight.getY(), "images/tiles/top-right-corner.png", this.getSquareWidth() / 2, this.getSquareHeight() / 2);
            StdDraw.picture(bottomLeft.getX(), bottomLeft.getY(), "images/tiles/bottom-left.png", this.getSquareWidth() / 2, this.getSquareHeight() / 2);
            StdDraw.picture(bottomRight.getX(), bottomRight.getY(), "images/tiles/bottom.png", this.getSquareWidth() / 2, this.getSquareHeight() / 2);
          } else {
              /*
              Corner type:

                 |
              -- *

               */
            StdDraw.picture(topLeft.getX(), topLeft.getY(), "images/tiles/top-left-corner.png", this.getSquareWidth() / 2, this.getSquareHeight() / 2);
            StdDraw.picture(topRight.getX(), topRight.getY(), "images/tiles/right.png", this.getSquareWidth() / 2, this.getSquareHeight() / 2);
            StdDraw.picture(bottomLeft.getX(), bottomLeft.getY(), "images/tiles/bottom.png", this.getSquareWidth() / 2, this.getSquareHeight() / 2);
            StdDraw.picture(bottomRight.getX(), bottomRight.getY(), "images/tiles/bottom-right.png", this.getSquareWidth() / 2, this.getSquareHeight() / 2);
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
          StdDraw.picture(topLeft.getX(), topLeft.getY(), "images/tiles/top-left.png", this.getSquareWidth() / 2, this.getSquareHeight() / 2);
          StdDraw.picture(topRight.getX(), topRight.getY(), "images/tiles/top-right.png", this.getSquareWidth() / 2, this.getSquareHeight() / 2);
          StdDraw.picture(bottomLeft.getX(), bottomLeft.getY(), "images/tiles/bottom-left.png", this.getSquareWidth() / 2, this.getSquareHeight() / 2);
          StdDraw.picture(bottomRight.getX(), bottomRight.getY(), "images/tiles/bottom-right.png", this.getSquareWidth() / 2, this.getSquareHeight() / 2);
        } else {
          if (previousOrNext.getY() == path.getY()) {
            if (previousOrNext.getX() > path.getX()) {
              StdDraw.picture(topLeft.getX(), topLeft.getY(), "images/tiles/top-left.png", this.getSquareWidth() / 2, this.getSquareHeight() / 2);
              StdDraw.picture(topRight.getX(), topRight.getY(), "images/tiles/top.png", this.getSquareWidth() / 2, this.getSquareHeight() / 2);
              StdDraw.picture(bottomLeft.getX(), bottomLeft.getY(), "images/tiles/bottom-left.png", this.getSquareWidth() / 2, this.getSquareHeight() / 2);
              StdDraw.picture(bottomRight.getX(), bottomRight.getY(), "images/tiles/bottom.png", this.getSquareWidth() / 2, this.getSquareHeight() / 2);
            } else {
              StdDraw.picture(topLeft.getX(), topLeft.getY(), "images/tiles/top.png", this.getSquareWidth() / 2, this.getSquareHeight() / 2);
              StdDraw.picture(topRight.getX(), topRight.getY(), "images/tiles/top-right.png", this.getSquareWidth() / 2, this.getSquareHeight() / 2);
              StdDraw.picture(bottomLeft.getX(), bottomLeft.getY(), "images/tiles/bottom.png", this.getSquareWidth() / 2, this.getSquareHeight() / 2);
              StdDraw.picture(bottomRight.getX(), bottomRight.getY(), "images/tiles/bottom-right.png", this.getSquareWidth() / 2, this.getSquareHeight() / 2);
            }
          } else {
            if (previousOrNext.getY() > path.getY()) {
              StdDraw.picture(topLeft.getX(), topLeft.getY(), "images/tiles/left.png", this.getSquareWidth() / 2, this.getSquareHeight() / 2);
              StdDraw.picture(topRight.getX(), topRight.getY(), "images/tiles/right.png", this.getSquareWidth() / 2, this.getSquareHeight() / 2);
              StdDraw.picture(bottomLeft.getX(), bottomLeft.getY(), "images/tiles/bottom-left.png", this.getSquareWidth() / 2, this.getSquareHeight() / 2);
              StdDraw.picture(bottomRight.getX(), bottomRight.getY(), "images/tiles/bottom-right.png", this.getSquareWidth() / 2, this.getSquareHeight() / 2);
            } else {
              StdDraw.picture(topLeft.getX(), topLeft.getY(), "images/tiles/top-left.png", this.getSquareWidth() / 2, this.getSquareHeight() / 2);
              StdDraw.picture(topRight.getX(), topRight.getY(), "images/tiles/top-right.png", this.getSquareWidth() / 2, this.getSquareHeight() / 2);
              StdDraw.picture(bottomLeft.getX(), bottomLeft.getY(), "images/tiles/left.png", this.getSquareWidth() / 2, this.getSquareHeight() / 2);
              StdDraw.picture(bottomRight.getX(), bottomRight.getY(), "images/tiles/right.png", this.getSquareWidth() / 2, this.getSquareHeight() / 2);
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
    for (int x = 0; x < this.getNbSquareX(); x++) {
      for (int y = 0; y < this.getNbSquareY(); y++) {
        if (y == 0) {
          if (x == 0) {
            StdDraw.picture(x * this.getSquareWidth() + this.getSquareWidth() / 2, y * this.getSquareHeight() + this.getSquareHeight() / 2, "images/tiles/water/bottom-left.png", this.getSquareWidth() + 0.001, this.getSquareHeight() + 0.001);
          } else if (x == this.getNbSquareX() - 1) {
            StdDraw.picture(x * this.getSquareWidth() + this.getSquareWidth() / 2, y * this.getSquareHeight() + this.getSquareHeight() / 2, "images/tiles/water/bottom-right.png", this.getSquareWidth() + 0.001, this.getSquareHeight() + 0.001);
          } else {
            StdDraw.picture(x * this.getSquareWidth() + this.getSquareWidth() / 2, y * this.getSquareHeight() + this.getSquareHeight() / 2, "images/tiles/water/bottom.png", this.getSquareWidth() + 0.001, this.getSquareHeight() + 0.001);
          }


        } else if (y == this.getNbSquareY() - 1) {
          if (x == 0) {
            StdDraw.picture(x * this.getSquareWidth() + this.getSquareWidth() / 2, y * this.getSquareHeight() + this.getSquareHeight() / 2, "images/tiles/water/top-left.png", this.getSquareWidth() + 0.001, this.getSquareHeight() + 0.001);
          } else if (x == this.getNbSquareX() - 1) {
            StdDraw.picture(x * this.getSquareWidth() + this.getSquareWidth() / 2, y * this.getSquareHeight() + this.getSquareHeight() / 2, "images/tiles/water/top-right.png", this.getSquareWidth() + 0.001, this.getSquareHeight() + 0.001);
          } else {
            StdDraw.picture(x * this.getSquareWidth() + this.getSquareWidth() / 2, y * this.getSquareHeight() + this.getSquareHeight() / 2, "images/tiles/water/top.png", this.getSquareWidth() + 0.001, this.getSquareHeight() + 0.001);
          }
        } else {
          if (x == 0) {
            StdDraw.picture(x * this.getSquareWidth() + this.getSquareWidth() / 2, y * this.getSquareHeight() + this.getSquareHeight() / 2, "images/tiles/water/left.png", this.getSquareWidth() + 0.001, this.getSquareHeight() + 0.001);
          } else if (x == this.getNbSquareX() - 1) {
            StdDraw.picture(x * this.getSquareWidth() + this.getSquareWidth() / 2, y * this.getSquareHeight() + this.getSquareHeight() / 2, "images/tiles/water/right.png", this.getSquareWidth() + 0.001, this.getSquareHeight() + 0.001);
          }
        }

      }

    }
  }

  /**
   * Affiche certaines informations sur l'écran telles que les points de vie du joueur ou son or
   */
  @Override
  public void drawInfos() {
    switch(currentStateGame){
      case Game:
        this.HUD.updateInterface(StdDraw.mouseX(), StdDraw.mouseY(), this.getDeltaTime());
        break;
      case Pause:
        this.hudPause.updateInterface(StdDraw.mouseX(), StdDraw.mouseY(), this.getDeltaTime());
        break;
      case End:
        this.hudEnd.updateInterface(StdDraw.mouseX(), StdDraw.mouseY(), this.getDeltaTime());
        break;
    }
  }

  /**
   * Fonction qui récupère le positionnement de la souris et permet d'afficher une image de tour en temps réél
   * lorsque le joueur appuie sur une des touches permettant la construction d'une tour.
   */
  @Override
  public void drawMouse() {
    double normalizedX = (int) (StdDraw.mouseX() / this.getSquareWidth()) * this.getSquareWidth() + this.getSquareWidth() / 2;
    double normalizedY = (int) (StdDraw.mouseY() / this.getSquareHeight()) * this.getSquareHeight() + this.getSquareHeight() / 2;
    if (this.building_class != null) {
      Position mousep = new Position((int) ((normalizedX * this.getNbSquareX())), (int) ((normalizedY * this.getNbSquareY())));
      if (this.paths.contains(mousep) || this.list_tower.containsKey(mousep)) {
        StdDraw.picture(normalizedX, normalizedY, "images/Select_tile_unavailable.png", this.getSquareWidth(), this.getSquareHeight());
      } else {
        StdDraw.picture(normalizedX, normalizedY, "images/Select_tile.png", this.getSquareWidth(), this.getSquareHeight());
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
      m.update(this.getDeltaTime());

      if (m.hasFinishedPath()) {
        this.life -= 1;
        i.remove();
        this.updateProgressBar();
      }
      if (m.isDead()) {
        this.updateProgressBar();
      }
      if (m.isReadyToBeRemoved()) {
        i.remove();
        this.player_wallet.addMoney(m.getGoldWhenDead());
      }
    }
  }

  public void updateProgressBar() {
    Wave currentWave = this.waves.get(0);
    this.HUD.setWaveEnemyProgress((100 * (this.amountAliveMonsters()+currentWave.monsterAmount()) / (double) this.totalMonsterAmount));
  }

  private int amountAliveMonsters() {
    return this.monsters.stream().filter(monster -> !monster.isDead()).collect(Collectors.toCollection(ArrayList::new)).size();
  }

  public void updateTowers() {
    double normalizedX = (int) (StdDraw.mouseX() / this.getSquareWidth()) * this.getSquareWidth() + this.getSquareWidth() / 2;
    double normalizedY = (int) (StdDraw.mouseY() / this.getSquareHeight()) * this.getSquareHeight() + this.getSquareHeight() / 2;
    Position mousep = new Position((int) ((normalizedX * this.getNbSquareX())), (int) ((normalizedY * this.getNbSquareY())));
    Tower towerUnderMouse = this.list_tower.get(mousep);

    if (this.getHUD().getUpgradingTower() != null) {
      this.getHUD().getUpgradingTower().upgradingVisual();
    }
    if (towerUnderMouse != null) {
      towerUnderMouse.hoveredVisual();
    }

    for (Map.Entry<Position, Tower> entry : this.list_tower.entrySet()) {
      Tower value = entry.getValue();
      value.Update(this.getDeltaTime());
    }
  }

  public void updateWave() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
    if (this.waves.size() > 0) {
      Wave currentWave = this.waves.get(0);
      if (currentWave.getCurrentTimeBeforeStartingSpawn() > 0) {
        currentWave.subtractTimeBeforeStartingSpawn(this.getDeltaTime());
        this.HUD.setWaveEnemyProgress(100 - ((100 * currentWave.getCurrentTimeBeforeStartingSpawn()) / currentWave.getTimeBeforeStartingSpawn()));
      } else if (currentWave.finishedSpawning() && this.monsters.size() <= 0) {
        this.waves.remove(0);
        if (this.waves.size() > 0) {
          this.totalMonsterAmount = currentWave.monsterAmount();
        }
      } else {
        currentWave.spawn(this, this.getDeltaTime());
      }
    }
  }

  public void drawLevel(){
    StdDraw.picture(0.5, 0.5, "images/currentlevel.png", 1.0, 1.0);
  }

  /**
   * Met à jour toutes les informations du plateau de jeu ainsi que les déplacements des monstres et les attaques des tours.
   *
   * @return les points de vie restants du joueur
   */
  @Override
  public int update() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
    switch(currentStateGame){
      case Game:
        this.drawLevel();
        this.updateWave();
        this.updateMonsters();
        this.updateTowers();
        this.drawMouse();
        this.drawInfos();
        break;
      case Pause:
      case End:
        StdDraw.picture(0.5, 0.5, "images/pauseTemporaryFile.png", 1.0, 1.0);
        StdDraw.picture(0.5, 0.5, "images/blurtest.png", 1.0, 1.0);
        this.drawInfos();
        break;
    }
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
  @Override
  public void mouseClick(double x, double y, int mouseButton) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
    double normalizedX = (int) (x / this.getSquareWidth()) * this.getSquareWidth() + this.getSquareWidth() / 2;
    double normalizedY = (int) (y / this.getSquareHeight()) * this.getSquareHeight() + this.getSquareHeight() / 2;
    Position p = new Position(normalizedX, normalizedY);
    Position mousep = new Position((int) ((normalizedX * this.getNbSquareX())), (int) ((normalizedY * this.getNbSquareY())));

    switch (currentStateGame) {
      case Pause:
        this.hudPause.onClick(x,y, mouseButton);
        return;
      case End:
        this.hudEnd.onClick(x, y, mouseButton);
        break;
    }

    if (this.HUD.onClick(x, y, mouseButton)) return;

    if (this.building_class != null && !this.isNeedReleaseMouse()) {
      if (!(this.paths.contains(mousep) || this.list_tower.containsKey(mousep))) {
        int price = this.listTowerData.get(this.listTowerData.indexOf(new TowerDataStruct("","","",0,this.building_class))).price;
        if (this.player_wallet.pay(price)) {
          try {
            Constructor cons = this.building_class.getConstructor(Position.class, double.class, double.class, WorldGame.class);
            Tower t = (Tower) cons.newInstance(new Position(normalizedX, normalizedY), this.getSquareWidth(), this.getSquareHeight(), this);
            this.list_tower.put(new Position((int) ((normalizedX * this.getNbSquareX())), (int) ((normalizedY * this.getNbSquareY()))), t);
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
    } else if (!this.isNeedReleaseMouse()) {
      Tower towerUnderMouse = this.list_tower.get(mousep);
      if (towerUnderMouse != null) {
        this.HUD.showUpgradeTowerBox(towerUnderMouse);
      }
    }
  }

  public void startBuilding(Class c) {
    this.building_class = c;
    this.setNeedReleaseMouse(true);
  }

  public void stopBuilding() {
    this.building_class = null;
    this.setNeedReleaseMouse(true);
  }

  public void pressingEscape() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
    this.needKeyRelease = true;
    if(this.currentStateGame == StateGame.Game){

       this.currentStateGame = StateGame.Pause;

      if(this.hudPause==null) this.hudPause = new InterfacePause(this);
      this.hudPause.getBox().showBox(0.8,0.0,1.5);
      StdDraw.save("images/pauseTemporaryFile.png");
      this.pauseBackground = StdDraw.pictureNget(0.5,0.5,"images/pauseTemporaryFile.png",1.0,1.0);

    } else exitPause();
  }

  public void exitPause(){
    this.currentStateGame = StateGame.Game;
    if(this.pauseBackground!=null) this.pauseBackground.flush();
  }

  /**
   * Récupère la touche entrée au clavier ainsi que la position de la souris et met à jour le plateau en fonction de ces interractions
   */
  @Override
  public void run() throws UnsupportedAudioFileException, IOException, LineUnavailableException {

    try {
      Sound gameMusic = new Sound(this.musicPath, true);
      gameMusic.play(0.25);
    } catch (LineUnavailableException | IOException | UnsupportedAudioFileException e) {
      e.printStackTrace();
    }

    super.run();
  }

  public void endGame(boolean win) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
    StdDraw.save("images/pauseTemporaryFile.png");
    this.pauseBackground = StdDraw.pictureNget(0.5,0.5,"images/pauseTemporaryFile.png",1.0,1.0);
    this.hudEnd = new InterfaceEndGame(this, win);
    this.hudEnd.getBox().showBox(0.8,0.0,1.0);
    this.currentStateGame = StateGame.End;
  }

  @Override
  public void endWorld() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
    if(this.levelBackground!=null) this.levelBackground.flush();
    if(this.pauseBackground!=null) this.pauseBackground.flush();
    super.endWorld();
  }

  @Override
  public void updateEvent() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
    if (StdDraw.isKeyPressed(27)) {
      if(!needKeyRelease) pressingEscape();
    } else if(this.needKeyRelease) this.needKeyRelease = false;
    if(currentStateGame == StateGame.Game){
      if(this.life<=0) endGame(false);
      if(this.waves.size()==0) endGame(this.life>0);
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

  public InterfaceGame getHUD() {
    return this.HUD;
  }

  public void setHUD(InterfaceGame HUD) {
    this.HUD = HUD;
  }

  public Wallet getPlayer_wallet() {
    return this.player_wallet;
  }

  public void setPlayer_wallet(Wallet player_wallet) {
    this.player_wallet = player_wallet;
  }

  public TreeMap<Position, Tower> getList_tower() {
    return this.list_tower;
  }

  public void setList_tower(TreeMap<Position, Tower> list_tower) {
    this.list_tower = list_tower;
  }

  public Class getBuilding_class() {
    return this.building_class;
  }

  public void setBuilding_class(Class building_class) {
    this.building_class = building_class;
  }

  public Position getSpawn() {
    return this.spawn;
  }

  public void setSpawn(Position spawn) {
    this.spawn = spawn;
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
