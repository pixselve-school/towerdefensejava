package warcraftTD;

import java.awt.*;

public class Wallet {
  private int money;
  private World world;

  public Wallet(World world){
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
    this.world.HUD.addNotifText(new Position(0.93,0.65),  new Font("Arial", Font.BOLD, 30),0.25, "- "+amount+" $");
    return true;
  }
}
