package mods.hinasch.lib.capability;

import mods.hinasch.lib.network.PacketSyncCapability;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public interface ISyncCapability {

	/**
	 * 渡すNBTを返す。StorageのwriteNBTを使って返すなど。
	 * @return
	 */
	public NBTTagCompound getSendingData();
	/**
	 * これだけでは呼ばれない。
	 * onPacketの補助として各自使う。
	 * ここにStorageのreadNBTをわけるなどする。
	 * @param nbt
	 */
	public void catchSyncData(NBTTagCompound nbt);
	/**
	 * パケットを受信した時に呼ばれる。
	 * @param message
	 * @param ctx
	 */
	public void onPacket(PacketSyncCapability message,MessageContext ctx);
	/**
	 * 特有のSTRINGIDを返す。
	 * @return
	 */
	public String getIdentifyName();
}
