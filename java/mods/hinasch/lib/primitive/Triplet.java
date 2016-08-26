package mods.hinasch.lib.primitive;

public class Triplet<A,B,C> extends Tuple<A,B>{

	public Triplet(A a,B b,C c) {
		super(a,b);
		this.third = c;
		// TODO 自動生成されたコンストラクター・スタブ
	}

	C third;

	public C third() {
		return third;
	}

	public static <Q,W,E> Triplet<Q,W,E> of(Q q,W w,E e){
		return new Triplet(q,w,e);
	}
}
