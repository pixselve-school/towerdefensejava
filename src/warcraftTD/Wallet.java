package warcraftTD;

public class Wallet {
  private int money;

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
    return true;
  }
}
