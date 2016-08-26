package mods.hinasch.lib.block;

import java.util.Random;
import java.util.function.Supplier;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDoor;
import net.minecraft.block.BlockDoor.EnumDoorHalf;
import net.minecraft.block.SoundType;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ComponentDoubleBlock {
	Supplier<Item> doorItemSupplier;
	public ComponentDoubleBlock(PropertyEnum<EnumDoorHalf> hALF, Block parent,Supplier<Item> doorItemSupplier) {
		super();
		HALF = hALF;
		this.parent = parent;
		this.doorItemSupplier = doorItemSupplier;
	}

	static PropertyEnum<BlockDoor.EnumDoorHalf> HALF;

	Block parent;

	public void neighborChanged(IBlockState state,World worldIn ,BlockPos pos, Block neighborBlock)
    {

        if (state.getValue(HALF) == BlockDoor.EnumDoorHalf.UPPER)
        {
            BlockPos downPos = pos.down();
            IBlockState downState = worldIn.getBlockState(downPos);

            if (downState.getBlock() != parent)
            {
                worldIn.setBlockToAir(pos);
            }
            else if (neighborBlock != parent)
            {
                parent.onNeighborChange(worldIn, pos, downPos);
            }
        }
        else
        {
            boolean flag1 = false;
            BlockPos upPos = pos.up();
            IBlockState upState = worldIn.getBlockState(upPos);

            if (upState.getBlock() != parent)
            {
                worldIn.setBlockToAir(pos);
                flag1 = true;
            }

            if (!worldIn.getBlockState(pos.down()).isFullyOpaque())
            {
                worldIn.setBlockToAir(pos);
                flag1 = true;

                if (upState.getBlock() == parent)
                {
                    worldIn.setBlockToAir(upPos);
                }
            }

            if (flag1)
            {
                if (!worldIn.isRemote)
                {
                    parent.dropBlockAsItem(worldIn, pos, state, 0);
                }
            }

        }
    }

    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        return state.getValue(HALF) == BlockDoor.EnumDoorHalf.UPPER ? null : this.doorItemSupplier.get();
    }

    public void onBlockHarvested(World worldIn, BlockPos pos, IBlockState state, EntityPlayer player)
    {
        BlockPos blockpos = pos.down();
        BlockPos blockpos1 = pos.up();

        if (player.capabilities.isCreativeMode && state.getValue(HALF) == BlockDoor.EnumDoorHalf.UPPER && worldIn.getBlockState(blockpos).getBlock() == parent)
        {
            worldIn.setBlockToAir(blockpos);
        }

        if (state.getValue(HALF) == BlockDoor.EnumDoorHalf.LOWER && worldIn.getBlockState(blockpos1).getBlock() == parent)
        {
            if (player.capabilities.isCreativeMode)
            {
                worldIn.setBlockToAir(pos);
            }

            worldIn.setBlockToAir(blockpos1);
        }
    }


    public EnumActionResult onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        if (facing != EnumFacing.UP)
        {
            return EnumActionResult.FAIL;
        }
        else
        {
            IBlockState iblockstate = worldIn.getBlockState(pos);
            Block block = iblockstate.getBlock();

            if (!block.isReplaceable(worldIn, pos))
            {
                pos = pos.offset(facing);
            }

            if (playerIn.canPlayerEdit(pos, facing, stack) && parent.canPlaceBlockAt(worldIn, pos))
            {
                EnumFacing enumfacing = EnumFacing.fromAngle((double)playerIn.rotationYaw);
                int i = enumfacing.getFrontOffsetX();
                int j = enumfacing.getFrontOffsetZ();
                boolean flag = i < 0 && hitZ < 0.5F || i > 0 && hitZ > 0.5F || j < 0 && hitX > 0.5F || j > 0 && hitX < 0.5F;
                placeDoor(worldIn, pos, enumfacing, parent, flag);
                SoundType soundtype = parent.getSoundType();
                worldIn.playSound(playerIn, pos, soundtype.getPlaceSound(), SoundCategory.BLOCKS, (soundtype.getVolume() + 1.0F) / 2.0F, soundtype.getPitch() * 0.8F);
                --stack.stackSize;
                return EnumActionResult.SUCCESS;
            }
            else
            {
                return EnumActionResult.FAIL;
            }
        }
    }

    public void placeDoor(World worldIn, BlockPos pos, EnumFacing facing, Block door, boolean p_179235_4_)
    {
        BlockPos blockpos = pos.offset(facing.rotateY());
        BlockPos blockpos1 = pos.offset(facing.rotateYCCW());
        int i = (worldIn.getBlockState(blockpos1).isNormalCube() ? 1 : 0) + (worldIn.getBlockState(blockpos1.up()).isNormalCube() ? 1 : 0);
        int j = (worldIn.getBlockState(blockpos).isNormalCube() ? 1 : 0) + (worldIn.getBlockState(blockpos.up()).isNormalCube() ? 1 : 0);
        boolean flag = worldIn.getBlockState(blockpos1).getBlock() == door || worldIn.getBlockState(blockpos1.up()).getBlock() == door;
        boolean flag1 = worldIn.getBlockState(blockpos).getBlock() == door || worldIn.getBlockState(blockpos.up()).getBlock() == door;

        if ((!flag || flag1) && j <= i)
        {
            if (flag1 && !flag || j < i)
            {
                p_179235_4_ = false;
            }
        }
        else
        {
            p_179235_4_ = true;
        }

        BlockPos blockpos2 = pos.up();



        IBlockState iblockstate =this.initBlockState(facing, door, p_179235_4_);
        worldIn.setBlockState(pos, iblockstate.withProperty(BlockDoor.HALF, BlockDoor.EnumDoorHalf.LOWER), 2);
        worldIn.setBlockState(blockpos2, iblockstate.withProperty(BlockDoor.HALF, BlockDoor.EnumDoorHalf.UPPER), 2);
        worldIn.notifyNeighborsOfStateChange(pos, door);
        worldIn.notifyNeighborsOfStateChange(blockpos2, door);
    }

    public IBlockState initBlockState(EnumFacing facing,Block door,boolean p_179235_4_){
        return door.getDefaultState();
    }

    public static interface IComponentDoubleBlock{
    	public ComponentDoubleBlock getComponentDoubleBlock();
    }
}
