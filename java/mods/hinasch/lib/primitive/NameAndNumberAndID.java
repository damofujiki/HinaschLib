package mods.hinasch.lib.primitive;

import mods.hinasch.lib.iface.IIntSerializable;

public class NameAndNumberAndID<T> extends NameAndNumber<T> implements Comparable<NameAndNumberAndID<T>>,IIntSerializable{

	int id;
	public int getId() {
		return id;
	}

	public NameAndNumberAndID(T num, String name,int id) {
		super(num, name);
		this.id = id;
		// TODO 自動生成されたコンストラクター・スタブ
	}

	@Override
	public int compareTo(NameAndNumberAndID<T> o) {
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
		return NameAndNumberAndID.class;
	}

	@Override
	public int getMeta() {
		// TODO 自動生成されたメソッド・スタブ
		return this.getId();
	}

}
