public class Food {

  private int x, y;

  public Food(int x, int y) {
    this.x = x;
    this.y = y;
  }

  public int getX() {
    return x;
  }

  public int getY() {
    return y;
  }

  public void setX(int x) {
    this.x = x;
  }

  public void setY(int y) {
    this.y = y;
  }

  public Food() {
    this(0, 0);
  }

  public Food(Food food) {
    this(food.getX(), food.getY());
  }
}
