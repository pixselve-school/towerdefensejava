package warcraftTD.hud;

import warcraftTD.WorldEditor;
import warcraftTD.utils.Position;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

    private Button pathButton;
    private HorizontalGroupBox settingsBox;

    private HorizontalGroupBox waveBox;
    private List<Button> listWaveButton;
    private List<Button> monstersButton;
    private int pageWave;
    private int pageQueue;

    private Button selectedWave;
    private Button selectedQueueInstance;
    private Button selectedMonster;

    //private List<List<QueueInstance>>

    private final Text building_text;
    private TypeBuildEditor building_type;

    public enum TypeBuildEditor{
        Spawn, Path, RemovePath, None
    }

    public TypeBuildEditor getBuilding_type() {
        return this.building_type;
    }

    public void setSelectedWave(Button selectedWave) {
        if(this.selectedWave!=null) this.selectedWave.setEnabled(true);
        this.selectedWave = selectedWave;
        this.pageQueue = 1;
        this.setSelectedQueueInstance(null);
        this.selectedWave.setEnabled(false);
    }

    public void setSelectedQueueInstance(Button selectedQueueInstance) {
        if(this.selectedWave!=null) this.selectedQueueInstance.setEnabled(true);
        this.selectedQueueInstance = selectedQueueInstance;
        this.selectedQueueInstance.setEnabled(false);
    }

    public void setSelectedMonster(Button selectedMonster) {
        this.selectedMonster = selectedMonster;
    }

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
        this.exitBtn = new Button(new Position(0.085, 0.08), 0.15,0.1,"images/mm_button_quit.png","images/mm_button_quit_hover.png","exit", this);
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

        this.lifeText = new Text(new Position(0.51, 0.865), 0.0, 0.0, new Font("Arial", Font.BOLD, 30),this, this.lifeInitial+"");
        this.settingsBox.addHUDElement(this.lifeText);
        this.moneyText = new Text(new Position(0.51, 0.735), 0.0, 0.0, new Font("Arial", Font.BOLD, 30),this, this.moneyInitial+"");
        this.settingsBox.addHUDElement(this.moneyText);
        this.widthText = new Text(new Position(0.51, 0.6), 0.0, 0.0, new Font("Arial", Font.BOLD, 30),this, this.world.getNbSquareX()+"");
        this.settingsBox.addHUDElement(this.widthText);
        this.heightText = new Text(new Position(0.51, 0.475), 0.0, 0.0, new Font("Arial", Font.BOLD, 30),this, this.world.getNbSquareY()+"");
        this.settingsBox.addHUDElement(this.heightText);

        btn = new Button(new Position(0.28, 0.25), 0.06, 0.07, "images/add_firstpath_button.png", "images/add_firstpath_button_hover.png", "AddSpawn", this);
        this.settingsBox.addHUDElement(btn);
        this.pathButton = new Button(new Position(0.28, 0.17), 0.06, 0.07, "images/add_path_button.png", "images/add_path_button_hover.png", "AddPath", this);
        this.settingsBox.addHUDElement(this.pathButton);
        this.pathButton.setEnabled(false);
        btn = new Button(new Position(0.28, 0.09), 0.06, 0.07, "images/clear_path_button.png", "images/clear_path_button_hover.png", "RemovePath", this);
        this.settingsBox.addHUDElement(btn);
        btn = new Button(new Position(0.28, 0.36), 0.06, 0.07, "images/close_button.png", "images/close_button_hover.png", "ClearPath", this);
        this.settingsBox.addHUDElement(btn);

        this.building_text = new Text(new Position(0.5, 0.07), 0.0, 0.0, new Font("Arial", Font.BOLD, 40), this, "Right click to cancel !");
        this.building_text.setVisible(false);
        this.getListElements().add(this.building_text);
        this.building_type = TypeBuildEditor.None;

        this.waveBox = new HorizontalGroupBox(new Position(0.5,0.5), 1.0,1.0,this, "images/WavePanel.png");
        this.getListElements().add(this.waveBox);

        this.listWaveButton = new ArrayList<Button>();
        this.monstersButton = new ArrayList<Button>();
        this.pageWave = 1;
        this.pageQueue = 1;

        for(int i = 0;i<10; i++){
            btn = new Button(new Position(0.727+(i%5)*0.045, 0.627-(i>4 ? 0.08 : 0.0)), 0.04, 0.06, "images/editor/button_monster_"+(i+1)+".png", "images/editor/button_monster_"+(i+1)+"_hover.png", "monster", this);
            this.waveBox.addHUDElement(btn);
            this.monstersButton.add(btn);
        }

        btn = new Button(new Position(0.26, 0.3), 0.08, 0.1, "images/button_upgrade.png", "images/button_upgrade_hover.png", "addWave", this);
        this.waveBox.addHUDElement(btn);
        btn = new Button(new Position(0.18, 0.3), 0.08, 0.1, "images/button_downgrade.png", "images/button_downgrade_hover.png", "removeWave", this);
        this.waveBox.addHUDElement(btn);

        btn = new Button(new Position(0.58, 0.3), 0.06, 0.08, "images/button_upgrade.png", "images/button_upgrade_hover.png", "addQueue", this);
        this.waveBox.addHUDElement(btn);
        btn = new Button(new Position(0.52, 0.3), 0.06, 0.08, "images/button_downgrade.png", "images/button_downgrade_hover.png", "removeQueue", this);
        this.waveBox.addHUDElement(btn);

        btn = new Button(new Position(0.88, 0.46), 0.05, 0.07, "images/button_upgrade.png", "images/button_upgrade_hover.png", "addQueue", this);
        this.waveBox.addHUDElement(btn);
        btn = new Button(new Position(0.76, 0.46), 0.05, 0.07, "images/button_downgrade.png", "images/button_downgrade_hover.png", "removeQueue", this);
        this.waveBox.addHUDElement(btn);

        btn = new Button(new Position(0.39, 0.78), 0.08, 0.1, "images/editor/nextPage_button.png", "images/editor/nextPage_button_hover.png", "nextPageWave", this);
        this.waveBox.addHUDElement(btn);
        btn = new Button(new Position(0.06, 0.78), 0.08, 0.1, "images/editor/previousPage_button.png", "images/editor/previousPage_button_hover.png", "previousPageWave", this);
        this.waveBox.addHUDElement(btn);

        btn = new Button(new Position(0.64, 0.78), 0.06, 0.08, "images/editor/nextPage_button.png", "images/editor/nextPage_button_hover.png", "nextPageQueue", this);
        this.waveBox.addHUDElement(btn);
        btn = new Button(new Position(0.46, 0.78), 0.06, 0.08, "images/editor/previousPage_button.png", "images/editor/previousPage_button_hover.png", "previousPageQueue", this);
        this.waveBox.addHUDElement(btn);

        btn = new Button(new Position(0.5, 0.15), 0.15, 0.09, "images/editor/exitPanel.png", "images/editor/exitPanel_hover.png", "closeWavePanel", this);
        this.waveBox.addHUDElement(btn);

    }

    public void startBuilding(TypeBuildEditor type){
        this.settingsBox.HideBox();
        this.building_text.setVisible(true);
        this.building_type = type;
    }

    public void stopBuilding(){
        this.settingsBox.ShowBox(0.0,0.0);
        this.building_text.setVisible(false);
        this.building_type = TypeBuildEditor.None;
    }

    public void toggleBottomToolbar(boolean visible){
        this.settingsBtn.setVisible(visible);
        this.waveBtn.setVisible(visible);
        this.saveBtn.setVisible(visible);
        this.exitBtn.setVisible(visible);
    }

    public void refreshPositionWavesButton(){
        for(int i = 0;i<this.listWaveButton.size();i++){
            for(int j = 0;j<=(this.listWaveButton.size()-1)/15;j++){

            }
        }
    }

    @Override
    public Boolean onClick(double mouseX, double mouseY, int mouseButton) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        if(mouseButton==3 && !building_type.equals(TypeBuildEditor.None)){
            stopBuilding();
            return true;
        }

        return super.onClick(mouseX, mouseY, mouseButton);
    }

    public Button getPathButton() {
        return this.pathButton;
    }

    @Override
    public void makeAction(String action, Element from) {
        switch (action) {
            case "settings":
                this.toggleBottomToolbar(false);
                this.world.setNeedReleaseMouse(true);

                this.settingsBox.ShowBox(0.0,0.0);
                break;
            case "ClosingSettings":
                this.toggleBottomToolbar(true);
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
                    double maxWidth = 0.0;
                    for(Position p : this.world.getPaths()){
                        if(p.getX()>maxWidth) maxWidth = p.getX();
                    }
                    if(this.world.getNbSquareX()>maxWidth+1) {
                        this.world.setNbSquareX(this.world.getNbSquareX() - 1);
                        this.world.refreshSquareSize();
                        this.widthText.setText(this.world.getNbSquareX() + "");
                    }
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
                    double maxHeight = 0.0;
                    for(Position p : this.world.getPaths()){
                        if(p.getY()>maxHeight) maxHeight = p.getY();
                    }
                    if(this.world.getNbSquareY()>maxHeight+1){
                        this.world.setNbSquareY(this.world.getNbSquareY()-1);
                        this.world.refreshSquareSize();
                        this.heightText.setText(this.world.getNbSquareY()+"");
                    }

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
            case "ClearPath":
                this.world.setPaths(new ArrayList<Position>());
                this.world.setNeedReleaseMouse(true);
                this.pathButton.setEnabled(false);
                break;
            case "AddSpawn":
                if(this.world.getPaths().size()<=1){
                    this.startBuilding(TypeBuildEditor.Spawn);
                }
                this.world.setNeedReleaseMouse(true);
                break;
            case "AddPath":
                if(this.world.getPaths().size()>=1){
                    this.startBuilding(TypeBuildEditor.Path);
                }
                this.world.setNeedReleaseMouse(true);
                break;
            case "RemovePath":
                if(this.world.getPaths().size()>=1){
                    this.startBuilding(TypeBuildEditor.RemovePath);
                }
                this.world.setNeedReleaseMouse(true);
                break;
            case "wave":
                toggleBottomToolbar(false);
                this.waveBox.ShowBox(1.0,0.0);
                this.world.setNeedReleaseMouse(true);
                break;
            case "addWave":
                break;
            case "removeWave":
                break;
            case "closeWavePanel":
                this.waveBox.HideBox();
                this.toggleBottomToolbar(true);
                this.world.setNeedReleaseMouse(true);
                break;
            case "exit":
                this.world.setEnd(true);
                this.world.setNeedReleaseMouse(true);
                break;
        }
    }
}
