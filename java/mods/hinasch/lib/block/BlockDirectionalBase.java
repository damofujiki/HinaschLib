package mods.hinasch.lib.block;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class BlockDirectionalBase {
//
//
//	public enum EnumSide{UP,DOWN,LEFT,RIGHT,FRONT,BACK};
//	public enum EnumDirectionalSetting{VERTICAL,HORIZONTAL,STRICT};
	/**
	 * 1000
	 */
	public static final int CAP_META = 8;
	/**
	 * 0111
	 */
	public static final int MASK_META =7;
//	protected Map<EnumSide,IIcon> iconMap;
//
//	public BlockDirectionalBase(Material p_i45401_1_) {
//		super(p_i45401_1_);
//		this.iconMap = new HashMap();
//		// TODO 自動生成されたコンストラクター・スタブ
//	}
//
//    @SideOnly(Side.CLIENT)
//    @Override
//    public IIcon getIcon(int side, int meta)
//    {
//    	ForgeDirection dir = ForgeDirection.getOrientation(side);
//    	ForgeDirection rotatedDir = ForgeDirection.getOrientation(side);
//    	int ori = meta & MASK_META;
//
//    	for(int i=0;i<ori;i++){
//    		rotatedDir = rotatedDir.getRotation(ForgeDirection.UP);
//    	}
//
//
//
//    	switch(dir){
//		case DOWN:
//			return this.getIconSide(EnumSide.DOWN, meta);
//		case EAST:
//			return this.getIconSide(this.getAssociatedSide(rotatedDir), meta);
//		case NORTH:
//			return this.getIconSide(this.getAssociatedSide(rotatedDir), meta);
//		case SOUTH:
//			return this.getIconSide(this.getAssociatedSide(rotatedDir), meta);
//		case UP:
//			return this.getIconSide(EnumSide.UP, meta);
//		case WEST:
//			return this.getIconSide(this.getAssociatedSide(rotatedDir), meta);
//		default:
//			break;
//
//    	}
//        return this.blockIcon;
//    }
//
//    public EnumSide getAssociatedSide(ForgeDirection dir){
//    	switch(dir){
//		case DOWN:
//			return EnumSide.DOWN;
//		case EAST:
//			return EnumSide.RIGHT;
//		case NORTH:
//			return EnumSide.BACK;
//		case SOUTH:
//			return EnumSide.FRONT;
//		case UP:
//			return EnumSide.UP;
//		case WEST:
//			return EnumSide.LEFT;
//		default:
//			break;
//
//    	}
//    	return EnumSide.FRONT;
//    }
//
//    public IIcon getIconSide(EnumSide dir,int meta){
//    	return this.iconMap.containsKey(dir) ? this.iconMap.get(dir) : this.blockIcon;
//    }

    public static int getDirectionFromYaw(float yaw){
    	return  MathHelper.floor_double((double)(yaw * 4.0F / 360.0F) + 0.5D) & 3;


    }

    public static EnumFacing determineOrientation(World w, BlockPos clickedBlock, EntityLivingBase entityIn,boolean isHorizontalOnly)
    {

    	if(!isHorizontalOnly){
            if (MathHelper.abs((float)entityIn.posX - (float)clickedBlock.getX()) < 2.0F && MathHelper.abs((float)entityIn.posZ - (float)clickedBlock.getZ()) < 2.0F)
            {
                double d0 = entityIn.posY + (double)entityIn.getEyeHeight();

                if (d0 - (double)clickedBlock.getY() > 2.0D)
                {
                    return EnumFacing.UP;
                }

                if ((double)clickedBlock.getY() - d0 > 0.0D)
                {
                    return EnumFacing.DOWN;
                }
            }
    	}

    	int l= 0;
    	if(isHorizontalOnly){
            return entityIn.getHorizontalFacing().getOpposite();
    	}


        return entityIn.getHorizontalFacing().getOpposite();
    }


    public static EnumFacing getVerticalDirectionFromPitch(float rotationPitch){
    	return rotationPitch < 0 ? EnumFacing.DOWN : EnumFacing.UP;
    }
}
