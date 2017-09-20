package mods.hinasch.lib.client;

import mods.hinasch.lib.iface.IIntSerializable;
import mods.hinasch.lib.network.PacketOpenGui;
import mods.hinasch.lib.world.XYZPos;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public interface IGuiAttribute extends IIntSerializable{

	public Container getContainer(World world,EntityPlayer player,XYZPos pos);
	public GuiContainer getGui(World world,EntityPlayer player,XYZPos pos);
	public void onGuiOpen(PacketOpenGui pgo,MessageContext ctx,EntityPlayer ep );
}
