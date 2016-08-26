package mods.hinasch.lib.primitive;

import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumSet;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import com.google.common.collect.Lists;

import mods.hinasch.lib.WeightedRandomItem;
import net.minecraft.util.WeightedRandom;



public class CollectorUtil {


//    static final Set<Collector.Characteristics> CH_ID
//    = Collections.unmodifiableSet(EnumSet.of(Collector.Characteristics.IDENTITY_FINISH));
	public static <T> RandomGetCollector<T> toRandomGet(){
		RandomGetCollector<T> collector = new RandomGetCollector();
		return collector;
//		return Collectors.collectingAndThen(Collectors.toList(), in -> new ListRandomGet<T>(in) );
	}




	public static class RandomGetCollector<T> implements Collector<T,List<T>,ListRandomGet<T>>{

		@Override
		public Supplier<List<T>> supplier() {
			// TODO 自動生成されたメソッド・スタブ
			return () -> Lists.<T>newArrayList();
		}

		@Override
		public BiConsumer<List<T>, T> accumulator() {
			// TODO 自動生成されたメソッド・スタブ
			return (list,elm) -> list.add(elm);
		}

		@Override
		public BinaryOperator<List<T>> combiner() {
			// TODO 自動生成されたメソッド・スタブ
			return (list0,list1)->{
				list0.addAll(list1);
				return list0;
			};
		}

		@Override
		public Function<List<T>, ListRandomGet<T>> finisher() {
			// TODO 自動生成されたメソッド・スタブ
			return in -> new ListRandomGet(in);
		}

		@Override
		public Set<java.util.stream.Collector.Characteristics> characteristics() {
			// TODO 自動生成されたメソッド・スタブ
			return EnumSet.of(Characteristics.UNORDERED);
		}


	}
	public static class ListRandomGet<T>{

		List<T> list = Lists.newArrayList();

		public ListRandomGet(List<T> list){
			this.list = list;
		}

		public boolean add(T e){
			return list.add(e);

		}

		public void addAll(List<T> e){
			list.addAll(e);
		}

		public ListRandomGet<T> diff(Collection<T> except){

			List<T> output = new ArrayList();
			for(T elm:this.list){
				if(!except.contains(elm)){
					output.add(elm);
				}
			}
			return new ListRandomGet(output);
		}
		public Optional<T> getRandom(Random rand){
			if(this.list.isEmpty()){
				return Optional.empty();
			}
			if(this.list.size()==1){
				return Optional.of(list.get(0));
			}
			int rnd = rand.nextInt(list.size());
			return Optional.of(list.get(rnd));
		}

		public T getWeighedRandom(Random rand,final Function<T,Integer> weightFunc,T def){
			List<WeightedRandomItem<T>> weightedList = this.list.stream()
					.map(input -> new WeightedRandomItem<T>(weightFunc.apply(input),input))
					.collect(Collectors.toList());
			if(weightedList.isEmpty()){
				return def;
			}
			return WeightedRandom.getRandomItem(rand, weightedList).getItem();
		}
	}
}
