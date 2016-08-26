package mods.hinasch.lib.world;

import java.util.ArrayList;
import java.util.List;

import mods.hinasch.lib.Pool;
import mods.hinasch.lib.SafeUpdateEventByInterval;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public abstract class AbstractConnectScanner extends SafeUpdateEventByInterval{

	//protected ArrayList<XYZPos> scheduledBreak;
	/**
	 * 限界付きの座標プール
	 */
	protected XYZPool scheduledBreakPool;
	protected List<IBlockState> compareBlock;
	/**
	 * 始点
	 */
	protected XYZPos startPoint;
//	protected WorldHelper helper;

	protected int index;
	protected World world;
	protected int length;
	/**
	 * これを見て終わるかどうか判断
	 */
	public boolean finish = false;

	public AbstractConnectScanner(World world,List<IBlockState> compareBlock,XYZPos startpoint,int length){
		this.compareBlock = compareBlock;
		this.scheduledBreakPool = new XYZPool(50);
		this.scheduledBreakPool.add(startpoint);
		this.startPoint = startpoint;
		this.length = length;
		this.world = world;
//		this.helper = new WorldHelper(world);
	}


	@Override
	public void loopByInterval(){

		if(this.index<this.length){
			List<XYZPos> listToRemove = new ArrayList();
			List<XYZPos> listToAdd = new ArrayList();
			for(XYZPos aPos:scheduledBreakPool){
				Block block = world.getBlockState(aPos).getBlock();
				//Block block = world.getBlock(pos.x, pos.y, pos.z);
				if(block!=Blocks.AIR && !scheduledBreakPool.isDummy(aPos)){

					this.onCheckScheduledPos(world, world.getBlockState(aPos), aPos);
				}

				listToRemove.add(aPos);
				for(XYZPos rotate:XYZPos.around){
					XYZPos rotatedPos = aPos.add(rotate);
					IBlockState ib = world.getBlockState(rotatedPos);
//					PairID roundBlockData = new PairID(ib.getBlock(),Block.getStateId(ib));
//					PairID pairCompare = new PairID(compareBlock);
//

					this.addToList(compareBlock, ib, rotatedPos, listToAdd);

//					if(pairCompare.equalsOrSameBlock(roundBlockData)){
//						listToAdd.add(addedPos);
//					}
//					if((compareBlock.getBlock() instanceof BlockRedstoneOre) && (roundBlockData.getBlockObject() instanceof BlockRedstoneOre)){
//						listToAdd.add(addedPos);
//					}

				}

			}
			//消去を同時にやると排他関係で怒られるので、分けてやる
			for(XYZPos rm:listToRemove){
				scheduledBreakPool.remove(rm);
			}

			for(XYZPos ad:listToAdd){
				scheduledBreakPool.add(ad);
			}


		}else{
			this.finish = true;
			return;
		}
		this.index += 1;


	}


	@Override
	public boolean hasFinished(){
		return this.finish;
	}

	public void addToList(List<IBlockState> blockToCompare,IBlockState checkBlock,XYZPos rotatedPos,List<XYZPos> listToAdd){

		for(IBlockState aBlockState:blockToCompare){
			if(aBlockState.getBlock()==checkBlock.getBlock()){
				listToAdd.add(rotatedPos);
			}
		}

		return;
	}

	abstract public void onCheckScheduledPos(World world,IBlockState currentBlock,XYZPos currentPos);

	public static class XYZPool extends Pool<XYZPos>{

		public XYZPool(int size) {
			super(size, new XYZPos(0,0,0));

		}

		@Override
		public boolean equalElements(XYZPos left,XYZPos right){
			return left.equalsInt(right);
		}
	}
}
