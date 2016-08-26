package mods.hinasch.lib.item;

import java.util.List;
import java.util.Map;

import com.google.common.base.Supplier;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mojang.realmsclient.util.Pair;

import net.minecraft.item.ItemStack;

public class RecipeParse {

	int index = 0;
	List<String> recipes = Lists.newArrayList();

	List<Object> buffer = Lists.newArrayList();
	Map<Character,Pair<ItemStack,OreDict>> recip2 = Maps.newHashMap();

	public RecipeParse(List<Object> objs){
		for(Object obj:objs){
			this.add(obj);
		}

	}

	public void add(Object elm){
		if(index<3){
			recipes.add((String)elm);
		}else{
			buffer.add(elm);
			if(buffer.size()==2){

				Character ch = (Character)buffer.get(0);
				Pair<ItemStack,OreDict> pair = new Supplier<Pair<ItemStack,OreDict>>(){

					@Override
					public Pair<ItemStack, OreDict> get() {
						if(buffer.get(1) instanceof ItemStack){
							return Pair.of((ItemStack)buffer.get(1), null);
						}
						if(buffer.get(1) instanceof String){
							return Pair.of(null,new OreDict((String) buffer.get(1)));
						}
						return null;
					}
				}.get();


				recip2.put(ch,pair);
				buffer.clear();
			}

		}
		index+=1;

	}

}
