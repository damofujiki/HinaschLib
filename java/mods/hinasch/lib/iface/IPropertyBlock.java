package mods.hinasch.lib.iface;

import net.minecraft.block.state.IBlockState;

//参照用
public interface IPropertyBlock {


    public IBlockState getStateFromMeta(int meta);

    public int getMetaFromState(IBlockState state);

    public IBlockState createBlockState();

}
