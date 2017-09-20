package mods.hinasch.lib.core;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;

import com.google.common.collect.Maps;
import com.mojang.realmsclient.util.Pair;

import mods.hinasch.lib.CustomDropEvent;
import mods.hinasch.lib.capability.CapabilityAdapterFactory;
import mods.hinasch.lib.client.ITextMenuAdapter;
import mods.hinasch.lib.debuff.DebuffBase;
import mods.hinasch.lib.debuff.capability.ICustomDebuff;
import mods.hinasch.lib.entity.EventLivingDrops;
import mods.hinasch.lib.iface.IModBase;
import mods.hinasch.lib.network.AbstractDispatcherAdapter;
import mods.hinasch.lib.network.PacketChangeGuiMessage;
import mods.hinasch.lib.network.PacketOpenGui;
import mods.hinasch.lib.network.PacketSendGuiInfoToClient;
import mods.hinasch.lib.network.PacketSound;
import mods.hinasch.lib.network.PacketSound.PacketSoundHandler;
import mods.hinasch.lib.network.PacketSyncCapability;
import mods.hinasch.lib.network.PacketSyncDebuffNew;
import mods.hinasch.lib.network.textmenu.PacketGuiButtonHSLib;
import mods.hinasch.lib.particle.PacketParticle;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.RegistryNamespaced;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class HSLibIntegration {
	private static SimpleNetworkWrapper packetDispatcher = NetworkRegistry.INSTANCE.newSimpleChannel(HSLib.MODID);


	public static CapabilityAdapterFactory capabilityAdapterFactory = CapabilityAdapterFactory.create(HSLib.MODID);

	@CapabilityInject(ICustomDebuff.class)
	public static Capability<ICustomDebuff> CAP_DEBUFF = null;

	public static Map<Pair<String,Integer>,ITextMenuAdapter> adapterMap = Maps.newHashMap();

	public static HSLibItems items = new HSLibItems();

	public static RegistryNamespaced<ResourceLocation,DebuffBase> debuffRegistry = new RegistryNamespaced();


	public static HSLibEvents events = new HSLibEvents();

	public static Map<Integer,IModBase> guis = Maps.newHashMap();


	public static AbstractDispatcherAdapter packetParticle = new AbstractDispatcherAdapter<PacketParticle>(){

		@Override
		public SimpleNetworkWrapper getDispatcher() {
			// TODO 自動生成されたメソッド・スタブ
			return packetDispatcher;
		}
	};

	public static void addMobDrop(CustomDropEvent e){
		getMobDropList().add(e);
	}
	public static EventLivingDrops getDropEvent(){
		return events.drop;
	}

	public static List<CustomDropEvent> getMobDropList(){
		return getDropEvent().getDropList();
	}
	public static SimpleNetworkWrapper getPacketDispatcher(){
		return packetDispatcher;
	}
	public static AbstractDispatcherAdapter<PacketParticle> getParticlePacketSender(){
		return packetParticle;
	}
	public static ITextMenuAdapter getTextMenuAdapter(String modid,int id){
		Optional<Entry<Pair<String, Integer>, ITextMenuAdapter>> ad = adapterMap.entrySet().stream().filter(in -> in.getKey().first()==modid && in.getKey().second()==id).findFirst();
		if(ad.isPresent()){
			return ad.get().getValue();
		}
		return null;
	}


	public static void initDropEvents(){
		events.drop.init();
	}
	public static void registerDebuff(DebuffBase base){
		HSLib.logger.get().info("registering debuff "+base.getName());
		debuffRegistry.register(base.getID(),base.getKey(), base);
	}

	public static void registerTextMenuAdapter(String modid,int id,ITextMenuAdapter adapter){
		adapterMap.put(Pair.of(modid, id), adapter);
	}

	public void registerGuiPacketHandler(int id,IModBase mod){
		if(!guis.containsKey(id)){
			guis.put(id, mod);
		}else{
			HSLib.logger.trace(this.getClass().getName(), "duplicated id",id,mod);
		}

	}
	public void registerDispatchers(){
		packetDispatcher.registerMessage(PacketSyncDebuffNew.Handler.class, PacketSyncDebuffNew.class, 1, Side.CLIENT);
		packetDispatcher.registerMessage(PacketParticle.Handler.class, PacketParticle.class, 2, Side.CLIENT);
		packetDispatcher.registerMessage(PacketSoundHandler.class, PacketSound.class, 3, Side.CLIENT);
		packetDispatcher.registerMessage(PacketChangeGuiMessage.Handler.class, PacketChangeGuiMessage.class, 4, Side.CLIENT);
		packetDispatcher.registerMessage(PacketSyncCapability.Handler.class, PacketSyncCapability.class, 5, Side.CLIENT);
		packetDispatcher.registerMessage(PacketSyncCapability.Handler.class, PacketSyncCapability.class, 6, Side.SERVER);
		packetDispatcher.registerMessage(PacketGuiButtonHSLib.Handler.class, PacketGuiButtonHSLib.class, 7, Side.SERVER);
		packetDispatcher.registerMessage(PacketOpenGui.Handler.class, PacketOpenGui.class, 8, Side.SERVER);
		packetDispatcher.registerMessage(PacketSendGuiInfoToClient.Handler.class, PacketSendGuiInfoToClient.class, 9, Side.CLIENT);
		packetDispatcher.registerMessage(PacketSendGuiInfoToClient.Handler.class, PacketSendGuiInfoToClient.class, 10, Side.SERVER);


	}
}
