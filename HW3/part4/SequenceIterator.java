//
//  ECS140a HW
//  SequenceIterator.java
//
//  Created by Tony Xiao on 2/12/17.
//  Copyright © 2017 TonyXiao. All rights reserved.
//
public class SequenceIterator{
    public Sequence seq;

    public SequenceIterator(){
      seq = null;
    }

    public SequenceIterator(Sequence res){
      this.seq = res;
    }

    public Boolean equal (SequenceIterator other) {
      return this.seq == other.seq;
    }

    public void advance(){
      this.seq = this.seq.next;
    }

    public Element get(){
      return this.seq.element;
    }

    public void Print(){
      (this.seq).Print();
    }
}
