package mods.hinasch.lib.primitive;

import com.mojang.realmsclient.util.Pair;

public class XY extends Pair<Integer,Integer>{

	public XY(Integer first, Integer second) {
		super(first, second);
		// TODO 自動生成されたコンストラクター・スタブ
	}

	public int getX(){
		return this.first();
	}

	public int getY(){
		return this.second();
	}

	@Override
	public String toString(){
		return "X:"+this.getX()+" Y:"+this.getY();
	}

	public static XY of(int x,int y){
		return new XY(x,y);
	}
}
