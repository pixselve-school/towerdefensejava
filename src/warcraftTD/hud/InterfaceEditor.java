package warcraftTD.hud;

import warcraftTD.WorldEditor;
import warcraftTD.libs.StdDraw;
import warcraftTD.utils.Position;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Interface utilisateur pour l'éditeur de niveau
 */
public class InterfaceEditor extends Interface{
    /** Référence vers le monde d'édition */
    private WorldEditor world;
    /** Vies avec lesquels le joueur commence pour ce niveau */
    private int lifeInitial;
    /** Argent avec lequel le joueur commence pour ce niveau */
    private int moneyInitial;

    /**
     *  ################ Affichage de la toolBar de base
     */

    /** Boutons pour affiches le panel d'options du niveau */
    private Button settingsBtn;
    /** Bouton pour afficher le panel des vagues de monstres */
    private Button waveBtn;
    /** Bouton pour quitter le level editor */
    private Button exitBtn;
    /** Bouton pour sauvegarder son niveau actuel */
    private Button saveBtn;
    /** Group Box Building Pannel */
    private GroupBox groupBoxBuilding;

    /**
     *  ################ Affichage du Panel des options
     */

    /** Texte affichant les vies initiales du niveau */
    private Text lifeText;
    /** Texte affichant l'argent initiale du niveau */
    private Text moneyText;
    /** Texte affichant la largeur du niveau */
    private Text widthText;
    /** Texte affichant la hauteur du niveau */
    private Text heightText;
    /** Bouton pour créer de nouveau chemins (activé uniquement si il y a deja un spawn de posé) */
    private Button pathButton;
    /** Texte indiquant comment quitter le mode construction */
    private final Text buildingText;
    /** GroupBox du panel options */
    private GroupBox settingsBox;
    /** ComboBox pour activer ou désactiver l'eau */
    private ComboBox comboBoxWater;
    /** ComboBox pour activer ou désactiver la végétation */
    private ComboBox comboBoxPlants;
    /** Type de construction actuellement */
    private TypeBuildEditor buildingType;

    /**
     *  ################ Affichage du Panel des vagues
     */

    /** GroupBox du panel des vagues */
    private GroupBox waveBox;
    /** List des boutons associés à chaque vague  */
    private List<Button> listWaveButton;
    /** List des Textes de numéros de vague associer à chaque bouton  */
    private List<Text> listTextWave;
    /** List des boutons de selection de monstre  */
    private List<Button> monstersButton;
    /** List des boutons de queue de Monstres pour la vague séléctionnées */
    private List<Button> listQueueButton;
    /** List des QueueMonster pour chaque vague */
    private List<List<QueueMonster>> listQueue;
    /** Texte affichant la page actuelle des vagues */
    private Text pageWaveText;
    /** Texte affichant la page actuelle des queues Monster */
    private Text pageQueueText;
    /** Texte affichant la durée d'attente pour le monstre de la queueMonster séléctionné */
    private Text timeQueueMonster;
    /** Bouton de la vague actuellement séléctionnée */
    private Button selectedWave;
    /** Bouton de QueueMonster actuellement séléctionnée */
    private Button selectedQueueInstance;
    /** Bouton de Monstre actuellement séléctionnée */
    private Button selectedMonster;

    /** numéro de page des vagues actuellement */
    private int pageWave;
    /** numéro de page des queueMonsters actuellement */
    private int pageQueue;

    /**
     * Enum pour chaque type de construction pour l'édition du terrain
     */
    public enum TypeBuildEditor{
        Spawn, Path, RemovePath, None
    }

    /**
     * Associe un monstre à un temps d'attente avant qu'il ne spawn dans la vague
     */
    private class QueueMonster{
        /** un id de monstre */
        private int monster;
        /** temps d'attente avant spawn */
        private double timeBeforeSpawning;

        /**
         * Modifie l'id du monstre actuel de la QueueMonster
         * @param monster le nouveau monstre
         */
        public void setMonster(int monster) {
            this.monster = monster;
        }

        /**
         * Récupère l'id du monstre de la QueueMonster
         * @return le monstre de la QueueMonster
         */
        public int getMonster() {
            return this.monster;
        }

        /**
         * Récupère le temps d'attente avant le spawn du monstre
         * @return le temps d'attente avant le spawn du monstre
         */
        public double getTimeBeforeSpawning() {
            return this.timeBeforeSpawning;
        }

        /**
         * Modifie le temps d'attente avant le spawn du monstre
         * @param timeBeforeSpawning le nouveau temps d'attente
         */
        public void setTimeBeforeSpawning(double timeBeforeSpawning) {
            this.timeBeforeSpawning = timeBeforeSpawning;
        }

        /**
         * Initialise la QueueMonster
         * @param monster un id de monstre
         * @param timeBeforeSpawning temps d'attente
         */
        public QueueMonster(int monster, double timeBeforeSpawning) {
            this.monster = monster;
            this.timeBeforeSpawning = timeBeforeSpawning;
        }
    }

    /**
     * Récupère le bouton de construction de chemin
     * @return le bouton de construction de chemin
     */
    public Button getPathButton() {
        return this.pathButton;
    }

    /**
     * Récupère le type de construction actuellement
     * @return le type de construction actuellement
     */
    public TypeBuildEditor getBuildingType() {
        return this.buildingType;
    }

    /**
     * Initialise une interface d'éditeur de level
     * @param world le world d'édition
     * @throws UnsupportedAudioFileException
     * @throws IOException
     * @throws LineUnavailableException
     */
    public InterfaceEditor(WorldEditor world) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        this.world = world;

        this.lifeInitial = 20;
        this.moneyInitial = 50;

        this.settingsBtn = new Button(new Position(0.94, 0.08), 0.1, 0.13, "images/settings_button_editor.png", "images/settings_button_editor_hover.png", "settings", this);
        this.getListElements().add(this.settingsBtn);
        this.waveBtn = new Button(new Position(0.83, 0.08), 0.1, 0.13, "images/wave_button_editor.png", "images/wave_button_editor_hover.png", "wave", this);
        this.getListElements().add(this.waveBtn);
        this.saveBtn = new Button(new Position(0.21, 0.08), 0.08, 0.11, "images/save_button_editor.png", "images/save_button_editor_hover.png", "saveMap", this);
        this.getListElements().add(this.saveBtn);
        this.exitBtn = new Button(new Position(0.085, 0.08), 0.15,0.1,"images/mm_button_quit.png","images/mm_button_quit_hover.png","exit", this);
        this.getListElements().add(this.exitBtn);

        this.settingsBox = new GroupBox(new Position(0.88,0.5), 0.32,1.08,this, "images/editor/PanelSettings.png");
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

        this.comboBoxWater = new ComboBox(new Position(0.5, 0.33),0.15, 0.07, this, new String[]{"OFF", "ON"}, "comboBoxWater");
        this.settingsBox.addHUDElement(this.comboBoxWater);
        this.comboBoxPlants = new ComboBox(new Position(0.5, 0.18),0.175, 0.07, this, new String[]{"None", "Plants", "Plants & Trees"}, "comboBoxPlants");
        this.settingsBox.addHUDElement(this.comboBoxPlants);

        this.groupBoxBuilding = new GroupBox(new Position(0.5,0.5), 1.0,1.0,this, "images/editor/BuildingPanelEditor.png");
        this.getListElements().add(this.groupBoxBuilding);
        this.getListElements().add(this.settingsBox);
        this.groupBoxBuilding.showBox();

        btn = new Button(new Position(0.33, 0.93), 0.08, 0.11, "images/editor/AddSpawnButon.png", "images/editor/AddSpawnButon_hover.png", "AddSpawn", this);
        this.groupBoxBuilding.addHUDElement(btn);
        this.pathButton = new Button(new Position(0.435, 0.93), 0.08, 0.11, "images/editor/AddPathButon.png", "images/editor/AddPathButon_hover.png", "AddPath", this);
        this.groupBoxBuilding.addHUDElement(this.pathButton);
        this.pathButton.setEnabled(false);
        btn = new Button(new Position(0.54, 0.93), 0.08, 0.11, "images/editor/RemovePathButon.png", "images/editor/RemovePathButon_hover.png", "RemovePath", this);
        this.groupBoxBuilding.addHUDElement(btn);
        btn = new Button(new Position(0.645, 0.93), 0.08, 0.11, "images/clear_path_button.png", "images/clear_path_button_hover.png", "ClearPath", this);
        this.groupBoxBuilding.addHUDElement(btn);

        this.buildingText = new Text(new Position(0.5, 0.07), 0.0, 0.0, new Font("Arial", Font.BOLD, 40), this, "Right click to cancel !");
        this.buildingText.setVisible(false);
        this.getListElements().add(this.buildingText);
        this.buildingType = TypeBuildEditor.None;

        this.waveBox = new GroupBox(new Position(0.5,0.5), 1.0,1.0,this, "images/WavePanel.png");
        this.getListElements().add(this.waveBox);

        this.listWaveButton = new ArrayList<Button>();
        this.monstersButton = new ArrayList<Button>();
        this.pageWave = 1;
        this.pageQueue = 1;

        for(int i = 0;i<10; i++){
            btn = new Button(new Position(0.727+(i%5)*0.045, 0.627-(i>4 ? 0.08 : 0.0)), 0.04, 0.06, "images/editor/button_monster_"+(i+1)+".png", "images/editor/button_monster_"+(i+1)+"_hover.png", "selectMonster", this);
            btn.setEnabled(false);
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

        btn = new Button(new Position(0.88, 0.46), 0.05, 0.07, "images/button_upgrade.png", "images/button_upgrade_hover.png", "addTimeQueue", this);
        this.waveBox.addHUDElement(btn);
        btn = new Button(new Position(0.76, 0.46), 0.05, 0.07, "images/button_downgrade.png", "images/button_downgrade_hover.png", "removeTimeQueue", this);
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

        this.listTextWave = new ArrayList<Text>();
        this.listQueueButton = new ArrayList<Button>();
        this.listQueue = new ArrayList<List<QueueMonster>>();

        this.pageWaveText = new Text(new Position(0.37,0.3),0.0,0.0,new Font("Arial", Font.BOLD, 45), this, "p 1/1");
        this.waveBox.addHUDElement(this.pageWaveText);
        this.pageQueueText = new Text(new Position(0.55,0.72),0.0,0.0,new Font("Arial", Font.BOLD, 35), this, "p 1/1");
        this.waveBox.addHUDElement(this.pageQueueText);

        this.timeQueueMonster = new Text(new Position(0.82,0.46),0.0,0.0,new Font("Arial", Font.BOLD, 30), this, "1.0");
        this.waveBox.addHUDElement(this.timeQueueMonster);
    }

    /**
     * Modifie le bouton de vague séléctionnée
     * @param selectedWave nouvelle vague à séléctionner
     * @throws UnsupportedAudioFileException
     * @throws IOException
     * @throws LineUnavailableException
     */
    public void setSelectedWave(Button selectedWave) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        if(this.selectedWave!=null) this.selectedWave.setEnabled(true);
        this.selectedWave = selectedWave;
        this.setSelectedQueueInstance(null);
        if(this.selectedWave!=null) {
            this.selectedWave.setEnabled(false);
            this.refreshQueueButtons();
        }
        switchPageQueue(1);
    }

    /**
     * Actualise l'apparence des Boutons de QueueMonsters (vague management)
     * @throws UnsupportedAudioFileException
     * @throws IOException
     * @throws LineUnavailableException
     */
    public void refreshQueueButtons() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        int i = this.listWaveButton.indexOf(this.selectedWave);
        if(i==-1) return;
        for(Button btn : this.listQueueButton){
            this.waveBox.removeHUDElement(btn);
        }
        this.listQueueButton = new ArrayList<Button>();
        for(QueueMonster queue : this.listQueue.get(i)){
            ButtonQueueEditor btnq = new ButtonQueueEditor(new Position(0.0,0.0),0.13,0.05,"images/largeButton_empty.png", "images/largeButton_empty_hover.png","selectQueue",this, "images/enemies/"+queue.getMonster()+"/die-0.png", queue.getTimeBeforeSpawning());
            generateButtonQueue(btnq);
        }
    }

    /**
     * Ajoute un nouveau bouton de QueueMonster pour la vague actuelle
     * @param btn le bouton
     */
    public void generateButtonQueue(Button btn){
        int i = this.listWaveButton.indexOf(this.selectedWave);
        if(i==-1) return;
        int j = this.listQueueButton.size();
        double delta_y = 0.05;
        btn.setPosition(new Position(0.55, 0.68-delta_y*(j%7)));
        this.listQueueButton.add(btn);
        this.waveBox.addHUDElement(btn);
        int page = j/7 + 1;
        if(page!=pageQueue){
            this.listQueueButton.get(j).setVisible(false);
        }
    }

    /**
     * Modifie le bouton de QueueMonster séléctionnée
     * @param selectedQueueInstance le bouton
     */
    public void setSelectedQueueInstance(Button selectedQueueInstance) {
        if(this.selectedQueueInstance!=null) this.selectedQueueInstance.setEnabled(true);
        this.selectedQueueInstance = selectedQueueInstance;
        if(this.selectedQueueInstance!=null){
            this.selectedQueueInstance.setEnabled(false);
            for(Button btn : this.monstersButton){
                btn.setEnabled(true);
            }
            int i = this.listQueueButton.indexOf(this.selectedQueueInstance);
            int j = this.listQueue.get(this.listWaveButton.indexOf(this.selectedWave)).get(i).getMonster();
            double time = this.listQueue.get(this.listWaveButton.indexOf(this.selectedWave)).get(i).getTimeBeforeSpawning();
            this.setSelectedMonster(this.monstersButton.get(j-1));
            this.timeQueueMonster.setText((time+"").length()>4 ? (time+"").substring(0,3) : (time+""));
        } else {
            for(Button btn : this.monstersButton){
                btn.setEnabled(false);
            }
        }
    }

    /**
     * Modifie le bouton de Monstre séléctionnée
     * @param selectedMonster le bouton
     */
    public void setSelectedMonster(Button selectedMonster) {
        if(this.selectedMonster!=null) this.selectedMonster.setEnabled(true);
        this.selectedMonster = selectedMonster;
        if(this.selectedMonster!=null){
            this.selectedMonster.setEnabled(false);
            int i = this.monstersButton.indexOf(this.selectedMonster);
            ((ButtonQueueEditor)this.selectedQueueInstance).setImagePath("images/enemies/"+(i+1)+"/die-0.png");
            int iw = this.listWaveButton.indexOf(this.selectedWave);
            int iq = this.listQueueButton.indexOf(this.selectedQueueInstance);
            this.listQueue.get(iw).get(iq).setMonster(i+1);
        }
    }

    /**
     * Lance l'édition du terrain / la construction
     * @param type le type de construction
     */
    public void startBuilding(TypeBuildEditor type){
        if(this.settingsBox.isVisible()) this.settingsBox.HideBox();
        if(this.waveBox.isVisible()) this.waveBox.HideBox();
        this.buildingText.setVisible(true);
        this.buildingType = type;
        this.groupBoxBuilding.HideBox();
        this.toggleBottomToolbar(false);
    }

    /**
     * Stop l'édition du terrain / le mode construction
     */
    public void stopBuilding(){
        this.buildingText.setVisible(false);
        this.buildingType = TypeBuildEditor.None;
        this.groupBoxBuilding.showBox();
        this.toggleBottomToolbar(true);
        this.consumeClick();
    }

    /**
     * Change la visibilité de la toolbar
     * @param visible nouvelle visibilité
     */
    public void toggleBottomToolbar(boolean visible){
        this.settingsBtn.setVisible(visible);
        this.waveBtn.setVisible(visible);
        this.saveBtn.setVisible(visible);
        this.exitBtn.setVisible(visible);
    }

    /**
     * Actualise la position des boutons des vagues
     */
    public void refreshPositionWavesButton(){
        double delta_x = 0.063;
        double delta_y = 0.12;
        for(int i = 0;i<this.listWaveButton.size();i++){
                int page = i/15 + 1;
                listWaveButton.get(i).setPosition(new Position(0.1+delta_x*(i%5), 0.67-delta_y*((i-(page-1)*15)/5)));
                listTextWave.get(i).setPosition(new Position(0.1+delta_x*(i%5), 0.67-delta_y*((i-(page-1)*15)/5)));
                listTextWave.get(i).setText(i+1+"");
                if(page!=pageWave){
                    listWaveButton.get(i).setVisible(false);
                    listTextWave.get(i).setVisible(false);
                }
                else {
                    listWaveButton.get(i).setVisible(true);
                    listTextWave.get(i).setVisible(true);
                }
        }
    }

    /**
     * Actualise les positions des boutons des QueuesMonster
     */
    public void refreshPositionQueueButton(){
        double delta_y = 0.05;
        for(int i = 0;i < this.listQueueButton.size();i++){
            int page = i/7 + 1;
            listQueueButton.get(i).setPosition(new Position(0.55, 0.68-delta_y*(i%7)));
            if(page!=pageQueue){
                listQueueButton.get(i).setVisible(false);
            }
            else {
                listQueueButton.get(i).setVisible(true);
            }
        }
    }

    /**
     * Change la page actuelle des boutons de vagues
     * @param page la nouvelle page
     */
    public void switchPageWave(int page){
        if(page > (this.listWaveButton.size()-1)/15 + 1) return;
        else if (page<1) return;
        this.pageWave = page;

        for(int i = 0;i<this.listWaveButton.size();i++){
            int pagei = i/15 + 1;
            if(pagei!=pageWave) {
                listWaveButton.get(i).setVisible(false);
                listTextWave.get(i).setVisible(false);
            }
            else {
                listWaveButton.get(i).setVisible(true);
                listTextWave.get(i).setVisible(true);
            }
        }

        this.pageWaveText.setText("p "+this.pageWave+"/"+((this.listWaveButton.size()-1)/15 + 1));
    }

    /**
     * Change la page actuelle des boutons de QueuesMonster
     * @param page la nouvelle page
     */
    public void switchPageQueue(int page){
        if(page > (this.listQueueButton.size()-1)/7 + 1) return;
        else if (page<1) return;
        this.pageQueue = page;

        for(int i = 0;i<this.listQueueButton.size();i++){
            int pagei = i/7 + 1;
            if(pagei!=this.pageQueue) {
                this.listQueueButton.get(i).setVisible(false);
            }
            else {
                this.listQueueButton.get(i).setVisible(true);
            }
        }

        this.pageQueueText.setText("p "+this.pageQueue+"/"+((this.listQueueButton.size()-1)/7 + 1));
    }

    /**
     * Ajoute un nouveau bouton dans les boutons de vagues
     * @param btn le bouton
     * @return Le bouton est ajouté
     */
    public boolean addingWaveButton(Button btn){
        int i = this.listWaveButton.size();
        if(i==60) return false;
        int j = (this.listWaveButton.size()-1)/15;
        this.listWaveButton.add(btn);
        double delta_x = 0.063;
        double delta_y = 0.12;
        int page = i/15 + 1;
        btn.setPosition(new Position(0.1+delta_x*(i%5), 0.67-delta_y*((i-(page-1)*15)/5)));
        listTextWave.add(new Text(new Position(0.1+delta_x*(i%5), 0.67-delta_y*((i-(page-1)*15)/5)),0.0,0.0,new Font("Arial", Font.BOLD, 35),this,i+1+""));
        this.listQueue.add(new ArrayList<QueueMonster>());
        this.waveBox.addHUDElement(btn);
        this.waveBox.addHUDElement(listTextWave.get(listTextWave.size()-1));
        if(page!=pageWave){
            listWaveButton.get(i).setVisible(false);
            listTextWave.get(i).setVisible(false);
        } else {
            listWaveButton.get(i).setVisible(true);
            listTextWave.get(i).setVisible(true);
        }
        return true;
    }

    /**
     * Ajoute un nouveau bouton dans les boutons de QueueMonster
     * @param btn le bouton
     * @return Le bouton est ajouté
     */
    public boolean addingQueueButton(Button btn){
        int i = this.listWaveButton.indexOf(this.selectedWave);
        if(i==-1) return false;
        int j = this.listQueue.get(i).size();
        double delta_y = 0.05;
        btn.setPosition(new Position(0.55, 0.68-delta_y*(j%7)));
        this.listQueueButton.add(btn);
        this.waveBox.addHUDElement(btn);
        this.listQueue.get(i).add(new QueueMonster(1,1.0));
        int page = j/7 + 1;
        if(page!=pageQueue){
            this.listQueueButton.get(j).setVisible(false);
        }
        return true;
    }

    /**
     * Méthode appelé par le world quand la souris est préssée
     * @param mouseX la position horizontale de la souris
     * @param mouseY la position verticale de la souris
     * @param mouseButton le bouton de la souris préssée
     * @return le clique sur l'interface à réaliser une action
     * @throws UnsupportedAudioFileException
     * @throws IOException
     * @throws LineUnavailableException
     */
    @Override
    public Boolean onClick(double mouseX, double mouseY, int mouseButton) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        if(mouseButton==3 && !buildingType.equals(TypeBuildEditor.None)){
            stopBuilding();
            return true;
        }

        return super.onClick(mouseX, mouseY, mouseButton);
    }

    /**
     * Demande de consumer le click (éviter de pouvoir rester appuyer)
     */
    @Override
    public void consumeClick() {
        this.world.setNeedReleaseMouse(true);
    }

    /**
     * Réalise une action sur l'interface
     * @param action l'action à réaliser
     * @param from l'élément d'où vient l'action à réaliser
     * @throws IOException
     * @throws LineUnavailableException
     * @throws UnsupportedAudioFileException
     */
    @Override
    public void makeAction(String action, Element from) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        switch (action) {
            case "settings":
                this.toggleBottomToolbar(false);
                this.world.setNeedReleaseMouse(true);

                this.settingsBox.showBox(new Position(1.3,0.5), new Position(0.88,0.5), 0.5);
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
                    this.world.generatePath();
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
                        this.world.generatePath();
                        this.widthText.setText(this.world.getNbSquareX() + "");
                    }
                }
                this.world.setNeedReleaseMouse(true);
                break;
            case "upgradeHeight":
                if(this.world.getNbSquareY()<50){
                    this.world.setNbSquareY(this.world.getNbSquareY()+1);
                    this.world.refreshSquareSize();
                    this.world.generatePath();
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
                        this.world.generatePath();
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
                this.world.clearPath();
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
                this.waveBox.showBox(new Position(0.5, -0.35), new Position(0.5, 0.5), 1.0);
                this.world.setNeedReleaseMouse(true);
                break;
            case "addWave":
                Button btn = new Button(new Position(0.0,0.0),0.06,0.08,"images/empty_button.png", "images/empty_button_hover.png","selectWave",this);
                if(addingWaveButton(btn)) {
                    setSelectedWave(btn);
                    int page = (this.listWaveButton.size()-1)/15 +1;
                    if(page!=this.pageWave) switchPageWave(page);
                }
                this.world.setNeedReleaseMouse(true);
                break;
            case "removeWave":
                if(this.selectedWave != null){
                    int i = this.listWaveButton.indexOf(this.selectedWave);
                    List<QueueMonster> l = this.listQueue.get(i);
                    if(i!=-1){
                        this.waveBox.removeHUDElement(this.selectedWave);
                        this.waveBox.removeHUDElement(this.listTextWave.get(i));
                        //this.getGarbage().add(this.selectedWave);
                        //this.getGarbage().add(this.listTextWave.get(i));
                        this.listWaveButton.remove(this.selectedWave);
                        this.listTextWave.remove(this.listTextWave.get(i));
                        this.listQueue.remove(l);
                        if(i!=0) this.setSelectedWave(this.listWaveButton.get(i-1));
                        else this.setSelectedWave(null);
                        refreshPositionWavesButton();
                        int page = (i-1)/15 +1;
                        if(page!=this.pageWave) switchPageWave(page);
                    }
                }
                this.world.setNeedReleaseMouse(true);
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
            case "nextPageWave":
                this.world.setNeedReleaseMouse(true);
                this.switchPageWave(this.pageWave+1);
                break;
            case "previousPageWave":
                this.world.setNeedReleaseMouse(true);
                this.switchPageWave(this.pageWave-1);
                break;
            case "selectWave":
                if(this.selectedWave!=from){
                    this.setSelectedWave((Button)from);
                }
                break;
            case "addQueue":
                if(this.selectedWave!=null){
                    ButtonQueueEditor btnq = new ButtonQueueEditor(new Position(0.0,0.0),0.13,0.05,"images/largeButton_empty.png", "images/largeButton_empty_hover.png","selectQueue",this, "images/enemies/1/die-0.png", 1.0);
                    if(addingQueueButton(btnq)) {
                        setSelectedQueueInstance(btnq);
                        int page = (this.listQueueButton.size()-1)/7 +1;
                        if(page!=this.pageQueue) switchPageQueue(page);
                    }
                }
                this.world.setNeedReleaseMouse(true);
                break;
            case "removeQueue":
                if(this.selectedWave!=null && this.selectedQueueInstance!=null){
                    int j = this.listWaveButton.indexOf(this.selectedWave);
                    int i = this.listQueueButton.indexOf(this.selectedQueueInstance);
                    QueueMonster qMonster = this.listQueue.get(j).get(i);
                    if(i!=-1){
                        this.waveBox.removeHUDElement(this.selectedQueueInstance);
                        this.listQueueButton.remove(this.selectedQueueInstance);
                        this.listQueue.get(j).remove(qMonster);
                        if(i!=0) this.setSelectedQueueInstance(this.listQueueButton.get(i-1));
                        else this.setSelectedQueueInstance(null);
                        refreshPositionQueueButton();
                        int page = (i-1)/7 +1;
                        if(page!=this.pageQueue) switchPageQueue(page);
                    }
                }
                this.world.setNeedReleaseMouse(true);
                break;
            case "nextPageQueue":
                this.world.setNeedReleaseMouse(true);
                this.switchPageQueue(this.pageQueue+1);
                break;
            case "previousPageQueue":
                this.world.setNeedReleaseMouse(true);
                this.switchPageQueue(this.pageQueue-1);
                break;
            case "selectQueue":
                if(this.selectedQueueInstance!=from){
                    this.setSelectedQueueInstance((Button) from);
                }
                this.world.setNeedReleaseMouse(true);
                break;
            case "selectMonster":
                this.world.setNeedReleaseMouse(true);
                if(this.selectedMonster!=from){
                    this.setSelectedMonster((Button) from);
                }
                this.world.setNeedReleaseMouse(true);
                break;
            case "addTimeQueue":
                if(this.selectedQueueInstance!=null){
                    int j = this.listWaveButton.indexOf(this.selectedWave);
                    int i = this.listQueueButton.indexOf(this.selectedQueueInstance);
                    double currenttime = this.listQueue.get(j).get(i).getTimeBeforeSpawning();
                    double time = currenttime + (currenttime<1.0 ? 0.1 : 0.5);
                    if(!(time>60.0)){
                        this.listQueue.get(j).get(i).setTimeBeforeSpawning(time);
                        ((ButtonQueueEditor)this.selectedQueueInstance).setTime(time);
                        this.timeQueueMonster.setText((time+"").length()>4 ? (time+"").substring(0,3) : (time+""));
                    }
                }
                this.world.setNeedReleaseMouse(true);
                break;
            case "removeTimeQueue":
                if(this.selectedQueueInstance!=null){
                    int j = this.listWaveButton.indexOf(this.selectedWave);
                    int i = this.listQueueButton.indexOf(this.selectedQueueInstance);
                    double currenttime = this.listQueue.get(j).get(i).getTimeBeforeSpawning();
                    double time = currenttime - (currenttime<=1.0 ? 0.1 : 0.5);
                    if(!(time<0.1)){
                        this.listQueue.get(j).get(i).setTimeBeforeSpawning(time);
                        ((ButtonQueueEditor)this.selectedQueueInstance).setTime(time);
                        this.timeQueueMonster.setText((time+"").length()>4 ? (time+"").substring(0,3) : (time+""));
                    }
                }
                this.world.setNeedReleaseMouse(true);
                break;
            case "saveMap":
                if(this.world.getPaths().size()<2) return;
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Specify a file to save");
                FileNameExtensionFilter filter = new FileNameExtensionFilter("Tower Defense Level", "tdl", "tdl");
                fileChooser.setFileFilter(filter);
                int userSelection = fileChooser.showSaveDialog(StdDraw.getFrame());
                if (userSelection == JFileChooser.APPROVE_OPTION) {
                    File fileChoosen = fileChooser.getSelectedFile();
                    File fileToSave;
                    if(fileChoosen.getName().length()>4 && fileChoosen.getName().substring(fileChoosen.getName().length()-4).equals(".tdl")){
                        fileToSave = fileChoosen;
                    } else {
                        fileToSave = new File(fileChoosen.getPath()+".tdl");
                    }
                    fileToSave.createNewFile();
                    FileWriter myWriter = new FileWriter(fileToSave.getAbsoluteFile());
                    myWriter.write("MUSIC_PATH=music\\glorious.wav\n");
                    myWriter.write("LIFE="+this.lifeInitial+"\n");
                    myWriter.write("MONEY="+this.moneyInitial+"\n");
                    myWriter.write(this.getPathTextSave()+"\n");
                    myWriter.write(this.getPaddingTextSave()+"\n");
                    myWriter.write("WATER="+(this.comboBoxWater.getSelectedChoice().equals("ON") ? 1 : 0)+"\n");
                    myWriter.write(this.getWaveTextSave()+"\n");
                    myWriter.write("PLANTS="+(this.comboBoxPlants.getSelectedChoice().equals("None") ? 0 : (this.comboBoxPlants.getSelectedChoice().equals("Plants") ? 1 : 2)));
                    myWriter.close();
                }
                this.world.setNeedReleaseMouse(true);
                break;
        }
    }

    /**
     * Récupère le chemin du terrain sous forme de chaine de caractères
     * @return le chemin du terrain sous forme de chaine de caractères
     */
    public String getPathTextSave(){
        String text = "PATH=";
        Position previous = null;
        int dir = 0;
        int count = 0;
        String[] dirText = new String[]{"null","U","D","L","R"};
        for(Position p : this.world.getPaths()){
            if(previous != null){
                int newDir = getDirText(previous, p);
                if(dir == newDir){
                    count ++;
                } else {
                    if(dir!=0) text+=count+dirText[dir];
                    dir = newDir;
                    count = 1;
                }
            }
            previous = p;
        }
        return text+count+dirText[dir];
    }

    /**
     * Récupère l'index de la direction entre 2 positions
     * @param previous 1ère position
     * @param p position suivante
     * @return l'index de la direction entre 2 positions
     */
    public int getDirText(Position previous, Position p){
        if(previous.getY()<p.getY()) return 1;
        else if(previous.getY()>p.getY()) return 2;
        else if(previous.getX()>p.getX()) return 3;
        else return 4;
    }

    /**
     * Récupère les listes de vagues sous forme de chaine de caractères
     * @return les listes de vagues sous forme de chaine de caractères
     */
    public String getWaveTextSave(){
        String text = "WAVES=";
        boolean first = true;
        for(List<QueueMonster> l : this.listQueue){
            if(l.size()>0){
                if(first)first = false;
                else text+=";";
                text+="[";
                for(int i = 0; i<l.size();i++){
                    text+="("+l.get(i).getMonster()+","+l.get(i).getTimeBeforeSpawning()+")"+(i!=l.size()-1 ? "_" : "");
                }
                text+="]";
            }
        }
        if(text.equals("WAVES=")) return "WAVES=[(1,1)]";
        return text;
    }

    /**
     * Récupère le padding du terrain sous forme de chaine de caractères
     * @return le padding du terrain sous forme de chaine de caractères
     */
    public String getPaddingTextSave(){
        String text = "PADDING=";
        int maxX = 0;
        int minX = this.world.getNbSquareX()-1;
        int maxY = 0;
        int minY = this.world.getNbSquareY()-1;
        for(Position p : this.world.getPaths()){
            if(p.getX()>maxX) maxX = (int)p.getX();
            if(p.getX()<minX) minX = (int)p.getX();
            if(p.getY()>maxY) maxY = (int)p.getY();
            if(p.getY()<minY) minY = (int)p.getY();
        }
        text+=(this.world.getNbSquareY()-maxY-1)+",";
        text+=minY+",";
        text+=minX+",";
        text+=(this.world.getNbSquareX()-maxX-1);
        return text;
    }
}
