package mods.hinasch.lib.item;

import java.util.Optional;

import mods.hinasch.lib.primitive.NameAndNumberAndID;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public abstract class ItemProperty extends NameAndNumberAndID<ResourceLocation>{

	Optional<String> oreDictID = Optional.empty();
	public ItemProperty(int id,String name) {
		super(new ResourceLocation(name), name, id);
		// TODO 自動生成されたコンストラクター・スタブ
	}

	public abstract Item getItem();
	public Optional<String> getOreDictID(){
		return oreDictID;
	}

	public void setOreDictID(String name){
		this.oreDictID = Optional.of(name);
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
