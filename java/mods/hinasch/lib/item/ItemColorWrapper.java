package mods.hinasch.lib.item;

import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.item.ItemStack;

public interface ItemColorWrapper extends IItemColor{

	int getColor(ItemStack stack, int tintIndex);

	static IItemColor of(ItemColorWrapper lambda){
		return lambda;
	}
	default int getColorFromItemstack(ItemStack stack, int tintIndex){
		return getColor(stack, tintIndex);
	}
}
