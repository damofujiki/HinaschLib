package mods.hinasch.lib.item;

import net.minecraft.item.ItemStack;

public class ItemStackWrapper {

	public final ItemStack is;
	
	protected int hashItem;
	protected int damage;
	public ItemStackWrapper(ItemStack is){
		this.is = is;
		this.hashItem = this.is.getItem().hashCode();
		this.damage = is.getItemDamage();
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + damage;
		result = prime * result + hashItem;
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ItemStackWrapper other = (ItemStackWrapper) obj;
		if (damage != other.damage)
			return false;
		if (hashItem != other.hashItem)
			return false;
		return true;
	}
	
	
}
