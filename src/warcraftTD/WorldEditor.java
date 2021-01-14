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
import java.util.Map;
import java.util.TreeMap;

/**
 * Un monde d'édition de niveau
 */
public class WorldEditor extends World{
    /** L'interface d'édition de niveau */
    private InterfaceEditor HUD;

    /**
     * Initialise un monde d'édition de niveau
     * @param width largeur de la fenetre
     * @param height hauteur de la fenetre
     * @param menu Menu Principal
     * @throws UnsupportedAudioFileException
     * @throws IOException
     * @throws LineUnavailableException
     */
    public WorldEditor(int width, int height, MainMenu menu) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        super(width, height, menu);
        this.setNbSquareX(11);
        this.setNbSquareY(11);
        refreshSquareSize();

        StdDraw.setCanvasSize(width, height);
        StdDraw.enableDoubleBuffering();

        this.HUD = new InterfaceEditor(this);
        this.setPositionTileMap(new TreeMap<>());
        generatePath();

        //this.positionTileMap.put(new Position(2,0),new Pathway(new Position(0.16,0.05), 0.1,0.1));
        //this.positionTileMap.get(new Position(2,0)).updateDirectionValue(this.positionTileMap, true);
    }

    /**
     * Actualise les variables correspondants aux tailles des tuiles
     */
    public void refreshSquareSize(){
        this.setSquareWidth((double) 1 / this.getNbSquareX());
        this.setSquareHeight((double) 1 / this.getNbSquareY());
    }

    /**
     * Méthode responsable de l'affichage des interfaces utilisateurs
     */
    public void drawInfos() {
        this.HUD.updateInterface(StdDraw.mouseX(), StdDraw.mouseY(), this.getDeltaTime());
    }

    /**
     * Méthode responsable d'afficher des éléments sous la souris
     */
    public void drawMouse() {
        Position normalized = this.getTileNormalizedPositionUnderMouse();
        Position tilePosition = this.getTilePositionUnderMouse();

        switch (this.HUD.getBuildingType()){
            case None:
                break;
            case Path:
                if(!this.getPaths().contains(tilePosition)){
                    int putPossibility = canPutNewPathAtLocation(tilePosition);
                    if(putPossibility!=0) StdDraw.picture(normalized.getX(), normalized.getY(), "images/Select_tile.png", this.getSquareWidth(), this.getSquareHeight());
                    else StdDraw.picture(normalized.getX(), normalized.getY(), "images/Select_tile_unavailable.png", this.getSquareWidth(), this.getSquareHeight());
                } else {
                    StdDraw.picture(normalized.getX(), normalized.getY(), "images/Select_tile_unavailable.png", this.getSquareWidth(), this.getSquareHeight());
                }
                break;
            case RemovePath:
                if(getPaths().size()>0 && (this.getPaths().get(0).equals(tilePosition) || this.getPaths().get(this.getPaths().size()-1).equals(tilePosition))){
                    StdDraw.picture(normalized.getX(), normalized.getY(), "images/Select_tile_unavailable.png", this.getSquareWidth(), this.getSquareHeight());
                }
                break;
            case Spawn:
                if(!this.getPaths().contains(tilePosition)){
                    StdDraw.picture(normalized.getX(), normalized.getY(), "images/Select_tile.png", this.getSquareWidth(), this.getSquareHeight());
                }
                break;
        }

    }

    /**
     * Méthode responsable d'afficher le fond du jeu
     */
    public void drawBackground() {
        for (Map.Entry<Position, Tile> tileEntry : this.getPositionTileMap().entrySet()) {
            tileEntry.getValue().drawStaticPart();
        }
    }

    /**
     * Méthode générant les tuiles de jeux
     */
    public void generatePath() {
        this.setPositionTileMap(new TreeMap<>());

        for (int i = 0; i < this.getNbSquareX(); i++) {
            for (int j = 0; j < this.getNbSquareY(); j++) {
                final Position position = new Position(i, j);

                if (this.getPaths().contains(position)) {

                    final Pathway pathway = new Pathway(position, this.getSquareHeight(), this.getSquareWidth());
                    this.getPositionTileMap().put(position, pathway);
                } else {
                    final Grass grass = new Grass(position, this.getSquareHeight(), this.getSquareWidth());
                    this.getPositionTileMap().put(position, grass);
                }
            }
        }

        this.getPositionTileMap().forEach((position, tile) -> tile.updateDirectionValue(this.getPositionTileMap(), false));
    }

    /**
     * Méthode responsable d'afficher le chemin
     */
    public void drawPath() {
		 if(this.getPaths().size()>0){
             StdDraw.setPenColor(StdDraw.BLACK);
             StdDraw.setFont(new Font("Arial", Font.BOLD, 30));
		     StdDraw.text(this.getPaths().get(0).getX()/this.getNbSquareX() + this.getSquareWidth()/2,this.getPaths().get(0).getY()/this.getNbSquareY() + this.getSquareHeight()/2, "Spawn");
         }
    }

    /**
     * Supprimes toutes les tuiles de chemins du niveau
     */
    public void clearPath(){
        for(Position p : this.getPaths()){
            this.getPositionTileMap().put(p,new Grass(p, this.getSquareHeight(),this.getSquareWidth()));
        }
        this.getPaths().clear();
    }

    /**
     * Actualise la logique du monde et affiche son apparence et ses éléments
     * @throws UnsupportedAudioFileException
     * @throws IOException
     * @throws LineUnavailableException
     */
    public void update() {
        this.drawBackground();
        this.drawPath();
        this.drawMouse();
        this.drawInfos();
    }

    /**
     * Ajoute un nouveau chemin sur le niveau si possible
     * @param tilePosition la position du chemin
     */
    public void addPathAtLocation(Position tilePosition){
        if(!this.getPaths().contains(tilePosition)){
            int putPossibility = canPutNewPathAtLocation(tilePosition);
            if(putPossibility==0) return;
            if(putPossibility==1) this.getPaths().add(tilePosition);
            else this.getPaths().add(0, tilePosition);

            this.getPositionTileMap().put(tilePosition,new Pathway(tilePosition, this.getSquareHeight(),this.getSquareWidth()));
            this.getPositionTileMap().get(tilePosition).updateDirectionValue(this.getPositionTileMap(), true);
        }
    }

    /**
     * Retire un chemin sur le niveau si possible
     * @param tilePosition la position du chemin
     */
    public void removePathAtLocation(Position tilePosition){
        if(getPaths().size()==0) return;
        if(this.getPaths().get(0).equals(tilePosition)){
            this.getPaths().remove(tilePosition);
            this.getPositionTileMap().put(tilePosition,new Grass(tilePosition, this.getSquareHeight(),this.getSquareWidth()));
            if(getPaths().size()==0) this.HUD.getPathButton().setEnabled(false);
            else this.getPositionTileMap().get(this.getPaths().get(0)).updateDirectionValue(this.getPositionTileMap(), true);
        } else if(this.getPaths().get(this.getPaths().size()-1).equals(tilePosition)){
            this.getPaths().remove(tilePosition);
            this.getPositionTileMap().put(tilePosition,new Grass(tilePosition, this.getSquareHeight(),this.getSquareWidth()));
            if(getPaths().size()==0) this.HUD.getPathButton().setEnabled(false);
            else this.getPositionTileMap().get(this.getPaths().get(this.getPaths().size()-1)).updateDirectionValue(this.getPositionTileMap(), true);
        }
    }

    /**
     * Change la position de la tuile de spawn des monstres
     * @param tilePosition la position du spawn
     */
    public void changeSpawnLocation(Position tilePosition){
        if(!this.getPaths().contains(tilePosition)){
            if(this.getPaths().size()==0){
                this.getPaths().add(tilePosition);
                this.getPositionTileMap().put(tilePosition,new Pathway(tilePosition, this.getSquareHeight(),this.getSquareWidth()));
                this.getPositionTileMap().get(tilePosition).updateDirectionValue(this.getPositionTileMap(), true);
            } else {
                this.getPositionTileMap().put(this.getPaths().get(0), new Grass(this.getPaths().get(0), this.getSquareHeight(),this.getSquareWidth()));
                this.setPaths(new ArrayList<Position>());
                this.getPaths().add(tilePosition);
                this.getPositionTileMap().put(tilePosition,new Pathway(tilePosition, this.getSquareHeight(),this.getSquareWidth()));
                this.getPositionTileMap().get(tilePosition).updateDirectionValue(this.getPositionTileMap(), true);
            }
            this.HUD.getPathButton().setEnabled(true);
        }
    }

    /**
     * Méthode appelé lorsque l'utilisateur presse la souris
     * @param x la position horizontale de la souris
     * @param y la position verticale de la souris
     * @param mouseButton le bouton de la souris utilisé
     * @throws UnsupportedAudioFileException
     * @throws IOException
     * @throws LineUnavailableException
     */
    public void mouseClick(double x, double y, int mouseButton) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        Position tilePosition = this.getTilePositionUnderMouse();

        if(this.HUD.onClick(x,y,mouseButton)) return;

        switch (this.HUD.getBuildingType()){
            case None:
                break;
            case Path:
                this.addPathAtLocation(tilePosition);
                break;
            case RemovePath:
                this.removePathAtLocation(tilePosition);
                break;
            case Spawn:
                this.changeSpawnLocation(tilePosition);
                break;
        }
    }

    /**
     * Spécifie si on peut poser un nouveau chemin a la position donnée
     * @param tilePosition la position
     * @return 0 si on ne peut pas, 1 si on peut à la fin du chemin, 2 si on peut et que ça remplace le spawn
     */
    public int canPutNewPathAtLocation(Position tilePosition){
        if(((this.getPaths().contains(new Position(tilePosition.getX()+1, tilePosition.getY())) ? 1 : 0) + (this.getPaths().contains(new Position(tilePosition.getX()-1, tilePosition.getY())) ? 1 : 0) + (this.getPaths().contains(new Position(tilePosition.getX(), tilePosition.getY()+1)) ? 1 : 0) + (this.getPaths().contains(new Position(tilePosition.getX(), tilePosition.getY()-1)) ? 1 : 0) == 1)) {
            if(((this.getPaths().get(this.getPaths().size()-1).equals(new Position(tilePosition.getX()+1, tilePosition.getY())) ? 1 : 0) + (this.getPaths().get(this.getPaths().size()-1).equals(new Position(tilePosition.getX()-1, tilePosition.getY())) ? 1 : 0) + (this.getPaths().get(this.getPaths().size()-1).equals(new Position(tilePosition.getX(), tilePosition.getY()+1)) ? 1 : 0) + (this.getPaths().get(this.getPaths().size()-1).equals(new Position(tilePosition.getX(), tilePosition.getY()-1)) ? 1 : 0) == 1)){
                return 1;
            }
            if(((this.getPaths().get(0).equals(new Position(tilePosition.getX()+1, tilePosition.getY())) ? 1 : 0) + (this.getPaths().get(0).equals(new Position(tilePosition.getX()-1, tilePosition.getY())) ? 1 : 0) + (this.getPaths().get(0).equals(new Position(tilePosition.getX(), tilePosition.getY()+1)) ? 1 : 0) + (this.getPaths().get(0).equals(new Position(tilePosition.getX(), tilePosition.getY()-1)) ? 1 : 0) == 1)){
                return 2;
            }
        }
        return 0;
    }
}
