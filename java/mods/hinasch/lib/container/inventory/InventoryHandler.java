package mods.hinasch.lib.container.inventory;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Stream;

import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import mods.hinasch.lib.DebugLog;
import mods.hinasch.lib.iface.Consumer;
import mods.hinasch.lib.primitive.ListHelper.BiPredicate;
import mods.hinasch.lib.primitive.ListHelper.Range;
import mods.hinasch.lib.util.HSLibs;
import mods.hinasch.lib.world.WorldHelper;
import mods.hinasch.lib.world.XYZPos;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;

public class InventoryHandler {


	public static final int INV_HELM = 0;
	public static final int INV_ARMOR  =1;
	public static final int INV_LEGGINS = 2;
	public static final int INV_BOOTS = 3;
	protected final IInventory inv;
	protected DebugLog logger;
	public InventoryHandler(IInventory par1){
		this.inv = par1;
	}


//	public static List<InventoryStatus> collect(IInventory par1,Range range){
//		List<InventoryStatus> list = new ArrayList();
//		for(int i=range.min;i<range.max;i++){
//			list.add(new InventoryStatus(i,par1.getStackInSlot(i),par1));
//		}
//		return list;
//	}


	public static InventoryHandler create(IInventory par1){
		return new InventoryHandler(par1);
	}
	public IInventory getInv(){
		return this.inv;
	}
	public boolean forEach(Predicate<InventoryStatus> consumer,Range range){
		for(int i=range.min;i<range.max;i++){
			if(consumer.apply(new InventoryStatus(i,inv.getStackInSlot(i),inv))){
				return true;
			}
		}
		return false;
	}


	public Stream<ItemStack> toItemStackStream(Range range){
		List<ItemStack> list = Lists.newArrayList();
		for(int i=range.min;i<range.max;i++){
			if(this.getInv().getStackInSlot(i)!=null){
				list.add(this.getInv().getStackInSlot(i));
			}

		}
		return list.stream();
	}
	public Stream<InventoryStatus> toStream(Range range){
		List<InventoryStatus> list = Lists.newArrayList();
		for(int i=range.min;i<range.max;i++){
			list.add(new InventoryStatus(i,inv.getStackInSlot(i),inv));
		}
		return list.stream();
	}
	public void forEach(Consumer<InventoryStatus> consumer,Range range){
		for(int i=range.min;i<range.max;i++){
			consumer.accept(new InventoryStatus(i,inv.getStackInSlot(i),inv));
		}
	}
	public InventoryHandler setLogger(DebugLog logger){
		this.logger = logger;
		return this;
	}
	public static void decrCurrentHeldItem(EntityPlayer ep,int decr){
		if(!ep.capabilities.isCreativeMode){
			IInventory invp = ep.inventory;
			int current = ep.inventory.currentItem;
			invp.decrStackSize(current, decr);
			return;
		}


	}
	public ItemStack getFirstInv(){
		return this.inv.getStackInSlot(0);
	}


	public void setToFirstEmptySlot(ItemStack is){
		if(this.getFirstEmptySlotNum().isPresent()){
			this.inv.setInventorySlotContents(this.getFirstEmptySlotNum().get(), is);
		}
	}

	public void swapFirstEmptySlot(Slot slot){
		this.setToFirstEmptySlot(slot.getStack());
		slot.putStack(null);
	}


	public void mergeSlot(Slot slot,int invnum){
		int max = this.inv.getInventoryStackLimit();
		ItemStack is = slot.getStack();
		if(slot.getStack().stackSize<=max){
			this.inv.setInventorySlotContents(invnum, is);
			slot.putStack(null);
		}else{
			ItemStack isInv = slot.getStack().copy();
			slot.getStack().stackSize -= max;
			isInv.stackSize = max;
			this.inv.setInventorySlotContents(invnum, isInv);
		}
	}

	public Optional<InventoryStatus> getFirstStackEqualSlot(ItemStack is){
		List<InventoryStatus> list = this.getInvItems(status -> status.getStack()!=null && status.getStack().isItemEqual(is));
		if(list.isEmpty()){
			return Optional.absent();
		}
		return Optional.of(list.get(0));
	}
	public Optional<Integer> getFirstEmptySlotNum(){
		for(int i=0;i<this.inv.getSizeInventory();i++){
			if(this.inv.getStackInSlot(i)==null){
				if(this.inv instanceof InventoryPlayer){
					int num = ((InventoryPlayer) this.inv).getFirstEmptyStack();
					return num<0  ? Optional.absent() : Optional.of(num);
				}
				return Optional.of(i);
			}
		}
		return Optional.absent();
	}

	public Optional<Integer> getEmptySlots(int... invnum){

		for(int num:invnum){
			if(this.inv.getStackInSlot(num)==null){
				return Optional.of(num);
			}
		}
		return Optional.absent();
	}

	public Optional<Integer> getEmptySlotInMinToMax(int min,int max){

		for(int i=min;i<max;i++){
			if(this.inv.getStackInSlot(i)==null){
				return Optional.of(i);
			}
		}
		return Optional.absent();
	}

	public boolean contains(ItemStack is){
		boolean fl = false;
		for(int i=0;i<this.inv.getSizeInventory();i++){
			ItemStack chestis = this.inv.getStackInSlot(i);
			if(chestis!=null && is.isItemEqual(chestis)){
				fl = true;
			}
		}
		return fl;
	}

	public void addItemStack(ItemStack is){
		if(!this.isInvFull()){
			for(int i=0;i<inv.getSizeInventory();i++){
				if(inv.getStackInSlot(i)==null){
					inv.setInventorySlotContents(i,is);
					return;
				}
			}
		}
	}
	public boolean isInvFull(){
		int inSlot = 0;
		for(int i=0;i<inv.getSizeInventory();i++){
			if(inv.getStackInSlot(i)!=null){
				inSlot +=1;
			}
		}
		if(inv.getSizeInventory()<=inSlot){
			return true;
		}
		return false;
	}
	public int getAmount(ItemStack is){
		int stack = 0;
		for(int i=0;i<this.inv.getSizeInventory();i++){
			ItemStack chestis = this.inv.getStackInSlot(i);
			if(chestis!=null && is.isItemEqual(chestis)){
				stack += this.inv.getStackInSlot(i).stackSize;
			}
		}
		return stack;
	}

//	/**
//	 *  sensitive version ->getInvItems(CustomCheck checker)
//	 *  <br>インヴェントリ番号、アイテムがセットになったMapを返す。
//	 * @param is
//	 * @param avoidDamage
//	 * @return
//	 */
//	public List<InventoryStatus> getInvItems(final ItemStack is,final boolean avoidDamage){
//
//
//	}


	public List<InventoryStatus> getInvItems(final ItemStack other,final boolean avoidDamage){
		//final ItemStack itemStackIn = is;
		return this.getInvItems(is ->{
			if(other!=null){
				if(avoidDamage){
					return is.getStack().getItem()==other.getItem();
				}else{
					return is.getStack().isItemEqual(other);
				}
			}
			return false;
		});
	}

	/**
	 * check out CustomCheck
	 * センシティヴ版、普通はgetInvItems(ItemStack is,boolean avoidDamage)を使う
	 * @param checker
	 * @return
	 */
//	public Map<Integer,ItemStack> getInvItems(CustomCheck checker){
//		Map<Integer,ItemStack> map = new HashMap();
//		for(int i=0;i<this.inv.getSizeInventory();i++){
//			ItemStack chestis = this.inv.getStackInSlot(i);
//			if(chestis!=null){
//				if(checker.check(chestis)){
//					map.put(i, chestis);
//				}
//
//			}
//
//		}
//		return map;
//	}

	public List<InventoryStatus> getInvItems(BiPredicate<ItemStack,Integer> checker){
		List<InventoryStatus> map = new ArrayList();
		for(int i=0;i<this.inv.getSizeInventory();i++){
			ItemStack chestis = this.inv.getStackInSlot(i);
			if(chestis!=null){
				if(checker.apply(chestis, i)){
					map.add(new InventoryStatus(i, chestis,this.inv));
				}

			}

		}
		return map;
	}

	public List<InventoryStatus> getAllInvItems(){
		return this.getInvItems(st -> st.getStack()!=null);
	}
	public List<InventoryStatus> getInvItems(Predicate<InventoryStatus> checker){
		List<InventoryStatus> map = new ArrayList();
		for(int i=0;i<this.inv.getSizeInventory();i++){
			ItemStack chestis = this.inv.getStackInSlot(i);
			if(chestis!=null){
				InventoryStatus status = new InventoryStatus(i, chestis,this.inv);
				if(checker.apply(status)){
					map.add(status);
				}

			}

		}
		return map;
	}
	public int getAmount(){
		int am = 0;
		for(int i=0;i<this.inv.getSizeInventory();i++){
			if(this.inv.getStackInSlot(i)!=null){
				am += 1;
			}
		}
		return am;
	}
//	public int getAmount(Item item){
////		int stack = 0;
////		for(int i=0;i<this.inv.getSizeInventory();i++){
////			ItemStack chestis = this.inv.getStackInSlot(i);
////			if(chestis!=null && chestis.getItem()==item){
////				stack += this.inv.getStackInSlot(i).stackSize;
////			}
////		}
////		return stack;
//		return this.getAmount(new CustomCheck(){
//
//			public Item item;
//			public CustomCheck setItem(Item item){
//				this.item = item;
//				return this;
//			}
//			@Override
//			public boolean check(ItemStack is){
//				return is.getItem() == item;
//			}
//		}.setItem(item));
//	}

	//test
	public int getAmount2(Item item){
		final Item _item = item;
		return this.getAmount(new Predicate<InventoryStatus>(){



			@Override
			public boolean apply(InventoryStatus input) {
				return input.is.getItem() == _item;
			}
		});
	}

//	public int getAmount(CustomCheck check){
//		int stack = 0;
//		for(int i=0;i<this.inv.getSizeInventory();i++){
//			ItemStack chestis = this.inv.getStackInSlot(i);
//			if(chestis!=null && check.check(chestis)){
//				stack += this.inv.getStackInSlot(i).stackSize;
//			}
//		}
//		return stack;
//	}

	public int getAmount(Predicate<InventoryStatus> delegate){
		int stack = 0;
		for(int i=0;i<this.inv.getSizeInventory();i++){
			ItemStack chestis = this.inv.getStackInSlot(i);
			if(chestis!=null && delegate.apply(new InventoryStatus(i,chestis,this.inv))){
				stack += this.inv.getStackInSlot(i).stackSize;
			}
		}

		return stack;
	}
//	public boolean contains(PairIDList pairList){
//		int flag =0;
//		for(PairID pair:pairList.list){
//			if(this.contains(pair.getAsItemStack()) && this.getAmount(pair.getAsItemStack())>=pair.stack){
//				flag += 1;
//				System.out.println(pair);
//				System.out.println("fl:"+flag);
//			}
//		}
//		System.out.println(pairList.list.size());
//		if(pairList.list.size()<=flag){
//			return true;
//		}
//		return false;
//	}


	//インベントリの場所によらずアイテムを消費
	public void decrItemStack(ItemStack is,int par1){
		int decr = par1;
		for(int i=0;i<this.inv.getSizeInventory();i++){
			ItemStack chestis = this.inv.getStackInSlot(i);
			if(chestis!=null && decr>0 && is.isItemEqual(chestis)){
				if(chestis.stackSize<decr){
					decr -= chestis.stackSize;
					this.inv.setInventorySlotContents(i, null);
				}else{
					this.inv.decrStackSize(i, decr);
					decr = 0;
				}
			}
		}
	}

//	//上のをまとめて実行するやつ
//	public void decrItemStack(PairIDList pairList){
//		for(PairID pair:pairList.list){
//			this.decrItemStack(pair.getAsItemStack(), pair.getStack());
//		}
//	}
	//もともとのやつと同じ。ちなみにアイテム増減は蔵鯖両方で実行したほうがよい1.7.2現在
	public void decrItemStack(int par1,int stack){
		this.inv.decrStackSize(par1,stack);
	}
	public void decrItemStack(Range range,int stack){
		for(int i=range.min;i<range.max;i++){
			if(this.inv.getStackInSlot(i)!=null){
				this.inv.decrStackSize(i,stack);
			}

		}

	}
	public ItemStack getArmorInv(int par1){

		if(this.inv instanceof InventoryPlayer){
			return ((InventoryPlayer) this.inv).armorItemInSlot(par1);
		}
		return null;
	}

	public void setArmor(int par1,ItemStack is){

		if(this.inv instanceof InventoryPlayer){
			((InventoryPlayer) this.inv).armorInventory[par1] = is;
		}
		return;
	}

	public void swapSlot(int num1,int num2){
		ItemStack is = inv.getStackInSlot(num1)!=null?inv.getStackInSlot(num1).copy() : null;
		ItemStack is2 =  inv.getStackInSlot(num2)!=null?inv.getStackInSlot(num2).copy() : null;
		inv.setInventorySlotContents(num1, is2);
		inv.setInventorySlotContents(num2, is);
	}

	@Deprecated
	public void collectStack(int num1,int num2){
		int stack1 = inv.getStackInSlot(num1).stackSize;
		int stack2 = inv.getStackInSlot(num2).stackSize;
//		SortInv.log(num1+"のスタックは"+stack1,this.getClass());
//		SortInv.log(num2+"のスタックは"+stack2,this.getClass());
		stack1 += stack2;
		stack2 = 0;
		if(stack1>64){

			stack2 = stack1 - 64;
			stack1 = 64;
		}
		inv.getStackInSlot(num1).stackSize = stack1;
		inv.getStackInSlot(num2).stackSize = stack2;
//		SortInv.log(num1+"のスタックは"+stack1+"に",this.getClass());
//		SortInv.log(num2+"のスタックは"+stack2+"に",this.getClass());
		if(inv.getStackInSlot(num2).stackSize<=0){
			inv.setInventorySlotContents(num2, null);
		}
	}

	@Deprecated
	public void sort(){
		ItemStack selected = null;
		boolean collected = false;
		for(int i=0;i<this.inv.getSizeInventory();i++){
			if(this.inv.getStackInSlot(i)!=null){
				selected = this.inv.getStackInSlot(i);
				collected = false;
				if(i+1<inv.getSizeInventory() && selected.isStackable() && selected.stackSize<64){

					for(int j=i+1;j<this.inv.getSizeInventory();j++){
						if(!collected && this.inv.getStackInSlot(j)!=null && selected.isItemEqual(this.inv.getStackInSlot(j))){
//							SortInv.log(i+":"+j, this.getClass());
							this.collectStack(i, j);
							if(selected.stackSize>=64){
								collected = true;
							}

						}
					}
				}

			}
		}

		for(int tier=0;tier<7;tier++){
			for(int i=0;i<this.inv.getSizeInventory();i++){
				if(this.inv.getStackInSlot(i)!=null){
					this.checkPair(i,tier);
				}
			}
		}

		for(int i=0;i<this.inv.getSizeInventory();i++){
			if(this.inv.getStackInSlot(i)==null){
				this.checkPair(i, 99);
			}
		}

	}


	public void checkPair(int selectedSlot,int sw){
		ItemStack selectedStack = this.inv.getStackInSlot(selectedSlot);
		if(selectedSlot+1<this.inv.getSizeInventory()){
			for(int i=selectedSlot+1;i<this.inv.getSizeInventory();i++){
				if(this.inv.getStackInSlot(i)!=null){
					ItemStack pairStack = this.inv.getStackInSlot(i);
					switch(sw){
					case 0:
						if(pairStack.isItemEqual(selectedStack)){
							if(selectedSlot!=i){
								this.swapSlot(selectedSlot+1, i);
							}

						}
						break;
					case 1:
						if(pairStack.getItem()==selectedStack.getItem()){
							if(selectedSlot!=i){
								this.swapSlot(selectedSlot+1, i);
							}
						}
						break;
					case 2:
						if(pairStack.getItem() instanceof ItemTool){
							if(selectedSlot!=i){
								this.swapSlot(selectedSlot+1, i);
							}
						}
						break;
					case 3:
						if(pairStack.getItem() instanceof ItemFood){
							if(selectedSlot!=i){
								this.swapSlot(selectedSlot+1, i);
							}
						}
						break;
					case 4:
						if(pairStack.getItem() instanceof ItemSword){
							if(selectedSlot!=i){
								this.swapSlot(selectedSlot+1, i);
							}
						}
						break;
					case 5:
						List<String> idnames1 = HSLibs.getOreNames(OreDictionary.getOreIDs(pairStack));
						List<String> idnames2 = HSLibs.getOreNames(OreDictionary.getOreIDs(selectedStack));
						boolean sw1 = false;
						boolean sw2 = false;
						for(String na:idnames1){
							if(na.contains("ore"))sw1 = true;
						}
						for(String na:idnames2){
							if(na.contains("ore"))sw2 = true;
						}
						if(sw1&&sw2){
							if(selectedSlot!=i){
								this.swapSlot(selectedSlot+1, i);
							}
						}
						break;
					case 6:
						Set idList1 = Sets.newHashSet(OreDictionary.getOreIDs(pairStack));
						Set idList2 = Sets.newHashSet(OreDictionary.getOreIDs(selectedStack));
						if(idList1!=null && idList2!=null && !Sets.intersection(idList1, idList2).isEmpty()){
							if(selectedSlot!=i){
								this.swapSlot(selectedSlot+1, i);
							}
						}
						break;
					case 99:
						if(selectedSlot!=i){
							this.swapSlot(selectedSlot, i);
						}
						break;
					}

				}
			}
		}
	}
	public void spreadChestContents(World world,IInventory inv,XYZPos pos){
		Random rnd = new Random();
        for (int i1 = 0; i1 < inv.getSizeInventory(); ++i1)
        {
            ItemStack itemstack = inv.getStackInSlot(i1);

            if (itemstack != null)
            {
                float f = rnd.nextFloat() * 0.8F + 0.1F;
                float f1 =rnd.nextFloat() * 0.8F + 0.1F;
                EntityItem entityitem;

                for (float f2 = rnd.nextFloat() * 0.8F + 0.1F; itemstack.stackSize > 0; world.spawnEntityInWorld(entityitem))
                {
                    int j1 = rnd.nextInt(21) + 10;

                    if (j1 > itemstack.stackSize)
                    {
                        j1 = itemstack.stackSize;
                    }

                    itemstack.stackSize -= j1;
                    entityitem = WorldHelper.getNewEntityItem(world, new XYZPos(pos).addDouble(f, f1, f2),new ItemStack(itemstack.getItem(),j1,itemstack.getItemDamage()));
                    //entityitem = new EntityItem(world, (double)((float)pos.x + f), (double)((float)pos.y + f1), (double)((float)pos.z + f2), new ItemStack(itemstack.getItem(), j1, itemstack.getItemDamage()));
                    float f3 = 0.05F;
                    entityitem.setVelocity(rnd.nextGaussian() * f3, rnd.nextGaussian() * f3 + 0.2F, rnd.nextGaussian() * f3);

                    if (itemstack.hasTagCompound())
                    {
                        entityitem.getEntityItem().setTagCompound((NBTTagCompound)itemstack.getTagCompound().copy());
                    }
                }
            }
        }
	}



	public static class InventoryStatus{
		protected ItemStack is;
		protected int number;
		protected IInventory parent;

		public InventoryStatus(int number,ItemStack is,IInventory inv){
			this.number = number;
			this.is = is;
			this.parent = inv;
		}

		public ItemStack getStack(){
			return is;

		}

		public IInventory getParent(){
			return this.parent;
		}

		public int getStackNumber(){
			return number;
		}

		public void swap(IInventory other,int num){
			if(other.getStackInSlot(num)==null){
				other.setInventorySlotContents(num, this.getStack().copy());
				this.parent.removeStackFromSlot(this.getStackNumber());
			}else{
				ItemStack cache = other.getStackInSlot(num).copy();
				other.setInventorySlotContents(num, this.getStack().copy());
				this.parent.setInventorySlotContents(this.getStackNumber(), cache);
			}
		}
		public void swap(InventoryStatus other){
			this.swap(other.getParent(),other.getStackNumber());
		}
		/**
		 * マージする（このオブジェクトが主）
		 * @param st
		 */
		public void merge(InventoryStatus st){
			if(st.getStack()!=null && st.getStack().isStackable()){
				int maxSize = this.getStack().getMaxStackSize();
				int require = maxSize - this.getStack().stackSize;

				if(require<=0){
					return;
				}
				if(require<=st.getStack().stackSize){
					st.getParent().decrStackSize(st.getStackNumber(), require);
					this.getStack().stackSize += require;
					this.getParent().setInventorySlotContents(this.getStackNumber(), this.getStack());
				}else{
					int stacksize = st.getStack().stackSize;
					st.getParent().removeStackFromSlot(st.getStackNumber());
					this.getStack().stackSize += stacksize;
					this.getParent().setInventorySlotContents(this.getStackNumber(), this.getStack());
				}

			}
		}
	}


}
