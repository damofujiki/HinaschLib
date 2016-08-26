package mods.hinasch.lib.network;

import mods.hinasch.lib.container.ContainerBase;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.Container;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;



public class PacketGuiButtonHandlerBase {

	public PacketGuiButtonHandlerBase(){

	}
	public IMessage onPacketData(PacketGuiButtonBaseNew message, MessageContext ctx) {
		if(ctx.side.isServer()){

			EntityPlayerMP player = ctx.getServerHandler().playerEntity;
			Container epContainer = player.openContainer;
			//message.getContainer(epContainer, message.getGuiID());
			if(epContainer instanceof ContainerBase){
				ContainerBase container = (ContainerBase) epContainer;
				container.readPacketData(message.getButtonID(),message.getArgs());
				container.onPacketData();
			}
		}
		return null;
	}
}
