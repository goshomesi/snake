import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import java.awt.Font;

public class SnakeGame extends JFrame implements KeyListener {

  public String username = "";

  public String getUsername() {
    return this.username;
  }

  public void setUsername(String _username) {
    String regex = "^\\S+$";
    if (_username != null && _username.matches(regex)) {
      this.username = _username;
    }
  }

  //constants for the game
  private int WIDTH = 400;
  private int HEIGHT = 400;
  private int GRID_SIZE = 20;
  private int GRID_WIDTH = WIDTH / GRID_SIZE;
  private int GRID_HEIGHT = HEIGHT / GRID_SIZE;
  //variables for the game
  private boolean gameOver = false;
  private boolean gameWon = false;
  private int score = 0;
  private int[] snakeX = new int[GRID_WIDTH * GRID_HEIGHT];
  private int[] snakeY = new int[GRID_WIDTH * GRID_HEIGHT];
  private int snakeLength = 5;
  private int foodX;
  private int foodY;
  Food food = new Food(foodX, foodY);
  private int direction = 3; // 0 = up, 1 = right, 2 = down, 3 = left
  // Set up a random number generator
  private Random random = new Random();

  public SnakeGame() {
    // Set up the JFrame
    setSize(WIDTH, HEIGHT);
    setTitle("Snake Game");
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setResizable(false);
    setVisible(true);
    addKeyListener(this);
    // Initialize the snake's starting position
    snakeX[0] = GRID_WIDTH / 2;
    snakeY[0] = GRID_HEIGHT / 2;
    snakeX[1] = GRID_WIDTH / 2 - 1;
    snakeY[1] = GRID_HEIGHT / 2;
    snakeX[2] = GRID_WIDTH / 2 - 2;
    snakeY[2] = GRID_HEIGHT / 2;
    // Generate the first food item
    generateFood();
  }

  public void paint(Graphics g) {
    g.setFont(new Font("Arial", Font.BOLD, 20));
    // Draw the background
    for (int i = 0; i < GRID_WIDTH; i++) {
      for (int j = 0; j < GRID_HEIGHT; j++) {
        if ((i + j) % 2 == 0) {
          g.setColor(new Color(95, 141, 78));
        } else {
          g.setColor(new Color(164, 190, 123));
        }
        g.fillRect(i * GRID_SIZE, j * GRID_SIZE, GRID_SIZE, GRID_SIZE);
      }
    }
    // Draw the snake
    g.setColor(new Color(33, 146, 255));
    for (int i = 0; i < snakeLength; i++) {
      g.fillRect(
        snakeX[i] * GRID_SIZE,
        snakeY[i] * GRID_SIZE,
        GRID_SIZE,
        GRID_SIZE
      );
    }
    // Draw the food
    g.setColor(Color.RED);
    g.fillRect(
      food.getX() * GRID_SIZE,
      food.getY() * GRID_SIZE,
      GRID_SIZE,
      GRID_SIZE
    );
    // Draw the score
    g.setColor(Color.WHITE);
    g.drawString("Score: " + score, 10, 50);
    // Check if the game is over
    //        for (int i = 1; i < snakeLength; i++) {
    //            if (snakeX[0] == snakeX[i] && snakeY[0] == snakeY[i] && snakeY.length > 5) {
    //                g.drawString("Game Over!", WIDTH / 2 - 50, HEIGHT / 2 - 10);
    //                gameOver = true;
    //            }
    //        }
    if (gameOver) {
      g.drawString("Game Over!", WIDTH / 2 - 50, HEIGHT / 2 - 40);
      g.drawString( username + "'s score: " + score, WIDTH / 2 - 50, HEIGHT / 2);
      try {
        BufferedReader reader = new BufferedReader(
          new FileReader("snake_game_results.txt")
        );

        String line;
        String scoreFile = "";
        while ((line = reader.readLine()) != null) {
          scoreFile = line;
        }
        g.drawString("" + scoreFile, WIDTH / 2 - 50, HEIGHT / 2 + 40);

        reader.close();
      } catch (IOException e) {
    
        g.drawString(
          "Previous score: " + score,
          WIDTH / 2 - 50,
          HEIGHT / 2 - 30
        );
      }
    }
    if (gameWon) {
      g.drawString("You Won!", WIDTH / 2 - 40, HEIGHT / 2 - 10);
    }
  }

  public void generateFood() {
    // Generate random coordinates for the food
    foodX = random.nextInt(GRID_WIDTH);
    foodY = random.nextInt(GRID_HEIGHT);
    food.setX(foodX);
    food.setY(foodY);
    // Check if the food is spawning on top of the snake
    for (int i = 0; i < snakeLength; i++) {
      if (snakeX[i] == food.getX() && snakeY[i] == food.getY()) {
        // If it is, try again
        generateFood();
        return;
      }
    }
  }

  public void move() {
    // Move the snake's body
    for (int i = snakeLength - 1; i > 0; i--) {
      snakeX[i] = snakeX[i - 1];
      snakeY[i] = snakeY[i - 1];
    }
    // Move the snake's head
    if (direction == 0) {
      snakeY[0]--;
    } else if (direction == 1) {
      snakeX[0]++;
    } else if (direction == 2) {
      snakeY[0]++;
    } else if (direction == 3) {
      snakeX[0]--;
    }
    // Check if the snake has hit a wall or itself
    if (
      snakeX[0] < 0 ||
      snakeX[0] >= GRID_WIDTH ||
      snakeY[0] < 0 ||
      snakeY[0] >= GRID_HEIGHT
    ) {
      gameOver = true;
    }
    // Check if the snake has eaten the food
    if (snakeX[0] == foodX && snakeY[0] == foodY) {
      snakeLength++;
      score++;
      generateFood();
    }
    // Check if the player has won
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

  public void writeToFile(String filename) {
    try {
      BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
      writer.write("Previous score: " + score);
      writer.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static void main(String[] args) {
    String username = JOptionPane.showInputDialog(null, "Enter your username:");
    SnakeGame game = new SnakeGame();
    game.setUsername(username);
    while (!game.gameOver && !game.gameWon) {
      game.move();
      game.repaint();
      try {
        Thread.sleep(80);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
    game.writeToFile("snake_game_results.txt");
  }
}
