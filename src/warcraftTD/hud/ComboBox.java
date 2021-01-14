package warcraftTD.hud;

import warcraftTD.utils.Position;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.awt.*;
import java.io.IOException;

/**
 * Une comboBox
 */
public class ComboBox extends ClickableElement{
    /** Listes des choix de la comboBox */
    String[] listPropositions;
    /** Spécifie si la comboBox est séléctionné */
    boolean selected;
    /** Bouton principal de la comboBox */
    Button mainButton;
    /** Liste des Textes d'interfaces */
    Text[] listTextHUD;
    /** Liste des boutons de chaque choix */
    Button[] listButtons;
    /** Action réalisé quand on change le choix séléctionné */
    String action;
    /** Position initial du comboBox */
    Position initialPosition;

    /**
     * Initialistion d'un élément d'interface
     *
     * @param position la position de l'élément
     * @param width    la largeur de l'élément
     * @param height   la hauteur de l'élément
     * @param parent   l'interface mère de l'élément
     */
    public ComboBox(Position position, double width, double height, Interface parent, String[] listPropositions, String action) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        super(position, width, height, parent);

        this.action = action;

        if(listPropositions!=null && listPropositions.length>0) this.listPropositions = listPropositions;
        else this.listPropositions = new String[]{"default"};

        this.mainButton = new Button(this.getPosition(), this.getWidth(), this.getHeight(), "images/editor/comboBoxDefault.png", "images/editor/comboBoxDefault_hover.png", "Something", this.getParent());

        this.initialPosition = this.getPosition();

        this.listTextHUD = new Text[this.listPropositions.length];
        this.listButtons = new Button[this.listPropositions.length-1];
        for(int i = 0;i<this.listPropositions.length;i++){
            int sizeText = this.listPropositions[i].length();
            int fontSizeIndex = (sizeText<=8 ? 0 : (sizeText<=16 ? 1 : (sizeText<=30 ? 3 : 2)));
            int fontSize = (fontSizeIndex==0 ? 40 : (fontSizeIndex== 1 ? 20 : (fontSizeIndex==3 ? 10 : 15)));
            this.listTextHUD[i] = new Text(new Position(this.getPosition().getX(), this.getPosition().getY()-i*this.getHeight()*0.95-0.005+fontSizeIndex*0.01), 0.0, 0.0, new Font("Arial", Font.BOLD, fontSize), this.getParent(), this.listPropositions[i]);
            if(i!=0) this.listButtons[i-1] = new Button(new Position(this.getPosition().getX(), this.getPosition().getY()-(i)*this.getHeight()*0.85), this.getWidth(), this.getHeight(), "images/editor/comboBoxBar.png", "images/editor/comboBoxBar_hover.png", "Something", this.getParent());
        }
    }

    /**
     * Actualise la position et la police d'écritures des textes
     */
    public void refreshPositionFontText(){
        for(int i = 0;i<this.listPropositions.length;i++){
            int sizeText = this.listPropositions[i].length();
            int fontSizeIndex = (sizeText<=8 ? 0 : (sizeText<=16 ? 1 : (sizeText<=30 ? 3 : 2)));
            int fontSize = (fontSizeIndex==0 ? 40 : (fontSizeIndex== 1 ? 20 : (fontSizeIndex==3 ? 10 : 15)));
            if(i!=0) this.listTextHUD[i].setPosition(new Position(this.listButtons[i-1].getPosition().getX(),this.listButtons[i-1].getPosition().getY()-0.005));
            else this.listTextHUD[i].setPosition(new Position(this.getPosition().getX(),this.getPosition().getY()-0.005));
            this.listTextHUD[i].setFont(new Font("Arial", Font.BOLD, fontSize));
            this.listTextHUD[i].setText(this.listPropositions[i]);
        }
    }

    /**
     * Récupère le texte séléctionné dans la comboBox
     * @return le texte séléctionné dans la comboBox
     */
    public String getSelectedChoice(){
        return this.listPropositions[0];
    }

    /**
     * Modifie la position de l'élément
     * @param position la nouvelle position
     */
    @Override
    public void setPosition(Position position) {
        super.setPosition(position);
        this.mainButton.setPosition(position);
        for(int i = 0;i<this.listPropositions.length;i++){
            if(i!=0) this.listButtons[i-1].setPosition(new Position(this.getPosition().getX(), this.getPosition().getY()-(i)*this.getHeight()*0.85));
        }
        refreshPositionFontText();
    }

    /**
     * Actualise la logique de l'élément et affiche son apparence
     * @param mouseX la position horizontale de la souris
     * @param mouseY la position verticale de la souris
     * @param deltaTime le temps d'un tick en seconde
     */
    @Override
    public void update(double mouseX, double mouseY, double deltaTime) {
        if(this.selected){
            for(int i = this.listPropositions.length-1;i>=0;i--){
                if(i!=0) this.listButtons[i-1].update(mouseX, mouseY, deltaTime);
                this.listTextHUD[i].update(mouseX, mouseY, deltaTime);
            }
        }
        this.mainButton.update(mouseX, mouseY, deltaTime);
        this.listTextHUD[0].update(mouseX, mouseY, deltaTime);
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
    @Override
    public ActionElement onClick(double mouseX, double mouseY) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        if(this.selected){
            if(this.mainButton.onClick(mouseX, mouseY) != null){
                this.selected = false;
                this.mainButton.setSprite("images/editor/comboBoxDefault.png");
                this.mainButton.setSpriteHover("images/editor/comboBoxDefault_hover.png");
            } else {
                boolean changed = false;
                for(int i = 1;i<this.listPropositions.length;i++){
                    if(this.listButtons[i-1].onClick(mouseX, mouseY) != null) {
                        String a = this.listPropositions[0];
                        this.listPropositions[0] = this.listPropositions[i];
                        this.listPropositions[i] = a;
                        changed = true;
                        break;
                    }
                }
                this.selected = false;
                this.mainButton.setSprite("images/editor/comboBoxDefault.png");
                this.mainButton.setSpriteHover("images/editor/comboBoxDefault_hover.png");
                if(changed) {
                    refreshPositionFontText();
                    return new ActionElement(this, action);
                }
            }
            this.getParent().consumeClick();
        } else {
            if(this.mainButton.onClick(mouseX, mouseY) != null){
                this.selected = true;
                this.mainButton.setSprite("images/editor/comboBoxSelected.png");
                this.mainButton.setSpriteHover("images/editor/comboBoxSelected.png");
                this.getParent().consumeClick();
            }
        }
        return null;
    }
}
