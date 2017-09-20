package mods.hinasch.lib.capability;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;


public class CapabilityAdapterFactory {


	final String modid;
	public static CapabilityAdapterFactory create(String modid){
		return new CapabilityAdapterFactory(modid);
	}

	public CapabilityAdapterFactory(String modid){
		this.modid = modid;
	}

	public <T> CapabilityAdapterFrame create(ICapabilityAdapterPlan adapter){
		return new CapabilityAdapterFrame<T>(this,adapter);
	}

	/**
	 *
	 * CapabilityAdapterFactoryからCapabilityAdapterFrameを
	 * 作る時に必要
	 *
	 *@param <T> capability interface
	 */
	public static interface ICapabilityAdapterPlan<T>{
		public Capability<T> getCapability();


		public Class<T> getCapabilityClass();


		public Class<? extends T> getDefault();


		public IStorage<T> getStorage();
	}
}
