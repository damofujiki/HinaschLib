package mods.hinasch.lib.config;

import net.minecraftforge.common.config.Configuration;

public abstract class ConfigBase {

	protected Configuration configFile;

	/**
	 * PropertyCustomなどを使う
	 * @param config
	 */
	public abstract void init();
	public void syncConfig(){
		if(this.configFile.hasChanged()){

			this.configFile.save();
		}
	}

	public ConfigBase setConfigFile(Configuration configFile){
		this.configFile = configFile;
		return this;
	}
}
