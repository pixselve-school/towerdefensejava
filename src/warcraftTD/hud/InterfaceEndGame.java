package warcraftTD.hud;

import warcraftTD.WorldGame;
import warcraftTD.utils.Position;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;

public class InterfaceEndGame extends Interface{
    private HorizontalGroupBox box;
    private boolean win;
    private WorldGame world;

    public HorizontalGroupBox getBox() {
        return this.box;
    }

    public InterfaceEndGame(WorldGame world, boolean win) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        this.world = world;
        this.win = win;

        if(win) this.box = new HorizontalGroupBox(new Position(0.5,0.5),0.45,0.4,this, "images/wonTab.png");
        else this.box = new HorizontalGroupBox(new Position(0.5,0.5),0.45,0.4,this, "images/lostTab.png");

        this.getListElements().add(this.box);
        Button btn = new Button(new Position(0.5,0.1),0.22,0.1,"images/quitToMenuButton.png","images/quitToMenuButton_hover.png","quit",this);
        this.box.addHUDElement(btn);
    }

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
