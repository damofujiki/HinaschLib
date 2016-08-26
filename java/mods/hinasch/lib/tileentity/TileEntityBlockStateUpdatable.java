package mods.hinasch.lib.tileentity;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public abstract class TileEntityBlockStateUpdatable extends TileEntity{


	public abstract Block getParentBlock();

	@Override
    public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newState)
    {
		//true=tileentityが破壊される false==破壊しない
		return !(oldState.getBlock() instanceof BlockTileEntityShouldReflesh);

    }

	public static interface BlockTileEntityShouldReflesh{

	}
}
