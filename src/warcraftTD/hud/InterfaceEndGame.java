package warcraftTD.hud;

import warcraftTD.WorldGame;
import warcraftTD.utils.Position;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;

public class InterfaceEndGame extends Interface{
    /** le groupement d'éléments du menu de fin de jeu */
    private GroupBox box;
    /** Référence vers le monde de jeu */
    private WorldGame world;

    /** Récupère le groupement d'éléments du menu pause */
    public GroupBox getBox() {
        return this.box;
    }

    /**
     * Initialise l'interface de fin de jeu
     * @param world référence du monde de jeu
     * @param win le joueur à gagner
     * @throws UnsupportedAudioFileException
     * @throws IOException
     * @throws LineUnavailableException
     */
    public InterfaceEndGame(WorldGame world, boolean win) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        this.world = world;

        if(win) this.box = new GroupBox(new Position(0.5,0.5),0.45,0.4,this, "images/wonTab.png");
        else this.box = new GroupBox(new Position(0.5,0.5),0.45,0.4,this, "images/lostTab.png");

        this.getListElements().add(this.box);
        Button btn = new Button(new Position(0.5,0.1),0.22,0.1,"images/quitToMenuButton.png","images/quitToMenuButton_hover.png","quit",this);
        this.box.addHUDElement(btn);
    }

    /**
     * Réalise une action sur l'interface
     * @param action l'action à réaliser
     * @param from l'élément d'où vient l'action à réaliser
     * @throws IOException
     * @throws LineUnavailableException
     * @throws UnsupportedAudioFileException
     */
    @Override
    public void makeAction(String action, Element from) throws IOException, LineUnavailableException, UnsupportedAudioFileException {
        switch (action){
            case "quit":
                this.world.setEnd(true);
                this.world.setNeedReleaseMouse(true);
                break;
        }
    }
}
