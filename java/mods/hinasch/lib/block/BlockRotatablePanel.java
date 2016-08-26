package mods.hinasch.lib.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public class BlockRotatablePanel extends BlockNonCube{

	public static final PropertyBool ROTATED = PropertyBool.create("rotated");

	public BlockRotatablePanel(Material materialIn) {
		super(materialIn);
		// TODO 自動生成されたコンストラクター・スタブ
	}

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
    	double width = 1.0D;
    	if(state.getValue(ROTATED)){
    		return new AxisAlignedBB(0.5D-(width/2),0.0D,0.0D,0.5D+(width/2),1.0D,1.0D);
    	}
		return new AxisAlignedBB(0.0D,0.0D,0.5D-(width/2),1.0D,1.0D,0.5D+(width/2));
    }
}
