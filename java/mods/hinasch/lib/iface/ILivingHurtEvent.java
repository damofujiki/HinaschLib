package mods.hinasch.lib.iface;

import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

public abstract class ILivingHurtEvent implements IWeightedEvent{
	public abstract boolean apply(LivingHurtEvent e,DamageSource dsu);
	public abstract String getName();
	public abstract DamageSource process(LivingHurtEvent e,DamageSource dsu);

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
