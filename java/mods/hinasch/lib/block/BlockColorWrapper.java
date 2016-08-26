package mods.hinasch.lib.block;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public interface BlockColorWrapper extends IBlockColor{

	int getColor(IBlockState state, IBlockAccess p_186720_2_, BlockPos pos, int tintIndex);
	static IBlockColor of(BlockColorWrapper lambda){
		return lambda;
	}

	@Override
	default int colorMultiplier(IBlockState state, IBlockAccess p_186720_2_, BlockPos pos, int tintIndex) {
		// TODO 自動生成されたメソッド・スタブ
		return getColor(state, p_186720_2_, pos, tintIndex);
	}

}
