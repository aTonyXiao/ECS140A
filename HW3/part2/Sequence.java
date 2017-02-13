public class Sequence extends Element{
	Element element;
	Sequence next;
	// int length = 0;

	public Sequence(){
	}

	public Sequence(Element element){
		this.element = element;
	}

	public Sequence(Element element, Sequence next){
		this.element = element;
		this.next = next;
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
		for(Sequence ptr = this; ptr != null && ptr.element != null; ptr = ptr.next)
			i++;

		return i;
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
		for(int i = 0; ptr != null && i < pos; i++){
			prev = ptr;
			ptr = ptr.next;
		}

		//insert head
		if(prev == null){
			if(ptr.element != null)
				this.next = new Sequence(this.element, this.next);
				this.element = elm;
		}else{
			prev.next = new Sequence(elm, ptr);
		}
		// if(ptr == null){
		// 	prev.next = new Sequence(elm);
		// }else{
		// 	prev.next = new Sequence(elm, ptr);
		// }
	}



	public void delete(int pos){
		Sequence ptr = this;
		Sequence prev = null;

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
	}

}
