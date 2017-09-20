package mods.hinasch.lib.network;

import io.netty.buffer.ByteBuf;
import mods.hinasch.lib.client.ClientHelper;
import mods.hinasch.lib.client.GuiContainerBase;
import mods.hinasch.lib.container.ContainerBase;
import mods.hinasch.lib.core.HSLib;
import mods.hinasch.lib.util.UtilNBT;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketSendGuiInfoToClient implements IMessage{

	NBTTagCompound comp;
	boolean isRequest = false;

	public static PacketSendGuiInfoToClient create(NBTTagCompound comp){
		return new PacketSendGuiInfoToClient(comp,false);
	}

	public static PacketSendGuiInfoToClient request(){

		return new PacketSendGuiInfoToClient(UtilNBT.createCompound(),true);
	}
	public PacketSendGuiInfoToClient(){


	}

	public PacketSendGuiInfoToClient(NBTTagCompound comp,boolean par1){
		this.isRequest = par1;
		this.comp = comp;
	}

	public NBTTagCompound getArgs(){
		return this.comp;
	}
	@Override
	public void fromBytes(ByteBuf buf) {
		int len = buf.readInt();
		if(len>0){
			ByteBuf bytes = buf.readBytes(len);
			this.comp = PacketUtil.bytesToNBT(bytes);
			this.isRequest = this.comp.getBoolean("isRequest");
		}


	}

	@Override
	public void toBytes(ByteBuf buf) {

		this.comp.setBoolean("isRequest", this.isRequest);
		byte[] bytes = PacketUtil.nbtToBytes(comp);
		buf.writeInt(bytes.length);
		buf.writeBytes(bytes);



	}

	public static class Handler implements IMessageHandler<PacketSendGuiInfoToClient,IMessage>{

		@Override
		public IMessage onMessage(PacketSendGuiInfoToClient message, MessageContext ctx) {

			if(ctx.side.isClient()){
				if(ClientHelper.getCurrentGui() instanceof GuiContainerBase){
					GuiContainerBase gui = (GuiContainerBase) ClientHelper.getCurrentGui();
					gui.onPacketFromServer(message.getArgs());
				}
			}
			if(ctx.side.isServer()){
				HSLib.logger.trace(this.getClass().getName(), "リクエスト受け付け");
				EntityPlayer ep = ctx.getServerHandler().playerEntity;
				if(ep.openContainer instanceof ContainerBase){
//					return null;
						return ((ContainerBase)ep.openContainer).getSyncPacketToClient(ep);
				}
			}
			return null;
		}
	}
}
