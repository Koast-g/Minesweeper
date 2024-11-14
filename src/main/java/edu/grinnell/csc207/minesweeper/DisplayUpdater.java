package edu.grinnell.csc207.minesweeper;

import edu.grinnell.csc207.blocks.AsciiBlock;
import edu.grinnell.csc207.blocks.Boxed;
import edu.grinnell.csc207.blocks.HComp;
import edu.grinnell.csc207.blocks.HComp.VAlignment;
import edu.grinnell.csc207.blocks.Line;
import edu.grinnell.csc207.blocks.VComp;
import edu.grinnell.csc207.blocks.VComp.HAlignment;
import edu.grinnell.csc207.util.Matrix;
import edu.grinnell.csc207.util.MatrixV0;
import java.io.PrintWriter;

/**
 * A sample one-player game (is that a puzzle?). Intended as a potential use of our Matrix
 * interface.
 *
 * @author Jana Vadillo
 */
public class DisplayUpdater {
  // +-----------+---------------------------------------------------
  // | Constants |
  // +-----------+

  /** enum used to keep track of the differnet possible values to display. */
  enum DisplayVals {
    /** Unchecked value. */
    UNCHECKED,
    /** Empty value (ie not surrounded by any mines but has been checked). */
    EMPTY,
    /** Mine :(). */
    MINE,
    /** the number of surrounding mines (greater than zero). */
    NUMBER,
    /** flag has been placed here. */
    FLAG
  } // DisplayVals

  // +-----------+---------------------------------------------------
  // | Variables |
  // +-----------+

  /** The width of the current row being used. */
  int width;

  /** The height of the row being used. */
  int height;

  /** The number of flags the user has placed so far. */
  int flagsPlaced = 0;

  /** The number of Bins the user has checked so far. */
  int checkedBins = 0;

  /** The number of mines the game mode selected. */
  int numberOfMines;

  /** the matrix generated and updated by this object in order to keep track of what to display. */
  Matrix<DisplayVals> display;

  /** the matrix of the given boardstate for the game itself used to keep track of it. */
  Matrix<Integer> reference;

  // +--------------+------------------------------------------------
  // | Constructors |
  // +--------------+
  /**
   * DisplayUpdator Constructor, to make an object to keep track of what the user has seen.
   *
   * @param mines number of mines
   * @param givenMatrix the reference to check against
   */
  public DisplayUpdater(Matrix<Integer> givenMatrix, int mines) {
    this.numberOfMines = mines;

    this.reference = givenMatrix.clone();
    this.width = givenMatrix.width();
    this.height = givenMatrix.height();
    this.display = new MatrixV0<DisplayVals>(this.width, this.height, DisplayVals.UNCHECKED);
  } // displayUpdater(Matrix<Integer>)

  // +--------------+-----------------------------------------------
  // | Helper Methods |
  // +-----------------+
  /**
   * simple helper function to define if the index r&c is in the bounds of the matrix.
   *
   * @param r row
   * @param c col
   * @return a true or false value depending on if we are in the bound
   */
  public boolean inBounds(int r, int c) {
    return ((r >= 0) && (c >= 0) && (r < this.height) && (c < this.width));
  } // inBounds(int, int)

  /**
   * if a number is found on a given index, handle its display.
   *
   * @param r row in which a number was found
   * @param c column in which a number was found
   */
  private void updateNumber(int r, int c) {
    this.display.set(r, c, DisplayVals.NUMBER);
  } // updateNumber(int, int)

  /**
   * handles empty values at given indexes.
   *
   * @param r row in which an empty space was found
   * @param c column in which an empty space was found
   */
  private void updateEmpty(int r, int c) {
    this.display.set(r, c, DisplayVals.EMPTY);
  } // updateEmpty(int,int)

  /**
   * handles what happens when a mine is checked.
   *
   * @param r row checked
   * @param c column checked
   */
  private void updateMine(int r, int c) {
    this.display.set(r, c, DisplayVals.MINE); // printing is done
    this.print();
  } // updateMine(int,int)

  /**
   * check if the user won.
   *
   * @return true if number of cheked cells and number of mines is greater than the area of the
   *     board
   */
  public boolean checkWin() {
    return ((this.checkedBins + this.numberOfMines) >= (this.width * this.height));
  } // updateMine(int,int)

  // +--------------+-----------------------------------------------
  // | Public Methods |
  // +-----------------+
  /**
   * places down a flag in the given location.
   *
   * @param r the row in which to place down the flag
   * @param col the column in which to place down the flag
   */
  public void flag(int r, char col) {
    int c = (int) col - (int) 'a';
    if (this.display.get(r, c) == DisplayVals.UNCHECKED) {
      this.display.set(r, c, DisplayVals.FLAG);
      this.flagsPlaced++;
    } // check if it is currently a valid place to do place a flag, otherwise do nothing
  } // flag(int,int)

  /**
   * unflags flagged sell.
   *
   * @param r num of rows
   * @param col num of col
   */
  public void unflag(int r, char col) {
    int c = (int) col - (int) 'a';
    if (this.display.get(r, c) == DisplayVals.FLAG) {
      this.display.set(r, c, DisplayVals.UNCHECKED);
      this.flagsPlaced--;
    } // check if it is currently a valid place to do place a flag, otherwise do nothing
  } // flag(int,int)

  /**
   * checks the given index for a mine.
   *
   * @param r row to check
   * @param col column to check
   * @return o if updating a mine, -1 empty cell, otherwise 1
   */
  public int checkIndex(int r, char col) {
    int c = (int) col - (int) 'a';
    int value = this.reference.get(r, c).intValue();
    if (this.display.get(r, c) == DisplayVals.UNCHECKED) {
      this.checkedBins++;
      if (value == -1) {
        this.updateMine(r, c);
        return (0);
      } // runs function to update the position with a mine

      if (value == 0) {
        this.updateEmpty(r, c);
        return (-1);
      } // runs function to update the empty position

      if (value > 0) {
        this.updateNumber(r, c);
        return (1);
      } else {
        return (1);
      } // runs function to update number
    } else {
      return (1);
    } // ensures that any index we check has not been checked before
  } // checkIndex(int,int)

  /** prints the current display. */
  public void print() {
    AsciiBlock[] rows = new AsciiBlock[height];
    AsciiBlock[] rowReference = new AsciiBlock[height + 3];
    rowReference[0] = new Line(" ");
    rowReference[1] = new Line(" ");
    rowReference[height + 2] = new Line(" ");

    AsciiBlock[] colReference = new AsciiBlock[width + 2];
    colReference[0] = new Line(" ");
    colReference[width + 1] = new Line(" ");

    // make a list of ascii blocks for the rows, the row reference  and the col ref

    for (int row = 0; row < this.height; row++) {
      AsciiBlock[] spaces = new AsciiBlock[width]; // make new array for the single charachters here
      for (int col = 0; col < this.width; col++) {
        AsciiBlock space;
        DisplayVals value = this.display.get(row, col);

        if (value == DisplayVals.MINE) {
          space = new Line("X ");
        } else if (value == DisplayVals.FLAG) {
          space = new Line("! ");
        } else if (value == DisplayVals.UNCHECKED) {
          space = new Line(". ");
        } else if (value == DisplayVals.NUMBER) {
          space = new Line(this.reference.get(row, col).toString() + " ");
        } else if (value == DisplayVals.EMPTY) {
          space = new Line("  ");
        } else {
          space = new Line("e");
        } // else to fill a value with an error for troubleshooting
        spaces[col] = space;

        int columnLetter = col + (int) 'a';
        colReference[col + 1] = new Line(String.valueOf((char) columnLetter) + " ");
      } // itterate through and set the corresponding spot in the line to the relative array value

      AsciiBlock asciiRow = new HComp(VAlignment.CENTER, spaces);
      rows[row] = asciiRow;

      rowReference[row + 2] = new Line(Integer.toString(row));
    } // take leters in a row and make them into a single ascii block adding them to the row array

    AsciiBlock colAsciiRef = new HComp(VAlignment.CENTER, colReference);
    AsciiBlock rowAsciiRef = new VComp(HAlignment.CENTER, rowReference);
    AsciiBlock asciiDisplay = new Boxed(new VComp(HAlignment.CENTER, rows));

    AsciiBlock[] verticalComposition = {colAsciiRef, asciiDisplay};
    asciiDisplay = new VComp(HAlignment.CENTER, verticalComposition);
    // add the column reference at the top of the ascii block
    AsciiBlock[] horComposition = {rowAsciiRef, asciiDisplay};
    asciiDisplay = new HComp(VAlignment.CENTER, horComposition);
    // add the line reference to the left of the display

    PrintWriter pen = new PrintWriter(System.out, true);

    AsciiBlock.print(pen, asciiDisplay);
    pen.println("this many unflagged mines remain:" + (this.numberOfMines - this.flagsPlaced));
    // finish up and print

  } // print()
} // end of class
