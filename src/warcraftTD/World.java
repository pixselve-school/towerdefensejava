package warcraftTD;

import warcraftTD.hud.MainMenu;
import warcraftTD.libs.StdDraw;
import warcraftTD.utils.Position;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public abstract class World {
    private double width;
    private double height;
    private int nbSquareX;
    private int nbSquareY;
    private boolean end;
    private double squareWidth;
    private double squareHeight;
    private boolean wasMouseClicked;

    private MainMenu menu;

    public MainMenu getMenu() {
        return this.menu;
    }

    public void setMenu(MainMenu menu) {
        this.menu = menu;
    }

    public double getWidth() {
        return this.width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getHeight() {
        return this.height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public int getNbSquareX() {
        return this.nbSquareX;
    }

    public void setNbSquareX(int nbSquareX) {
        this.nbSquareX = nbSquareX;
    }

    public int getNbSquareY() {
        return this.nbSquareY;
    }

    public void setNbSquareY(int nbSquareY) {
        this.nbSquareY = nbSquareY;
    }

    public boolean isEnd() {
        return this.end;
    }

    public void setEnd(boolean end) {
        this.end = end;
    }

    public double getSquareWidth() {
        return this.squareWidth;
    }

    public void setSquareWidth(double squareWidth) {
        this.squareWidth = squareWidth;
    }

    public double getSquareHeight() {
        return this.squareHeight;
    }

    public void setSquareHeight(double squareHeight) {
        this.squareHeight = squareHeight;
    }

    public boolean isNeedReleaseMouse() {
        return this.needReleaseMouse;
    }

    public void setNeedReleaseMouse(boolean needReleaseMouse) {
        this.needReleaseMouse = needReleaseMouse;
    }

    public List<Position> getPaths() {
        return this.paths;
    }

    public void setPaths(List<Position> paths) {
        this.paths = paths;
    }

    public double getDeltaTime() {
        return this.deltaTime;
    }

    public void setDeltaTime(double deltaTime) {
        this.deltaTime = deltaTime;
    }

    private boolean needReleaseMouse = false;

    private List<Position> paths;

    private double deltaTime;

    public World(int width, int height, MainMenu menu){
        this.width = width;
        this.height = height;
        this.menu = menu;


        StdDraw.setCanvasSize(width, height);
        StdDraw.enableDoubleBuffering();

        this.deltaTime = 0.0;
        this.paths = new ArrayList<Position>();
        this.end = false;
        this.wasMouseClicked = false;
    }

    public abstract void drawInfos();

    public abstract void drawMouse();

    public abstract void drawBackground();

    public abstract void drawPath();

    public abstract int update() throws UnsupportedAudioFileException, IOException, LineUnavailableException;

    public abstract void mouseClick(double x, double y, int mouseButton) throws UnsupportedAudioFileException, IOException, LineUnavailableException;

    public abstract void singleMouseClick(double x, double y, int mouseButton) throws UnsupportedAudioFileException, IOException, LineUnavailableException;

    public void run() throws UnsupportedAudioFileException, IOException, LineUnavailableException{
        while (!this.end) {
            long time_nano = System.nanoTime();

            StdDraw.clear();


            if (StdDraw.isMousePressed() && !this.wasMouseClicked) {
                this.wasMouseClicked = true;
                this.singleMouseClick(StdDraw.mouseX(), StdDraw.mouseY(), StdDraw.mouseButtonPressed());
            } else if (!StdDraw.isMousePressed() && this.wasMouseClicked) {
                this.wasMouseClicked = false;
            }

            if (StdDraw.isMousePressed()) {
                if (!this.needReleaseMouse) {
                    this.mouseClick(StdDraw.mouseX(), StdDraw.mouseY(), StdDraw.mouseButtonPressed());
                }
            } else if (this.needReleaseMouse) {
                this.needReleaseMouse = false;
            }


            this.updateEvent();
            this.update();
            StdDraw.show();

            int ms = (int) (System.nanoTime() - time_nano) / 1000000;
            int fps = 1000 / ms;
            this.deltaTime = 1.0 / fps;
        }

        endWorld();
    }

    public void updateEvent() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        // optional implementation in children
        return;
    }

    public void endWorld() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        this.menu.setNeedReleaseMouse(true);
        this.menu.run();
    }
}
