package mods.hinasch.lib.core.plugin;

import net.minecraftforge.fml.common.Loader;

public class HSLibPluginIntegration {

	public HSLibPluginHAC hac;
//	boolean isLoadedHAC = false;
	public void checkLoadedMods(){

	}
	public void load(){
		if(this.isLoadedHAC()){
			hac = new HSLibPluginHAC();
		}

	}

	public boolean isLoadedHAC(){
		return Loader.isModLoaded("dcs_climate|lib");
	}
}
