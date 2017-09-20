package mods.hinasch.lib.primitive;

import mods.hinasch.lib.iface.IIntSerializable;
import net.minecraft.util.ResourceLocation;

public class PropertyElementWithID extends PropertyElementBase implements Comparable<PropertyElementWithID>,IIntSerializable{

	final int id;
	public int getId() {
		return id;
	}

	public PropertyElementWithID(ResourceLocation key, String name,int id) {
		super(key, name);
		this.id = id;
		// TODO 自動生成されたコンストラクター・スタブ
	}

	@Override
	public int compareTo(PropertyElementWithID o) {
		if(this.getId()>o.getId()){
			return 1;
		}
		if(this.getId()==o.getId()){
			return 0;
		}
		return -1;
	}

	@Override
	public Class getParentClass() {
		// TODO 自動生成されたメソッド・スタブ
		return PropertyElementWithID.class;
	}

	@Override
	public int getMeta() {
		// TODO 自動生成されたメソッド・スタブ
		return this.getId();
	}

}
