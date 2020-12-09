package warcraftTD;

import org.w3c.dom.Text;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Interface {
    private List<HUD_Element> list_HUD_Elements;
    private ButtonHUD shop_btn;
    private ProgressBar waveEnnemyBar;
    private TextHUD waveNameHUD;

    public Interface(){
        this.list_HUD_Elements = new ArrayList<HUD_Element>();
        shop_btn = new ButtonHUD(new Position(0.9,0.1),0.1, 0.1, "images/button_shop.jpg", "images/button_shop_hover.jpg", "Shopping",this);
        this.list_HUD_Elements.add(shop_btn);
        waveEnnemyBar = new ProgressBar(new Position(0.5,0.9),0.25,0.05,"images/bar.jpg","images/bar_fill.png",this);
        this.list_HUD_Elements.add(waveEnnemyBar);
        waveNameHUD = new TextHUD(new Position(0.5,0.95), 0.0,0.0,new Font("Arial", Font.BOLD, 50),this, "Wave 1");
        this.list_HUD_Elements.add(waveNameHUD);
    }

    public void UpdateInterface(double MouseX, double MouseY){
        Iterator<HUD_Element> i = list_HUD_Elements.iterator();
        HUD_Element el;
        while (i.hasNext()) {
            el = i.next();
            el.Update(MouseX, MouseY);
        }
    }

    public void makeAction(String action){
        switch (action) {
            case "Shopping" :
                // TODO Action quand on clique sur le bouton shop
                break;
            case "autres" :
                // TODO Ajouter d'autres actions
                break;
        }
    }

    public void setWaveEnemyProgress(double ProgressPercent){
        this.waveEnnemyBar.setProgressPercent(ProgressPercent);
    }
}
