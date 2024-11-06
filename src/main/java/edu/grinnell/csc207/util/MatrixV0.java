package edu.grinnell.csc207.util;

/**
 * An implementation of two-dimensional matrices.
 *
 * @author Kostiantyn Tsymbal
 * @author Samuel A. Rebelsky
 * @param <T> The type of values stored in the matrix.
 */
public class MatrixV0<T> implements Matrix<T> {
  // +--------+------------------------------------------------------
  // | Fields |
  // +--------+
  /** Width of the array. */
  private int numCol;

  /** Height of the array. */
  private int numRow;

  /** A two dimentional array. */
  private T[][] matrix;

  /** The default value. */
  private T defVal;

  // +--------------+------------------------------------------------
  // | Constructors |
  // +--------------+

  /**
   * Create a new matrix of the specified width and height with the given value as the default.
   *
   * @param width The width of the matrix.
   * @param height The height of the matrix.
   * @param def The default value, used to fill all the cells.
   * @throws NegativeArraySizeException If either the width or height are negative.
   */
  @SuppressWarnings("unchecked")
  public MatrixV0(int width, int height, T def) {
    if (width < 0 || height < 0) {
      throw new NegativeArraySizeException("Either widht or height are negative");
    } else {
      // Initializing the height and width and def value of the matrix
      this.numCol = width;
      this.numRow = height;
      this.defVal = def;
      // Initializing the matrix with T type
      this.matrix = (T[][]) new Object[height][width];
      // Fill the matrix with default values
      for (int i = 0; i < height; i++) {
        for (int j = 0; j < width; j++) {
          this.matrix[i][j] = def;
        } // inner for loop
      } // outer for loop
    } // if
  } // MatrixV0(int, int, T)

  /**
   * Create a new matrix of the specified width and height with null as the default value.
   *
   * @param width The width of the matrix.
   * @param height The height of the matrix.
   * @throws NegativeArraySizeException If either the width or height are negative.
   */
  public MatrixV0(int width, int height) {
    this(width, height, null);
  } // MatrixV0

  // +--------------+------------------------------------------------
  // | Core methods |
  // +--------------+

  /**
   * Get the element at the given row and column.
   *
   * @param row The row of the element.
   * @param col The column of the element.
   * @return the value at the specified location.
   * @throws IndexOutOfBoundsException If either the row or column is out of reasonable bounds.
   */
  public T get(int row, int col) {
    if (row >= this.numRow || col >= this.numCol) {
      throw new IndexOutOfBoundsException("row or column are out of resonable bounds");
    } else {
      return this.matrix[row][col];
    } // if
  } // get(int, int)

  /**
   * Set the element at the given row and column.
   *
   * @param row The row of the element.
   * @param col The column of the element.
   * @param val The value to set.
   * @throws IndexOutOfBoundsException If either the row or column is out of reasonable bounds.
   */
  public void set(int row, int col, T val) {
    if (row < 0 || col < 0 || row >= this.numRow || col >= this.numCol) {
      throw new IndexOutOfBoundsException("row or column are out of resonable bounds");
    } // if
    this.matrix[row][col] = val;
  } // set(int, int, T)

  /**
   * Determine the number of rows in the matrix.
   *
   * @return the number of rows.
   */
  public int height() {
    return this.numRow;
  } // height()

  /**
   * Determine the number of columns in the matrix.
   *
   * @return the number of columns.
   */
  public int width() {
    return this.numCol;
  } // width()

  /**
   * Insert a row filled with the default value.
   *
   * @param row The number of the row to insert.
   * @throws IndexOutOfBoundsException If the row is negative or greater than the height.
   */
  @SuppressWarnings("unchecked")
  public void insertRow(int row) {
    if (row < 0 || row > this.numRow) {
      throw new IndexOutOfBoundsException("row is negative or greater than the height");
    } else {
      // Initializing the new array
      T[][] newMatrix = (T[][]) new Object[this.numRow + 1][this.numCol];
      // Coping the values from old 2d array to the new one before the point of the insetion
      for (int i = 0; i < row; i++) {
        System.arraycopy(matrix[i], 0, newMatrix[i], 0, this.numCol);
      } // for loop
      // Filling the row with the new value
      for (int j = 0; j < this.numCol; j++) {
        newMatrix[row][j] = this.defVal;
      } // for loop
      // Copying the array after the insetion point
      for (int i = row; i < this.numRow; i++) {
        System.arraycopy(matrix[i], 0, newMatrix[i + 1], 0, this.numCol);
      } // for loop
      // Updating the matrix
      this.matrix = newMatrix;
      ++this.numRow;
    } // end of if
  } // insertRow(int)

  /**
   * Insert a row filled with the specified values.
   *
   * @param row The number of the row to insert.
   * @param vals The values to insert.
   * @throws IndexOutOfBoundsException If the row is negative or greater than the height.
   * @throws ArraySizeException If the size of vals is not the same as the width of the matrix.
   */
  public void insertRow(int row, T[] vals) throws ArraySizeException {
    if (vals.length != this.numCol) {
      throw new ArraySizeException("size of vals is not the same as the width of matrix");
    } else {
      try {
        insertRow(row);
        // Setting the vals to the new edded row
        for (int i = 0; i < this.numCol; i++) {
          this.matrix[row][i] = vals[i];
        } // for loop
      } catch (IndexOutOfBoundsException e) {
        throw new IndexOutOfBoundsException("The row is negative or greater than the height");
      } // try/catch
    } // if
  } // insertRow(int, T[])

  /**
   * Insert a column filled with the default value.
   *
   * @param col The number of the column to insert.
   * @throws IndexOutOfBoundsException If the column is negative or greater than the width.
   */
  @SuppressWarnings("unchecked")
  public void insertCol(int col) {
    if (col < 0 || col > this.numCol) {
      throw new IndexOutOfBoundsException("column is negative or greater than the width");
    } else {
      // Initializing a new array
      T[][] newMatrix = (T[][]) new Object[this.numRow][this.numCol + 1];
      // Copying the array and adding a new column
      for (int i = 0; i < this.numRow; i++) {
        for (int j = 0; j < col; j++) {
          newMatrix[i][j] = this.matrix[i][j];
        } // inner for loop
        // Set the new column value
        newMatrix[i][col] = this.defVal;
        // Copy elements after the new column
        for (int j = col; j < this.numCol; j++) {
          newMatrix[i][j + 1] = this.matrix[i][j];
        } // inner for loop
      } // outer for loop
      this.matrix = newMatrix;
      ++this.numCol;
    } // if
  } // insertCol(int)

  /**
   * Insert a column filled with the specified values.
   *
   * @param col The number of the column to insert.
   * @param vals The values to insert.
   * @throws IndexOutOfBoundsException If the column is negative or greater than the width.
   * @throws ArraySizeException If the size of vals is not the same as the height of the matrix.
   */
  public void insertCol(int col, T[] vals) throws ArraySizeException {
    if (vals.length != this.numRow) {
      throw new ArraySizeException("size of vals is not the same as the width of matrix");
    } else {
      try {
        insertCol(col);
        for (int i = 0; i < this.numRow; i++) {
          this.matrix[i][col] = vals[i];
        } // for
      } catch (IndexOutOfBoundsException e) {
        throw new IndexOutOfBoundsException("The row is negative or greater than the height");
      } // try/catch
    } // if
  } // insertCol(int, T[])

  /**
   * Delete a row.
   *
   * @param row The number of the row to delete.
   * @throws IndexOutOfBoundsException If the row is negative or greater than or equal to the
   *     height.
   */
  @SuppressWarnings("unchecked")
  public void deleteRow(int row) {
    if (row < 0 || row >= this.numRow) {
      throw new IndexOutOfBoundsException("row is negative or greater than the height");
    } else {
      T[][] newMatrix = (T[][]) new Object[this.numRow - 1][this.numCol];
      // Coping the values from old 2d up to the removed row
      for (int i = 0; i < row; i++) {
        System.arraycopy(matrix[i], 0, newMatrix[i], 0, this.numCol);
      } // for loop
      // Copying the array after the removed row
      for (int i = row; i < this.numRow - 1; i++) {
        System.arraycopy(matrix[i + 1], 0, newMatrix[i], 0, this.numCol);
      } // for loop
      // Updating the matrix
      this.matrix = newMatrix;
      --this.numRow;
    } // if
  } // deleteRow(int)

  /**
   * Delete a column.
   *
   * @param col The number of the column to delete.
   * @throws IndexOutOfBoundsException If the column is negative or greater than or equal to the
   *     width.
   */
  @SuppressWarnings("unchecked")
  public void deleteCol(int col) {
    if (col < 0 || col >= this.numCol) {
      throw new IndexOutOfBoundsException("column is negative or greater than the width");
    } else {
      // Initializing a new array
      T[][] newMatrix = (T[][]) new Object[this.numRow][this.numCol - 1];
      // Copying the array and adding a new column
      for (int i = 0; i < this.numRow; i++) {
        for (int j = 0; j < col; j++) {
          newMatrix[i][j] = this.matrix[i][j];
        } // inner for loop
        // Copy elements after the removed column
        for (int j = col; j < this.numCol - 1; j++) {
          newMatrix[i][j] = this.matrix[i][j + 1];
        } // inner for loop
      } // outer for loop
      this.matrix = newMatrix;
      --this.numCol;
    } // if
  } // deleteCol(int)

  /**
   * Fill a rectangular region of the matrix.
   *
   * @param startRow The top edge / row to start with (inclusive).
   * @param startCol The left edge / column to start with (inclusive).
   * @param endRow The bottom edge / row to stop with (exclusive).
   * @param endCol The right edge / column to stop with (exclusive).
   * @param val The value to store.
   * @throw IndexOutOfBoundsException If the rows or columns are inappropriate.
   */
  public void fillRegion(int startRow, int startCol, int endRow, int endCol, T val) {
    if (startRow < 0
        || startCol < 0
        || endRow > this.numRow
        || endCol > this.numCol
        || startRow >= endRow
        || startCol >= endCol) {
      throw new IndexOutOfBoundsException("The rows or columns are inappropriate");
    } else {
      for (int i = startRow; i < endRow; i++) {
        for (int j = startCol; j < endCol; j++) {
          this.matrix[i][j] = val;
        } // inner for loop
      } // outer for loop
    } // if
  } // fillRegion(int, int, int, int, T)

  /**
   * Fill a line (horizontal, vertical, diagonal).
   *
   * @param startRow The row to start with (inclusive).
   * @param startCol The column to start with (inclusive).
   * @param deltaRow How much to change the row in each step.
   * @param deltaCol How much to change the column in each step.
   * @param endRow The row to stop with (exclusive).
   * @param endCol The column to stop with (exclusive).
   * @param val The value to store.
   * @throw IndexOutOfBoundsException If the rows or columns are inappropriate.
   */
  public void fillLine(
      int startRow, int startCol, int deltaRow, int deltaCol, int endRow, int endCol, T val) {
    if (startRow < 0
        || startCol < 0
        || endRow > this.numRow
        || endCol > this.numCol
        || startRow >= endRow
        || startCol >= endCol
        || deltaCol < 0
        || deltaCol > this.numCol
        || deltaRow < 0
        || deltaRow > this.numRow) {
      throw new IndexOutOfBoundsException("The rows or columns are inappropriate");
    } else {
      int i = startRow;
      int j = startCol;
      while (i < endRow && j < endCol) {
        this.matrix[i][j] = val;
        i += deltaRow;
        j += deltaCol;
      } // while
    } // if
  } // fillLine(int, int, int, int, int, int, T)

  /**
   * A make a copy of the matrix. May share references (e.g., if individual elements are mutable,
   * mutating them in one matrix may affect the other matrix) or may not.
   *
   * @return a copy of the matrix.
   */
  public Matrix<T> clone() {
    MatrixV0<T> clonedMatrix = new MatrixV0<>(this.numCol, this.numRow, this.defVal);
    for (int i = 0; i < this.numRow; i++) {
      System.arraycopy(this.matrix[i], 0, clonedMatrix.matrix[i], 0, this.numCol);
    } // for
    return clonedMatrix;
  } // clone()

  /**
   * Determine if this object is equal to another object.
   *
   * @param other The object to compare.
   * @return true if the other object is a matrix with the same width, height, and equal elements;
   *     false otherwise.
   */
  @SuppressWarnings("unchecked")
  public boolean equals(Object other) {
    if (other instanceof MatrixV0) {
      MatrixV0<T> obj = (MatrixV0<T>) other;
      if (this.numRow != obj.numRow || this.numCol != obj.numCol) {
        return false;
      } // if different dimensionas return false
      for (int i = 0; i < this.numRow; i++) {
        for (int j = 0; j < this.numCol; j++) {
          if (!this.matrix[i][j].equals(obj.matrix[i][j])) {
            return false;
          } // if
        } // inner for loop
      } // outer loop
      return true;
    } else {
      return false;
    } // if
  } // equals(Object)

  /**
   * Compute a hash code for this matrix. Included because any object that implements `equals` is
   * expected to implement `hashCode` and ensure that the hash codes for two equal objects are the
   * same.
   *
   * @return the hash code.
   */
  public int hashCode() {
    int multiplier = 7;
    int code = this.width() + multiplier * this.height();
    for (int row = 0; row < this.height(); row++) {
      for (int col = 0; col < this.width(); col++) {
        T val = this.get(row, col);
        if (val != null) {
          // It's okay if the following computation overflows, since
          // it will overflow uniformly.
          code = code * multiplier + val.hashCode();
        } // if
      } // for col
    } // for row
    return code;
  } // hashCode()
} // class MatrixV0
