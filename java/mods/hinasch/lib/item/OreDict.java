package mods.hinasch.lib.item;

import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class OreDict {


	String name;

	public OreDict(String name) {
		super();
		this.name = name;
	}

	public boolean equalsOreDict(ItemStack is){
		int[] ints = OreDictionary.getOreIDs(is);
		int j = OreDictionary.getOreID(name);
		if(ints.length>0){
			for(int i:ints){
				if(i==j){
					return true;
				}
			}
		}
		return false;
	}

	public String getOreString(){
		return this.name;
	}
}
