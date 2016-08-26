package mods.hinasch.lib.network;

import io.netty.buffer.ByteBuf;
import mods.hinasch.lib.client.IGuiAttribute;
import mods.hinasch.lib.container.ContainerBase;
import net.minecraft.inventory.Container;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;



/**
 * GUIで使うパケットまわりをまとめたもの。
 *
 *
 */
public abstract class PacketGuiButtonBaseNew implements IMessage{

	public int buttonID;
	public int guiid;
	public NBTTagCompound args;

	public PacketGuiButtonBaseNew(){
		this.buttonID = 0;
		this.guiid = 0;
	}

	public PacketGuiButtonBaseNew(IGuiAttribute guiid,int buttonID,NBTTagCompound args){
		this.buttonID = buttonID;
		this.guiid = guiid.getMeta();
		this.args = args;

	}


	public int getButtonID(){
		return this.buttonID;
	}

	public int getGuiID(){
		return this.guiid;
	}

	public NBTTagCompound getArgs(){
		return this.args;
	}


	public void encodeAdditionalArgs(ByteBuf buffer){
		byte[] bytes = PacketUtil.nbtToBytes(args);
		buffer.writeInt(bytes.length);
		buffer.writeBytes(bytes);
	}



	public void decodeAdditionalArgs(ByteBuf buffer){
		int len = buffer.readInt();
		if(len>0){
			ByteBuf bytes = buffer.readBytes(len);
			this.args = PacketUtil.bytesToNBT(bytes);
		}

	}



	/**
	 * GUIIDに沿ってコンテナを返す。
	 * @param openContainer
	 * @param guiID
	 * @return
	 */
	public abstract ContainerBase getContainer(Container openContainer,int guiID);

	@Override
	public void fromBytes(ByteBuf buffer) {
		this.buttonID = buffer.readInt();
		this.guiid = buffer.readInt();

		this.decodeAdditionalArgs(buffer);
//		if(this.hasAdditionalArgs(this.guiid)){
//			this.decodeAdditionalArgs(buffer);
//		}
	}

	@Override
	public void toBytes(ByteBuf buffer) {
		buffer.writeInt(this.buttonID);
		buffer.writeInt(this.guiid);


		if(this.args!=null){
			if(!this.args.hasNoTags()){
				this.encodeAdditionalArgs(buffer);
			}

		}else{
			buffer.writeInt(-1);
		}
//		if(this.hasAdditionalArgs(this.guiid)){
//			this.encodeAdditionalArgs(buffer);
//		}

	}



}
