package warcraftTD.particules;

import warcraftTD.libs.StdDraw;


public class ParticuleTest {
  public static void main(String[] args) {


    StdDraw.setCanvasSize(1200, 800);
    StdDraw.enableDoubleBuffering();

    StdDraw.clear();


    StdDraw.pictureHeight(0.5, 0.5, "images/tower_arrow.png", 0.2);

    StdDraw.show();


  }
}
