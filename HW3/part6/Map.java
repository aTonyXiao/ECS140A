//
//  ECS140a HW
//  Map.java
//
//  Created by Tony Xiao on 2/13/17.
//  Copyright © 2017 TonyXiao. All rights reserved.
//


public class Map extends Element{
  public Pair firstPair;

  public Map(){}

  public void Print(){
    System.out.print("[");
    Pair ptr = firstPair;

    while(ptr != null){
      System.out.print(" ");
      ptr.Print();
    }
    System.out.print(" ]");
  }

  public MapIterator begin(){
    return new MapIterator(firstPair);
  }

  public MapIterator end(){
    return new MapIterator();
  }

  public void add(Pair inval){
    Pair ptr = firstPair;
    Pair prev = null;

    while(ptr != null && ptr.key.Get() <= inval.key.Get()){
      prev = ptr;

      if(prev == null) // insert at head
        firstPair = new Pair(inval.key, inval.val, firstPair);
      else
        prev.next = new Pair(inval.key, inval.val, ptr);

      ptr = ptr.next;
    }
  }

  public MapIterator find(MyChar key){
    // MapIterator it;
    Pair ptr = this.firstPair;
    while(ptr != null && (ptr.key).Get() != key.Get()){
      ptr = ptr.next;
    }

    return new MapIterator(ptr);
    // return null;
  }

}

class Pair extends Element{
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
