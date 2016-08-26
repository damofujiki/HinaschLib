package mods.hinasch.lib;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 限度つきのリストみたいなもの？
 * 
 * 
 *
 * @param <T>
 */
public class Pool<T> implements Iterable<T>{

	protected List<T> pool;
	protected final int size;
	protected final T dummy;
	
	public Pool(int size,T dummy){
		this.size = size;
		this.pool = new ArrayList();
		this.dummy = dummy;
		this.pool.add(this.dummy);
	}
	
	public boolean containsSame(T left){
		if(this.pool.isEmpty()){
			return false;
		}
		for(T elm:this.pool){
			if(left!=dummy &&(left==elm || equalElements(left,elm))){
				return true;
			}
			
		}
		return false;
	}
	
	public void add(T elm){
		if(this.pool.size()>this.size){
			return;
		}
		if(!this.containsSame(elm)){
			this.pool.add(elm);
		}
		
	}
	
	public boolean isDummy(T elm){
		return this.dummy==elm;
	}
	public void remove(T rem){
		T remove = null;
		for(T elm:this.pool){
			if(rem!=dummy &&(rem==elm || equalElements(rem,elm))){
				remove = elm;
			}
		}
		if(remove!=null){
			this.pool.remove(remove);
		}
	}
	

	public String toString(){
		return "Pool size:"+this.size;
	}
	public boolean equalElements(T left,T right){
		return false;
	}

	@Override
	public Iterator iterator() {
		// TODO 自動生成されたメソッド・スタブ
		return this.pool.iterator();
	}
}
