package mods.hinasch.lib;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.AxisAlignedBB;

public interface IPostRangeAttack {
	public void postRangeAttacked(DamageSource ds, AxisAlignedBB aabb, float damage, EntityLivingBase mob);
}
