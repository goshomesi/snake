import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;
import javax.swing.JFrame;

public class SnakeGame extends JFrame implements KeyListener {

  //podgotvq razmerite na ejrana i kvadratchetata
  private int WIDTH = 500;
  private int HEIGHT = 500;
  private int GRID_SIZE = 20;
  private int GRID_WIDTH = WIDTH / GRID_SIZE;
  private int GRID_HEIGHT = HEIGHT / GRID_SIZE;
  private boolean gameOver = false;
  private boolean gameWon = false;
  private int[] snakeX = new int[GRID_WIDTH * GRID_HEIGHT];
  private int[] snakeY = new int[GRID_WIDTH * GRID_HEIGHT];
  private int snakeLength = 5;
  private int direction = 3; // 0 = up, 1 = right, 2 = down, 3 = left
  // ramdom generator
  private Random random = new Random();

  public SnakeGame() {
    setSize(WIDTH, HEIGHT);
    setTitle("Snake Game");
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setResizable(false);
    setVisible(true);
    addKeyListener(this);
    snakeX[0] = GRID_WIDTH / 2;
    snakeY[0] = GRID_HEIGHT / 2;
    snakeX[1] = GRID_WIDTH / 2 - 1;
    snakeY[1] = GRID_HEIGHT / 2;
    snakeX[2] = GRID_WIDTH / 2 - 2;
    snakeY[2] = GRID_HEIGHT / 2;
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
    g.setColor(new Color(0, 0, 128));
    for (int i = 0; i < snakeLength; i++) {
      g.fillRect(
        snakeX[i] * GRID_SIZE,
        snakeY[i] * GRID_SIZE,
        GRID_SIZE,
        GRID_SIZE
      );
    }
  }

  public void move() {
    // dviji z,iqta
    for (int i = snakeLength - 1; i > 0; i--) {
      snakeX[i] = snakeX[i - 1];
      snakeY[i] = snakeY[i - 1];
    }
    // dviji glavata na zmiqta
    if (direction == 0) {
      snakeY[0]--;
    } else if (direction == 1) {
      snakeX[0]++;
    } else if (direction == 2) {
      snakeY[0]++;
    } else if (direction == 3) {
      snakeX[0]--;
    }
    // gleda dali zmiqta se e udarila
    if (
      snakeX[0] < 0 ||
      snakeX[0] >= GRID_WIDTH ||
      snakeY[0] < 0 ||
      snakeY[0] >= GRID_HEIGHT
    ) {
      gameOver = true;
    }
    // gleda dali e spechelena igrata
    if (snakeLength == GRID_WIDTH * GRID_HEIGHT) {
      gameWon = true;
    }
  }

  public void keyPressed(KeyEvent e) {
    // Change the direction of the snake based on the key pressed
    if (e.getKeyCode() == KeyEvent.VK_UP && direction != 2) {
      direction = 0;
    } else if (e.getKeyCode() == KeyEvent.VK_RIGHT && direction != 3) {
      direction = 1;
    } else if (e.getKeyCode() == KeyEvent.VK_DOWN && direction != 0) {
      direction = 2;
    } else if (e.getKeyCode() == KeyEvent.VK_LEFT && direction != 1) {
      direction = 3;
    }
  }

  public void keyReleased(KeyEvent e) {}

  public void keyTyped(KeyEvent e) {}

  public static void main(String[] args) {
    SnakeGame game = new SnakeGame();
    while (!game.gameOver && !game.gameWon) {
      game.move();
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
