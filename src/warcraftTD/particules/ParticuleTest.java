package warcraftTD.particules;

import warcraftTD.libs.StdDraw;
import warcraftTD.utils.Position;


public class ParticuleTest {
  public static void main(String[] args) {

    ParticuleGenerator particuleGenerator1 = new RandomParticuleGenerator(new Position(0.4, 0.5), 9999999, 0.1, 0.05, new CircleParticule(1, 0.1, 0.2));
    ParticuleGenerator particuleGenerator2 = new RandomParticuleGenerator(new Position(0.6, 0.5), 9999999, 0.1, 0.05, new ImageParticule(1, 0.1, 0.2, "images/poison.png"));
    double deltaTime = 0;

    StdDraw.setCanvasSize(1200, 800);
    StdDraw.enableDoubleBuffering();
    while (true) {
      StdDraw.clear();
      long time_nano = System.nanoTime();

      particuleGenerator1.generateAndDrawParticules(deltaTime);
      particuleGenerator2.generateAndDrawParticules(deltaTime);

      StdDraw.show();

      int ms = (int) (System.nanoTime() - time_nano) / 1000000;
      int fps = 1000 / ms;
      deltaTime = 1.0 / fps;
    }
  }
}
