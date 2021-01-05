package warcraftTD.utils;

public class Padding {
  private final int top;
  private final int bottom;
  private final int left;
  private final int right;

  public Padding(int top, int bottom, int left, int right) {
    this.top = top;
    this.bottom = bottom;
    this.left = left;
    this.right = right;
  }

  public int getTop() {
    return this.top;
  }

  public int getBottom() {
    return this.bottom;
  }

  public int getLeft() {
    return this.left;
  }

  public int getRight() {
    return this.right;
  }

  public String toString() {
    return "Padding{" +
        "top=" + this.top +
        ", bottom=" + this.bottom +
        ", left=" + this.left +
        ", right=" + this.right +
        '}';
  }
}
