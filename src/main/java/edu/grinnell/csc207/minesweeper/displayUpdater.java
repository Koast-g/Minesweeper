package edu.grinnell.csc207.minesweeper;

import edu.grinnell.csc207.util.ArrayUtils;
import edu.grinnell.csc207.util.IOUtils;
import edu.grinnell.csc207.util.Matrix;
import edu.grinnell.csc207.util.MatrixV0;
import edu.grinnell.csc207.blocks.AsciiBlock;
import edu.grinnell.csc207.blocks.Line;
import edu.grinnell.csc207.blocks.VComp;
import edu.grinnell.csc207.blocks.HComp.VAlignment;
import edu.grinnell.csc207.blocks.VComp.HAlignment;
import edu.grinnell.csc207.blocks.HComp;
import edu.grinnell.csc207.blocks.Boxed;

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

  /**
   * enum used to keep track of the differnet possible values to display
   */
  enum displayVals {
    UNCHECKED,
    EMPTY,
    MINE,
    NUMBER,
    FLAG
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
 * simple helper function to define if the index r&c is in the bounds of the matrix
 * @param r
 * @param c
 * @return
 */
  public boolean inBounds(int r, int c){
    return((r>=0) && (c>= 0) && (r< this.height) &&  (c < this.width));
  }//inBounds(int, int)

  /**
   * if a number is found on a given index that seeks to be updated, this function does the appropriate steps to update the display at that index
   * @param r row in which a number was found
   * @param c column in which a number was found
   */
  private void updateNumber(int r, int c){
    this.display.set(r,c,displayVals.NUMBER);
  }//updateNumber(int, int)
  /**
   * if a empty value is found on a given index that seeks to be updated,  this function updates the display, and checks all the surrounding spaces autmatically 
   * @param r row in which an empty space was found
   * @param c column in which an empty space was found
   */
  private void updateEmpty(int r, int c){
    this.display.set(r,c,displayVals.EMPTY);
    for (int row = -1; row < 2; row ++){
      for (int col = -1; col < 2; row ++){
        if ((row !=col) && (row!= 0)){
          int checkRow = r +row;
          int checkCol = c + col;

          if (this.inBounds(checkRow, checkCol)){
            checkIndex(checkRow, checkCol);
          }// check for the new slot being within the bounds of the matrix of a whole
        }//check avoiding us re-doing the check on the central loop and starting the cycle over again
      }//itterate through three columns

    }//itterate through the three rows and later columns to check all available slots around an empty one 
    
  }//updateEmpty(int,int)

  /**
   * run when a user checks a hidden tile that contains a mine under it, printing the value before exiting the game
   * @param r row checked
   * @param c column checked
   */
  private void updateMine(int r, int c){
    //STUB
    this.display.set(r,c,displayVals.MINE); // printing is done 
    this.print();
    
    //Should simply call a function for the end of the game.
    
  }// updateMine(int,int)



    // +--------------+-----------------------------------------------
  // | Public Methods |
  // +-----------------+
/**
 * places down a flag in the given location
 * @param r the row in which to place down the flag
 * @param c the column in which to place down the flag
 */
  public void Flag(int r, int c){
    if (this.display.get(r,c) == displayVals.UNCHECKED){
      this.display.set(r,c,displayVals.FLAG);
    }// check if it is currently a valid place to do place a flag, otherwise do nothing
    print();
  }//flag(int,int)
/**
 * checks the given index for a mine
 * @param r row to check
 * @param c column to check
 */
  public void checkIndex(int r, int c){
    int value = this.reference.get(r,c).intValue();

    if (value == -1) {
      this.updateMine(r,c);
    }// runs function to update the position with a mine

    if (value == 0) {
      this.updateEmpty(r, c);
    } //runs function to update the empty position

    if (value > 0) {
      this.updateNumber(r, c);
    }// runs function to update nu,ber
    
    this.print();

  }//checkIndex(int,int)
/**
 * prints the current display
 */
  public void print(){
    AsciiBlock[] rows = new AsciiBlock[height];
    AsciiBlock[] rowReference = new AsciiBlock[height+3];
    rowReference[0] = new Line(" ");
    rowReference[1] = new Line(" ");


    AsciiBlock[] colReference = new AsciiBlock[height+3];
    colReference[0] = new Line(" ");
    colReference[1] = new Line(" ");

    //make a list of ascii blocks for the rows, the row reference (which will count up for the rows we have)  and the col ref


    for (int row = 0; row<this.height; row++){
      AsciiBlock[] spaces = new AsciiBlock[width];// make a new array for all the single charachters in this row
      for (int col =0; col <this.width; col++){
        AsciiBlock space;
        displayVals value = this.display.get(row,col);

        if (value == displayVals.MINE) {
          space = new Line("O");
        }// if a mine is found set the space as the given charachter
    
        else if (value == displayVals.FLAG) {
          space = new Line("!");
        }//if a flag is found add an exclamation point
    

        else if (value == displayVals.UNCHECKED) {
          space = new Line("X");
        }  // unchecked values are X

        else if (value == displayVals.NUMBER) {
          space = new Line(this.reference.get(row,col).toString());
        }  // if a number is found look up the respective numver and cast it as a string

        else {
          space = new Line(" ");
        }// otherwise it must be an empty space, cast it as such
        spaces[col] = space;
        colReference[col+2] = new Line(Integer.toString(col)); // will this run more times than necesarry? perhaps, please forgive this minor overwriting
      }//itterate through and set the corresponding spot in the line to the relative array value and update the column refreence

      AsciiBlock asciiRow = new HComp(VAlignment.CENTER, spaces);
      rows[row] = asciiRow;

      rowReference[row+2] = new Line(Integer.toString(row));
    }// much like above, take all of the leters in a single row and make them into a single ascii block adding them to the row array
    AsciiBlock colAsciiRef = new HComp(VAlignment.CENTER, colReference);
    AsciiBlock rowAsciiRef = new VComp(HAlignment.CENTER, rowReference);
    AsciiBlock asciiDisplay = new Boxed(new  VComp(HAlignment.CENTER, rows));
    // make ascii blocks of the whole array stacked up as well as references to look up respective numbers
    // AsciiBlock[] verticalComposition = {colAsciiRef, asciiDisplay};
    // asciiDisplay = new VComp(HAlignment.CENTER, verticalComposition);
    // //add the column reference at the top of the ascii block
    // AsciiBlock[] HComposition = {rowAsciiRef, asciiDisplay};
    // asciiDisplay = new HComp(VAlignment.CENTER, HComposition);
    // // add the line reference to the left of the display

    PrintWriter pen = new PrintWriter(System.out, true);


    AsciiBlock.print(pen,asciiDisplay);
    pen.close();
    //finish up and print

    
    
  }//print()

}
