package mods.hinasch.lib.container;

import mods.hinasch.lib.iface.IIconItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class SlotIcon extends Slot{

	public SlotIcon(IInventory p_i1824_1_, int p_i1824_2_, int p_i1824_3_,
			int p_i1824_4_) {
		super(p_i1824_1_, p_i1824_2_, p_i1824_3_, p_i1824_4_);
		// TODO 自動生成されたコンストラクター・スタブ
	}

    public boolean canTakeStack(EntityPlayer p_82869_1_)
    {
        return false;
    }
    
    @Override
    public boolean isItemValid(ItemStack par1ItemStack)
    {
        return this.isItemStackValid(par1ItemStack);
    }
    
    public static boolean isItemStackValid(ItemStack par1ItemStack)
    {
        Item item = (par1ItemStack == null ? null : par1ItemStack.getItem());
        return (item instanceof IIconItem)? true : false;
    }
}
