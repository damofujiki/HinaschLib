package mods.hinasch.lib.network;

import java.util.Map;

import com.google.common.collect.Maps;

import io.netty.buffer.ByteBuf;
import mods.hinasch.lib.capability.ISyncCapability;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketSyncCapability<T extends ISyncCapability> implements IMessage{

	public static Map<String,Capability<?>> syncCapabilityMap = Maps.newHashMap();
	Capability<T> capability;
	T capabilityInstance;
	NBTTagCompound nbt;
	NBTTagCompound args;


	public NBTTagCompound getArgs() {
		return args;
	}
	public static <K extends ISyncCapability> PacketSyncCapability create(Capability<K> capability,K capa,NBTTagCompound... args){
		return new PacketSyncCapability(capability,capa,args);
	}
	public PacketSyncCapability(){

	}
	protected PacketSyncCapability(Capability<T> capability,T capa,NBTTagCompound... args){
		this.capabilityInstance = capa;
		this.capability = capability;
		if(args.length>0){
			this.args = args[0];
		}
	}
	public Capability<T> getCapability() {
		return capability;
	}

	public NBTTagCompound getNbt() {
		return nbt;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		// TODO 自動生成されたメソッド・スタブ

		int length = buf.readInt();
		ByteBuf bytes = buf.readBytes(length);
		NBTTagCompound comp = PacketUtil.bytesToNBT(bytes);

		this.nbt = comp;
		String id = nbt.getString("identify");
		int length2 = buf.readInt();
		if(length2>0){
			ByteBuf child = buf.readBytes(length2);
			this.args = PacketUtil.bytesToNBT(child);
		}
		this.capability = (Capability<T>) syncCapabilityMap.get(id);
		this.capabilityInstance = this.capability.getDefaultInstance();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		NBTTagCompound comp = capabilityInstance.getSendingData();
		comp.setString("identify", capabilityInstance.getIdentifyName());

		byte[] bytes = PacketUtil.nbtToBytes(comp);
		buf.writeInt(bytes.length);
		buf.writeBytes(bytes);
		if(this.args!=null){
			byte[] child = PacketUtil.nbtToBytes(args);
			buf.writeInt(child.length);
			buf.writeBytes(child);
		}else{
			buf.writeInt(-1);
		}
	}

	public static class Handler implements IMessageHandler<PacketSyncCapability,IMessage>{

		@Override
		public IMessage onMessage(PacketSyncCapability message, MessageContext ctx) {
			if(ctx.side.isClient()){
				 message.capabilityInstance.onPacket(message, ctx);
			}
			if(ctx.side.isServer()){
				message.capabilityInstance.onPacket(message, ctx);
			}
			return null;
		}

	}
}
