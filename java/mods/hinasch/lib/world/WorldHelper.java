package mods.hinasch.lib.world;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.mojang.realmsclient.util.Pair;

import mods.hinasch.lib.DebugLog;
import mods.hinasch.lib.primitive.FileObject;
import mods.hinasch.lib.util.HSLibs;
import mods.hinasch.lib.world.ScanHelper.ScanConsumer;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;

public class WorldHelper {

//	public static final XYZPos UP = new XYZPos(0,+1,0);
//	public static final XYZPos DOWN = new XYZPos(0,-1,0);


	public static final int SETBLOCK_FLAG_NO_NOTIFY = 0;
	public static final int SETBLOCK_FLAG_NOTIFY_NEIGHBOUR = 1; //onNeighbour..を呼ぶ
	public static final int SETBLOCK_FLAG_NOTIFY_CHUNKONLY = 2;
	public static final int SETBLOCK_FLAG_NOTIFY_ALL = 3;

//	public World world;;
//	public WorldHelper(World world){
//		this.world = world;
//	}
//
//	public boolean canSustainPlant(XYZPos pos,ForgeDirection dir,IPlantable plant){
//		return this.world.getBlock(pos.x, pos.y, pos.z).canSustainPlant(world, pos.x, pos.y, pos.z, dir, plant);
//	}
//	public void setBlock(XYZPos pos,PairID pairid){
//		this.setBlock(pos, pairid, SETBLOCK_FLAG_NOTIFY_ALL);
//
//	}
//
	public static boolean isSameBlock(IBlockState comp,IBlockState in){
		if(comp.getBlock()==in.getBlock()){
			if(comp.getBlock().getMetaFromState(comp)==in.getBlock().getMetaFromState(in)){
				return true;
			}
		}
		return false;
	}
	public static void setEntityPosition(Entity ent,XYZPos pos){
		ent.setPosition(pos.dx, pos.dy, pos.dz);
	}
	public static Explosion createExplosion(World world,Entity owner,XYZPos pos,float str,boolean destroyBlocki){
		return world.createExplosion(owner, pos.dx, pos.dy, pos.dz, str, destroyBlocki);
	}
	/**
	 * ブロックが破壊できるもので、2.0F以下のもの。
	 * @param pos
	 * @param pairid
	 * @return
	 */
	public static boolean isBlockHard(World world,XYZPos pos){
		if(getBlockHardness(world, pos)<0.0F){
			return true;
		}
		if(getBlockHardness(world,pos)>2.0F){
			return true;
		}
		return false;

	}

	public static Block getBlock(World world,XYZPos pos){
		return world.getBlockState(pos).getBlock();
	}

	public static Material getMaterial(World world,XYZPos pos){
		return getBlock(world,pos).getMaterial(world.getBlockState(pos));
	}
	public static float getBlockHardness(World world,XYZPos pos){
		return getBlock(world,pos).getBlockHardness(world.getBlockState(pos),world, pos);
	}

	public static XYZPos findNearMaterial(World world,Material material,XYZPos pos,int range){
		ScanHelper scan = new ScanHelper(pos.getX(),pos.getY(),pos.getZ(),range,range);
		scan.setWorld(world);
		for(;scan.hasNext();scan.next()){
			if(scan.getBlock().getMaterial(scan.getBlockState())==material){
				return scan.getXYZPos();
			}
		}
		return null;
	}

	public static XYZPos findNear(World world,Block block,XYZPos pos,int range,CustomCheckWH checker){
		ScanHelper scan = new ScanHelper(pos.getX(),pos.getY(),pos.getZ(),range,range).setWorld(world);
		for(;scan.hasNext();scan.next()){
			if(checker.apply(world,pos, block, scan)){
				return scan.getXYZPos();
			}
		}
		return null;
	}

	public static List<XYZPos> findNear(final World world,final XYZPos pos,int range,int rangeY,final ICustomChecker custom){
		final ScanHelper scan = new ScanHelper(pos.getX(),pos.getY(),pos.getZ(),range,rangeY).setWorld(world);
		return scan.stream().flatMap(input ->{
			if(custom.apply(world, pos, input)){
				return Stream.of(pos);
			}
			return Stream.empty();
		}).collect(Collectors.toList());
//		return scan.asStream(new Function<ScanHelper,XYZPos>(){
//
//			@Override
//			public XYZPos apply(ScanHelper input) {
//				if(custom.apply(world, pos, scan)){
//					return pos;
//				}
//				return null;
//			}}
//		);
	}

	public static interface ICustomChecker{
		public boolean apply(World world,XYZPos pos,ScanHelper scan);
	}
	public static class CustomCheckWH{

		public boolean apply(World world,XYZPos pos,Block block,ScanHelper scan){
			return false;
		}
	}


	public static XYZPos getMapLength(FileObject pasteTempFile){
		int lenX = Integer.parseInt(pasteTempFile.read().get());
		int lenY = Integer.parseInt(pasteTempFile.read().get());
		int lenZ = Integer.parseInt(pasteTempFile.read().get());

		return new XYZPos(lenX,lenY,lenZ);

	}

	public static abstract class LoadHook implements BiConsumer<World,Pair<XYZPos,XYZPos>>{

		/**
		 * World,Pair(startPos,endPos)
		 */
		public abstract void accept(World world, Pair<XYZPos, XYZPos> positionsBox);

	}


	public static void loadMapToWorld(XYZPos pastePos,FileObject pasteTempFile,World world,LoadHook hook
			,final DebugLog... logger){
		pasteTempFile.openForInputStream();
		Consumer<String> logging = str->{
			if(logger!=null && logger.length>=1){
				logger[0].log(str);
			}
		};
//		Consumer<String> logging = new Consumer<String>(){
//
//			@Override
//			public void accept(String str) {
//				if(logger!=null && logger.length>=1){
//					logger[0].log(str);
//				}
//			}
//		};
		long size = 0;
		try {
			size = pasteTempFile.getChannel().size();
		} catch (IOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}



		logging.accept("size:"+size);
		if(size<=0){
			return;
		}
		//ファイルの容量分だけのByteBufferを作る
		ByteBuffer buffer = ByteBuffer.allocate((int) size);
		//bufferにファイルから読み込む
		pasteTempFile.readBuffer(buffer);
		//読み込んだ時点でpositionが最後に到達している？のでここでposition=0,limit=positionにする
		buffer.flip();
		//ヘッダーを読み込む(MaxSize)
		int mx = buffer.getInt();
		int my = buffer.getInt();
		int mz = buffer.getInt();
		logging.accept(String.format("mx:%d my:%d mz:%d", mx,my,mz));
//		int mx = Integer.parseInt(pasteTempFile.read().get());
//		int my = Integer.parseInt(pasteTempFile.read().get());
//		int mz = Integer.parseInt(pasteTempFile.read().get());
		XYZPos endPoint = pastePos.add(new XYZPos(mx,my,mz));
		if(hook!=null){
			hook.accept(world,Pair.of(pastePos,endPoint));
//			hook.invoke(world, pastePos, endPoint);
		}


		boolean flag = false;
		XYZPos shift = new XYZPos(0,0,0);
		//制限つき
		for(int stopper = 0; !flag || stopper<400;stopper += 1){
			//Optional<String> line = pasteTempFile.read();
			if(!buffer.hasRemaining()){
				flag = true;
			}else{
				//String[] data = line.get().split(",");
				//Iterable<String> data = Splitter.on(",").split(line.get());
				//System.out.println(data.toString());
				//PairID pair = new PairID();

				//順番に読み込む blockid,stateid
				int blockid = buffer.getInt();
				int stateid = buffer.getInt();
				//CreativeToolMod.logger.log(blockid,stateid);
				IBlockState state = Block.getBlockById(blockid).getStateFromMeta(stateid);
				world.setBlockState(pastePos.add(shift), state);
				shift = shift.add(new XYZPos(1,0,0));

				//XがラップしたらZを＋１
				if(shift.getX() > mx){
					//Zを足した後Xを頭に戻す
					shift = shift.add(new XYZPos(0,0,1));
					shift = new XYZPos(0,shift.getY(),shift.getZ());
				}
				//ZがラップしたらYを＋１
				if(shift.getZ() > mz){
					shift = shift.add(new XYZPos(0,1,0));
					shift = new XYZPos(shift.getX(),shift.getY(),0);

				}
				//Yも終わったら終了
				if(shift.getY() >my){
					flag = true;
				}


			}

		}
	}


	public static int getHeightFromPos(World world,XYZPos pos){
		if(pos.getY()<0){
			return 0;
		}
		if(world.isAirBlock(pos)){
			return getHeightFromPos(world,new XYZPos(pos.getX(),pos.getY()-1,pos.getZ()));
		}
		return pos.getY();

	}
	public static void saveMapToTemp(FileObject fileobj,ScanHelper scan,World world){
		//String mcdir = Minecraft.getMinecraft().mcDataDir.getAbsolutePath();
		//FileObject fileobj = new FileObject(mcdir+"\\creativeitem_temp.txt");
//		final PairIDList listPair =  new PairIDList();
//		fileobj.openForOutput();
		fileobj.openForOutputStream();

//		final ClearableJoiner joiner = new ClearableJoiner(",");
		int mx = scan.endX - scan.startX;
		int my = scan.endY - scan.startY;
		int mz = scan.endZ - scan.startZ;
//		fileobj.write(String.valueOf(mx)+"\r\n");
//		fileobj.write(String.valueOf(my)+"\r\n");
//		fileobj.write(String.valueOf(mz)+"\r\n");

		//header 12byte
		//本体 mx*my*mz*8(id=4byte stateid=4byte)
		final int size = 12 + ((mx+1)*(my+1)*(mz+1)*8);
		final ByteBuffer buffer = ByteBuffer.allocate(size+256);

		//ヘッダ
		buffer.putInt(mx).putInt(my).putInt(mz);

		scan.setWorld(world);
		scan.asStream(new ScanConsumer(){

			@Override
			public void accept(ScanHelper scan) {
				//blockSet.getFromWorld(world, scan.sx, scan.sy, scan.sz);
				IBlockState blockSet = scan.getBlockState();
//				listPair.addStack(new PairID(blockSet.getBlock(),blockSet.getBlock().getStateId(blockSet)), 1);
//				String joined = joiner.get().join(Block.getIdFromBlock(blockSet.getBlock()),blockSet.getBlock().getStateId(blockSet));
				int blockid = Block.getIdFromBlock(blockSet.getBlock());
				int stateid =HSLibs.getMetaIDFromBlock(blockSet);
				buffer.putInt(blockid);
				buffer.putInt(stateid);
				//CreativeToolMod.logger.log(blockid,stateid);
				//CreativeToolMod.logger.log(buffer.position()+"/"+size);
				if(scan.isEndSide()){
					//				if(temp.substring(temp.length()-1).equals(",")){
					//					temp = temp.substring(0, temp.length()-2);
					//
					//				}
//					fileobj.write(joined+"\r\n");
//					joiner.clear();
				}
			}}
		);

		buffer.flip();
		fileobj.writeBuffer(buffer);

		fileobj.close();
//		return listPair;
	}

	public static EntityItem getNewEntityItem(World world,XYZPos pos, ItemStack itemStack){
		return new EntityItem(world,pos.dx,pos.dy,pos.dz,itemStack);
	}
	public static boolean isClient(World world){
		return world.isRemote;
	}

	public static boolean  isServer(World world){
		return !world.isRemote;
	}

	/**
	 * "server" or "client"
	 * @param world
	 * @return
	 */
	public static String debug(World world){
		if(world==null){
			return "world is null!!!";
		}
		return world.isRemote ? "client" : "server";
	}
	public static Biome getBiomeGenForCoords(World world,BlockPos blockPos) {

		return world.getBiomeGenForCoords(blockPos);
	}

	public static String getSide(World world){
		return isServer(world) ? "Server" : "Client";
	}
}
