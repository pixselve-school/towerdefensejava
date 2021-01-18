package warcraftTD;

import warcraftTD.hud.InterfaceEndGame;
import warcraftTD.hud.InterfaceGame;
import warcraftTD.hud.InterfacePause;
import warcraftTD.hud.MainMenu;
import warcraftTD.libs.StdDraw;
import warcraftTD.monsters.Monster;
import warcraftTD.monsters.Wave;
import warcraftTD.monsters.entities.Scorpion;
import warcraftTD.towers.*;
import warcraftTD.utils.*;
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

/**
 * Un monde de jeu
 */
public class WorldGame extends World {
  /** Liste des monstres en jeu */
  private List<Monster> monsters;
  /** Nombre des monstres sur le terrain */
  private int totalMonsterAmount;
  /** Spécifie si on doit relacher la touche échap avant de pouvoir à nouveau l'utiliser */
  private boolean needKeyRelease;
  /** Son pour la musique de fond */
  private Sound music;
  /** Image de fond du menu pause */
  private Image pauseBackground;
  /** Image de fond du niveau de jeu */
  private Image levelBackground;
  /** L'interface utilisateur de jeu */
  private InterfaceGame hud;
  /** L'interface du menu pause */
  private InterfacePause hudPause;
  /** L'interface pour une fin de partie */
  private InterfaceEndGame hudEnd;
  /** Porte monnaie du joueur */
  private Wallet playerWallet;
  /** Liste des caractéristiques pour chaque type de tours */
  private ArrayList<TowerDataStruct> listTowerData;
  /** Arbre des tours associés à leur position */
  private TreeMap<Position, Tower> listTower;
  /** Classe de la tour en construction actuellement (null si on ne construit pas) */
  private Class buildingClass;
  /** Curseur afficher en mode construction */
  warcraftTD.utils.Cursor buildingCursor;
  /** Nombre de points de vie du joueur */
  int life;
  /** Spécifie si le terrain est entouré d'eau */
  boolean displayWater;
  /** Tuile sous la souris */
  Tile selectedTile;
  /** Liste des différentes vagues de monstres de la partie */
  List<Wave> waves;
  /** Chemin vers la musique du jeu */
  String musicPath;
  /** Etat actuel de la souris (en jeu, en pause, fin de partie) */
  private StateGame currentStateGame;
  /** Spécifie le degré de spawn des végétation sur la map */
  PlantPresence plantPresence;

  /** Enum pour spécifier l'état de la partie */
  private enum StateGame {
    Game, Pause, End
  }

  /**
   * Récupère la liste des monstres en jeu
   * @return la liste des monstres en jeu
   */
  public List<Monster> getMonsters() {
    return this.monsters;
  }

  /**
   * Modifie la liste des monstres en jeu
   * @param monsters la liste des monstres en jeu
   */
  public void setMonsters(List<Monster> monsters) {
    this.monsters = monsters;
  }

  /**
   * Récupère l'interface utilisateur de jeu
   * @return l'interface utilisateur de jeu
   */
  public InterfaceGame getHud() {
    return this.hud;
  }

  /**
   * Récupère le porte monnaie du joueur
   * @return le porte monnaie du joueur
   */
  public Wallet getPlayerWallet() {
    return this.playerWallet;
  }

  /**
   * Récupère les vies du joueur
   * @return les vies du joueur
   */
  public int getLife() {
    return this.life;
  }

  /**
   * Ajoute un monstre à la liste des monstres de jeu
   * @param monster le monstre à ajouter
   */
  public void addMonster(Monster monster) {
    monster.resizeMonster(this.getNbSquareX(), this.getNbSquareY());
    this.monsters.add(monster);
  }

  /**
   * Initialise un monde de jeu
   * @param width la largeur de la fenetre
   * @param height la hauteur de la fenetre
   * @param nbSquareX nombre de tuiles horizontalement
   * @param nbSquareY nombre de tuiles verticalement
   * @param money argent initial
   * @param health vies initiales
   * @param displayWater spécifie si le terrain est entouré d'eau
   * @param musicPath le chemin vers la musique de fond
   * @param path liste des positions des chemins
   * @param waves liste des vagues de monstres
   * @param menu référence vers le menu principal
   * @param plantPresence le degré de spawn des végétation sur la map
   * @throws UnsupportedAudioFileException
   * @throws IOException
   * @throws LineUnavailableException
   */
  public WorldGame(int width, int height, int nbSquareX, int nbSquareY, int money, int health, boolean displayWater, String musicPath, List<Position> path, List<Wave> waves, MainMenu menu, PlantPresence plantPresence) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
    super(width, height, menu);
    this.setNbSquareX(nbSquareX);
    this.setNbSquareY(nbSquareY);
    this.setSquareWidth((double) 1.006 / nbSquareX);
    this.setSquareHeight((double) 1.006 / nbSquareY);

    this.life = health;
    this.playerWallet = new Wallet(this);
    this.playerWallet.addMoney(money);

    this.displayWater = displayWater;
    this.plantPresence = plantPresence;
    this.setPaths(path);

    this.listTowerData = new ArrayList<TowerDataStruct>();
    this.listTowerData.add(new TowerDataStruct("images/shop_arrowtower.png", "images/shop_arrowtower_hover.png", "turret_arrow", 50, Arrow.class, new Color(255, 125, 26)));
    this.listTowerData.add(new TowerDataStruct("images/shop_bombtower.png", "images/shop_bombtower_hover.png", "turret_bomb", 60, Bomb.class, new Color(0, 0, 0)));
    this.listTowerData.add(new TowerDataStruct("images/shop_icetower.png", "images/shop_icetower_hover.png", "turret_ice", 70, Ice.class, new Color(124, 220, 209)));
    this.listTowerData.add(new TowerDataStruct("images/shop_poisontower.png", "images/shop_poisontower_hover.png", "turret_poison", 80, Poison.class, new Color(95, 158, 25)));
    this.listTower = new TreeMap<>();
    this.buildingClass = null;

    this.hud = new InterfaceGame(this, this.listTowerData);
    this.needKeyRelease = false;

    this.monsters =  new ArrayList<Monster>();
    this.waves = waves;
    if (this.waves.size() > 0) {
      this.hud.setWaveEnemyProgress(100);
      this.totalMonsterAmount = this.waves.get(0).monsterAmount();
    }

    this.musicPath = musicPath;

    this.setPositionTileMap(new TreeMap<>());

    this.initTerrain();
    this.currentStateGame = StateGame.Game;
    this.buildingCursor = new warcraftTD.utils.Cursor(this.getSquareWidth() / 2, this.getSquareHeight() / 2);
    this.buildingCursor.setColor(new Color(1, 0, 0));
  }

  /**
   * Génère l'affichage du terrain, le prends en photo pour pouvoir afficher cette photo en fond pendant le jeu
   */
  public void initTerrain() {
    this.generatePath();
    this.drawBackground();

    StdDraw.show();

    StdDraw.save("temporary/currentlevel.png");
    this.levelBackground = StdDraw.pictureNget(0.5, 0.5, "temporary/currentlevel.png", 1.0, 1.0);
  }

  /**
   * Méthode générant les tuiles de jeux
   */
  public void generatePath() {
    for (int i = 0; i < this.getNbSquareX(); i++) {
      for (int j = 0; j < this.getNbSquareY(); j++) {
        final Position position = new Position(i, j);

        if (this.displayWater && ((i == 0 || i == this.getNbSquareX() - 1) || (j == 0 || j == this.getNbSquareY() - 1))) {
          final Water water = new Water(position, this.getSquareHeight(), this.getSquareWidth());
          this.getPositionTileMap().put(position, water);
        } else if (this.getPaths().contains(position)) {

          final Pathway pathway = new Pathway(position, this.getSquareHeight(), this.getSquareWidth());
          this.getPositionTileMap().put(position, pathway);
        } else {
          final Grass grass = new Grass(position, this.getSquareHeight(), this.getSquareWidth());
          double random = Math.random();
          if (random > 0.90) {
            if (this.plantPresence == PlantPresence.Full) {
              grass.putRandomTree();
            }
          } else if (random > 0.5) {
            if (this.plantPresence == PlantPresence.Full || this.plantPresence == PlantPresence.Small) {
              grass.putRandomSmallVegetation();
            }

          }
          this.getPositionTileMap().put(position, grass);
        }
      }
    }

    Position spawnPath = this.getPaths().get(0);
    Position finishPath = this.getPaths().get(this.getPaths().size() - 1);
    this.getPositionTileMap().get(spawnPath).replaceContains(new IndestructibleEntity("images/tiles/rock.png", 1));
    this.getPositionTileMap().get(finishPath).replaceContains(new IndestructibleEntity("images/tiles/house.png", 1.5));


    this.getPositionTileMap().forEach((position, tile) -> tile.updateDirectionValue(this.getPositionTileMap(), false));

  }

  /**
   * Affiche les tuiles de fond en jeu
   */
  @Override
  public void drawBackground() {
    for (Map.Entry<Position, Tile> tileEntry : this.getPositionTileMap().entrySet()) {
      tileEntry.getValue().drawStaticPart();
    }
  }

  /**
   * Affiche certaines informations sur l'écran telles que les points de vie du joueur ou son or
   */
  @Override
  public void drawInfos() {
    switch (currentStateGame) {
      case Game:
        this.hud.updateInterface(StdDraw.mouseX(), StdDraw.mouseY(), this.getDeltaTime());
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
   * Méthode responsable d'afficher des éléments sous la souris
   */
  @Override
  public void drawMouse() {
    Position normalized = this.getTileNormalizedPositionUnderMouse();
    Position tilePosition = this.getTilePositionUnderMouse();
    Tile tile = this.getPositionTileMap().get(tilePosition);

    if (tile != null && tile != this.selectedTile) {
      if (this.selectedTile != null) {
        this.selectedTile.onHoverLeave();
      }
      this.selectedTile = tile;
      this.selectedTile.onHover(0, 0);
      if (this.buildingClass != null) this.buildingCursor.setColorByTileUnder(tile);
    } else if (tile == null && this.selectedTile != null) {
      this.selectedTile.onHoverLeave();
      this.selectedTile = null;
    }

    if (this.buildingClass != null) {

      int price = this.listTowerData.get(this.listTowerData.indexOf(new TowerDataStruct("", "", "", 0, this.buildingClass, null))).price;
      if (this.selectedTile.getContains() != null && this.selectedTile.getContains().getBuildable().equals(EntityBuildable.PAYING))
        price += 20;

      this.buildingCursor.update(new Position(normalized.getX(), normalized.getY()), this.getDeltaTime());

      StdDraw.setFont(new Font("Arial", Font.BOLD, 40));
      if (playerWallet.canPay(price)) StdDraw.setPenColor(new Color(255, 230, 0));
      else StdDraw.setPenColor(new Color(255, 0, 0));

      StdDraw.text(normalized.getX() + 0.01, normalized.getY() + 0.07, price + "");
      StdDraw.picture(normalized.getX() - 0.02, normalized.getY() + 0.075, "images/moneyIcon.png", 0.02, 0.04);

    }
  }

  /**
   * Actualise et vérifie les états des monstres (mort, finis le chemin etc...)
   */
  public void updateMonsters() {
    Iterator<Monster> i = this.monsters.iterator();
    Monster m;
    while (i.hasNext()) {
      m = i.next();
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
        this.playerWallet.addMoney(m.getGoldWhenDead());
      }
    }
  }

  /**
   * Actualise la logique et le visuel des entités du jeux
   */
  public void drawTileEntitiesAndMonsters() {
    List<DrawableEntity> drawableEntityList = new LinkedList<>(this.monsters);
    drawableEntityList.addAll(this.getPositionTileMap().values());
    drawableEntityList.sort((o1, o2) -> (int) ((o2.getPosition().getY() - o1.getPosition().getY()) * 100));
    for (DrawableEntity drawableEntity : drawableEntityList) {
      if (drawableEntity instanceof Tile) {
        final Tile tile = (Tile) drawableEntity;
        if (this.hud.getUpgradingTower() != null && drawableEntity == this.hud.getUpgradingTower().getParentTile()) {
          tile.drawSelected(Color.orange, 0.005);
        }
        tile.updateContainsEntity(this.getDeltaTime());
        tile.drawAnimatedPart(this.getDeltaTime());

        tile.drawSettings();
      } else if (drawableEntity instanceof Monster) {
        ((Monster) drawableEntity).update(this.getDeltaTime());
      }

    }
  }

  /**
   * Actualise la bar de progression du jeu
   */
  public void updateProgressBar() {
    Wave currentWave = this.waves.get(0);
    this.hud.setWaveEnemyProgress((100 * (this.amountAliveMonsters() + currentWave.monsterAmount()) / (double) this.totalMonsterAmount));
  }

  /**
   * Récupère le nombre de monstres en vie
   * @return le nombre de monstres en vie
   */
  private int amountAliveMonsters() {
    return this.monsters.stream().filter(monster -> !monster.isDead()).collect(Collectors.toCollection(ArrayList::new)).size();
  }

  /**
   * Actualise l'avancement des vagues de monstres
   */
  public void updateWave() {
    if (this.waves.size() > 0) {
      Wave currentWave = this.waves.get(0);
      if (currentWave.getCurrentTimeBeforeStartingSpawn() > 0) {
        currentWave.subtractTimeBeforeStartingSpawn(this.getDeltaTime());
        this.hud.setWaveEnemyProgress(100 - ((100 * currentWave.getCurrentTimeBeforeStartingSpawn()) / currentWave.getTimeBeforeStartingSpawn()));
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

  /**
   * Affiche le niveau en fond
   */
  public void drawLevel() {
    StdDraw.picture(0.5, 0.5, "temporary/currentlevel.png", 1.0, 1.0);
    if (buildingClass != null) {
      this.getPositionTileMap().forEach((k, tile) -> {
        if (tile instanceof Water) {
          tile.drawOverlay(new Color(0, 0, 0, 100));
        } else if (tile.getContains() != null) {
          switch (tile.getContains().getBuildable()) {
            case BUILDABLE:
              tile.drawOverlay(new Color(0, 255, 0, 100));
              return;
            case PAYING:
              tile.drawOverlay(new Color(255, 100, 0, 100));
              return;
            case NOTBUILDABLE:
              tile.drawOverlay(new Color(255, 0, 0, 100));
              return;
          }
        } else if (tile.isBuildable()) tile.drawOverlay(new Color(0, 255, 0, 100));
        else tile.drawOverlay(new Color(255, 0, 0, 100));
      });
    }
  }

  /**
   * Actualise la logique du monde et affiche son apparence et ses éléments
   * @throws UnsupportedAudioFileException
   * @throws IOException
   * @throws LineUnavailableException
   */
  @Override
  public void update() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
    switch (this.currentStateGame) {
      case Game:
        this.drawLevel();
        this.updateWave();
        this.drawTileEntitiesAndMonsters();
        this.updateMonsters();
        this.drawMouse();
        this.drawInfos();
        break;
      case Pause:
      case End:
        StdDraw.picture(0.5, 0.5, "temporary/pauseTemporaryFile.png", 1.0, 1.0);
        this.drawInfos();
        break;
    }
  }

  /**
   * Méthode appelé lorsque l'utilisateur presse la souris
   * @param x la position horizontale de la souris
   * @param y la position verticale de la souris
   * @param mouseButton le bouton de la souris utilisé
   * @throws UnsupportedAudioFileException
   * @throws IOException
   * @throws LineUnavailableException
   */
  @Override
  public void mouseClick(double x, double y, int mouseButton) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
    Position tilePosition = this.getTilePositionUnderMouse();

    switch (currentStateGame) {
      case Pause:
        this.hudPause.onClick(x, y, mouseButton);
        return;
      case End:
        this.hudEnd.onClick(x, y, mouseButton);
        break;
    }

    if (this.hud.onClick(x, y, mouseButton)) return;
    Tile tile = this.getPositionTileMap().get(tilePosition);

    if (this.buildingClass != null && !this.isNeedReleaseMouse()) {
      if (tile != null && tile.isBuildable()) {
        TowerDataStruct td = this.listTowerData.get(this.listTowerData.indexOf(new TowerDataStruct("", "", "", 0, this.buildingClass, null)));
        int price = td.price;
        if (this.selectedTile.getContains() != null && this.selectedTile.getContains().getBuildable().equals(EntityBuildable.PAYING))
          price += 20;
        if (this.playerWallet.pay(price)) {
          try {
            Constructor cons = this.buildingClass.getConstructor(double.class, double.class, WorldGame.class);
            Tower t = (Tower) cons.newInstance(this.getSquareWidth(), this.getSquareHeight(), this);

            tile.replaceContains(t, true, td.colorParticleSpawn);
            this.buildingCursor.setColorByTileUnder(this.selectedTile);
            this.listTower.put(tilePosition, t);
            Sound soundTower = new Sound("music/putTower.wav", false);
            soundTower.play(0.5);
          } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
            e.printStackTrace();
          }
        }
      }
    } else {
      if (tile != null && buildingClass == null) {
        tile.onClick(x, y);
        this.setNeedReleaseMouse(true);
        if (tile.getContains() instanceof Tower) {
          this.hud.showUpgradeTowerBox((Tower) tile.getContains());
        }
      }
    }
  }

  /**
   * Passe en mode construction d'une tour de classe c
   * @param c la classe de la tour à construire
   */
  public void startBuilding(Class c) {
    this.buildingClass = c;
    this.setNeedReleaseMouse(true);
  }

  /**
   * Quitte le mode construction
   */
  public void stopBuilding() {
    this.buildingClass = null;
    this.setNeedReleaseMouse(true);
  }

  /**
   * Méthode appelé lorsque la touche échap est préssée
   * @throws UnsupportedAudioFileException
   * @throws IOException
   * @throws LineUnavailableException
   */
  public void pressingEscape() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
    this.needKeyRelease = true;
    if (this.currentStateGame == StateGame.Game) {

      this.currentStateGame = StateGame.Pause;

      if (this.hudPause == null) this.hudPause = new InterfacePause(this);
      this.hudPause.getBox().showBox(new Position(0.5, -0.3), new Position(0.5, 0.5), 1.2);
      StdDraw.save("temporary/pauseTemporaryFile.png");
      this.pauseBackground = StdDraw.pictureNget(0.5, 0.5, "temporary/pauseTemporaryFile.png", 1.0, 1.0);


    } else if (this.currentStateGame == StateGame.Pause) exitPause();
  }

  /**
   * Quitte le menu pause
   */
  public void exitPause() {
    this.currentStateGame = StateGame.Game;
    if (this.pauseBackground != null) this.pauseBackground.flush();
  }

  /**
   * Lance la boucle de jeu
   * @throws UnsupportedAudioFileException
   * @throws IOException
   * @throws LineUnavailableException
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

  /**
   * Affiche l'interface de fin de partie
   * @param win le joueur a gagné
   * @throws UnsupportedAudioFileException
   * @throws IOException
   * @throws LineUnavailableException
   */
  public void endGame(boolean win) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
    StdDraw.save("temporary/pauseTemporaryFile.png");
    this.pauseBackground = StdDraw.pictureNget(0.5, 0.5, "temporary/pauseTemporaryFile.png", 1.0, 1.0);
    this.hudEnd = new InterfaceEndGame(this, win);
    this.hudEnd.getBox().showBox(new Position(0.5, -0.3), new Position(0.5, 0.5), 1.2);
    this.currentStateGame = StateGame.End;
  }

  /**
   * Méthode lancant le menu principal
   * @throws UnsupportedAudioFileException
   * @throws IOException
   * @throws LineUnavailableException
   */
  @Override
  public void endWorld() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
    if (this.levelBackground != null) this.levelBackground.flush();
    if (this.pauseBackground != null) this.pauseBackground.flush();
    this.music.stop();
    super.endWorld();
  }

  /**
   * Evenement appelé dans la boucle de jeu avant d'update le monde
   * @throws UnsupportedAudioFileException
   * @throws IOException
   * @throws LineUnavailableException
   */
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
}
