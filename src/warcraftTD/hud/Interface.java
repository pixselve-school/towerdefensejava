package warcraftTD.hud;

import warcraftTD.utils.Position;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public abstract class Interface {
    private final List<Element> listElements;
    private final List<Element> garbage;

    public List<Element> getListElements() {
        return this.listElements;
    }

    public List<Element> getGarbage() {
        return this.garbage;
    }

    public Interface(){
        this.listElements = new ArrayList<Element>();
        this.garbage = new ArrayList<Element>();
    }

    public void addNotifText(Position p, Font font, double deltay, String text, Color color) {
        NotifText notif = new NotifText(p, 0.0, 0.0, font, this, text, deltay, color);
        this.listElements.add(notif);
    }

    public void removeNotif(NotifText text) {
        this.garbage.add(text);
    }

    public abstract void makeAction(String action, Element from) throws IOException, LineUnavailableException, UnsupportedAudioFileException;

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

}
