package mods.hinasch.lib.block;

import java.util.Optional;

import mods.hinasch.lib.item.ItemProperty;

public abstract class BlockOreProperty extends BlockProperty {

	final float exp;

	Optional<? extends ItemProperty> insideItem = Optional.empty();

	Optional<? extends ItemProperty> smelted = Optional.empty();

	final int harvestLevel;

	public BlockOreProperty(int id, String name,float exp,int harvest) {
		super(id, name);
		this.exp = exp;
		this.harvestLevel = harvest;
		// TODO 自動生成されたコンストラクター・スタブ
	}

	public float getExp() {
		return exp;
	}

	public int getHarvestLevel() {
		return harvestLevel;
	}

	public Optional<? extends ItemProperty> getInsideItem() {
		return insideItem;
	}

	public Optional<? extends ItemProperty> getSmelted() {
		return smelted;
	}

	public void setInsideItem(Optional<? extends ItemProperty> insideItem) {
		this.insideItem = insideItem;
	}
	public void setInsideItem(ItemProperty insideItem) {
		this.insideItem = Optional.of(insideItem);
	}
	public void setSmelted(Optional<? extends ItemProperty> smelted) {
		this.smelted = smelted;
	}
	public void setSmelted(ItemProperty smelted) {
		this.smelted = Optional.of(smelted);
	}

	public void setSmeltedAndInside(ItemProperty smelted) {
		this.smelted = Optional.of(smelted);
		this.insideItem = Optional.of(smelted);
	}
	public abstract String getOreDict();

}
