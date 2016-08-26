package mods.hinasch.lib;

import com.mojang.realmsclient.util.Pair;

import net.minecraft.block.Block;

public class PairID extends Pair<Block,Integer>{

	protected PairID(Block first, int second) {
		super(first, second);
		// TODO 自動生成されたコンストラクター・スタブ
	}

	public static PairID of(Block first,int second){
		return new PairID(first,second);
	}
//	//public int id;
//	//public int metadata;
//	//option
//	public int stack;
//
//	public List<PairID> sameBlocks;
//
//	public boolean checkMetadata;
//
//	//public Block blockObj;
//	//public Item itemObj;
//
//	public PairID(){
//		super(null,0);
//		this.stack = 0;
//		this.sameBlocks = new ArrayList();
//		this.checkMetadata = false;
//	}
//
//	public PairID(int par2){
//		super(null,par2);
//
//	}
//
//	public PairID(ItemStack is){
//		this(is.getItemDamage());
//		this.setObject(is.getItem());
//		this.sameBlocks = new ArrayList();
//		this.checkMetadata = true;
//	}
//
//	public PairID(Item item, int itemDamage) {
//		this(itemDamage);
//		this.setObject(item);
//		this.sameBlocks = new ArrayList();
//		this.checkMetadata = true;
//	}
//	public PairID(Block block,int meta){
//		this(meta);
//		this.setObject(block);
//		this.sameBlocks = new ArrayList();
//		this.checkMetadata = true;
//
//	}
//	public PairID(IBlockState state){
//		this(Block.getStateId(state));
//		this.setObject(state.getBlock());
//		this.sameBlocks = new ArrayList();
//		this.checkMetadata = true;
//
//	}
//
//
//
//	public void setObject(Object par1){
//		this.left = par1;
//	}
//
//	public Object getObject(){
//		return this.getLeft();
//	}
//
//	public void setMeta(int par1){
//		this.setValue(par1);
//	}
//
//	public int getMeta(){
//		return (Integer) this.getRight();
//	}
//
//	public int getId(){
//		if(this.getObject() instanceof Block){
//			return Block.blockRegistry.getIDForObject(this.getBlockObject());
//		}
//		if(this.getObject() instanceof Item){
//			return Item.itemRegistry.getIDForObject(this.getItemObject());
//		}
//		return -1;
//	}
//	@Override
//	public String toString(){
//		StringBuilder builder = new StringBuilder();
//		if(this.getObject() instanceof Block){
//			builder.append(((Block) this.getObject()).getLocalizedName());
//		}else{
//			//builder.append("Block is null");
//		}
//		if(this.getObject() instanceof Item){
//			builder.append(((Item) this.getObject()).getUnlocalizedName());
//		}else{
//			//builder.append("Item is null");
//		}
//		builder.append(this.getMeta());
//		return new String(builder);
//	}
//
//	public Item getItemObject(){
//		return (Item)this.getObject();
//	}
//
//	public Block getBlockObject(){
//		return (Block) this.getObject();
//	}
//	public void setData(Object par1,int par2){
//		this.setObject(par1);
//		this.setMeta(par2);
//	}
//
////	public void getFromWorld(World world,int x,int y,int z){
////		this.setObject(world.getBlock(x, y, z));
////		this.setMeta(world.getBlockMetadata(x, y, z));
////
////	}
//
//	public ItemStack getAsItemStack(){
//		if(this.getObject() instanceof Block){
//			return new ItemStack(this.getBlockObject(),this.stack,this.getMeta());
//		}
//		if(this.getObject() instanceof Item){
//			return new ItemStack(this.getItemObject(),this.stack,this.getMeta());
//		}
//		return null;
//	}
//
//	public boolean equalsPair(PairID pairid){
//		if(this.getObject()==null)return false;
//
//		Object obj = this.getObject();
//		if(obj instanceof Block || obj instanceof Item){
//
//			if(this.getObject()==pairid.getObject()){
//				if(this.getMeta()==pairid.getMeta() && this.checkMetadata || !this.checkMetadata){
//					return true;
//				}
//			}
//		}
////		if(this.itemObj!=null){
////			if(this.itemObj==pairid.itemObj){
////				if(this.metadata==pairid.metadata && this.checkMetadata || !this.checkMetadata){
////					return true;
////				}
////			}
////		}
//
//
//		return false;
//	}
//
//
//	public PairID setCheckMetadata(boolean par1){
//		this.checkMetadata = par1;
//		return this;
//	}
//
//	public boolean equalsOrSameBlock(PairID pairid){
//		if(this.equalsPair(pairid)){
//			return true;
//		}
//		for(PairID blockdata:this.sameBlocks){
//			if(blockdata.equalsPair(pairid)){
//				return true;
//			}
//		}
//		return false;
//	}
//
//	public PairID setStack(int par1){
//		this.stack = par1;
//		return this;
//	}
//
//	public int getStack(){
//		return this.stack;
//	}
//
//	public static PairID getPair(IBlockState state){
//		return new PairID(state);
//	}
////	public static PairID getBlockFromWorld(World world,XYZPos pos){
////
////		Block block = world.getBlock(pos.x,pos.y,pos.z);
////		int meta = world.getBlockMetadata(pos.x, pos.y, pos.z);
////		PairID pairid = new PairID(block,meta);
////		return pairid;
////	}
}
