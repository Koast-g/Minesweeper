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


public class Board{
    
    private Matrix <Integer> matrix;

    //Expect Medium: 14x18 with 40 mines
  public void initializeBoard(int w, int h){

    matrix = new MatrixV0<>(w,h,0);

    for(int i = 0; i<w; i++){
      for(int j = 0; j<h; j++){
        matrix.set(i,j,0);
      }
    }
  }

  public int mineCounter(int r, int c){
    int count = 0;
    
    if(matrix.get(r,c)==-1){
      return -1;
    }

    for(int i = r-1; i<=r+1; i++){
      for(int j = c-1; j<=c+1; j++){
        if(!(i<0 || j<0 || i>=matrix.width() || j>=matrix.height())){
          if(matrix.get(i,j)==-1){
            count++;
          }
        }
      }
    }

    return count;
    // if(r == 0) // Ignore left side
    // if(c == 0) // Ignore top
    // if(r == matrix.width()-1) // Ignore right
    // if(c == matrix.height()-1) // Ignore bottom
  }

  public Board(int w, int h, int mines){

    initializeBoard(w, h);
        
    while(mines != 0){
      int r = (int)(Math.random() * w);
      int c = (int)(Math.random() * h);

      if(matrix.get(r,c)!=-1){
        mines--;
        matrix.set(r,c,-1);
      }
    }

    for(int i = 0; i<w; i++){
      for(int j = 0; j<h; j++){
        matrix.set(i,j, mineCounter(i,j));
      }
    }
  }
  

  
  public static void main(String[] args){
    PrintWriter pen = new PrintWriter(System.out, true);
    Board minesweeper = new Board(14,18,40);
    Matrix.print(pen,minesweeper.matrix);
  }
}
