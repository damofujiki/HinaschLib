package mods.hinasch.lib.primitive;

public class Tuple<T,V> {

	public final T first;
	public final V second;

	public Tuple(T f,V s){
		this.first = f;
		this.second = s;
	}

	public static <A,B> Tuple<A,B> of(A a,B b){
		return new Tuple(a,b);
	}


}
