package warcraftTD;

import warcraftTD.hud.MainMenu;
import warcraftTD.utils.Level;
import warcraftTD.utils.Sound;


import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;

public class Main {

  public static void main(String[] args) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
    int width = 1200;
    int height = 800;

    MainMenu menu = new MainMenu(width, height);
    menu.run();
  }
}