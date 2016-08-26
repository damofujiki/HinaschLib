package mods.hinasch.lib.primitive;

import com.google.common.base.Function;

import joptsimple.internal.Strings;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.RegistrySimple;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.common.registry.IForgeRegistryEntry;

public abstract class BlockItemRegistry<V> extends RegistrySimple<ResourceLocation,V>{

	public String modid;
	public String unlocalizedNamePrefix = Strings.EMPTY;

	public String getUnlocalizedNamePrefix() {
		return unlocalizedNamePrefix;
	}
	public void setUnlocalizedNamePrefix(String unlocalizedNamePrefix) {
		this.unlocalizedNamePrefix = unlocalizedNamePrefix;
	}
	public BlockItemRegistry(String modid){
		this.modid = modid;
	}
	public abstract void register();

	public void register(V[] objs,String[] names,CreativeTabs tab){

	}
	public V put(V obj,String name,CreativeTabs tab){
		return this.put(obj, name, tab, null);
	}
	public V put(V obj,String name,CreativeTabs tab,Function<Block,ItemBlock> itemBlockFunc){
		return this.put(obj, name, name, tab, itemBlockFunc);
	}
	public V put(V obj,String name,String unl,CreativeTabs tab,Function<Block,ItemBlock> itemBlockFunc){
		ResourceLocation res = new ResourceLocation(modid,name);

		if(obj instanceof IForgeRegistryEntry.Impl){
			IForgeRegistryEntry.Impl forgeObj = (IForgeRegistryEntry.Impl) obj;
			forgeObj.setRegistryName(res);
			if(tab!=null){
				if(obj instanceof Item){
					((Item) obj).setCreativeTab(tab);
				}
				if(obj instanceof Block){
					((Block)obj).setCreativeTab(tab);
				}
			}
			if(obj instanceof Item){
				((Item) obj).setUnlocalizedName(unlocalizedNamePrefix+"."+unl);
			}
			if(obj instanceof Block){
				((Block)obj).setUnlocalizedName(unlocalizedNamePrefix+"."+unl);
			}
			GameRegistry.register(forgeObj);
			if(obj instanceof Block){
				Block block = (Block) obj;
				if(itemBlockFunc!=null){
					ItemBlock itemBlock = itemBlockFunc.apply(block);
					itemBlock.setRegistryName(res);
					GameRegistry.register(itemBlock);
				}else{
					ItemBlock itemBlock = new ItemBlock(block);
					itemBlock.setRegistryName(res);
					GameRegistry.register(itemBlock);
				}
			}
			this.putObject(res, obj);
			return this.getObject(res);
		}
		return null;
	}

	public V getRegisteredObject(String name){
		return this.getObject(new ResourceLocation(modid,name));
	}
}
