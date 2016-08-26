package mods.hinasch.lib.iface;

import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;

public abstract class ILivingUpdateEvent implements IWeightedEvent{
	public abstract void update(LivingUpdateEvent e);
	public abstract String getName();

	@Override
	public int getWeight(){
		return 0;
	}
	@Override
	public int compareTo(IWeightedEvent o) {
		if(o.getWeight()>this.getWeight()){
			return -1;
		}
		if(o.getWeight()<this.getWeight()){
			return 1;
		}
		return 0;
	}
}
