package mods.hinasch.lib.container;

import mods.hinasch.lib.container.inventory.AbstractInventoryBinder;
import mods.hinasch.lib.core.HSLib;
import mods.hinasch.lib.network.PacketGuiButtonBaseNew;
import mods.hinasch.lib.world.WorldHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;

/**
 * バインダーの基礎クラス。GUIはguicontainerbaseを使う
 *
 *
 */
public abstract class AbstractContainerBinder<T> extends ContainerBase{

	protected AbstractInventoryBinder<T> invBinder;
	protected IInventory invEP;
	protected T binderObject;
	public AbstractContainerBinder(EntityPlayer ep,T is,AbstractInventoryBinder<T> inv) {
		super(ep,inv);
		this.invBinder = (AbstractInventoryBinder<T>) inv;
		this.invEP = ep.inventory;
		this.binderObject = is;


		for(int i=0;i<this.inv.getSizeInventory();i++){
			if(i>=0 && i<8){
				this.addSlotToContainer(this.getBinderSlot(this.inv , i, 18+(i* 18), 53-(18*2)));
			}else{
				this.addSlotToContainer(this.getBinderSlot(this.inv , i, 18+((i-8)* 18), 53-(18*1)));
			}

		}
		this.spreadSlotItems = false;
	}

	public abstract Slot getBinderSlot(IInventory inv,int par1,int par2,int par3);

	public abstract T getBinderItem();

	@Override
	public boolean canInteractWith(EntityPlayer var1) {
		// TODO 自動生成されたメソッド・スタブ
		return inv.isUseableByPlayer(var1);
	}

	@Override
	public PacketGuiButtonBaseNew getPacketGuiButton(int guiID, int buttonID,
			NBTTagCompound args) {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	public SimpleNetworkWrapper getPacketPipeline() {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	public int getGuiID() {
		// TODO 自動生成されたメソッド・スタブ
		return 0;
	}

	@Override
	public void onPacketData() {
		// TODO 自動生成されたメソッド・スタブ

	}






	public static abstract class BinderStack extends AbstractContainerBinder<ItemStack>{



		public BinderStack(EntityPlayer ep, ItemStack is, AbstractInventoryBinder inv) {
			super(ep, is, inv);
			// TODO 自動生成されたコンストラクター・スタブ
		}

		@Override
		public void onContainerClosed(EntityPlayer par1EntityPlayer)
		{

			if(!ep.worldObj.isRemote){

				if(this.getBinderItem()!=null){
					if(ep.getHeldItemMainhand()!=null){
						this.invBinder.saveToNBT(ep.getHeldItemMainhand());
						//par1EntityPlayer.entityDropItem(binderStack, 0.1F);
					}
				}
				super.onContainerClosed(par1EntityPlayer);
			}
		}
		@Override
		public ItemStack getBinderItem(){
			return this.binderObject;
		}
		/**
		 * アイテム内コンテナの場合はそのアイテムを動かせないようにする
		 */
		@Override
		public ItemStack slotClick(int par1, int dragType, ClickType clickTypeIn, EntityPlayer player)
		{

			if(par1>0){
				HSLib.logger.trace("slotclick", WorldHelper.debug(player.worldObj));
				if(this.getBinderItem()!=null){
					if(this.getSlot(par1).getStack()!=null){
						ItemStack is = this.getSlot(par1).getStack();
						if(is.getItem()==this.getBinderItem().getItem()){
							return null;
						}
					}
				}

			}

			return super.slotClick(par1, dragType, clickTypeIn, player);
		}
	}

	public static abstract class BinderTileEntity extends AbstractContainerBinder<TileEntity>{

		public BinderTileEntity(EntityPlayer ep, TileEntity is, AbstractInventoryBinder inv) {
			super(ep, is, inv);
//			HSLib.logger.trace("inv", inv);
		}

		@Override
		public void onContainerClosed(EntityPlayer par1EntityPlayer)
		{

			if(!ep.worldObj.isRemote){

				if(this.getBinderItem()!=null){
					this.invBinder.saveToNBT(binderObject);
				}

			}


			super.onContainerClosed(par1EntityPlayer);
		}


		@Override
		public TileEntity getBinderItem(){
			return this.binderObject;
		}

		@Override
		public ItemStack slotClick(int par1, int dragType, ClickType clickTypeIn, EntityPlayer player)
		{
			if(par1>0){
				HSLib.logger.trace("item", this.inv,this.getSlot(par1).getStack());
			}
			return super.slotClick(par1, dragType, clickTypeIn, player);
		}
	}
}
