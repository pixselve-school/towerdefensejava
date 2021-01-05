package warcraftTD;

import warcraftTD.utils.Level;


import java.io.File;
import java.io.IOException;

public class Main {

  public static void main(String[] args) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
    int width = 1200;
    int height = 800;
    int nbSquareX = 11;
    int nbSquareY = 11;
    int startX = 1;
    int startY = 10;

    MainMenu menu = new MainMenu(width, height);
    menu.start();

    //WorldEditor we = new WorldEditor(width, height);

    //we.run();

    try {
      Sound gameMusic = new Sound("music/glorious.wav", true);
      gameMusic.play(0.25);
    } catch (LineUnavailableException | IOException | UnsupportedAudioFileException e) {
      e.printStackTrace();
    }

    World w = new World(width, height, nbSquareX, nbSquareY, startX, startY);

    //Level level = new Level(new File("levels/level.tdl"));
    //World world = level.getWorld();

    // Lancement de la boucle principale du jeu
    world.run();
  }
}

