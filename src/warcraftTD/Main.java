package warcraftTD;

public class Main {


  public static void main(String[] args) {
    int width = 1200;
    int height = 800;
    int nbSquareX = 11;
    int nbSquareY = 11;
    int startX = 1;
    int startY = 10;

    World w = new World(width, height, nbSquareX, nbSquareY, startX, startY);

    // Ajout d'un monstre "à la main" pour afficher comment un monstre se déplaçe. Vous ne devez pas faire pareil, mais ajouter une vague comportant plusieurs monstres
    Monster monster = new BaseMonster(new Position(startX * w.squareWidth + w.squareWidth / 2, startY * w.squareHeight + w.squareHeight / 2));
    monster.nextP = new Position(startX * w.squareWidth + w.squareWidth / 2, 0);
    monster.speed = 0.01;
    w.monsters.add(monster);

    // Lancement de la boucle principale du jeu
    w.run();
  }
}

