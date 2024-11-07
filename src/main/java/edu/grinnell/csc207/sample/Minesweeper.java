package edu.grinnell.csc207.sample;

import edu.grinnell.csc207.util.MatrixV0;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;

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

  // +--------+---------------------------------------------------
  // | Fields |
  // +--------+
  private MatrixV0<Integer> grid;
  private MatrixV0<Boolean> reveled;
  private MatrixV0<Boolean> flagged;
  private int rows;
  private int column;
  private int minesPlaced;
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
        this.minesPlaced = EASY_MODE * EASY_MODE / 8;
        break;
      case "Medium":
        this.column = MEDIUM_MODE;
        this.rows = MEDIUM_MODE;
        this.minesPlaced = MEDIUM_MODE * MEDIUM_MODE / 6;
        break;
      case "Hard":
        this.column = HARD_MODE;
        this.rows = HARD_MODE;
        this.minesPlaced = HARD_MODE * HARD_MODE / 4;
        break;
      default:
        System.out.println("Invalid input, setting to a default easy mode");
        this.column = EASY_MODE;
        this.rows = EASY_MODE;
        this.minesPlaced = EASY_MODE * EASY_MODE / 8;
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

        * -E easy - creates a 10 by 10 board
        * -M medium - creates a 40 by 40 board
        * -H hard - creates a 100 by 100 board

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
  public void placeMines() {} // placeMines()

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
