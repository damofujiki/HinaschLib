package mods.hinasch.lib.core;



import mods.hinasch.lib.capability.CapabilityInventory;
import mods.hinasch.lib.capability.VillagerHelper;
import mods.hinasch.lib.core.plugin.HSLibPluginIntegration;
import mods.hinasch.lib.debuff.capability.DefaultICustomDebuff;
import mods.hinasch.lib.debuff.capability.ICustomDebuff;
import mods.hinasch.lib.debuff.capability.StorageICustomDebuff;
import mods.hinasch.lib.primitive.LogWrapper;
import mods.hinasch.lib.util.HSLibs;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;



@Mod(modid = HSLib.MODID  ,name = HSLib.NAME , version= HSLib.VERSION,guiFactory="mods.hinasch.lib.core.ModGuiFactoryHSLib",dependencies="after:TConstruct,dcs_climate|lib,Waila")
public class HSLib {
	public static final String MODID = "hinasch.lib";
	public static final String NAME = "Hinaschlippenbach Lib";
	public static final String VERSION = "1.0.1 16/8/29";
	public static final String DOMAIN_GENERIC = "hinasch.generic";

//	public static Optional<Boolean> debug = Optional.empty();

	@SidedProxy(modId= HSLib.MODID,clientSide = "mods.hinasch.lib.core.ClientProxy", serverSide = "mods.hinasch.lib.core.CommonProxy")
	public static CommonProxy proxy;

//	public static boolean trace = true;
	public static boolean isUnsagaLoaded = false;
	@Instance(HSLib.MODID)
	public static HSLib instance;


	public static Configuration configFile;
	public static ConfigHandlerHSLib configHandler = new ConfigHandlerHSLib();
	public static LogWrapper logger = LogWrapper.newLogger(MODID);



	private static final HSLibIntegration core = new HSLibIntegration();


	private static final HSLibPluginIntegration plugin = new HSLibPluginIntegration();

	public static HSLibIntegration core(){
		return core;
	}

	public static boolean isDebug(){
//		if(debug.isPresent()){
//			return debug.get();
//		}
//		checkLoadedMods();
//		if(debug.isPresent()){
//			return debug.get();
//		}

		return configHandler.isDebug();
	}


	public static HSLibPluginIntegration plugin(){
		return plugin;
	}

	//レシピなど
	@EventHandler
	public void init(FMLInitializationEvent event)
	{

		core.initDropEvents();
		proxy.registerEvents();



		proxy.registerRenderers();

	}

	//連携関連
	@EventHandler
	public void postInit(FMLPostInitializationEvent event)
	{


		plugin.load();
		core.events.livingHurt.sortAll();
	}
	//基本設定、アイテムブロックのついか　はここ
	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		logger = LogWrapper.create(event);
		configFile = new Configuration(event.getSuggestedConfigurationFile());
		configFile.load();
		configHandler.setConfigFile(configFile).init();
		configHandler.syncConfig();
		plugin.checkLoadedMods();
		core.registerDispatchers();

		HSLibs.registerCapability(ICustomDebuff.class, new StorageICustomDebuff(), DefaultICustomDebuff.class);
		VillagerHelper.register();
		CapabilityInventory.adapterBase.registerCapability();


		core.items.register();

		configFile.save();
	}

}
