package mods.hinasch.lib.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemDebug extends ItemArmor{


	public ItemDebug() {
		super(ItemArmor.ArmorMaterial.DIAMOND, 0, EntityEquipmentSlot.CHEST);

	}

    public void onArmorTick(World world, EntityPlayer player, ItemStack itemStack){
    	if(player.shouldHeal()){
    		player.heal(30.0F);
    	}
    }
}
