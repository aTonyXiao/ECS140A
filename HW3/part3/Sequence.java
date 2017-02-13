//
//  ECS140a HW
//  Sequence.java
//
//  Created by Tony Xiao on 2/12/17.
//  Copyright © 2017 TonyXiao. All rights reserved.
//

public class Sequence extends Element{
	Element element;
	Sequence next;
	int length;
	// int length = 0;

	public Sequence(){
		length = 1;
	}

	public Sequence(Element element){
		this.element = element;
		this.length = 1;
	}

	public Sequence(Element element, Sequence next){
		this.element = element;
		this.next = next;
		this.length = 1;
	}

	public void Print(){
		System.out.print("[");
		for(Sequence ptr = this; ptr !=null; ptr = ptr.next){
			System.out.print(" ");
			ptr.element.Print();
		}
		System.out.print(" ]");
	}
	// its a singel linked list,
	//get the first element should I change it to 'double' linkedlist?
	// might look back at this later
	public Element first(){
		return this.element;
	}

	public Sequence rest(){
		return this.next;
	}

	public int length(){
		int i = 0;
		Sequence ptr = this;
		while(ptr != null && ptr.element != null)
		{
			i++;
			ptr = ptr.next;
		}
		return i;
		// return length;
	}

	public void add(Element elm, int pos){
		Sequence ptr = this;
		Sequence prev = null;

		// empty Sequence
		// if(ptr.element == null)
		// {
		// 	this.element = elm;
		// 	return;
		// }
		// not empty
		if(pos < 0 || pos > this.length())
			System.err.print("Add out of Boundary");


		for(int i = 0; ptr != null && i < pos; i++){
			prev = ptr;
			ptr = ptr.next;
		}

		//insert head
		if(prev == null){
			if(ptr.element != null)
				this.next = new Sequence(this.element, this.next);
				this.element = elm;
				// this.length ++;
		}else{
			prev.next = new Sequence(elm, ptr);
			// this.length ++;
		}

		this.length ++;
		// if(ptr == null){
		// 	prev.next = new Sequence(elm);
		// }else{
		// 	prev.next = new Sequence(elm, ptr);
		// }
	}



	public void delete(int pos){
		Sequence ptr = this;
		Sequence prev = null;

		if(pos < 0 || pos > this.length)
			System.err.print("delete out of Boundary");


		for(int i = 0; ptr != null && i < pos; i++){
			prev = ptr;
			ptr = ptr.next;
		}
		//i not reach pos
		if(ptr == null){
			return;
		}else{
			if(prev == null) // delete the head
			{
				this.element = this.next.element;
				this.next = this.next.next;
			}else{
				prev.next = ptr.next;
			}
		}
		this.length --;
	}


	public Element index(int pos){
		Sequence ptr = this;

		if(pos < 0 || pos > ptr.length())
			System.err.print("index out of Boundary");

		for(int i = 0; i < pos; ptr = ptr.next, i++);

		// if(ptr != null)
		return ptr.element;
	}

	public Sequence flatten(){
		Sequence res = new Sequence();
		Sequence ptr = this;

		while(ptr != null && ptr.element != null){
			if(ptr.element instanceof Sequence){//recursive call flatten
				Sequence nptr = ((Sequence)(ptr.element)).flatten();
				while(nptr != null){
					res.add(nptr.element, res.length());
					nptr = nptr = nptr.next;
				}
			}else{
				res.add(ptr.element,res.length());
			}
			ptr = ptr.next;
		}
		return res;
	}

	public Sequence copy(){
		Sequence res = new Sequence();
		Sequence ptr = this;
		while(ptr!= null && ptr.element != null)
		{
			if(ptr.element instanceof MyInteger){// is an int
				MyInteger tmp = new MyInteger((MyInteger)ptr.element);
				res.add(tmp, res.length());
			}else if(ptr.element instanceof MyChar){// element is a char
				MyChar tmp = new MyChar((MyChar)ptr.element);
				res.add(tmp, res.length());
			}else if(ptr.element instanceof Sequence){//recursive call deep copy
				res.add(((Sequence)ptr.element).copy(), res.length());
			}
			ptr = ptr.next;
		}
		return res;
	}
}
