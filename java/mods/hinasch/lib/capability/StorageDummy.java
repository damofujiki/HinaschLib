package mods.hinasch.lib.capability;

import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;

public class StorageDummy<T> implements IStorage<T>{

	@Override
	public NBTBase writeNBT(Capability<T> capability, T instance, EnumFacing side) {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	public void readNBT(Capability<T> capability, T instance, EnumFacing side, NBTBase nbt) {
		// TODO 自動生成されたメソッド・スタブ

	}

}
