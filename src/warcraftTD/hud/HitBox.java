package warcraftTD.hud;

import warcraftTD.utils.Position;

/**
 * Class représentant une zone (une hitBox)
 */
public class HitBox {
    /** largeur de la HitBox */
    private double width;
    /** hauteur de la HitBox */
    private double height;
    /** valeur minimale horizontale pour être dans la zone */
    private double minX;
    /** valeur minimale verticale pour être dans la zone */
    private double minY;
    /** valeur maximale horizontale pour être dans la zone */
    private double maxX;
    /** valeur maximale verticale pour être dans la zone */
    private double maxY;

    /**
     * Initialise une hitBox
     * @param pos la position de la HitBox
     * @param width la largeur
     * @param height la hauteur
     */
    public HitBox(Position pos, double width, double height){
        this.width = width;
        this.height = height;
        refresh(pos);
    }

    /**
     * Actualise les valeurs de délimitations de la zone en fonction de la position
     * @param pos la position
     */
    public void refresh(Position pos){
        this.minX = pos.getX() - (this.width / 2);
        this.maxX = pos.getX() + (this.width / 2);
        this.minY = pos.getY() - (this.height / 2);
        this.maxY = pos.getY() + (this.height / 2);
    }

    /**
     * Spécifie si la souris se trouve dans la zone de Hit (HitBox)
     * @param mouseX la position horizontale de la souris
     * @param mouseY la position verticale de la souris
     * @return la souris est dans la HitBox
     */
    public Boolean isHit(double mouseX, double mouseY){
        return mouseX > this.minX && mouseX < this.maxX && mouseY > this.minY && mouseY < this.maxY;
    }
}
