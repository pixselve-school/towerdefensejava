package warcraftTD.utils;

import warcraftTD.monsters.Monster;

public class QueueInstance {
  private final Monster monster;
  private double timeLeftBeforeSpawning;

  public Monster getMonster() {
    return this.monster;
  }

  public double getTimeLeftBeforeSpawning() {
    return this.timeLeftBeforeSpawning;
  }

  public void decrementTime(double value) {
    this.timeLeftBeforeSpawning -= value;
  }

  public QueueInstance(Monster monster, double timeLeftBeforeSpawning) {
    this.monster = monster;
    this.timeLeftBeforeSpawning = timeLeftBeforeSpawning;
  }
}
