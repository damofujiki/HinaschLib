package mods.hinasch.lib.core;

import mods.hinasch.lib.capability.CapabilityInventory;
import mods.hinasch.lib.capability.VillagerHelper;
import mods.hinasch.lib.config.EventConfigChanged;
import mods.hinasch.lib.core.event.EventEntityJoinWorld;
import mods.hinasch.lib.core.event.EventKeyBinding;
import mods.hinasch.lib.debuff.EventAttachDebuff;
import mods.hinasch.lib.debuff.EventLivingUpdateDebuff;
import mods.hinasch.lib.network.ProxyBase;
import mods.hinasch.lib.util.HSLibs;
import mods.hinasch.lib.world.XYZPos;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.minecraftforge.fml.common.network.NetworkRegistry;

public class CommonProxy extends ProxyBase implements IGuiHandler{






	@Override
	public void registerRenderers() {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public void registerKeyHandlers() {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public void registerEntityRenderers() {
		// TODO 自動生成されたメソッド・スタブ

	}

	public void registerEvents(){
		VillagerHelper.registerEvent();


		HSLibEvents events = HSLib.core().events;
		HSLibs.registerEvent(new EventKeyBinding());
		HSLibs.registerEvent(new EventEntityJoinWorld());
		HSLibs.registerEvent(new EventAttachDebuff());
		HSLibs.registerEvent(EventConfigChanged.instance().setLogger(HSLib.logger));
		HSLibs.registerEvent(events.livingUpdate);

		HSLibs.registerEvent(events.getDropEvent());
		events.livingUpdate.getEvents().add(HSLib.core().events.scannerEventPool);
		events.livingUpdate.getEvents().add(new EventLivingUpdateDebuff());
		CapabilityInventory.registerEvents();
//		if(event.getSide().isClient()){
//			HSLibs.registerEvent(new PlaySFXEvent());
//		}
		HSLibs.registerEvent(events.breakSpeed);
		HSLibs.registerEvent(events.livingHurt);

		EventConfigChanged.instance().addConfigHandler(HSLib.MODID, HSLib.configHandler);

		NetworkRegistry.INSTANCE.registerGuiHandler(HSLib.MODID, this);
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return HSLibGui.Type.fromMeta(ID).getGui(world, player, new XYZPos(x,y,z));
	}

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return HSLibGui.Type.fromMeta(ID).getContainer(world, player, new XYZPos(x,y,z));
	}



}
