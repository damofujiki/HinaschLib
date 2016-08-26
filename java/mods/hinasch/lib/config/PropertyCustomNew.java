package mods.hinasch.lib.config;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.google.common.base.Optional;
import com.google.common.base.Preconditions;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;

public class PropertyCustomNew {


	protected Map<Integer,PropertySetting> settingList;
	protected Map<Integer,Property> adaptedList;

	public static class PropertySetting<T, V>{
		public PropertySetting(String name, String comment, String category, T value,Class clazz) {
			this.name = name;
			this.comment = comment;
			this.category = category;
			this.value = value;
			this.collectionClass = Optional.of(clazz);
		}
		public final String name;
		public final String comment;
		public final String category;
		public final T value;
		public Optional<Class> collectionClass;


	}

	public static PropertyCustomNew newInstance(){
		return new PropertyCustomNew();
	}
	protected PropertyCustomNew(){
		this.settingList = new HashMap();
	}

	public <T> PropertyCustomNew add(int number,String name,String comment,T value){

		this.settingList.put(number,new PropertySetting(name,comment,Configuration.CATEGORY_GENERAL,value,Object.class));
		return this;
	}

	public <V> PropertyCustomNew add(int number,String name,String comment,Collection<V> value,Class clazz){

		this.settingList.put(number,new PropertySetting(name,comment,Configuration.CATEGORY_GENERAL,value,clazz));
		return this;
	}

	public <V> void adapt(Configuration config){
		Preconditions.checkNotNull(config,"ConfigFile is null!!");
		if(this.adaptedList==null){
			this.adaptedList = new HashMap();
			for(Integer key:this.settingList.keySet()){
				PropertySetting setting = this.settingList.get(key);
				if(setting.value instanceof String){
					adaptedList.put(key,config.get(setting.category, setting.name, (String)setting.value,setting.comment));
				}
				if(setting.value instanceof Integer){
					adaptedList.put(key,config.get(setting.category, setting.name, (Integer)setting.value,setting.comment));
				}
				if(setting.value instanceof Boolean){
					adaptedList.put(key,config.get(setting.category, setting.name, (Boolean)setting.value,setting.comment));
				}
				if(setting.value instanceof Collection){
					Collection<V> list = (Collection<V>) setting.value;
					if(setting.collectionClass.get()==String.class){
						adaptedList.put(key,config.get(setting.category, setting.name,list.toArray(new String[list.size()]),setting.comment));
					}

					if(setting.collectionClass.get()==Boolean.class){
						boolean[] booleans = new boolean[list.size()];
						int index = 0;
						for(V elm:list){

							booleans[index] = (Boolean) elm;
							index += 1;
						}
						adaptedList.put(key,config.get(setting.category, setting.name,booleans,setting.comment));
					}
					if(setting.collectionClass.get()==Integer.class){
						int[] integers = new int[list.size()];
						int index = 0;
						for(V elm:list){

							integers[index] = (Integer) elm;
							index += 1;
						}
						adaptedList.put(key,config.get(setting.category, setting.name,integers,setting.comment));
					}
					if(setting.collectionClass.get()==Double.class){
						double[] doubles = new double[list.size()];
						int index = 0;
						for(V elm:list){

							doubles[index] = (Double) elm;
							index += 1;
						}
						adaptedList.put(key,config.get(setting.category, setting.name,doubles,setting.comment));
					}
					if(setting.collectionClass.get()==String.class){
						String[] strings = new String[list.size()];
						int index = 0;
						for(V elm:list){

							strings[index] = (String) elm;
							index += 1;
						}
						adaptedList.put(key,config.get(setting.category, setting.name,strings,setting.comment));
					}
				}


			}
		}



	}

	public Map<Integer,Property> getAdaptedProperties(){
		Preconditions.checkNotNull(this.adaptedList, "adapt before getAdaptedProperties.");
		return this.adaptedList;
	}
}
