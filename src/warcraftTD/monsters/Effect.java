package warcraftTD.monsters;

/**
 * The Effect class
 */
public class Effect {
  /**
   * The amount the health will be multiplied to
   */
  private double healthMultiplier;
  /**
   * The amount the health that will be added
   */
  private double healthAdd;
  /**
   * The amount the speed will be multiplied to
   */
  private double speedMultiplier;
  /**
   * The effect duration
   */
  private double duration;
  /**
   * The time tracking if the effect
   */
  private double timeTracking;

  /**
   * Create a new effect
   *
   * @param duration         The effect duration
   * @param healthMultiplier The amount the health will be multiplied to
   * @param healthAdd        The amount the health that will be added
   * @param speedMultiplier  The amount the speed will be multiplied to
   */
  public Effect(double duration, double healthMultiplier, double healthAdd, double speedMultiplier) {
    this.healthMultiplier = healthMultiplier;
    this.healthAdd = healthAdd;
    this.speedMultiplier = speedMultiplier;
    this.timeTracking = 0.0;
    this.duration = duration;
  }

  /**
   * Get the the time tracking if the effect
   *
   * @return The time tracking if the effect
   */
  public double getTimeTracking() {
    return this.timeTracking;
  }

  /**
   * Reset the the time tracking if the effect
   */
  public void resetTimeTracking() {
    this.timeTracking = 0.0;
  }

  /**
   * Add time to the the time tracking if the effect
   *
   * @param value The value that will be added
   */
  public void addTimeToTimeTracking(double value) {
    this.timeTracking += value;
  }

  /**
   * Get the Effect as a String
   *
   * @return The Effect as a String
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
   * Get the amount the health will be multiplied to
   *
   * @return The amount the health will be multiplied to
   */
  public double getHealthMultiplier() {
    return this.healthMultiplier;
  }

  /**
   * Set the amount the health will be multiplied to
   *
   * @param healthMultiplier The new amount the health will be multiplied to
   */
  public void setHealthMultiplier(double healthMultiplier) {
    this.healthMultiplier = healthMultiplier;
  }

  /**
   * Get the amount the health that will be added
   *
   * @return The amount the health that will be added
   */
  public double getHealthAdd() {
    return this.healthAdd;
  }

  /**
   * Set the amount the health that will be added
   *
   * @param healthAdd The new amount the health that will be added
   */
  public void setHealthAdd(double healthAdd) {
    this.healthAdd = healthAdd;
  }

  /**
   * Get the amount the speed will be multiplied to
   *
   * @return The amount the speed will be multiplied to
   */
  public double getSpeedMultiplier() {
    return this.speedMultiplier;
  }

  /**
   * Set the amount the speed will be multiplied to
   *
   * @param speedMultiplier The new amount the speed will be multiplied to
   */
  public void setSpeedMultiplier(double speedMultiplier) {
    this.speedMultiplier = speedMultiplier;
  }

  /**
   * Get the effect duration
   *
   * @return The effect duration
   */
  public double getDuration() {
    return this.duration;
  }

  /**
   * Set the effect duration
   *
   * @param duration The new effect duration
   */
  public void setDuration(double duration) {
    this.duration = duration;
  }

  /**
   * Set the effect duration if the new duration is greater than the current one
   *
   * @param duration The new duration
   */
  public void setDurationIfGreater(int duration) {
    this.duration = Math.max(this.duration, duration);
  }
}
