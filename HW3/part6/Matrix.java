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
      for(int i = 0; i < rowsize * colsize; i++)
      {
        MyInteger tmp = new MyInteger();
        ((Sequence)matrix).add(tmp, i);
      }
    }

    //2 dimensional array in memory storage for M*N matrix
    //row major: i*N+j
    public void Set(int rowsize, int colsize, int value){
      ((MyInteger)((this.matrix).index(rowsize * this.col + colsize))).Set(value);
    } // set the value of an element

    public int Get(int rowsize, int colsize){
      return ((MyInteger)((this.matrix).index(rowsize * this.col + colsize))).Get();
    } // get the value of an element

    public Matrix Sum(Matrix mat){
      Matrix result = new Matrix(row, col);
      for(int i = 0; i < row; i++)
        for(int j = 0; j < col; j++)
          result.Set(i, j, this.Get(i, j) + mat.Get(i, j));

      return result;
    } // return the sum of two matrices: mat & this

    public Matrix Product(Matrix mat){
      if(col != mat.row){
        System.out.println("Matrix dimensions incompatible for Product");
        System.exit(0);
      }

      // Matrix result = new Matrix(row, col);
      Matrix result = new Matrix(this.row, mat.col);
      int productSum = 0;

      for(int i = 0; i < row; i++){
        for(int j = 0; j < mat.col; j++){
          for(int k = 0; k < mat.row; k++){
            productSum += this.Get(i, k) * mat.Get(k, j);
          }// k
        result.Set(i, j, productSum);
        productSum = 0;
        }// j
      }//i
      return result;
    } // return the product of two matrices: mat & this

    public void Print(){
      int count = 0;
      int flag = 0;
      Sequence ptr = matrix;

      while(ptr != null && ptr.element != null && flag < this.col * this.row){
        if(count == 0){
          System.out.print("[ ");
        }
        ptr.element.Print();
        flag ++;
        System.out.print(" ");
        count ++;
        if(count == this.col){
          System.out.println("]");
          count = 0;
        }

        ptr = ptr.next;
      }
    }
}
