package mods.hinasch.lib.network;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import io.netty.buffer.ByteBuf;
import mods.hinasch.lib.client.IGuiAttribute;
import mods.hinasch.lib.core.HSLib;
import mods.hinasch.lib.iface.IModBase;
import mods.hinasch.lib.util.UtilNBT;
import mods.hinasch.lib.world.XYZPos;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.internal.FMLNetworkHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;



public class PacketOpenGui implements IMessage{


	protected int modid;
	protected byte guinum;
	protected XYZPos pos;
//	protected int entityid;
	protected NBTTagCompound comp;
	public NBTTagCompound getComp() {
		return comp;
	}

	public PacketOpenGui(){

	}

	public PacketOpenGui(int numberGui,XYZPos pos,NBTTagCompound... args){

		this.guinum = (byte)numberGui;
		if(args.length>0){
			this.comp = args[0];
		}else{
			this.comp = UtilNBT.createCompound();
		}

		pos.writeToNBT(comp);

	}

//	public PacketOpenGui(int numberGui,XYZPos pos){
//		this(numberGui);
//		this.pos = pos;
//	}
//
////	public PacketOpenGui(int numberGui,TileEntityUnsagaChest chest){
////		this(numberGui);
////		this.entityid = -1;
////		this.pos = XYZPos.tileEntityPosToXYZ(chest);
////	}
////
////	public PacketOpenGui(int numberGui,EntityUnsagaChest chest){
////		this(numberGui);
////		this.entityid = chest.getEntityId();
////		this.pos = XYZPos.entityPosToXYZ(chest);
////	}
//	public PacketOpenGui(int numberGui,EntityVillager entity){
//		this(numberGui);
//		this.entityid = entity.getEntityId();
//	}
	public static PacketOpenGui create(int modguiid,IGuiAttribute gui,XYZPos pos,NBTTagCompound... args){
		PacketOpenGui p = new PacketOpenGui(gui.getMeta(),pos,args);
		p.setModID(modguiid);
		return p;
	}

	public static PacketOpenGui create(IGuiAttribute gui,XYZPos pos,NBTTagCompound... args){
		return new PacketOpenGui(gui.getMeta(),pos,args);
	}
//	public int getEntityID(){
//		return this.entityid;
//	}

	public XYZPos getPos(){
		return this.pos;
	}

	public byte getGuiNumber(){
		return this.guinum;
	}

	public int getModID(){
		return this.modid;
	}


	protected void setModID(int id){
		this.modid = id;
	}

	@Override
	public void fromBytes(ByteBuf buffer) {
		this.guinum = buffer.readByte();
		this.modid = buffer.readInt();
		int length = buffer.readInt();
		if(length>0){
			ByteBuf bytes = buffer.readBytes(length);
			this.comp = PacketUtil.bytesToNBT(bytes);
		}
//		switch(this.guinum){
//		case guiNumber.CHEST:
//			this.entityid = buffer.readInt();
//			this.pos = XYZPos.readFromBuffer(buffer);
////			this.pos = new XYZPos(0,0,0);
////			this.pos.x = buffer.readInt();
////			this.pos.y = buffer.readInt();
////			this.pos.z = buffer.readInt();
//			break;
//		case guiNumber.BARTERING:
//		case guiNumber.SMITH:
//			this.entityid = buffer.readInt();
//			break;
//		}
	}

	@Override
	public void toBytes(ByteBuf buffer) {
		buffer.writeByte(this.guinum);
		buffer.writeInt(this.modid);
		if(this.comp!=null){

			byte[] bytes = PacketUtil.nbtToBytes(comp);
			buffer.writeInt(bytes.length);
			buffer.writeBytes(bytes);
		}else{
			buffer.writeInt(-1);
		}
//		switch(this.guinum){
//		case guiNumber.CHEST:
//			buffer.writeInt(entityid);
//			this.pos.writeToBuffer(buffer);
////			buffer.writeInt(pos.x);
////			buffer.writeInt(pos.y);
////			buffer.writeInt(pos.z);
//			break;
//		case guiNumber.SMITH:
//			buffer.writeInt(this.entityid);
//			break;
//		case guiNumber.BARTERING:
//			buffer.writeInt(this.entityid);
//			break;
//		}

	}

	public static class Handler implements IMessageHandler<PacketOpenGui,IMessage>{

		@Override
		public IMessage onMessage(final PacketOpenGui message, MessageContext ctx) {
			if(ctx.side.isServer()){
				final EntityPlayerMP ep = ctx.getServerHandler().playerEntity;

//				EntityVillager villager = null;
//				XYZPos p = null;
//				switch(UnsagaMod.GuiType.fromMeta(message.getGuiNumber())){
				XYZPos p = XYZPos.readFromNBT(message.comp);





				IModBase modbase = HSLib.core().guis.get(message.getModID());
				Class<? extends IGuiAttribute> clazz = modbase.getGuiClass();
				Method m;
				try {
					m = clazz.getMethod("fromMeta", new Class[]{int.class});
					Object obj = m.invoke(null, new Object[]{message.getGuiNumber()});
					IGuiAttribute at = (IGuiAttribute) obj;
					at.onGuiOpen(message, ctx, ep);
					FMLNetworkHandler.openGui(ep, modbase.getModInstance(), at.getMeta(), ep.worldObj,p.getX(),p.getY(),p.getZ());
				} catch (NoSuchMethodException e) {
					// TODO 自動生成された catch ブロック
					e.printStackTrace();
				} catch (SecurityException e) {
					// TODO 自動生成された catch ブロック
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO 自動生成された catch ブロック
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					// TODO 自動生成された catch ブロック
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					// TODO 自動生成された catch ブロック
					e.printStackTrace();
				}




//				UnsagaGui.Type.fromMeta(message.getGuiNumber()).onGuiOpen(message,ctx,ep);


//				case EQUIPMENT:
//					FMLNetworkHandler.openGui(ep, UnsagaMod.instance, GuiType.EQUIPMENT.getMeta(), ep.worldObj, (int)ep.posX, (int)ep.posY, (int)ep.posZ);
//					break;
//				default:
//					break;

//				switch(message.getGuiNumber()){
//				case guiNumber.EQUIP:
//
//					FMLNetworkHandler.openGui((EntityPlayer) ep, Unsaga.instance, Unsaga.guiNumber.EQUIP, ep.worldObj, (int)ep.posX, (int)ep.posY, (int)ep.posZ);
//					break;
//				case guiNumber.SMITH:
//					Entity entity  = ep.worldObj.getEntityByID(message.getEntityID());
//					Unsaga.debug(villager);
//					if(entity instanceof EntityVillager){
//						 villager = (EntityVillager)entity;
//						ExtendedPlayerData.getData(ep).setMerchant(villager);
//						XYZPos pos = XYZPos.entityPosToXYZ(ep);
//
//						HSLibs.openGui((EntityPlayer) ep, Unsaga.instance, Unsaga.guiNumber.SMITH, ep.worldObj,pos);
//					}else{
////						XYZPos pos = message.getPos();
////						//Unsaga.debug(ep.worldObj.getBlock(pos.x, pos.y, pos.z));
////						HSLibs.openGui((EntityPlayer) ep, Unsaga.instance, Unsaga.guiNumber.SMITH, ep.worldObj,pos);
//					}
//					break;
//				case guiNumber.BARTERING:
//					villager = (EntityVillager) ep.worldObj.getEntityByID(message.getEntityID());
//					if(villager!=null){
//						ExtendedPlayerData.getData(ep).setMerchant(villager);
//						XYZPos pos = XYZPos.entityPosToXYZ(ep);
//						HSLibs.openGui((EntityPlayer) ep, Unsaga.instance, Unsaga.guiNumber.BARTERING, ep.worldObj,pos);
//					}
//					break;
//				case guiNumber.BLENDER:
//					FMLNetworkHandler.openGui((EntityPlayer) ep, Unsaga.instance, Unsaga.guiNumber.BLENDER, ep.worldObj, (int)ep.posX, (int)ep.posY, (int)ep.posZ);
//					break;
//				case guiNumber.CHEST:
//					int entityid = message.getEntityID();
//					if(entityid==-1){
//						XYZPos pos = message.getPos();
//						TileEntity te = ep.worldObj.getTileEntity(pos);
//						if(te instanceof TileEntityUnsagaChest){
//							ExtendedPlayerData.getData(ep).setInteractingChest((TileEntityUnsagaChest) te);
//							HSLibs.openGui((EntityPlayer) ep, Unsaga.instance, Unsaga.guiNumber.CHEST, ep.worldObj, pos);
//						}
//					}else{
//						Entity en = ep.worldObj.getEntityByID(message.getEntityID());
//
//						if(en instanceof EntityUnsagaChest){
//							XYZPos pos = XYZPos.entityPosToXYZ(en);
//							ExtendedPlayerData.getData(ep).setInteractingChest((EntityUnsagaChest) en);
//							HSLibs.openGui((EntityPlayer) ep, Unsaga.instance, Unsaga.guiNumber.CHEST, ep.worldObj, pos);
//						}
//					}
//
//
//					break;
//				case guiNumber.CARRIER:
//					p = XYZPos.entityPosToXYZ(ep);
//					HSLibs.openGui(ep, Unsaga.instance, guiNumber.CARRIER, ep.worldObj, p);
//					break;
//				case guiNumber.SKILLPANEL:
//					break;
//				case guiNumber.TABLET:
//					p = XYZPos.entityPosToXYZ(ep);
//					HSLibs.openGui(ep, Unsaga.instance, guiNumber.TABLET, ep.worldObj, p);
//					break;
//				}
			}
			return null;
		}

	}
}
