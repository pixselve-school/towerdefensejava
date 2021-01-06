package warcraftTD.hud;

import warcraftTD.utils.Position;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.awt.*;
import java.io.IOException;

public class ButtonQueueEditor extends Button{
    double time;
    String imagePath;
    Image image;
    Text text;

    public double getTime() {
        return this.time;
    }

    public void setTime(double time) {
        this.time = time;
        this.text.setText(time+" s");
    }

    public String getImagePath() {
        return this.imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
        this.image.setSprite(imagePath);
    }

    public ButtonQueueEditor(Position pos, double width, double height, String sprite, String sprite_hover, String action, Interface Parent, String imagePath, double time) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        super(pos, width, height, sprite, sprite_hover, action, Parent);
        this.imagePath  = imagePath;
        this.time = time;
        this.image = new Image(new Position(0.0,0.0),0.04,0.05,this.getParent(),this.imagePath);
        this.text = new Text(new Position(0.0,0.0),0.0,0.0,new Font("Arial", Font.BOLD, 30),this.getParent(),time+" s");
    }

    @Override
    public void setVisible(boolean visible) {
        super.setVisible(visible);
        this.image.setVisible(visible);
        this.text.setVisible(visible);
    }

    @Override
    public void setPosition(Position position) {
        super.setPosition(position);
        this.image.setPosition(new Position(this.getPosition().getX()-0.035, this.getPosition().getY()));
        this.text.setPosition(new Position(this.getPosition().getX()+0.02, this.getPosition().getY()));
    }

    @Override
    public void update(double MouseX, double MouseY, double delta_time) {
        super.update(MouseX, MouseY, delta_time);

        image.update(MouseX, MouseY, delta_time);
        text.update(MouseX, MouseY, delta_time);
    }
}
