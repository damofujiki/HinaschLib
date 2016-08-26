package mods.hinasch.lib.item;

import com.google.common.base.Function;
import com.mojang.realmsclient.util.Pair;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class PairItem extends Pair<Item,Integer>{

	protected PairItem(Item first, Integer second) {
		super(first, second);
		// TODO 自動生成されたコンストラクター・スタブ
	}

	public Item getItem(){
		return this.first();

	}

	public static Function<PairItem,ItemStack> TO_ITEMSTACK = new Function<PairItem,ItemStack>(){

		@Override
		public ItemStack apply(PairItem input) {
			return input.asItemStack();
		}
	};
	public ItemStack asItemStack(){
		return new ItemStack(this.getItem(),1,this.getMeta());
	}
	public int getMeta(){
		return this.second();
	}

	public static PairItem of(Item item,int meta){
		return new PairItem(item,meta);
	}

	public static PairItem of(ItemStack is){
		return new PairItem(is.getItem(),is.getItemDamage());
	}
}
