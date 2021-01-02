package warcraftTD.hud;

import warcraftTD.libs.StdDraw;
import warcraftTD.utils.Position;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class HorizontalGroupBox extends Element {
  private final List<RelativeHUD_Element> list_HUD_Elements = new ArrayList<RelativeHUD_Element>();
  private double deltax;
  private double deltay;
  private double fromy;
  private double fromx;
  private boolean forward_anim;
  private final double speed;

  private final Position initialPos;
  private String background = "";


  class RelativeHUD_Element {
    Element element;
    Position relativepos;

    public RelativeHUD_Element(Element element, Position relativepos) {
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

  public void addHUDElement(Element element) {
    if (!this.list_HUD_Elements.contains(element)) {
      RelativeHUD_Element el = new RelativeHUD_Element(element, element.position);
      this.list_HUD_Elements.add(el);
    }
  }

  @Override
  public void Update(double MouseX, double MouseY, double delta_time) {
    if (this.visible) {

      if (this.deltay > 0.0) this.deltay -= this.speed * delta_time;
      if (this.deltax > 0.0) this.deltax -= this.speed * delta_time;

      if (this.forward_anim)
        this.position = new Position(this.initialPos.getX() - this.deltax, this.initialPos.getY() - this.deltay);
      else {
        this.position = new Position(this.initialPos.getX() - (this.fromx - this.deltax), this.initialPos.getY() - (this.fromy - this.deltay));
        if (this.deltax <= 0.0 && this.deltay <= 0.0) this.visible = false;
      }
      StdDraw.picture(this.position.getX(), this.position.getY(), this.background, this.width, this.height);

      Iterator<RelativeHUD_Element> i = this.list_HUD_Elements.iterator();
      RelativeHUD_Element el;
      while (i.hasNext()) {
        el = i.next();
        el.element.setPosition(new Position((this.position.getX() - (this.width / 2) + el.relativepos.getX() * this.width), (this.position.getY() - (this.height / 2) + el.relativepos.getY() * this.height)));
        el.element.Update(MouseX, MouseY, delta_time);
      }
    }
  }

  @Override
  public String onClick(double MouseX, double MouseY) {
    if (this.visible) {
      Iterator<RelativeHUD_Element> i = this.list_HUD_Elements.iterator();
      RelativeHUD_Element el;
      String action = "";
      while (i.hasNext()) {
        el = i.next();
        action = el.element.onClick(MouseX, MouseY);
        if (!action.equals("")) break;
      }
      return action;
    }
    return "";
  }

  public void ShowBox(double fromy, double fromx) {
    this.deltax = fromx;
    this.deltay = fromy;
    this.fromy = fromy;
    this.fromx = fromx;
    this.visible = true;
    this.forward_anim = true;
  }

  public void HideBox() {
    this.forward_anim = false;
    this.deltax = this.fromx;
    this.deltay = this.fromy;
    //this.visible = false;
  }
}
