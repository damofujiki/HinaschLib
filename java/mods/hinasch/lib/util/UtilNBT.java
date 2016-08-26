package mods.hinasch.lib.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;
import java.util.stream.Stream;

import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.mojang.realmsclient.util.Pair;

import mods.hinasch.lib.iface.INBTWritable;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public class UtilNBT {

	private static enum EnumNBTType {FLOAT,BYTE,INTEGER,STRING,TAG,LONG,DOUBLE,SHORT};
	public static final int NBKEY_TAGLIST = 10;
	public static final int NBTKEY_COMPOUND = 10;

	protected static final String KEY = "FreeState";
	public static final int ERROR = -1;

	public static void initNBTIfNotInit(ItemStack is){

		NBTTagCompound nbt = is.getTagCompound();
		if(nbt == null){
			nbt = new NBTTagCompound();
			is.setTagCompound(nbt);
		}

		return;
	}



	public static Stream<Pair<Integer,NBTTagCompound>> getStream(NBTTagList tags){
		List<Pair<Integer,NBTTagCompound>> list = Lists.newArrayList();
		for(int i=0;i<tags.tagCount();i++){
			list.add(Pair.of(i, tags.getCompoundTagAt(i)));
		}
		return list.stream();
	}
	public static void writeUUID(NBTTagCompound tagCompound,UUID uuid){
		tagCompound.setLong("UUIDMost",uuid.getMostSignificantBits());
		tagCompound.setLong("UUIDLeast", uuid.getLeastSignificantBits());
	}

	public static UUID readUUID(NBTTagCompound tagCompound){
        if (tagCompound.hasKey("PersistentIDMSB") && tagCompound.hasKey("PersistentIDLSB"))
        {
           return new UUID(tagCompound.getLong("PersistentIDMSB"), tagCompound.getLong("PersistentIDLSB"));
        }
        return null;
	}

	public static NBTTagCompound createCompound(){
		return new NBTTagCompound();
	}

	public static NBTTagCompound getNewCompound(ItemStack is){
		initStackNBT(is);
		return is.getTagCompound();
	}
	public static boolean hasTag(ItemStack is){
		Preconditions.checkNotNull(is);
		return is.hasTagCompound();
	}

	public static boolean hasKey(ItemStack is,String key){
		Preconditions.checkNotNull(is);
		if(hasTag(is)){
			NBTTagCompound nbt = is.getTagCompound();
			if(nbt.hasKey(key)){
				return true;
			}
		}
		return false;
	}

	public static void removeTag(ItemStack is,String key){
		Preconditions.checkNotNull(is);
		NBTTagCompound nbt = is.getTagCompound();
		nbt.removeTag(key);
		return;
	}

	public static void clearNBT(ItemStack is){
		Preconditions.checkNotNull(is);
		is.setTagCompound(null);
		return;
	}

	public static void initStackNBT(ItemStack is){
		Preconditions.checkNotNull(is);
		initNBTIfNotInit(is);
	}

	public static void setFreeNestTag(ItemStack is,String key,NBTTagCompound nest){
		initStackNBT(is);
		NBTTagCompound nbt = is.getTagCompound();
		nbt.setTag(key, nest);
		return;
	}
	public static void setFreeByteArray(ItemStack is,String key,byte[] bytes){
		initStackNBT(is);
		NBTTagCompound nbt = is.getTagCompound();
		nbt.setByteArray(key, bytes);
		return;
	}
	public static void setFreeTag(ItemStack is,String key,int val){
		initStackNBT(is);
		NBTTagCompound nbt = is.getTagCompound();
		nbt.setInteger(key, (int)val);
		return;
	}

	public static void setFreeTag(ItemStack is,String key,String val){
		initStackNBT(is);
		NBTTagCompound nbt = is.getTagCompound();
		nbt.setString(key, val);
		return;
	}

	public static void setFreeTag(ItemStack is,String key,float val){
		initStackNBT(is);
		NBTTagCompound nbt = is.getTagCompound();
		nbt.setFloat(key, (float)val);
		return;
	}

	public static void setFreeTag(ItemStack is,String key,boolean val){
		initStackNBT(is);
		NBTTagCompound nbt = is.getTagCompound();
		nbt.setBoolean(key, val);
		return;
	}

	public static boolean tagCheck(NBTTagCompound nbt,String key){
		if(nbt==null){
			return false;
		}
		if(!nbt.hasKey(key)){
			System.out.println("tag not found key:"+key);
			return false;
		}
		return true;
	}
	public static NBTTagCompound readFreeNestTag(ItemStack is,String key){
		Preconditions.checkNotNull(is);
		NBTTagCompound nbt = is.getTagCompound();
		if(!tagCheck(nbt,key))return null;
		return (NBTTagCompound) nbt.getTag(key);
	}

	public static int readFreeTag(ItemStack is,String key){
		Preconditions.checkNotNull(is);
		NBTTagCompound nbt = is.getTagCompound();
		if(!tagCheck(nbt,key))return ERROR;
		int rt = (int)nbt.getInteger(key);
		return rt;
	}
	public static byte[] readFreeBytes(ItemStack is,String key){
		Preconditions.checkNotNull(is);
		NBTTagCompound nbt = is.getTagCompound();
		if(!tagCheck(nbt,key))return null;
		return nbt.getByteArray(key);
	}
	public static float readFreeFloat(ItemStack is,String key){
		Preconditions.checkNotNull(is);
		NBTTagCompound nbt = is.getTagCompound();
		if(!tagCheck(nbt,key))return ERROR;
		float rt = (float)nbt.getFloat(key);
		return rt;
	}

	public static String readFreeStrTag(ItemStack is,String key){
		Preconditions.checkNotNull(is);
		NBTTagCompound nbt = is.getTagCompound();
		if(!tagCheck(nbt,key))return "";
		String rt = nbt.getString(key);
		return rt;
	}

	public static boolean readFreeTagBool(ItemStack is,String key){
		Preconditions.checkNotNull(is);
		NBTTagCompound nbt = is.getTagCompound();
		if(!tagCheck(nbt,key))return false;
		boolean rt = nbt.getBoolean(key);
		return rt;
	}

	public static void setState(ItemStack is,int state){
		setFreeTag(is,KEY,state);
	}

	public static void setState(ItemStack is,boolean state){
		setFreeTag(is,KEY,state);
	}

	public static int readState(ItemStack is){
		int rt = 0;
		rt = readFreeTag(is,KEY);
		return rt;
	}

	public static boolean hasiInitState(ItemStack is){
		NBTTagCompound nbt = is.getTagCompound();
		if(nbt==null){
			initNBTIfNotInit(is);
		}
		return readFreeTag(is,KEY)==ERROR ? false : true;
	}

	public static boolean readStateBool(ItemStack is){
		boolean rt = false;
		rt = readFreeTagBool(is,KEY);
		return rt;
	}

	/**
	 * compoundそのものを格納できるNBT。最後にnbt.setTagで登録する。５１２までネストできるらしい。
	 * @return
	 */
	public static NBTTagList newTagList(){
		return new NBTTagList();
	}

	/**
	 * NBTTagListはCompoundそのものを格納できる。Tag自体を登録することによりネストできる。
	 * @param nbt
	 * @param key
	 * @return
	 */
	public static NBTTagList getTagList(NBTTagCompound nbt,String key){
		NBTTagList tags = nbt.getTagList(key, NBKEY_TAGLIST);

		Preconditions.checkNotNull(tags);
		return tags;
	}

	public static void writeItemStacksToNBTTag(NBTTagList tagList,ItemStack[] iss){

		for(int i=0;i<iss.length;i++){
			if(iss[i]!=null){
				NBTTagCompound compound = new NBTTagCompound();
				compound.setByte("Slot", (byte)i);
				iss[i].writeToNBT(compound);
				tagList.appendTag(compound);
			}
		}
	}

	public static ItemStack[] getItemStacksFromNBT(NBTTagList tagList,int length){

		ItemStack[] iss = new ItemStack[length];

        for (int i = 0; i < tagList.tagCount(); ++i)
        {

            NBTTagCompound nbttagcompound1 = (NBTTagCompound)tagList.getCompoundTagAt(i);
            int j = nbttagcompound1.getByte("Slot") & 255;


            if (j >= 0 && j < iss.length)
            {
            	iss[j] = ItemStack.loadItemStackFromNBT(nbttagcompound1);

            }
        }
        return iss;
	}

	/**
	 * 直列化されたNBTを復元する。ネストと配列は非対応
	 * @param str
	 * @return
	 */
	public static NBTTagCompound restoreNBTFromSerialized(String str){
		List<String> elms = new ArrayList();
		int depth = 0;
		String lin = "";
		for(int i=1;i<str.length()-1;i++){
			char c = str.charAt(i);
			if(c=='['){
				depth += 1;
			}

				if(c==']'){
					depth -= 1;
					if(depth<0){
						depth = 0;
					}
				}

			if(c==',' && depth==0){
				elms.add(lin);


				lin = "";
			}else{
				lin += c;
			}
		}
		return nbtStinrgListToNBT(elms);
	}

	protected static EnumNBTType charToEnum(char ch){
		if(ch=='b')return EnumNBTType.BYTE;
		if(ch=='\"')return EnumNBTType.STRING;
		if(ch=='f')return EnumNBTType.FLOAT;
		if(ch=='d')return EnumNBTType.DOUBLE;
		if(ch=='s')return EnumNBTType.SHORT;
		if(ch=='L')return EnumNBTType.LONG;
		if(ch==']')return EnumNBTType.TAG;
		return EnumNBTType.INTEGER;
	}
	protected static NBTTagCompound nbtStinrgListToNBT(List<String> list){
		Map<String,String> map = new HashMap();
		NBTTagCompound nbt = new NBTTagCompound();
		for(String elm:list){
			if(elm.length()>0 && elm.contains(":")){
				int var1 = elm.indexOf(':');

				map.put(elm.substring(0, var1), elm.substring(var1+1));
			}

			//String[] splitted = elm.split(":");
			//map.put(splitted[0], splitted[1]);
		}

		if(map.isEmpty()){
			return null;
		}
		for(Entry<String,String> entry:map.entrySet()){
			String valuestr = entry.getValue();
			char lastch =valuestr.charAt(valuestr.length()-1);
			String key = entry.getKey();


			switch(charToEnum(lastch)){
			case BYTE:
				byte bt = Byte.parseByte(valuestr.split("b$")[0]);
				nbt.setByte(key, bt);
				break;
			case DOUBLE:
				double db = Double.parseDouble(valuestr.split("d$")[0]);
				nbt.setDouble(key, db);
				break;
			case FLOAT:
				float ft = Float.parseFloat(valuestr.split("f$")[0]);
				nbt.setFloat(key, ft);
				break;
			case INTEGER:

					int it = Integer.parseInt(valuestr);
					nbt.setInteger(key, it);

				break;
			case LONG:
				long lg = Long.parseLong(valuestr.split("L$")[0]);
				nbt.setLong(key, lg);
				break;
			case SHORT:
				short sh = Short.parseShort(valuestr.split("s$")[0]);
				nbt.setLong(key, sh);
				break;
			case STRING:
				if(valuestr.length()<=2){
					nbt.setString(key, "");
				}else{
					String st = valuestr.substring(1,valuestr.length()-2);
					nbt.setString(key, valuestr);
				}
				break;
			case TAG:
				break;
			default:
				break;

			}

		}


		return nbt;
	}

	public static void writeListToNBT(Collection<? extends INBTWritable> list,NBTTagCompound nbt,String key){
		NBTTagList tag = new NBTTagList();
		for(INBTWritable writable:list){
			NBTTagCompound child = UtilNBT.createCompound();
			writable.writeToNBT(child);
			tag.appendTag(child);
		}
		nbt.setTag(key,tag);
	}

	public static <T> List<T> readListFromNBT(NBTTagCompound comp,String key,RestoreFunc<T> nbtToObj){
		NBTTagList tag = comp.getTagList(key, NBKEY_TAGLIST);
		List<T> rt = new ArrayList();
		for(int i=0;i<tag.tagCount();i++){
			NBTTagCompound child = tag.getCompoundTagAt(i);
			T elm = nbtToObj.apply(child);
			rt.add(elm);
		}
		return rt;
	}

//	public static <T> asStreamNBTTags(NBTTagList tag,Function<>){
//
//	}
	public static interface RestoreFunc<T> extends Function<NBTTagCompound,T>{

	}
}
