package mods.hinasch.lib.debuff;

import mods.hinasch.lib.core.HSLib;
import mods.hinasch.lib.debuff.capability.ICustomDebuff;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EventAttachDebuff {

	@SubscribeEvent
	public void onEntityConstruct(AttachCapabilitiesEvent.Entity e){

		if(e.getEntity() instanceof EntityLivingBase){

			e.addCapability(new ResourceLocation(HSLib.MODID,"debuffs"), new ICapabilitySerializable<NBTBase>(){

				ICustomDebuff inst = HSLib.CAP_DEBUFF.getDefaultInstance();
				@Override
				public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
					// TODO 自動生成されたメソッド・スタブ
					return HSLib.CAP_DEBUFF !=null && capability == HSLib.CAP_DEBUFF;
				}

				@Override
				public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
					if(HSLib.CAP_DEBUFF !=null && capability == HSLib.CAP_DEBUFF)return (T)inst;
					return null;
				}

				@Override
				public NBTBase serializeNBT() {

					return (NBTBase)HSLib.CAP_DEBUFF.getStorage().writeNBT(HSLib.CAP_DEBUFF, inst, null);
				}

				@Override
				public void deserializeNBT(NBTBase nbt) {
					HSLib.CAP_DEBUFF.getStorage().readNBT(HSLib.CAP_DEBUFF, inst, null, nbt);

				}}
			);
		}
	}
}
