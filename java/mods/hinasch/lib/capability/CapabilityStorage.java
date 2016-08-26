package mods.hinasch.lib.capability;

import mods.hinasch.lib.util.UtilNBT;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;

public abstract class CapabilityStorage<T> implements IStorage<T>{

	@Override
	public NBTBase writeNBT(Capability<T> capability, T instance, EnumFacing side) {
		NBTTagCompound comp =  UtilNBT.createCompound();
		this.writeNBT(comp, capability, instance, side);
		return comp;
	}

	@Override
	public void readNBT(Capability<T> capability, T instance, EnumFacing side, NBTBase nbt) {
		if(nbt instanceof NBTTagCompound){
			this.readNBT((NBTTagCompound)nbt,capability, instance, side);
		}

	}

	public abstract void writeNBT(NBTTagCompound comp,Capability<T> capability, T instance, EnumFacing side);
	public abstract void readNBT(NBTTagCompound comp,Capability<T> capability, T instance, EnumFacing side) ;
}
