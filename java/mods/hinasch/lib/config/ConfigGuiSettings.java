package mods.hinasch.lib.config;

import java.util.List;

import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.config.GuiConfig;
import net.minecraftforge.fml.client.config.IConfigElement;


public class ConfigGuiSettings {

//	public final String MODID;

//	public Configuration configFile;
//	public String configID;
//	public boolean requireMCRestart;
//	public String displayConfigName;
//	public String category;

//	public ConfigGuiSettings(String modid){
//		this.MODID = modid;
//
//		this.category = Configuration.CATEGORY_GENERAL;
//		this.requireMCRestart = false;
//	}
//	public ConfigGuiSettings setConfigFile(Configuration par1){
//		this.configFile = par1;
//		return this;
//	}
//	
//	public ConfigGuiSettings setConfigID(String par1){
//		this.configID = par1;
//		return this;
//	}
//	
//	public ConfigGuiSettings setRequireMCRestart(boolean par1){
//		this.requireMCRestart = par1;
//		return this;
//		
//	}
//	
//	public ConfigGuiSettings setDisplayerConfigName(String par1){
//		this.displayConfigName = par1;
//		return this;
//		
//	}
	/**

	 * @param modid
	 */
	
	/**
	 * コンフィグGUI用ユーティリティメソッド。
	 * IModGuiFactoryを継承したものを@ModのguiFactory="com.～～"みたいに指定する。<br>
	 * 	<q>@SubscribeEvent<br>
	public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent e){<br>
		if(e.modID.equals(Unsaga.MODID)){<br>
			Unsaga.debug(this.getClass().getName(),"sync!");<br>
			Unsaga.configs.syncConfig();<br>
		}<br>
	}</q>
	あと↑こんな感じでコンフィグが変わった時のイベントを追加する。
	（FMLCommonHandlerに登録なので注意。）
	 * @param config
	 * @param category
	 * @return
	 */
	public static List<IConfigElement> getConfigElements(Configuration config,String category){
		return new ConfigElement(config.getCategory(category)).getChildElements();
	}
	
	public static String getTitleLine2(Configuration config){
		return GuiConfig.getAbridgedConfigPath(config.toString());
	}
	
	//↓コピーして使う用
//	public GuiConfigUnsaga(GuiScreen parentScreen) {
//		super(parentScreen, ConfigGuiSettings.getConfigElements(Unsaga.configFile, Configuration.CATEGORY_GENERAL), Unsaga.MODID, "config.unsaga", false,
//				false, "Unsaga Mod Configuration", ConfigGuiSettings.getTitleLine2(Unsaga.configFile));
//		// TODO 自動生成されたコンストラクター・スタブ
//	}
}
