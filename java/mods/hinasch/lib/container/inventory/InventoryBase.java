package mods.hinasch.lib.container.inventory;

import mods.hinasch.lib.core.HSLib;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;

/**
 * インベントリクラス使い回しのためのクラス。
 *
 *
 */
public class InventoryBase implements IInventory{
	protected ItemStack[] theInventory;



	public InventoryBase(int size){

		this.theInventory = new ItemStack[size];
	}

	@Override
	public int getSizeInventory() {
		// TODO 自動生成されたメソッド・スタブ
		return this.theInventory.length;
	}

	@Override
	public ItemStack getStackInSlot(int i) {

		return this.theInventory[i];
	}

	@Override
	public ItemStack decrStackSize(int num, int size) {
        if (this.theInventory[num] != null)
        {
            ItemStack itemstack;

            if (this.theInventory[num].stackSize <= size)
            {
                itemstack = this.theInventory[num];
                this.theInventory[num] = null;
                this.markDirty();
                return itemstack;
            }
            else
            {
                itemstack = this.theInventory[num].splitStack(size);

                if (this.theInventory[num].stackSize == 0)
                {
                    this.theInventory[num] = null;
                }

                this.markDirty();
                return itemstack;
            }
        }
        else
        {
            return null;
        }
//		if(this.theInventory[i]!=null){
//			ItemStack is = this.theInventory[i];
//			this.theInventory[i] = null;
//			return is;
//		}
//		return null;
	}

	@Override
	public ItemStack removeStackFromSlot(int i) {
		// TODO 自動生成されたメソッド・スタブ
		if(this.theInventory[i]!=null){
			ItemStack is = this.theInventory[i];
			this.theInventory[i] = null;
			return is;
		}
		return null;
	}

	@Override
	public void setInventorySlotContents(int i, ItemStack itemstack) {

		HSLib.logger.trace("setslot", i,itemstack);
		this.theInventory[i] = itemstack;

        if (itemstack != null && itemstack.stackSize > this.getInventoryStackLimit())
        {
            itemstack.stackSize = this.getInventoryStackLimit();
        }


	}





	@Override
	public int getInventoryStackLimit() {
		// TODO 自動生成されたメソッド・スタブ
		return 64;
	}



	@Override
	public boolean isUseableByPlayer(EntityPlayer entityplayer) {
		// TODO 自動生成されたメソッド・スタブ

		return false;
	}



	@Override
	public void closeInventory(EntityPlayer playerIn) {


	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemstack) {
		// TODO 自動生成されたメソッド・スタブ
		return false;
	}

	@Override
	public String getName() {
		// TODO 自動生成されたメソッド・スタブ
		return "unknown";
	}

	@Override
	public boolean hasCustomName() {
		// TODO 自動生成されたメソッド・スタブ
		return false;
	}

	@Override
	public void markDirty() {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public void openInventory(EntityPlayer playerIn) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public ITextComponent getDisplayName() {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	public int getField(int id) {
		// TODO 自動生成されたメソッド・スタブ
		return 0;
	}

	@Override
	public void setField(int id, int value) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public int getFieldCount() {
		// TODO 自動生成されたメソッド・スタブ
		return 0;
	}

	@Override
	public void clear() {
		// TODO 自動生成されたメソッド・スタブ

	}
}
