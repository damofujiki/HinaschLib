package mods.hinasch.lib.block;

import java.util.List;

import com.google.common.base.Splitter;

import net.minecraft.block.Block;
import net.minecraft.util.ResourceLocation;

public class StringToBlockSplitter {

	public static Block apply(String input) {
		try{
			List<String> list = Splitter.on(":").splitToList(input);

			return Block.REGISTRY.getObject(new ResourceLocation(list.get(0),list.get(1)));
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}

	}

}
