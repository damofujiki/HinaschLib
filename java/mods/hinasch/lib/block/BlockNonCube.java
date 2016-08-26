package mods.hinasch.lib.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockRenderLayer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockNonCube extends Block {

	public BlockNonCube(Material materialIn) {
		super(materialIn);
		// TODO 自動生成されたコンストラクター・スタブ
	}
	@Override
    public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }


	@Override
    public boolean isFullCube(IBlockState state)
    {
        return false;
    }

	@Override
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer()
    {
		//半透明にしたい場合はトランスクルーエントに
        return BlockRenderLayer.CUTOUT;
    }


}
