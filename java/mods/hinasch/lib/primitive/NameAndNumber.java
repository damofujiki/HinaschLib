package mods.hinasch.lib.primitive;

import net.minecraft.util.ResourceLocation;

public abstract class NameAndNumber<T>{

	public final String name;
	public final T key;

	public NameAndNumber(T num,String name){
		this.name = name;
		this.key = num;

	}

	public String getName(){
		return this.name;
	}

	public T getKey(){
		return this.key;
	}

	@Override
	public int hashCode() {
		if(this.key instanceof Integer){
			return (1+(Integer)this.key) * 31;
		}
		if(this.key instanceof String){
			return ((String)this.key).hashCode();
		}
		if(this.key instanceof ResourceLocation){
			return ((ResourceLocation)this.key).hashCode();
		}

		return super.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if(obj==null)return false;
		if(obj instanceof NameAndNumber){
			if(this.getParentClass()==obj.getClass() || this.getParentClass().isInstance(obj)){

					return obj.hashCode() == this.hashCode();

			}

		}
		return super.equals(obj);
	}



	public abstract Class getParentClass();

	public String serialize(){
		return String.valueOf(getKey());
	}

	@Override
	public String toString(){
		return this.getName();
	}


//	@Override
//	public String getVersion() {
//		// TODO 自動生成されたメソッド・スタブ
//		return "none";
//	}

//	@Override
//	public int getSerialized() {
//		if(this.number instanceof Integer){
//			return (Integer) this.number;
//		}
//		return 0;
//	}
}
