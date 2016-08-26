package mods.hinasch.lib.debuff.capability;

import io.netty.util.internal.ConcurrentSet;
import mods.hinasch.lib.capability.CapabilityStorage;
import mods.hinasch.lib.core.HSLib;
import mods.hinasch.lib.debuff.DebuffEffectBase;
import mods.hinasch.lib.util.UtilNBT;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;

public class StorageICustomDebuff extends CapabilityStorage<ICustomDebuff>{

	private static final String KEY = "debuffs";


	/**
	 * デバフ効果の復元。ワールドロード時に使う
	 * @param strList
	 * @return
	 */
	public ConcurrentSet<DebuffEffectBase> restoreDebuffsFromNBTTagList(NBTTagList tagList){

//		UnsagaMod.logger.trace("restoring..."+this.getClass().getName());
		ConcurrentSet<DebuffEffectBase> output = new ConcurrentSet();
		for(int i=0;i<tagList.tagCount();i++){
			NBTTagCompound child = tagList.getCompoundTagAt(i);
			DebuffEffectBase ld = DebuffEffectBase.buildFromNBT(child);
			if(ld!=null){
				output.add(ld);
			}

			//Unsaga.debug("復元中",ld.getDebuff());
		}
//		for(String str:strList){
//			output.add(LivingDebuff.buildFromString(str));
//		}
		return output;
	}

	@Override
	public void writeNBT(NBTTagCompound nbt, Capability<ICustomDebuff> capability, ICustomDebuff instance,
			EnumFacing side) {
		if(!instance.getDebuffs().isEmpty()){
//			HSLib.logger.trace("saving debuffs...");

			UtilNBT.writeListToNBT(instance.getDebuffs(), nbt, KEY);

		}
		nbt.setBoolean("stopping", instance.isTimeStopping());
	}

	@Override
	public void readNBT(NBTTagCompound compound, Capability<ICustomDebuff> capability, ICustomDebuff instance,
			EnumFacing side) {
		if(compound.hasKey(KEY)){
			HSLib.logger.trace("restoreing debuffs...");
			instance.setDebuffs(restoreDebuffsFromNBTTagList(compound.getTagList(KEY, UtilNBT.NBTKEY_COMPOUND)));
			return;
		}
		instance.setTimeStop(compound.getBoolean("stopping"));
	}

}
