package mods.hinasch.lib.item;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;

public class ItemBlockMultiBase extends ItemBlock{
	public ItemBlockMultiBase(Block parent) {
		super(parent);
		this.setHasSubtypes(true);
	}

	
	@Override
    public int getMetadata(int par1)
    {
        return par1;
    }

	
}
