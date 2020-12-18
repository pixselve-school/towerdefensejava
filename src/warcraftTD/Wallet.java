package warcraftTD;

public class Wallet {
    private int money;

    public int getMoney() {
        return this.money;
    }

    public void addMoney(int amount){
        this.money += amount;
    }

    public boolean canPay(int amount){
        return money>=amount;
    }

    public boolean pay(int amount){
        if(!canPay(amount)) return false;
        money -= amount;
        return true;
    }
}
