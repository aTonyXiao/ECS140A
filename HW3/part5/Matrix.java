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
      matrix = new Sequence(new MyInteger());
      Sequence ptr = this.matrix;
      for(int i = 1; i < rowsize * colsize; i++)
      {
        ptr.next = new Sequence(new MyInteger());
        ptr = ptr.next;
      }



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
    // void Set(int rowsize, int colsize, int value); // set the value of an element
    // int Get(int rowsize, int colsize); // get the value of an element
    // Matrix Sum(Matrix mat); // return the sum of two matrices: mat & this
    // Matrix Product(Matrix mat); // return the product of two matrices: mat & this
    // void Print();  // print the elements of matrix
}
