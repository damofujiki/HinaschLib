package mods.hinasch.lib.network.textmenu;

import mods.hinasch.lib.container.ContainerBase;
import mods.hinasch.lib.core.HSLibGui;
import mods.hinasch.lib.network.PacketGuiButtonBaseNew;
import mods.hinasch.lib.network.PacketGuiButtonHandlerBase;
import net.minecraft.inventory.Container;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketGuiButtonHSLib extends PacketGuiButtonBaseNew{

	public PacketGuiButtonHSLib(){

	}
	public PacketGuiButtonHSLib(HSLibGui.Type guiID, int buttonID) {
		super(guiID,buttonID,null);
	}

	public PacketGuiButtonHSLib(HSLibGui.Type guiID,int buttonID,NBTTagCompound bytearg){
		super(guiID,buttonID,bytearg);
	}
	public static PacketGuiButtonHSLib create(HSLibGui.Type guiID,int buttonID,NBTTagCompound bytearg){
		return new PacketGuiButtonHSLib(guiID,buttonID,bytearg);
	}
	@Override
	public ContainerBase getContainer(Container openContainer, int guiID) {
		// TODO 自動生成されたメソッド・スタブ
		return (ContainerBase) openContainer;
	}

	public static class Handler extends PacketGuiButtonHandlerBase implements IMessageHandler<PacketGuiButtonHSLib,IMessage>{

		@Override
		public IMessage onMessage(PacketGuiButtonHSLib message, MessageContext ctx) {
			return this.onPacketData(message, ctx);
		}
	}
}
