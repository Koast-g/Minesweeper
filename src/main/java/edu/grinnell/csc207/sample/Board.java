package edu.grinnell.csc207.sample;

// import edu.grinnell.csc207.minesweeper.displayUpdater;
import edu.grinnell.csc207.util.Matrix;
import edu.grinnell.csc207.util.MatrixV0;

/** Class that creates a board for minesweeper game. */
public class Board {

  /** Creates a grid of integers. */
  private Matrix<Integer> matrix;

  /**
   * Expect Medium: 14x18 with 40 mines.
   *
   * @param w width
   * @param h height
   */
  public void initializeBoard(int w, int h) {
    matrix = new MatrixV0<Integer>(h, w, Integer.valueOf(0));
  } // initializeBoard

  /**
   * Counts the amount of mines.
   *
   * @param r row number
   * @param c column number
   * @return count of the mines
   */
  public int mineCounter(int r, int c) {
    int count = 0;

    if (matrix.get(r, c).intValue() == -1) {
      return -1;
    } // if

    for (int i = r - 1; i <= r + 1; i++) {
      for (int j = c - 1; j <= c + 1; j++) {
        if (!(i < 0 || j < 0 || i >= matrix.height() || j >= matrix.width())) {
          if (matrix.get(i, j).intValue() == -1) {
            count++;
          } // if
        } // if
      } // for
    } // for
    // if(r == 0) // Ignore left side
    // if(c == 0) // Ignore top
    // if(r == matrix.width()-1) // Ignore right
    // if(c == matrix.height()-1) // Ignore bottom
    return count;
  } // mineCount(int, int)

  /**
   * Initialize the Board.
   *
   * @param w width
   * @param h height
   * @param mines amount of mines on the field
   */
  public Board(int w, int h, int mines) {

    initializeBoard(h, w);

    while (mines != 0) {
      int r = (int) (Math.random() * h);
      int c = (int) (Math.random() * w);

      if (matrix.get(r, c).intValue() != -1) {
        mines--;
        System.out.printf("%d\n", mines);
        matrix.set(r, c, Integer.valueOf(-1));
      } // if
    } // while

    for (int i = 0; i < h; i++) {
      for (int j = 0; j < w; j++) {
        matrix.set(i, j, Integer.valueOf(mineCounter(i, j)));
      } // for
    } // for
  } // Board(int, int, int)
} // Board
