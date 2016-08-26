package mods.hinasch.lib.container;

import mods.hinasch.lib.core.HSLib;
import mods.hinasch.lib.iface.IIconItem;
import mods.hinasch.lib.network.PacketChangeGuiMessage;
import mods.hinasch.lib.network.PacketGuiButtonBaseNew;
import mods.hinasch.lib.util.SoundAndSFX;
import mods.hinasch.lib.world.WorldHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;



/**
 * GuiconatinerBaseとセットで使う。
 *
 *
 */
public abstract class ContainerBase extends Container{

	public EntityPlayer ep;
	public IInventory inv;
	/**
	 * Guiが閉じた時にアイテムをぶちまけるかどうか。
	 * 通常はtrue
	 */
	public boolean spreadSlotItems = true;
	public int buttonID;
	/**
	 * パケットで送られた付加情報はここに入る。
	 */
	public NBTTagCompound argsSent;

	public ContainerBase(EntityPlayer ep,IInventory inv){

		this.ep = ep;
		this.inv = inv;


		if(this.isShowPlayerInv()){
			for (int i = 0; i < 3; ++i)
			{
				for (int j = 0; j < 9; ++j)
				{
					this.addSlotToContainer(new Slot(ep.inventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
				}
			}

			for (int i = 0; i < 9; ++i)
			{
				this.addSlotToContainer(new Slot(ep.inventory, i, 8 + i * 18, 142));
			}
		}

	}

	/**
	 * プレイヤーのインベントリを表示するかどうか
	 * @return
	 */
	public boolean isShowPlayerInv(){
		return true;
	}

	public void playClickSound(){
		if(WorldHelper.isClient(this.ep.worldObj)){
			SoundAndSFX.playPositionedSoundRecord(SoundEvents.UI_BUTTON_CLICK, 1.0F);
		}
	}
	@Override
	public boolean canInteractWith(EntityPlayer var1) {
		// TODO 自動生成されたメソッド・スタブ
		return inv.isUseableByPlayer(var1);
	}

	public void onButtonPushed(int id,NBTTagCompound args){
		PacketGuiButtonBaseNew packetButton = this.getPacketGuiButton(this.getGuiID(), id,args);
		this.getPacketPipeline().sendToServer(packetButton);
	}

	/**
	 * 別にPacketGuiButtonBaseを継承したのを作ってそれを返してください。
	 * @param guiID
	 * @param buttonID
	 * @param args
	 * @return
	 */
	public abstract PacketGuiButtonBaseNew getPacketGuiButton(int guiID,int buttonID,NBTTagCompound args);

	/**
	 * ベースクラスのシンプルネットワークラッパーを返してください。
	 * @return
	 */
	public abstract SimpleNetworkWrapper getPacketPipeline();

	/**
	 * 固有のGuiID
	 * @return
	 */
	public abstract int getGuiID();


	/**
	 * transfer....(Slot slot)をみてください
	 */
	@Override
	public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int par2)
	{

		return this.transferStackInSlot((Slot) this.inventorySlots.get(par2));
	}

	/**
	 * 事故防止にnullを返してます（初期値）
	 */
	public ItemStack transferStackInSlot(Slot slot)
	{
		return null;
	}
	/**
	 * パケットが送られてきた時に実行される。
	 *
	 */
	public abstract void onPacketData();

	public void readPacketData(int buttonID,NBTTagCompound args){
		this.buttonID = buttonID;

		this.argsSent = args;

	}
	public boolean isSpereadSlotItemsOnContainerClosed(EntityPlayer par1EntityPlayer,ItemStack is){
		return this.isSpereadSlotItemsOnContainerClosed(par1EntityPlayer);
	}
	public boolean isSpereadSlotItemsOnContainerClosed(EntityPlayer par1EntityPlayer){
		return this.spreadSlotItems;
	}
	@Override
	public void onContainerClosed(EntityPlayer par1EntityPlayer)
	{

		super.onContainerClosed(par1EntityPlayer);

		if(!par1EntityPlayer.worldObj.isRemote && this.inv!=null){
			for(int i=0;i<this.inv.getSizeInventory();i++){
				ItemStack is = this.inv.getStackInSlot(i);
				if(this.isSpereadSlotItemsOnContainerClosed(par1EntityPlayer, is)){

					if(is!=null && !(is.getItem() instanceof IIconItem)){
						par1EntityPlayer.entityDropItem(is,0.1F);
					}
				}

			}
		}

	}


	public void changeMessage(EntityPlayer sender,String message){
		if(ep instanceof EntityPlayerMP){
			HSLib.getPacketDispatcher().sendTo(PacketChangeGuiMessage.create(message), (EntityPlayerMP) ep);
		}
	}
	/**
	 *
	 * @param rawSlotNumber　そのままのスロット番号
	 * @param containerSlotNumber　プレイヤーインベントリのスロットを引いた番号
	 * @param clickButton
	 * @param mode
	 * mode (0 = basic click, 1 = shift click, 2 = Hotbar, 3 =
	 * pickBlock, 4 = Drop, 5 = ?, 6 = Double click)
	 *
	 * @param ep
	 */
	public void onSlotClick(int rawSlotNumber,int containerSlotNumber,int clickButton,ClickType clickTypeIn,EntityPlayer ep){


	}
	@Override
	public ItemStack slotClick(int par1, int dragType, ClickType clickTypeIn, EntityPlayer player)
	{
//		HSLib.logger.trace("slot", par1,dragType,clickTypeIn,player);
		if(par1>=0){
			this.onSlotClick(par1, par1-36,dragType, clickTypeIn, player);
		}

		return super.slotClick(par1, dragType, clickTypeIn, player);

	}
}
