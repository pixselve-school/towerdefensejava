package warcraftTD.monsters;

/**
 * Un effet
 */
public class Effect {
  /**
   * Le montant auquel la santé sera multipliée
   */
  private double healthMultiplier;
  /**
   * Le montant de la santé qui sera ajouté
   */
  private double healthAdd;
  /**
   * Le montant auquel la vitesse sera multipliée
   */
  private double speedMultiplier;
  /**
   * La durée de l'effet
   */
  private double duration;
  /**
   * Le suivi du temps de l'effet
   */
  private double timeTracking;

  /**
   * Créer un nouvel effet
   *
   * @param duration         La durée de l'effet
   * @param healthMultiplier Le montant auquel la santé sera multipliée
   * @param healthAdd        Le montant de la santé qui sera ajouté
   * @param speedMultiplier  Le montant auquel la vitesse sera multipliée
   */
  public Effect(double duration, double healthMultiplier, double healthAdd, double speedMultiplier) {
    this.healthMultiplier = healthMultiplier;
    this.healthAdd = healthAdd;
    this.speedMultiplier = speedMultiplier;
    this.timeTracking = 0.0;
    this.duration = duration;
  }

  /**
   * Récupère le suivi du temps de l'effet
   *
   * @return Le suivi du temps de l'effet
   */
  public double getTimeTracking() {
    return this.timeTracking;
  }

  /**
   * Réinitialise le suivi du temps de l'effet
   */
  public void resetTimeTracking() {
    this.timeTracking = 0.0;
  }

  /**
   * Ajoute du temps au suivi du temps de l'effet
   *
   * @param value le temps à ajouter
   */
  public void addTimeToTimeTracking(double value) {
    this.timeTracking += value;
  }

  /**
   * Récupère l'effet sous forme d'une chaine de caractère
   *
   * @return l'effet sous forme d'une chaine de caractère
   */
  public String toString() {
    return "Effect{" +
        "healthMultiplier=" + this.healthMultiplier +
        ", healthAdd=" + this.healthAdd +
        ", speedMultiplier=" + this.speedMultiplier +
        ", duration=" + this.duration +
        '}';
  }

  /**
   * Récupère le montant auquel la santé sera multipliée
   *
   * @return Le montant auquel la santé sera multipliée
   */
  public double getHealthMultiplier() {
    return this.healthMultiplier;
  }

  /**
   * Change le montant auquel la santé sera multipliée
   *
   * @param healthMultiplier Le nouveau montant auquel la santé sera multipliée
   */
  public void setHealthMultiplier(double healthMultiplier) {
    this.healthMultiplier = healthMultiplier;
  }

  /**
   * Récupère le montant de la santé qui sera ajouté
   *
   * @return Le montant de la santé qui sera ajouté
   */
  public double getHealthAdd() {
    return this.healthAdd;
  }

  /**
   * Change le montant de la santé qui sera ajouté
   *
   * @param healthAdd Le nouveau montant de la santé qui sera ajouté
   */
  public void setHealthAdd(double healthAdd) {
    this.healthAdd = healthAdd;
  }

  /**
   * Récupère le montant auquel la vitesse sera multipliée
   *
   * @return Le montant auquel la vitesse sera multipliée
   */
  public double getSpeedMultiplier() {
    return this.speedMultiplier;
  }

  /**
   * Change le montant auquel la vitesse sera multipliée
   *
   * @param speedMultiplier Le nouveau montant auquel la vitesse sera multipliée
   */
  public void setSpeedMultiplier(double speedMultiplier) {
    this.speedMultiplier = speedMultiplier;
  }

  /**
   * Récupère la durée de l'effet
   *
   * @return La durée de l'effet
   */
  public double getDuration() {
    return this.duration;
  }

  /**
   * Change la durée de l'effet
   *
   * @param duration La nouvelle durée de l'effet
   */
  public void setDuration(double duration) {
    this.duration = duration;
  }

  /**
   * Change la durée de l'effet si la nouvelle valeur est supérieur à l'ancienne
   *
   * @param duration La nouvelle durée de l'effet
   */
  public void setDurationIfGreater(int duration) {
    this.duration = Math.max(this.duration, duration);
  }
}
