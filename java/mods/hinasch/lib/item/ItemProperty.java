package mods.hinasch.lib.item;

import java.util.Optional;

import jline.internal.Preconditions;
import mods.hinasch.lib.primitive.PropertyElementWithID;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public abstract class ItemProperty extends PropertyElementWithID{

	Optional<String> oreDictID = Optional.empty();
	String iconname;

	public ItemProperty(int id,String name) {
		super(new ResourceLocation(name), name, id);
		this.iconname = Preconditions.checkNotNull(name);
	}

	public ItemProperty(int id,String name,String unlname) {
		super(new ResourceLocation(name), unlname, id);
		this.iconname = Preconditions.checkNotNull(unlname);
	}

	public String getIconName(){
		return this.iconname;
	}
	public ItemProperty setIconName(String par1){
		this.iconname = par1;
		return this;
	}

	public abstract Item getItem();
	public Optional<String> getOreDictID(){
		return oreDictID;
	}

	public ItemProperty setOreDictID(String name){
		this.oreDictID = Optional.of(name);
		return this;
	}

	public boolean isItemStackEqual(ItemStack is){
		if(is!=null){
			return is.getItem()==this.getItem() && is.getItemDamage() == this.getMeta();
		}
		return false;
	}

	public ItemStack getItemStack(int amount){
		return new ItemStack(this.getItem(),amount,this.getMeta());
	}
}
