package mods.hinasch.lib.primitive;

import net.minecraft.util.ResourceLocation;

public interface IPropertyElement<T> extends IPropertyElementFrame<ResourceLocation> {

	public String getName();
	public ResourceLocation getKey();
}
