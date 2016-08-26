package mods.hinasch.lib.capability;

import mods.hinasch.lib.core.HSLib;
import mods.hinasch.lib.util.HSLibs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class VillagerHelper {

	@CapabilityInject(ICustomer.class)
	private static Capability<ICustomer> INSTANCE_CUSTOMER = null;

	public static Capability<ICustomer> customer(){
		return INSTANCE_CUSTOMER;
	}

	public static void register(){
		HSLibs.registerCapability(ICustomer.class, new StorageDummy(), DefaultImpl.class);
	}

	public static void registerEvent(){
		HSLibs.registerEvent(new EventRegister());
	}

	public static interface ICustomer {

		public EntityLivingBase getMerchant();
		public void setMerchant(EntityVillager par1);
	}

	public static class DefaultImpl implements ICustomer{

		EntityLivingBase merchant;
		@Override
		public EntityLivingBase getMerchant() {
			// TODO 自動生成されたメソッド・スタブ
			return this.merchant;
		}

		@Override
		public void setMerchant(EntityVillager par1) {
			// TODO 自動生成されたメソッド・スタブ
			this.merchant = par1;
		}

	}

	public static class EventRegister{

		@SubscribeEvent
		public void attachEvent(AttachCapabilitiesEvent.Entity ev){
			if(ev.getEntity() instanceof EntityPlayer){
				ev.addCapability(new ResourceLocation(HSLib.MODID,"customer"),new ICapabilityProvider(){

					ICustomer inst = INSTANCE_CUSTOMER.getDefaultInstance();
					@Override
					public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
						// TODO 自動生成されたメソッド・スタブ
						return INSTANCE_CUSTOMER!=null && INSTANCE_CUSTOMER==capability;
					}

					@Override
					public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
						// TODO 自動生成されたメソッド・スタブ
						return (INSTANCE_CUSTOMER!=null && INSTANCE_CUSTOMER==capability) ? (T)inst : null;
					}}
				);
			}
		}
	}
	public static boolean hasCustomerCapability(EntityLivingBase living){
		return living.hasCapability(INSTANCE_CUSTOMER, null);
	}

	public static ICustomer getCustomerCapability(EntityLivingBase customer){
		if(hasCustomerCapability(customer)){
			return customer.getCapability(INSTANCE_CUSTOMER, null);//customer.getCapability(UnsagaCapability.villager(), null);
		}
		return null;
	}
}
