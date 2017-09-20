package mods.hinasch.lib.container;

import mods.hinasch.lib.client.GuiTextMenu;
import mods.hinasch.lib.core.HSLib;
import mods.hinasch.lib.core.HSLibGui;
import mods.hinasch.lib.network.PacketGuiButtonBaseNew;
import mods.hinasch.lib.network.textmenu.PacketGuiButtonHSLib;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;

public class ContainerTextMenu extends ContainerBase{

	public ContainerTextMenu(EntityPlayer ep) {
		super(ep, null);
		// TODO 自動生成されたコンストラクター・スタブ
	}

	@Override
	public PacketGuiButtonBaseNew getPacketGuiButton(int guiID, int buttonID, NBTTagCompound args) {
		// TODO 自動生成されたメソッド・スタブ
		return PacketGuiButtonHSLib.create(HSLibGui.Type.fromMeta(guiID), buttonID,args);
	}
	@Override
	public boolean canInteractWith(EntityPlayer var1) {
		// TODO 自動生成されたメソッド・スタブ
		return true;
	}
	@Override
	public SimpleNetworkWrapper getPacketPipeline() {
		// TODO 自動生成されたメソッド・スタブ
		return HSLib.core().getPacketDispatcher();
	}

	@Override
	public int getGuiID() {
		// TODO 自動生成されたメソッド・スタブ
		return HSLibGui.Type.TEXTMENU.getMeta();
	}

	@Override
	public void onPacketData() {
		if(this.buttonID==GuiTextMenu.BUTTON_DOWN_ID){
			HSLib.logger.trace("gui", "down");
		}
		if(this.buttonID==GuiTextMenu.BUTTON_UP_ID){
			HSLib.logger.trace("gui", "up");
		}
	}

}
