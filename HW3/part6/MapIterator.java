//
//  ECS140a HW
//  MapIterator.java
//
//  Created by Tony Xiao on 2/13/17.
//  Copyright © 2017 TonyXiao. All rights reserved.
//
public class MapIterator extends Element{
  public Pair pair;

  public MapIterator(){
    pair = null;
  }

  public MapIterator(Pair res){
    this.pair = res;
  }

  public MapIterator(MapIterator res){
    this.pair = res.pair;
  }

  public Boolean equal(MapIterator other){
    return this.pair.key.Get() == other.pair.key.Get();
  }

  public void advance(){
    this.pair = this.pair.next;
  }

  public Pair get(){
    return this.pair;
  }

  public void Print(){
    this.pair.Print();
  }
}
