package warcraftTD.hud;

import warcraftTD.World;
import warcraftTD.WorldEditor;
import warcraftTD.libs.StdDraw;
import warcraftTD.utils.Animation;
import warcraftTD.utils.Level;
import warcraftTD.utils.MonsterDieCallback;
import warcraftTD.utils.Position;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/**
 * Interface du menu principal
 */
public class MainMenu extends Interface {
  /**
   * Group Box contenant les boutons de selection de niveaux
   */
  private GroupBox groupBox;
  /**
   * Booléen spécifiant si l'on quitte la boucle principale du menu
   */
  private boolean quit;
  /**
   * Temps d'execution d'un tick en seconde
   */
  private double deltaTime;
  /**
   * Booléen spécifiaitn si il faut relacher la souris avec de recliquer
   */
  private boolean needReleaseMouse;
  /**
   * Animation de fond du menu
   */
  private Animation background;
  /**
   * Numéro de partie de vidéo de fond du menu
   */
  private int index_background;
  /**
   * Monde à lancer quand on quitte le menu
   */
  private World nextWorld;
  /**
   * Image du titre du jeu
   */
  private Image title;
  /**
   * Largeur de la fenetre
   */
  private int width;
  /**
   * Hauteur de la fenetre
   */
  private int height;

  /**
   * Animation du logo
   */
  private Animation logoAnimation;

  /**
   * Récupère la largeur de la fenetre
   *
   * @return la largeur de la fenetre
   */
  public int getWidth() {
    return this.width;
  }

  /**
   * Récupère la hauteur de la fenetre
   *
   * @return la hauteur de la fenetre
   */
  public int getHeight() {
    return this.height;
  }

  /**
   * Spécifie si on doit relacher la souris avant de recliquer
   *
   * @param needReleaseMouse un booléen indiquant si on doit relacher la souris avant de recliquer
   */
  public void setNeedReleaseMouse(boolean needReleaseMouse) {
    this.needReleaseMouse = needReleaseMouse;
  }

  /**
   * Initialise le menu principal
   *
   * @param width  la largeur de la fenetre
   * @param height la hauteur de la fenetre
   * @throws UnsupportedAudioFileException
   * @throws IOException
   * @throws LineUnavailableException
   */
  public MainMenu(int width, int height) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
    StdDraw.setCanvasSize(width, height);
    StdDraw.enableDoubleBuffering();

    this.width = width;
    this.height = height;

    this.nextWorld = null;

    index_background = 0;
    loadBackground("images/mainMenuBackground/game0/videMenu_", true);
    this.groupBox = new GroupBox(new Position(0.5, 0.5), 0.4, 0.6, this, "");
    this.getListElements().add(this.groupBox);

    Button btn = new Button(new Position(0.5, 0.7), 0.2, 0.1, "images/mm_button_lvl1.png", "images/mm_button_lvl1_hover.png", "lvl1", this);
    this.groupBox.addHUDElement(btn);
    btn = new Button(new Position(0.5, 0.5), 0.2, 0.1, "images/mm_button_lvl2.png", "images/mm_button_lvl2_hover.png", "lvl2", this);
    this.groupBox.addHUDElement(btn);
    btn = new Button(new Position(0.5, 0.3), 0.2, 0.1, "images/mm_button_load.png", "images/mm_button_load_hover.png", "load", this);
    this.groupBox.addHUDElement(btn);
    btn = new Button(new Position(0.5, 0.1), 0.2, 0.1, "images/mm_button_quit.png", "images/mm_button_quit_hover.png", "quit", this);
    this.groupBox.addHUDElement(btn);

    this.groupBox.showBox(new Position(0.5, -0.25), new Position(0.5, 0.5), 0.75);

    btn = new Button(new Position(0.105, 0.055), 0.2, 0.1, "images/leveleditor_button.png", "images/leveleditor_button_hover.png", "leveleditor", this);
    this.getListElements().add(btn);

    btn = new Button(new Position(0.9, 0.05), 0.06, 0.08, "images/Capybara_Button.png", "images/Capybara_Button_hover.png", "switchCapybara", this);
    this.getListElements().add(btn);
    btn = new Button(new Position(0.965, 0.05), 0.06, 0.08, "images/Cat_Button.png", "images/Cat_Button_hover.png", "switchCat", this);
    this.getListElements().add(btn);

    this.quit = false;
    this.deltaTime = 0.0;
    this.needReleaseMouse = false;

    List<String> logoFrames = new LinkedList<>();
      for (int i = 0; i < 60; i++) {
          logoFrames.add("images/logo/animation/logo" + (i >= 10 ? i : "0" + i) + ".png");
      }

    this.logoAnimation = new Animation(logoFrames.toArray(String[]::new), 0.25, 0.25, new Position(0.5,0.85), 60, true);
  }

  /**
   * Charge l'animation de fond en fonction du chemin d'accès des images
   *
   * @param path chemin d'accès des images
   */
  public void loadBackground(String path, boolean menuOfficial) {
    String[] backgroundImages = new String[300];
    for (int i = 0; i < 300; i++) {
      backgroundImages[i] = path + (i < 100 ? "0" : "") + (i < 10 ? "0" : "") + i + ".jpg";
    }
    this.background = new Animation(backgroundImages, 1, 1, new Position(0.5, 0.5), 24, false);
    if(menuOfficial) {
      this.index_background++;
      if(this.index_background==2) this.index_background = 0;
    }
    this.background.setCallback(() -> this.loadBackground("images/mainMenuBackground/game"+this.index_background+"/videMenu_", true));
  }

  /**
   * Actualise la logique de l'interface et affiche son apparence
   *
   * @param mouseX    la position horizontale de la souris
   * @param mouseY    la position verticale de la souris
   * @param deltaTime le temps d'un tick en seconde
   */
  @Override
  public void updateInterface(double mouseX, double mouseY, double deltaTime) {
    this.background.draw(deltaTime);
    this.logoAnimation.draw(deltaTime);
    super.updateInterface(mouseX, mouseY, deltaTime);
  }

  /**
   * Demande de consumer le click (éviter de pouvoir rester appuyer)
   */
  @Override
  public void consumeClick() {
    this.setNeedReleaseMouse(true);
  }

  /**
   * Réalise une action sur l'interface
   *
   * @param action l'action à réaliser
   * @param from   l'élément d'où vient l'action à réaliser
   * @throws IOException
   * @throws LineUnavailableException
   * @throws UnsupportedAudioFileException
   */
  @Override
  public void makeAction(String action, Element from) throws IOException, LineUnavailableException, UnsupportedAudioFileException {
    switch (action) {
      case "quit":
        System.exit(0);
        break;
      case "load":
        this.needReleaseMouse = true;
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File("levels/"));
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Tower Defense Level", "tdl", "tdl");
        fileChooser.setFileFilter(filter);

        int result = fileChooser.showOpenDialog(StdDraw.getFrame());
        if (result == JFileChooser.APPROVE_OPTION) {
          File selectedLvl = fileChooser.getSelectedFile();
          Level level = new Level(selectedLvl);
          nextWorld = level.getWorld(this);
          this.quit = true;
        }
        break;
      case "lvl1":
        this.needReleaseMouse = true;
        File lvl1 = new File("levels/level1_official.tdl");
        Level level = new Level(lvl1);
        nextWorld = level.getWorld(this);
        this.quit = true;
        break;
      case "lvl2":
        this.needReleaseMouse = true;
        File lvl2 = new File("levels/level2.tdl");
        Level level2 = new Level(lvl2);
        nextWorld = level2.getWorld(this);
        this.quit = true;
        break;
      case "switchCapybara":
        this.needReleaseMouse = true;
        loadBackground("images/mainMenuBackground/capybara/capybara_",false);
        break;
      case "switchCat":
        this.needReleaseMouse = true;
        loadBackground("images/mainMenuBackground/kitten/The most dangerous kitten in the world_",false);
        break;
      case "leveleditor":
        nextWorld = new WorldEditor(1200, 800, this);
        this.quit = true;
        break;
    }
  }

  /**
   * Lance le menu, lance des mondes quand on quitte le menu
   *
   * @throws UnsupportedAudioFileException
   * @throws IOException
   * @throws LineUnavailableException
   */
  public void run() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
    this.quit = false;
    this.nextWorld = null;
    while (nextWorld == null) {
      this.startLoop();
    }
    this.nextWorld.setNeedReleaseMouse(true);
    this.nextWorld.run();
  }

  /**
   * Lance la boucle principale du menu
   *
   * @throws UnsupportedAudioFileException
   * @throws IOException
   * @throws LineUnavailableException
   */
  public void startLoop() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
    while (!quit) {
      long time_nano = System.nanoTime();

      StdDraw.clear();

      if (StdDraw.isMousePressed()) {
        if (!this.needReleaseMouse) this.onClick(StdDraw.mouseX(), StdDraw.mouseY(), StdDraw.mouseButtonPressed());
      } else if (this.needReleaseMouse) {
        this.needReleaseMouse = false;
      }
      this.updateInterface(StdDraw.mouseX(), StdDraw.mouseY(), deltaTime);

      StdDraw.show();

      int ms = (int) (System.nanoTime() - time_nano) / 1000000;
      int fps = 1000 / ms;
      this.deltaTime = 1.0 / fps;

    }
  }
}
