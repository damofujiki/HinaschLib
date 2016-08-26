package mods.hinasch.lib.container.inventory;

import mods.hinasch.lib.capability.CapabilityInventory;
import mods.hinasch.lib.core.HSLib;
import mods.hinasch.lib.iface.ILockable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;


public abstract class AbstractInventoryBinder<T> extends InventoryBase{


	public T binderObject;
	public final int stackMax;
	public AbstractInventoryBinder(boolean isRemote,T obj,int max) {
		super(max);
//		HSLib.logger.trace("isRemote", isRemote);
		this.binderObject = obj;
		this.stackMax = max;
		if(!isRemote){
			this.loadFromNBT();
		}


	}

	public abstract T getBinderItem();

	public abstract void loadFromNBT();

	public abstract void saveToNBT(T binder);


	public static class BinderStack extends AbstractInventoryBinder<ItemStack>{

		public BinderStack(boolean isRemote, ItemStack is, int max) {
			super(isRemote, is, max);
			// TODO 自動生成されたコンストラクター・スタブ
		}
		@Override
		public ItemStack getBinderItem(){
			return this.binderObject;
		}

		@Override
		public boolean isUseableByPlayer(EntityPlayer entityplayer) {
			// TODO 自動生成されたメソッド・スタブ

			if(this.getBinderItem()!=null){
				if(entityplayer.getHeldItemMainhand()!=null && entityplayer.getHeldItemMainhand().getItem()==this.getBinderItem().getItem()){
					return true;
				}
			}

			return false;
		}

		@Override
		public void saveToNBT(ItemStack binder){

			if(CapabilityInventory.adapter.hasCapability(binderObject)){
				CapabilityInventory.adapter.getCapability(binderObject).setStacks(theInventory);
			}
			//				ItemUtil.saveItemStacksToItemNBT((ItemStack) binder,theInventory);


		}
		@Override
		public void loadFromNBT() {
			ItemStack[] maps = null;

			if(CapabilityInventory.adapter.hasCapability((ItemStack) binderObject)){
				maps = CapabilityInventory.adapter.getCapability((ItemStack) binderObject).getStacks();

			}

			if(maps!=null){
				for(int i=0;i<this.getSizeInventory();i++){
					if(maps[i]!=null){
						this.setInventorySlotContents(i, maps[i]);
					}

				}
			}


		}
	}

	public static class BinderTileEntity extends AbstractInventoryBinder<TileEntity>{


		public BinderTileEntity(boolean isRemote, TileEntity is, int max) {
			super(isRemote, is, max);
			// TODO 自動生成されたコンストラクター・スタブ
		}

		@Override
		public TileEntity getBinderItem(){
			return this.binderObject;
		}

		@Override
		public boolean isUseableByPlayer(EntityPlayer entityplayer) {

			if(this.getBinderItem()!=null){
				if(this.getBinderItem() instanceof ILockable){
					ILockable lockable = (ILockable) this.getBinderItem();
					if(lockable.getUser()==entityplayer.getUniqueID()){
						return true;
					}
				}
			}

			return false;
		}

		@Override
		public void saveToNBT(TileEntity binder){

			if(CapabilityInventory.adapterTE.hasCapability(binderObject)){
				HSLib.logger.trace("save", binder);
				CapabilityInventory.adapterTE.getCapability(binderObject).setStacks(theInventory);
			}
			//				ItemUtil.saveItemStacksToItemNBT((ItemStack) binder,theInventory);


		}

		@Override
		public void loadFromNBT() {
			ItemStack[] maps = null;



			if(CapabilityInventory.adapterTE.hasCapability(binderObject)){
				HSLib.logger.trace("save", binderObject);
				maps = CapabilityInventory.adapterTE.getCapability(binderObject).getStacks();
			}

			if(maps!=null){
				for(int i=0;i<this.getSizeInventory();i++){
					if(maps[i]!=null){
						this.setInventorySlotContents(i, maps[i]);
					}

				}
			}


		}
	}
}
