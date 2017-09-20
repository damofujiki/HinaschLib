package mods.hinasch.lib.primitive;

import jline.internal.Preconditions;
import net.minecraft.util.ResourceLocation;

public abstract class PropertyElementBase implements IPropertyElement{

	public final String name;
	public final ResourceLocation key;

	public PropertyElementBase(ResourceLocation key,String name){
		this.name = Preconditions.checkNotNull(name);
		this.key = Preconditions.checkNotNull(key);

	}

	public String getName(){
		return this.name;
	}

	public ResourceLocation getKey(){
		return this.key;
	}

	@Override
	public int hashCode() {
		if(this.key instanceof ResourceLocation){
			return ((ResourceLocation)this.key).hashCode();
		}

		return super.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if(obj==null)return false;
		if(obj instanceof PropertyElementBase){
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
