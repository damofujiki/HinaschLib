package mods.hinasch.lib.primitive;

@Deprecated
public interface Accumulator<R,T> {



	public abstract  void accumulate(R collector,T in);



}
