package mods.hinasch.lib.network;

import io.netty.buffer.ByteBuf;
import joptsimple.internal.Strings;
import mods.hinasch.lib.util.SoundAndSFX;
import mods.hinasch.lib.util.UtilNBT;
import mods.hinasch.lib.world.XYZPos;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;



public class PacketSound implements IMessage{

	//protected int mode;
	protected Type type;
	protected int soundnumber = -1;
	protected int entityid = -1;
	protected String soundName = "";
	protected XYZPos pos = XYZPos.ZERO;
	protected NBTTagCompound nbt;
//	protected static final int PLAY_AUX_FROM_PLAYER = 0x01;
//	protected static final int PLAY_SOUND_AT_ENTITY = 0x02;
//	protected static final int PLAY_AUX_AT_ENTITY = 0x03;
//	protected static final int PLAY_SOUND_AT_POS = 0x04;

	public static enum Type{
		AUX_FROM_PLAYER(1),SOUND_AT_ENTITY(2),AUX_AT_ENTITY(3),
		SOUND_AT_POS(4);

		public int getMeta() {
			return meta;
		}

		private final int meta;

		private Type(int meta){
			this.meta = meta;
		}

		public static Type fromMeta(int meta){
			for(Type type:Type.values()){
				if(type.getMeta()==meta){
					return type;
				}
			}
			return null;

		}
	}
	public static class MODE{
		public static final int AUX = 0x01;
		public static final int SOUND = 0x02;
	}

	public static PacketSound create(int number){

		return new PacketSound(Type.AUX_FROM_PLAYER,number,Strings.EMPTY,-1,XYZPos.ZERO);
	}

	public static PacketSound atEntity(SoundEvent sound,Entity entity){
		return new PacketSound(Type.SOUND_AT_ENTITY,-1,sound.getSoundName().getResourcePath(),entity.getEntityId(),XYZPos.ZERO);
	}
	public static PacketSound atEntity(int auxnumber,Entity entity){
		return new PacketSound(Type.AUX_AT_ENTITY,auxnumber,Strings.EMPTY,entity.getEntityId(),XYZPos.ZERO);
	}

	public static PacketSound atPos(SoundEvent sound,XYZPos pos){
		return new PacketSound(Type.SOUND_AT_POS,-1,sound.getSoundName().getResourcePath(),-1,pos);
	}
	public PacketSound(){

	}


	protected PacketSound(Type type,int soundnum,String soundName,int entityid,XYZPos pos){
		this.soundnumber = soundnum;
		this.soundName = soundName;
		this.entityid = entityid;
		this.pos = pos;
		this.type = type;



	}




	public Type getType(){
		int num = this.nbt.getInteger("type");
		return Type.fromMeta(num);
	}
	@Override
	public void fromBytes(ByteBuf buffer) {
		int length = buffer.readInt();
		ByteBuf bytes = buffer.readBytes(length);
		this.nbt = PacketUtil.bytesToNBT(bytes);


	}

	@Override
	public void toBytes(ByteBuf buffer) {

		this.nbt = UtilNBT.createCompound();
		nbt.setInteger("type", type.getMeta());
		nbt.setInteger("soundnumber", this.soundnumber);
		nbt.setString("soundname", this.soundName);
		nbt.setInteger("entityid", this.entityid);
		this.pos.writeToNBT(nbt);

		byte[] bytes = PacketUtil.nbtToBytes(nbt);
		buffer.writeInt(bytes.length);
		buffer.writeBytes(bytes);

//		UnsagaMod.logger.trace("soundname", this.soundName);


	}

	public static class PacketSoundHandler implements IMessageHandler<PacketSound,IMessage>{


		@Override
		public IMessage onMessage(PacketSound message, MessageContext ctx) {
			if(ctx.side.isClient()){
				EntityPlayer player = Minecraft.getMinecraft().thePlayer;
				Entity entity = null;
				XYZPos pos = null;
				SoundEvent se = null;
				NBTTagCompound comp = message.nbt;
//				UnsagaMod.logger.trace("type", message.getType());
				switch(message.getType()){
				case AUX_FROM_PLAYER:
					if(comp.getInteger("soundnumber")>0){
						player.worldObj.playEvent(comp.getInteger("soundnumber"), XYZPos.createFrom(player), 0);
					}
					break;
				case SOUND_AT_ENTITY:
					entity = player.worldObj.getEntityByID(comp.getInteger("entityid"));
					if(entity!=null){
						SoundEvent sound = SoundEvent.REGISTRY.getObject(new ResourceLocation(comp.getString("soundname")));

//						UnsagaMod.logger.trace("soundname", sound.getRegistryName(),entity);
						if(sound!=null){
							pos = XYZPos.createFrom(entity);
							SoundAndSFX.playSound(player.worldObj, pos.dx, pos.dy, pos.dz, sound ,SoundCategory.NEUTRAL, 1.0F, 1.0F / (player.worldObj.rand.nextFloat() * 0.4F + 1.2F) + 1.0F * 0.5F,false);
//							player.worldObj.playSound(pos.dx, pos.dy, pos.dz, sound ,SoundCategory.NEUTRAL, 1.0F, 1.0F / (player.worldObj.rand.nextFloat() * 0.4F + 1.2F) + 1.0F * 0.5F,false);
						}
					}
					break;
				case AUX_AT_ENTITY:
					entity = player.worldObj.getEntityByID(comp.getInteger("entityid"));
					if(entity!=null){
						pos = XYZPos.readFromNBT(comp);
						player.worldObj.playEvent(comp.getInteger("soundnumber"), pos, 0);
					}
					break;
				case SOUND_AT_POS:
					pos = XYZPos.readFromNBT(comp);
					se = SoundEvent.REGISTRY.getObject(new ResourceLocation(comp.getString("soundname")));

					if(se!=null){
//						UnsagaMod.logger.trace("soundname", se.getRegistryName());
						SoundAndSFX.playSound(player.worldObj, (double)pos.getX(), (double)pos.getY(), (double)pos.getZ(), se ,SoundCategory.NEUTRAL, 1.0F, 1.0F / (player.worldObj.rand.nextFloat() * 0.4F + 1.2F) + 1.0F * 0.5F,false);
//						player.worldObj.playSound((double)pos.getX(), (double)pos.getY(), (double)pos.getZ(), se ,SoundCategory.NEUTRAL, 1.0F, 1.0F / (player.worldObj.rand.nextFloat() * 0.4F + 1.2F) + 1.0F * 0.5F,false);

					}
					break;
				}
			}
			return null;
		}

	}
}
