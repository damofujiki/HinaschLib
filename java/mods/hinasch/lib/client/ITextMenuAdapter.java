package mods.hinasch.lib.client;

import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.item.ItemStack;

public interface ITextMenuAdapter {

	public String getText(int index);
	public int getMaxElement();
	public int getColumn();
	public List<String> getHoverText(int index);
	@Nullable
	public ItemStack getIconStack(int index);
}
