package mods.hinasch.lib.primitive;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

import joptsimple.internal.Strings;
import mods.hinasch.lib.core.HSLib;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;







public class LogWrapper {


	protected Logger logger;

	protected LogWrapper(String modid){
		logger = (Logger) LogManager.getLogger(modid);
	}
	protected LogWrapper(FMLPreInitializationEvent event){

//		this.modid = modid;
//		logger = (Logger) LogManager.getLogger(modid);

		logger = (Logger) event.getModLog();

	}


	public void setModeFromDebugConfig(){
//		if(HSLib.configHandler.isDebug()){
//			System.out.println(modid+" set logger debugmode.");
//			this.logger.setLevel(Level.TRACE);
//		}else{
//			System.out.println(modid+" set logger infomode.");
//			this.logger.setLevel(Level.INFO);
//		}
	}
	public static LogWrapper newLogger(String event){
		return new LogWrapper(event);
	}
	public static LogWrapper create(FMLPreInitializationEvent event){
		return new LogWrapper(event);
	}

	public void trace(String mes,Object... params){

		if(HSLib.configHandler.isDebug()){
			String paramStr = Strings.EMPTY;
			for(Object obj:params){
				if(obj!=null){
					paramStr += obj.toString() +",";
					if(obj instanceof Iterable){
						Iterable<?> iterable = (Iterable<?>) obj;
						for(Object child:iterable){
							paramStr += child!=null ? child.toString() + ":" : "NULL!"+";";
						}
					}

				}else{
					paramStr += "NULL!" +",";
				}

			}
			this.logger.info(mes+" param:"+paramStr);
		}

	}
	public Logger get(){
		return this.logger;
	}
}
