package mods.hinasch.lib.capability;

import com.google.common.base.Predicate;

import mods.hinasch.lib.capability.CapabilityAdapterFactory.ICapabilityAdapterPlan;
import mods.hinasch.lib.capability.SimpleCapabilityAttachEvent.IPreAttach;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.event.AttachCapabilitiesEvent;

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

	ICapabilityAdapterPlan<T> capabilityAdapter;
	CapabilityAdapterFrame<T> parent;
	public ComponentCapabilityAdapter(String modid,String name,CapabilityAdapterFrame parent){
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
					return input.getObject()!=null;
				}

			};
		}
	}


	/**
	 * default impliment やstorageを書いたクラス内に
	 * static{}内に書いておくとOK
	 * 例： static{ adapter.setpredicate(ev -> ev.getItem() instnaceof ItemArmor) }
	 * @param predicate
	 * @return
	 */
	public ComponentCapabilityAdapter setPredicate(Predicate<K> predicate){
		this.predicate = predicate;
		return this;
	}

	public ComponentCapabilityAdapter setRequireSerialize(boolean par1){
		this.isRequireSerialize = par1;
		return this;
	}

	public void registerAttachEvent(){
		this.registerAttachEvent(null);
	}
	public abstract void registerAttachEvent(IPreAttach<T,K> preAttach);

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
