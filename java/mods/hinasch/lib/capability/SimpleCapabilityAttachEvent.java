package mods.hinasch.lib.capability;

import com.google.common.base.Predicate;

import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.event.AttachCapabilitiesEvent;

public abstract class SimpleCapabilityAttachEvent<T extends AttachCapabilitiesEvent, K> implements Predicate<T>{

	String modid;
	boolean isSerializable = false;
	IPreAttach preAttach = new IPreAttach<K,T>(){

		@Override
		public void preAttach(K instance, Capability<K> capability, EnumFacing facing, T ev) {
			// TODO 自動生成されたメソッド・スタブ

		}





	};
	public SimpleCapabilityAttachEvent(String modid,boolean serialize){
		this.modid = modid;
		this.isSerializable = serialize;

	}

	public SimpleCapabilityAttachEvent(String modid,boolean serialize,IPreAttach preAttach){
		this(modid,serialize);
		if(preAttach!=null){
			this.preAttach = preAttach;
		}

	}


	/**
	 *
	 *
	 * @param <T> capability interface
	 * @param <K> event
	 */
	public static interface IPreAttach<T,K extends AttachCapabilitiesEvent>{
		public void preAttach(T instance,Capability<T> capability, EnumFacing facing,K ev);
	}
	public abstract String getName();

	public abstract Capability<K> getCapabilityInstance();
}
