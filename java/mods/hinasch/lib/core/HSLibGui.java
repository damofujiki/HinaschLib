package mods.hinasch.lib.core;

import mods.hinasch.lib.client.IGuiAttribute;
import mods.hinasch.lib.container.ContainerTextMenu;
import mods.hinasch.lib.util.HSLibs;
import mods.hinasch.lib.world.XYZPos;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class HSLibGui {

	public static enum Type implements IGuiAttribute{
		TEXTMENU(0);

		int id;

		public static Type fromMeta(int meta){
			return HSLibs.fromMeta(Type.values(), meta);
		}
		private Type(int id){
			this.id = id;
		}

		@Override
		public int getMeta() {
			// TODO 自動生成されたメソッド・スタブ
			return id;
		}

		@Override
		public Container getContainer(World world, EntityPlayer player, XYZPos pos) {
			switch(this){
			case TEXTMENU:
//				ITextMenuAdapter adapter = new AbilityTextMenuAdapter();
				return new ContainerTextMenu(player);
			default:
				break;

			}
			return null;
		}

		@Override
		public GuiContainer getGui(World world, EntityPlayer player, XYZPos pos) {
			switch(this){
			case TEXTMENU:
//				ITextMenuAdapter adapter = new AbilityTextMenuAdapter();
//				return new GuiTextMenu(player, adapter);
			default:
				break;

			}
			return null;
		}
		@Override
		public void onGuiOpen(mods.hinasch.lib.network.PacketOpenGui pgo, MessageContext ctx, EntityPlayer ep) {
			// TODO 自動生成されたメソッド・スタブ

		}


	}
}
