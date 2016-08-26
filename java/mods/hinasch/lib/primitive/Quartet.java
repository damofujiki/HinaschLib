package mods.hinasch.lib.primitive;

public class Quartet<A,B,C,D> extends Triplet<A,B,C>{

	D fourth;

	public D fourth() {
		return fourth;
	}

	public Quartet(A a, B b, C c,D d) {
		super(a, b, c);
		this.fourth = d;
	}

	public static <E, R,W,Q> Quartet<Q,W,E,R> of(Q a,W b,E c,R d){
		return new Quartet(a,b,c,d);
	}
}
