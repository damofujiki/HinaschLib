package mods.hinasch.lib.entity;

import java.util.ArrayList;
import java.util.List;

import mods.hinasch.lib.CustomDropEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EventLivingDrops {


	public List<CustomDropEvent> behaviorList;
	public EventLivingDrops init(){
		this.behaviorList = new ArrayList();

		return this;

	}

	@SubscribeEvent
	public void hookEntityDropNew(LivingDropsEvent e){
		CustomDropEvent.processDrop(e, behaviorList);
	}

	public List<CustomDropEvent> getDropList(){
		return this.behaviorList;
	}










}
