package warcraftTD;

import warcraftTD.hud.InterfaceJeu;
import warcraftTD.libs.StdDraw;
import warcraftTD.monsters.Monster;
import warcraftTD.monsters.Wave;
import warcraftTD.monsters.entities.ScienceKnight;
import warcraftTD.monsters.entities.Scorpion;
import warcraftTD.monsters.entities.StoneChild;
import warcraftTD.monsters.entities.StoneGiant;
import warcraftTD.towers.*;
import warcraftTD.utils.Position;
import warcraftTD.utils.Wallet;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.stream.Collectors;

public class World {
  // l'ensemble des monstres, pour gerer (notamment) l'affichage
  private List<Monster> monsters = new ArrayList<Monster>();
  private int totalMonsterAmount;


  // l'ensemble des cases du chemin
  private List<Position> paths = new ArrayList<Position>();

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

  Wave wave;

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
  public World(int width, int height, int nbSquareX, int nbSquareY, int startSquareX, int startSquareY) {
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

  /**
   * Définit le décors du plateau de jeu.
   */
  public void drawBackground() {
    //StdDraw.setPenColor(StdDraw.LIGHT_GREEN);
    StdDraw.picture(0.5, 0.5, "images/fondtest_complet.jpg", 1.0, 1.0);

//    for (int i = 0; i < nbSquareX; i++) {
//      for (int j = 0; j < nbSquareY; j++) {
//        StdDraw.filledRectangle(i * squareWidth + squareWidth / 2, j * squareHeight + squareHeight / 2, squareWidth , squareHeight);
//        StdDraw.picture(i * squareWidth + squareWidth / 2, j * squareHeight + squareHeight / 2, "images/grass.jpg", squareWidth, squareHeight);
//      }
//    }


  }

  /**
   * Initialise le chemin sur la position du point de départ des monstres. Cette fonction permet d'afficher une route qui sera différente du décors.
   */
  public void drawPath() {


//		 Iterator<Position> i = paths.iterator();
//		 Position p;
//		 while (i.hasNext()) {
//		 	p = i.next();
//			 StdDraw.setPenColor(StdDraw.YELLOW);
//			 double coorX = p.x / nbSquareX + (squareWidth/2);
//			 double coorY = p.y / nbSquareY + (squareHeight/2);
//			 //StdDraw.filledRectangle(coorX, coorY, squareWidth / 2, squareHeight / 2);
//			 StdDraw.picture(coorX, coorY, "images/sand.jpg", squareWidth, squareHeight);
//		 }

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

  double secondCounter = 0.0;

  /**
   * Pour chaque monstre de la liste de monstres de la vague, utilise la fonction update() qui appelle les fonctions run() et draw() de Monster.
   * Modifie la position du monstre au cours du temps à l'aide du paramètre nextP.
   */
  public void updateMonsters() {
    this.wave.spawn(this, this.delta_time);
    Iterator<Monster> i = this.monsters.iterator();
    Monster m;
    while (i.hasNext()) {
      m = i.next();
      m.update(this.delta_time);

      if (m.hasFinishedPath() && this.secondCounter >= 1.0) {
        this.life -= 1;
      }
      if (m.isDead()) {
        this.HUD.setWaveEnemyProgress(((100 * this.amountAliveMonsters()) / (double) this.totalMonsterAmount));
      }
      if (m.isReadyToBeRemoved()) {
        i.remove();
        this.player_wallet.addMoney(m.getGoldWhenDead());
      }
    }

//    if (this.monsters.size() == 0) {
//      this.isMonsterActing = false;
//      new java.util.Timer().schedule(
//          new java.util.TimerTask() {
//            @Override
//            public void run() {
//              World.this.initWave(World.this.totalMonsterAmount + 1);
//            }
//          },
//          5000
//      );
//    }

  }

  private int amountAliveMonsters() {
    return this.monsters.stream().filter(monster -> !monster.isDead()).collect(Collectors.toCollection(ArrayList::new)).size();
  }


  public void initWave(int monsterAmount) {
    this.HUD.setWaveEnemyProgress(100);
    this.totalMonsterAmount = monsterAmount;
    this.monsters = new ArrayList<>();
    this.wave = new Wave();
    for (int i = 0; i < monsterAmount; i++) {
      double random = Math.random();
      if (random < 0.2) {
        this.wave.addMonster(new Scorpion(new Position(this.paths.get(0).getX() * this.squareWidth + this.squareWidth / 2, this.paths.get(0).getY() * this.squareHeight + this.squareHeight / 2), this), 1);
      } else if (random < 0.4) {
        this.wave.addMonster(new ScienceKnight(new Position(this.paths.get(0).getX() * this.squareWidth + this.squareWidth / 2, this.paths.get(0).getY() * this.squareHeight + this.squareHeight / 2), this), 1);
      } else if (random < 0.6) {
        this.wave.addMonster(new StoneChild(new Position(this.paths.get(0).getX() * this.squareWidth + this.squareWidth / 2, this.paths.get(0).getY() * this.squareHeight + this.squareHeight / 2), this), 1);
      } else {
        this.wave.addMonster(new StoneGiant(new Position(this.paths.get(0).getX() * this.squareWidth + this.squareWidth / 2, this.paths.get(0).getY() * this.squareHeight + this.squareHeight / 2), this), 5);
      }

    }
  }

  public void updateTowers() {
    double normalizedX = (int) (StdDraw.mouseX() / this.squareWidth) * this.squareWidth + this.squareWidth / 2;
    double normalizedY = (int) (StdDraw.mouseY() / this.squareHeight) * this.squareHeight + this.squareHeight / 2;
    Position mousep = new Position((int) ((normalizedX * this.nbSquareX)), (int) ((normalizedY * this.nbSquareY)));
    Tower towerUnderMouse = this.list_tower.get(mousep);

    if(this.getHUD().getUpgradingTower()!=null){
      this.getHUD().getUpgradingTower().upgradingVisual();
    }
    if(towerUnderMouse!=null){
      towerUnderMouse.hoveredVisual();
    }

    for (Map.Entry<Position, Tower> entry : this.list_tower.entrySet()) {
      Tower value = entry.getValue();
      value.Update(this.delta_time);
    }
  }


  /**
   * Met à jour toutes les informations du plateau de jeu ainsi que les déplacements des monstres et les attaques des tours.
   *
   * @return les points de vie restants du joueur
   */
  public int update() {
    this.drawBackground();
    this.drawPath();
    this.updateMonsters();
    this.updateTowers();
    this.drawMouse();
    this.drawInfos();
    return this.life;
  }

  /**
   * Récupère la touche appuyée par l'utilisateur et affiche les informations pour la touche séléctionnée
   *
   * @param key la touche utilisée par le joueur
   */
  public void keyPress(char key) {
    key = Character.toLowerCase(key);
    this.key = key;
    switch (key) {
      case 'a':
        System.out.println("Arrow Tower selected (50g).");
        break;
      case 'b':
        System.out.println("Bomb Tower selected (60g).");
        break;
      case 'e':
        System.out.println("Evolution selected (40g).");
        break;
      case 's':
        System.out.println("Starting game!");
      case 'q':
        System.out.println("Exiting.");
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
  public void mouseClick(double x, double y, int mouseButton) {
    double normalizedX = (int) (x / this.squareWidth) * this.squareWidth + this.squareWidth / 2;
    double normalizedY = (int) (y / this.squareHeight) * this.squareHeight + this.squareHeight / 2;
    Position p = new Position(normalizedX, normalizedY);
    Position mousep = new Position((int) ((normalizedX * this.nbSquareX)), (int) ((normalizedY * this.nbSquareY)));

    if(this.HUD.onClick(x, y, mouseButton)) return;

    if (this.building_class != null && !this.needReleaseMouse) {
      if (!(this.paths.contains(mousep) || this.list_tower.containsKey(mousep))) {
        int price = this.price_tower.get(this.building_class);
        if (this.player_wallet.pay(price)) {
          try {
            Constructor cons = this.building_class.getConstructor(Position.class, double.class, double.class, World.class);
            Tower t = (Tower) cons.newInstance(new Position(normalizedX, normalizedY), this.squareWidth, this.squareHeight, this);
            this.list_tower.put(new Position((int) ((normalizedX * this.nbSquareX)), (int) ((normalizedY * this.nbSquareY))), t);
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

  /**
   * Comme son nom l'indique, cette fonction permet d'afficher dans le terminal les différentes possibilités
   * offertes au joueur pour intéragir avec le clavier
   */
  public void printCommands() {
    System.out.println("Press A to select Arrow Tower (cost 50g).");
    System.out.println("Press B to select Cannon Tower (cost 60g).");
    System.out.println("Press E to update a tower (cost 40g).");
    System.out.println("Click on the grass to build it.");
    System.out.println("Press S to start.");
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
    this.printCommands();
    while (!this.end) {
      long time_nano = System.nanoTime();

      StdDraw.clear();
			/*if (StdDraw.hasNextKeyTyped()) {
				keyPress(StdDraw.nextKeyTyped());
			}*/

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

      if (this.secondCounter >= 1.0) {
        this.secondCounter = 0.0;
      } else {
        this.secondCounter += this.delta_time;
      }

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

  public double getSecondCounter() {
    return this.secondCounter;
  }

  public void setSecondCounter(double secondCounter) {
    this.secondCounter = secondCounter;
  }

  public void addMonster(Monster monster) {
    this.monsters.add(monster);
  }
}
