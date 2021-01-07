package warcraftTD.hud;

import warcraftTD.World;
import warcraftTD.WorldEditor;
import warcraftTD.libs.StdDraw;
import warcraftTD.utils.Animation;
import warcraftTD.utils.Level;
import warcraftTD.utils.Position;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;

import java.io.IOException;

public class MainMenu extends Interface {
    private HorizontalGroupBox groupBox;
    private boolean quit;
    private double delta_time;
    private boolean needReleaseMouse;
    private Animation background;
    private World nextWorld;
    private Image title;

    public void setNeedReleaseMouse(boolean needReleaseMouse) {
        this.needReleaseMouse = needReleaseMouse;
    }

    public MainMenu(int width, int height) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        StdDraw.setCanvasSize(width, height);
        StdDraw.enableDoubleBuffering();

        this.nextWorld = null;

        loadBackground("images/mainMenuBackground/capybara/capybara_");
        this.groupBox = new HorizontalGroupBox(new Position(0.5,0.5), 0.4,0.6,this, "");
        this.getListElements().add(this.groupBox);

        Button btn = new Button(new Position(0.5,0.7),0.2,0.1,"images/mm_button_lvl1.png","images/mm_button_lvl1_hover.png","lvl1", this);
        this.groupBox.addHUDElement(btn);
        btn = new Button(new Position(0.5,0.5),0.2,0.1,"images/mm_button_lvl2.png","images/mm_button_lvl2_hover.png","lvl2", this);
        this.groupBox.addHUDElement(btn);
        btn = new Button(new Position(0.5,0.3),0.2,0.1,"images/mm_button_load.png","images/mm_button_load_hover.png","load", this);
        this.groupBox.addHUDElement(btn);
        btn = new Button(new Position(0.5,0.1),0.2,0.1,"images/mm_button_quit.png","images/mm_button_quit_hover.png","quit", this);
        this.groupBox.addHUDElement(btn);

        this.title = new Image(new Position(0.5,0.85), 0.8,0.25,this, "images/titlegame.png");
        this.getListElements().add(this.title);

        this.groupBox.showBox(0.5,0.0);

        btn = new Button(new Position(0.105,0.055),0.2,0.1,"images/leveleditor_button.png","images/leveleditor_button_hover.png","leveleditor", this);
        this.getListElements().add(btn);

        btn = new Button(new Position(0.9,0.05),0.06,0.08,"images/Capybara_Button.png","images/Capybara_Button_hover.png","switchCapybara", this);
        this.getListElements().add(btn);
        btn = new Button(new Position(0.965,0.05),0.06,0.08,"images/Cat_Button.png","images/Cat_Button_hover.png","switchCat", this);
        this.getListElements().add(btn);

        this.quit = false;
        this.delta_time = 0.0;
        this.needReleaseMouse = false;
    }

    public void loadBackground(String path){
        String[] backgroundImages = new String[300];
        for(int i = 0;i<300;i++){
            backgroundImages[i] = path + (i<100 ? "0" : "") + (i<10 ? "0" : "") + i + ".jpg";
        }
        this.background = new Animation(backgroundImages, 1,1,new Position(0.5,0.5), 24, true);
    }

    @Override
    public void updateInterface(double mouseX, double mouseY, double delta_time) {
        this.background.draw(delta_time);

        super.updateInterface(mouseX, mouseY, delta_time);
    }

    @Override
    public void makeAction(String action, Element from) throws IOException, LineUnavailableException, UnsupportedAudioFileException {
        switch (action) {
            case "quit":
                System.exit(0);
                break;
            case "load":
                this.needReleaseMouse = true;
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setCurrentDirectory(new File("levels/"));
                FileNameExtensionFilter filter = new FileNameExtensionFilter("Tower Defense Level", "tdl", "tdl");
                fileChooser.setFileFilter(filter);

                int result = fileChooser.showOpenDialog(StdDraw.getFrame());
                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedLvl = fileChooser.getSelectedFile();
                    Level level = new Level(selectedLvl);
                    nextWorld = level.getWorld(this);
                    this.quit = true;
                }
                break;
            case "lvl1":
                this.needReleaseMouse = true;
                File lvl1 = new File("levels/level1.tdl");
                Level level = new Level(lvl1);
                nextWorld = level.getWorld(this);
                this.quit = true;
                break;
            case "lvl2":
                this.needReleaseMouse = true;
                File lvl2 = new File("levels/level2.tdl");
                Level level2 = new Level(lvl2);
                nextWorld = level2.getWorld(this);
                this.quit = true;
                break;
            case "switchCapybara":
                this.needReleaseMouse = true;
                loadBackground("images/mainMenuBackground/capybara/capybara_");
                break;
            case "switchCat":
                this.needReleaseMouse = true;
                loadBackground("images/mainMenuBackground/kitten/The most dangerous kitten in the world_");
                break;
            case "leveleditor":
                nextWorld = new WorldEditor(1200,800,this);
                this.quit = true;
                break;
        }
    }

    public void run() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        this.quit = false;
        this.nextWorld = null;
        while(nextWorld==null){
            this.startLoop();
        }
        this.nextWorld.setNeedReleaseMouse(true);
        this.nextWorld.run();
    }

    public void startLoop() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        while(!quit){
            long time_nano = System.nanoTime();

            StdDraw.clear();

            if (StdDraw.isMousePressed()) {
                if(!this.needReleaseMouse) this.onClick(StdDraw.mouseX(), StdDraw.mouseY(), StdDraw.mouseButtonPressed());
            } else if(this.needReleaseMouse){
                this.needReleaseMouse = false;
            }
            this.updateInterface(StdDraw.mouseX(), StdDraw.mouseY(), delta_time);

            StdDraw.show();

            int ms = (int) (System.nanoTime() - time_nano) / 1000000;
            int fps = 1000 / ms;
            this.delta_time = 1.0 / fps;

        }
    }
}
