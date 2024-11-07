package edu.grinnell.csc207.sample;

import edu.grinnell.csc207.util.MatrixV0;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Random;

public class Minesweeper {
  // +-----------+---------------------------------------------------
  // | Constants |
  // +-----------+

  /** The default width and height of the easy mode game. */
  static final int EASY_MODE = 8;

  /** The default width and height of the medium mode game. */
  static final int MEDIUM_MODE = 16;

  /** The default width and height of the hard mode game. */
  static final int HARD_MODE = 24;

  /** Code for a bomb */
  static final int BOMB = -1;

  /** Code for an empty cell */
  static final int EMPTY_CELL = 0;

  // +--------+---------------------------------------------------
  // | Fields |
  // +--------+
  private MatrixV0<Integer> grid;
  private MatrixV0<Boolean> reveled;
  private MatrixV0<Boolean> flagged;
  private int rows;
  private int column;
  private int totalMines;
  private String mode;

  // +-------------+---------------------------------------------------
  // | Constructor |
  // +-------------+
  /**
   * Based on the mode passed (Easy, Medium, or Hard), it initializes the grid dimensions (rows and
   * cols) and calculates the number of mines. It then initializes the grid and revealed arrays, and
   * places the mines using placeMines().
   *
   * @param mode string
   */
  public Minesweeper(String mode) {
    this.mode = mode;
    switch (mode) {
      case "Easy":
        this.column = EASY_MODE;
        this.rows = EASY_MODE;
        this.totalMines = EASY_MODE * EASY_MODE / 8;
        break;
      case "Medium":
        this.column = MEDIUM_MODE;
        this.rows = MEDIUM_MODE;
        this.totalMines = MEDIUM_MODE * MEDIUM_MODE / 6;
        break;
      case "Hard":
        this.column = HARD_MODE;
        this.rows = HARD_MODE;
        this.totalMines = HARD_MODE * HARD_MODE / 4;
        break;
      default:
        System.out.println("Invalid input, setting to a default easy mode");
        this.column = EASY_MODE;
        this.rows = EASY_MODE;
        this.totalMines = EASY_MODE * EASY_MODE / 8;
        break;
    }

    this.grid = new MatrixV0<>(this.column, this.rows);
    this.reveled = new MatrixV0<>(this.column, this.rows, false);
    this.flagged = new MatrixV0<>(this.column, this.rows, false);

    placeMines();
    calculateAdjacentMines();
  }

  // +----------------+----------------------------------------------
  // | Helper methods |
  // +----------------+
  /**
   * Print the insturctions.
   *
   * @param pen The printwriter used to print the instructions.
   */
  public static void printInstructions(PrintWriter pen) {
    pen.println(
        """
        Welcome to the Minesweeper.

        Command-line arguments:

        Selecting difficulty of the game:

        * Easy - creates a 8 by 8 board
        * Medium - creates a 16 by 16 board
        * Hard - creates a 24 by 24 board

        Your goal is to flag all the possible mines on the field.

        * flag key*: puts a flag on the field(possible mine)
        * reset: resets the game
        * uncover key*: uncvovers the hidden feild

        """);
  } // printInstructions(PrintWriter)

  /**
   * Randomly places mines on the grid by selecting random coordinates and ensuring that no two
   * mines are placed at the same position.
   */
  public void placeMines() {
    Random rand = new Random();
    int minesPlaced = 0;

    while (minesPlaced < this.totalMines) {
      int x = rand.nextInt(this.column);
      int y = rand.nextInt(this.rows);
      if (this.grid.get(x, y) != BOMB) {
        this.grid.set(x, y, BOMB);
        ++minesPlaced;
      } // if(the position in the grid doesn't equal -1)
    } // while(the cound of the placed mines doesn't equal to the count of the total Mines allowed)
  } // placeMines()

  /**
   * For each non-mine cell, it calculates how many of the adjacent cells contain mines and stores
   * this count in the grid.
   */
  public void calculateAdjacentMines() {} // calculateAdjacentMines()

  /**
   * When a cell is clicked, the game reveals the cell and, if necessary, recursively reveals
   * adjacent empty cells (cells with 0 adjacent mines).
   */
  public void revelingCell() {} // revelingCell()

  /** This allows the player to flag a cell, potentially indicating that it contains a mine */
  public void flaggedCell() {} // flaggedCell()

  /** The game checks if all non-mine cells are revealed, in which case the player wins. */
  public void checkWin() {} // checkWin()

  /** Resets the game */
  public void resetGame() {} // resetGame()

  // +------+--------------------------------------------------------
  // | Main |
  // +------+

  /**
   * Run the game.
   *
   * @param args Command-line arguments.
   */
  public static void main(String[] args) {
    PrintWriter pen = new PrintWriter(System.out, true);
    BufferedReader eyes = new BufferedReader(new InputStreamReader(System.in));
  }
}
