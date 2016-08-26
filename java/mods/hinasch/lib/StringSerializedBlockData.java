package mods.hinasch.lib;

import com.mojang.realmsclient.util.Pair;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class StringSerializedBlockData extends Pair<String,String>{

	protected final int index;

	protected StringSerializedBlockData(int index,String first, String second) {
		super(first, second);
		this.index = index;
		// TODO 自動生成されたコンストラクター・スタブ
	}

	public String getModID(){
		return this.first();
	}

	public String getElementName(){

		return this.second();
	}

	public int getIndex(){
		return this.index;
	}
	public Block asBlock(){
		return GameRegistry.findBlock(getModID(), getElementName());
	}

	public Item asItem(){
		return GameRegistry.findItem(getModID(), getElementName());
	}
	public static StringSerializedBlockData split(String s){
		String[] splitted2 = s.split("@");
		if(splitted2.length>=2){
			int index = Integer.parseInt(splitted2[0]);
			String[] splitted = splitted2[1].split(":");
			if(splitted.length>=2){
				return new StringSerializedBlockData(index,splitted[0],splitted[1]);
			}
		}


		return null;
	}
}
