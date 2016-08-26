package mods.hinasch.lib.client;

import java.util.Map;

import com.google.common.collect.Maps;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class RenderPlayerHook {

	Map<EntityPlayer,Timer> waits = Maps.newHashMap();

	static RenderPlayerHook INSTANCE;

	public static RenderPlayerHook instance(){
		if(INSTANCE==null){
			INSTANCE = new RenderPlayerHook();
		}
		return INSTANCE;
	}
	@SubscribeEvent
	public void onRenderPlayer(RenderGameOverlayEvent.Pre ev){

		if(!waits.isEmpty()){
			for(Timer timer:waits.values()){
				timer.decr();
			}
		}

	}

	public void setWaiting(EntityPlayer ep){
		waits.put(ep, new Timer(30));
	}
	public boolean isWaiting(EntityPlayer ep){
		if(waits.containsKey(ep)){
			return waits.get(ep).time > 0;
		}
		return false;
	}


	public static class Timer{

		int time = 0;

		public Timer(int time){
			this.time = time;
		}

		public void decr(){
			this.time -= 1;
			if(this.time<0){
				this.time = 0;
			}
//			HSLib.logger.trace("tmier", time);
		}
	}
}
