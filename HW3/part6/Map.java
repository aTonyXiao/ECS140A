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
      ptr = ptr.next;
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
    Pair ptr = this.firstPair;
    Pair prev = null;

    while(ptr != null && ptr.key.Get() <= inval.key.Get()){
      prev = ptr;
      ptr = ptr.next;
    }
      if(prev == null) // insert at head
        this.firstPair = new Pair(inval.key, inval.val, this.firstPair);
      else
        prev.next = new Pair(inval.key, inval.val, ptr);
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
