package mods.hinasch.lib.core;

import java.util.List;
import java.util.function.Consumer;

import com.google.common.collect.Lists;

import net.minecraftforge.event.entity.player.PlayerEvent.BreakSpeed;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EventBreakSpeed {


	List<Consumer<BreakSpeed>> list = Lists.newArrayList();

	public static EventBreakSpeed INSTANCE;

	public static EventBreakSpeed instance(){
		if(INSTANCE==null){
			INSTANCE = new EventBreakSpeed();
		}
		return INSTANCE;
	}
	protected EventBreakSpeed(){

	}
	@SubscribeEvent
	public void breakSpeed(BreakSpeed e){
		for(Consumer<BreakSpeed> consumer:list){
			consumer.accept(e);
		}
	}

	public void addEvent(Consumer<BreakSpeed> ev){
		list.add(ev);
	}

}
