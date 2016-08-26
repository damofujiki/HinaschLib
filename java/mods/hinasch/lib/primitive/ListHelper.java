package mods.hinasch.lib.primitive;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.function.UnaryOperator;

import com.google.common.base.Function;
import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import com.google.common.base.Supplier;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import io.netty.util.internal.chmv8.ConcurrentHashMapV8.BiFun;
import mods.hinasch.lib.WeightedRandomItem;
import mods.hinasch.lib.iface.BiConsumer;
import mods.hinasch.lib.iface.Consumer;
import net.minecraft.util.WeightedRandom;

/** Streamっぽいやつとかいろいろ*/
public class ListHelper<T> {




	public static interface BinaryOperator<T>{

		public T apply(T left,T right);
	}
	public static interface BiPredicate<T,V>{

		public boolean apply(T left,V right);
	}

	public static <T> List<T> toList(Iterator<T> iterator){
		List<T> list = Lists.newArrayList();
		for(Iterator<T> ite=iterator;ite.hasNext();){
			T elm = ite.next();
			list.add(elm);
		}
		return list;
	}
	/**
	 * 汎用カウンタ
	 *
	 */
	public static class Counter{
		private int var1;
		public static Counter of(int c){
			return new Counter(c);
		}
		public Counter(int var1){
			this.var1 = var1;
		}
		public void count(){
			this.var1 += 1;
		}

		public void reset(int c){
			this.var1 = c;
		}
		public int getCount(){
			return this.var1;
		}
	}

	public static class FilterFunc<T,V>{
		List<V> list;
		int inte;
		public FilterFunc(){
			this.list = new ArrayList();
			this.inte = 0;
		}

		public int getInteger(){
			return this.inte;
		}

		public List<V> getList(){
			return this.list;
		}
		public void invoke(T elm,Collection<T> parent){

		}

		public void setInteger(int var1){
			this.inte = var1;
		}
	}

	public static interface FlatMapFunction<T,V>{
		public Collection<V> apply(T input);
	}

	public static interface IntConsumer extends Consumer<Integer>{

	}

	public static interface IntFunction<T> extends Function<Integer,T>{

	}

	public static interface IPriority{
		public int getPriority();
	}
	public static interface ObjIntConsumer<T> extends BiConsumer<T,Integer>{

	}

	public T getWeighedRandom(Random rand,final Function<T,Integer> weightFunc,T def){
		List<WeightedRandomItem<T>> weightedList = this.map(new Function<T,WeightedRandomItem<T>>(){

			@Override
			public WeightedRandomItem<T> apply(T input) {
				// TODO 自動生成されたメソッド・スタブ
				return new WeightedRandomItem(weightFunc.apply(input),input);
			}}
		).getList();
		if(weightedList.isEmpty()){
			return def;
		}
		return WeightedRandom.getRandomItem(rand, weightedList).getItem();
	}
	public static <K> K getRandom(Collection<K> collection,Random rand){
		if(collection.isEmpty()){
			return null;
		}
		if(collection.size()==1){
			for(K elm:collection){
				return elm;
			}
		}else{
			int index = 0;
			int pick = rand.nextInt(collection.size());
			for(K elm:collection){
				if(pick==index){
					return elm;
				}
				index ++;
			}
		}
		return null;
	}
	public static class Range{
		public final int max;
		public final int min;
		public Range(int min,int max){
			this.max = max;
			this.min = min;
		}

		public Range(int max){
			this.max = max;
			this.min = 0;
		}
		public static Range max(int m){
			return new Range(m);
		}
		public float getPercentage(int in){
			int par1 = this.max - this.min;
			int par2 = in - this.min;
			float f1 = (float)par2/(float)par1;
			System.out.println(this.max+","+this.min);
			System.out.println(in);
			return f1;
		}
		public int getCenter(){
			int par1 = max - min;
			return min + (par1/2);
		}
		public int getRandomNumber(Random rand){
			return rand.nextInt(max-min) + min;
		}
	}

	public static interface ToIntFunction<T> extends Function<T,Integer>{

	}

	public static interface ToStrFunction<T> extends Function<T,String>{

	}


	public static <V, A, B> ListHelper<V> asStream(Range range,BiFun<A,Integer,V> func,A par2){
		List<V> list = new ArrayList();
		for(int i=range.min;i<range.max;i++){
			if(func.apply(par2, i)!=null){
				list.add(func.apply(par2, i));
			}

		}
		return new ListHelper(list);
	}



	public static <V> ListHelper<V> asStream(Range range,Function<Integer,V> func){
		List<V> list = new ArrayList();
		for(int i=range.min;i<range.max;i++){
			if(func.apply( i)!=null){
				list.add(func.apply(i));
			}

		}
		return new ListHelper(list);
	}

	public static <V> void asStream(Range range,ObjIntConsumer<V> consumer,V par2){
		for(int i=range.min;i<range.max;i++){
			consumer.accept(par2,i);
		}
	}
	public static <V> ListHelper<V> asStream(Range range,IntFunction<V> func){
		List<V> list = new ArrayList();

		for(int i=range.min;i<range.max;i++){
			if(func.apply(i)!=null){
				list.add(func.apply(i));
			}
		}
		return new ListHelper(list);
	}

	public static <A> ListHelper stream(A... array){
		return new ListHelper<A>(array);
	}

	public static <A> ListHelper newHelper(A... array){
		return new ListHelper<A>(array);
	}
	public static <A> ListHelper stream(Collection<A> col){
		return new ListHelper<A>(col);
	}
	public static <A> ListHelper stream(Iterable<A> ite){
		return new ListHelper<A>(ite);
	}
	public static <A> ListHelper stream(Iterator<A> ite){
		return new ListHelper<A>(ite);
	}
	public static ListHelper<Integer> stream(int[] col){
		List<Integer> intList = new ArrayList();
		for(int inte:col){
			intList.add(inte);
		}
		return new ListHelper(intList);
	}



	protected Collection<T> list;

	protected int index;

	public static BinaryOperator<Integer> operatorMax = new BinaryOperator<Integer>(){

		@Override
		public Integer apply(Integer left, Integer right) {
			if(left<right){
				return right;
			}
			return left;
		}

	};
	public static BinaryOperator<Integer> operatorSum = new BinaryOperator<Integer>(){

		@Override
		public Integer apply(Integer left, Integer right) {
			return left+right;
		}

	};
	public static BinaryOperator<String> operatorStringJoin = new BinaryOperator<String>(){

		@Override
		public String apply(String left, String right) {
			return left+right;
		}

	};
	public static BinaryOperator<IPriority> operatorPriority = new BinaryOperator<IPriority>(){

		@Override
		public IPriority apply(IPriority left, IPriority right) {
			if(left.getPriority()<right.getPriority()){
				return right;
			}
			return left;
		}

	};
	public ListHelper(Collection<T> list){
		this.list = list;
	}
	public ListHelper(T... array){
		this.index = 0;
		List<T> output = new ArrayList();
		for(T elm:array){
			output.add(elm);
		}
		this.list = output;
	}

	protected ListHelper(Iterable<? extends T> ite){

		List<T> output = new ArrayList();
		for(T elm:ite){
			output.add(elm);
		}

		this.list = output;

	}
	protected ListHelper(Iterator<? extends T> ite){

		List<T> output = new ArrayList();
		for(;ite.hasNext();){
			T elm = ite.next();
			output.add(elm);
		}

		this.list = output;

	}
	public boolean allMatch(Predicate<T> predicate){
		int var1 = 0;
		for(T elm:this.list){
			if(predicate.apply(elm)){
				var1 += 1;
			}
		}
		return var1 >= this.list.size();
	}
	public boolean anyMatch(Predicate<T> predicate){
		boolean var1 = false;
		for(T elm:this.list){
			if(predicate.apply(elm)){
				var1 = true;
			}
		}
		return var1;
	}
	public boolean contains(T refer){
		return this.list.contains(refer);
	}

	public boolean containsAny(Collection<T> refer){
		boolean rt = false;
		for(T elm:this.list){
			if(refer.contains(elm)){
				rt = true;
			}
		}
		return rt;
	}

	public boolean containsAny(T... refer){
		boolean rt = false;
		List list = Arrays.asList(refer);
		return this.containsAny(list);
	}

	public int count(){
		return this.list.size();
	}

	public int count(T base){
		int count=0;
		if(base!=null){
			for(T elm:this.list){
				if(elm!=null && base==elm){
					count ++;
				}
			}
		}

		return count;
	}
//	public static class Predicates<T>{
//		List<Predicate<T>> list = new ArrayList();
//
//		public Predicates(List<Predicate<T>> predicates){
//			this.list = predicates;
//		}
//
//		public boolean apply(T input){
//			int var1 = 0;
//			for(Predicate<T> predicate:this.list){
//				if(predicate.apply(input)){
//					var1 += 1;
//
//				}
//			}
//			if(var1>=this.list.size()){
//				return true;
//			}
//			return false;
//		}
//	}

	/** 差。引数のものを省く*/
	public ListHelper<T> diff(Collection<T> except){

		if(this.list instanceof Set){
			Set<T> output = new HashSet();
			for(T elm:this.list){
				if(!except.contains(elm)){
					output.add(elm);
				}
			}
			return new ListHelper(output);
		}
		List<T> output = new ArrayList();
		for(T elm:this.list){
			if(!except.contains(elm)){
				output.add(elm);
			}
		}
		return new ListHelper(output);
	}
	public <V> List<T> filter(BiPredicate<T,V> predicate,V par2){
		List<T> list = new ArrayList();
		for(T elm:this.list){
			if(predicate.apply(elm,par2)){
				list.add(elm);
			}
		}
		return list;
	}



//	public ListHelper<T> sorted(){
//		return (new SortedListHelper(this.list)).sorted();
//	}
	public ListHelper<T> sorted(Comparator<T> comparator){
		List<T> newList = new ArrayList();

		for(T elm:this.list){
			newList.add(elm);
		}
		Collections.sort(newList,comparator);

		return new ListHelper(newList);
	}

	public ListHelper<T> sorted(final Function<T,Integer> supplier){
		return this.sorted(new Comparator<T>(){

			@Override
			public int compare(T o1, T o2) {
				if(supplier.apply(o1)>supplier.apply(o2)){
					return 1;
				}
				if(supplier.apply(o1)==supplier.apply(o2)){
					return 0;
				}
				return -1;
			}}
		);
	}
	//	public  <V> List<V> iterate(FunctionWithIndex<T,V> function){
	//		List<V> list = new ArrayList();
	//		for(T elm:this.list){
	//			if(function.apply(elm)!=null){
	//				list.add(function.apply(elm));
	//			}
	//			function.index += 1;
	//		}
	//		return list;
	//	}

	//	public <V> int iterateAndGetInteger(FilterFunc<T,V> delegate){
	//		List<T> list = new ArrayList();
	//		for(T elm:this.list){
	//			delegate.invoke(elm,this.list);
	//		}
	//		return delegate.getInteger();
	//	}
	//	public static class FunctionWithIndex<T,V> implements Function<T,V>{
	//
	//		protected int index;
	//		public FunctionWithIndex(){
	//			this.index = 0;
	//		}
	//
	//		public int getIndex(){
	//			return this.index;
	//		}
	//		@Override
	//		public V apply(T input) {
	//			// TODO 自動生成されたメソッド・スタブ
	//			return null;
	//		}
	//
	//	}

	public ListHelper<T> filter(Predicate<T> predicate){
		List<T> list = new ArrayList();
		for(T elm:this.list){
			if(predicate.apply(elm)){
				list.add(elm);
			}
		}
		return new ListHelper(list);
	}
	public ListHelper<T> filterAsListHelper(Predicate<T> predicate){
		List<T> list = new ArrayList();
		for(T elm:this.list){
			if(predicate.apply(elm)){
				list.add(elm);
			}
		}
		return ListHelper.stream(list);
	}


	public Optional<T> findAny(Random rand){

		if(this.list.isEmpty()){
			return Optional.absent();
		}
		int size = this.list.size();
		int index = 0;
		int select = size == 1 ? 0 : rand.nextInt(size);
		for(T elm:this.list){
			if(index==select){
				return Optional.of(elm);
			}
			index += 1;
		}
		return Optional.absent();
	}
	public Optional<T> findFirst(){
		if(this.list.isEmpty()){
			return Optional.absent();
		}
		return this.reduce(new BinaryOperator<T>(){

			@Override
			public T apply(T left, T right) {

				return left;
			}});
	}
	/** 複数の値を返したい時に使う。*/
	public  <V> List<V> flatMap(FlatMapFunction<T,V> function){
		List<V> list = new ArrayList();

		for(T elm:this.list){
			if(function.apply(elm)!=null){
				list.addAll(function.apply(elm));
			}
		}
		return list;
	}

	public <V> void forEach(BiConsumer<T, V> biConsumer,V par2){
		for(T elm:this.list){
			biConsumer.accept(elm,par2);
		}

	}

	/** 副作用だけの*/
	public void forEach(Consumer<T> consumer){
		for(T elm:this.list){
			consumer.accept(elm);
		}

	}
	public Collection<T> get(){
		return this.list;
	}



	public List<T> getList(){
		if(this.list instanceof List){
			return (List<T>) this.list;
		}
		return Lists.newArrayList(this.list);
	}


	public Set<T> getSet(){
		return Sets.newHashSet(this.list);
	}
	/** 重なる部分を返す*/
	public Collection<T> intersect(Collection<T> filter){
		if(this.list instanceof Set){
			Set<T> output = new HashSet();
			for(T elm:this.list){
				if(filter.contains(elm)){
					output.add(elm);
				}
			}
			return output;
		}
		List<T> output = new ArrayList();
		for(T elm:this.list){
			if(filter.contains(elm)){
				output.add(elm);
			}
		}
		return output;
	}
	public  <V, U> List<V> map(BiFun<T,U,V> function,U par2){
		List<V> list = new ArrayList();
		for(T elm:this.list){
			if(function.apply(elm,par2)!=null){
				list.add(function.apply(elm, par2));
			}
		}
		return list;
	}

	/**
	 * 値を変換する。(mapTo)
	 * nullを返すとその要素は追加されない
	 * @param function
	 * @return
	 */
	public  <V> ListHelper<V> map(Function<T,V> function){
		List<V> list = new ArrayList();

		for(T elm:this.list){
			if(function.apply(elm)!=null){
				list.add(function.apply(elm));
			}
		}
		return new ListHelper(list);
	}

	/** カウンターつき */
	public  <V> List<V> mapByCount(BiFun<T,Counter,V> function){
		List<V> list = new ArrayList();
		final Counter counter = new Counter(0);
		for(T elm:this.list){
			if(function.apply(elm,counter)!=null){
				list.add(function.apply(elm, counter));
			}
			counter.count();
		}
		return list;
	}
//	/** カウンターつき */
//	public  <V> List<V> mapByCount(Function<Tuple<T,Counter>,V> function){
//		List<V> list = new ArrayList();
//		final Counter counter = new Counter(0);
//		for(T elm:this.list){
//			if(function.apply(new Tuple(elm,counter))!=null){
//				list.add(function.apply(new Tuple(elm, counter)));
//			}
//			counter.count();
//		}
//		return list;
//	}

	public ListHelper<T> mapTo(UnaryOperator<T> function){
		List<T> newList = new ArrayList();
		for(T elm:this.list){
			if(function.apply(elm)!=null){
				newList.add(function.apply(elm));
			}
		}
		return new ListHelper(newList);
	}

	public ListHelper<Integer> mapToInt(ToIntFunction<T> function){
		return this.map(function);
	}
	//	public  <V> List<V> mapWithIndex(Function<T,V> function){
	//		List<V> list = new ArrayList();
	//		for(T elm:this.list){
	//			if(function.apply(elm)!=null){
	//				list.add(function.apply(elm));
	//			}
	//			index+=1;
	//		}
	//		return list;
	//	}

	public ListHelper<String> mapToString(ToStrFunction<T> function){
		return this.map(function);
	}

	public <R> R collect(Supplier<R> supplier,Accumulator<R,T> accumulator){

		return this.collect(supplier, accumulator, new Finisher<R,R>(){

			@Override
			public R apply(R input) {
				// TODO 自動生成されたメソッド・スタブ
				return input;
			}}
		);
//		R outer = supplier.get();
//
//		for(T elm:this.list){
//			accumulator.accumulate(outer, elm);
//		}
//
//		return outer;
	}

	public <R, K> K collect(Supplier<R> supplier,Accumulator<R,T> accumulator,Finisher<K,R> finisher){


		R outer = supplier!=null ? supplier.get() : new Supplier<R>(){

			@Override
			public R get() {
				// TODO 自動生成されたメソッド・スタブ
				return null;
			}
		}.get();

		for(T elm:this.list){
			if(accumulator!=null){
				accumulator.accumulate(outer, elm);
			}

		}

		return finisher.apply(outer);
	}


	public <U, K> Map<K,U> toMap(final Function<T,K> keyMapper,final Function<T,U> valueMapper){
		return this.collect(new Collector<T,Map<K,U>,Map<K,U>>(){

			@Override
			public Map<K, U> apply(Map<K, U> input) {
				// TODO 自動生成されたメソッド・スタブ
				return input;
			}

			@Override
			public void accumulate(Map<K,U> collector, T in) {
				K key = keyMapper.apply(in);
				U value = valueMapper.apply(in);
				collector.put(key, value);

			}

			@Override
			public Map<K,U> get() {
				// TODO 自動生成されたメソッド・スタブ
				return Maps.newHashMap();
			}}
		);
	}
	public <R, A> R collect(Collector<T,A,R> collector){
		return this.collect(collector, collector,collector);

	}
	/** 値を集約する。初期値は最初の値
	複数ある値をひとつに絞るのに使う */
	public Optional<T> reduce(BinaryOperator<T> operator){
		T rt = null;
		for(T elm:this.list){
			if(rt==null){
				rt = elm;
			}else{
				rt = operator.apply(rt, elm);
			}

		}
		if(rt==null){
			return Optional.absent();
		}
		return Optional.of(rt);
	}
	/** 値を集約する。初期値はひとつめの引数
	 複数ある値をひとつに絞るのに使う */
	public T reduce(T init,BinaryOperator<T> operator){
		T rt = init;
		for(T elm:this.list){
			rt = operator.apply(rt, elm);
		}
		return rt;
	}

	public boolean subsetOf(Collection<T> refer){
		boolean rt = false;
		int var1 = 0;
		for(T elm:this.list){
			if(refer.contains(elm)){
				var1 += 1;
			}
		}
		if(var1>=refer.size()){
			return true;
		}
		return false;
	}
	public boolean subsetOf(T... refer){
		boolean rt = false;
		List list = Arrays.asList(refer);
		return this.subsetOf(list);
	}




	/**
	 * 初期値は０
	 * @return
	 */
	public int sum(){
		ListHelper<Integer> helper = new ListHelper(this.list);
		return helper.reduce(0,operatorSum);
	}


	/**
	 * 初期値は０
	 * @return
	 */
	public int max(){
		ListHelper<Integer> helper = new ListHelper(this.list);
		return helper.reduce(0,operatorMax);
	}

	public static interface Finisher<C,B> extends Function<B,C>{


	}

	public static interface Collector<A,B,C> extends Finisher<C,B>,Accumulator<B,A>,Supplier<B>{

	}



	public Optional<T> toOptional(){
		return this.collect(null, null,new Finisher<Optional<T>,T>(){

			@Override
			public Optional<T> apply(T input) {
				// TODO 自動生成されたメソッド・スタブ
				return Optional.of(input);
			}}
		);
	}
	public static class CollectOptional<A> implements Collector<A,A,Optional<A>>{



		@Override
		public void accumulate(A collector, A in) {
			// TODO 自動生成されたメソッド・スタブ

		}

		@Override
		public A get() {
			// TODO 自動生成されたメソッド・スタブ
			return null;
		}

		@Override
		public Optional<A> apply(A input) {
			if(input!=null){
				return Optional.of(input);
			}
			return Optional.absent();
		}


	}


}
