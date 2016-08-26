package mods.hinasch.lib.capability;

import com.google.common.base.Predicate;

import mods.hinasch.lib.capability.CapabilityAdapterFactory.ICapabilityAdapter;
import mods.hinasch.lib.capability.SimpleCapabilityAttachEvent.IPreAttach;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent.Entity;
import net.minecraftforge.event.AttachCapabilitiesEvent.Item;

/**
 *
 *
 * @param <T> キャパビリティのインターフェイス
 * @param <K> キャパビリティレジスターのイベント
 */
public abstract class ComponentCapabilityAdapter<T,K extends AttachCapabilitiesEvent,G extends ICapabilitySerializable<?>> {

	final String modid;
	final String name;
	Predicate<K> predicate;
	boolean isRequireSerialize = true;

	ICapabilityAdapter<T> capabilityAdapter;
	CapabilityAdapterBase<T> parent;
	public ComponentCapabilityAdapter(String modid,String name,CapabilityAdapterBase parent){
		this.modid = modid;
		this.name = name;
		this.parent = parent;
		this.capabilityAdapter = parent.capabilityAdapter;
		if(this.capabilityAdapter.getStorage() instanceof StorageDummy){
			this.isRequireSerialize = false;
		}
		if(this.predicate==null){
			this.predicate = new Predicate<K>(){

				@Override
				public boolean apply(K input) {
					if(input instanceof Item){
						return ((Item) input).getItemStack()!=null;
					}
					if(input instanceof Entity){
						return ((Entity)input).getEntity()!=null;
					}
					return false;
				}

			};
		}
	}


	public ComponentCapabilityAdapter setPredicate(Predicate<K> predicate){
		this.predicate = predicate;
		return this;
	}

	public ComponentCapabilityAdapter setRequireSerialize(boolean par1){
		this.isRequireSerialize = par1;
		return this;
	}

	public void registerEvent(){
		this.registerEvent(null);
	}
	public abstract void registerEvent(IPreAttach<T,K> preAttach);

	public T getCapability(G entity){
		return entity.getCapability(this.capabilityAdapter.getCapability(), null);
	}

//	public T getCapability(ItemStack is){
//		return is.getCapability(this.capabilityAdapter.getCapability(), null);
//	}
//	public T getCapability(TileEntity entity){
//		return entity.getCapability(this.capabilityAdapter.getCapability(), null);
//	}

	public boolean hasCapability(G entity){
		return entity.hasCapability(capabilityAdapter.getCapability(), null);
	}

//	public boolean hasCapability(ItemStack is){
//		return is.hasCapability(capabilityAdapter.getCapability(), null);
//	}
//	public boolean hasCapability(TileEntity entity){
//		return entity.hasCapability(capabilityAdapter.getCapability(), null);
//	}
//	public void registerCapability(){
//		HSLibs.registerCapability(this.capabilityAdapter.getCapabilityClass(), this.capabilityAdapter.getStorage(), this.capabilityAdapter.getDefault());
//	}






}
