package warcraftTD.hud;

import warcraftTD.utils.Position;
import warcraftTD.utils.Sound;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;

abstract public class ClickableElement extends Element{
    private HitBox hitBox;
    private Sound clickSound;

    public Sound getClickSound() {
        return this.clickSound;
    }

    public HitBox getHitBox() {
        return this.hitBox;
    }

    public void setHitBox(HitBox hitBox) {
        this.hitBox = hitBox;
    }

    public ClickableElement(Position position, double width, double height, Interface parent) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        super(position, width, height, parent);
        this.hitBox = new HitBox(position, width, height);
        this.clickSound = new Sound("music/click.wav", false);
    }

    @Override
    public void setPosition(Position position) {
        super.setPosition(position);
        this.hitBox.refresh(this.getPosition());
    }

    abstract public String onClick(double MouseX, double MouseY) throws UnsupportedAudioFileException, IOException, LineUnavailableException;
}
