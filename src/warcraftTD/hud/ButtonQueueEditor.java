package warcraftTD.hud;

import warcraftTD.utils.Position;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.awt.*;
import java.io.IOException;

/**
 * Un Bouton d'interface spécifique pour gérer une instance d'une queue dans une vague de monstres
 */
public class ButtonQueueEditor extends Button{
    /** le temps avant que le monstre spawn */
    private double time;
    /** le chemin vers l'apparence du monstre */
    private String imagePath;
    /** L'image d'interface pour le monstre*/
    private Image image;
    /** le text d'interface pour afficher la variable time */
    private Text text;

    /**
     * Récupère le temps avant que le monstre spawn
     * @return le temps avant que le monstre spawn
     */
    public double getTime() {
        return this.time;
    }

    /**
     * Modifie le temps avant que le monstre spawn
     * @param time Récupère le temps avant que le monstre spawn
     */
    public void setTime(double time) {
        this.time = time;
        this.text.setText(((time+"").length()>4 ? (time+"").substring(0,3) : (time+""))+" s");
    }

    /**
     * Modifie le chemin vers l'apparence du monstre
     * @param imagePath le chemin vers l'apparence du monstre
     */
    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
        this.image.setSprite(imagePath);
    }

    /**
     * Initialise le bouton
     * @param pos la position du bouton
     * @param width la largueur du bouton
     * @param height la hauteur du bouton
     * @param sprite l'apparence du bouton
     * @param spriteHover l'apparence du bouton quand il est survolé par la souris
     * @param action l'action du bouton quand on clique dessus
     * @param parent l'interface à laquelle le bouton appartient
     * @param imagePath le chemin vers l'apparence du monstre
     * @param time le temps avant que le monstre spawn
     * @throws UnsupportedAudioFileException
     * @throws IOException
     * @throws LineUnavailableException
     */
    public ButtonQueueEditor(Position pos, double width, double height, String sprite, String spriteHover, String action, Interface parent, String imagePath, double time) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        super(pos, width, height, sprite, spriteHover, action, parent);
        this.imagePath  = imagePath;
        this.image = new Image(new Position(0.0,0.0),0.04,0.05,this.getParent(),this.imagePath);
        this.text = new Text(new Position(0.0,0.0),0.0,0.0,new Font("Arial", Font.BOLD, 30),this.getParent(),"");
        this.setTime(time);
    }

    /**
     * Modifie la visibilité de l'élément
     * @param visible l'élément est visible
     */
    @Override
    public void setVisible(boolean visible) {
        super.setVisible(visible);
        this.image.setVisible(visible);
        this.text.setVisible(visible);
    }

    /**
     * Modifie la position de l'élément
     * @param position la nouvelle position
     */
    @Override
    public void setPosition(Position position) {
        super.setPosition(position);
        this.image.setPosition(new Position(this.getPosition().getX()-0.035, this.getPosition().getY()));
        this.text.setPosition(new Position(this.getPosition().getX()+0.02, this.getPosition().getY()));
    }

    /**
     * Actualise la logique de l'élément et affiche son apparence
     * @param mouseX la position horizontale de la souris
     * @param mouseY la position verticale de la souris
     * @param deltaTime le temps d'un tick en seconde
     */
    @Override
    public void update(double mouseX, double mouseY, double deltaTime) {
        super.update(mouseX, mouseY, deltaTime);

        image.update(mouseX, mouseY, deltaTime);
        text.update(mouseX, mouseY, deltaTime);
    }
}
