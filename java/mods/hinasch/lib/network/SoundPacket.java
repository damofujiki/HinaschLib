package mods.hinasch.lib.network;

import mods.hinasch.lib.core.HSLib;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;

public class SoundPacket {

	public static void sendTo(PacketSound packet,EntityPlayerMP player){
		HSLib.getPacketDispatcher().sendTo(packet, player);
	}

	public static void sendToAll(PacketSound packet){
		HSLib.getPacketDispatcher().sendToAll(packet);
	}

	public static void sendToAllAround(PacketSound packet,TargetPoint target){
		HSLib.getPacketDispatcher().sendToAllAround(packet, target);
	}

	public static void sendToDimension(PacketSound packet,int dimensionId){
		HSLib.getPacketDispatcher().sendToDimension(packet, dimensionId);
	}

	public static void sendToServer(PacketSound packet){
		HSLib.getPacketDispatcher().sendToServer(packet);
	}
}
