package mods.hinasch.lib;

import java.util.Collection;

import com.google.common.base.Function;





public class DebugLog {

	public final String modname;
	public boolean isDebug = true;
	public DebugLog(String name){
		this.modname = name;
		this.isDebug = true;//HSLib.isDebug();
	}

	public DebugLog setDebug(boolean isDebug){
		this.isDebug = isDebug;
		return this;
	}

	public static boolean checkDebugDetectClass(){

		boolean debug = false;
		String className1[] = {
				"hinasch.mods.Debug"
		};
		String cn = null;
		for(int i=0;i<className1.length;i++){
			try{
				//cn = getClassName(className1[i]);
				cn = className1[i];
				System.out.println(cn);
				//cn="realterrainbiomes.mods.RTBiomesCore";
				cn = ""+Class.forName(cn);
				System.out.println(cn+"is ok.");
				switch(i){
				case 0:
					debug = true;
					break;
				}
			}catch(ClassNotFoundException e){

			}
		}

		System.out.println("check end");


		return debug;
	}

	public void log(Object... par1){



		String str = "["+this.modname+"]";
		for(Object obj:par1){
			if(obj!=null){
				Class clas = obj.getClass();
				str += clas.cast(obj).toString()+":";
			}else{
				str += "Null!";
			}

		}
		if(isDebug){
			System.out.println(str);
		}
	}

	public void warn(Object... par1){



		String str = "[WARN/"+this.modname+"]";
		for(Object obj:par1){
			if(obj!=null){
				Class clas = obj.getClass();
				str += clas.cast(obj).toString()+":";
			}else{
				str += "Null!";
			}

		}
		System.out.println(str);

	}

	public void logNotice(Object... par1){



		String str = "[NOTICE/"+this.modname+"]";
		for(Object obj:par1){
			if(obj!=null){
				Class clas = obj.getClass();
				str += clas.cast(obj).toString()+":";
			}else{
				str += "Null!";
			}

		}
		System.out.println(str);

	}

	public  void log(Object par1,Class parent){
		if(isDebug){
			System.out.println("["+this.modname+"/"+parent.getName()+"]"+par1);
		}
	}

	public <T, V> void logList(Class from,Collection<T> collection,Function<T,V> outFunc){

		log("From:"+from.getName(),"{");
		for(T t:collection){
			log(outFunc.apply(t));
		}
		log("}");
	}
}
