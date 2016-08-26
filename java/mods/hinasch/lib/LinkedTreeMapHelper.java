package mods.hinasch.lib;

import com.google.gson.internal.LinkedTreeMap;
/**
 * 	Jsonファイル読み込み用に作ったけどあんま使わなさそう
 *
 *
 */
public class LinkedTreeMapHelper {


	protected LinkedTreeMap map;
	public LinkedTreeMapHelper(LinkedTreeMap map){
		this.map = map;
	}
	
	public int getInteger(Object key){
		double d = (Double) this.map.get(key);
		return (int)d;
	}
	
	public static LinkedTreeMapHelper getNewHelper(Object obj){
		return new LinkedTreeMapHelper((LinkedTreeMap)obj);
	}
}
