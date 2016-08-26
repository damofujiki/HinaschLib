package mods.hinasch.lib.primitive;

import java.util.function.Function;
import java.util.function.Supplier;

public class FunctionCall {



	public static <T> Supplier<T> supplier(Supplier<T> s){
		return s;
	}

	public static <T,K> Function<T,K> function(Function<T,K> f){
		return f;
	}
}
