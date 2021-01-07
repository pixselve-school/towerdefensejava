package warcraftTD.utils;

import java.util.Objects;

public class TowerDataStruct {
    public String buttonSprite;
    public String buttonSpriteHover;
    public String buttonAction;

    public int price;
    public Class towerClass;

    public TowerDataStruct(String buttonSprite, String buttonSpriteHover, String buttonAction, int price, Class towerClass) {
        this.buttonSprite = buttonSprite;
        this.buttonSpriteHover = buttonSpriteHover;
        this.buttonAction = buttonAction;
        this.price = price;
        this.towerClass = towerClass;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        TowerDataStruct that = (TowerDataStruct) o;
        return this.towerClass.equals(that.towerClass);
    }
}
