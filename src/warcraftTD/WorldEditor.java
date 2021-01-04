package warcraftTD;

import warcraftTD.hud.InterfaceEditor;
import warcraftTD.libs.StdDraw;
import warcraftTD.utils.Position;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class WorldEditor {
    private double width;
    private double height;
    private int nbSquareX;
    private int nbSquareY;
    private boolean end;
    private double squareWidth;
    private double squareHeight;

    private InterfaceEditor HUD;

    private boolean needReleaseMouse = false;

    public boolean isNeedReleaseMouse() {
        return this.needReleaseMouse;
    }

    public void setNeedReleaseMouse(boolean needReleaseMouse) {
        this.needReleaseMouse = needReleaseMouse;
    }

    // représente le temps pour chaque tick en s
    private double delta_time;

    private List<Position> paths;

    public WorldEditor(int width, int height) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        this.width = width;
        this.height = height;
        this.nbSquareX = 11;
        this.nbSquareY = 11;
        this.end = false;
        refreshSquareSize();

        StdDraw.setCanvasSize(width, height);
        StdDraw.enableDoubleBuffering();

        this.HUD = new InterfaceEditor(this);

        paths = new ArrayList<Position>();
    }

    public void refreshSquareSize(){
        this.squareWidth = (double) 1 / this.nbSquareX;
        this.squareHeight = (double) 1 / this.nbSquareY;
    }

    /**
     * Affiche certaines informations sur l'écran telles que les points de vie du joueur ou son or
     */
    public void drawInfos() {
        this.HUD.updateInterface(StdDraw.mouseX(), StdDraw.mouseY(), this.delta_time);
    }

    /**
     * Fonction qui récupère le positionnement de la souris et permet d'afficher une image de tour en temps réél
     * lorsque le joueur appuie sur une des touches permettant la construction d'une tour.
     */
    public void drawMouse() {
        double normalizedX = (int) (StdDraw.mouseX() / this.squareWidth) * this.squareWidth + this.squareWidth / 2;
        double normalizedY = (int) (StdDraw.mouseY() / this.squareHeight) * this.squareHeight + this.squareHeight / 2;
        Position mousep = new Position((int) ((normalizedX * this.nbSquareX)), (int) ((normalizedY * this.nbSquareY)));

        switch (this.HUD.getBuilding_type()){
            case None:
                break;
            case Path:
                if(!this.paths.contains(mousep)){
                    if(((this.paths.get(this.paths.size()-1).equals(new Position(mousep.getX()+1, mousep.getY())) ? 1 : 0) + (this.paths.get(this.paths.size()-1).equals(new Position(mousep.getX()-1, mousep.getY())) ? 1 : 0) + (this.paths.get(this.paths.size()-1).equals(new Position(mousep.getX(), mousep.getY()+1)) ? 1 : 0) + (this.paths.get(this.paths.size()-1).equals(new Position(mousep.getX(), mousep.getY()-1)) ? 1 : 0) == 1)){
                        if(((this.paths.contains(new Position(mousep.getX()+1, mousep.getY())) ? 1 : 0) + (this.paths.contains(new Position(mousep.getX()-1, mousep.getY())) ? 1 : 0) + (this.paths.contains(new Position(mousep.getX(), mousep.getY()+1)) ? 1 : 0) + (this.paths.contains(new Position(mousep.getX(), mousep.getY()-1)) ? 1 : 0) == 1)) {
                            StdDraw.picture(normalizedX, normalizedY, "images/Select_tile.png", this.squareWidth, this.squareHeight);
                            return;
                        }
                    }
                    StdDraw.picture(normalizedX, normalizedY, "images/Select_tile_unavailable.png", this.squareWidth, this.squareHeight);
                } else {
                    StdDraw.picture(normalizedX, normalizedY, "images/Select_tile_unavailable.png", this.squareWidth, this.squareHeight);
                }
                break;
            case RemovePath:
                if(paths.size()>0 && (this.paths.get(0).equals(mousep) || this.paths.get(this.paths.size()-1).equals(mousep))){
                    StdDraw.picture(normalizedX, normalizedY, "images/Select_tile_unavailable.png", this.squareWidth, this.squareHeight);
                }
                break;
            case Spawn:
                if(!this.paths.contains(mousep)){
                    StdDraw.picture(normalizedX, normalizedY, "images/Select_tile.png", this.squareWidth, this.squareHeight);
                }
                break;
        }

    }

    public void drawBackground() {
        for (int i = 0; i < nbSquareX; i++) {
          for (int j = 0; j < nbSquareY; j++) {
              StdDraw.setPenColor(StdDraw.LIGHT_GREEN);
              StdDraw.filledRectangle(i * squareWidth + squareWidth, j * squareHeight + squareHeight, squareWidth , squareHeight);
              StdDraw.setPenColor(StdDraw.DARK_GREEN);
              StdDraw.rectangle(i * squareWidth + squareWidth, j * squareHeight + squareHeight, squareWidth , squareHeight);
            //StdDraw.picture(i * squareWidth + squareWidth / 2, j * squareHeight + squareHeight / 2, "images/grass.jpg", squareWidth, squareHeight);
          }
        }
    }

    public double getWidth() {
        return this.width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getHeight() {
        return this.height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public int getNbSquareX() {
        return this.nbSquareX;
    }

    public void setNbSquareX(int nbSquareX) {
        this.nbSquareX = nbSquareX;
    }

    public int getNbSquareY() {
        return this.nbSquareY;
    }

    public void setNbSquareY(int nbSquareY) {
        this.nbSquareY = nbSquareY;
    }

    public boolean isEnd() {
        return this.end;
    }

    public void setEnd(boolean end) {
        this.end = end;
    }

    public double getSquareWidth() {
        return this.squareWidth;
    }

    public void setSquareWidth(double squareWidth) {
        this.squareWidth = squareWidth;
    }

    public double getSquareHeight() {
        return this.squareHeight;
    }

    public void setSquareHeight(double squareHeight) {
        this.squareHeight = squareHeight;
    }

    public InterfaceEditor getHUD() {
        return this.HUD;
    }

    public void setHUD(InterfaceEditor HUD) {
        this.HUD = HUD;
    }

    public double getDelta_time() {
        return this.delta_time;
    }

    public void setDelta_time(double delta_time) {
        this.delta_time = delta_time;
    }

    public List<Position> getPaths() {
        return this.paths;
    }

    public void setPaths(List<Position> paths) {
        this.paths = paths;
    }

    /**
     * Initialise le chemin sur la position du point de départ des monstres. Cette fonction permet d'afficher une route qui sera différente du décors.
     */
    public void drawPath() {
        StdDraw.setPenColor(StdDraw.YELLOW);
		 Iterator<Position> i = paths.iterator();
		 Position p;
		 while (i.hasNext()) {
		 	p = i.next();
			 double coorX = p.getX() / nbSquareX + (squareWidth/2);
			 double coorY = p.getY() / nbSquareY + (squareHeight/2);
             StdDraw.setPenColor(StdDraw.YELLOW);
			 StdDraw.filledRectangle(coorX, coorY, squareWidth / 2, squareHeight / 2);
			 StdDraw.setPenColor(StdDraw.PRINCETON_ORANGE);
             StdDraw.rectangle(coorX, coorY, squareWidth/2 , squareHeight/2);
			 //StdDraw.picture(coorX, coorY, "images/sand.jpg", squareWidth, squareHeight);
		 }
		 if(this.paths.size()>0){
             StdDraw.setPenColor(StdDraw.BLACK);
             StdDraw.setFont(new Font("Arial", Font.BOLD, 30));
		     StdDraw.text(this.paths.get(0).getX()/nbSquareX + squareWidth/2,this.paths.get(0).getY()/nbSquareY + squareHeight/2, "Spawn");
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
        double normalizedX = (int) (x / this.squareWidth) * this.squareWidth + this.squareWidth / 2;
        double normalizedY = (int) (y / this.squareHeight) * this.squareHeight + this.squareHeight / 2;
        Position p = new Position(normalizedX, normalizedY);
        Position mousep = new Position((int) ((normalizedX * this.nbSquareX)), (int) ((normalizedY * this.nbSquareY)));

        if(this.HUD.onClick(x,y,mouseButton)) return;

        switch (this.HUD.getBuilding_type()){
            case None:
                break;
            case Path:
                if(!this.paths.contains(mousep)){
                    if(((this.paths.get(this.paths.size()-1).equals(new Position(mousep.getX()+1, mousep.getY())) ? 1 : 0) + (this.paths.get(this.paths.size()-1).equals(new Position(mousep.getX()-1, mousep.getY())) ? 1 : 0) + (this.paths.get(this.paths.size()-1).equals(new Position(mousep.getX(), mousep.getY()+1)) ? 1 : 0) + (this.paths.get(this.paths.size()-1).equals(new Position(mousep.getX(), mousep.getY()-1)) ? 1 : 0) == 1)){
                        if(((this.paths.contains(new Position(mousep.getX()+1, mousep.getY())) ? 1 : 0) + (this.paths.contains(new Position(mousep.getX()-1, mousep.getY())) ? 1 : 0) + (this.paths.contains(new Position(mousep.getX(), mousep.getY()+1)) ? 1 : 0) + (this.paths.contains(new Position(mousep.getX(), mousep.getY()-1)) ? 1 : 0) == 1)) {
                            this.paths.add(mousep);
                        }
                    }
                }
                break;
            case RemovePath:
                if(paths.size()>0 && (this.paths.get(0).equals(mousep) || this.paths.get(this.paths.size()-1).equals(mousep))){
                    this.paths.remove(mousep);
                    if(paths.size()==0) this.getHUD().getPathButton().setEnabled(false);
                }
                break;
            case Spawn:
                if(!this.paths.contains(mousep)){
                    if(this.paths.size()==0){
                        this.paths.add(mousep);
                    } else {
                        this.paths = new ArrayList<Position>();
                        this.paths.add(mousep);
                    }
                    this.getHUD().getPathButton().setEnabled(true);
                }
                break;
        }
    }

    public void run() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
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

            this.update();
            StdDraw.show();

            int ms = (int) (System.nanoTime() - time_nano) / 1000000;
            int fps = 1000 / ms;
            this.delta_time = 1.0 / fps;
        }
    }
}
