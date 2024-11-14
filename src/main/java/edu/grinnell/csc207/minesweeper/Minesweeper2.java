package edu.grinnell.csc207.minesweeper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

/** Second version of the Minesweeper game. */
public class Minesweeper2 {

  // +----------------+----------------------------------------------
  // |   Parameters    |
  // +----------------+

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
        Welcome to Minesweeper 2.0.
        There are two versions of this game, Minesweeper under sample is writen by Koast,
        and this one utilizes modifies said version to integrate code written by Jana and Alex

        Command-line arguments:

        Selecting difficulty of the game:

        * Easy - creates a 8 by 10 board
        * Medium - creates a 14 by 18 board
        * Hard - creates a 20 by 24 board

        Your goal is to flag all the possible mines on the field.Ha

        * flag row col: puts a flag on the field(possible mine)
        * unflag row col: puts a flag on the field(possible mine)
        * reset: resets the game
        * check row col: uncvovers the hidden feild
        * end : ends the game
        """);
  } // printInstructions(PrintWriter)

  // +------+--------------------------------------------------------
  // | Main |
  // +------+

  /**
   * Run the game.
   *
   * @param args Command-line arguments.
   */
  public static void main(String[] args) throws IOException {
    PrintWriter pen = new PrintWriter(System.out, true);
    BufferedReader eyes = new BufferedReader(new InputStreamReader(System.in));

    printInstructions(pen);
    pen.println("Please proveide the level of the game: (Easy, Medium or Hard)");
    String mode = "";

    try {
      mode = eyes.readLine().trim();
    } catch (Exception e) {
      // do nothing
    } // try/catch

    if (!mode.equals("Easy") && !mode.equals("Medium") && !mode.equals("Hard")) {
      pen.println("Invalid input selected! Easy mode selected automatically!");
      mode = "Easy";
    } // if
    Board minesweeper;
    int mines;

    switch (mode) {
      case "Easy":
        minesweeper = new Board(8, 10, 10);
        mines = 10;
        break;
      case "Medium":
        minesweeper = new Board(14, 18, 40);
        mines = 40;
        break;
      case "Hard":
        minesweeper = new Board(20, 24, 99);
        mines = 99;
        break;
      default:
        minesweeper = new Board(8, 10, 10);
        mines = 10;
    } // switch

    DisplayUpdater display = new DisplayUpdater(minesweeper.getMatrix(), mines);

    while (!display.checkWin()) {
      display.print(); // Display the grid before the move
      pen.println("Enter command: ");
      String command = "";
      try {
        command = eyes.readLine().trim();
      } catch (Exception e) {
        // do nothing
      } // try/catch

      String[] commandArray = command.split(" ");
      if (commandArray.length < 3) {
        continue;
      } // if

      String action = commandArray[0];
      int row = 0;
      char column = 'a';
      try {
        row = Integer.parseInt(commandArray[1]);
        column = commandArray[2].charAt(0);
      } catch (Exception e) {
        pen.println(
            "the order of your commands was wrong, it should be row (ie number) first and letter"
                + " second");
        return;
      } // try/catch

      switch (action.toLowerCase()) {
        case "flag":
          display.flag(row, column);
          break;
        case "unflag":
          display.unflag(row, column);
          break;
        case "check":
          if (display.checkIndex(row, column) == 0) {
            pen.println("you hit a mine and lost :( please re start the game");
            return;
          } // if
          break;
        case "reset":
          display = new DisplayUpdater(minesweeper.getMatrix(), mines);
        case "end":
          return;
        default:
          pen.println("Invalid command. Try again!");
          break;
      } // switch
    } // while

    pen.println("You win!");
    pen.close();
    eyes.close();
  } // main(String[])
} // Minesweeper2
