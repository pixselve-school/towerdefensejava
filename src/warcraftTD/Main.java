package warcraftTD;

import warcraftTD.utils.Level;


import java.io.File;
import java.io.IOException;

public class Main {

  public static void main(String[] args) throws IOException {



    Level level = new Level(new File("levels/level.tdl"));
    World world = level.getWorld();


    // Lancement de la boucle principale du jeu
    world.run();
  }
}

