package mods.hinasch.lib.config;



import java.util.Map;

import com.google.common.collect.Maps;

import mods.hinasch.lib.primitive.LogWrapper;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EventConfigChanged {


	Map<String,ConfigBase> children = Maps.newHashMap();

	protected static EventConfigChanged INSTANCE;

//	private final String modid;
	private LogWrapper logger;
//	private final ConfigBase configHandler;
	public static EventConfigChanged instance(){
		if(INSTANCE==null){
			INSTANCE = new EventConfigChanged();
		}
		return INSTANCE;
	}
//	protected EventConfigChanged(String modid,ConfigBase configHandler){
//
//		this.modid = modid;
//		this.configHandler = configHandler;
//	}

	public void addConfigHandler(String modid,ConfigBase handler){
		this.children.put(modid, handler);
	}
	public EventConfigChanged setLogger(LogWrapper logger){
		this.logger = logger;
		return this;
	}

	@SubscribeEvent
	public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent e){
		children.forEach((id,con)->{
			if(e.getModID().equals(id)){

				if(logger!=null){
					logger.trace("config sync!", id);
				}
				con.syncConfig();
			}
		});
//		if(e.getModID().equals(modid)){
//			if(logger!=null){
//				logger.trace("config sync!");
//			}
//
//			configHandler.syncConfig();
//		}
	}


}
