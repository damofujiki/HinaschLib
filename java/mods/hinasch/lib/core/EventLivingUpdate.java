package mods.hinasch.lib.core;

import java.util.ArrayList;
import java.util.List;

import mods.hinasch.lib.iface.ILivingUpdateEvent;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EventLivingUpdate {


	protected AxisAlignedBB bb;
//	protected AbilityRegistry ar = Unsaga.abilities;
	private List<ILivingUpdateEvent> events;
	public EventLivingUpdate(){
		this.events = new ArrayList();
//		this.events.add(new LivingUpdateDebuff());
//		this.events.add(new LivingUpdateAbility());
//		this.events.add(new LivingUpdateSkillPanels());
//		this.events.add(new LivingUpdateEventBerserk());
//		this.events.add(Unsaga.scannerPool);
	}

	public List<ILivingUpdateEvent> getEvents(){
		return this.events;
	}

	public void sort(){
		this.events.sort(null);
	}
	@SubscribeEvent
	public void onLivingUpdata(LivingUpdateEvent e){

		for(ILivingUpdateEvent ev:this.events){
			ev.update(e);
		}

//		if(Unsaga.modConfig.enableLP){
//			Unsaga.lpLogicManager.lpEventOnLivingUpdate(e);
//		}


	}

}
