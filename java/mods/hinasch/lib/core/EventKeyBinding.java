package mods.hinasch.lib.core;

import java.util.List;
import java.util.function.Consumer;

import com.google.common.collect.Lists;

import mods.hinasch.lib.client.ClientHelper;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent.KeyInputEvent;

public class EventKeyBinding {

	private static List<Consumer<KeyInputEvent>> list = Lists.newArrayList();


	
	public static void addEvent(Consumer<KeyInputEvent> e){
		list.add(e);
	}

	public static void addKeyBindings(KeyBinding... binds){
		ClientHelper.registerKeyBinding(binds);
	}
	@SubscribeEvent
	public void onKeyPressed(KeyInputEvent e){

		list.forEach(in -> in.accept(e));
	}


}
