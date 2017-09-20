package mods.hinasch.lib.debuff.capability;

import java.util.Set;

import io.netty.util.internal.ConcurrentSet;
import mods.hinasch.lib.debuff.DebuffBase;
import mods.hinasch.lib.debuff.DebuffEffectBase;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;

public interface ICustomDebuff {


	public Potion getPotion();
	public Set<DebuffEffectBase> getDebuffs();
	public void setDebuffs(ConcurrentSet<DebuffEffectBase> debuffs);
	public boolean hasDebuff(DebuffBase debuff);
	public void updateAllRemainings(EntityLivingBase living);
	public void updateAllRemainingsByTick(EntityLivingBase living);
	public void setTimeStop(boolean par1);
	public boolean isTimeStopping();
}
