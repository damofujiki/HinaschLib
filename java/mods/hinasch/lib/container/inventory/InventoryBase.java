package mods.hinasch.lib.container.inventory;

import mods.hinasch.lib.item.ItemUtil;
import mods.hinasch.lib.item.ItemUtil.ItemStackList;
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
	protected ItemStackList theInventory;



	public InventoryBase(int size){

		this.theInventory = new ItemStackList(size);
	}

	@Override
	public int getSizeInventory() {
		// TODO 自動生成されたメソッド・スタブ
		return this.theInventory.getRawList().size();
	}

	@Override
	public ItemStack getStackInSlot(int i) {

		return this.theInventory.get(i)!=ItemUtil.EMPTY_STACK?this.theInventory.get(i) : null;
	}

	@Override
	public ItemStack decrStackSize(int num, int size) {
        if (this.theInventory.get(num) != ItemUtil.EMPTY_STACK)
        {
            ItemStack itemstack;

            if (this.theInventory.get(num).stackSize <= size)
            {
                itemstack = this.theInventory.get(num);
                this.theInventory.setStack(num, ItemUtil.EMPTY_STACK);
                this.markDirty();
                return itemstack!=ItemUtil.EMPTY_STACK ? itemstack : null;
            }
            else
            {
                itemstack = this.theInventory.get(num).splitStack(size);

                if (this.theInventory.get(num).stackSize == 0)
                {
                    this.theInventory.setStack(num, ItemUtil.EMPTY_STACK);
                }

                this.markDirty();
                return itemstack!=ItemUtil.EMPTY_STACK ? itemstack : null;
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
		if(this.theInventory.get(i)!=ItemUtil.EMPTY_STACK){
			ItemStack is = this.theInventory.get(i);
			this.theInventory.setStack(i, ItemUtil.EMPTY_STACK);
			return is!=ItemUtil.EMPTY_STACK ? is : null;
		}
		return null;
	}

	@Override
	public void setInventorySlotContents(int i, ItemStack itemstack) {

//		HSLib.logger.trace("setslot", i,itemstack);
		this.theInventory.setStack(i, itemstack);

        if (itemstack!=ItemUtil.EMPTY_STACK&& itemstack != null && itemstack.stackSize > this.getInventoryStackLimit())
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
