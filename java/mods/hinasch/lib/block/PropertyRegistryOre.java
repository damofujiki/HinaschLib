package mods.hinasch.lib.block;

import mods.hinasch.lib.PropertyRegistry;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraftforge.oredict.OreDictionary;

public abstract class PropertyRegistryOre<T extends BlockOreProperty> extends PropertyRegistry<T>{



	protected void registerSmelted(){
		this.getKeys().stream().map(in -> this.getObject(in)).forEach(in ->{
			if(in.getSmelted().isPresent()){
				Item item = in.getSmelted().get().getItem();
				int meta = in.getSmelted().get().getMeta();
				ItemStack smelted = new ItemStack(item,1,meta);
				FurnaceRecipes.instance().addSmeltingRecipeForBlock(in.getBlock(), smelted, in.getExp());
			}
		});
	}
	protected void registerOreDicts(){

		this.getKeys().stream().map(in -> this.getObject(in)).forEach(in -> {
			if(in.getOreDict()!=null){
				OreDictionary.registerOre(in.getOreDict(), in.getBlock());
			}

		});
	}

	protected void registerSmeltedAndOreDicts(){
		this.initSmelted();
		this.initOreDicts();
		this.registerSmelted();
		this.registerOreDicts();
	}
	protected abstract void initSmelted();
	protected abstract void initOreDicts();
}
