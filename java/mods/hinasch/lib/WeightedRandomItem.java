package mods.hinasch.lib;

import net.minecraft.util.WeightedRandom;

public class WeightedRandomItem<T> extends WeightedRandom.Item{

	T item;
	public WeightedRandomItem(int itemWeightIn,T item) {
		super(itemWeightIn);
		this.item = item;
		// TODO 自動生成されたコンストラクター・スタブ
	}

	public T getItem(){
		return this.item;
	}
}
