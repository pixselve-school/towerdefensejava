package warcraftTD.hud;

import warcraftTD.utils.Position;

import java.awt.*;
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

    public abstract void makeAction(String action, Element from);

    public void updateInterface(double mouseX, double mouseY, double delta_time) {
        Iterator<Element> i = this.listElements.iterator();
        Element el;
        while (i.hasNext()) {
            el = i.next();
            el.update(mouseX, mouseY, delta_time);
        }
        i = this.garbage.iterator();
        while (i.hasNext()) {
            el = i.next();
            this.listElements.remove(el);
        }
    }

    public Boolean onClick(double mouseX, double mouseY, int mouseButton) {
        Element elc = null;
        String action = "";
        for (Element el : this.listElements) {
            if(el instanceof ClickableElement) action = ((ClickableElement) el).onClick(mouseX, mouseY);
            if (!action.equals("")) {
                elc = el;
                break;
            }
        }
        if (!action.equals("")){
            this.makeAction(action, elc);
            return true;
        }
        return false;
    }

}
