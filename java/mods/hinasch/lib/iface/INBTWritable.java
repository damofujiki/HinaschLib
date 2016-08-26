package mods.hinasch.lib.iface;

import net.minecraft.nbt.NBTTagCompound;

public interface INBTWritable {

	public void writeToNBT(NBTTagCompound stream);
}
