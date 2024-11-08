package edu.grinnell.csc207.minesweeper;

import edu.grinnell.csc207.util.ArrayUtils;
import edu.grinnell.csc207.util.IOUtils;
import edu.grinnell.csc207.util.Matrix;
import edu.grinnell.csc207.util.MatrixV0;
import edu.grinnell.csc207.minesweeper.displayUpdater;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.PrintWriter;

import java.util.Arrays;
import java.util.List;
import java.util.Random;


/* 
 * Creates the board of integers for the minesweeper game.
*/
public class Board {
    
  // +--------+------------------------------------------------------
  // | Fields |
  // +--------+

  /**
   * The matrix of integers.
   */
  private Matrix <Integer> matrix;

  
  // +--------------+------------------------------------------------
  // | Constructors |
  // +--------------+


    /**
   * Creates the minesweeper grid where each cell contains either the amount of mines
   * surrounding it or -1 if the cell contains a mine.
   * 
   * @param w The width of the minesweeper board.
   * @param h The height of the minesweeper board.
   * @param mines The number of mines in the minesweeper board.
   */
  public Board(int w, int h, int mines) {
    
    //Initializes the each element to 0.
    matrix = new MatrixV0 <Integer>(w, h, Integer.valueOf(0));
    
    //Inserts mines into the minesweeper board
    while(mines != 0) {
      int r = (int)(Math.random() * h);
      int c = (int)(Math.random() * w);

      if(matrix.get(r, c).intValue() != -1) {
        mines--;
        matrix.set(r, c, Integer.valueOf(-1));
      } // if
    } // while-loop
 
    //Inserts the numbers counting the surrounding mines into the minesweeper board.
    for(int i = 0; i < h; i++) {
      for(int j = 0; j < w; j++) {
        matrix.set(i, j, Integer.valueOf(mineCounter(i,j)));
      } // for-loop
    } // for-loop
  } // Board(int, int, int)


  // +--------------+------------------------------------------------
  // | Core methods |
  // +--------------+


    /**
   * Gets the given matrix.
   * 
   * @return the integer matrix.
   */
  public Matrix <Integer> getMatrix() {
    return this.matrix;
  } // getMatrix()
  

  /**
   * Counts the number of mines surrounding the given cell. Returns -1 if the cell is a mine.
   * 
   * @param r The row of the cell.
   * @param c The column of the cell.
   * 
   * @return An integer representing the number of mines around the cell or -1 if there is a mine.
   */
  public int mineCounter(int r, int c) {
    int count = 0;
    
    //Checks to see if the given cell contains a mine.
    if(matrix.get(r, c).intValue() == -1) {
      return -1;
    } // if

    //Counts the number of mines surrounding the cell.
    for(int i = r-1; i <= r+1; i++) {
      for(int j = c-1; j <= c+1; j++){
        if(!(i < 0 || j < 0 || i >= matrix.height() || j >= matrix.width())) {
          if(matrix.get(i,j).intValue() == -1) {
            count++;
          } // if
        } // if
      } // for-loop
    } // for-loop

    return count;
  } // mineCounter(int, int)

  

  public static void main(String[] args) {
    PrintWriter pen = new PrintWriter(System.out, true);
    Board minesweeper = new Board(14,18,40);
    Matrix.print(pen,minesweeper.getMatrix());

    displayUpdater display = new displayUpdater(minesweeper.getMatrix());
    display.print();
  }
  
} // class Board
