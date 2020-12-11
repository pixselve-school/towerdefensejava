package warcraftTD;

import java.awt.*;

public class TextHUD extends HUD_Element {
    private String text;
    private Font font;

    public String getText() {
        return this.text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public TextHUD(Position position, double width, double height, Font font, Interface parent, String text) {
        super(position, width, height, parent);
        this.font = font;
        this.text = text;
    }

    @Override
    public void Update(double MouseX, double MouseY) {
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setFont(font);
        StdDraw.text(position.x, position.y, text);
    }

    @Override
    public void onClick(double MouseX, double MouseY) {

    }


}
