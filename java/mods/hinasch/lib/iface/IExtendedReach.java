package mods.hinasch.lib.iface;

import java.util.List;

import mods.hinasch.lib.client.ClientHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.server.SPacketAnimation;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public interface IExtendedReach {

	public float getReach();

	public static boolean checkSpearLength(ItemStack is,EntityPlayer ep,World par2World){

		float reach = ((IExtendedReach) is.getItem()).getReach();
		RayTraceResult rayTrace = ClientHelper.getMouseOverLongReach();
		if(rayTrace!=null && rayTrace.entityHit!=null){
			float dis = ep.getDistanceToEntity(rayTrace.entityHit);
			if(dis<reach){
				return onSpearReachedEntity(rayTrace, reach, ep, par2World);
			}
		}
		return false;
	}

	public static void swingArm(EntityPlayer ep,EnumHand hand)
	{
		ItemStack stack = ep.getHeldItem(hand);
		if (!ep.isSwingInProgress || ep.swingProgressInt < 0)
		{
			ep.swingProgressInt = -1;
			ep.isSwingInProgress = true;
			ep.swingingHand = hand;

			if (ep.worldObj instanceof WorldServer)
			{
				((WorldServer)ep.worldObj).getEntityTracker().sendToAllTrackingEntity(ep, new SPacketAnimation(ep, hand == EnumHand.MAIN_HAND ? 0 : 3));
			}
		}
	}

	public static boolean onSpearReachedEntity(RayTraceResult mop,float reach,EntityPlayer ep,World par2World){


		if(mop.entityHit.hurtResistantTime==0 ){
			AxisAlignedBB ab = mop.entityHit.getEntityBoundingBox();
			List<Entity> list = par2World.getEntitiesWithinAABB(Entity.class, ab);

			if(!list.isEmpty()){

				Entity hurtEnt = list.get(0);
				//ep.attackTargetEntityWithCurrentItem(hurtEnt);
				if(hurtEnt!=ep){

					swingArm(ep,EnumHand.MAIN_HAND);
					Minecraft.getMinecraft().playerController.attackEntity(ep, hurtEnt);

					return true;
				}
			}


		}
		return false;
	}
}
