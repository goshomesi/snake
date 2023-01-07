import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JFrame;

public class SnakeGame extends JFrame {

  //podgotvq razmerite na ejrana i kvadratchetata
  private int WIDTH = 500;
  private int HEIGHT = 500;
  private int GRID_SIZE = 20;
  private int GRID_WIDTH = WIDTH / GRID_SIZE;
  private int GRID_HEIGHT = HEIGHT / GRID_SIZE;
  private boolean gameOver = false;
  private boolean gameWon = false;

  public SnakeGame() {
    setSize(WIDTH, HEIGHT);
    setTitle("Snake Game");
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setResizable(false);
    setVisible(true);
  }

  public void paint(Graphics g) {
    // Draw the background
    for (int i = 0; i < GRID_WIDTH; i++) {
      for (int j = 0; j < GRID_HEIGHT; j++) {
        if ((i + j) % 2 == 0) {
          g.setColor(new Color(0, 128, 0));
        } else {
          g.setColor(new Color(0, 200, 0));
        }
        g.fillRect(i * GRID_SIZE, j * GRID_SIZE, GRID_SIZE, GRID_SIZE);
      }
    }
  }

  public static void main(String[] args) {
    SnakeGame game = new SnakeGame();
    while (!game.gameOver && !game.gameWon) {
      game.repaint();
      try {
        //fps
        Thread.sleep(200);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }
}
