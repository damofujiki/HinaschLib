package mods.hinasch.lib.network;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;

public abstract class AbstractDispatcherAdapter<T extends IMessage> {

	public abstract SimpleNetworkWrapper getDispatcher();

	public void sendToAll(T message){
		this.getDispatcher().sendToAll(message);
	}

	public void sendToPlayer(T message,EntityPlayerMP player){
		this.getDispatcher().sendTo(message, player);
	}

	public void sendToAllAround(T message,TargetPoint point){
		this.getDispatcher().sendToAllAround(message, point);
	}

	public void sendToServer(T message){
		this.getDispatcher().sendToServer(message);
	}

	public void sendToDimension(T message,int dimid){
		this.getDispatcher().sendToDimension(message,dimid);
	}
}
