package mods.hinasch.lib.primitive;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class SortableListHelper<T extends Comparable> extends ListHelper<T>{

	public static <K extends Comparable> SortableListHelper create(Collection<K> list){
		return new SortableListHelper(list);
	}
	public SortableListHelper(Collection<T> list) {
		super(list);
	}

	public ListHelper<T> sorted(){
		List<T> newList = new ArrayList();
		for(T elm:this.list){
				newList.add(elm);
		}
		Collections.sort(newList);
		return new ListHelper(newList);
	}
}
