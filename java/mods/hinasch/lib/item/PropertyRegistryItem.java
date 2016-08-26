package mods.hinasch.lib.item;

import java.util.List;
import java.util.stream.Collectors;

import mods.hinasch.lib.PropertyRegistry;
import net.minecraftforge.oredict.OreDictionary;

public abstract class PropertyRegistryItem<T extends ItemProperty> extends PropertyRegistry<T>{


	protected void registerOreDicts(){
		this.getKeys().stream().forEach(in -> {
			T item = this.getObject(in);
			if(item.getOreDictID().isPresent()){
				OreDictionary.registerOre(item.getOreDictID().get(), item.getItemStack(1));
			}

		});
	}



	public List<String> getNameList(){
		return this.getProperties().stream().sorted().map(in -> in.getName()).collect(Collectors.toList());
	}

}
