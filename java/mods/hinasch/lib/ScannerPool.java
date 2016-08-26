package mods.hinasch.lib;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import mods.hinasch.lib.iface.ILivingUpdateEvent;
import mods.hinasch.lib.primitive.LogWrapper;
import mods.hinasch.lib.world.WorldHelper;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;

public class ScannerPool extends ILivingUpdateEvent{


	protected LogWrapper logger;
	protected Map<Integer,SafeUpdateEvent> eventList;
	protected final int SIZE = 10;
	protected ScannerPool(){
		this.eventList = new ConcurrentHashMap();
	}

	public static ScannerPool create(){
		return new ScannerPool();
	}
	public ScannerPool setLogger(LogWrapper par1){
		this.logger = par1;
		return this;
	}
	public void addEvent(SafeUpdateEvent e){
		if(logger!=null){
			logger.trace("Adding Event[size:"+this.eventList.size()+"]",this.getName());
		}



			if(this.eventList.size()<SIZE){
				int num = this.publishNumber();
				if(logger!=null){
					logger.trace("event number:"+num);
				}
				if(num>=0){
					this.eventList.put(num,e);
				}

			}





	}

	protected int publishNumber(){

		for(int i=0;i<100;i++){
			if(!this.eventList.containsKey(i)){
				return i;
			}
		}
		return -1;
	}

	public void removeEvent(int key){

			if(this.eventList.containsKey(key)){
				this.eventList.remove(key);
			}



	}


	@Override
	public void update(LivingUpdateEvent e) {
		if(WorldHelper.isServer(e.getEntityLiving().worldObj)){

			int removeEvent = -1;
			if(!this.eventList.isEmpty()){

				for(int key:this.eventList.keySet()){

					if(this.eventList.containsKey(key)){
						SafeUpdateEvent ev = this.eventList.get(key);
						if(ev.hasFinished()){
							removeEvent = key;
						}else{
							ev.loop();
						}
					}
				}
//				for(int i=0;i<100;i++){
//					if(this.eventList.containsKey(i)){
//						SafeUpdateEvent ev = this.eventList.get(i);
//						if(ev.hasFinished()){
//							removeEvent = i;
//						}else{
//							ev.loop();
//						}
//					}
//				}
//					for(SafeUpdateEvent ev:eventList){
//						if(ev.hasFinished()){
//							removeEvent = ev;
//						}else{
//							ev.loop();
//						}
//
//					}


			}
			if(removeEvent>0){
				this.removeEvent(removeEvent);
			}

		}


	}

	@Override
	public String getName() {
		// TODO 自動生成されたメソッド・スタブ
		return "Scanner Pool LivingUpdate";
	}
}
