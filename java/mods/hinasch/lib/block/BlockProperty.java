package mods.hinasch.lib.block;

import mods.hinasch.lib.primitive.PropertyElementWithID;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

public abstract class BlockProperty extends PropertyElementWithID{

	public BlockProperty(int id,String name) {
		super(new ResourceLocation(name), name, id);
		// TODO 自動生成されたコンストラクター・スタブ
	}

	public abstract Block getBlock();
	public Item getBlockAsItem(){
		return Item.getItemFromBlock(getBlock());
	}


}
