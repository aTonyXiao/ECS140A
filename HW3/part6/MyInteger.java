//
//  ECS140a HW
//  MyInteger.java
//
//  Created by Tony Xiao on 2/12/17.
//  Copyright © 2017 TonyXiao. All rights reserved.
//
public class MyInteger extends Element{
	private int val;

	public MyInteger(){
		val = 0;
	}

	public MyInteger(MyInteger res){
		val = res.Get();
	}

	public int Get(){
		return val;
	}

	public void Set(int val){
		this.val = val;
	}

	public void Print(){
		System.out.print(val);
	}

}
