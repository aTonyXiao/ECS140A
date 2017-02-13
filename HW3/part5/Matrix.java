//
//  ECS140a HW
//  Matrix.java
//
//  Created by Tony Xiao on 2/12/17.
//  Copyright © 2017 TonyXiao. All rights reserved.
//
public class Matrix extends Sequence{
    // constructor for creating a matrix of specific number of rows and columns
    public Sequence matrix;
    public int row;
    public int col;

    public Matrix(int rowsize, int colsize){//initallize
      this.row = rowsize;
      this.col = colsize;

      for(int i = 0; i < rowsize * colsize; i++)
      {
        matrix.add(new MyInteger(), i);
      }

      // matrix = new Sequence(new MyInteger());
      // Sequence ptr = this.matrix;
      // for(int i = 1; i < rowsize * colsize; i++)
      // {
      //   ptr.next = new Sequence(new MyInteger());
      //   ptr = ptr.next;
      // }


      // System.out.print(matrix.length());

      // Sequence ptr;
      // matrix = new Sequence();
      // Sequence ptr = matrix;
      // Sequence prev = ptr;
      //
      // for(int i = 0; i < rowsize; i++){
      //   ptr.element = new Sequence(new MyInteger());
      //   prev = ptr;
      //
      //   for(int j = 1; j < colsize; j++){
      //     ((Sequence)ptr.element).add(new MyInteger, j);
      //     // prev = prev.next;
      //   }
      //
      //
      //   ptr = ptr.next;
      //   ptr = new Sequence();
      //
      //
      //
      // }
    }

    //2 dimensional array in memory storage for M*N matrix
    //row major: i*N+j
    public void Set(int rowsize, int colsize, int value){
      ((MyInteger)(this.matrix).index(rowsize * this.row + colsize)).Set(value);
    } // set the value of an element

    public int Get(int rowsize, int colsize){
      return ((MyInteger)(this.matrix).index(rowsize * this.row + colsize)).Get();
    } // get the value of an element

    public Matrix Sum(Matrix mat){
      Matrix result = new Matrix(row, col);
      for(int i = 0; i < row; i++)
        for(int j = 0; j < col; j++)
          result.Set(i, j, this.Get(i, j) + mat.Get(i, j));

      return result;
    } // return the sum of two matrices: mat & this

    public Matrix Product(Matrix mat); // return the product of two matrices: mat & this
    public void Print(){
      Sequence ptr = matrix;
      while(ptr != null && ptr.element != null)
      {
        ((Sequence)(ptr.element)).Print();
      }
    }  // print the elements of matrix
}
