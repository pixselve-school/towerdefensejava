package warcraftTD.utils;

import warcraftTD.WorldGame;
import warcraftTD.hud.MainMenu;
import warcraftTD.monsters.Wave;
import warcraftTD.monsters.entities.*;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Outil pour la création d'un niveau
 */
public class Level {
  /**
   * Chemin vers la musique du niveau
   */
  private String musicPath;
  /**
   * Nombre de points de vie du joueur
   */
  private int life;
  /**
   * Quantité d'argent du joueur
   */
  private int money;
  /**
   * Positions du chemin
   */
  private LinkedList<Position> pathPositions;
  /**
   * Nombre de tuiles sur l'axe X
   */
  private int nbSquareX;
  /**
   * Nombre de tuiles sur l'axe Y
   */
  private int nbSquareY;
  /**
   * Vagues
   */
  private final List<Wave> waves;
  /**
   * Donnée des vagues
   */
  private List<WaveData> waveData;
  /**
   * Décalage de la carte
   */
  private Padding padding;
  /**
   * Indique si de l'eau doit être générée
   */
  private boolean waterActivated;
  /**
   * Indique la présence de végétation
   */
  private PlantPresence plants;

  /**
   * Création d'un niveau
   *
   * @param fileToParse Fichier .tld à charcger
   * @throws IOException Le fichier n'éxiste pas
   */
  public Level(File fileToParse) throws IOException {
    BufferedReader br = new BufferedReader(new FileReader(fileToParse));
    String st;

    this.musicPath = "music/glorious.wav";
    this.waves = new LinkedList<>();
    this.waterActivated = true;
    this.life = 20;
    this.money = 200;
    this.pathPositions = new LinkedList<>();
    this.plants = PlantPresence.Full;

    while ((st = br.readLine()) != null) {
      if (st.matches("^MUSIC_PATH=(.+)\\.wav$")) {
        this.musicPath = this.parseMusic(this.parseConfig(st));
      } else if (st.matches("^LIFE=\\d+$")) {
        this.life = this.parseLife(this.parseConfig(st));
      } else if (st.matches("^MONEY=\\d+$")) {
        this.money = this.parseMoney(this.parseConfig(st));
      } else if (st.matches("^WATER=[10]$")) {
        this.waterActivated = this.parseWaterActivation(this.parseConfig(st));
      } else if (st.matches("^PLANTS=[012]$")) {
        this.plants = this.parsePlantPresence(this.parseConfig(st));
      } else if (st.matches("^PADDING=(\\d+,){3}\\d+$")) {
        this.padding = this.parsePadding(this.parseConfig(st));
      } else if (st.matches("^PATH=(\\d+[UDLR])+$")) {
        PathData pathData = this.parsePath(this.parseConfig(st));
        this.pathPositions = pathData.pathPositions;
        this.nbSquareX = pathData.getNbSquareX();
        this.nbSquareY = pathData.getNbSquareY();
      } else if (st.matches("^WAVES=(\\[(\\((\\d*\\.?\\d*)+,(\\d*\\.?\\d*)+\\)_?)+\\];?)+$")) {
        this.waveData = this.parseWaves(this.parseConfig(st));
      } else {
        throw new Error("Error parsing the line '" + st + "'");
      }
    }
    br.close();

    if (this.padding == null) {
      if (this.nbSquareY >= this.nbSquareX) {
        int squaresMissingX = (int) Math.round(this.nbSquareY * 1.5) - this.nbSquareX;
        this.padding = new Padding(0, 0, squaresMissingX / 2, squaresMissingX / 2 + squaresMissingX % 2);
      } else if (Math.ceil(this.nbSquareX / 1.5) > this.nbSquareY) {
        int squaresMissingY = (int) Math.ceil(this.nbSquareX / 1.5) - this.nbSquareY;
        this.padding = new Padding(squaresMissingY / 2, squaresMissingY / 2 + squaresMissingY % 2, 0, 0);
      } else {
        this.padding = new Padding(0, 0, 0, 0);
      }
    }
    if (this.waterActivated) {
      this.padding = new Padding(this.padding.getTop() + 1, this.padding.getBottom() + 1, this.padding.getLeft() + 1, this.padding.getRight() + 1);
    }
    this.nbSquareY += this.padding.getBottom() + this.padding.getTop();
    this.nbSquareX += this.padding.getLeft() + this.padding.getRight();
    this.pathPositions = this.pathPositions.stream().map(position -> new Position(position.getX() + this.padding.getLeft(), position.getY() + this.padding.getBottom())).collect(Collectors.toCollection(LinkedList::new));

    List<Position> pathPositionsInWorld = this.pathPositions.stream().map(position -> new Position(position.getX() * this.getSquareWidth() + this.getSquareWidth() / 2, position.getY() * this.getSquareHeight() + this.getSquareHeight() / 2)).collect(Collectors.toCollection(LinkedList::new));


    for (int i = 0; i < this.waveData.size(); i++) {
      WaveData waveDatum = this.waveData.get(i);
      Wave wave;
      if (i == 0) {
        wave = new Wave(20);
      } else {
        wave = new Wave();
      }
      for (WaveData.MonsterData monster : waveDatum.monsters) {
        switch (monster.id) {
          default:
            wave.addMonster(new Scorpion(pathPositionsInWorld), monster.timeBeforeSpawning);
            break;
          case 2:
            wave.addMonster(new Mage(pathPositionsInWorld), monster.timeBeforeSpawning);
            break;
          case 3:
            wave.addMonster(new OgreWithMallet(pathPositionsInWorld), monster.timeBeforeSpawning);
            break;
          case 4:
            wave.addMonster(new ScienceKnight(pathPositionsInWorld), monster.timeBeforeSpawning);
            break;
          case 5:
            wave.addMonster(new OgreKnight(pathPositionsInWorld), monster.timeBeforeSpawning);
            break;
          case 6:
            wave.addMonster(new FoolKnight(pathPositionsInWorld), monster.timeBeforeSpawning);
            break;
          case 7:
            wave.addMonster(new BlueOgre(pathPositionsInWorld), monster.timeBeforeSpawning);
            break;
          case 8:
            wave.addMonster(new StoneChild(pathPositionsInWorld), monster.timeBeforeSpawning);
            break;
          case 9:
            wave.addMonster(new FlyingNinja(pathPositionsInWorld), monster.timeBeforeSpawning);
            break;
          case 10:
            wave.addMonster(new StoneGiant(pathPositionsInWorld), monster.timeBeforeSpawning);
            break;
        }
      }
      this.waves.add(wave);
    }


  }

  /**
   * Récupère la largeur d'une tuile
   *
   * @return La largeur d'une tuile
   */
  public double getSquareWidth() {
    return 1.0 / this.nbSquareX;
  }

  /**
   * Récupère la hauteur d'une tuile
   *
   * @return La hauteur d'une tuile
   */
  public double getSquareHeight() {
    return 1.0 / this.nbSquareY;
  }

  /**
   * Extrait le paramètre d'une chaine de caractère de configuration
   *
   * @param stringToParse La chaine de caractère à traiter
   * @return Le paramètre de la chaine de caractère
   */
  private String parseConfig(String stringToParse) {
    return stringToParse.split("=")[1];
  }

  /**
   * Récupère la quantité d'argent du joueur depuis une chaine de caractère
   *
   * @param stringToParse La chaine de caractère à traiter
   * @return La quantité d'argent du joueur
   */
  private int parseMoney(String stringToParse) {
    int moneyParsed = Integer.parseInt(stringToParse);
    if (moneyParsed < 50 || moneyParsed > 999999) {
      throw new Error("Invalid money number");
    }
    return moneyParsed;
  }

  /**
   * Récupère le chemin vers la musique du niveau depuis une chaine de caractère
   *
   * @param stringToParse La chaine de caractère à traiter
   * @return Le chemin vers la musique du niveau
   */
  private String parseMusic(String stringToParse) {
    File tempFile = new File(stringToParse);
    if (!tempFile.exists()) {
      throw new Error("Can't find the music file");
    }
    return stringToParse;
  }

  /**
   * Récupère le nombre de points de vie du joueur depuis une chaine de caractère
   *
   * @param stringToParse La chaine de caractère à traiter
   * @return Le nombre de points de vie du joueur
   */
  private int parseLife(String stringToParse) {
    int lifeParsed = Integer.parseInt(stringToParse);
    if (lifeParsed <= 0 || lifeParsed > 9999) {
      throw new Error("Invalid life number");
    }
    return lifeParsed;
  }

  /**
   * Récupère les données du chemin depuis une chaine de caractère
   *
   * @param stringToParse La chaine de caractère à traiter
   * @return Les données du chemin
   */
  private PathData parsePath(String stringToParse) {
    PathData pathData = new PathData();

    String[] instructions = stringToParse.split("(?<=\\D)(?=\\d)");

    for (String position : instructions) {
      String[] lengthAndDirection = position.split("(?<=\\d)(?=\\D)");
      int length = Integer.parseInt(lengthAndDirection[0]);
      for (int i = 1; i <= length; i++) {
        Position lastPosition = pathData.pathPositions.getLast();
        switch (lengthAndDirection[1]) {
          case "U":
            pathData.pathPositions.add(new Position(lastPosition.getX(), lastPosition.getY() + 1));
            break;
          case "D":
            pathData.pathPositions.add(new Position(lastPosition.getX(), lastPosition.getY() - 1));
            break;
          case "L":
            pathData.pathPositions.add(new Position(lastPosition.getX() - 1, lastPosition.getY()));
            break;
          default:
            pathData.pathPositions.add(new Position(lastPosition.getX() + 1, lastPosition.getY()));
            break;
        }
        pathData.minX = Math.min(pathData.minX, pathData.pathPositions.getLast().getX());
        pathData.minY = Math.min(pathData.minY, pathData.pathPositions.getLast().getY());

        pathData.maxX = Math.max(pathData.maxX, pathData.pathPositions.getLast().getX());
        pathData.maxY = Math.max(pathData.maxY, pathData.pathPositions.getLast().getY());
      }
    }
    pathData.maxX -= pathData.minX;
    pathData.maxY -= pathData.minY;
    pathData.pathPositions = pathData.pathPositions.stream().map(position -> new Position(position.getX() - pathData.minX, position.getY() - pathData.minY)).collect(Collectors.toCollection(LinkedList::new));

    return pathData;
  }

  /**
   * Récupère le décalage de la carte depuis une chaine de caractère
   *
   * @param stringToParse La chaine de caractère à traiter
   * @return Le décalage de la carte
   */
  private Padding parsePadding(String stringToParse) {
    int[] paddingValues = Arrays.stream(stringToParse.split(",")).mapToInt(Integer::parseInt).toArray();
    return new Padding(paddingValues[0], paddingValues[1], paddingValues[2], paddingValues[3]);
  }

  /**
   * Récupère l'indication sur la présence d'eau depuis une chaine de caractère
   *
   * @param stringToParse La chaine de caractère à traiter
   * @return L'indication sur la présence d'eau
   */
  private boolean parseWaterActivation(String stringToParse) {
    return stringToParse.equals("1");
  }

  /**
   * Récupère l'indication sur la présence de végétation depuis une chaine de caractère
   *
   * @param stringToParse La chaine de caractère à traiter
   * @return L'indication sur la présence de végétation
   */
  private PlantPresence parsePlantPresence(String stringToParse) {
    switch (stringToParse) {
      case "0":
        return PlantPresence.Absent;
      case "1":
        return PlantPresence.Small;
      default:
        return PlantPresence.Full;
    }
  }

  /**
   * Récupère les données des vagues depuis une chaine de caractère
   *
   * @param stringToParse La chaine de caractère à traiter
   * @return Les données des vagues
   */
  private List<WaveData> parseWaves(String stringToParse) {
    List<WaveData> wavesResult = new LinkedList<>();
    String stringToParseWithoutDistractions = stringToParse.replaceAll("[\\[()\\]]", "");
    String[] waves = stringToParseWithoutDistractions.split(";");
    for (String waveString : waves) {
      WaveData wave = new WaveData();
      String[] monstersData = waveString.split("_");
      for (String monsterData : monstersData) {
        String[] idAndDelay = monsterData.split(",");
        int id = Integer.parseInt(idAndDelay[0]);
        double delay = Double.parseDouble(idAndDelay[1]);
        wave.monsters.add(new WaveData.MonsterData(id, delay));
      }
      wavesResult.add(wave);

    }
    return wavesResult;
  }

  /**
   * Génère un monde depuis le niveau
   *
   * @param menu Le menu du monde
   * @return Le monde fraichement généré
   * @throws UnsupportedAudioFileException Erreur lié à l'audio
   * @throws IOException                   Erreur lié à l'audio
   * @throws LineUnavailableException      Erreur lié à l'audio
   */
  public WorldGame getWorld(MainMenu menu) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
    return new WorldGame(menu.getWidth(), menu.getHeight(), this.nbSquareX, this.nbSquareY, this.money, this.life, this.waterActivated, this.musicPath, this.pathPositions, this.waves, menu, this.plants);
  }

  /**
   * Données des chemins
   */
  private static class PathData {
    /**
     * Positions des chemins
     */
    public LinkedList<Position> pathPositions;
    /**
     * Position X minimum
     */
    public double minX;
    /**
     * Position Y minimum
     */
    public double minY;

    /**
     * Position X maximum
     */
    public double maxX;
    /**
     * Position Y maximum
     */
    public double maxY;

    /**
     * Création d'une nouvelle donnée de chemin
     */
    public PathData() {
      this.pathPositions = new LinkedList<>();
      this.pathPositions.add(new Position(0, 0));
      this.minX = 0.0;
      this.minY = 0.0;
      this.maxX = 0.0;
      this.maxY = 0.0;
    }

    /**
     * Récupère le nombre de tuiles sur l'axe X
     *
     * @return Le nombre de tuiles sur l'axe X
     */
    public int getNbSquareX() {
      return (int) (this.maxX + 1);
    }

    /**
     * Récupère le nombre de tuiles sur l'axe Y
     *
     * @return Le nombre de tuiles sur l'axe Y
     */
    public int getNbSquareY() {
      return (int) (this.maxY + 1);
    }
  }

  /**
   * Donnée de vague
   */
  private static class WaveData {
    /**
     * Les monstres
     */
    public List<MonsterData> monsters;

    /**
     * Création d'une nouvelle donnée de vague
     */
    public WaveData() {
      this.monsters = new LinkedList<>();
    }

    /**
     * Donnée de monstre
     */
    private static class MonsterData {
      /**
       * ID du monstre
       */
      public int id;
      /**
       * Temps avant apparition du monstre
       */
      public double timeBeforeSpawning;

      /**
       * Création d'une nouvelle donnée de monstre
       *
       * @param id                 ID du monstre
       * @param timeBeforeSpawning Temps avant apparition du monstre
       */
      public MonsterData(int id, double timeBeforeSpawning) {
        this.id = id;
        this.timeBeforeSpawning = timeBeforeSpawning;
      }
    }
  }
}

