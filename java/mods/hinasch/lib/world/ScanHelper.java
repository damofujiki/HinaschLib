package mods.hinasch.lib.world;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import com.google.common.base.Function;

import mods.hinasch.lib.core.HSLib;
import mods.hinasch.lib.iface.BiConsumer;
import mods.hinasch.lib.iface.IIntSerializable;
import mods.hinasch.lib.primitive.ListHelper;
import mods.hinasch.lib.primitive.Tuple;
import mods.hinasch.lib.util.HSLibs;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ScanHelper implements Iterator<ScanHelper>{

	public static interface ScanConsumer extends Consumer<ScanHelper>{

	}

	public static enum Mode implements IIntSerializable{
		REVERSE(2),RANGE(0),START_TO_END(1);

		int meta;

		private Mode(int meta){
			this.meta = meta;
		}
		@Override
		public int getMeta() {
			// TODO 自動生成されたメソッド・スタブ
			return meta;
		}

		public static Mode fromMeta(int meta){
			return HSLibs.fromMeta(Mode.values(), meta);
		}

	}
	public static final int REVERSE = 2;
	public static ScanHelper create(Entity ep,int par1range,int par2rangey){
		return new ScanHelper(ep,par1range,par2rangey);
	}
	public static ScanHelper create(XYZPos start,int par1range,int par2rangey){
		return new ScanHelper(start.getX(),start.getY(),start.getZ(),par1range,par2rangey);
	}
	public static ScanHelper create(XYZPos start,XYZPos end){
		return new ScanHelper(start,end);
	}
	public static ScanHelper create(XYZPos start,XYZPos end,int mode){
		return new ScanHelper(start,end,mode);
	}
	public static XYZPos entityPosToXYZ(EntityLivingBase ep){
		XYZPos xyz = new XYZPos((int)ep.posX,(int)ep.posY,(int)ep.posZ);
		return xyz;
	}
	public static Integer[] swap(int par1,int par2){
		Integer[] var1 = new Integer[2];
		if(par1>par2){
			var1[0] = par2;
			var1[1] = par1;
			return var1;
		}
		var1[0] = par1;
		var1[1] = par2;
		return var1;
	}
	public int sx;
	public int sy;

	public int sz;
	public int range;
	public int rangeY;


	//public XYZPos count;

	//	public ScanHelper previous;
	public boolean requireFirst = false;
	public boolean cutEnd = false;


	public final int startX;
	public final int startY;
	public final int startZ;
	public final int endX;

	public final int endY;

	public final int endZ;

	public final Mode mode;

	public Entity entity;
	public World world;
	protected boolean scanned;

	public ScanHelper(Entity ep,int par1range,int par2rangey){
		this.range = par1range;
		this.rangeY = par2rangey;
		this.sx = (int)ep.posX - (range/2);
		this.sy = (int)ep.posY - (rangeY/2);
		this.sz = (int)ep.posZ - (range/2);
		this.startX = this.sx;
		this.startY = this.sy;
		this.startZ = this.sz;
		this.scanned = false;
		this.entity = ep;
		this.mode = Mode.RANGE;
		this.endX = 0;
		this.endY = 0;
		this.endZ = 0;
	}

	public ScanHelper(int sx1,int sy1,int sz1,int par1range,int par2rangey){
		this.range = par1range;
		this.rangeY = par2rangey;
		this.sx = sx1 - (range/2);
		this.sy = sy1 - (rangeY/2);
		this.sz = sz1 - (range/2);
		this.startX = this.sx;
		this.startY = this.sy;
		this.startZ = this.sz;
		this.scanned = false;
		this.mode = Mode.RANGE;
		this.endX = 0;
		this.endY = 0;
		this.endZ = 0;
	}
	public ScanHelper(XYZPos s,XYZPos e){
		Tuple<XYZPos, XYZPos> swapped = XYZPos.compareAndSwap(s, e);
		XYZPos start = s;
		XYZPos end = e;
		this.startX = start.getX();
		this.startY = start.getY();
		this.startZ = start.getZ();
		this.sx = this.startX;
		this.sy = this.startY;
		this.sz = this.startZ;
		this.endX = end.getX();
		this.endY = end.getY();
		this.endZ = end.getZ();
		this.scanned = false;
		this.mode = Mode.START_TO_END;

	}
	public ScanHelper(XYZPos s,XYZPos e,int mode){

		Tuple<XYZPos, XYZPos> swapped = XYZPos.compareAndSwap(s, e);
		XYZPos start = s;
		XYZPos end = e;
		this.startX = start.getX();
		this.startY = start.getY();
		this.startZ = start.getZ();
		this.sx = this.startX;
		this.sy = this.startY;
		this.sz = this.startZ;
		this.endX = end.getX();
		this.endY = end.getY();
		this.endZ = end.getZ();
		this.scanned = false;
		this.mode = Mode.fromMeta(mode);


	}

	public  <T> void asStream(BiConsumer<ScanHelper,T> consumer,T par2){
		for(;this.hasNext();this.next()){
			consumer.accept(this,par2);
		}
	}
	//	public int getMetadata(){
	//
	//		if(this.world!=null){
	//			return this.world.getBlockMetadata(sx, sy, sz);
	//		}else{
	//			System.out.println("World is null(ScanHelper)");
	//			return -1;
	//		}
	//	}

	/** コールバックもほしい場合とか。steamで返す*/
	public  <T, V> ListHelper<T> asStream(BiFunction<ScanHelper,V,T> func,V v){
		List<T> list = new ArrayList();
		for(;this.hasNext();this.next()){
			if(func.apply(this,v)!=null){
				list.add(func.apply(this,v));
			}

		}
		return ListHelper.stream(list);
	}

	public  void asStream(Consumer<ScanHelper> consumer){
		this.stream().forEach(consumer);
	}

	public boolean anyMatch(Predicate<ScanHelper> func){
		return this.stream().anyMatch(func);
	}

	public boolean allMatch(Predicate<ScanHelper> func){
		return this.stream().allMatch(func);
	}
	public  <T> List<T> asStream(Function<ScanHelper,T> func){
		List<T> list = new ArrayList();
		for(;this.hasNext();this.next()){
			if(func.apply(this)!=null){
				list.add(func.apply(this));
			}

		}
		return list;
	}

	/** メタデータを加味しない。*/
	public int countBlock(final Block blockIn){
		return this.stream().mapToInt(input -> input.getBlock()==blockIn ? 1 : 0).sum();
		//		return this.asStream(new BiFunction<ScanHelper,IBlockState,Integer>(){
		//
		//			@Override
		//			public Integer apply(ScanHelper scanner, IBlockState b) {
		//				return scanner.getBlock()==b.getBlock() ? 1 : 0;
		//			}
		//		}, blockIn.getDefaultState()).sum();

	}

	public Block getBlock(){

		if(this.world!=null){
			if(this.world.isAirBlock(new BlockPos(this.sx,this.sy,this.sz))){
				return Blocks.AIR;
			}
			return this.world.getBlockState(this.getXYZPos()).getBlock();
		}else{
			System.out.println("World is null(ScanHelper)");
			return null;
		}
	}

	public IBlockState getBlockState(){
		if(this.world!=null){
			return this.world.getBlockState(this.getXYZPos());
		}else{
			System.out.println("World is null(ScanHelper)");
			return null;
		}
	}
	public XYZPos getCount(){
		int x = Math.abs(Math.abs(this.sx) - Math.abs(this.startX));
		int y = Math.abs(Math.abs(this.sy) - Math.abs(this.startY));
		int z = Math.abs(Math.abs(this.sz) - Math.abs(this.startZ));
		return new XYZPos(x,y,z);
	}


	public XYZPos getXYZPos(){
		return new XYZPos(this.sx,this.sy,this.sz);
	}

	public boolean hasNext(){
		if(scanned){
			return false;
		}
		if(this.mode==Mode.REVERSE){
			if(this.sy < this.endY){
				return false;
			}
		}
		if(this.mode==Mode.START_TO_END){
			if(this.sy > this.endY){
				return false;
			}
		}
		if(this.mode==Mode.RANGE && this.sy > this.startY + rangeY){
			return false;
		}
		return true;
	}

	public boolean isAirBlock(){
		if(this.world!=null){
			return this.world.isAirBlock(new BlockPos(sx,sy,sz));
		}else{
			System.out.println("World is null(ScanHelper)");
			return false;
		}
	}
	public boolean isAirBlockUp(){
		if(this.world!=null){
			return this.world.isAirBlock(new BlockPos(sx,sy,sz).up());
		}else{
			System.out.println("World is null(ScanHelper)");
			return false;
		}
	}
	public boolean isEndSide(){
		if(mode ==Mode.START_TO_END){
			if(this.sx == this.endX)return true;
			if(this.sy == this.endY)return true;
			if(this.sz == this.endZ)return true;
		}
		return false;
	}

	//
	//	public boolean canSnowHere(boolean lightCheck){
	//		if(this.world!=null){
	//			return this.world.provider.canSnowAt(this.sx, this.sy, this.sz,lightCheck);
	//		}else{
	//			System.out.println("World is null(ScanHelper)");
	//			return false;
	//		}
	//	}
	//
	public boolean isOpaqueBlock(){
		if(this.world!=null){
			IBlockState state = this.world.getBlockState(this.getXYZPos());
			return state.getBlock().isOpaqueCube(state);
		}else{
			System.out.println("World is null(ScanHelper)");
			return false;
		}
	}

	public boolean isPlayerPos(){
		return ((int)this.entity.posX==this.sx)&&((int)this.entity.posY==this.sy)&&((int)this.entity.posZ==this.sz);
	}
	public boolean isSide(){
		if(mode ==Mode.START_TO_END){
			if(this.sx == this.startX)return true;
			if(this.sy == this.startY)return true;
			if(this.sz == this.startZ)return true;
			if(this.sx == this.endX)return true;
			if(this.sy == this.endY)return true;
			if(this.sz == this.endZ)return true;
		}
		return false;
	}
	public boolean isValidHeight(){
		if(this.sy>0 && this.sy<255){
			return true;
		}
		return false;
	}
	public ScanHelper next(){

//		HSLib.logger.trace("xyz", this.getXYZPos());
		if(this.requireFirst){
			this.requireFirst = false;
			return this;
		}

		if(!scanned){
			if(this.mode==Mode.REVERSE){
				this.sx -= 1;

				if(this.sx < this.endX){
					this.sx = this.startX;
					this.sz -=1;
				}
				if(this.sz < this.endZ){
					this.sz = this.startZ;
					this.sy -= 1;
				}
				if(this.sy < this.endY){
					this.scanned = true;
				}
				return this;
			}
			if(this.mode==Mode.START_TO_END){
				this.sx += 1;
				if(this.sx > this.endX){
					this.sx = this.startX;
					this.sz += 1;
				}
				if(this.sz > this.endZ){
					this.sz = this.startZ;
					this.sy += 1;
					//System.out.println(this.sy);
				}
				if(this.sy > this.endY){
					this.scanned = true;
				}
				return this;
			}
			this.sx += 1;
			if(this.sx > this.startX + range -1){
				this.sx = this.startX;
				this.sz += 1;
			}
			if(this.sz > this.startZ + range -1){

				this.sz = this.startZ;
				this.sy += 1;
				//System.out.println(this.sy);

			}

			if(this.sy > this.startY + rangeY -1){
				this.scanned = true;
			}


		}
		return this;
	}

	public void setBlockAll(final IBlockState block){

		this.stream().forEach(input -> input.setBlockHere(block));
	}

	//
	public void	setBlockHere(IBlockState par1){
		this.setBlockHere(par1,WorldHelper.SETBLOCK_FLAG_NOTIFY_ALL);
	}

	//
	//	public void	setBlockHereOffset(int offsetx,int offsety,int offsetz,Block par1){
	//		if(this.world!=null){
	//			this.world.setBlock(new BlockPos(thi),par1);
	//			return;
	//		}else{
	//			System.out.println("World is null(ScanHelper)");
	//			return;
	//		}
	//	}
	//
	//	public void	setBlockHereOffset(int offsetx,int offsety,int offsetz,Block par1,int par2, int par3){
	//		if(this.world!=null){
	//			this.world.setBlock(this.sx+offsetx, this.sy+offsety, this.sz+offsetz,par1,par2,par3);
	//			return;
	//		}else{
	//			System.out.println("World is null(ScanHelper)");
	//			return;
	//		}
	//	}
	//
	public void	setBlockHere(IBlockState par1,int par3){
		HSLib.logger.trace("xyz2", this.getXYZPos());
		if(this.world!=null){
			this.world.setBlockState(this.getXYZPos(),par1,par3);
			return;
		}else{
			System.out.println("World is null(ScanHelper)");
			return;
		}
	}

	public ScanHelper setWorld(World par1){
		this.world = par1;
		return this;
	}

	public Stream<ScanHelper> stream(){

		return StreamSupport.stream(Spliterators.spliteratorUnknownSize(this.setRequireFirst(), Spliterator.NONNULL), false).filter(in -> !in.scanned);
	}


	protected ScanHelper setRequireFirst(){
		this.requireFirst = true;
		this.cutEnd = true;
		return this;
	}

}
