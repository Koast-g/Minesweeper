package edu.grinnell.csc207.minesweeper;

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


/**
 * A sample one-player game (is that a puzzle?). Intended as a potential
 * use of our Matrix interface.
 *
 * @author Jana Vadillo
 */
public class displayUpdater {
  // +-----------+---------------------------------------------------
  // | Constants |
  // +-----------+
  enum displayVals {
    UNCHECKED,
    EMPTY,
    MINE,
    NUMBER
  }

  // +-----------+---------------------------------------------------
  // | Variables |
  // +-----------+

  /**
   * The width of the current row being used.
   */
  int width;

  /**
   * The height of the row being used.
   */
  int height;
/**
 * the matrix generated and updated by this object in order to keep track of what to display
 */
  Matrix<displayVals> display;
  /**
   * the matrix of the given boardstate for the game itself used to keep track of it
   */
  Matrix<Integer> reference;

  
  // +--------------+------------------------------------------------
  // | Constructors |
  // +--------------+
  public displayUpdater(Matrix<Integer> givenMatrix){
    this.reference = givenMatrix.clone();
    this.width = givenMatrix.width();
    this.height = givenMatrix.height();
    this.display = new MatrixV0<displayVals>(this.width, this.height, displayVals.UNCHECKED);
  }//displayUpdater(Matrix<Integer>)


    // +--------------+-----------------------------------------------
  // | Helper Methods |
  // +-----------------+

  /**
   * if a number is found on a given index that seeks to be updated, this function does the appropriate steps to update the display at that index
   * @param r row in which a number was found
   * @param c column in which a number was found
   */
  private void updateNumber(int r, int c){
    
  }//updateNumber(int, int)
  /**
   * if a empty value is found on a given index that seeks to be updated,  this function updates the display, and checks all the surrounding spaces autmatically 
   * @param r row in which an empty space was found
   * @param c column in which an empty space was found
   */
  private void updateEmpty(int r, int c){
    
  }//updateEmpty(int,int)

  private void updateMine(int r, int c){
    
  }// updateMine(int,int)



    // +--------------+-----------------------------------------------
  // | Public Methods |
  // +-----------------+

  private void checkIndex(int r, int c){
    
  }//checkIndex(int,int)

  private void print(){
    
  }//print()

}
