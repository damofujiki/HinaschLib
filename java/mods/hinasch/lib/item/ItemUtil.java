package mods.hinasch.lib.item;

import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

import com.google.common.collect.Lists;

import mods.hinasch.lib.core.HSLib;
import mods.hinasch.lib.iface.BiConsumer;
import mods.hinasch.lib.util.UtilNBT;
import mods.hinasch.lib.world.WorldHelper;
import mods.hinasch.lib.world.XYZPos;
import mods.hinasch.unsaga.UnsagaMod;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;

public class ItemUtil {

	public static final ItemStack EMPTY_STACK = new ItemStack(HSLib.core().items.itemAir);

	public static class ItemStackList {
		List<ItemStack> list;

		public ItemStackList(int size){
			list = Lists.newArrayList();
			for(int i=0;i<size;i++){
				list.add(i, EMPTY_STACK);
			}
		}

		public ItemStack[] toArray(){
			ItemStack[] iss = new ItemStack[list.size()];
			for(int i=0;i<list.size();i++){
				iss[i] = list.get(i)!=null && list.get(i)!=EMPTY_STACK ? list.get(i) : null;

			}
			return iss;
		}


		public void setToInventory(int startindex,IInventory inv){
			for(int i=0;i<this.size();i++){
				if(ItemUtil.isItemStackPresent(this.get(i))){
					inv.setInventorySlotContents(i + startindex, this.get(i));
				}

			}
		}
		public void clear(){
			int size = list.size();
			for(int i=0;i<size;i++){
				list.add(i,EMPTY_STACK);
			}
		}
		public static ItemStackList of(IInventory inv,int start,int size){
			ItemStackList list = new ItemStackList(size);
			for(int i=0;i<size;i++){
				if(ItemUtil.isItemStackPresent(inv.getStackInSlot(start+i))){
					list.setStack(i, inv.getStackInSlot(start+i));
				}
			}
			return list;
		}
		public static ItemStackList of(Collection<ItemStack> iss){
			ItemStackList list = new ItemStackList(iss.size());
			list.setStacks(Lists.newArrayList(iss));
			return list;
		}

		public static ItemStackList of(ItemStack[] iss){
			ItemStackList list = new ItemStackList(iss.length);
			list.fromArray(iss);
			return list;
		}
		public List<ItemStack> getRawList(){
			return this.list;
		}

		public ItemStackList getTrimmed(int size){
			ItemStackList list = new ItemStackList(size);
			for(int i=0;i<list.size();i++){
				list.setStack(i, this.get(i));
			}
			return list;
		}
		public boolean isSame(int slot,ItemStack is){
			if(is==null){
				return this.list.get(slot)==ItemUtil.EMPTY_STACK;
			}
			return this.list.get(slot)==is;
		}
		public void setStacks(List<ItemStack> list){
			this.list = list;
		}

		public void setStack(int slot,ItemStack is){
			this.list.set(slot, is!=null? is : EMPTY_STACK);
		}

		public void fromArray(ItemStack[] iss){
			for(int i=0;i<iss.length;i++){
				if(iss[i]!=null){
					this.setStack(i, iss[i]);
				}else{
					this.setStack(i, EMPTY_STACK);
				}
			}
		}

		@Override
		public String toString(){
			StringBuilder str = new StringBuilder("");
			for(int i=0;i<list.size();i++){
				if(list.get(i)==EMPTY_STACK || list.get(i)==null){
					str.append("EMPTY");

				}else{
					str.append(list.get(i).toString());
				}
			}
			str.append("/");
			return str.toString();
		}

		public int size(){
			return this.list.size();
		}
		public ItemStack getAllowNull(int slot){
			if(this.list.size()>slot){
				if(ItemUtil.isItemStackPresent(this.get(slot))){
					return this.get(slot);
				}
			}
			return null;
		}
		public ItemStack get(int slot){
			if(this.list.size()>slot){
				return this.list.get(slot);
			}
			return EMPTY_STACK;
		}

		public void writeToNBT(NBTTagList tagList) {
			for(int i=0;i<list.size();i++){
				if(ItemUtil.isItemStackPresent(list.get(i))){
					NBTTagCompound compound = new NBTTagCompound();
					compound.setByte("Slot", (byte)i);
					list.get(i).writeToNBT(compound);
					tagList.appendTag(compound);
//					UnsagaMod.logger.trace(this.getClass().getName(), compound);
				}
			}
			UnsagaMod.logger.trace(this.getClass().getName(), tagList);
//			stream.setTag("items", tagList);
		}

		public static ItemStackList readFromNBT(NBTTagList tagList,int length){
			UnsagaMod.logger.trace("itemstack load", tagList);
//			NBTTagList tagList = comp.getTagList("items", UtilNBT.NBKEY_TAGLIST);
			ItemStackList list = new ItemStackList(length);

	        for (int i = 0; i < tagList.tagCount(); ++i)
	        {

	            NBTTagCompound compound = (NBTTagCompound)tagList.getCompoundTagAt(i);
	            int slot = compound.getByte("Slot") & 255;
	            UnsagaMod.logger.trace("itemstack load", compound);

	            if (slot >= 0 && slot < list.size())
	            {
	            	list.setStack(slot, ItemStack.loadItemStackFromNBT(compound));
//	            	iss[j] = ItemStack.loadItemStackFromNBT(nbttagcompound1);

	            }
	        }
//	        ItemStackList list = new ItemStackList(length);
//	        list.fromArray(iss);
	        return list;
		}
	}
	public static EntityItem getEntityItem(ItemStack tablet,XYZPos pos,World world){
		return new EntityItem(world,pos.dx,pos.dy,pos.dz,tablet);
	}

	public static void dropItem(ItemStack dropItemStack,EntityPlayer ep){
		if(ItemUtil.isItemStackPresent(dropItemStack)){
			ep.entityDropItem(dropItemStack, 0.1F);

		}
	}
	public static boolean isItemStackPresent(ItemStack... stacks){
		return Lists.newArrayList(stacks).stream().allMatch(is -> !isItemStackNull(is));
	}
	public static boolean isItemStackNull(ItemStack is){
		return is==null || is==EMPTY_STACK;
	}
	public static boolean isItemStackNull(ItemStack... stacks){
		return Lists.newArrayList(stacks).stream().allMatch(in -> in==null || in==EMPTY_STACK);
	}
	public static int getStackSize(ItemStack is){
		return is.stackSize;
	}
	public static void setStackSize(ItemStack is,int stack){
		is.stackSize = stack;
	}
	public static void decrStackSize(ItemStack is,int stack){
		is.stackSize -= stack;
		if(is.stackSize<0){
			is.stackSize = 0;
		}
	}
	public static void setStackNull(ItemStack is){
		is = null;
	}
	public static void dropBlockAsItem(World world,XYZPos pos,IBlockState state,int fortune){
		dropBlockAsItem(world, pos, state.getBlock(),state, fortune);
	}

	public static void dropBlockAsItem(World world,XYZPos pos,Block block,IBlockState state,int fortune){
		block.dropBlockAsItem(world, pos, state, fortune);
	}

	public static void dropItem(final EntityPlayer ep,final float offSetY,Stream<ItemStack> itemList){

		itemList.filter(is -> is!=null).forEach(is -> ep.entityDropItem(is, offSetY));
	}
	/**
	 * nullチェックあり。
	 * @param world
	 * @param itemstack
	 * @param x
	 * @param y
	 * @param z
	 */
	public static void dropItem(World world,ItemStack itemstack,double x,double y,double z){

		Random rand = world.rand;
		if(itemstack!=null){
			EntityItem item = new EntityItem(world, x,y,z,itemstack);
			item.setDefaultPickupDelay();
			if(WorldHelper.isServer(world)){
				world.spawnEntityInWorld(item);
			}
		}

		return;
	}

	public static void dropItem(World world,ItemStack itemstack,XYZPos xyz){
		dropItem(world, itemstack, xyz.dx, xyz.dy, xyz.dz);
		return;
	}

	/**
	 * 秒をポーションタイムに変換
	 * @param sec
	 * @return
	 */
	public static int getPotionTime(int sec){
		return sec*20;
	}

	public static void removePotionEffects(EntityLivingBase living,Potion... potions){
		for(Potion potion:potions){
			living.removePotionEffect(potion);
		}
	}

	public static void addPotionIfLiving(Entity entity,PotionEffect potionEffect){
		if(entity instanceof EntityLivingBase){
			EntityLivingBase el = (EntityLivingBase)entity;
			el.addPotionEffect(potionEffect);
		}
	}

	@Deprecated
	public static boolean isSameClass(ItemStack is,Class _class){

			if(_class.isInstance(is.getItem())){
				return true;
			}
			if(is.getItem().getClass()==_class){
				return true;
			}

		return false;

	}

	public static List<String> getOreNames(ItemStack is){
		List<String> list = Lists.newArrayList();
		if(ItemUtil.isItemStackPresent(is)){
			for(int id:OreDictionary.getOreIDs(is)){
				String name = OreDictionary.getOreName(id);
				list.add(name);
			}
		}
		return list;
	}

	public static boolean isOreDict(ItemStack is,String name){
		return getOreNames(is).stream().anyMatch(in -> in.equals(name));
	}
	@Deprecated
	public static boolean hasItemInstance(EntityLivingBase player,Class _class){
		if(player.getHeldItemMainhand()!=null){
			if(_class.isInstance(player.getHeldItemMainhand().getItem())){
				return true;
			}
			if(player.getHeldItemMainhand().getItem().getClass()==_class){
				return true;
			}
		}
		return false;

	}

    public static void saveItemStacksToItemNBT(ItemStack binder,ItemStack[] maps){
    	UtilNBT.initNBTIfNotInit(binder);
    	NBTTagCompound nbt = binder.getTagCompound();
    	NBTTagList tagList = UtilNBT.newTagList();
    	UtilNBT.writeItemStacksToNBTTag(tagList, maps);
    	nbt.setTag("items", tagList);
    	binder.setTagCompound(nbt);
    }

    public static ItemStack[] loadItemStacksFromItemNBT(ItemStack is,int max){
    	UtilNBT.initNBTIfNotInit(is);
    	if(UtilNBT.hasKey(is, "items")){
    		NBTTagList tagList = UtilNBT.getTagList(is.getTagCompound(), "items");
    		return UtilNBT.getItemStacksFromNBT(tagList, max);
    	}
    	return null;
    }

    public static void clearItemStacksInItemNBT(ItemStack is,int max){
    	ItemStack[] newStacks = new ItemStack[max];
    	saveItemStacksToItemNBT(is,newStacks);
    }

    public static float getRandomPitch(Random rand){
    	return  0.4F / rand.nextInt()* 0.4F + 0.8F;
    }

    @Deprecated
    public static void registerItems(Item[] items,List<String> names){
    	int index = 0;
    	for(Item item:items){
    		GameRegistry.registerItem(item, names.get(index));
    		index += 1;
    	}
    }


    @Deprecated
    public static BiConsumer<ItemStack,EntityPlayer> getItemDropConsumer(){
    	return new BiConsumer<ItemStack,EntityPlayer>(){

			@Override
			public void accept(ItemStack left, EntityPlayer right) {
				ItemUtil.dropItem(left, right);

			}};
    }


}
