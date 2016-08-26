package mods.hinasch.lib;

import java.util.List;

import com.google.common.collect.Lists;

import mods.hinasch.lib.util.HSLibs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;

public class RangeDamage implements IPostRangeAttack{

	protected World world;

	protected List<IPostRangeAttack> hooks = Lists.newArrayList();
	protected boolean hitOnGroundOnly = true;
	protected boolean isNoAttack = false;

	public boolean isNoAttack() {
		return isNoAttack;
	}

	public RangeDamage setNoAttack(boolean isNoAttack) {
		this.isNoAttack = isNoAttack;
		return this;
	}

	public static AxisAlignedBB makeBBFromPlayer(EntityPlayer player,double horizontal,double vertical){
		return player.getEntityBoundingBox().expand(horizontal,vertical,horizontal);
	}

	public static RangeDamage create(World world){
		return new RangeDamage(world);
	}
	protected RangeDamage(World world){
		this.world = world;
		this.hitOnGroundOnly = false;
	}

	public RangeDamage setHitOnlyOnGroundMob(boolean par1){
		this.hitOnGroundOnly = par1;
		return this;
	}

	public boolean isHitOnlyOnGroundMob(){

		return this.hitOnGroundOnly;
	}
	public RangeDamage addHook(IPostRangeAttack range){
		this.hooks.add(range);
		return this;
	}
	public RangeDamage addHook(RangeDamage range){
		this.hooks.add(range);
		return this;
	}
	public void causeDamage(final DamageSource ds,final AxisAlignedBB aabb,final float damage){
		final Entity attacker = ds.getEntity();
		List<EntityLivingBase> mobList = world.getEntitiesWithinAABB(EntityLivingBase.class, aabb,(EntityLivingBase mob) -> (mob!=attacker));

		if(!mobList.isEmpty()){
			mobList.stream().filter(mob -> !this.isSameTeam(attacker, mob)).forEach(mob -> this.doRangedAttack(ds, aabb, damage, mob));
//			ListHelper.stream(mobList).forEach(new BiConsumer<EntityLivingBase,RangeDamage>(){
//
//				@Override
//				public void accept(EntityLivingBase input,RangeDamage parent) {
////					UnsagaMod.logger.trace("range", input);
//					if(!isSameTeam(attacker,input)){
//						parent.doRangedAttack(ds,aabb,damage,input);
//					}
//
//				}},this);

		}
	}

	protected void doRangedAttack(DamageSource ds, AxisAlignedBB aabb, float damage,EntityLivingBase mob){
		if(mob.onGround && this.hitOnGroundOnly || !this.hitOnGroundOnly){
			if(!this.isNoAttack){
				mob.attackEntityFrom(ds, damage);
			}

			if(!this.hooks.isEmpty()){
				for(IPostRangeAttack hook:this.hooks){
					hook.postRangeAttacked(ds, aabb, damage, mob);
				}

			}else{
				this.postRangeAttacked(ds,aabb,damage,mob);
			}

		}
	}


	public void postRangeAttacked(DamageSource ds, AxisAlignedBB aabb, float damage, EntityLivingBase mob){

	}

	protected boolean isSameTeam(Entity atacker,EntityLivingBase mob){
		if(atacker instanceof EntityLivingBase){
			if(HSLibs.isSameTeam((EntityLivingBase) atacker, mob)){
				return true;
			}
		}
		return false;
	}


}
