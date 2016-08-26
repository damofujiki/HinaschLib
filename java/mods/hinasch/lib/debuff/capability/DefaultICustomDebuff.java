package mods.hinasch.lib.debuff.capability;

import java.util.HashSet;
import java.util.Set;

import io.netty.util.internal.ConcurrentSet;
import mods.hinasch.lib.core.HSLib;
import mods.hinasch.lib.debuff.DebuffBase;
import mods.hinasch.lib.debuff.DebuffEffectBase;
import net.minecraft.entity.EntityLivingBase;

public class DefaultICustomDebuff  implements ICustomDebuff{

	boolean isStopping;
	protected ConcurrentSet<DebuffEffectBase> debuffs = new ConcurrentSet();
	@Override
	public Set<DebuffEffectBase> getDebuffs() {
		// TODO 自動生成されたメソッド・スタブ
		return this.debuffs;
	}
	@Override
	public void setDebuffs(ConcurrentSet<DebuffEffectBase> debuffs) {
		this.debuffs = debuffs;

	}
	@Override
	public boolean hasDebuff(DebuffBase debuff) {

		if(this.debuffs.isEmpty())return false;
		for(DebuffEffectBase living:this.debuffs){
			if(living.getDebuff().key==debuff.key)return true;
		}
		return false;
	}
	@Override
	public void updateAllRemainings(EntityLivingBase living) {
		if(this.isStopping)return;
		if(!this.debuffs.isEmpty()){
			Set<DebuffEffectBase> removes = new HashSet();
			for(DebuffEffectBase debuff:this.debuffs){
				debuff.updateRemaining(living);
				if(debuff.isExpired()){
					debuff.onExpiredEvent(living);
					removes.add(debuff);
				}
			}
			if(!removes.isEmpty()){
				for(DebuffEffectBase debuff:removes){
					this.debuffs.remove(debuff);
				}
			}
		}

	}
	@Override
	public void updateAllRemainingsByTick(EntityLivingBase living) {
		if(this.isStopping)return;
		if(!this.debuffs.isEmpty()){
			HSLib.logger.trace(this.getClass().getName(), "debuffある");
			for(DebuffEffectBase debuff:this.debuffs){
				debuff.updateRemainingByTick(living);
			}
		}

	}
	@Override
	public void setTimeStop(boolean par1) {
		this.isStopping = par1;

	}
	@Override
	public boolean isTimeStopping() {
		// TODO 自動生成されたメソッド・スタブ
		return this.isStopping;
	}

}
