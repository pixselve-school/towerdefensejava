package warcraftTD.utils;

import warcraftTD.libs.StdDraw;
import warcraftTD.world.Tile;
import warcraftTD.world.Water;

import java.awt.*;

public class Cursor {
  double animationTime;
  boolean upAnimation;
  Color color;
  double width;
  double height;

  public Cursor(double width, double height) {
    this.animationTime = 1.0;
    this.width = width;
    this.height = height;
    this.upAnimation = false;
  }

  public void setColor(Color color) {
    this.color = color;
    this.animationTime = 1.0;
    this.upAnimation = false;
  }

  public void setColorByTileUnder(Tile tile) {
    if (tile instanceof Water) {
        this.setColor(new Color(0, 0, 0));
    } else if (tile.getContains() != null) {
      switch (tile.getContains().getBuildable()) {
        case BUILDABLE:
            this.setColor(new Color(0, 255, 0));
          return;
        case PAYING:
            this.setColor(new Color(255, 150, 0));
          return;
        case NOTBUILDABLE:
            this.setColor(new Color(255, 0, 0));
          return;
      }
    } else if (tile.isBuildable()) this.setColor(new Color(0, 255, 0));
    else this.setColor(new Color(255, 0, 0));

  }

  public void update(Position p, double deltaTime) {
    StdDraw.setPenRadius(0.005);
    Color c = new Color((float) this.color.getRed() / 255, (float) this.color.getGreen() / 255, (float) this.color.getBlue() / 255, (float) (this.animationTime));
    StdDraw.setPenColor(c);
    StdDraw.rectangle(p.getX(), p.getY(), this.width, this.height);
    if (this.upAnimation) {
      this.animationTime += deltaTime * 2;
      if (this.animationTime > 1.0) {
        this.upAnimation = false;
        this.animationTime = 1.0;
      }
    } else {
      this.animationTime -= deltaTime * 2;
      if (this.animationTime < 0.0) {
        this.upAnimation = true;
        this.animationTime = 0.0;
      }
    }
  }


}
