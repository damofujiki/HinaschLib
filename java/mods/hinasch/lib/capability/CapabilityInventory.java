package mods.hinasch.lib.capability;

import java.util.Map;
import java.util.function.Predicate;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import mods.hinasch.lib.capability.CapabilityAdapterFactory.ICapabilityAdapter;
import mods.hinasch.lib.capability.ComponentCapabilityAdapterItem.ComponentCapabilityAdapterTileEntity;
import mods.hinasch.lib.core.HSLib;
import mods.hinasch.lib.iface.IRequireInitializing;
import mods.hinasch.lib.util.UtilNBT;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.event.AttachCapabilitiesEvent;

public class CapabilityInventory {

	public static Map<Predicate<AttachCapabilitiesEvent.Item>,Integer> predicates = Maps.newHashMap();
	public static Map<Predicate<AttachCapabilitiesEvent.TileEntity>,Integer> predicatesTE = Maps.newHashMap();

	@CapabilityInject(IItemInventory.class)
	public static Capability<IItemInventory> CAPA;
	public static ICapabilityAdapter<IItemInventory> iCapabilityAdapter = new ICapabilityAdapter<IItemInventory>(){

		@Override
		public Capability<IItemInventory> getCapability() {
			// TODO 自動生成されたメソッド・スタブ
			return CAPA;
		}

		@Override
		public Class<IItemInventory> getCapabilityClass() {
			// TODO 自動生成されたメソッド・スタブ
			return IItemInventory.class;
		}

		@Override
		public Class<? extends IItemInventory> getDefault() {
			// TODO 自動生成されたメソッド・スタブ
			return DefaultImpl.class;
		}

		@Override
		public IStorage<IItemInventory> getStorage() {
			// TODO 自動生成されたメソッド・スタブ
			return new Storage();
		}
	};

	public static class Storage extends CapabilityStorage<IItemInventory>{

		@Override
		public void writeNBT(NBTTagCompound comp, Capability<IItemInventory> capability, IItemInventory instance,
				EnumFacing side) {

			if(instance.hasInitialized()){
				comp.setInteger("maxSize", instance.getMaxStackSize());
				NBTTagList tagList = UtilNBT.newTagList();
				UtilNBT.writeItemStacksToNBTTag(tagList, instance.getStacks());
				comp.setTag("stacks", tagList);
			}


			comp.setBoolean("initialized", instance.hasInitialized());
		}

		@Override
		public void readNBT(NBTTagCompound comp, Capability<IItemInventory> capability, IItemInventory instance,
				EnumFacing side) {
			instance.setMaxStackSize(comp.getInteger("maxSize"));
			NBTTagList tagList = comp.getTagList("stacks",UtilNBT.NBTKEY_COMPOUND);
			instance.setStacks(UtilNBT.getItemStacksFromNBT(tagList, instance.getMaxStackSize()));
			instance.setInitialized(comp.getBoolean("initialized"));

		}

	}
	public static CapabilityAdapterBase adapterBase = HSLib.capabilityAdapterFactory.create(iCapabilityAdapter);
	public static ComponentCapabilityAdapterItem<IItemInventory> adapter = adapterBase.createChildItem("itemInventory");
	public static ComponentCapabilityAdapterTileEntity<IItemInventory> adapterTE = adapterBase.createChildTileEntity("itemInventoryTE");
	static{
		adapter.setPredicate(ev ->predicates.keySet().stream().anyMatch(in -> in.test(ev))).setRequireSerialize(true);
		adapterTE.setPredicate(ev ->predicatesTE.keySet().stream().anyMatch(in -> in.test(ev))).setRequireSerialize(true);
	}
	public static void registerEvents(){
		adapter.registerEvent((inst,capa,facing,ev)->{
			predicates.entrySet().forEach(entry ->{
				if(entry.getKey().test(ev) && !inst.hasInitialized()){
					inst.setMaxStackSize(entry.getValue());
					inst.init();
					inst.setInitialized(true);
				}
			});
		});
		adapterTE.registerEvent((inst,capa,facing,ev)->{
			predicatesTE.entrySet().forEach(entry ->{
				if(entry.getKey().test(ev) && !inst.hasInitialized()){
					inst.setMaxStackSize(entry.getValue());
					inst.init();
					inst.setInitialized(true);
				}
			});
		});
	}
	public static class DefaultImpl implements IItemInventory{

		int maxStackSize;
		ItemStack[] is;
		boolean initialize = false;
		@Override
		public ItemStack[] getStacks() {
			// TODO 自動生成されたメソッド・スタブ
			return is;
		}

		@Override
		public void setStacks(ItemStack[] is) {
			// TODO 自動生成されたメソッド・スタブ
			this.is = is;
		}

		@Override
		public void initStacks(int max) {
			// TODO 自動生成されたメソッド・スタブ
			this.is = new ItemStack[max];
			this.maxStackSize = max;
		}

		@Override
		public boolean hasInitialized() {
			// TODO 自動生成されたメソッド・スタブ
			return this.initialize;
		}

		@Override
		public void setInitialized(boolean par1) {
			// TODO 自動生成されたメソッド・スタブ
			this.initialize = par1;
		}

		@Override
		public int getMaxStackSize() {
			// TODO 自動生成されたメソッド・スタブ
			return this.maxStackSize;
		}

		@Override
		public void setMaxStackSize(int size) {
			// TODO 自動生成されたメソッド・スタブ

			this.maxStackSize = size;
		}

		@Override
		public void setStack(int num, ItemStack is) {
			this.is[num] = is;

		}

		@Override
		public Iterable<ItemStack> getItemList() {
			// TODO 自動生成されたメソッド・スタブ
			return Lists.newArrayList(this.is);
		}

		@Override
		public void init() {
			// TODO 自動生成されたメソッド・スタブ
			this.is = new ItemStack[this.getMaxStackSize()];
		}

	}
	public static interface IItemInventory extends IRequireInitializing{
		public Iterable<ItemStack> getItemList();
		public ItemStack[] getStacks();
		public void setStacks(ItemStack[] is);
		public void setStack(int num,ItemStack is);
		public void initStacks(int max);
		public int getMaxStackSize();
		public void setMaxStackSize(int size);
		public void init();
	}
}
