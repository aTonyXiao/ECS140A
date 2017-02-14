//
//  ECS140a HW
//  Pair.java
//
//  Created by Tony Xiao on 2/13/17.
//  Copyright © 2017 TonyXiao. All rights reserved.
//
public class Pair extends Element{
  public MyChar key;
  public Element val;
  public Pair next;

  public Pair(){}

  public Pair(MyChar key, Element val){
      this.key = key;
      this.val = val;
  }

  public Pair(MyChar key, Element val, Pair next){
    this.key = key;
    this.val = val;
    this.next = next;
  }

  public void Print(){
    System.out.print("(");
    this.key.Print();
    System.out.print(" ");
    this.val.Print();
    System.out.print(")");
  }
}
