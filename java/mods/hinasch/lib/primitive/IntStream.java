package mods.hinasch.lib.primitive;

import java.util.List;

import com.google.common.collect.Lists;

@Deprecated
public class IntStream {

	/**
	 * min -> max-1まで
	 * @param min
	 * @param max
	 * @return
	 */
	public static ListHelper<Integer> range(int min,int max){
		List<Integer> intlist = Lists.newArrayList();


		for(int i=min;i<max;i++){
			intlist.add(i);
		}

		return ListHelper.stream(intlist);
	}
}
