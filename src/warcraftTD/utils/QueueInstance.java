package warcraftTD.utils;

import warcraftTD.monsters.Monster;

/**
 * Instance de file de monstre
 */
public class QueueInstance {
  /**
   * Le monstre
   */
  private final Monster monster;
  /**
   * Le temps avant l'apparition du monstre
   */
  private double timeLeftBeforeSpawning;

  /**
   * Créer une nouvelle instance de file de monstre
   *
   * @param monster                Le monstre
   * @param timeLeftBeforeSpawning Le temps avant l'apparition du monstre
   */
  public QueueInstance(Monster monster, double timeLeftBeforeSpawning) {
    this.monster = monster;
    this.timeLeftBeforeSpawning = timeLeftBeforeSpawning;
  }

  /**
   * Récupère le monstre
   *
   * @return le monstre
   */
  public Monster getMonster() {
    return this.monster;
  }

  /**
   * Récupère le temps avant l'apparition du monstre
   *
   * @return Le temps avant l'apparition du monstre
   */
  public double getTimeLeftBeforeSpawning() {
    return this.timeLeftBeforeSpawning;
  }

  /**
   * Décrémente le temps avant l'apparition du monstre
   *
   * @param value Le temps qui s'est écoulé
   */
  public void decrementTime(double value) {
    this.timeLeftBeforeSpawning -= value;
  }
}
