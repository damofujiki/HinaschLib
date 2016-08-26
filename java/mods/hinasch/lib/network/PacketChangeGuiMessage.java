package mods.hinasch.lib.network;

import io.netty.buffer.ByteBuf;
import mods.hinasch.lib.client.ClientHelper;
import mods.hinasch.lib.client.GuiContainerBase;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketChangeGuiMessage implements IMessage{

	String string;


	public PacketChangeGuiMessage(){

	}

	public PacketChangeGuiMessage(String message){
		this.string = message;

	}

	public static PacketChangeGuiMessage create(String mes){
		return new PacketChangeGuiMessage(mes);
	}
	@Override
	public void fromBytes(ByteBuf buf) {
		this.string = PacketUtil.readStringFromByteBuf(buf);



	}

	@Override
	public void toBytes(ByteBuf buf) {
		PacketUtil.writeStringtoByteBuf(buf, string);

	}

	public static class Handler implements IMessageHandler<PacketChangeGuiMessage,IMessage>{

		@Override
		public IMessage onMessage(PacketChangeGuiMessage message, MessageContext ctx) {
			if(ctx.side.isClient()){
				if(ClientHelper.getCurrentGui() instanceof GuiContainerBase){
					GuiContainerBase gui = (GuiContainerBase) ClientHelper.getCurrentGui();

					gui.setMessage(message.string);
				}
			}
			return null;
		}

	}
}
