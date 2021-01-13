package warcraftTD;

import warcraftTD.hud.InterfaceEditor;
import warcraftTD.hud.MainMenu;
import warcraftTD.libs.StdDraw;
import warcraftTD.utils.Position;
import warcraftTD.world.*;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

public class WorldEditor extends World{

    private InterfaceEditor HUD;

    private TreeMap<Position, Tile> positionTileMap;

    private boolean displayWater;

    public WorldEditor(int width, int height, MainMenu menu) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        super(width, height, menu);
        this.setNbSquareX(11);
        this.setNbSquareY(11);
        refreshSquareSize();

        StdDraw.setCanvasSize(width, height);
        StdDraw.enableDoubleBuffering();

        this.HUD = new InterfaceEditor(this);
        this.displayWater = false;
        this.positionTileMap = new TreeMap<>();
    }

    public void refreshSquareSize(){
        this.setSquareWidth((double) 1 / this.getNbSquareX());
        this.setSquareHeight((double) 1 / this.getNbSquareY());
    }

    /**
     * Affiche certaines informations sur l'écran telles que les points de vie du joueur ou son or
     */
    public void drawInfos() {
        this.HUD.updateInterface(StdDraw.mouseX(), StdDraw.mouseY(), this.getDeltaTime());
    }

    /**
     * Fonction qui récupère le positionnement de la souris et permet d'afficher une image de tour en temps réél
     * lorsque le joueur appuie sur une des touches permettant la construction d'une tour.
     */
    public void drawMouse() {
        double normalizedX = (int) (StdDraw.mouseX() / this.getSquareWidth()) * this.getSquareWidth() + this.getSquareWidth() / 2;
        double normalizedY = (int) (StdDraw.mouseY() / this.getSquareHeight()) * this.getSquareHeight() + this.getSquareHeight() / 2;
        Position mousep = new Position((int) ((normalizedX * this.getNbSquareX())), (int) ((normalizedY * this.getNbSquareY())));

        switch (this.HUD.getBuildingType()){
            case None:
                break;
            case Path:
                if(!this.getPaths().contains(mousep)){
                    if(((this.getPaths().get(this.getPaths().size()-1).equals(new Position(mousep.getX()+1, mousep.getY())) ? 1 : 0) + (this.getPaths().get(this.getPaths().size()-1).equals(new Position(mousep.getX()-1, mousep.getY())) ? 1 : 0) + (this.getPaths().get(this.getPaths().size()-1).equals(new Position(mousep.getX(), mousep.getY()+1)) ? 1 : 0) + (this.getPaths().get(this.getPaths().size()-1).equals(new Position(mousep.getX(), mousep.getY()-1)) ? 1 : 0) == 1)){
                        if(((this.getPaths().contains(new Position(mousep.getX()+1, mousep.getY())) ? 1 : 0) + (this.getPaths().contains(new Position(mousep.getX()-1, mousep.getY())) ? 1 : 0) + (this.getPaths().contains(new Position(mousep.getX(), mousep.getY()+1)) ? 1 : 0) + (this.getPaths().contains(new Position(mousep.getX(), mousep.getY()-1)) ? 1 : 0) == 1)) {
                            StdDraw.picture(normalizedX, normalizedY, "images/Select_tile.png", this.getSquareWidth(), this.getSquareHeight());
                            return;
                        }
                    }
                    StdDraw.picture(normalizedX, normalizedY, "images/Select_tile_unavailable.png", this.getSquareWidth(), this.getSquareHeight());
                } else {
                    StdDraw.picture(normalizedX, normalizedY, "images/Select_tile_unavailable.png", this.getSquareWidth(), this.getSquareHeight());
                }
                break;
            case RemovePath:
                if(getPaths().size()>0 && (this.getPaths().get(0).equals(mousep) || this.getPaths().get(this.getPaths().size()-1).equals(mousep))){
                    StdDraw.picture(normalizedX, normalizedY, "images/Select_tile_unavailable.png", this.getSquareWidth(), this.getSquareHeight());
                }
                break;
            case Spawn:
                if(!this.getPaths().contains(mousep)){
                    StdDraw.picture(normalizedX, normalizedY, "images/Select_tile.png", this.getSquareWidth(), this.getSquareHeight());
                }
                break;
        }

    }

    public void drawBackground() {
        for (int i = 0; i < this.getNbSquareX(); i++) {
          for (int j = 0; j < this.getNbSquareY(); j++) {
              StdDraw.setPenColor(StdDraw.LIGHT_GREEN);
              StdDraw.filledRectangle(i * this.getSquareWidth() + this.getSquareWidth(), j * this.getSquareHeight() + this.getSquareHeight(), this.getSquareWidth() , this.getSquareHeight());
              StdDraw.setPenColor(StdDraw.DARK_GREEN);
              StdDraw.rectangle(i * this.getSquareWidth() + this.getSquareWidth(), j * this.getSquareHeight() + this.getSquareHeight(), this.getSquareWidth() , this.getSquareHeight());
            //StdDraw.picture(i * squareWidth + squareWidth / 2, j * squareHeight + squareHeight / 2, "images/grass.jpg", squareWidth, squareHeight);
          }
        }
    }

    /**
     * Initialise le chemin sur la position du point de départ des monstres. Cette fonction permet d'afficher une route qui sera différente du décors.
     */
    public void drawPath() {
        StdDraw.setPenColor(StdDraw.YELLOW);
		 Iterator<Position> i = getPaths().iterator();
		 Position p;
		 while (i.hasNext()) {
		 	p = i.next();
			 double coorX = p.getX() / this.getNbSquareX() + (this.getSquareWidth()/2);
			 double coorY = p.getY() / this.getNbSquareY() + (this.getSquareHeight()/2);
             StdDraw.setPenColor(StdDraw.YELLOW);
			 StdDraw.filledRectangle(coorX, coorY, this.getSquareWidth() / 2, this.getSquareHeight() / 2);
			 StdDraw.setPenColor(StdDraw.PRINCETON_ORANGE);
             StdDraw.rectangle(coorX, coorY, this.getSquareWidth()/2 , this.getSquareHeight()/2);
			 //StdDraw.picture(coorX, coorY, "images/sand.jpg", squareWidth, squareHeight);
		 }
		 if(this.getPaths().size()>0){
             StdDraw.setPenColor(StdDraw.BLACK);
             StdDraw.setFont(new Font("Arial", Font.BOLD, 30));
		     StdDraw.text(this.getPaths().get(0).getX()/this.getNbSquareX() + this.getSquareWidth()/2,this.getPaths().get(0).getY()/this.getNbSquareY() + this.getSquareHeight()/2, "Spawn");
         }
    }

    public int update() {
        this.drawBackground();
        this.drawPath();
        this.drawMouse();
        this.drawInfos();
        return 0;
    }

    public void mouseClick(double x, double y, int mouseButton) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        double normalizedX = (int) (x / this.getSquareWidth()) * this.getSquareWidth() + this.getSquareWidth() / 2;
        double normalizedY = (int) (y / this.getSquareHeight()) * this.getSquareHeight() + this.getSquareHeight() / 2;
        Position p = new Position(normalizedX, normalizedY);
        Position mousep = new Position((int) ((normalizedX * this.getNbSquareX())), (int) ((normalizedY * this.getNbSquareY())));

        if(this.HUD.onClick(x,y,mouseButton)) return;

        switch (this.HUD.getBuildingType()){
            case None:
                break;
            case Path:
                if(!this.getPaths().contains(mousep)){
                    if(((this.getPaths().get(this.getPaths().size()-1).equals(new Position(mousep.getX()+1, mousep.getY())) ? 1 : 0) + (this.getPaths().get(this.getPaths().size()-1).equals(new Position(mousep.getX()-1, mousep.getY())) ? 1 : 0) + (this.getPaths().get(this.getPaths().size()-1).equals(new Position(mousep.getX(), mousep.getY()+1)) ? 1 : 0) + (this.getPaths().get(this.getPaths().size()-1).equals(new Position(mousep.getX(), mousep.getY()-1)) ? 1 : 0) == 1)){
                        if(((this.getPaths().contains(new Position(mousep.getX()+1, mousep.getY())) ? 1 : 0) + (this.getPaths().contains(new Position(mousep.getX()-1, mousep.getY())) ? 1 : 0) + (this.getPaths().contains(new Position(mousep.getX(), mousep.getY()+1)) ? 1 : 0) + (this.getPaths().contains(new Position(mousep.getX(), mousep.getY()-1)) ? 1 : 0) == 1)) {
                            this.getPaths().add(mousep);
                        }
                    }
                }
                break;
            case RemovePath:
                if(getPaths().size()>0 && (this.getPaths().get(0).equals(mousep) || this.getPaths().get(this.getPaths().size()-1).equals(mousep))){
                    this.getPaths().remove(mousep);
                    if(getPaths().size()==0) this.HUD.getPathButton().setEnabled(false);
                }
                break;
            case Spawn:
                if(!this.getPaths().contains(mousep)){
                    if(this.getPaths().size()==0){
                        this.getPaths().add(mousep);
                    } else {
                        this.setPaths(new ArrayList<Position>());
                        this.getPaths().add(mousep);
                    }
                    this.HUD.getPathButton().setEnabled(true);
                }
                break;
        }
    }

    public void singleMouseClick(double x, double y, int mouseButton) {

    }
}
