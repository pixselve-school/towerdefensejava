package warcraftTD;

import java.awt.*;

public class NotifTextHUD extends TextHUD {
    private double deltay;
    private double maxdeltay;
    private boolean UpDirection = true;
    private Position initpos;
    private double speed;

    public NotifTextHUD(Position position, double width, double height, Font font, Interface parent, String text, double deltay) {
        super(position, width, height, font, parent, text);
        this.initpos = position;
        this.speed = 0.5;
        if(deltay>0) {
            this.deltay = deltay;
            this.maxdeltay = this.deltay;
        }
        else {
            this.deltay = -deltay;
            this.maxdeltay = this.deltay;
            this.UpDirection = false;
        }

    }

    @Override
    public void Update(double MouseX, double MouseY, double delta_time) {
        if(visible){
            if (this.deltay > 0.0) this.deltay -= this.speed * delta_time;
            else parent.removeNotif(this);

            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.setFont(this.font);
            StdDraw.text(this.position.x, (UpDirection ? this.initpos.y + deltay : this.initpos.y + (maxdeltay - deltay)), this.text);
        }
    }

}
