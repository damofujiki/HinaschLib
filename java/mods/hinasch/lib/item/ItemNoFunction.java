package mods.hinasch.lib.item;

import java.util.List;

import mods.hinasch.lib.primitive.NameAndNumberAndID;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;

public abstract class ItemNoFunction extends Item{

	String prefix;
	public ItemNoFunction(String prefix) {
		super();
		this.setHasSubtypes(true);
		this.setMaxDamage(0);
		this.prefix = prefix;
	}

	public abstract PropertyRegistryItem<? extends ItemProperty> getItemProperties();

	@Override
	public void getSubItems(Item par1, CreativeTabs par2CreativeTabs, List par3List)
	{
		for (int var4 = 0; var4 < getItemProperties().getLength(); ++var4)
		{
				par3List.add(new ItemStack(par1, 1, var4));

		}
	}
	@Override
	public String getUnlocalizedName(ItemStack par1ItemStack)
	{
		int var2 = MathHelper.clamp_int(par1ItemStack.getItemDamage(), 0, getItemProperties().getLength()-1);
		return prefix+ "." + getItemProperties().getObjectById(var2).getName();
	}

	public static interface IPropertyGroup{
		public String getName(int meta);
		public NameAndNumberAndID<ResourceLocation> fromMeta(int meta);
		public int length();
	}
}
