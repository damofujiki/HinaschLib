package mods.hinasch.lib.config;

import java.util.Map;

import com.google.common.collect.Maps;

import mods.hinasch.lib.core.HSLib;

@Deprecated
public abstract class SplittedStringWrapper<T,V> {

	String[] element;
	String identifier;
	int requireLength;
	String splitter;
	Map<T,V> map;

	public SplittedStringWrapper(String identifier,String[] strs,int requireLength,String splitter){
		this.element = strs;
		this.identifier = identifier;
		this.requireLength = requireLength;
		this.splitter = splitter;
		this.map = Maps.newHashMap();
	}



	public SplittedStringWrapper parse(){
		for(String elm:this.element){
			String[] splitted = elm.split(splitter);

//			HSLib.logger.trace("splitter", splitted);
			if(splitted.length>=requireLength){

				try {
					T t = this.parseKey(splitted);
					V v = this.parseValue(splitted);
					this.map.put(t, v);
				} catch (Exception e) {
					this.printError("");
					e.printStackTrace();
				}


			}else{
				this.printError("strange length");
			}

		}

		return this;
	}

	public abstract T parseKey(String[] str) throws Exception;
	public abstract V parseValue(String[] str) throws Exception;

	public Map<T,V> get(){
		return this.map;
	}

	public void printError(String message){
		HSLib.logger.get().info("[config "+this.identifier+" parse error]"+message);
	}
	public abstract static class SplittedStringWrapperMap<A, B, C> extends SplittedStringWrapper<A,Map<B,C>>{

		public SplittedStringWrapperMap(String identifier, String[] strs, int requireLength, String splitter) {
			super(identifier, strs, requireLength, splitter);
			// TODO 自動生成されたコンストラクター・スタブ
		}
		public SplittedStringWrapper parse(){
			for(String elm:this.element){
				String[] splitted = elm.split(splitter);
				if(splitted.length>=requireLength){

					try {
						A t = this.parseKey(splitted);

						if(!this.map.containsKey(t)){
							this.map.put(t, Maps.<B,C>newHashMap());
						}
						Map<B,C> innerMap = this.map.get(t);

						B b = this.parseKeySecondMap(splitted);
						C c = this.parseValueSecondMap(splitted);
						innerMap.put(b, c);
					} catch (Exception e) {
						this.printError("");
						e.printStackTrace();
					}


				}else{
					this.printError("");
				}

			}
			return this;
		}


		public abstract B parseKeySecondMap(String[] str) throws Exception;
		public abstract C parseValueSecondMap(String[] str) throws Exception;

		@Override
		public Map<B, C> parseValue(String[] str) throws Exception {
			// TODO 自動生成されたメソッド・スタブ
			return null;
		}

	}
}
