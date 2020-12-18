package warcraftTD;

import java.util.List;

import java.util.ArrayList;
import java.util.Iterator;

public class World {
	// l'ensemble des monstres, pour gerer (notamment) l'affichage
	List<Monster> monsters = new ArrayList<Monster>();

	// l'ensemble des cases du chemin
	List<Position> paths = new ArrayList<Position>();

	// L'interface du jeu
	Interface HUD;

	// le porte monnaie du joueur
	Wallet player_wallet;

	// représente le temps pour chaque tick en s
	double delta_time;

	// Position par laquelle les monstres vont venir
	Position spawn;
	
	// Information sur la taille du plateau de jeu
	int width;
	int height;
	int nbSquareX;
	int nbSquareY;
	double squareWidth;
	double squareHeight;
	
	// Nombre de points de vie du joueur
	int life = 20;
	
	// Commande sur laquelle le joueur appuie (sur le clavier)
	char key;
	
	// Condition pour terminer la partie
	boolean end = false;
	
	/**
	 * Initialisation du monde en fonction de la largeur, la hauteur et le nombre de cases données
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
		squareWidth = (double) 1 / nbSquareX;
		squareHeight = (double) 1 / nbSquareY;
		spawn = new Position(startSquareX * squareWidth + squareWidth / 2, startSquareY * squareHeight + squareHeight / 2);
		StdDraw.setCanvasSize(width, height);
		StdDraw.enableDoubleBuffering();
		delta_time = 0.0;

		this.player_wallet = new Wallet();
		this.HUD = new Interface(player_wallet);


		// Chemin temporaire
		paths.add(new Position(1,10));
		paths.add(new Position(1,9));
		paths.add(new Position(1,8));
		paths.add(new Position(1,7));
		paths.add(new Position(1,6));
		paths.add(new Position(1,5));
		paths.add(new Position(1,4));
		paths.add(new Position(1,3));
		paths.add(new Position(1,2));
		paths.add(new Position(2,2));
		paths.add(new Position(3,2));
		paths.add(new Position(4,2));
		paths.add(new Position(4,3));
		paths.add(new Position(4,4));
		paths.add(new Position(4,5));
		paths.add(new Position(4,6));
		paths.add(new Position(4,7));
		paths.add(new Position(4,8));
		paths.add(new Position(4,9));
		paths.add(new Position(5,9));
		paths.add(new Position(6,9));
		paths.add(new Position(7,9));
		paths.add(new Position(8,9));
		paths.add(new Position(8,8));
		paths.add(new Position(8,7));
		paths.add(new Position(8,6));
		paths.add(new Position(8,5));
		paths.add(new Position(8,4));
		paths.add(new Position(8,3));
		paths.add(new Position(8,2));
		paths.add(new Position(8,1));
		paths.add(new Position(8,0));
	}
	
	/**
	 * Définit le décors du plateau de jeu.
	 */
	 public void drawBackground() {	
		 StdDraw.setPenColor(StdDraw.LIGHT_GREEN);
		 for (int i = 0; i < nbSquareX; i++)
			 for (int j = 0; j < nbSquareY; j++)
				 //StdDraw.filledRectangle(i * squareWidth + squareWidth / 2, j * squareHeight + squareHeight / 2, squareWidth , squareHeight);
				 StdDraw.picture(i * squareWidth + squareWidth / 2, j * squareHeight + squareHeight / 2, "images/grass.jpg", squareWidth, squareHeight);
	 }
	 
	 /**
	  * Initialise le chemin sur la position du point de départ des monstres. Cette fonction permet d'afficher une route qui sera différente du décors.
	  */
	 public void drawPath() {
		 Iterator<Position> i = paths.iterator();
		 Position p;
		 while (i.hasNext()) {
		 	p = i.next();
			 StdDraw.setPenColor(StdDraw.YELLOW);
			 double coorX = p.x / nbSquareX + (squareWidth/2);
			 double coorY = p.y / nbSquareY + (squareHeight/2);
			 //StdDraw.filledRectangle(coorX, coorY, squareWidth / 2, squareHeight / 2);
			 StdDraw.picture(coorX, coorY, "images/sand.jpg", squareWidth, squareHeight);
		 }
	 }
	 
	 /**
	  * Affiche certaines informations sur l'écran telles que les points de vie du joueur ou son or
	  */
	 public void drawInfos() {
		 HUD.UpdateInterface(StdDraw.mouseX(), StdDraw.mouseY(), delta_time);
	 }
	 
	 /**
	  * Fonction qui récupère le positionnement de la souris et permet d'afficher une image de tour en temps réél
	  *	lorsque le joueur appuie sur une des touches permettant la construction d'une tour.
	  */
	 public void drawMouse() {
		double normalizedX = (int)(StdDraw.mouseX() / squareWidth) * squareWidth + squareWidth / 2;
		double normalizedY = (int)(StdDraw.mouseY() / squareHeight) * squareHeight + squareHeight / 2;
		String image = null;
		//StdDraw.picture(normalizedX, normalizedY, "images/Select_tile.png", squareWidth, squareHeight);

		switch (key) {
		case 'a' : 
			 // TODO Ajouter une image pour représenter une tour d'archers
			 break;
		case 'b' :
			// TODO Ajouter une image pour représenter une tour à canon
			 break;
		}
		 if (image != null)
			 StdDraw.picture(normalizedX, normalizedY, image, squareWidth, squareHeight);

	 }
		 
	 /**
	  * Pour chaque monstre de la liste de monstres de la vague, utilise la fonction update() qui appelle les fonctions run() et draw() de Monster.
	  * Modifie la position du monstre au cours du temps à l'aide du paramètre nextP.
	  */
	 public void updateMonsters() {
	 
		Iterator<Monster> i = monsters.iterator();
		Monster m;
		while (i.hasNext()) {
			 m = i.next();
			 m.update();
			 if(m.p.y < 0) {
				 m.p.y = 1;
			 }
		 }
	 }
	 
	 /**
	  * Met à jour toutes les informations du plateau de jeu ainsi que les déplacements des monstres et les attaques des tours.
	  * @return les points de vie restants du joueur
	  */
	 public int update() {
		drawBackground();
		drawPath();
		updateMonsters();
		drawMouse();
		drawInfos();
		return life;
	 }
	 
	/**
	 * Récupère la touche appuyée par l'utilisateur et affiche les informations pour la touche séléctionnée
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
	 * 		- Ajouter une tour à la position indiquée par la souris.
	 * 		- Améliorer une tour existante.
	 * Puis l'ajouter à la liste des tours
	 * @param x
	 * @param y
	 */
	public void mouseClick(double x, double y) {
		double normalizedX = (int)(x / squareWidth) * squareWidth + squareWidth / 2;
		double normalizedY = (int)(y / squareHeight) * squareHeight + squareHeight / 2;
		Position p = new Position(normalizedX, normalizedY);
		HUD.onClick(x,y);
		switch (key) {
		case 'a':
			System.out.println("il faut ajouter une tour d'archers si l'utilisateur à de l'or !!");
			break;
		case 'b':
			System.out.println("Ici il faut ajouter une tour de bombes");
			break;
		case 'e':
			System.out.println("Ici il est possible de faire évolué une des tours");
			break;
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



	/**
	 * Récupère la touche entrée au clavier ainsi que la position de la souris et met à jour le plateau en fonction de ces interractions
	 */
	public void run() {
		printCommands();
		while(!end) {
			long time_nano = System.nanoTime();

			StdDraw.clear();
			/*if (StdDraw.hasNextKeyTyped()) {
				keyPress(StdDraw.nextKeyTyped());
			}*/
			
			if (StdDraw.isMousePressed()) {
				mouseClick(StdDraw.mouseX(), StdDraw.mouseY());
				//StdDraw.pause(50);
			}
			
			update();
			StdDraw.show();
			//StdDraw.pause(20);

			int ms = (int)(System.nanoTime() - time_nano) / 1000000;
			int fps = 1000 / ms;
			delta_time = 1.0 / fps;
		}
	}
}
