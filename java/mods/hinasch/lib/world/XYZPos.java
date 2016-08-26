package mods.hinasch.lib.world;

import java.util.List;
import java.util.Random;

import com.google.common.collect.Lists;
import com.google.common.io.ByteArrayDataInput;

import io.netty.buffer.ByteBuf;
import mods.hinasch.lib.iface.INBTWritable;
import mods.hinasch.lib.primitive.Tuple;
import mods.hinasch.lib.util.UtilNBT;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;


public class XYZPos extends BlockPos implements INBTWritable{

//	public int x;
//	public int y;
//	public int z;
	public boolean sw = false;

	public static final String TAG_POS = "position";

	public static final XYZPos ZERO = new XYZPos(0,0,0);
//	public static final XYZPos EAST = new XYZPos(ForgeDirection.EAST);
//	public static final XYZPos WEST = new XYZPos(ForgeDirection.WEST);
//	public static final XYZPos SOUTH = new XYZPos(ForgeDirection.SOUTH);
//	public static final XYZPos NORTH = new XYZPos(ForgeDirection.NORTH);

//	public static final Set<XYZPos> AROUND_HORIZONTAL = Sets.newHashSet(EAST,WEST,NORTH,SOUTH);
	public static final List<XYZPos> around = Lists.newArrayList(new XYZPos(1,0,0),new XYZPos(-1,0,0),new XYZPos(0,1,0),new XYZPos(0,-1,0),new XYZPos(0,0,1),new XYZPos(0,0,-1));

	//TODO ゲッターをつけたほうがいい？
	public double dx;
	public double dy;
	public double dz;
	protected boolean isBlockPos = false;

//	public ForgeDirection dir;

	public XYZPos(BlockPos pos){
		this(pos.getX(),pos.getY(),pos.getZ());
	}
	public XYZPos(int par1,int par2,int par3){
		super(par1,par2,par3);
		dx=(double)par1;
		dy=(double)par2;
		dz=(double)par3;
		this.isBlockPos = true;
	}

//	public XYZPos(ForgeDirection dir){
//		this(dir.offsetX,dir.offsetY,dir.offsetZ);
//		this.dir = dir;
//	}

	public XYZPos(double par1,double par2,double par3){
		super(par1,par2,par3);
		dx=par1;
		dy=par2;
		dz=par3;
		this.isBlockPos = false;
	}
	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder();

		if(this.isBlockPos){
			sb.append(this.getX()).append(",").append(this.getY()).append(",").append(this.getZ());
		}else{
			sb.append(dx).append(",").append(dy).append(",").append(dz);
		}

		return new String(sb);

	}

	public void setAsBlockPos(boolean par1){
		this.isBlockPos = par1;
	}


	public static XYZPos strapOff(String par1){
		String[] str = par1.split(",");
		if(str.length<3)return null;
		XYZPos xyz = new XYZPos(Integer.parseInt(str[0]),Integer.parseInt(str[1]),Integer.parseInt(str[2]));
		return xyz;

	}


	public static XYZPos parseString(String par1){
		String[] str = par1.split(",");
		if(str.length<3)return null;
		XYZPos xyz = new XYZPos(Integer.parseInt(str[0]),Integer.parseInt(str[1]),Integer.parseInt(str[2]));
		return xyz;

	}

//	public XYZPos getDirectedPos(ForgeDirection dir){
//		return new XYZPos(this.x+dir.offsetX,this.y+dir.offsetY,this.z+dir.offsetZ);
//	}

	public static XYZPos fromMovingObjectPos(RayTraceResult mop){
		XYZPos xyz = new XYZPos(mop.getBlockPos());
		return xyz;
	}
	public static XYZPos createFrom(Entity en){
		XYZPos xyz = new XYZPos(en.getPosition());
		xyz.dx = en.posX;
		xyz.dy = en.posY;
		xyz.dz = en.posZ;
		return xyz;
	}
	public static XYZPos createFrom(Vec3d vec){
		XYZPos xyz = new XYZPos(vec.xCoord,vec.yCoord,vec.zCoord);
		xyz.dx = vec.xCoord;
		xyz.dy = vec.yCoord;
		xyz.dz = vec.zCoord;
		return xyz;
	}
	public static XYZPos tileEntityPosToXYZ(TileEntity en){
		XYZPos xyz = new XYZPos(en.getPos());
		return xyz;
	}

	public static Tuple<XYZPos,XYZPos> compareAndSwap(XYZPos par1,XYZPos par2){
		EditablePos newxyz = new EditablePos();
		EditablePos newxyze = new EditablePos();
		if(par1.getX()>par2.getX()){
			newxyz.setX(par2.getX());
			newxyze.setX(par1.getX());
		}else{
			newxyz.setX(par1.getX());
			newxyze.setX(par2.getX());
		}
		if(par1.getY()>par2.getY()){
			newxyz.setY(par2.getY());
			newxyze.setY(par1.getY());
		}else{
			newxyz.setY(par1.getY());
			newxyze.setY(par2.getY());
		}
		if(par1.getZ()>par2.getZ()){
			newxyz.setZ(par2.getZ());
			newxyze.setZ(par1.getZ());
		}else{
			newxyz.setZ(par1.getZ());
			newxyze.setZ(par2.getZ());
		}
		Tuple ret = new Tuple(newxyz.toXYZPos(),newxyze.toXYZPos());

		return ret;

	}

	public static class EditablePos {
		public MutableBlockPos pos;

		public EditablePos(){
			this.pos = new MutableBlockPos();
		}

		public void setX(int par1){
			pos.setPos(par1, pos.getY(), pos.getZ());
		}

		public void setY(int par1){
			pos.setPos(pos.getX(), par1, pos.getZ());
		}

		public void setZ(int par1){
			pos.setPos(pos.getX(), pos.getY(), par1);
		}
		public XYZPos toXYZPos(){
			return new XYZPos(pos.getX(),pos.getY(),pos.getZ());
		}
	}

	public XYZPos subtract(XYZPos par2){
		return new XYZPos(this.getX() - par2.getX(),this.getY() - par2.getY(),this.getZ() - par2.getZ());
	}

	public XYZPos add(XYZPos par2){
		return new XYZPos(this.getX() + par2.getX(),this.getY() + par2.getY(),this.getZ() + par2.getZ());
	}

	public XYZPos addPos(int px,int py,int pz){
		XYZPos newpos = this.add(new XYZPos(px,py,pz));
		return newpos;
	}

	public XYZPos addDouble(double ax,double ay,double az){
		XYZPos newpos = new XYZPos(this.dx,this.dy,this.dz);
		newpos.dx += ax;
		newpos.dy += ay;
		newpos.dz += az;
		return newpos;
	}

	//おもにデバグ用？
//	public void setBlockToHere(World world,Block blockid){
//		world.setBlock(this.x,this.y, this.z, blockid);
//	}

//	public void sync(){
//		this.dx = (double)this.x;
//		this.dy = (double)this.y;
//		this.dz = (double)this.z;
//	}

	public boolean equalsInt(XYZPos pos){
		if(this.getX()==pos.getX() && this.getY()==pos.getY() && this.getZ()==pos.getZ()){
			return true;
		}
		return false;
	}

	public static XYZPos buildFromByteArrayDataInput(ByteArrayDataInput data){

		int x = data.readInt();
		int y = data.readInt();
		int z = data.readInt();

		return new XYZPos(x,y,z);
	}

	public XYZPos getGaussian(Random rand){
		XYZPos xyz = new XYZPos(this.getX(),this.getY(),this.getZ());
		xyz.dx += rand.nextGaussian();
		xyz.dy += rand.nextGaussian();
		xyz.dz += rand.nextGaussian();
		return xyz;
	}

	/* integer only */
	public void writeToBuffer(ByteBuf buff){
		buff.writeInt(this.getX());
		buff.writeInt(this.getY());
		buff.writeInt(this.getZ());
	}

//	public double getDistanceTo(XYZPos pos){
//		Vec3 v1 = Vec3.createVectorHelper(this.dx,this.dy,this.dz);
//		Vec3 v2 = Vec3.createVectorHelper(pos.dx, pos.dy, pos.dz);
//		return v1.distanceTo(v2);
//	}
	public static XYZPos readFromBuffer(ByteBuf buf){
		int x = buf.readInt();
		int y = buf.readInt();
		int z = buf.readInt();
		return new XYZPos(x,y,z);
	}
	public static XYZPos readFromNBT(NBTTagCompound stream){
		NBTTagCompound child = stream.getCompoundTag(TAG_POS);

			int x = child.getInteger("x");
			int y = child.getInteger("y");
			int z = child.getInteger("z");
			return new XYZPos(x,y,z);
	}

	@Override
	public void writeToNBT(NBTTagCompound stream) {
		NBTTagCompound child = UtilNBT.createCompound();

		child.setInteger("x", getX());
		child.setInteger("y", getY());
		child.setInteger("z", getZ());
		stream.setTag(TAG_POS, child);
	}
}
