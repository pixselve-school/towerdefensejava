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
    private HorizontalGroupBox shopBox;

    public Interface(){
        this.list_HUD_Elements = new ArrayList<HUD_Element>();
        shop_btn = new ButtonHUD(new Position(0.9,0.1),0.1, 0.1, "images/button_shop.jpg", "images/button_shop_hover.jpg", "Shopping",this);
        this.list_HUD_Elements.add(shop_btn);
        waveEnnemyBar = new ProgressBar(new Position(0.5,0.9),0.25,0.05,"images/bar.jpg","images/bar_fill.png",this);
        this.list_HUD_Elements.add(waveEnnemyBar);
        waveNameHUD = new TextHUD(new Position(0.5,0.95), 0.0,0.0,new Font("Arial", Font.BOLD, 50),this, "Wave 1");
        this.list_HUD_Elements.add(waveNameHUD);
        shopBox = new HorizontalGroupBox(new Position(0.5,0.1),0.75,0.15,this, "images/background_hor_box.jpg");
        this.list_HUD_Elements.add(shopBox);
        ButtonHUD closeshop_btn = new ButtonHUD(new Position(0.97,0.85),0.03, 0.03, "images/close_btn.jpg", "images/close_btn_hover.jpg", "ClosingBox",this);
        this.shopBox.addHUDElement(closeshop_btn);
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
                shopBox.ShowBox(0.3,0.0);
                shop_btn.visible = false;
                break;
            case "ClosingBox" :
                shopBox.HideBox();
                shop_btn.visible = true;
                break;
            case "autres" :
                // TODO Ajouter d'autres actions
                break;
        }
    }

    public void onClick(double MouseX, double MouseY){
        Iterator<HUD_Element> i = list_HUD_Elements.iterator();
        HUD_Element el;
        while (i.hasNext()) {
            el = i.next();
            el.onClick(MouseX, MouseY);
        }
    }

    public void setWaveEnemyProgress(double ProgressPercent){
        this.waveEnnemyBar.setProgressPercent(ProgressPercent);
    }

    public void setWaveName(String name){
        waveNameHUD.setText(name);
    }
}
