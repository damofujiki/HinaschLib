package mods.hinasch.lib.capability;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;

public class CapabilityAdapterFactory {


	final String modid;
	public CapabilityAdapterFactory(String modid){
		this.modid = modid;
	}

	public <T> CapabilityAdapterBase create(ICapabilityAdapter adapter){
		return new CapabilityAdapterBase<T>(this,adapter);
	}

	public static interface ICapabilityAdapter<T>{
		public Capability<T> getCapability();


		public Class<T> getCapabilityClass();


		public Class<? extends T> getDefault();


		public IStorage<T> getStorage();
	}
}
