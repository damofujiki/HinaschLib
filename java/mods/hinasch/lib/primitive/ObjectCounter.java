package mods.hinasch.lib.primitive;

import java.util.Iterator;
import java.util.Map;

import com.google.common.collect.Maps;

import mods.hinasch.lib.iface.BiConsumer;
import mods.hinasch.lib.iface.Consumer;
import mods.hinasch.lib.world.ScanHelper;

/** 任意のオブジェクトの数を数えるクラス。
 * オブジェクトの同定はmap.containsKey(obj）に依存
 * staticにstream関連をちょっと用意
 * */
public class ObjectCounter<T> implements Iterable<ObjectCounter<T>>,Iterator<ObjectCounter<T>>{

	public Map<T,Integer> map;

	int ite_counter = 0;

	public ObjectCounter(){
		map = Maps.newHashMap();
	}

	public void add(T obj){
		if(map.containsKey(obj)){
			int cnt = map.get(obj);
			map.put(obj, cnt+1);
		}else{
			map.put(obj, 1);
		}
	}

	public int get(T obj){
		return map.get(obj)==null ? 0 : map.get(obj);
	}


	public static <K> ObjectCounter<K> fromScanner(ScanHelper scan,ScannerCounterConsumer<K>  biconsumer){
		ObjectCounter counter = new ObjectCounter();
		scan.asStream(biconsumer,counter);
		return counter;
	}

	public static <T> ObjectCounter<T> of(Consumer<ObjectCounter<T>> consumer){
		ObjectCounter counter = new ObjectCounter();
		consumer.accept(counter);
		return counter;
	}



	public interface ScannerCounterConsumer<K> extends BiConsumer<ScanHelper,ObjectCounter<K>>{

	}

	public static class CounterConsumer<V> implements BiConsumer<V,ObjectCounter>{

		@Override
		public void accept(V input,ObjectCounter counter) {
			counter.add(input);

		}
	}



	@Override
	public Iterator<ObjectCounter<T>> iterator() {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	public boolean hasNext() {
		// TODO 自動生成されたメソッド・スタブ
		return false;
	}

	@Override
	public ObjectCounter<T> next() {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}


}
