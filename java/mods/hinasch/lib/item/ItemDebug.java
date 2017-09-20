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


//    @Override
//    public ActionResult<ItemStack> onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn, EnumHand hand)
//    {
//    	if(itemStackIn!=null){
//    		HSLibs.openGui(playerIn, HSLib.MODID, HSLibGui.Type.TEXTMENU.getMeta(), worldIn, XYZPos.createFrom(playerIn));
//    	}
//        return new ActionResult(EnumActionResult.PASS, itemStackIn);
//    }
}
