package warcraftTD.hud;

import warcraftTD.utils.Position;
import warcraftTD.utils.Sound;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;

/**
 * Element d'interface utilisateur qui est cliquable à la souris
 */
abstract public class ClickableElement extends Element{
    /** la zone dans laquelle le clique sera pris en compte */
    private HitBox hitBox;
    /** le son produit quand on clique sur l'élément */
    private Sound clickSound;

    /**
     * Récupère le son joué quand on  sur l'élément
     * @return le son joué quand on  sur l'élément
     */
    public Sound getClickSound() {
        return this.clickSound;
    }

    /**
     * Récupère la hitBox de l'élément cliquable
     * @return la hitBox de l'élément cliquable
     */
    public HitBox getHitBox() {
        return this.hitBox;
    }

    /**
     * Initialise l'élément cliquable
     * @param position la position de l'élément
     * @param width la largeur de l'élément
     * @param height la hauteur de l'élément
     * @param parent l'interface mère de l'élément
     * @throws UnsupportedAudioFileException
     * @throws IOException
     * @throws LineUnavailableException
     */
    public ClickableElement(Position position, double width, double height, Interface parent) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        super(position, width, height, parent);
        this.hitBox = new HitBox(position, width, height);
        this.clickSound = new Sound("music/click.wav", false);
    }

    /**
     * Modifie la position de l'élément
     * @param position la nouvelle position
     */
    @Override
    public void setPosition(Position position) {
        super.setPosition(position);
        this.hitBox.refresh(this.getPosition());
    }

    /**
     * Méthode appelé par le world quand la souris est préssée
     * @param mouseX la position horizontale de la souris
     * @param mouseY la position verticale de la souris
     * @return un ActionElement spécifiant si l'élément à consumer le clique et l'action à réaliser
     * @throws UnsupportedAudioFileException
     * @throws IOException
     * @throws LineUnavailableException
     */
    abstract public ActionElement onClick(double mouseX, double mouseY) throws UnsupportedAudioFileException, IOException, LineUnavailableException;

    /**
     * Une classe associant un element d'interface à son action
     */
    protected class ActionElement{
        /** un élément d'interface */
        private Element element;
        /** une action d'interface */
        private String action;

        /**
         * Récupère l'élément d'interface
         * @return l'élément d'interface
         */
        public Element getElement() {
            return this.element;
        }

        /**
         * Change l'élément d'interface
         * @param element le nouvel élément d'interface
         */
        public void setElement(Element element) {
            this.element = element;
        }

        /**
         * Récupère l'action d'interface
         * @return l'action d'interface
         */
        public String getAction() {
            return this.action;
        }

        /**
         * Modifie l'action d'interface
         * @param action l'action d'interface
         */
        public void setAction(String action) {
            this.action = action;
        }

        /**
         * Initialise l'ActionElement
         * @param el un élément d'interface
         * @param action une action d'interface
         */
        public ActionElement(Element el, String action){
            this.element = el;
            this.action = action;
        }

    }
}
