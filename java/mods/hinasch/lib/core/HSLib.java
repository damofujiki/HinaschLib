package mods.hinasch.lib.core;



import java.util.List;

import com.google.common.base.Optional;

import mods.hinasch.lib.CustomDropEvent;
import mods.hinasch.lib.ScannerPool;
import mods.hinasch.lib.capability.CapabilityAdapterFactory;
import mods.hinasch.lib.capability.CapabilityInventory;
import mods.hinasch.lib.capability.VillagerHelper;
import mods.hinasch.lib.client.ClientHelper;
import mods.hinasch.lib.client.ClientHelper.ModelHelper;
import mods.hinasch.lib.client.ClientHelper.PluralVariantsModelFactory;
import mods.hinasch.lib.client.RenderPlayerHook;
import mods.hinasch.lib.config.EventConfigChanged;
import mods.hinasch.lib.debuff.DebuffBase;
import mods.hinasch.lib.debuff.EventAttachDebuff;
import mods.hinasch.lib.debuff.EventLivingUpdateDebuff;
import mods.hinasch.lib.debuff.ItemIconBuff;
import mods.hinasch.lib.debuff.capability.DefaultICustomDebuff;
import mods.hinasch.lib.debuff.capability.ICustomDebuff;
import mods.hinasch.lib.debuff.capability.StorageICustomDebuff;
import mods.hinasch.lib.entity.EventLivingDrops;
import mods.hinasch.lib.item.ItemDebug;
import mods.hinasch.lib.item.ItemIcon;
import mods.hinasch.lib.network.AbstractDispatcherAdapter;
import mods.hinasch.lib.network.PacketChangeGuiMessage;
import mods.hinasch.lib.network.PacketSound;
import mods.hinasch.lib.network.PacketSound.PacketSoundHandler;
import mods.hinasch.lib.network.PacketSyncCapability;
import mods.hinasch.lib.network.PacketSyncDebuffNew;
import mods.hinasch.lib.particle.PacketParticle;
import mods.hinasch.lib.particle.ParticleTypeWrapper;
import mods.hinasch.lib.primitive.LogWrapper;
import mods.hinasch.lib.util.HSLibs;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.RegistryNamespaced;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;



@Mod(modid = HSLib.MODID  ,name = HSLib.NAME , version= HSLib.VERSION,guiFactory="mods.hinasch.lib.core.ModGuiFactoryHSLib")
public class HSLib {
	public static final String MODID = "hinasch.lib";
	public static final String NAME = "Hinaschlippenbach Lib";
	public static final String VERSION = "1.0.1 16/8/29";
	public static final String DOMAIN_GENERIC = "hinasch.generic";

	public static Optional<Boolean> debug = Optional.absent();

//	public static boolean trace = true;
	public static boolean isUnsagaLoaded = false;
	@Instance(HSLib.MODID)
	public static HSLib instance;

	public static Configuration configFile;
	public static final ConfigHandlerHSLib configHandler = new ConfigHandlerHSLib();
	public static LogWrapper logger = LogWrapper.newLogger(MODID);

	public static CapabilityAdapterFactory capabilityAdapterFactory = new CapabilityAdapterFactory(MODID);

	@CapabilityInject(ICustomDebuff.class)
	public static Capability<ICustomDebuff> CAP_DEBUFF = null;

	public static Item itemDebugArmor;

	public static ItemIcon itemParticleIcon;
	public static ItemIconBuff itemIconBuff;

	public static final EventBreakSpeed breakSpeedEvent = EventBreakSpeed.instance();
	public static final RegistryNamespaced<ResourceLocation,DebuffBase> debuffRegistry = new RegistryNamespaced();

	public static final ClientHelper.ModelHelper modelHelper = new ModelHelper(MODID);

	public static final AbstractDispatcherAdapter packetParticle = new AbstractDispatcherAdapter<PacketParticle>(){

		@Override
		public SimpleNetworkWrapper getDispatcher() {
			// TODO 自動生成されたメソッド・スタブ
			return packetDispatcher;
		}
	};


	private static EventLivingDrops dropEvent;

	private static final SimpleNetworkWrapper packetDispatcher = NetworkRegistry.INSTANCE.newSimpleChannel(MODID);

	public static final ScannerPool scannerEventPool = ScannerPool.create().setLogger(logger);


	public static final EventLivingUpdate eventLivingUpdate = new EventLivingUpdate();
	public static final EventLivingHurt eventLivingHurt = new EventLivingHurt();

	//基本設定、アイテムブロックのついか　はここ
	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		configFile = new Configuration(event.getSuggestedConfigurationFile());
		configFile.load();
		configHandler.setConfigFile(configFile).init();
		configHandler.syncConfig();
		checkLoadedMods();

		logger = LogWrapper.create(event);
		HSLibs.registerCapability(ICustomDebuff.class, new StorageICustomDebuff(), DefaultICustomDebuff.class);
		VillagerHelper.register();
		CapabilityInventory.adapterBase.registerCapability();
		this.itemDebugArmor = new ItemDebug().setUnlocalizedName("armor.debug");
		this.itemIconBuff = new ItemIconBuff();
		this.itemParticleIcon = (ItemIcon) new ItemIcon().setRegistryName("iconParticle");
		GameRegistry.register(itemIconBuff.setRegistryName("iconBuff"));
		GameRegistry.register(itemParticleIcon);
		GameRegistry.register(itemDebugArmor.setRegistryName("armor.debug"));
		if(debug.isPresent()){
			if(debug.get()){
				this.itemDebugArmor.setCreativeTab(CreativeTabs.COMBAT);
			}
		}

		configFile.save();
	}

	//レシピなど
	@EventHandler
	public void init(FMLInitializationEvent event)
	{


		VillagerHelper.registerEvent();

		HSLibs.registerEvent(new EventKeyBinding());
		HSLibs.registerEvent(new EventEntityJoinWorld());
		HSLibs.registerEvent(new EventAttachDebuff());
		HSLibs.registerEvent(EventConfigChanged.instance().setLogger(logger));
		HSLibs.registerEvent(eventLivingUpdate);
		dropEvent = new EventLivingDrops().init();
		HSLibs.registerEvent(dropEvent);
		eventLivingUpdate.getEvents().add(scannerEventPool);
		eventLivingUpdate.getEvents().add(new EventLivingUpdateDebuff());
		CapabilityInventory.registerEvents();
//		if(event.getSide().isClient()){
//			HSLibs.registerEvent(new PlaySFXEvent());
//		}
		HSLibs.registerEvent(breakSpeedEvent);
		HSLibs.registerEvent(eventLivingHurt);
		packetDispatcher.registerMessage(PacketSyncDebuffNew.Handler.class, PacketSyncDebuffNew.class, 1, Side.CLIENT);
		packetDispatcher.registerMessage(PacketParticle.Handler.class, PacketParticle.class, 2, Side.CLIENT);
		packetDispatcher.registerMessage(PacketSoundHandler.class, PacketSound.class, 3, Side.CLIENT);
		packetDispatcher.registerMessage(PacketChangeGuiMessage.Handler.class, PacketChangeGuiMessage.class, 4, Side.CLIENT);
		packetDispatcher.registerMessage(PacketSyncCapability.Handler.class, PacketSyncCapability.class, 5, Side.CLIENT);
		packetDispatcher.registerMessage(PacketSyncCapability.Handler.class, PacketSyncCapability.class, 6, Side.SERVER);


		EventConfigChanged.instance().addConfigHandler(MODID, configHandler);

		if(event.getSide().isClient()){
			HSLibs.registerEvent(RenderPlayerHook.instance());
			final ModelHelper modelAgent = new ModelHelper(MODID);

			PluralVariantsModelFactory.create(modelAgent, itemParticleIcon)
			.prepareVariants(ParticleTypeWrapper.Particles.getJsonNames())
			.attach(ParticleTypeWrapper.Particles.getJsonNames().iterator(), (String input,Integer index) -> (modelAgent.getNewModelResource(input, "inventory")));

			PluralVariantsModelFactory.create(modelAgent, itemIconBuff)
			.prepareVariants(ItemIconBuff.getJsonNames())
			.attach(ItemIconBuff.getJsonNames().iterator(), (String input,Integer index) -> (modelAgent.getNewModelResource(input, "inventory")));
//			ClientHelper.addToModelBakeryWithVariantName(itemIconBuff, MODID, ItemIconBuff.getJsonNames());
//			ClientHelper.registerWithSubTypes(itemIconBuff, ItemIconBuff.getJsonNames(), new ModelResourceFunction<String>(){
//
//				@Override
//				public ModelResourceLocation apply(String input,Integer index) {
//					return modelAgent.getNewModelResource(input, "inventory");
//				}}
//			);
		}
	}

	//連携関連
	@EventHandler
	public void postInit(FMLPostInitializationEvent event)
	{



		eventLivingHurt.sortAll();
	}

	public static void registerDebuff(DebuffBase base){
		HSLib.logger.get().info("registering debuff "+base.getName());
		debuffRegistry.register(base.getID(),base.getKey(), base);
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
	public static void checkLoadedMods(){

		String className1[] = {
				"hinasch.mods.Debug","com.hinasch.unlsaga.Unsaga"
		};
		String cn = null;
		for(int i=0;i<className1.length;i++){
			try{
				//cn = getClassName(className1[i]);
				cn = className1[i];
				//System.out.println(cn);
				//cn="realterrainbiomes.mods.RTBiomesCore";
				cn = ""+Class.forName(cn);

				switch(i){
				case 0:
					debug = Optional.of(true);
					break;
				case 1:
					isUnsagaLoaded = true;
					break;

				}
			}catch(ClassNotFoundException e){

			}
		}




	}



	public static void addMobDrop(CustomDropEvent e){
		getMobDropList().add(e);
	}
	public static List<CustomDropEvent> getMobDropList(){
		return dropEvent.getDropList();
	}
	public static SimpleNetworkWrapper getPacketDispatcher(){
		return packetDispatcher;
	}

	public static AbstractDispatcherAdapter<PacketParticle> getParticlePacketSender(){
		return packetParticle;
	}


}
