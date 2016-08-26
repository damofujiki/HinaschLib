package mods.hinasch.lib.network;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import mods.hinasch.lib.container.ContainerBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.Container;

public abstract class PacketGuiButtonBase extends AbstractPacket{

	public int buttonID;
	public int guiid;
	public Object[] args;

	public PacketGuiButtonBase(){
		this.buttonID = 0;
		this.guiid = 0;
	}

	public PacketGuiButtonBase(int guiid,int buttonID,Object... args){
		this.buttonID = buttonID;
		this.guiid = guiid;
		if(args.length>0){
			this.args = new Object[args.length];
			for(int i=0;i<args.length;i++){
				this.args[i] = args[i];
			}
		}

	}
	@Override
	public void encodeInto(ChannelHandlerContext ctx, ByteBuf buffer) {
		buffer.writeInt(this.buttonID);
		buffer.writeInt(this.guiid);
		if(this.getAdditionalArgs(this.guiid)!=null){
			this.encodeAdditionalArgs(buffer);
		}


	}

	public void encodeAdditionalArgs(ByteBuf buffer){
		for(int i=0;i<args.length;i++){
			if(this.args[i] instanceof Integer){
				buffer.writeInt((Integer)args[i]);
			}
			if(this.args[i] instanceof Byte){
				buffer.writeByte((Byte)args[i]);
			}
			if(this.args[i] instanceof Float){
				buffer.writeFloat((Float)args[i]);
			}
			if(this.args[i] instanceof Short){
				buffer.writeShort((Short)args[i]);
			}
		}
	}
	@Override
	public void decodeInto(ChannelHandlerContext ctx, ByteBuf buffer) {
		this.buttonID = buffer.readInt();
		this.guiid = buffer.readInt();
		if(this.getAdditionalArgs(this.guiid)!=null){
			this.decodeAdditionalArgs(buffer);
		}

	}

	public Class[] getAdditionalArgs(int guiID){
		return null;
	}
	public void decodeAdditionalArgs(ByteBuf buffer){
		Class[] clazzes = this.getAdditionalArgs(this.guiid);
		this.args = new Object[clazzes.length];

		for(int i=0;i<clazzes.length;i++){
			if(clazzes[i]==Integer.class){
				this.args[i] = buffer.readInt();
			}
			if(clazzes[i]==Byte.class){
				this.args[i] = buffer.readByte();
			}
			if(clazzes[i]==Float.class){
				this.args[i] = buffer.readFloat();
			}
			if(clazzes[i]==Short.class){
				this.args[i] = buffer.readShort();
			}
		}
	}

	@Override
	public void handleClientSide(EntityPlayer player) {
		// TODO 自動生成されたメソッド・スタブ

	}

	//ここでスイッチ文とかで分岐
	@Override
	public void handleServerSide(EntityPlayer player) {

		Container epContainer = ((EntityPlayerMP)player).openContainer;
		ContainerBase container = this.getContainer(epContainer, this.guiid);
		if(container!=null){
//			container.readPacketData(this.buttonID,this.args);
			container.onPacketData();
		}
	}


	public abstract ContainerBase getContainer(Container openContainer,int guiID);


}
