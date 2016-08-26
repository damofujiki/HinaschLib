package mods.hinasch.lib.network;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.google.common.base.Joiner;
import com.google.common.base.Splitter;

import io.netty.buffer.ByteBuf;
import joptsimple.internal.Strings;
import mods.hinasch.lib.core.HSLib;
import mods.hinasch.lib.util.UtilNBT;
import mods.hinasch.lib.world.XYZPos;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;

public class PacketUtil {

	public static XYZPos bufferToXYZPos(ByteBuf buf){
		int x = buf.readInt();
		int y = buf.readInt();
		int z = buf.readInt();
		return new XYZPos(x,y,z);

	}

	public static void XYZPosToPacket(ByteBuf buf,XYZPos xyz){
		buf.writeInt(xyz.getX());
		buf.writeInt(xyz.getY());
		buf.writeInt(xyz.getZ());
	}


	public static void writeString(ByteArrayOutputStream stream,String str){

		try {
			byte[] bytes = str.getBytes("UTF-8");
			HSLib.logger.trace("write string length:", bytes.length);
			stream.write(bytes.length);
			stream.write(bytes);
		} catch (IOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
	}

	public static byte[] tagListToBytes(NBTTagList tagList){
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		stream.write(tagList.tagCount());
		for(int i=0;i<tagList.tagCount();i++){
			NBTTagCompound nbt = tagList.getCompoundTagAt(i);
			byte[] bytes = nbtToBytes(nbt);

			try {
				stream.write(i);
				stream.write(bytes.length);
				stream.write(bytes);
			} catch (IOException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}
		}

		return stream.toByteArray();
	}

	public static NBTTagList readTagListFromBytes(ByteBuf buf){
		NBTTagList tagList = UtilNBT.newTagList();
		int count = buf.readInt();
		for(int i=0;i<count;i++){
			int index = buf.readInt();
			int length = buf.readInt();
			ByteBuf bytes = buf.readBytes(length);
			NBTTagCompound comp = bytesToNBT(bytes);
			tagList.appendTag(comp);
		}
		return tagList;
	}


	public static String bytesToCSV(byte[] bytes){
		if(bytes!=null && bytes.length>0){
			Joiner joiner = Joiner.on(",");
			Byte[] byteobj = new Byte[bytes.length];
			for(int i=0;i<bytes.length;i++){
				byteobj[i] = Byte.valueOf(bytes[i]);
			}
			return joiner.join(byteobj);
		}

		return null;

	}

	public static byte[] CSVToBytes(String csv){
		if(csv!=null && !csv.isEmpty()){
			Splitter splitter = Splitter.on(",");
			List<String> strings = splitter.splitToList(csv);
			Byte[] byteobj = new Byte[strings.size()];
			for(int i=0;i<strings.size();i++){
				byteobj[i] = Byte.valueOf(strings.get(i));
			}
			byte[] bytes = new byte[byteobj.length];
			for(int i=0;i<bytes.length;i++){
				bytes[i] = byteobj[i].byteValue();
			}
			return bytes;
		}
		return null;

	}


	/**
	 * ネストに非対応なのでリストとかは{@link #tagListToBytes(NBTTagList)}を使う
	 * @param comp
	 * @return
	 */
	public static byte[] nbtToBytes(NBTTagCompound comp){
		ByteArrayOutputStream stream = new ByteArrayOutputStream();

		Set<String> kies = comp.getKeySet();


		int nbtLen = kies.size();
		stream.write(nbtLen);
		HSLib.logger.trace("NBT Serialize.. nbt長", nbtLen);
		for(String key:kies){
//			HSLib.logger.trace(key+"をシリアライズします");
			int type = comp.getTagId(key);
			writeString(stream,key);
			stream.write(type);
			try {

				String str = Strings.EMPTY;
				switch(type){
				case 1:
					stream.write(comp.getByte(key));
					break;
				case 2:
					stream.write(comp.getShort(key));
					break;
				case 3:
					str = String.valueOf(comp.getInteger(key));
					writeString(stream,str);
					break;
				case 4:
					str = String.valueOf(comp.getLong(key));
					writeString(stream,str);
					break;
				case 5:
					str = String.valueOf(comp.getFloat(key));
					writeString(stream,str);
					break;
				case 6:
					str = String.valueOf(comp.getDouble(key));
					writeString(stream,str);
					break;
				case 7:
					stream.write(comp.getByteArray(key).length);
					stream.write(comp.getByteArray(key));
					break;
				case 8:
					writeString(stream,comp.getString(key));
					break;
				case 9:

					NBTTagList tags = comp.getTagList(key, UtilNBT.NBTKEY_COMPOUND);
//					HSLib.logger.trace("TAG length", tags.tagCount());
					stream.write(tags.tagCount());
					for(int i=0;i<tags.tagCount();i++){
						NBTTagCompound tag = tags.getCompoundTagAt(i);
						byte[] bytes = PacketUtil.nbtToBytes(tag);
						writeString(stream,String.valueOf(bytes.length));
						stream.write(bytes);
					}
					break;
				case 10:
					byte[] bytes = PacketUtil.nbtToBytes((NBTTagCompound) comp.getTag(key));
					writeString(stream,String.valueOf(bytes.length));
					stream.write(bytes);
					break;
				}



			} catch (IOException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}


		}
		try {
			stream.close();
		} catch (IOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		if(stream.size()>0){

			byte[] bytes = stream.toByteArray();
			return bytes;
		}





		return null;
	}


	/**
	 * 今のところ非ネスト、リスト非使用のものだけ
	 * @param buff
	 * @param comp
	 */
	public static byte[] nbtToBytesOld(NBTTagCompound comp){
		Set<String> kies = comp.getKeySet();
		Joiner joiner = Joiner.on(",");
		List<String> list = new ArrayList();





		for(String key:kies){

			int type = comp.getTagId(key);
			switch(type){
			case 1:
				list.add(Joiner.on(":").join(key,type,comp.getByte(key)));
				break;
			case 2:
				list.add(Joiner.on(":").join(key,type,comp.getShort(key)));
				break;
			case 3:
				list.add(Joiner.on(":").join(key,type,comp.getInteger(key)));
				break;
			case 4:
				list.add(Joiner.on(":").join(key,type,comp.getLong(key)));
				break;
			case 5:
				list.add(Joiner.on(":").join(key,type,comp.getFloat(key)));
				break;
			case 6:
				list.add(Joiner.on(":").join(key,type,comp.getDouble(key)));
				break;
			case 7:
				list.add(Joiner.on(":").join(key,type,comp.getByteArray(key)));
				break;
			case 8:
				list.add(Joiner.on(":").join(key,type,comp.getString(key)));
				break;
			case 9:

			}

		}
		String joined = joiner.join(list);

		byte[] bytes;
		try {
			bytes = joined.getBytes("UTF-8");
//			int length = bytes.length;

			return bytes;

		} catch (UnsupportedEncodingException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}

		return null;
	}
	/** バイトバッファーからUTF-8で文字を読み込む。最初に文字長intを読み込むので
	 * バッファの位置はそこに合わせておくこと
	 * bytearrayoutputstreamをarrayにして書き込んだものはすべてbyteで読む必要がある?ので
	 * そういうときはreadLengthAsByteをonにする
	 * */
	public static String readStringFromByteBuf(ByteBuf buf,boolean readLengthAsByte){

		int length = readLengthAsByte ? buf.readByte() : buf.readInt();
		HSLib.logger.trace("str length(Read)", length);
		ByteBuf bytes = buf.readBytes(length);

		try {
			String str = new String(bytes.array(),"UTF-8");
			return str;
		} catch (UnsupportedEncodingException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		return null;
	}

	public static String readStringFromByteBuf(ByteBuf buf){
		return readStringFromByteBuf(buf,false);
	}
	public static void writeByteArrayWithLength(ByteBuf  buf,byte[] bytes){
		buf.writeInt(bytes.length);
		if(bytes.length>0){
			buf.writeBytes(bytes);
		}

	}


	/** エンコードUTF－８でバイトバッファーに書きこむ（文字長intとbytearrayのセットで） */
	public static void writeStringtoByteBuf(ByteBuf buf,String str){
		if(str!=null && !str.equals("")){
			try {
				byte[] bytes = str.getBytes("UTF-8");
				buf.writeInt(bytes.length);
				buf.writeBytes(bytes);
			} catch (UnsupportedEncodingException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}
		}

	}
	public static NBTTagCompound bytesToNBT(ByteBuf buf){
		NBTTagCompound comp = new NBTTagCompound();
		int nbtLen = buf.readByte();

		HSLib.logger.trace("nbt長", nbtLen);
		for(int i=0;i<nbtLen;i++){
			String key = readStringFromByteBuf(buf,true);
//			HSLib.logger.trace(key+"をデシリアライズします");
			int type = buf.readByte();
			switch(type){
			case 1:
				comp.setByte(key, (byte)buf.readByte());
				break;
			case 2:
				comp.setShort(key, (short)buf.readByte());
				break;
			case 3:
				comp.setInteger(key, Integer.valueOf(readStringFromByteBuf(buf,true)));
				break;
			case 4:
				comp.setLong(key, Long.valueOf(readStringFromByteBuf(buf,true)));
				break;
			case 5:
				comp.setFloat(key, Float.valueOf(readStringFromByteBuf(buf,true)));
				break;
			case 6:
				comp.setDouble(key, Double.valueOf(readStringFromByteBuf(buf,true)));
				break;
			case 7:
				int length = buf.readByte();
				comp.setByteArray(key, buf.readBytes(length).array());
				break;
			case 8:
				comp.setString(key, readStringFromByteBuf(buf,true));

				break;
			case 9:
				int count = buf.readByte();
				NBTTagList tagList = UtilNBT.newTagList();
				for(int j=0;j<count;j++){
					length = Integer.valueOf(readStringFromByteBuf(buf,true));
					ByteBuf bytes = buf.readBytes(length);
					NBTTagCompound comp2 = PacketUtil.bytesToNBT(bytes);
					tagList.appendTag(comp2);
				}
				comp.setTag(key, tagList);
				break;

			case 10:
				int bytelen = Integer.valueOf(readStringFromByteBuf(buf,true));
				ByteBuf buf2 = buf.readBytes(bytelen);
				NBTTagCompound comp2 = PacketUtil.bytesToNBT(buf2);
				comp.setTag(key, comp2);
				break;
			}
		}

		return comp;

	}
	@Deprecated
	public static NBTTagCompound bytesToNBTOld(byte[] bytes){
		NBTTagCompound comp = new NBTTagCompound();


		try {
			String str = new String(bytes,"UTF-8");
			for(String elm:Splitter.on(",").split(str)){
				String[] tagStr = elm.split(":");
				String key = tagStr[0];
				int type = Integer.valueOf(tagStr[1]);
				switch(type){
				case 1:
					comp.setByte(key, Byte.valueOf(tagStr[2]));
					break;
				case 2:
					comp.setShort(key, Short.valueOf(tagStr[2]));
					break;
				case 3:
					comp.setInteger(key, Integer.valueOf(tagStr[2]));
					break;
				case 4:
					comp.setLong(key, Long.valueOf(tagStr[2]));
					break;
				case 5:
					comp.setFloat(key, Float.valueOf(tagStr[2]));
					break;
				case 6:
					comp.setDouble(key, Double.valueOf(tagStr[2]));
					break;
				case 7:
					comp.setByteArray(key, tagStr[2].getBytes());
					break;
				case 8:
					comp.setString(key, tagStr[2]);
					break;
				}
			}
			return comp;
		} catch (UnsupportedEncodingException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}

		return comp;

	}
	public static TargetPoint getTargetPointNear(Entity entity){
		TargetPoint target = new TargetPoint(entity.worldObj.provider.getDimension(),
				entity.posX, entity.posY, entity.posZ,100D);
		return target;
	}

	public static TargetPoint getTargetPointNear(XYZPos pos,World world){
		TargetPoint target = new TargetPoint(world.provider.getDimension(),
				pos.dx, pos.dy, pos.dz,100D);
		return target;
	}
}
