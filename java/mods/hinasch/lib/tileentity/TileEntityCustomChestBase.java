//package mods.hinasch.lib.tileentity;
//
//import net.minecraft.block.BlockChest;
//import net.minecraft.entity.player.EntityPlayer;
//import net.minecraft.inventory.IInventory;
//import net.minecraft.item.ItemStack;
//import net.minecraft.nbt.NBTTagCompound;
//import net.minecraft.nbt.NBTTagList;
//import net.minecraft.network.NetworkManager;
//import net.minecraft.network.play.server.SPacketUpdateTileEntity;
//import net.minecraft.util.EnumFacing;
//import net.minecraft.util.ITickable;
//import net.minecraft.util.text.ITextComponent;
//
//public class TileEntityCustomChestBase extends TileEntityMultiFacing implements IInventory,ITickable{
//
//	private ItemStack[] chestContents = new ItemStack[36];
//	protected Boolean[] isSet = new Boolean[36];
//	public int numPlayersUsing;
//
//
//	private int ticksSinceSync;
//	private int cachedChestType;
//	protected float per = 0F;
//	public String customName;
//	public TileEntityCustomChestBase(){
//		this.setOrientation(EnumFacing.NORTH);
//
//		for(int i=0;i<this.isSet.length;i++){
//			this.isSet[i] = false;
//		}
//	}
//
//
//
//	public float getAmountAsPercentage(){
//		return this.per;
//	}
//
//
//
//
//	@Override
//	public int getSizeInventory() {
//		// TODO 自動生成されたメソッド・スタブ
//		return 27;
//	}
//
//	@Override
//	public ItemStack getStackInSlot(int var1) {
//		// TODO 自動生成されたメソッド・スタブ
//		return this.chestContents[var1];
//	}
//
//	@Override
//	public ItemStack decrStackSize(int par1, int par2) {
//		if (this.chestContents[par1] != null)
//		{
//			ItemStack itemstack;
//
//			if (this.chestContents[par1].stackSize <= par2)
//			{
//				itemstack = this.chestContents[par1];
//				this.chestContents[par1] = null;
//				this.markDirty();
//				return itemstack;
//			}
//			else
//			{
//				itemstack = this.chestContents[par1].splitStack(par2);
//
//				if (this.chestContents[par1].stackSize == 0)
//				{
//					this.chestContents[par1] = null;
//				}
//
//				this.markDirty();
//				return itemstack;
//			}
//		}
//		else
//		{
//			return null;
//		}
//	}
//
//	@Override
//	public ItemStack removeStackFromSlot(int par1) {
//		if (this.chestContents[par1] != null)
//		{
//			ItemStack itemstack = this.chestContents[par1];
//			this.chestContents[par1] = null;
//			return itemstack;
//		}
//		else
//		{
//			return null;
//		}
//	}
//
//	@Override
//	public void setInventorySlotContents(int par1, ItemStack par2ItemStack) {
//		this.chestContents[par1] = par2ItemStack;
//
//		if (par2ItemStack != null && par2ItemStack.stackSize > this.getInventoryStackLimit())
//		{
//			par2ItemStack.stackSize = this.getInventoryStackLimit();
//		}
//
//		this.markDirty();
//
//	}
//
//	@Override
//	public String getName() {
//		return this.hasCustomName() ? this.customName : "container.rack";
//	}
//
//	@Override
//	public boolean hasCustomName() {
//		return this.customName != null && this.customName.length() > 0;
//	}
//
//	@Override
//	public int getInventoryStackLimit() {
//		return 64;
//	}
//
//	@Override
//	public boolean isUseableByPlayer(EntityPlayer par1EntityPlayer) {
//		return this.worldObj.getTileEntity(this.getPos()) != this ? false : par1EntityPlayer.getDistanceSq((double)this.getPos().getX() + 0.5D, (double)this.getPos().getY() + 0.5D, (double)this.getPos().getZ() + 0.5D) <= 64.0D;
//	}
//
//	@Override
//	public void openInventory(EntityPlayer ep) {
//		if (this.numPlayersUsing < 0)
//		{
//			this.numPlayersUsing = 0;
//		}
//
//		++this.numPlayersUsing;
//		this.worldObj.addBlockEvent(this.getPos(), this.getBlockType(), 1, this.numPlayersUsing);
//		this.worldObj.notifyBlockOfStateChange(this.getPos(), this.getBlockType());
//
//	}
//
//	@Override
//	public void closeInventory(EntityPlayer ep) {
//		if (this.getBlockType() instanceof BlockChest)
//		{
//			--this.numPlayersUsing;
//			this.worldObj.addBlockEvent(this.getPos(), this.getBlockType(), 1, this.numPlayersUsing);
//			this.worldObj.notifyBlockOfStateChange(this.getPos(), this.getBlockType());
//		}
//		int amount = 0;
//		for(int i=0;i<this.getSizeInventory();i++){
//			if(this.chestContents[i]!=null){
//				amount += 1;
//			}
//		}
//		this.per = (float)amount/(float)this.getSizeInventory();
//
//	}
//
//	@Override
//	public boolean isItemValidForSlot(int var1, ItemStack var2) {
//		// TODO 自動生成されたメソッド・スタブ
//		return true;
//	}
//
//
//	@Override
//	public void update(){
//		++this.ticksSinceSync;
//		float f;
//
//		if (!this.worldObj.isRemote && this.numPlayersUsing != 0 && (this.ticksSinceSync + this.getPos().getX() + this.getPos().getY() + this.getPos().getZ()) % 200 == 0)
//		{
//			this.numPlayersUsing = 0;
//
//		}
//	}
//
//	@Override
//	public void writeToNBT(NBTTagCompound nbt)
//	{
//		super.writeToNBT(nbt);
//		NBTTagList nbttaglist = new NBTTagList();
//
//		for (int i = 0; i < this.chestContents.length; ++i)
//		{
//			if (this.chestContents[i] != null)
//			{
//				NBTTagCompound nbttagcompound1 = new NBTTagCompound();
//				nbttagcompound1.setByte("Slot", (byte)i);
//				this.chestContents[i].writeToNBT(nbttagcompound1);
//				nbttaglist.appendTag(nbttagcompound1);
//
//
//			}
//
//
//		}
//
//		nbt.setTag("Items", nbttaglist);
//
//		if (this.hasCustomName())
//		{
//			nbt.setString("CustomName", this.customName);
//		}
//
//
//
//		nbt.setFloat("percentage", this.per);
//
//	}
//
//	@Override
//	public void readFromNBT(NBTTagCompound nbt)
//	{
//		super.readFromNBT(nbt);
//		NBTTagList nbttaglist = nbt.getTagList("Items", 10);
//		this.chestContents = new ItemStack[this.getSizeInventory()];
//
//		if (nbt.hasKey("CustomName", 8))
//		{
//			this.customName = nbt.getString("CustomName");
//		}
//
//		for (int i = 0; i < nbttaglist.tagCount(); ++i)
//		{
//			NBTTagCompound nbttagcompound1 = nbttaglist.getCompoundTagAt(i);
//			int j = nbttagcompound1.getByte("Slot") & 255;
//
//			if (j >= 0 && j < this.chestContents.length)
//			{
//				this.chestContents[j] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
//			}
//		}
//
//		this.per = nbt.getFloat("percentage");
//	}
//
//
//
//
//	@Override
//	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt)
//	{
//		super.onDataPacket(net, pkt);
//		this.per = pkt.getNbtCompound().getFloat("percentage");
//		NBTTagList nbttaglist = pkt.getNbtCompound().getTagList("Items", 10);
//		//NewBlockMod.log(nbttaglist.tagCount());
//		for (int i = 0; i < nbttaglist.tagCount(); ++i)
//		{
//			NBTTagCompound nbttagcompound1 = nbttaglist.getCompoundTagAt(i);
//			int j = nbttagcompound1.getByte("Slot") & 255;
//			this.isSet[j] = true;
//		}
//	}
//
//
//
//	@Override
//	public ITextComponent getDisplayName() {
//		// TODO 自動生成されたメソッド・スタブ
//		return null;
//	}
//
//
//
//
//
//	@Override
//	public int getField(int id) {
//		// TODO 自動生成されたメソッド・スタブ
//		return 0;
//	}
//
//
//
//	@Override
//	public void setField(int id, int value) {
//		// TODO 自動生成されたメソッド・スタブ
//
//	}
//
//
//
//	@Override
//	public int getFieldCount() {
//		// TODO 自動生成されたメソッド・スタブ
//		return 0;
//	}
//
//
//
//	@Override
//	public void clear() {
//		// TODO 自動生成されたメソッド・スタブ
//
//	}
//
//}
