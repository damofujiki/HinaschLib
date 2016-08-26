package mods.hinasch.lib.primitive;

public class Quintet<A,B,C,D,E> extends Quartet<A,B,C,D>{

	E fifth;
	public E fifth() {
		return fifth;
	}

	public Quintet(A a, B b, C c, D d,E e) {
		super(a, b, c, d);
		this.fifth = e;
		// TODO 自動生成されたコンストラクター・スタブ
	}

	public static <Q,W,E,R,T> Quintet<Q,W,E,R,T> of(Q q,W w,E e,R r,T t){
		return new Quintet(q,w,e,r,t);
	}
}
