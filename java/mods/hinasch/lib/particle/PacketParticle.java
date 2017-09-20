package mods.hinasch.lib.particle;

import java.util.Random;

import io.netty.buffer.ByteBuf;
import mods.hinasch.lib.client.ClientHelper;
import mods.hinasch.lib.core.HSLib;
import mods.hinasch.lib.iface.IIntSerializable;
import mods.hinasch.lib.network.PacketUtil;
import mods.hinasch.lib.particle.ParticleTypeWrapper.Particles;
import mods.hinasch.lib.primitive.XY;
import mods.hinasch.lib.util.HSLibs;
import mods.hinasch.lib.util.UtilNBT;
import mods.hinasch.lib.world.XYZPos;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumParticleTypes;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;


public class PacketParticle implements IMessage{


	public static enum Type implements IIntSerializable{DENS(0),TO_POS(1);

		private final int meta;

		public int getMeta() {
			return meta;
		}

		private Type(int meta){
			this.meta = meta;
		}

		public static Type fromMeta(int in){
			return HSLibs.fromMeta(Type.values(), in);
		}
	}

	//	protected Type packetId;
	protected NBTTagCompound nbt;
	protected int particleNumber;
	protected int entityId;
	protected short dens;

	protected XYZPos xyz;

	public void sendToPlayer(EntityPlayer player){
		if(player instanceof EntityPlayerMP){
			HSLib.core().getPacketDispatcher().sendTo(this, (EntityPlayerMP) player);
		}

	}

	public void sendToServer(){
		HSLib.core().getPacketDispatcher().sendToServer(this);
	}

	public void sendToAll(){
		HSLib.core().getPacketDispatcher().sendToAll(this);
	}

	public void sendToAllAround(TargetPoint point){
		HSLib.core().getPacketDispatcher().sendToAllAround(this, point);
	}

	public static PacketParticle toEntity(EnumParticleTypes type,Entity entity,int dens){
		return new PacketParticle(type,entity.getEntityId(),dens);
	}

	public static PacketParticle toEntity(ParticleTypeWrapper particleNumber, EntityLivingBase living, int dens) {
		// TODO 自動生成されたメソッド・スタブ
		return new PacketParticle(particleNumber,living.getEntityId(),dens,XYZPos.ZERO);
	}

	public static PacketParticle create(XYZPos pos,ParticleTypeWrapper particlenum,int dens) {
		// TODO 自動生成されたメソッド・スタブ
		return new PacketParticle(particlenum,-1,dens,pos);
	}
	public static PacketParticle create(XYZPos pos,EnumParticleTypes type,int dens) {
		// TODO 自動生成されたメソッド・スタブ
		return new PacketParticle(new ParticleTypeWrapper(type),-1,dens,pos);
	}
	public static PacketParticle create(XYZPos pos,Particles type,int dens) {
		// TODO 自動生成されたメソッド・スタブ
		return new PacketParticle(new ParticleTypeWrapper(type),-1,dens,pos);
	}
	public PacketParticle(){

	}

	protected PacketParticle(ParticleTypeWrapper particlenum,int entityid,int dens,XYZPos pos){
		//		this.packetId = Type.DENS;
		this.particleNumber = particlenum.getParticleID();
		this.entityId = entityid;
		this.dens = (short)dens;
		this.xyz = pos;
	}


	protected PacketParticle(EnumParticleTypes particlenum,int entityid,int dens){
		this(new ParticleTypeWrapper(particlenum),entityid,dens,XYZPos.ZERO);
	}

	protected PacketParticle(Particles particlenum,int entityid,int dens){
		this(new ParticleTypeWrapper(particlenum),entityid,dens,XYZPos.ZERO);
	}

	//	protected PacketParticle(XYZPos pos,ParticleTypeWrapper particlenum,int dens){
	////		this.packetId = Type.TO_POS;
	//
	//		this.particleNumber = particlenum.getParticleID();
	//		this.xyz = pos;
	//		this.dens = (short)dens;
	//
	//	}

	//	public PacketParticle(XYZPos pos,EnumParticleTypes particlenum,int dens){
	//		this(pos,new ParticleTypeWrapper(particlenum),dens);
	//	}


	@Override
	public void fromBytes(ByteBuf buffer) {

		int length = buffer.readInt();
		ByteBuf bytes = buffer.readBytes(length);
		this.nbt = PacketUtil.bytesToNBT(bytes);


	}

	@Override
	public void toBytes(ByteBuf buffer) {
		this.nbt = UtilNBT.createCompound();

		nbt.setInteger("number", particleNumber);
		nbt.setByte("dens", (byte)this.dens);
		nbt.setInteger("entityid", entityId);
		xyz.writeToNBT(nbt);

		byte[] bytes = PacketUtil.nbtToBytes(nbt);
		buffer.writeInt(bytes.length);
		buffer.writeBytes(bytes);




	}

	public static class Handler implements IMessageHandler<PacketParticle,IMessage>{

		@Override
		public IMessage onMessage(PacketParticle message, MessageContext ctx) {
			EntityPlayer player = ClientHelper.getPlayer();
			if(ctx.side!=Side.CLIENT){
				return null;
			}
			//			if(message.getMode()==Type.DENS){


			NBTTagCompound nbt = message.nbt;
			EnumParticleTypes particleType = null;
			//				if(message.getParticleNumber()>=200){
			//					particlestr = message.getParticleNumber()==200 ? "stone" : message.getParticleNumber()==201 ? "leave" : message.getParticleNumber()==202 ? "bubble" : "none";
			//				}else{
			//					particlestr = Statics.particleMap.get(message.getParticleNumber());
			//				}

			AbstractClientPlayer ep = (AbstractClientPlayer)player;

			if(ep==null){
				return null;
			}
			if(ep.worldObj==null){
				return null;
			}
			Entity en = ep.worldObj.getEntityByID(nbt.getInteger("entityid"));
			XYZPos pos;
			if(en!=null){
				pos = XYZPos.createFrom(en);
			}else{
				pos = XYZPos.readFromNBT(nbt);
			}
			if(pos!=null){
				int density = (int)nbt.getByte("dens");
				int particleNumber = nbt.getInteger("number");
				XYZPos motion = en!=null ? new XYZPos(en.motionX,en.motionY,en.motionZ) : new XYZPos(0,0,0);
//				XYZPos epo = XYZPos.createFrom(en);
				for (int i = 0; i < density; ++i)
				{
					if(particleNumber>=200){
						//							HSLib.debug("出てますパーティ");
						Random par5Random = ep.getRNG();
						double r = 0.8D + par5Random.nextDouble();
						double t = par5Random.nextDouble() * 2 * Math.PI;

						double d0 = pos.dx + 0.5D + r * Math.sin(t);
						double d1 = pos.dy + 0.0D + par5Random.nextDouble();
						double d2 = pos.dz + 0.5D + r * Math.cos(t);

						double d3 = Math.sin(t) / 64.0D;
						double d4 = 0.03D;
						double d5 = Math.cos(t) / 64.0D;

						CustomFX entityFX = new CustomFX(ep.worldObj,d0,d1,d2,(float)d3,(float)d4,(float)d5);
						XY particleIndex = ParticleTypeWrapper.Particles.fromMeta(particleNumber).getParticleIndex();
						entityFX.setParticleTextureIndex(particleIndex.getX(),particleIndex.getY());
						FMLClientHandler.instance().getClient().effectRenderer.addEffect(entityFX);
////						entityFX.setResourceLocation(HSLib.MODID, "textures/particles/custom.png");
//						Particles pa = ParticleTypeWrapper.Particles.fromMeta(particleNumber);
//						IParticleFactory factory = CustomParticles.getParticleFactory(pa.getParticleName());
//						if(factory!=null){
//							Particle particle = factory.getEntityFX(particleNumber, ep.worldObj, d0,d1,d2,d3,d4,d5);
////							EntityFXCustom entityFX = new EntityFXCustom(ep.worldObj,d0,d1,d2,(float)d3,(float)d4,(float)d5);
////							entityFX.setResourceLocation(HSLib.MODID, "textures/particle/custom.png");
//							//スプライトをセット
////							Pair<Integer,Integer> indexXY = ParticleTypeWrapper.getParticleIndexFromID(particleNumber);
////							entityFX.setParticleTextureIndex(indexXY.getLeft(),indexXY.getRight());
//							//TextureAtlasSprite tex = ClientHelper.getTextureAtlasSprite(Items.apple, 0);
//							//entityFX.func_180435_a(tex);
//							//							Unsaga.debug(message.getParticleNumber());
//							FMLClientHandler.instance().getClient().effectRenderer.addEffect(particle);
//						}

					}else{
						particleType = EnumParticleTypes.getParticleFromId(particleNumber);
						switch(particleType){
						case WATER_BUBBLE:
							float f4 = 0.25F;

							ep.worldObj.spawnParticle(EnumParticleTypes.WATER_BUBBLE, pos.dx - motion.dx* (double)f4, pos.dy - motion.dy * (double)f4, pos.dz - motion.dz * (double)f4, motion.dx, motion.dy, motion.dz);
							break;
						case REDSTONE:
							ep.worldObj.spawnParticle(particleType, pos.dx, pos.dy + ep.worldObj.rand.nextDouble() * 2.0D, pos.dz, 0.0D,0.0D,0.0D);
							break;
//						case EXPLOSION_NORMAL:
//						case EXPLOSION_HUGE:
//						case EXPLOSION_LARGE:
//							ep.worldObj.spawnParticle(particleType, pos.dx, pos.dy, pos.dz, 1.0D,0.0D,0.0D,new int[0]);
//							break;
						default:
							double dd = ep.getRNG().nextGaussian() * 0.02D;
							ep.worldObj.spawnParticle(particleType, pos.dx + ep.getRNG().nextFloat() , pos.dy + ep.worldObj.rand.nextDouble(), pos.dz + ep.getRNG().nextFloat() ,dd,dd,dd);
							break;

						}




					}


				}
			}

			//			}
			//			if(message.getMode()==Type.TO_POS){
			//				EnumParticleTypes particlestr = EnumParticleTypes.getParticleFromId(message.getParticleNumber());
			//				AbstractClientPlayer ep = (AbstractClientPlayer)player;
			//				for (int i = 0; i < message.getDensity(); ++i)
			//				{
			//					ep.worldObj.spawnParticle(particlestr, (double)message.getPos().getX(), (double)message.getPos().getY() + ep.worldObj.rand.nextDouble() * 2.0D, (double)message.getPos().getZ(), ep.worldObj.rand.nextGaussian()*2, 0.0D, ep.worldObj.rand.nextGaussian()*2);
			//				}
			//
			//
			//			}
			return null;
		}

	}
}
