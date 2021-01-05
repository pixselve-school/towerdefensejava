package warcraftTD.monsters;

import warcraftTD.WorldGame;
import warcraftTD.utils.QueueInstance;

import java.util.LinkedList;

public class Wave {
  LinkedList<QueueInstance> monsterQueue;
  private final double timeBeforeStartingSpawn;
  private double timeTracking;

  public double getCurrentTimeBeforeStartingSpawn() {
    return this.timeBeforeStartingSpawn - this.timeTracking;
  }

  public double getTimeBeforeStartingSpawn() {
    return this.timeBeforeStartingSpawn;
  }

  public void subtractTimeBeforeStartingSpawn(double time) {
    this.timeTracking += time;
  }


  public Wave() {
    this.monsterQueue = new LinkedList<>();
    this.timeBeforeStartingSpawn = 20;
    this.timeTracking = 0.0;
  }

  public int monsterAmount() {
    return this.monsterQueue.size();
  }

  public boolean finishedSpawning() {
    return this.monsterQueue.size() <= 0;
  }

  public void addMonster(Monster monster, double timeLeftBeforeSpawning) {
    this.monsterQueue.addFirst(new QueueInstance(monster, timeLeftBeforeSpawning));
  }

  public void spawn(WorldGame world, double deltaTime) {
    if (this.monsterQueue.size() > 0) {
      this.monsterQueue.getFirst().decrementTime(deltaTime);
      if (this.monsterQueue.getFirst().getTimeLeftBeforeSpawning() <= 0) {
        world.addMonster(this.monsterQueue.getFirst().getMonster());
        this.monsterQueue.removeFirst();
      }
    }

  }
}

