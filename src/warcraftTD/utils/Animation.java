package warcraftTD.utils;

import warcraftTD.libs.StdDraw;

import javax.swing.*;
import java.awt.*;


public class Animation {
  private final String[] imagesPaths;
  private double scaledHeight;
  private final double scaledWidth;

  private Position position;
  private final int fps;
  private double timeTracking;
  private int currentFrame;
  private final boolean isLoop;

  public void setCallback(MonsterDieCallback callback) {
    this.callback = callback;
  }

  private MonsterDieCallback callback;

  public Animation(String[] imagesPaths, double scaledHeight, double scaledWidth, Position position, int fps, boolean isLoop) {
    this.imagesPaths = imagesPaths;
    this.scaledHeight = scaledHeight;
    this.scaledWidth = scaledWidth;
    this.position = position;
    this.fps = fps;
    this.timeTracking = 0;
    this.currentFrame = 0;
    this.isLoop = isLoop;
    this.callback = null;
  }

  public Animation(String[] imagesPaths, double scaledHeight, double scaledWidth, Position position, int fps, MonsterDieCallback callback) {
    this.imagesPaths = imagesPaths;
    this.scaledHeight = scaledHeight;
    this.scaledWidth = scaledWidth;
    this.position = position;
    this.fps = fps;
    this.timeTracking = 0;
    this.currentFrame = 0;
    this.isLoop = false;
    this.callback = callback;
  }

  public void draw(double deltaTime) {
    this.timeTracking += deltaTime;
    if (this.timeTracking >= 1.0 / this.fps) {
      this.currentFrame++;
      this.timeTracking = 0;
      if (this.currentFrame >= this.imagesPaths.length) {
        if (this.isLoop) {
          this.currentFrame = 0;
        } else {
          assert this.callback != null;
          this.callback.die();
          return;
        }
      }
    }
//    StdDraw.picture(this.position.getX(), this.position.getY(), this.imagesPaths[this.currentFrame], this.scaledWidth, this.scaledHeight);
    if(this.position!=null) StdDraw.pictureHeight(this.position.getX(), this.position.getY(), this.imagesPaths[this.currentFrame], this.scaledHeight);
  }

  public void draw(double deltaTime, double shiftX, double shiftY) {
    this.timeTracking += deltaTime;
    if (this.timeTracking >= 1.0 / this.fps) {
      this.currentFrame++;
      this.timeTracking = 0;
      if (this.currentFrame >= this.imagesPaths.length) {
        if (this.isLoop) {
          this.currentFrame = 0;
        } else {
          assert this.callback != null;
          this.callback.die();
          return;
        }
      }
    }
    if(this.position!=null) StdDraw.pictureHeight(this.position.getX() - shiftX, this.position.getY() - shiftY, this.imagesPaths[this.currentFrame], this.scaledHeight);
  }

  public void setPosition(Position position) {
    this.position = position;
  }

  public String getCurrentFrame() {
    return this.imagesPaths[this.currentFrame];
  }

  public void setScaledHeight(double scaledHeight) {
    this.scaledHeight = scaledHeight;
  }
}
