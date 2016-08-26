package mods.hinasch.lib.iface;

import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

@Deprecated
public interface IMultiItem {

	public void getSubItems(Item par1, CreativeTabs par2CreativeTabs, List par3List);
    //public IIcon getIconFromDamage(int par1);
	public String getUnlocalizedName(ItemStack par1ItemStack);
    //public void registerIcons(IIconRegister iconRegister);
	//public IconHelper helper = new IconHelper();
   
}
