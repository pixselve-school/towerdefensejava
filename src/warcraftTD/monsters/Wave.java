package warcraftTD.monsters;

import warcraftTD.World;

import java.util.LinkedList;

public class Wave {
  LinkedList<QueueInstance> monsterQueue;

  public Wave() {
    this.monsterQueue = new LinkedList<>();
  }

  public boolean finishedSpawning() {
    return this.monsterQueue.size() <= 0;
  }

  public void addMonster(Monster monster, double timeLeftBeforeSpawning) {
    this.monsterQueue.addFirst(new QueueInstance(monster, timeLeftBeforeSpawning));
  }

  public void spawn(World world, double deltaTime) {
    if (this.monsterQueue.size() > 0) {
      this.monsterQueue.getFirst().decrementTime(deltaTime);
      if (this.monsterQueue.getFirst().getTimeLeftBeforeSpawning() <= 0) {
        world.addMonster(this.monsterQueue.getFirst().getMonster());
        this.monsterQueue.removeFirst();
      }
    }

  }
}

class QueueInstance {
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