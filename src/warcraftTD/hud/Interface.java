package warcraftTD.hud;

import warcraftTD.utils.Position;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Classe pour créer des interfaces utilisateurs
 */
public abstract class Interface {
    /** liste comportant tous les éléments qui appartiennent à cette interface */
    private final List<Element> listElements;
    /** liste des éléments à supprimer de l'interface à la prochaine update */
    private final List<Element> garbage;

    /**
     * Récupère la liste des éléments qui appartiennent à cette interface
     * @return la liste des éléments qui appartiennent à cette interface
     */
    public List<Element> getListElements() {
        return this.listElements;
    }

    /**
     * Initialise une interface
     */
    public Interface(){
        this.listElements = new ArrayList<Element>();
        this.garbage = new ArrayList<Element>();
    }

    /**
     * Ajoute une notification de texte à l'interface
     * @param p la position de la notification
     * @param font la police d'écriture de la notification
     * @param deltay la distance verticale de l'animation de la notification
     * @param text le texte à afficher
     * @param color la couleur du texte
     */
    public void addNotifText(Position p, Font font, double deltay, String text, Color color) {
        NotifText notif = new NotifText(p, 0.0, 0.0, font, this, text, deltay, color);
        this.listElements.add(notif);
    }

    /**
     * Enlève une notification de texte de l'interface
     * @param text la notification
     */
    public void removeNotif(NotifText text) {
        this.garbage.add(text);
    }

    /**
     * Réalise une action sur l'interface
     * @param action l'action à réaliser
     * @param from l'élément d'où vient l'action à réaliser
     * @throws IOException
     * @throws LineUnavailableException
     * @throws UnsupportedAudioFileException
     */
    public abstract void makeAction(String action, Element from) throws IOException, LineUnavailableException, UnsupportedAudioFileException;

    /**
     * Actualise la logique de l'interface et affiche son apparence
     * @param mouseX la position horizontale de la souris
     * @param mouseY la position verticale de la souris
     * @param deltaTime le temps d'un tick en seconde
     */
    public void updateInterface(double mouseX, double mouseY, double deltaTime) {
        Iterator<Element> i = this.listElements.iterator();
        Element el;
        while (i.hasNext()) {
            el = i.next();
            el.update(mouseX, mouseY, deltaTime);
        }
        i = this.garbage.iterator();
        while (i.hasNext()) {
            el = i.next();
            this.listElements.remove(el);
        }
    }

    /**
     * Méthode appelé par le world quand la souris est préssée
     * @param mouseX la position horizontale de la souris
     * @param mouseY la position verticale de la souris
     * @param mouseButton le bouton de la souris préssée
     * @return le clique sur l'interface à réaliser une action
     * @throws UnsupportedAudioFileException
     * @throws IOException
     * @throws LineUnavailableException
     */
    public Boolean onClick(double mouseX, double mouseY, int mouseButton) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        Element elc = null;
        ClickableElement.ActionElement action = null;
        for (Element el : this.listElements) {
            if(el instanceof ClickableElement) action = ((ClickableElement) el).onClick(mouseX, mouseY);
            if (action!=null) {
                elc = el;
                break;
            }
        }
        if (action!=null){
            this.makeAction(action.getAction(), action.getElement());
            return true;
        }
        return false;
    }

    /**
     * Demande de consumer le click (éviter de pouvoir rester appuyer)
     */
    public abstract void consumeClick();

}
