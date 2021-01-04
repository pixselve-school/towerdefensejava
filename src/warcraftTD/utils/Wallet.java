package warcraftTD.utils;

import warcraftTD.World;

import java.awt.*;

public class Wallet {
  private int money;
  private final World world;

  public Wallet(World world) {
    this.world = world;
  }

  public int getMoney() {
    return this.money;
  }

  public void addMoney(int amount) {
    this.money += amount;
  }

  public boolean canPay(int amount) {
    return this.money >= amount;
  }

  public boolean pay(int amount) {
    if (!this.canPay(amount)) return false;
    this.money -= amount;
    this.world.getHUD().addNotifText(new Position(0.93, 0.65), new Font("Arial", Font.BOLD, 30), 0.25, "- " + amount + " $", new Color(240, 255, 11));
    return true;
  }
}