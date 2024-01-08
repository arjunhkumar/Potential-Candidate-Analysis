
/**
 * @author arjun
 *
 */
public class A {

	public int i1 = 45;
	public boolean z1 = true;
	
	public static void main(String args[]) {
		A a = new A();
		a.computeVal();
		B b = new B();
		b.computeVal();
		C c = new C();
		c.computeVal();
		D d = new D();
		d.computeVal();
	}
	
	public int computeVal() {
		if(z1) {
			return i1;
		}
		return 0;
	}
	
}


class B {

	public int i2;
	public A v1;
	
	public int computeVal() {
		this.v1 = new A();
		return v1.computeVal();
	}
	
}

class C {
	public A v2;
	
	public int computeVal() {
		this.v2 = new A();
		return v2.computeVal();
	}
}

class D {
	public boolean z2;
	public A v3;
	
	public int computeVal() {
		if(z2) {
			this.v3 = new A();
			return v3.computeVal();
		}
		return 0;
	}
}