package mods.hinasch.lib.item;

import java.util.List;

import jline.internal.Preconditions;
import mods.hinasch.lib.primitive.PropertyElementWithID;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public abstract class ItemNoFunction extends Item{

	String prefix;
	public ItemNoFunction(String prefix) {
		super();
		this.setHasSubtypes(true);
		this.setMaxDamage(0);
		this.prefix = Preconditions.checkNotNull(prefix);
	}

	public abstract PropertyRegistryItem<? extends ItemProperty> getItemProperties();

	@Override
	public void getSubItems(Item par1, CreativeTabs par2CreativeTabs, List par3List)
	{
		for (ItemProperty prop:this.getItemProperties())
		{
				par3List.add(new ItemStack(par1, 1, prop.getId()));

		}
	}


	@Override
	public String getUnlocalizedName(ItemStack par1ItemStack)
	{

//		int var2 = MathHelper.clamp_int(par1ItemStack.getItemDamage(), 0, getItemProperties().getLength()-1);
//		UnsagaMod.logger.trace(this.getClass().getName(), var2);
		if(this.getItemProperties().getObjectById(par1ItemStack.getItemDamage())!=null){
			return "item."+prefix+ "." + getItemProperties().getObjectById(par1ItemStack.getItemDamage()).getName();
		}
		return "null";
	}

	public static interface IPropertyGroup{
		public String getName(int meta);
		public PropertyElementWithID fromMeta(int meta);
		public int length();
	}
}
