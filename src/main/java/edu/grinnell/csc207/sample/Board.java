package edu.grinnell.csc207.sample;

//import edu.grinnell.csc207.minesweeper.displayUpdater;
import edu.grinnell.csc207.util.ArrayUtils;
import edu.grinnell.csc207.util.IOUtils;
import edu.grinnell.csc207.util.Matrix;
import edu.grinnell.csc207.util.MatrixV0;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.PrintWriter;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Board {

  private Matrix<Integer> matrix;

  // Expect Medium: 14x18 with 40 mines
  public void initializeBoard(int w, int h) {

    matrix = new MatrixV0<Integer>(h, w, Integer.valueOf(0));

  }

  public int mineCounter(int r, int c) {
    int count = 0;

    if (matrix.get(r, c).intValue() == -1) {
      return -1;
    }

    for (int i = r - 1; i <= r + 1; i++) {
      for (int j = c - 1; j <= c + 1; j++) {
        if (!(i < 0 || j < 0 || i >= matrix.height() || j >= matrix.width())) {
          if (matrix.get(i, j).intValue() == -1) {
            count++;
          }
        }
      }
    }
    // if(r == 0) // Ignore left side
    // if(c == 0) // Ignore top
    // if(r == matrix.width()-1) // Ignore right
    // if(c == matrix.height()-1) // Ignore bottom
    return count;
  }

  public Board(int w, int h, int mines) {

    initializeBoard(h, w);

    while (mines != 0) {
      int r = (int) (Math.random() * h);
      int c = (int) (Math.random() * w);
      System.out.printf("%d:%d\n", r, c);

      if (matrix.get(r, c).intValue() != -1) {
        mines--;
        System.out.printf("%d\n", mines);
        matrix.set(r, c, Integer.valueOf(-1));

      }
    }
    System.out.printf("Made it out\n");

    for (int i = 0; i < h; i++) {
      for (int j = 0; j < w; j++) {
        matrix.set(i, j, Integer.valueOf(mineCounter(i, j)));
      }
    }
  }

  public static void main(String[] args) {
    PrintWriter pen = new PrintWriter(System.out, true);
    Board minesweeper = new Board(14, 18, 40);


    // displayUpdater display = new displayUpdater(minesweeper.matrix);
    // display.print();

    // display.flag(1, 'd');

    // display.checkIndex(1, 'c');
    // display.checkIndex(10, 'c');

    // display.print();

    // display.checkIndex(5, 'c');

  }

}
