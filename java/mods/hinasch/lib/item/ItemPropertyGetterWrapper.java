package mods.hinasch.lib.item;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public interface ItemPropertyGetterWrapper extends IItemPropertyGetter{

	static IItemPropertyGetter of(ItemPropertyGetterWrapper lambda){
		return lambda;
	}
	float getProp(ItemStack stack, World worldIn, EntityLivingBase entityIn);
    @SideOnly(Side.CLIENT)
    default float apply(ItemStack stack, World worldIn, EntityLivingBase entityIn){
    	return getProp(stack, worldIn, entityIn);
    }


}
