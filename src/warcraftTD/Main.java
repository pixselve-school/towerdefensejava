package warcraftTD;

import warcraftTD.utils.Sound;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;

public class Main {

  public static void main(String[] args){
    int width = 1200;
    int height = 800;
    int nbSquareX = 11;
    int nbSquareY = 11;
    int startX = 1;
    int startY = 10;
    try {
      Sound gameMusic = new Sound("music/glorious.wav", true);
      gameMusic.play(0.25);
    } catch (LineUnavailableException | IOException | UnsupportedAudioFileException e) {
      e.printStackTrace();
    }


    World w = new World(width, height, nbSquareX, nbSquareY, startX, startY);

    w.initWave(100);


    // Lancement de la boucle principale du jeu
    w.run();
  }
}

