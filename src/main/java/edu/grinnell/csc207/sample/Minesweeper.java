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
  private MatrixV0<Boolean> revealed;
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

    this.grid = new MatrixV0<>(this.column, this.rows, 0);
    this.revealed = new MatrixV0<>(this.column, this.rows, false);
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
        Y representing the row number and X a column

        * flag x y: puts a flag on the field(possible mine)
        * unflag x y: puts a flag on the field(possible mine)
        * reset: resets the game
        * reveal x y: uncvovers the hidden feild

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
      if (this.grid.get(y, x) != BOMB) {
        this.grid.set(y, x, BOMB);
        ++minesPlaced;
      } // if(the position in the grid doesn't equal -1)
    } // while(the cound of the placed mines doesn't equal to the count of the total Mines allowed)
  } // placeMines()

  /**
   * For each non-mine cell, it calculates how many of the adjacent cells contain mines and stores
   * this count in the grid.
   */
  public void calculateAdjacentMines() {
    for (int i = 0; i < this.rows; i++) {
      for (int j = 0; j < this.column; j++) {
        if (this.grid.get(i, j) == BOMB) {
          continue; // Skip mines
        }
        int mineCount = 0;
        // Check all adjacent cells (8 directions)
        for (int m = -1; m <= 1; m++) {
          for (int n = -1; n <= 1; n++) {
            int mRow = i + m;
            int nCol = j + n;
            if (mRow >= 0
                && nCol >= 0
                && mRow < this.rows
                && nCol < this.column
                && this.grid.get(mRow, nCol) == BOMB) {
              mineCount++;
            } //
          } //
        } //
        this.grid.set(i, j, mineCount); // Store the count of adjacent mines
      } //
    } //
  } // calculateAdjacentMines()

  /**
   * When a cell is clicked, the game reveals the cell and, if necessary, recursively reveals
   * adjacent empty cells (cells with 0 adjacent mines).
   *
   * @param y int(row)
   * @param x int(col)
   * @param pen Printer Object
   */
  public void revealingCell(int y, int x, PrintWriter pen) {
    if (this.revealed.get(y, x)) {
      return; // cant revel already reveled cell
    } //

    this.revealed.set(y, x, true);

    // if revealed cell is a BOMB, restart the game
    if (this.grid.get(y, x) == BOMB) {
      pen.println("Looser! You hit the mine!");
      resetGame();
      return;
    } //
    // If the cell is empty (0 adjacent mines), recursively reveal adjacent cells
    if (this.grid.get(y, x) == EMPTY_CELL) {
      for (int i = -1; i <= 1; i++) {
        for (int j = -1; j <= 1; j++) {
          revealingCell(y + i, x + j, pen);
        } //
      } //
    } //
  } // revealingCell()

  /**
   * This allows the player to flag a cell, potentially indicating that it contains a mine
   *
   * @param x int(col)
   * @param y int(row)
   */
  public void flagCell(int y, int x) {
    if (this.revealed.get(y, x)) {
      return; // can't flag already uncovered cell
    } //
    this.flagged.set(y, x, true);
  } // flagCell(int, int)

  /**
   * This allows the player to flag a cell, potentially indicating that it contains a mine
   *
   * @param x int(col)
   * @param y int(row)
   */
  public void unFlagCell(int y, int x) {
    if (!this.flagged.get(y, x) || this.revealed.get(y, x)) {
      return; // can't unflag already uncovered cell or cell that is not flagged
    }
    this.flagged.set(y, x, false);
  } // unFlagCell(int, int)

  /** The game checks if all non-mine cells are revealed, in which case the player wins. */
  public boolean checkWin() {
    for (int i = 0; i < this.rows; i++) {
      for (int j = 0; j < this.column; j++) {
        if (this.grid.get(i, j) != BOMB && !this.revealed.get(i, j)) {
          return false;
        } // if found unreveled bomb
      } // inner for loop
    } // outer for loop
    return true;
  } // checkWin()

  /** Resets the game */
  public void resetGame() {
    this.grid = new MatrixV0<>(this.column, this.rows, 0);
    this.revealed = new MatrixV0<>(this.column, this.rows, false);
    this.flagged = new MatrixV0<>(this.column, this.rows, false);
    placeMines();
    calculateAdjacentMines();
  } // resetGame()

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
