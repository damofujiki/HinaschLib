package mods.hinasch.lib.debuff;
//package com.hinasch.lib.debuff;
//
//import com.google.common.base.Predicate;
//import com.hinasch.lib.ClientHelper;
//import com.hinasch.lib.core.HSLib;
//import com.hinasch.lib.intf.BiConsumer;
//
//import io.netty.buffer.ByteBuf;
//import net.minecraft.entity.EntityLivingBase;
//import net.minecraft.entity.player.EntityPlayer;
//import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
//import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
//import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
//
//
//
//public class PacketSyncDebuff implements IMessage{
//
//	protected int number;
//	protected int entityid;
//	protected int remain;
//	protected String dataStr;
//	protected int mode;
//
//	public static final int Sync_END = 1;
//	public static final int Sync_START  = 2;
//	public PacketSyncDebuff(){
//
//	}
//
//	public PacketSyncDebuff(int entityid,int number){
//		this.number = number;
//		this.entityid = entityid;
//		this.mode = Sync_END;
//	}
//
//	public PacketSyncDebuff(int entityid,int number,int remain){
//		this.number = number;
//		this.entityid = entityid;
//		this.remain = remain;
//		this.mode = Sync_START;
//
//	}
//
//	public int getMode(){
//		return this.mode;
//	}
//
//	public int getEntityID(){
//		return this.entityid;
//	}
//
//	public int getRemainingTime(){
//		return this.remain;
//	}
//
//
//	public int getDebuffNumber(){
//		return this.number;
//	}
//
//
//	@Override
//	public void fromBytes(ByteBuf buffer) {
//		this.mode = buffer.readInt();
//		this.entityid = buffer.readInt();
//		this.number = buffer.readInt();
//		if(this.mode==Sync_START){
//			this.remain = buffer.readInt();
//		}
//
//	}
//
//	@Override
//	public void toBytes(ByteBuf buf) {
//		buf.writeInt(mode);
//		buf.writeInt(entityid);
//		buf.writeInt(number);
//		switch(this.mode){
//		case Sync_END:
//			break;
//		case Sync_START:
//			buf.writeInt(remain); //クライアント側はremainさえ合えばいいので
//			break;
//		}
//
//	}
//
//	public static class PacketSyncDebuffHandler implements IMessageHandler<PacketSyncDebuff,IMessage>{
//
//		protected BiConsumer<EntityLivingBase,DebuffBase> debuffRemover  = new BiConsumer<EntityLivingBase,DebuffBase>(){
//
//			@Override
//			public void accept(EntityLivingBase living, DebuffBase debuff) {
//				if(DebuffHelper.hasDebuff(living, debuff)){
//					DebuffHelper.removeDebuff(living, debuff);
//				}
//			}};
//
//
//			protected Predicate<PacketSyncDebuff> isEntityEmpty = new Predicate<PacketSyncDebuff>(){
//
//				@Override
//				public boolean apply(PacketSyncDebuff input) {
//					if(ClientHelper.getClientPlayer().worldObj.getEntityByID(input.getEntityID())==null){
//						return true;
//					}
//					return false;
//				}};
//		@Override
//		public IMessage onMessage(PacketSyncDebuff message, MessageContext ctx) {
//			EntityPlayer player;
//			if(ctx.side.isClient()){
//				player = ClientHelper.getClientPlayer();
////				Unsaga.debug("kitemasu",this.getClass());
//				switch(message.getMode()){
//				case Sync_END:
//					if(!isEntityEmpty.apply(message)){
//						this.syncDebuffEnd(message);
//					}
//
//					break;
//				case Sync_START:
//					if(!isEntityEmpty.apply(message)){
//						this.syncDebuffStart(message);
//					}
//					break;
//				}
//				return null;
//			}else{
//				player = ctx.getServerHandler().playerEntity;
//				if(message.getMode()==Sync_END){
//					if(HSLib.debuffRegistry.getObject(message.getDebuffNumber())!=null && player.worldObj.getEntityByID(message.getEntityID())!=null){
//						DebuffBase debuff = HSLib.debuffRegistry.getObject(message.getDebuffNumber());
////						DebuffBase debuff = Unsaga.debuffs.getDebuff(message.getDebuffNumber());
//						EntityLivingBase entity = (EntityLivingBase) player.worldObj.getEntityByID(message.getDebuffNumber());
//						debuffRemover.accept(entity, debuff);
//					}
//				}
//			}
//			return null;
//		}
//
//
//		public void syncDebuffEnd(PacketSyncDebuff message){
//			EntityPlayer player = ClientHelper.getClientPlayer();
//			EntityLivingBase living = (EntityLivingBase) player.worldObj.getEntityByID(message.getEntityID());
//			if(DebuffHelper.hasDebuff(living, HSLib.debuffRegistry.getObject(message.getDebuffNumber()))){
//				DebuffBase debuff = HSLib.debuffRegistry.getObject(message.getDebuffNumber());
//				debuffRemover.accept(living, debuff);
//			}
//		}
//
//		public void syncDebuffStart(PacketSyncDebuff message){
//			EntityPlayer player = ClientHelper.getClientPlayer();
////			Unsaga.debug(player.worldObj.getEntityByID(message.getEntityID()),this.getClass());
//			EntityLivingBase linving = (EntityLivingBase) player.worldObj.getEntityByID(message.getEntityID());
//			if(!DebuffHelper.hasDebuff(linving, HSLib.debuffRegistry.getObject(message.getDebuffNumber()))){
////				Unsaga.debug("add",this.getClass());
////				DebuffHelper.addDebuff(linving, Unsaga.debuffs.getDebuff(message.getDebuffNumber()), message.getRemainingTime());
//			}else{
//				LivingDebuffBase ld = DebuffHelper.getLivingDebuff(linving, HSLib.debuffRegistry.getObject(message.getDebuffNumber())).get();
//				ld.syncRemain(message.getRemainingTime());
//			}
//		}
//	}
//}
