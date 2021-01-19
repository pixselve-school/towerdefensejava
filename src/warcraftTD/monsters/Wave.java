package warcraftTD.monsters;

import warcraftTD.WorldGame;
import warcraftTD.utils.QueueInstance;

import java.util.LinkedList;

/**
 * Vague de monstre
 */
public class Wave {
  /**
   * La file des monstres à faire apparaitre
   */
  LinkedList<QueueInstance> monsterQueue;
  /**
   * Le temps avant que la vague s'active
   */
  private final double timeBeforeStartingSpawn;
  /**
   * Suivi du temps
   */
  private double timeTracking;

  /**
   * Récupère le temps actuel avant que la vague s'active
   *
   * @return Le temps actuel avant que la vague s'active
   */
  public double getCurrentTimeBeforeStartingSpawn() {
    return this.timeBeforeStartingSpawn - this.timeTracking;
  }

  /**
   * Récupère le temps global avant que la vague s'active
   *
   * @return Le temps global avant que la vague s'active
   */
  public double getTimeBeforeStartingSpawn() {
    return this.timeBeforeStartingSpawn;
  }

  /**
   * Ajoute du temps au suivi du temps
   *
   * @param time Le temps qui s'est écoulé
   */
  public void subtractTimeBeforeStartingSpawn(double time) {
    this.timeTracking += time;
  }


  /**
   * Création d'une nouvelle vague
   *
   * @param startDelay Le temps avant que la vague s'active
   */
  public Wave(double startDelay) {
    this.monsterQueue = new LinkedList<>();
    this.timeBeforeStartingSpawn = startDelay;
    this.timeTracking = 0.0;
  }

  /**
   * Création d'une nouvelle vague avec 5 secondes avant que la vague s'active
   */
  public Wave() {
    this.monsterQueue = new LinkedList<>();
    this.timeBeforeStartingSpawn = 5;
    this.timeTracking = 0.0;
  }

  /**
   * Récupère le nombre de monstre en attente
   *
   * @return le nombre de monstre en attente
   */
  public int monsterAmount() {
    return this.monsterQueue.size();
  }

  /**
   * Vérifie si tous les monstres sont apparus
   *
   * @return true si tous les monstres sont apparus
   */
  public boolean finishedSpawning() {
    return this.monsterQueue.size() <= 0;
  }

  /**
   * Ajoute un monstre à la file d'attente
   *
   * @param monster                Le monstre
   * @param timeLeftBeforeSpawning Le temps avant qu'il apparaitra après le dernier monstre apparu
   */
  public void addMonster(Monster monster, double timeLeftBeforeSpawning) {
    this.monsterQueue.addLast(new QueueInstance(monster, timeLeftBeforeSpawning));
  }

  /**
   * Fait apparaitre (si les conditions sont bonnes) un monstre dans le monde
   *
   * @param world     Le monde
   * @param deltaTime Le delta temps du jeu
   */
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

