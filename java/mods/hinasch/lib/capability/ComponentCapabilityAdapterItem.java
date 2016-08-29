package mods.hinasch.lib.capability;

import mods.hinasch.lib.capability.SimpleCapabilityAttachEvent.IPreAttach;
import mods.hinasch.lib.util.HSLibs;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.event.AttachCapabilitiesEvent;

public class ComponentCapabilityAdapterItem<T> extends ComponentCapabilityAdapter<T,AttachCapabilitiesEvent.Item,ItemStack>{

	public ComponentCapabilityAdapterItem(String modid, String name, CapabilityAdapterBase parent) {
		super(modid, name, parent);
		// TODO 自動生成されたコンストラクター・スタブ
	}

	public void registerEvent(IPreAttach<T,AttachCapabilitiesEvent.Item> preAttach){

    	HSLibs.registerEvent(new SimpleItemCapabilityAttachEvent<T>(this.parent.parent.modid,this.isRequireSerialize,preAttach){

			@Override
			public boolean apply(net.minecraftforge.event.AttachCapabilitiesEvent.Item input) {
				// TODO 自動生成されたメソッド・スタブ
				return predicate.apply(input);
			}

			@Override
			public Capability<T> getCapabilityInstance() {
				// TODO 自動生成されたメソッド・スタブ
				return capabilityAdapter.getCapability();
			}

			@Override
			public String getName() {
				// TODO 自動生成されたメソッド・スタブ
				return name;
			}}
    	);
	}

	public static class ComponentCapabilityAdapterEntity<T> extends ComponentCapabilityAdapter<T,AttachCapabilitiesEvent.Entity,Entity>{

		public ComponentCapabilityAdapterEntity(String modid, String name, CapabilityAdapterBase parent) {
			super(modid, name, parent);
			// TODO 自動生成されたコンストラクター・スタブ
		}
		/**
		 * IPreAttach (inst,capa,facing,ev)
		 */
		public void registerEvent(IPreAttach<T,AttachCapabilitiesEvent.Entity> preAttach){

	    	HSLibs.registerEvent(new SimpleEntityCapabilityAttachEvent<T>(this.parent.parent.modid,this.isRequireSerialize,preAttach){


				@Override
				public boolean apply(AttachCapabilitiesEvent.Entity input) {
					// TODO 自動生成されたメソッド・スタブ
					return predicate.apply(input);
				}

				@Override
				public Capability<T> getCapabilityInstance() {
					// TODO 自動生成されたメソッド・スタブ
					return capabilityAdapter.getCapability();
				}

				@Override
				public String getName() {
					// TODO 自動生成されたメソッド・スタブ
					return name;
				}}
	    	);
		}
	}

	public static class ComponentCapabilityAdapterTileEntity<T> extends ComponentCapabilityAdapter<T,AttachCapabilitiesEvent.TileEntity,TileEntity>{

		public ComponentCapabilityAdapterTileEntity(String modid, String name, CapabilityAdapterBase parent) {
			super(modid, name, parent);
			// TODO 自動生成されたコンストラクター・スタブ
		}

		public void registerEvent(IPreAttach<T,AttachCapabilitiesEvent.TileEntity> preAttach){

	    	HSLibs.registerEvent(new SimpleTECapabilityAttachEvent<T>(this.parent.parent.modid,this.isRequireSerialize,preAttach){


				@Override
				public boolean apply(AttachCapabilitiesEvent.TileEntity input) {
					// TODO 自動生成されたメソッド・スタブ
					return predicate.apply( input);
				}

				@Override
				public Capability<T> getCapabilityInstance() {
					// TODO 自動生成されたメソッド・スタブ
					return capabilityAdapter.getCapability();
				}

				@Override
				public String getName() {
					// TODO 自動生成されたメソッド・スタブ
					return name;
				}}
	    	);
		}

	}
}
