package warcraftTD.monsters;

public class Effect {
  private double healthMultiplier;
  private double healthAdd;
  private double speedMultiplier;
  private double duration;

  public double getTimeTracking() {
    return this.timeTracking;
  }

  public void resetTimeTracking() {
    this.timeTracking = 0.0;
  }

  public void addTimeToTimeTracking(double value) {
    this.timeTracking += value;
  }

  private double timeTracking;

  public String toString() {
    return "Effect{" +
        "healthMultiplier=" + this.healthMultiplier +
        ", healthAdd=" + this.healthAdd +
        ", speedMultiplier=" + this.speedMultiplier +
        ", duration=" + this.duration +
        '}';
  }

  public Effect(double duration, double healthMultiplier, double healthAdd, double speedMultiplier) {
    this.healthMultiplier = healthMultiplier;
    this.healthAdd = healthAdd;
    this.speedMultiplier = speedMultiplier;
    this.timeTracking = 0.0;
    this.duration = duration;
  }

  public double getHealthMultiplier() {
    return this.healthMultiplier;
  }

  public void setHealthMultiplier(double healthMultiplier) {
    this.healthMultiplier = healthMultiplier;
  }

  public double getHealthAdd() {
    return this.healthAdd;
  }

  public void setHealthAdd(double healthAdd) {
    this.healthAdd = healthAdd;
  }

  public double getSpeedMultiplier() {
    return this.speedMultiplier;
  }

  public void setSpeedMultiplier(double speedMultiplier) {
    this.speedMultiplier = speedMultiplier;
  }


  public double getDuration() {
    return this.duration;
  }

  public void setDuration(double duration) {
    this.duration = duration;
  }

  public void setDurationIfGreater(int duration) {
    this.duration = Math.max(this.duration, duration);
  }
}
