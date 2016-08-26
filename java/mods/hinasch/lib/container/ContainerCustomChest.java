package mods.hinasch.lib.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerCustomChest extends Container{
	private IInventory lowerChestInventory;
	protected int numRows;
	protected int parRow = 9;
	public ContainerCustomChest(IInventory par1iInventory, IInventory par2IInventory,EntityPlayer player) {

		this.lowerChestInventory = par2IInventory;
		this.numRows = par2IInventory.getSizeInventory() / 9;
		par2IInventory.openInventory(player);
//		if(this.numRows<=0){
//			this.numRows = 1;
//		}
//		if(this.parRow>par2IInventory.getSizeInventory()){
//			this.parRow = par2IInventory.getSizeInventory();
//		}
		int i = (this.numRows - 4) * 18;
		int j;
		int k;

		for (j = 0; j < this.numRows; ++j)
		{
			for (k = 0; k < this.parRow; ++k)
			{
				this.addSlotToContainer(this.getSlot(par2IInventory, k + j * 9, 8 + k * 18, 18 + j * 18));
			}
		}

		for (j = 0; j < 3; ++j)
		{
			for (k = 0; k < 9; ++k)
			{
				this.addSlotToContainer(new Slot(par1iInventory, k + j * 9 + 9, 8 + k * 18, 103 + j * 18 + i));
			}
		}

		for (j = 0; j < 9; ++j)
		{
			this.addSlotToContainer(new Slot(par1iInventory, j, 8 + j * 18, 161 + i));
		}
	}


	public Slot getSlot(IInventory inv,int par1,int par2,int par3){
		return new Slot(inv,par1,par2,par3);
	}
    @Override
	public boolean canInteractWith(EntityPlayer par1EntityPlayer)
	{
		return this.lowerChestInventory.isUseableByPlayer(par1EntityPlayer);
	}
    @Override
    public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int index)
    {
        ItemStack itemstack = null;
        Slot slot = (Slot)this.inventorySlots.get(index);

       // TotanMod.log(index);
//        if (slot != null && slot.getHasStack())
//        {
//            ItemStack itemstack1 = slot.getStack();
//            itemstack = itemstack1.copy();
//
//            if (index < this.numRows * 9)
//            {
//                if (!this.mergeItemStack(itemstack1, this.numRows * 9, this.inventorySlots.size(), true))
//                {
//                    return null;
//                }
//            }
//            else if (!this.mergeItemStack(itemstack1, 0, this.numRows * 9, false))
//            {
//                return null;
//            }
//
//            if (itemstack1.stackSize == 0)
//            {
//                slot.putStack((ItemStack)null);
//            }
//            else
//            {
//                slot.onSlotChanged();
//            }
//        }

        return null;
    }

    /**
     * Called when the container is closed.
     */
    @Override
    public void onContainerClosed(EntityPlayer par1EntityPlayer)
    {
        super.onContainerClosed(par1EntityPlayer);
        this.lowerChestInventory.closeInventory(par1EntityPlayer);
    }

    /**
     * Return this chest container's lower chest inventory.
     */
    public IInventory getLowerChestInventory()
    {
        return this.lowerChestInventory;
    }
}
