package warcraftTD.hud;

import warcraftTD.WorldEditor;
import warcraftTD.utils.Position;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.awt.*;
import java.io.IOException;

public class InterfaceEditor extends Interface{
    private WorldEditor world;

    private Button settingsBtn;
    private Button waveBtn;
    private Button exitBtn;
    private Button saveBtn;

    private Text lifeText;
    private int lifeInitial;
    private Text moneyText;
    private int moneyInitial;
    private Text widthText;
    private Text heightText;
    private Text musicText;

    private HorizontalGroupBox settingsBox;

    public InterfaceEditor(WorldEditor world) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        this.world = world;

        this.lifeInitial = 20;
        this.moneyInitial = 50;

        this.settingsBtn = new Button(new Position(0.94, 0.08), 0.1, 0.13, "images/settings_button_editor.png", "images/settings_button_editor_hover.png", "settings", this);
        this.getListElements().add(this.settingsBtn);
        this.waveBtn = new Button(new Position(0.83, 0.08), 0.1, 0.13, "images/wave_button_editor.png", "images/wave_button_editor_hover.png", "wave", this);
        this.getListElements().add(this.waveBtn);
        this.saveBtn = new Button(new Position(0.21, 0.08), 0.08, 0.11, "images/save_button_editor.png", "images/save_button_editor_hover.png", "save", this);
        this.getListElements().add(this.saveBtn);
        this.exitBtn = new Button(new Position(0.085, 0.08), 0.15,0.1,"images/mm_button_quit.png","images/mm_button_quit_hover.png","quit", this);
        this.getListElements().add(this.exitBtn);

        this.settingsBox = new HorizontalGroupBox(new Position(0.88,0.5), 0.32,1.08,this, "images/PanelSettings.png");
        this.getListElements().add(this.settingsBox);
        Button btn = new Button(new Position(0.1, 0.93), 0.06, 0.06, "images/close_button.png", "images/close_button_hover.png", "ClosingSettings", this);
        this.settingsBox.addHUDElement(btn);

        btn = new Button(new Position(0.71, 0.87), 0.055, 0.055, "images/button_upgrade.png", "images/button_upgrade_hover.png", "upgradeLife", this);
        this.settingsBox.addHUDElement(btn);
        btn = new Button(new Position(0.31, 0.87), 0.055, 0.055, "images/button_downgrade.png", "images/button_downgrade_hover.png", "downgradeLife", this);
        this.settingsBox.addHUDElement(btn);

        btn = new Button(new Position(0.71, 0.74), 0.055, 0.055, "images/button_upgrade.png", "images/button_upgrade_hover.png", "upgradeMoney", this);
        this.settingsBox.addHUDElement(btn);
        btn = new Button(new Position(0.31, 0.74), 0.055, 0.055, "images/button_downgrade.png", "images/button_downgrade_hover.png", "downgradeMoney", this);
        this.settingsBox.addHUDElement(btn);

        btn = new Button(new Position(0.71, 0.61), 0.055, 0.055, "images/button_upgrade.png", "images/button_upgrade_hover.png", "upgradeWidth", this);
        this.settingsBox.addHUDElement(btn);
        btn = new Button(new Position(0.31, 0.61), 0.055, 0.055, "images/button_downgrade.png", "images/button_downgrade_hover.png", "downgradeWidth", this);
        this.settingsBox.addHUDElement(btn);

        btn = new Button(new Position(0.71, 0.48), 0.055, 0.055, "images/button_upgrade.png", "images/button_upgrade_hover.png", "upgradeHeight", this);
        this.settingsBox.addHUDElement(btn);
        btn = new Button(new Position(0.31, 0.48), 0.055, 0.055, "images/button_downgrade.png", "images/button_downgrade_hover.png", "downgradeHeight", this);
        this.settingsBox.addHUDElement(btn);

        btn = new Button(new Position(0.79, 0.35), 0.05, 0.065, "images/save_button_editor.png", "images/save_button_editor_hover.png", "musicload", this);
        this.settingsBox.addHUDElement(btn);

        this.lifeText = new Text(new Position(0.51, 0.865), 0.0, 0.0, new Font("Arial", Font.BOLD, 30),this, this.lifeInitial+"");
        this.settingsBox.addHUDElement(this.lifeText);
        this.moneyText = new Text(new Position(0.51, 0.735), 0.0, 0.0, new Font("Arial", Font.BOLD, 30),this, this.moneyInitial+"");
        this.settingsBox.addHUDElement(this.moneyText);
        this.widthText = new Text(new Position(0.51, 0.6), 0.0, 0.0, new Font("Arial", Font.BOLD, 30),this, this.world.getNbSquareX()+"");
        this.settingsBox.addHUDElement(this.widthText);
        this.heightText = new Text(new Position(0.51, 0.475), 0.0, 0.0, new Font("Arial", Font.BOLD, 30),this, this.world.getNbSquareY()+"");
        this.settingsBox.addHUDElement(this.heightText);
        this.musicText = new Text(new Position(0.44, 0.345), 0.0, 0.0, new Font("Arial", Font.BOLD, 30),this, "none");
        this.settingsBox.addHUDElement(this.musicText);
    }

    @Override
    public void makeAction(String action, Element from) {
        switch (action) {
            case "settings":
                this.settingsBtn.setVisible(false);
                this.waveBtn.setVisible(false);
                this.saveBtn.setVisible(false);
                this.exitBtn.setVisible(false);
                this.world.setNeedReleaseMouse(true);

                this.settingsBox.ShowBox(0.0,0.0);
                break;
            case "ClosingSettings":
                this.settingsBtn.setVisible(true);
                this.waveBtn.setVisible(true);
                this.saveBtn.setVisible(true);
                this.exitBtn.setVisible(true);
                this.world.setNeedReleaseMouse(true);

                this.settingsBox.HideBox();
                break;
            case "upgradeWidth":
                if(this.world.getNbSquareX()<50){
                    this.world.setNbSquareX(this.world.getNbSquareX()+1);
                    this.world.refreshSquareSize();
                    this.widthText.setText(this.world.getNbSquareX()+"");
                }
                this.world.setNeedReleaseMouse(true);
                break;
            case "downgradeWidth":
                if(this.world.getNbSquareX()>5){
                    this.world.setNbSquareX(this.world.getNbSquareX()-1);
                    this.world.refreshSquareSize();
                    this.widthText.setText(this.world.getNbSquareX()+"");
                }
                this.world.setNeedReleaseMouse(true);
                break;
            case "upgradeHeight":
                if(this.world.getNbSquareY()<50){
                    this.world.setNbSquareY(this.world.getNbSquareY()+1);
                    this.world.refreshSquareSize();
                    this.heightText.setText(this.world.getNbSquareY()+"");
                }
                this.world.setNeedReleaseMouse(true);
                break;
            case "downgradeHeight":
                if(this.world.getNbSquareY()>5){
                    this.world.setNbSquareY(this.world.getNbSquareY()-1);
                    this.world.refreshSquareSize();
                    this.heightText.setText(this.world.getNbSquareY()+"");
                }
                this.world.setNeedReleaseMouse(true);
                break;
            case "upgradeMoney":
                if(this.moneyInitial<9999){
                    this.moneyInitial+=10;
                    this.moneyText.setText(this.moneyInitial+"");
                }
                this.world.setNeedReleaseMouse(true);
                break;
            case "downgradeMoney":
                if(this.moneyInitial>50){
                    this.moneyInitial-=10;
                    this.moneyText.setText(this.moneyInitial+"");
                }
                this.world.setNeedReleaseMouse(true);
                break;
            case "upgradeLife":
                if(this.lifeInitial<99){
                    this.lifeInitial+=1;
                    this.lifeText.setText(this.lifeInitial+"");
                }
                this.world.setNeedReleaseMouse(true);
                break;
            case "downgradeLife":
                if(this.lifeInitial>1){
                    this.lifeInitial-=1;
                    this.lifeText.setText(this.lifeInitial+"");
                }
                this.world.setNeedReleaseMouse(true);
                break;
        }
    }
}
