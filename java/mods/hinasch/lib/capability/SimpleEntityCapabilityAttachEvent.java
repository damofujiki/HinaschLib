package mods.hinasch.lib.capability;

import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public abstract class SimpleEntityCapabilityAttachEvent<K> extends  SimpleCapabilityAttachEvent<AttachCapabilitiesEvent.Entity,K>{

	public SimpleEntityCapabilityAttachEvent(String modid, boolean serialize) {
		super(modid, serialize);
		// TODO 自動生成されたコンストラクター・スタブ
	}

	public SimpleEntityCapabilityAttachEvent(String modid, boolean serialize, IPreAttach preAttach) {
		super(modid, serialize, preAttach);
		// TODO 自動生成されたコンストラクター・スタブ
	}


	@SubscribeEvent
	public void attach(final AttachCapabilitiesEvent.Entity ev){

		final Capability<K> capa = this.getCapabilityInstance();

		if(this.apply(ev)){
			if(this.isSerializable){
				ev.addCapability(new ResourceLocation(modid,this.getName()), new ICapabilitySerializable<NBTBase>(){

					K inst = capa.getDefaultInstance();
					@Override
					public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
						// TODO 自動生成されたメソッド・スタブ
						return capa==capability && capa!=null;
					}

					@Override
					public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
						if(capa==capability && capa!=null){
							preAttach.preAttach((T)inst, capability, facing,ev);
							return (T)inst;
						}
						return null;
					}

					@Override
					public NBTBase serializeNBT() {
						// TODO 自動生成されたメソッド・スタブ
						return capa.getStorage().writeNBT(capa, inst, null);
					}

					@Override
					public void deserializeNBT(NBTBase nbt) {
						// TODO 自動生成されたメソッド・スタブ
						capa.getStorage().readNBT(capa, inst, null, nbt);
					}}
				);
			}else{
				ev.addCapability(new ResourceLocation(modid,this.getName()), new ICapabilityProvider(){
					K inst = capa.getDefaultInstance();
					@Override
					public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
						// TODO 自動生成されたメソッド・スタブ
						return capa==capability && capa!=null;
					}

					@Override
					public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
						if(capa==capability && capa!=null){
							preAttach.preAttach((T)inst, capability, facing,ev);
							return (T)inst;
						}
						return null;
					}

				});
			}

		}
	}

}
