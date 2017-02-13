//
//  ECS140a HW
//  MyChar.java
//
//  Created by Tony Xiao on 2/12/17.
//  Copyright © 2017 TonyXiao. All rights reserved.
//
public class MyChar extends Element{
	private char val;

	public MyChar(){
		val = '0';
	}

	public MyChar(MyChar res){//copy constructor
		val = res.Get();
	}

	public char Get(){
		return val;
	}

	public void Set(char val){
		this.val = val;
	}

	public void Print(){
		System.out.print("'" + val + "'");
	}
}
