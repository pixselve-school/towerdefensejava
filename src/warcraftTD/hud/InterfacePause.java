package warcraftTD.hud;

import warcraftTD.WorldGame;
import warcraftTD.utils.Position;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;

public class InterfacePause extends Interface {
    /** le groupement d'éléments du menu pause */
    private GroupBox box;
    /** Référence vers le monde de jeu */
    private WorldGame world;

    /** Récupère le groupement d'éléments du menu pause */
    public GroupBox getBox() {
        return this.box;
    }

    /**
     * Initialise l'interface de pause
     * @param world référence du monde de jeu
     * @throws UnsupportedAudioFileException
     * @throws IOException
     * @throws LineUnavailableException
     */
    public InterfacePause(WorldGame world) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        this.world = world;

        this.box = new GroupBox(new Position(0.5,0.5),0.45,0.4,this, "images/pauseTab.png");
        this.getListElements().add(this.box);
        Button btn = new Button(new Position(0.5,0.57),0.2,0.07,"images/resumeButton.png","images/resumeButton_hover.png","resume",this);
        this.box.addHUDElement(btn);
        btn = new Button(new Position(0.5,0.35),0.2,0.07,"images/quitToMenuButton.png","images/quitToMenuButton_hover.png","quit",this);
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
            case "resume":
                this.world.exitPause();
                this.world.setNeedReleaseMouse(true);
                break;
            case "quit":
                this.world.setEnd(true);
                this.world.setNeedReleaseMouse(true);
                break;
        }
    }
}
