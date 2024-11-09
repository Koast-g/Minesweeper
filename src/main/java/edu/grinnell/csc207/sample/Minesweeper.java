package edu.grinnell.csc207.sample;

import edu.grinnell.csc207.util.MatrixV0;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Random;

/** This is a one player game Minesweeper. Try to escape the mine field in order to survive. */
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

  /** Code for a bomb. */
  static final int BOMB = -1;

  /** Code for an empty cell. */
  static final int EMPTY_CELL = 0;

  // +--------+---------------------------------------------------
  // | Fields |
  // +--------+
  /**
   * Creates a grid of integers, where -1 is a bomb, 0 is empty cell and positive integer
   * repressents how many bombs are there.
   */
  private MatrixV0<Integer> grid;

  /** Revealed cells. */
  private MatrixV0<Boolean> revealed;

  /** Flagged cells. */
  private MatrixV0<Boolean> flagged;

  /** Number of rows in matrics. */
  private int rows;

  /** Number of columns in matrics. */
  private int column;

  /** Total number of mines on the field. */
  private int totalMines;

  /** Difficulty level. */
  private String gameMode;

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
    this.gameMode = mode;
    switch (mode) {
      case "easy":
        this.column = EASY_MODE;
        this.rows = EASY_MODE;
        this.totalMines = EASY_MODE * EASY_MODE / 8;
        break;
      case "medium":
        this.column = MEDIUM_MODE;
        this.rows = MEDIUM_MODE;
        this.totalMines = MEDIUM_MODE * MEDIUM_MODE / 6;
        break;
      case "hard":
        this.column = HARD_MODE;
        this.rows = HARD_MODE;
        this.totalMines = HARD_MODE * HARD_MODE / 4;
        break;
      default:
        this.column = EASY_MODE;
        this.rows = EASY_MODE;
        this.totalMines = EASY_MODE * EASY_MODE / 8;
        break;
    } // switch

    this.grid = new MatrixV0<>(this.column, this.rows, 0);
    this.revealed = new MatrixV0<>(this.column, this.rows, false);
    this.flagged = new MatrixV0<>(this.column, this.rows, false);

    placeMines();
    calculateAdjacentMines();
  } // Minesweeper(String)

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
        y representing the row number and X a column

        * flag y x: puts a flag on the field(possible mine)
        * unflag yx: puts a flag on the field(possible mine)
        * reveal yx: uncvovers the hidden feild
        * end : ends the game
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
        } // if
        int mineCount = 0;
        // Check all adjacent cells (8 directions)
        for (int m = -1; m <= 1; m++) {
          for (int n = -1; n <= 1; n++) {
            int mRow = i + m;
            int nRow = j + n;
            if (mRow >= 0
                && nRow >= 0
                && mRow < this.rows
                && nRow < this.column
                && this.grid.get(mRow, nRow) == BOMB) {
              mineCount++;
            } // if
          } // inner for loop
        } // outer for loop
        this.grid.set(i, j, mineCount); // Store the count of adjacent mines
      } // inner for loop(looping through columns)
    } // outer for loop
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
    } // if

    this.revealed.set(y, x, true);

    // if revealed cell is a BOMB, restart the game
    if (this.grid.get(y, x) == BOMB) {
      pen.println("Loser! You hit the mine!");
      resetGame();
      return;
    } // if

    // If the cell is empty, recursively revealing adjacent cells
    if (this.grid.get(y, x) == EMPTY_CELL) {
      for (int i = -1; i <= 1; i++) {
        for (int j = -1; j <= 1; j++) {
          if (y + i >= 0 && y + i < this.rows && x + j >= 0 && x + j < this.column) {
            revealingCell(y + i, x + j, pen);
          } // if
        } // inner for loop
      } // outer for loop
    } // if
  } // revealingCell()

  /**
   * This allows the player to flag a cell, potentially indicating that it contains a mine.
   *
   * @param x int(col)
   * @param y int(row)
   */
  public void flagCell(int y, int x) {
    if (this.revealed.get(y, x)) {
      return; // can't flag already uncovered cell
    } // if
    this.flagged.set(y, x, true);
  } // flagCell(int, int)

  /**
   * This allows the player to flag a cell, potentially indicating that it contains a mine.
   *
   * @param x int(col)
   * @param y int(row)
   */
  public void unFlagCell(int y, int x) {
    if (!this.flagged.get(y, x) || this.revealed.get(y, x)) {
      return; // can't unflag already uncovered cell or cell that is not flagged
    } // if
    this.flagged.set(y, x, false);
  } // unFlagCell(int, int)

  /**
   * The game checks if all non-mine cells are revealed, in which case the player wins.
   *
   * @return if all of the bomb were revelead return true
   */
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

  /** Resets the game. */
  public void resetGame() {
    this.grid = new MatrixV0<>(this.column, this.rows, 0);
    this.revealed = new MatrixV0<>(this.column, this.rows, false);
    this.flagged = new MatrixV0<>(this.column, this.rows, false);
    placeMines();
    calculateAdjacentMines();
  } // resetGame()

  /**
   * Displays the mine field. H is hidden values, and F if the cells is flagged.
   *
   * @param pen Printwriter object
   */
  public void displayGrid(PrintWriter pen) {
    pen.print("  ");
    for (int j = 0; j < this.column; j++) {
        pen.print(j + 1 + " ");  // Print column index
    }
    pen.println();
    for (int i = 0; i < this.rows; i++) {
      pen.print(i + 1 + " ");
      for (int j = 0; j < this.column; j++) {
        if (this.revealed.get(i, j)) {
          if (this.grid.get(i, j) == BOMB) {
            pen.print("X "); // Represent mine with "X"
          } else {
            pen.print(this.grid.get(i, j) + " "); // Show adjacent mine count
          } // if
        } else if (this.flagged.get(i, j)) {
          pen.print("F "); // Flagged cell
        } else {
          pen.print("H "); // Hidden cell
        } // if
      } // inner for loop
      pen.println();
    } // outer for loop
  } //displayGrid(PrintWriter)

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

    printInstructions(pen);
    pen.println("Please proveide the level of the game:");
    String mode = "";
    try {
      mode = eyes.readLine().trim();
    } catch (Exception e) {
      // do nothing
    } // try/catch
    mode.toLowerCase();
    if (!mode.equals("easy") && !mode.equals("medium") && !mode.equals("hard")) {
      pen.println("Invalid input selected! Easy mode selected automatically!");
      mode = "easy";
    } // if

    Minesweeper game = new Minesweeper(mode);
    while (!game.checkWin()) {
      game.displayGrid(pen); // Display the grid before the move
      pen.println("Enter command: ");
      String command = "";
      try {
        command = eyes.readLine().trim();
      } catch (Exception e) {
        // do nothing
      } // try/catch

      String[] parts = command.split(" ");
      if (parts.length == 1 && parts[0].equals("end")) {
        pen.println("Thanks for playing my game");
        return;
      } else if (parts.length < 3) {
        pen.println("Invalid number of commands");
        continue;
      } // if

      String action = parts[0];
      int y = Integer.parseInt(parts[1]) - 1;
      int x = Integer.parseInt(parts[2]) - 1;
      if (x < 0 || x >= game.column || y < 0 || y >= game.rows) {
        pen.println("Range is out of the mine field!");
        continue;
      } //if

      switch (action.toLowerCase()) {
        case "flag":
          game.flagCell(y, x);
          break;
        case "unflag":
          game.unFlagCell(y, x);
          break;
        case "reveal":
          game.revealingCell(y, x, pen);
          break;
        case "reset":
          game.resetGame();
          break;
        default:
          pen.println("Invalid command. Try again!");
          break;
      } // switch
    } // while
    pen.println("You win!");
  } // main(String[])
} // Minesweeper
