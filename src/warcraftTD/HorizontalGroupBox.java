package warcraftTD;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class HorizontalGroupBox extends HUD_Element {
    private List<RelativeHUD_Element> list_HUD_Elements = new ArrayList<RelativeHUD_Element>();
    private double deltax;
    private double deltay;
    private double fromy;
    private double fromx;
    private boolean forward_anim;
    private double speed;

    private Position initialPos;
    private String background = "";


    class RelativeHUD_Element{
        HUD_Element element;
        Position relativepos;

        public RelativeHUD_Element(HUD_Element element, Position relativepos){
            this.element = element;
            this.relativepos = relativepos;
        }
    }

    public HorizontalGroupBox(Position position, double width, double height, Interface parent, String background) {
        super(position, width, height, parent);
        this.initialPos = position;
        this.deltax = 0.0;
        this.deltay = 0.0;
        this.speed = 0.5;
        this.background = background;
        this.visible = false;
    }

    public void addHUDElement(HUD_Element element){
        if(!list_HUD_Elements.contains(element)){
            RelativeHUD_Element el = new RelativeHUD_Element(element, element.position);
            list_HUD_Elements.add(el);
        }
    }

    @Override
    public void Update(double MouseX, double MouseY, double delta_time) {
        if(this.visible){

            if(deltay>0.0) deltay-=speed*delta_time;
            if(deltax>0.0) deltax-=speed*delta_time;

            if(forward_anim) this.position = new Position(initialPos.x-deltax, initialPos.y-deltay);
            else {
                this.position = new Position(initialPos.x-(fromx-deltax), initialPos.y-(fromy-deltay));
                if(deltax<=0.0 && deltay<=0.0) this.visible = false;
            }
            StdDraw.picture(this.position.x, this.position.y, background, width, height);

            Iterator<RelativeHUD_Element> i = list_HUD_Elements.iterator();
            RelativeHUD_Element el;
            while (i.hasNext()) {
                el = i.next();
                el.element.setPosition(new Position((this.position.x-(width/2)+el.relativepos.x*width), (this.position.y-(height/2)+el.relativepos.y*height)));el.element.Update(MouseX, MouseY,delta_time);
            }
        }
    }

    @Override
    public void onClick(double MouseX, double MouseY) {
        if(visible){
            Iterator<RelativeHUD_Element> i = list_HUD_Elements.iterator();
            RelativeHUD_Element el;
            while (i.hasNext()) {
                el = i.next();
                el.element.onClick(MouseX, MouseY);
            }
        }
    }

    public void ShowBox(double fromy, double fromx){
        this.deltax = fromx;
        this.deltay = fromy;
        this.fromy = fromy;
        this.fromx = fromx;
        this.visible = true;
        this.forward_anim = true;
    }

    public void HideBox(){
        this.forward_anim = false;
        this.deltax = this.fromx;
        this.deltay = this.fromy;
        //this.visible = false;
    }
}
