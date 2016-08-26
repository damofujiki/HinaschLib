package mods.hinasch.lib.iface;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;

public interface ICustomModel {

	public final Inner customModelHelper = new Inner();

    public net.minecraft.client.model.ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, EntityEquipmentSlot armorSlot, net.minecraft.client.model.ModelBiped _default);


    public static class Inner{


        public ModelBiped getArmorModel(ModelBiped modelBiped,EntityLivingBase entityLiving, ItemStack itemStack, EntityEquipmentSlot armorSlot)
        {
        	modelBiped.bipedHead.showModel = armorSlot == EntityEquipmentSlot.HEAD;
        	modelBiped.bipedHeadwear.showModel = armorSlot ==EntityEquipmentSlot.HEAD;
        	modelBiped.bipedBody.showModel = armorSlot == EntityEquipmentSlot.CHEST;
        	//modelBiped.bipedCloak.showModel = armorSlot == 1;
        	modelBiped.bipedLeftArm.showModel = armorSlot == EntityEquipmentSlot.CHEST;
        	modelBiped.bipedRightArm.showModel = armorSlot == EntityEquipmentSlot.CHEST;
        	modelBiped.bipedRightLeg.showModel = (armorSlot == EntityEquipmentSlot.LEGS)||(armorSlot == EntityEquipmentSlot.FEET);
        	modelBiped.bipedLeftLeg.showModel = (armorSlot == EntityEquipmentSlot.LEGS)||(armorSlot == EntityEquipmentSlot.FEET);

        	//modelBiped.setItemStack(itemStack);
            return modelBiped;
        }
    };

}
