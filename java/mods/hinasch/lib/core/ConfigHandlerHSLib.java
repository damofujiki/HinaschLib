package mods.hinasch.lib.core;

import mods.hinasch.lib.config.ConfigBase;
import mods.hinasch.lib.config.PropertyCustomNew;

public class ConfigHandlerHSLib extends ConfigBase{

	private boolean isDebug = false;
	boolean allowOreGeneration = true;
	public boolean isAllowOreGeneration() {
		return allowOreGeneration;
	}

	public boolean isAllowOreGenerationOverworld() {
		return allowOreGenerationOverworld;
	}

	boolean allowOreGenerationOverworld = true;
	PropertyCustomNew props;
	@Override
	public void init() {
		// TODO 自動生成されたメソッド・スタブ

		props = PropertyCustomNew.newInstance();
		props.add(0,"isDebug", "Set Debug Flag True.", false);
		props.add(1, "enableOreGeneration", "set true to allow ore generation.", true);
		props.add(2, "enableAllowOreGenerationOverworld", "set true to allow ore generation in overworld.", true);

		props.adapt(this.configFile);
//		props.getAdaptedProperties().get(0).setRequiresMcRestart(true);
	}

	@Override
	public void syncConfig(){

		this.isDebug = props.getAdaptedProperties().get(0).getBoolean();
		this.allowOreGeneration = props.getAdaptedProperties().get(1).getBoolean();
		this.allowOreGenerationOverworld = props.getAdaptedProperties().get(2).getBoolean();
		super.syncConfig();
	}

	public boolean isDebug(){
		return this.isDebug;
	}
}
