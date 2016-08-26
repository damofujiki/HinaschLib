//package mods.hinasch.lib;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import net.minecraft.block.Block;
//import net.minecraft.block.state.IBlockState;
//import net.minecraft.item.Item;
//
//import com.google.gson.internal.LinkedTreeMap;
//
//public class PairIDList {
//
//	public ArrayList<PairID> list;
//
//	public PairIDList(){
//		this.list = new ArrayList();
//	}
//
//	public int getNumber(PairID pair){
//		for(int i=0;i<list.size();i++){
//			if(list.get(i).equalsPair(pair)){
//				return i;
//			}
//		}
//		return -1;
//	}
//
//	public boolean contains(PairID pair){
//		return this.getNumber(pair)!=-1 ? true : false;
//	}
//
//	public PairID getPairID(int num){
//		return this.list.get(num);
//	}
//	public PairID getElement(PairID pair){
//		int num = this.getNumber(pair);
//		return this.list.get(num);
//	}
//	public void addStack(IBlockState state,int amount){
//		Block block = state.getBlock();
//		int meta = block.getMetaFromState(state);
//		this.addStack(new PairID(block,meta), amount);
//
//	}
//	public void addStack(PairID pair,int amount){
//		if(this.contains(pair)){
//			this.getElement(pair).stack += amount ;
//		}else{
//			this.list.add(pair.setStack(amount));
//		}
//
//	}
//	//public
//	public class IntPair{
//		public int id;
//		public int meta;
//		public int stack;
//
//		public IntPair(int par1,int par2,int par3){
//			this.id = par1;
//			this.meta = par2;
//			this.stack = par3;
//		}
//
//		public IntPair(double par1,double par2,double par3){
//			this.id = (int) par1;
//			this.meta = (int) par2;
//			this.stack = (int) par3;
//		}
//
//		public PairID getPairID(Class clazz){
//			if(clazz==Block.class){
//				return new PairID(Block.getBlockById(id),this.meta).setStack(stack);
//			}
//			return new PairID((Item)Item.itemRegistry.getObjectById(this.id),this.meta).setStack(stack);
//		}
//	}
//
//	public List<IntPair> getIntPairList(){
//		List<IntPair> intList = new ArrayList();
//		for(PairID pair:this.list){
//			intList.add(new IntPair(pair.getId(),pair.getMeta(),pair.getStack()));
//		}
//		return intList;
//	}
//
//	public static PairIDList buildFromJsonList(List<LinkedTreeMap> list){
//		PairIDList pairList = new PairIDList();
//		for(LinkedTreeMap amap:list){
//			PairID pair = pairList.getIntPairFromLinkedTreeMap(amap).getPairID(Block.class);
//			pairList.addStack(pair, pair.getStack());
//		}
//		return pairList;
//	}
//
//	protected IntPair getIntPairFromLinkedTreeMap(LinkedTreeMap<String,Double> map){
//
//		return new IntPair(map.get("id"),map.get("meta"),map.get("stack"));
//	}
//	@Override
//	public String toString(){
//		StringBuilder strb = new StringBuilder();
//		if(!this.list.isEmpty()){
//			for(PairID aPair:this.list){
//				strb.append(aPair.toString()+":"+aPair.stack+",");
//			}
//			return new String(strb);
//		}
//		return "empty?";
//
//	}
//}
