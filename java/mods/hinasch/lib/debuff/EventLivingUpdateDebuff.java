package mods.hinasch.lib.debuff;

import mods.hinasch.lib.debuff.capability.ICustomDebuff;
import mods.hinasch.lib.iface.ILivingUpdateEvent;
import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;

public class EventLivingUpdateDebuff extends ILivingUpdateEvent{

	@Override
	public void update(LivingUpdateEvent e) {
		EntityLivingBase living =(EntityLivingBase)e.getEntityLiving();
		if(DebuffHelper.hasCapability(living)){

			ICustomDebuff capa = DebuffHelper.getCapability(living);
//			ExtendedLivingData ldata = getData(living);

			if(living.ticksExisted % 20 * 12 == 0){

				capa.updateAllRemainingsByTick(living);
			}
			capa.updateAllRemainings(living);

		}
	}

	@Override
	public String getName() {
		// TODO 自動生成されたメソッド・スタブ
		return "debuff.livingupdate";
	}

}
