package mods.hinasch.lib.item;

import net.minecraft.item.ItemStack;
import net.minecraft.util.WeightedRandom;

public class WeightedRandomStack extends WeightedRandom.Item{

	public ItemStack is;
	
	public WeightedRandomStack(int p_i1556_1_,ItemStack is) {
		super(p_i1556_1_);
		this.is = is;
		// TODO 自動生成されたコンストラクター・スタブ
	}

}
