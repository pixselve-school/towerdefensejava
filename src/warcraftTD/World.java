package warcraftTD;

import warcraftTD.hud.MainMenu;
import warcraftTD.libs.StdDraw;
import warcraftTD.utils.Position;
import warcraftTD.world.Tile;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

/**
 * Un monde
 */
public abstract class World {
    /** Largeur de la fenetre du monde */
    private double width;
    /** Hauteur de la fenetre du monde */
    private double height;
    /** Nombre de tuiles horizontalement */
    private int nbSquareX;
    /** Nombre de tuiles verticalement */
    private int nbSquareY;
    /** Spécifie si on arrête la boucle de jeu */
    private boolean end;
    /** Largeur d'une tuile */
    private double squareWidth;
    /** Hauteur d'une tuile */
    private double squareHeight;
    /** Référence vers le menu principal */
    private MainMenu menu;
    /** Spécifie si on doit relacher la souris avant de pouvoir recliquers */
    private boolean needReleaseMouse = false;
    /** Liste des positions des bouts de chemins */
    private List<Position> paths;
    /** Le temps d'éxecution d'un tick en secondes */
    private double deltaTime;
    /** Arbre contenant les tuiles associés à leur position */
    private TreeMap<Position, Tile> positionTileMap;

    /**
     * Récupère l'arbre contenant les tuiles associés à leur position
     * @return l'arbre contenant les tuiles associés à leur position
     */
    public TreeMap<Position, Tile> getPositionTileMap() {
        return this.positionTileMap;
    }

    /**
     * Modifie l'arbre contenant les tuiles associés à leur position
     * @param positionTileMap l'arbre contenant les tuiles associés à leur position
     */
    public void setPositionTileMap(TreeMap<Position, Tile> positionTileMap) {
        this.positionTileMap = positionTileMap;
    }

    /**
     * Récupère la référence vers le menu principal
     * @return la référence vers le menu principal
     */
    public MainMenu getMenu() {
        return this.menu;
    }

    /**
     * Modifie la référence vers le menu principal
     * @param menu la référence vers le menu principal
     */
    public void setMenu(MainMenu menu) {
        this.menu = menu;
    }

    /**
     * Récupère la largeur de la fenetre de jeu
     * @return la largeur de la fenetre de jeu
     */
    public double getWidth() {
        return this.width;
    }

    /**
     * Modifie la largeur de la fenetre de jeu
     * @param width la largeur de la fenetre de jeu
     */
    public void setWidth(double width) {
        this.width = width;
    }

    /**
     * Récupère la hauteur de la fenetre de jeu
     * @return la hauteur de la fenetre de jeu
     */
    public double getHeight() {
        return this.height;
    }

    /**
     * Modifie la hauteur de la fenetre de jeu
     * @param height la hauteur de la fenetre de jeu
     */
    public void setHeight(double height) {
        this.height = height;
    }

    /**
     * Récupère le nombre de tuiles horizontalement
     * @return le nombre de tuiles horizontalement
     */
    public int getNbSquareX() {
        return this.nbSquareX;
    }

    /**
     * Modifie le nombre de tuiles horizontalement
     * @param nbSquareX le nombre de tuiles horizontalement
     */
    public void setNbSquareX(int nbSquareX) {
        this.nbSquareX = nbSquareX;
    }

    /**
     * Récupère le nombre de tuiles verticalement
     * @return le nombre de tuiles verticalement
     */
    public int getNbSquareY() {
        return this.nbSquareY;
    }

    /**
     * Modifie le nombre de tuiles verticalement
     * @param nbSquareY le nombre de tuiles verticalement
     */
    public void setNbSquareY(int nbSquareY) {
        this.nbSquareY = nbSquareY;
    }

    /**
     * Modifie l'état d'arret de la boucle update
     * @param end l'état d'arret de la boucle update
     */
    public void setEnd(boolean end) {
        this.end = end;
    }

    /**
     * Récupère la largeur d'une tuile
     * @return la largeur d'une tuile
     */
    public double getSquareWidth() {
        return this.squareWidth;
    }

    /**
     * Modifie la largeur d'une tuile
     * @param squareWidth la largeur d'une tuile
     */
    public void setSquareWidth(double squareWidth) {
        this.squareWidth = squareWidth;
    }

    /**
     * Récupère la hauteur d'une tuile
     * @return la hauteur d'une tuile
     */
    public double getSquareHeight() {
        return this.squareHeight;
    }

    /**
     * Modifie la hauteur d'une tuile
     * @param squareHeight la hauteur d'une tuile
     */
    public void setSquareHeight(double squareHeight) {
        this.squareHeight = squareHeight;
    }

    /**
     * Récupère un booléen indiquant si on doit relacher la souris avant de recliquer
     * @return un booléen indiquant si on doit relacher la souris avant de recliquer
     */
    public boolean isNeedReleaseMouse() {
        return this.needReleaseMouse;
    }

    /**
     * Spécifie si on doit relacher la souris avant de recliquer
     * @param needReleaseMouse un booléen indiquant si on doit relacher la souris avant de recliquer
     */
    public void setNeedReleaseMouse(boolean needReleaseMouse) {
        this.needReleaseMouse = needReleaseMouse;
    }

    /**
     * Récupère la liste des positions des bouts de chemins
     * @return la liste des positions des bouts de chemins
     */
    public List<Position> getPaths() {
        return this.paths;
    }

    /**
     * Modifie la liste des positions des bouts de chemins
     * @param paths la liste des positions des bouts de chemins
     */
    public void setPaths(List<Position> paths) {
        this.paths = paths;
    }

    /**
     * Récupère le temps d'éxecution d'un tick en seconde
     * @return le temps d'éxecution d'un tick en seconde
     */
    public double getDeltaTime() {
        return this.deltaTime;
    }

    /**
     * Modifie la valeur représentant le temps d'éxecution d'un tick en seconde
     * @param deltaTime le temps d'éxecution d'un tick en seconde
     */
    public void setDeltaTime(double deltaTime) {
        this.deltaTime = deltaTime;
    }

    /**
     * Initialise un monde
     * @param width largeur de la fenetre
     * @param height hauteur de la fenetre
     * @param menu Menu Principal
     */
    public World(int width, int height, MainMenu menu){
        this.width = width;
        this.height = height;
        this.menu = menu;

        StdDraw.setCanvasSize(width, height);
        StdDraw.enableDoubleBuffering();

        this.deltaTime = 0.0;
        this.paths = new ArrayList<Position>();
        this.end = false;
    }

    /**
     * Récupère la position de la tuile sous la souris
     * @return la position de la tuile sous la souris
     */
    public Position getTilePositionUnderMouse(){
        return new Position((int) Math.floor(StdDraw.mouseX() * this.getNbSquareX()), (int) Math.floor(StdDraw.mouseY() * this.getNbSquareY()));
    }

    /**
     * Récupère la position normalisé (0<=p<=1) de la tuile sous la souris
     * @return la position normalisé (0<=p<=1) de la tuile sous la souris
     */
    public Position getTileNormalizedPositionUnderMouse(){
        return new Position((int) (StdDraw.mouseX() / this.getSquareWidth()) * this.getSquareWidth() + this.getSquareWidth() / 2, (int) (StdDraw.mouseY() / this.getSquareHeight()) * this.getSquareHeight() + this.getSquareHeight() / 2);
    }

    /**
     * Méthode responsable de l'affichage des interfaces utilisateurs
     */
    public abstract void drawInfos();

    /**
     * Méthode responsable d'afficher des éléments sous la souris
     */
    public abstract void drawMouse();

    /**
     * Méthode responsable d'afficher le fond du jeu
     */
    public abstract void drawBackground();

    /**
     * Actualise la logique du monde et affiche son apparence et ses éléments
     * @throws UnsupportedAudioFileException
     * @throws IOException
     * @throws LineUnavailableException
     */
    public abstract void update() throws UnsupportedAudioFileException, IOException, LineUnavailableException;

    /**
     * Méthode appelé lorsque l'utilisateur presse la souris
     * @param x la position horizontale de la souris
     * @param y la position verticale de la souris
     * @param mouseButton le bouton de la souris utilisé
     * @throws UnsupportedAudioFileException
     * @throws IOException
     * @throws LineUnavailableException
     */
    public abstract void mouseClick(double x, double y, int mouseButton) throws UnsupportedAudioFileException, IOException, LineUnavailableException;

    /**
     * Lance la boucle de jeu
     * @throws UnsupportedAudioFileException
     * @throws IOException
     * @throws LineUnavailableException
     */
    public void run() throws UnsupportedAudioFileException, IOException, LineUnavailableException{
        while (!this.end) {
            long time_nano = System.nanoTime();

            StdDraw.clear();

            if (StdDraw.isMousePressed()) {
                if (!this.needReleaseMouse) {
                    this.mouseClick(StdDraw.mouseX(), StdDraw.mouseY(), StdDraw.mouseButtonPressed());
                }
            } else if (this.needReleaseMouse) {
                this.needReleaseMouse = false;
            }


            this.updateEvent();
            this.update();
            StdDraw.show();

            int ms = (int) (System.nanoTime() - time_nano) / 1000000;
            int fps = 1000 / ms;
            this.deltaTime = 1.0 / fps;
        }

        endWorld();
    }

    /**
     * Evenement appelé dans la boucle de jeu avant d'update le monde
     * @throws UnsupportedAudioFileException
     * @throws IOException
     * @throws LineUnavailableException
     */
    public void updateEvent() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        // optional implementation in children
        return;
    }

    /**
     * Méthode lancant le menu principal
     * @throws UnsupportedAudioFileException
     * @throws IOException
     * @throws LineUnavailableException
     */
    public void endWorld() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        this.menu.setNeedReleaseMouse(true);
        this.menu.run();
    }
}
