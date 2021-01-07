package warcraftTD.hud;

import warcraftTD.WorldGame;
import warcraftTD.utils.Position;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;

public class InterfacePause extends Interface{
    private GroupBox box;
    private WorldGame world;

    public GroupBox getBox() {
        return this.box;
    }

    public InterfacePause(WorldGame world) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        this.world = world;

        this.box = new GroupBox(new Position(0.5,0.5),0.45,0.4,this, "images/pauseTab.png");
        this.getListElements().add(this.box);
        Button btn = new Button(new Position(0.5,0.57),0.2,0.07,"images/resumeButton.png","images/resumeButton_hover.png","resume",this);
        this.box.addHUDElement(btn);
        btn = new Button(new Position(0.5,0.35),0.2,0.07,"images/quitToMenuButton.png","images/quitToMenuButton_hover.png","quit",this);
        this.box.addHUDElement(btn);
    }


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
