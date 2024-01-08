/**
 * @author arjun
 *
 */
public class E {

	public E v1;
	
	public static void main(String args[]) {
		E e = new E();
		e.computeVal();
		F f = new F();
		f.computeVal();
		G g = new G();
		g.computeVal();
	}
	
	public int computeVal() {
		this.v1 = new E();
		return 0;
	}
}

class F {

	public int i1 = 45;
	public F v2;
	
	public int computeVal() {
		return i1;
	}
	
}

class G {

	public F v3;
	
	public int computeVal() {
		return 78;
	}
	
}
