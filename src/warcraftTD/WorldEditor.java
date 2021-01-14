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

    public WorldEditor(int width, int height, MainMenu menu) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        super(width, height, menu);
        this.setNbSquareX(11);
        this.setNbSquareY(11);
        refreshSquareSize();

        StdDraw.setCanvasSize(width, height);
        StdDraw.enableDoubleBuffering();

        this.HUD = new InterfaceEditor(this);
        this.positionTileMap = new TreeMap<>();
        generatePath();

        //this.positionTileMap.put(new Position(2,0),new Pathway(new Position(0.16,0.05), 0.1,0.1));
        //this.positionTileMap.get(new Position(2,0)).updateDirectionValue(this.positionTileMap, true);
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
        //Position mousep = new Position((int) ((normalizedX * this.getNbSquareX())), (int) ((normalizedY * this.getNbSquareY())));
        Position tilePosition = new Position((int) Math.floor(StdDraw.mouseX() * this.getNbSquareX()), (int) Math.floor(StdDraw.mouseY() * this.getNbSquareY()));

        switch (this.HUD.getBuildingType()){
            case None:
                break;
            case Path:
                if(!this.getPaths().contains(tilePosition)){
                    if(((this.getPaths().get(this.getPaths().size()-1).equals(new Position(tilePosition.getX()+1, tilePosition.getY())) ? 1 : 0) + (this.getPaths().get(this.getPaths().size()-1).equals(new Position(tilePosition.getX()-1, tilePosition.getY())) ? 1 : 0) + (this.getPaths().get(this.getPaths().size()-1).equals(new Position(tilePosition.getX(), tilePosition.getY()+1)) ? 1 : 0) + (this.getPaths().get(this.getPaths().size()-1).equals(new Position(tilePosition.getX(), tilePosition.getY()-1)) ? 1 : 0) == 1)){
                        if(((this.getPaths().contains(new Position(tilePosition.getX()+1, tilePosition.getY())) ? 1 : 0) + (this.getPaths().contains(new Position(tilePosition.getX()-1, tilePosition.getY())) ? 1 : 0) + (this.getPaths().contains(new Position(tilePosition.getX(), tilePosition.getY()+1)) ? 1 : 0) + (this.getPaths().contains(new Position(tilePosition.getX(), tilePosition.getY()-1)) ? 1 : 0) == 1)) {
                            StdDraw.picture(normalizedX, normalizedY, "images/Select_tile.png", this.getSquareWidth(), this.getSquareHeight());
                            return;
                        }
                    }
                    if(((this.getPaths().get(0).equals(new Position(tilePosition.getX()+1, tilePosition.getY())) ? 1 : 0) + (this.getPaths().get(0).equals(new Position(tilePosition.getX()-1, tilePosition.getY())) ? 1 : 0) + (this.getPaths().get(0).equals(new Position(tilePosition.getX(), tilePosition.getY()+1)) ? 1 : 0) + (this.getPaths().get(0).equals(new Position(tilePosition.getX(), tilePosition.getY()-1)) ? 1 : 0) == 1)){
                        if(((this.getPaths().contains(new Position(tilePosition.getX()+1, tilePosition.getY())) ? 1 : 0) + (this.getPaths().contains(new Position(tilePosition.getX()-1, tilePosition.getY())) ? 1 : 0) + (this.getPaths().contains(new Position(tilePosition.getX(), tilePosition.getY()+1)) ? 1 : 0) + (this.getPaths().contains(new Position(tilePosition.getX(), tilePosition.getY()-1)) ? 1 : 0) == 1)) {
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
                if(getPaths().size()>0 && (this.getPaths().get(0).equals(tilePosition) || this.getPaths().get(this.getPaths().size()-1).equals(tilePosition))){
                    StdDraw.picture(normalizedX, normalizedY, "images/Select_tile_unavailable.png", this.getSquareWidth(), this.getSquareHeight());
                }
                break;
            case Spawn:
                if(!this.getPaths().contains(tilePosition)){
                    StdDraw.picture(normalizedX, normalizedY, "images/Select_tile.png", this.getSquareWidth(), this.getSquareHeight());
                }
                break;
        }

    }

    public void drawBackground() {
        /*for (int i = 0; i < this.getNbSquareX(); i++) {
          for (int j = 0; j < this.getNbSquareY(); j++) {
              StdDraw.setPenColor(StdDraw.LIGHT_GREEN);
              StdDraw.filledRectangle(i * this.getSquareWidth() + this.getSquareWidth(), j * this.getSquareHeight() + this.getSquareHeight(), this.getSquareWidth() , this.getSquareHeight());
              StdDraw.setPenColor(StdDraw.DARK_GREEN);
              StdDraw.rectangle(i * this.getSquareWidth() + this.getSquareWidth(), j * this.getSquareHeight() + this.getSquareHeight(), this.getSquareWidth() , this.getSquareHeight());
            //StdDraw.picture(i * squareWidth + squareWidth / 2, j * squareHeight + squareHeight / 2, "images/grass.jpg", squareWidth, squareHeight);
          }
        }*/
        for (Map.Entry<Position, Tile> tileEntry : this.positionTileMap.entrySet()) {
            tileEntry.getValue().drawStaticPart();
        }
    }

    public void generatePath() {
        this.positionTileMap = new TreeMap<>();

        for (int i = 0; i < this.getNbSquareX(); i++) {
            for (int j = 0; j < this.getNbSquareY(); j++) {
                final Position position = new Position(i, j);

                if (this.getPaths().contains(position)) {

                    final Pathway pathway = new Pathway(position, this.getSquareHeight(), this.getSquareWidth());
                    this.positionTileMap.put(position, pathway);
                } else {
                    final Grass grass = new Grass(position, this.getSquareHeight(), this.getSquareWidth());
                    this.positionTileMap.put(position, grass);
                }
            }
        }

        /*Position spawnPath = this.getPaths().get(0);
        Position finishPath = this.getPaths().get(this.getPaths().size() - 1);
        this.positionTileMap.get(spawnPath).replaceContains(new IndestructibleEntity("images/tiles/rock.png", 0.1));
        this.positionTileMap.get(finishPath).replaceContains(new IndestructibleEntity("images/tiles/house.png", 0.15));*/


        this.positionTileMap.forEach((position, tile) -> tile.updateDirectionValue(this.positionTileMap, false));

    }

    /**
     * Initialise le chemin sur la position du point de départ des monstres. Cette fonction permet d'afficher une route qui sera différente du décors.
     */
    public void drawPath() {
        StdDraw.setPenColor(StdDraw.YELLOW);
		 /*Iterator<Position> i = getPaths().iterator();
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
		 }*/
		 if(this.getPaths().size()>0){
             StdDraw.setPenColor(StdDraw.BLACK);
             StdDraw.setFont(new Font("Arial", Font.BOLD, 30));
		     StdDraw.text(this.getPaths().get(0).getX()/this.getNbSquareX() + this.getSquareWidth()/2,this.getPaths().get(0).getY()/this.getNbSquareY() + this.getSquareHeight()/2, "Spawn");
         }
    }

    public void clearPath(){
        for(Position p : this.getPaths()){
            this.positionTileMap.put(p,new Grass(p, this.getSquareHeight(),this.getSquareWidth()));
        }
        this.getPaths().clear();
    }

    public int update() {
        this.drawBackground();
        this.drawPath();
        this.drawMouse();
        this.drawInfos();
        return 0;
    }

    public void mouseClick(double x, double y, int mouseButton) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        Position tilePosition = new Position((int) Math.floor(StdDraw.mouseX() * this.getNbSquareX()), (int) Math.floor(StdDraw.mouseY() * this.getNbSquareY()));

        if(this.HUD.onClick(x,y,mouseButton)) return;

        switch (this.HUD.getBuildingType()){
            case None:
                break;
            case Path:
                if(!this.getPaths().contains(tilePosition)){
                    if(((this.getPaths().get(this.getPaths().size()-1).equals(new Position(tilePosition.getX()+1, tilePosition.getY())) ? 1 : 0) + (this.getPaths().get(this.getPaths().size()-1).equals(new Position(tilePosition.getX()-1, tilePosition.getY())) ? 1 : 0) + (this.getPaths().get(this.getPaths().size()-1).equals(new Position(tilePosition.getX(), tilePosition.getY()+1)) ? 1 : 0) + (this.getPaths().get(this.getPaths().size()-1).equals(new Position(tilePosition.getX(), tilePosition.getY()-1)) ? 1 : 0) == 1)){
                        if(((this.getPaths().contains(new Position(tilePosition.getX()+1, tilePosition.getY())) ? 1 : 0) + (this.getPaths().contains(new Position(tilePosition.getX()-1, tilePosition.getY())) ? 1 : 0) + (this.getPaths().contains(new Position(tilePosition.getX(), tilePosition.getY()+1)) ? 1 : 0) + (this.getPaths().contains(new Position(tilePosition.getX(), tilePosition.getY()-1)) ? 1 : 0) == 1)) {
                            this.getPaths().add(tilePosition);

                            this.positionTileMap.put(tilePosition,new Pathway(tilePosition, this.getSquareHeight(),this.getSquareWidth()));
                            this.positionTileMap.get(tilePosition).updateDirectionValue(this.positionTileMap, true);
                            break;
                        }
                    }
                    if(((this.getPaths().get(0).equals(new Position(tilePosition.getX()+1, tilePosition.getY())) ? 1 : 0) + (this.getPaths().get(0).equals(new Position(tilePosition.getX()-1, tilePosition.getY())) ? 1 : 0) + (this.getPaths().get(0).equals(new Position(tilePosition.getX(), tilePosition.getY()+1)) ? 1 : 0) + (this.getPaths().get(0).equals(new Position(tilePosition.getX(), tilePosition.getY()-1)) ? 1 : 0) == 1)){
                        if(((this.getPaths().contains(new Position(tilePosition.getX()+1, tilePosition.getY())) ? 1 : 0) + (this.getPaths().contains(new Position(tilePosition.getX()-1, tilePosition.getY())) ? 1 : 0) + (this.getPaths().contains(new Position(tilePosition.getX(), tilePosition.getY()+1)) ? 1 : 0) + (this.getPaths().contains(new Position(tilePosition.getX(), tilePosition.getY()-1)) ? 1 : 0) == 1)) {
                            this.getPaths().add(0, tilePosition);

                            this.positionTileMap.put(tilePosition,new Pathway(tilePosition, this.getSquareHeight(),this.getSquareWidth()));
                            this.positionTileMap.get(tilePosition).updateDirectionValue(this.positionTileMap, true);
                            break;
                        }
                    }
                }
                break;
            case RemovePath:
                if(getPaths().size()==0) break;
                if(this.getPaths().get(0).equals(tilePosition)){
                    this.getPaths().remove(tilePosition);
                    this.positionTileMap.put(tilePosition,new Grass(tilePosition, this.getSquareHeight(),this.getSquareWidth()));
                    if(getPaths().size()==0) this.HUD.getPathButton().setEnabled(false);
                    else this.positionTileMap.get(this.getPaths().get(0)).updateDirectionValue(this.positionTileMap, true);
                } else if(this.getPaths().get(this.getPaths().size()-1).equals(tilePosition)){
                    this.getPaths().remove(tilePosition);
                    this.positionTileMap.put(tilePosition,new Grass(tilePosition, this.getSquareHeight(),this.getSquareWidth()));
                    if(getPaths().size()==0) this.HUD.getPathButton().setEnabled(false);
                    else this.positionTileMap.get(this.getPaths().get(this.getPaths().size()-1)).updateDirectionValue(this.positionTileMap, true);
                }
                break;
            case Spawn:
                if(!this.getPaths().contains(tilePosition)){
                    if(this.getPaths().size()==0){
                        this.getPaths().add(tilePosition);
                        this.positionTileMap.put(tilePosition,new Pathway(tilePosition, this.getSquareHeight(),this.getSquareWidth()));
                        this.positionTileMap.get(tilePosition).updateDirectionValue(this.positionTileMap, true);
                    } else {
                        this.positionTileMap.put(this.getPaths().get(0), new Grass(this.getPaths().get(0), this.getSquareHeight(),this.getSquareWidth()));
                        this.setPaths(new ArrayList<Position>());
                        this.getPaths().add(tilePosition);
                        this.positionTileMap.put(tilePosition,new Pathway(tilePosition, this.getSquareHeight(),this.getSquareWidth()));
                        this.positionTileMap.get(tilePosition).updateDirectionValue(this.positionTileMap, true);
                    }
                    this.HUD.getPathButton().setEnabled(true);
                }
                break;
        }
    }

    public void singleMouseClick(double x, double y, int mouseButton) {

    }
}
