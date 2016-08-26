package mods.hinasch.lib.network;

public abstract class ProxyBase {

	public abstract void registerRenderers();
	public abstract void registerKeyHandlers();
	/** preinitializationでregisterすること*/
	public abstract void registerEntityRenderers();
}
