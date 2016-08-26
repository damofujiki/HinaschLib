package mods.hinasch.lib.capability;

import mods.hinasch.lib.network.PacketSyncCapability;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public interface ISyncCapability {

	public NBTTagCompound getSendingData();
	public void catchSyncData(NBTTagCompound nbt);
	public void onPacket(PacketSyncCapability message,MessageContext ctx);
	public String getIdentifyName();
}
