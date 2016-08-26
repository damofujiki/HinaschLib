package mods.hinasch.lib;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import com.google.common.collect.Lists;

@Deprecated
public class StringCut {
	public static ArrayList strapOffInt(String par1){
		String[] splited = par1.split(",");
		ArrayList<Integer> arraylist = new ArrayList();
		for(String str:splited){
			arraylist.add(Integer.parseInt(str));
		}
		return arraylist;
	}

	public static ArrayList strapOffHexInt(String par1){
		String[] splited = par1.split(",");
		ArrayList<Integer> arraylist = new ArrayList();
		for(String str:splited){
			arraylist.add(Integer.decode(str));
		}
		return arraylist;
	}

	public static ArrayList strapOffStr(String par1){
		String[] splited = par1.split(",");
		ArrayList<String> arraylist = Lists.newArrayList(splited);
		return arraylist;
	}



	public static ArrayList strapOffShort(String par1){
		String[] splited = par1.split(",");
		ArrayList<Short> arraylist = new ArrayList();
		for(String str:splited){
			arraylist.add(Short.parseShort(str));
		}
		return arraylist;
	}
	

	public static ArrayList strapOffByte(String par1){
		String[] splited = par1.split(",");
		ArrayList<Byte> arraylist = new ArrayList();
		for(String str:splited){
			arraylist.add(Byte.valueOf(str));
		}
		return arraylist;
	}

	public static ArrayList strapOffBoolean(String par1){
		String[] splited = par1.split(",");
		ArrayList<Boolean> arraylist = new ArrayList();
		for(String str:splited){
			arraylist.add(Boolean.parseBoolean(str));
		}
		return arraylist;
	}

	public static List<Integer> strListToIntList(Collection<String> input){
		List<Integer> intlist = new ArrayList();
		for(Iterator<String> ite=input.iterator();ite.hasNext();){
			intlist.add(Integer.parseInt(ite.next()));
		}
		return intlist;
	}
	

}
