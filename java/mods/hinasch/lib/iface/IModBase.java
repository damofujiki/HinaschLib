package mods.hinasch.lib.iface;

import mods.hinasch.lib.client.IGuiAttribute;

public interface IModBase {

	public Class<? extends IGuiAttribute> getGuiClass();
	public Object getModInstance();
	public int getModGuiID();
}
