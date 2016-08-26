package mods.hinasch.lib.network;

import io.netty.buffer.ByteBuf;
import mods.hinasch.lib.client.ClientHelper;
import mods.hinasch.lib.core.HSLib;
import mods.hinasch.lib.debuff.DebuffBase;
import mods.hinasch.lib.debuff.DebuffEffectBase;
import mods.hinasch.lib.debuff.DebuffHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketSyncDebuffNew implements IMessage{

	public static class Handler implements IMessageHandler<PacketSyncDebuffNew,IMessage>{

		@Override
		public IMessage onMessage(PacketSyncDebuffNew message, MessageContext ctx) {
			if(ctx.side.isClient()){


				if(message.getTargetid()>0){
					Entity entity = ClientHelper.getWorld().getEntityByID(message.getTargetid());
					switch(message.getType()){
					case END:
						if(entity instanceof EntityLivingBase){
							HSLib.logger.trace(message.getDebuff().getName()+" is expired.(sync)");
							DebuffHelper.removeDebuff((EntityLivingBase) entity, message.getDebuff());
						}
						break;
					case START:
						if(message.getData()!=null){
							if(entity instanceof EntityLivingBase){
								HSLib.logger.trace(message.getDebuff().getName()+" is start.(sync)");
								DebuffEffectBase debuffEffect;
								try{
									debuffEffect = message.getDebuff().initLivingDebuff(message.getData());
									DebuffHelper.addLivingDebuff((EntityLivingBase) entity, debuffEffect);
								}catch(Exception e){
									e.printStackTrace();

								}


							}

						}
						break;
					default:
						break;

					}

				}

			}
			return null;
		}

	};
	public static enum Type {START(0),END(1);

		public static Type fromInt(int meta){
			for(Type type:Type.values()){
				if(type.getMeta()==meta){
					return type;
				}
			}
			return null;
		}

		private int meta;

		private Type(int meta){
			this.meta = meta;
		}

		public int getMeta(){return this.meta;}
	}

	public static PacketSyncDebuffNew syncEnd(EntityLivingBase target,DebuffBase debuff){
		return new PacketSyncDebuffNew(target,debuff,null,Type.END);
	}

	public static PacketSyncDebuffNew syncStart(EntityLivingBase target,DebuffEffectBase debuffEffect){
		return new PacketSyncDebuffNew(target,debuffEffect.getDebuff(),debuffEffect,Type.START);
	}

	int targetid  = -1;

	DebuffBase debuff;


	Type type;
	int remain;
	NBTTagCompound data;

	public PacketSyncDebuffNew(){

	}

	protected PacketSyncDebuffNew(EntityLivingBase target,DebuffBase debuff,DebuffEffectBase debuffEffect,Type type){

		this.debuff = debuff;
		this.type = type;
		this.targetid = target.getEntityId();
		this.remain = debuffEffect!=null ? debuffEffect.getRemaining() : -1;
		this.data = new NBTTagCompound();
		if(type==Type.START){
			debuffEffect.writeToNBT(data);
		}


	}

	@Override
	public void fromBytes(ByteBuf buf) {
		this .debuff = HSLib.debuffRegistry.getObjectById(buf.readInt());
		this.type = Type.fromInt(buf.readInt());
		this.targetid = buf.readInt();
		if(this.type==Type.START){
			int length = buf.readInt();
			if(length>0){

				ByteBuf buf2 = buf.readBytes(length);
				this.data = PacketUtil.bytesToNBT(buf2);
			}
		}


	}

	public NBTTagCompound getData() {
		return data;
	}
	public DebuffBase getDebuff() {
		return debuff;
	}

	public int getRemain() {
		return remain;
	}

	public int getTargetid() {
		return targetid;
	}


	public Type getType() {
		return type;
	}

	public void setData(NBTTagCompound data) {
		this.data = data;
	}

	public void setDebuff(DebuffBase debuff) {
		this.debuff = debuff;
	}

	public void setRemain(int remain) {
		this.remain = remain;
	}

	public void setTargetid(int targetid) {
		this.targetid = targetid;
	}

	public void setType(Type type) {
		this.type = type;
	}

	@Override
	public void toBytes(ByteBuf buf) {
		// TODO 自動生成されたメソッド・スタブ

		buf.writeInt(debuff.getID());
		buf.writeInt(type.getMeta());
		buf.writeInt(this.targetid);
		if(type==Type.START){
			if(PacketUtil.nbtToBytes(data)!=null){
				byte[] bytes = PacketUtil.nbtToBytes(data);
				buf.writeInt(bytes.length);
				buf.writeBytes(bytes);
			}else{
				buf.writeInt(-1);
			}
		}

	}
}
