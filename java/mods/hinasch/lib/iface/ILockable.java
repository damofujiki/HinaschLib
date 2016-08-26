package mods.hinasch.lib.iface;

import java.util.UUID;

public interface ILockable {

	public void setUser(UUID user);
	public UUID getUser();
}
