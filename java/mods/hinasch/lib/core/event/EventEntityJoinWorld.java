package mods.hinasch.lib.core.event;

import java.util.List;
import java.util.function.Consumer;

import com.google.common.collect.Lists;

import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EventEntityJoinWorld {

	private static List<Consumer<EntityJoinWorldEvent>> list = Lists.newArrayList();
	@SubscribeEvent
	public void onEntityJoinWorld(EntityJoinWorldEvent e){
		list.forEach(in -> in.accept(e));
	}

	public static void addEvent(Consumer<EntityJoinWorldEvent> c){
		list.add(c);
	}
}
