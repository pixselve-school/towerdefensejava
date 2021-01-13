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
import warcraftTD.world.*;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.awt.*;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.*;
import java.util.stream.Collectors;

public class WorldGame extends World {
  // l'ensemble des monstres, pour gerer (notamment) l'affichage
  private List<Monster> monsters = new ArrayList<Monster>();
  private int totalMonsterAmount;

  private boolean needKeyRelease;
  private boolean pause;
  // l'ensemble des cases du chemin
  private List<Position> paths;

  private Sound music;

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

  warcraftTD.utils.Cursor buildingCursor;

  // Position par laquelle les monstres vont venir
  private Position spawn;

  // Nombre de points de vie du joueur
  int life = 20;

  // Commande sur laquelle le joueur appuie (sur le clavier)
  char key;

  boolean isMonsterActing = false;

  boolean displayWater;

  Tile selectedTile;

  List<Wave> waves;

  String musicPath;

  private StateGame currentStateGame;

  private enum StateGame {
    Game, Pause, End
  }

  TreeMap<Position, Tile> positionTileMap;

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
    this.listTowerData.add(new TowerDataStruct("images/shop_arrowtower.png", "images/shop_arrowtower_hover.png", "turret_arrow", 50, Arrow.class, new Color(255, 125, 26)));
    this.listTowerData.add(new TowerDataStruct("images/shop_bombtower.png", "images/shop_bombtower_hover.png", "turret_bomb", 60, Bomb.class, new Color(0, 0, 0)));
    this.listTowerData.add(new TowerDataStruct("images/shop_icetower.png", "images/shop_icetower_hover.png", "turret_ice", 70, Ice.class, new Color(124, 220, 209)));
    this.listTowerData.add(new TowerDataStruct("images/shop_poisontower.png", "images/shop_poisontower_hover.png", "turret_poison", 80, Poison.class, new Color(95, 158, 25)));

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

    this.positionTileMap = new TreeMap<>();

    this.initTerrain();
    this.currentStateGame = StateGame.Game;
    this.buildingCursor = new warcraftTD.utils.Cursor(this.getSquareWidth()/2, this.getSquareHeight()/2);
    this.buildingCursor.setColor(new Color(1,0,0));
  }

  public void initTerrain() {
    this.generatePath();
    this.drawBackground();

    StdDraw.show();

    StdDraw.save("temporary/currentlevel.png");
    this.levelBackground = StdDraw.pictureNget(0.5,0.5,"temporary/currentlevel.png",1.0,1.0);
  }


  public void generatePath() {
    for (int i = 0; i < this.getNbSquareX(); i++) {
      for (int j = 0; j < this.getNbSquareY(); j++) {
        final Position position = new Position(i, j);

        if (this.displayWater && ((i == 0 || i == this.getNbSquareX() - 1) || (j == 0 || j == this.getNbSquareY() - 1))) {
          final Water water = new Water(position.getWorldPosition(this), this.getSquareHeight(), this.getSquareWidth());
//          water.setDebug(true);
          this.positionTileMap.put(position, water);
          this.updateTile(position, true);
        } else if (this.paths.contains(position)) {
          final Pathway pathway = new Pathway(position.getWorldPosition(this), this.getSquareHeight(), this.getSquareWidth());

          this.positionTileMap.put(position, pathway);
          this.updateTile(position, true);

        } else {
          final Grass grass = new Grass(position.getWorldPosition(this), this.getSquareHeight(), this.getSquareWidth());
          double random = Math.random();
          if (random > 0.95) {
            grass.replaceContains(new Flower(FlowerType.RED, grass));
          } else if (random > 0.90) {
            grass.replaceContains(new Flower(FlowerType.BLUE, grass));
          } else if (random > 0.85) {
            grass.replaceContains(new Flower(FlowerType.WHITE, grass));
          } else if (random > 0.80) {
            grass.replaceContains(new Flower(FlowerType.YELLOW, grass));
          } else if (random > 0.75) {
            grass.replaceContains(new Flower(FlowerType.BUSH, grass));
          } else if (random > 0.70) {
            grass.replaceContains(new Tree());
          }
          this.positionTileMap.put(position, grass);
        }
      }
    }

  }


  public boolean isPositionInView(Position position) {
    return position.getX() >= 0 && position.getX() < this.getNbSquareX() && position.getY() >= 0 && position.getY() < this.getNbSquareY();
  }

  public void updateTile(Position position, boolean cascade) {
    Tile tile = this.positionTileMap.get(position);

    final double x = position.getX();
    final double y = position.getY();

    final Position topPosition = new Position(x, y + 1);
    Tile top = this.positionTileMap.get(topPosition);
    final Position bottomPosition = new Position(x, y - 1);
    Tile bottom = this.positionTileMap.get(bottomPosition);
    final Position leftPosition = new Position(x - 1, y);
    Tile left = this.positionTileMap.get(leftPosition);
    final Position rightPosition = new Position(x + 1, y);
    Tile right = this.positionTileMap.get(rightPosition);
    tile.setDirectionValue(0);
    if (!this.isPositionInView(topPosition) || top != null && top.getClass() == tile.getClass()) {
      tile.setDirectionValue(tile.getDirectionValue() + 1);
      if (cascade && top != null) {
        this.updateTile(topPosition, false);
      }
    }
    if (!this.isPositionInView(bottomPosition) || bottom != null && bottom.getClass() == tile.getClass()) {
      tile.setDirectionValue(tile.getDirectionValue() + 8);
      if (cascade && bottom != null) {
        this.updateTile(bottomPosition, false);
      }
    }
    if (!this.isPositionInView(leftPosition) || left != null && left.getClass() == tile.getClass()) {
      tile.setDirectionValue(tile.getDirectionValue() + 2);
      if (cascade && left != null) {
        this.updateTile(leftPosition, false);
      }
    }
    if (!this.isPositionInView(rightPosition) || right != null && right.getClass() == tile.getClass()) {
      tile.setDirectionValue(tile.getDirectionValue() + 4);
      if (cascade && right != null) {
        this.updateTile(rightPosition, false);
      }
    }
  }


  /**
   * Définit le décors du plateau de jeu.
   */
  @Override
  public void drawBackground() {
    for (Map.Entry<Position, Tile> tileEntry : this.positionTileMap.entrySet()) {
      tileEntry.getValue().drawStaticPart();

    }
  }


  /**
   * Initialise le chemin sur la position du point de départ des monstres. Cette fonction permet d'afficher une route qui sera différente du décors.
   */
  @Override
  public void drawPath() {

  }

  public void updateTileEntities(double deltaTime) {
    for (Map.Entry<Position, Tile> tileEntry : this.positionTileMap.descendingMap().entrySet()) {
      tileEntry.getValue().update(deltaTime);
      tileEntry.getValue().updateContainsEntity(deltaTime);
    }
  }

  public void drawTileAnimation(double deltaTime) {
    for (Map.Entry<Position, Tile> tileEntry : this.positionTileMap.descendingMap().entrySet()) {
      tileEntry.getValue().drawAnimatedPart(deltaTime);
    }
  }

  public void drawTileSettings() {
    for (Map.Entry<Position, Tile> tileEntry : this.positionTileMap.descendingMap().entrySet()) {
      tileEntry.getValue().drawSettings();
    }
  }



  /**
   * Affiche certaines informations sur l'écran telles que les points de vie du joueur ou son or
   */
  @Override
  public void drawInfos() {
    switch (currentStateGame) {
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
    Position mousep = new Position((int) ((normalizedX * this.getNbSquareX())), (int) ((normalizedY * this.getNbSquareY())));

    if (this.building_class != null) {
      Tile tile = this.positionTileMap.get(mousep);
      if(this.selectedTile != tile) {
        this.selectedTile = tile;
        this.buildingCursor.setColorByTileUnder(this.selectedTile);
      }
      int price = this.listTowerData.get(this.listTowerData.indexOf(new TowerDataStruct("", "", "", 0, this.building_class, null))).price;
      if(this.selectedTile.getContains()!=null && this.selectedTile.getContains().getBuildable().equals(EntityBuildable.PAYING)) price +=20;

      this.buildingCursor.update(new Position(normalizedX, normalizedY), this.getDeltaTime());

      StdDraw.setFont(new Font("Arial", Font.BOLD, 40));
      if(player_wallet.canPay(price)) StdDraw.setPenColor(new Color(255,230,0));
      else StdDraw.setPenColor(new Color(255,0,0));

      StdDraw.text(normalizedX+0.01, normalizedY+0.07,price+"");
      StdDraw.picture(normalizedX-0.02, normalizedY+0.075, "images/moneyIcon.png", 0.02, 0.04);
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
    this.HUD.setWaveEnemyProgress((100 * (this.amountAliveMonsters() + currentWave.monsterAmount()) / (double) this.totalMonsterAmount));
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
    StdDraw.picture(0.5, 0.5, "temporary/currentlevel.png", 1.0, 1.0);
    if(building_class!=null){
      this.positionTileMap.forEach((k, tile) -> {
        if(tile instanceof Water){
          tile.drawOverlay(new Color(0,0,0, 100));
        } else if(tile.getContains() != null){
          switch (tile.getContains().getBuildable()){
            case BUILDABLE:
              tile.drawOverlay(new Color(0,255,0, 100));
              return;
            case PAYING:
              tile.drawOverlay(new Color(255,100,0, 100));
              return;
            case NOTBUILDABLE:
              tile.drawOverlay(new Color(255,0,0, 100));
              return;
          }
        } else if(tile.isBuildable()) tile.drawOverlay(new Color(0,255,0, 100));
        else tile.drawOverlay(new Color(255,0,0, 100));
      });
    }
  }

  /**
   * Met à jour toutes les informations du plateau de jeu ainsi que les déplacements des monstres et les attaques des tours.
   *
   * @return les points de vie restants du joueur
   */
  @Override
  public int update() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
    switch (this.currentStateGame) {
      case Game:
        this.drawLevel();
        this.updateWave();
        this.updateMonsters();
        this.updateTileEntities(this.getDeltaTime());
        this.drawTileAnimation(this.getDeltaTime());
        this.drawTileSettings();
        this.updateTowers();
        this.drawMouse();
        //this.mouseHover();
        this.drawInfos();
        break;
      case Pause:
      case End:
        StdDraw.picture(0.5, 0.5, "temporary/pauseTemporaryFile.png", 1.0, 1.0);
        //StdDraw.picture(0.5, 0.5, "images/blurtest.png", 1.0, 1.0);
        this.drawInfos();
        break;
    }
    return this.life;
  }

  public void mouseHover() {
    final double mouseX = StdDraw.mouseX();
    final double mouseY = StdDraw.mouseY();
    Position tilePosition = new Position((int) Math.floor( mouseX * this.getNbSquareX()), (int) Math.floor( mouseY * this.getNbSquareY()));
    Tile tile = this.positionTileMap.get(tilePosition);
    if (tile != null && tile != this.selectedTile) {
      if (this.selectedTile != null) {
        this.selectedTile.onHoverLeave();
      }
      this.selectedTile = tile;
      this.selectedTile.onHover(mouseX, mouseY);
    } else if (tile == null && this.selectedTile != null) {
      this.selectedTile.onHoverLeave();
      this.selectedTile = null;
    }
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


    Position mousep = new Position((int) ((normalizedX * this.getNbSquareX())), (int) ((normalizedY * this.getNbSquareY())));

    Position tilePosition = new Position((int) Math.floor( x * this.getNbSquareX()), (int) Math.floor( y * this.getNbSquareY()));


    switch (currentStateGame) {
      case Pause:
        this.hudPause.onClick(x, y, mouseButton);
        return;
      case End:
        this.hudEnd.onClick(x, y, mouseButton);
        break;
    }

    if (this.HUD.onClick(x, y, mouseButton)) return;


    Tile tile = this.positionTileMap.get(tilePosition);

    if (this.building_class != null && !this.isNeedReleaseMouse()) {

      if (tile != null && tile.isBuildable()) {
        TowerDataStruct td = this.listTowerData.get(this.listTowerData.indexOf(new TowerDataStruct("", "", "", 0, this.building_class, null)));
        int price = td.price;
        if(this.selectedTile.getContains()!=null && this.selectedTile.getContains().getBuildable().equals(EntityBuildable.PAYING)) price +=20;
        if (this.player_wallet.pay(price)) {
          try {
            Constructor cons = this.building_class.getConstructor(Position.class, double.class, double.class, WorldGame.class);
            Tower t = (Tower) cons.newInstance(new Position(normalizedX, normalizedY), this.getSquareWidth(), this.getSquareHeight(), this);
            tile.replaceContains(t, true, td.colorParticleSpawn);
            this.buildingCursor.setColorByTileUnder(this.selectedTile);
            this.list_tower.put(mousep, t);
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

  public void singleMouseClick(double x, double y, int mouseButton) {
    Position tilePosition = new Position((int) Math.floor( x * this.getNbSquareX()), (int) Math.floor( y * this.getNbSquareY()));
    Tile tileClick = this.positionTileMap.get(tilePosition);
    if (tileClick != null) {
      tileClick.onClick(x, y);
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
    if (this.currentStateGame == StateGame.Game) {

      this.currentStateGame = StateGame.Pause;


      if(this.hudPause==null) this.hudPause = new InterfacePause(this);
      this.hudPause.getBox().showBox(new Position(0.5,-0.3), new Position(0.5,0.5), 1.2);
      StdDraw.save("temporary/pauseTemporaryFile.png");
      this.pauseBackground = StdDraw.pictureNget(0.5,0.5,"temporary/pauseTemporaryFile.png",1.0,1.0);


    } else if(this.currentStateGame == StateGame.Pause) exitPause();
  }

  public void exitPause() {
    this.currentStateGame = StateGame.Game;
    if (this.pauseBackground != null) this.pauseBackground.flush();
  }

  /**
   * Récupère la touche entrée au clavier ainsi que la position de la souris et met à jour le plateau en fonction de ces interractions
   */
  @Override
  public void run() throws UnsupportedAudioFileException, IOException, LineUnavailableException {

    try {
      this.music = new Sound(this.musicPath, true);
      this.music.play(0.25);
    } catch (LineUnavailableException | IOException | UnsupportedAudioFileException e) {
      e.printStackTrace();
    }

    super.run();
  }

  public void endGame(boolean win) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
    StdDraw.save("temporary/pauseTemporaryFile.png");
    this.pauseBackground = StdDraw.pictureNget(0.5, 0.5, "temporary/pauseTemporaryFile.png", 1.0, 1.0);
    this.hudEnd = new InterfaceEndGame(this, win);
    this.hudEnd.getBox().showBox(new Position(0.5,-0.3), new Position(0.5,0.5), 1.2);
    this.currentStateGame = StateGame.End;
  }

  @Override
  public void endWorld() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
    if (this.levelBackground != null) this.levelBackground.flush();
    if (this.pauseBackground != null) this.pauseBackground.flush();
    this.music.stop();
    super.endWorld();
  }

  @Override
  public void updateEvent() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
    if (StdDraw.isKeyPressed(27)) {
      if (!needKeyRelease) pressingEscape();
    } else if (this.needKeyRelease) this.needKeyRelease = false;
    if (currentStateGame == StateGame.Game) {
      if (this.life <= 0) endGame(false);
      if (this.waves.size() == 0) endGame(this.life > 0);
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
