package mods.hinasch.lib.util;

import java.util.List;
import java.util.Random;

import com.google.common.collect.Lists;

import mods.hinasch.lib.entity.EntityThrowableBase;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IProjectile;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class VecUtil {


	public static void setThrowableToTarget(EntityLivingBase thrower,EntityLivingBase target,Entity throwable){
        double d0 = target.posX - thrower.posX;
        double d1 = target.getEntityBoundingBox().minY + (double)(target.height / 3.0F) - throwable.posY;
        double d2 = target.posZ - thrower.posZ;
        double d3 = (double)MathHelper.sqrt_double(d0 * d0 + d2 * d2);
        if(throwable instanceof IProjectile){
        	 ((IProjectile) throwable).setThrowableHeading(d0, d1 + d3 * 0.20000000298023224D, d2, 1.6F, (float)(14 - thrower.worldObj.getDifficulty().getDifficultyId() * 4));
        }
     }
	/**
	 * 矢連続打ちなど、ブレを得たい時に使う。ベクトルで返ってくる。
	 * @param motion
	 * @param rand
	 * @param upDownMin
	 * @param upDownMax
	 * @param leftRightMin
	 * @param leftRightMax
	 * @return
	 */
	public static Vec3d getShake(Vec3d motion,Random rand,int upDownMin,int upDownMax,int leftRightMin,int leftRightMax){

		motion.rotateYaw((float)Math.toRadians(rand.nextInt(leftRightMax)-leftRightMin));
		motion.rotateYaw((float)Math.toRadians(rand.nextInt(leftRightMax)-leftRightMin));
		motion.rotatePitch((float)Math.toRadians(rand.nextInt(upDownMax)-upDownMin));
		return motion;
	}

	public static void addShake(Entity projectile,double dx,double dy,double dz){
		projectile.posX += dx;
		projectile.posY += dy;
		projectile.posZ += dz;
	}
	/**
	 * エンティティの向かってるベクトルを返す。（not 位置ベクトル）
	 * @param world
	 * @param ent
	 * @return
	 */
	public static Vec3d getVecFromEntityMotion(World world,Entity ent){
		Vec3d vec = new Vec3d(ent.motionX, ent.motionY, ent.motionZ);
		return vec;
	}

	public static Vec3d getHeadingToEntityVec(Entity base,Entity target){
		double d0 = target.posX - base.posX;
		double d2 = target.posZ - base.posZ;
		double d1 = target.getEntityBoundingBox().minY + (double)(target.height / 3.0F) - base.posY;
		double d3 = (double)MathHelper.sqrt_double(d0 * d0 + d2 * d2);
		return new Vec3d(d0, d1 + d3 * 0.20000000298023224D, d2);
	}
	public static void setReadyProjectile(EntityThrowableBase projectile,Entity thrower,float drive){
		projectile.func_184538_a(thrower, thrower.rotationPitch, thrower.rotationYaw, 0.0F, drive, 1.0F);
	}
	public static double getHorizontalMotionAbs(EntityLivingBase living){
		double x = Math.abs(living.motionX);
		double z = Math.abs(living.motionZ);
		return x+z;
	}

	public static enum EnumHorizontalDirection{FRONT,BACK,SIDE,NEUTRAL};
	public static List<EnumHorizontalDirection> getHorizontalDirection(EntityLivingBase ep){
		Vec3d v = ep.getLookVec();
		Vec3d v1 = new Vec3d(v.xCoord,0,v.zCoord);
		Vec3d v2 = new Vec3d(ep.motionX,0,ep.motionZ);
		double d = v1.dotProduct(v2)/(v1.lengthVector()*v2.lengthVector());
		double coss = Math.acos(d);
		double angle = coss *180 /Math.PI;
		double error = 5;
		List<EnumHorizontalDirection> list = Lists.newArrayList();
		if(ep.motionX==0 && ep.motionZ==0){
			list.add(EnumHorizontalDirection.NEUTRAL);
		}
		if(angle>=0 && angle<25+error){
			list.add(EnumHorizontalDirection.FRONT);
		}
		if(angle>25+error && angle<90+error){
			list.add(EnumHorizontalDirection.SIDE);
		}
		if(angle>95+error && angle<=180){
			list.add(EnumHorizontalDirection.BACK);
		}
		return list;
	}

	public static Vec3d getHorizontalMotionVec(EntityLivingBase living,double yMotion){
		return new Vec3d(living.motionX,yMotion,living.motionZ);
	}
}
