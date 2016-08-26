package mods.hinasch.lib.block;
//package com.hinasch.lib.base;
//
//import net.minecraft.block.BlockCrops;
//import net.minecraft.client.renderer.texture.IIconRegister;
//import net.minecraft.item.Item;
//import net.minecraft.util.IIcon;
//import cpw.mods.fml.relauncher.Side;
//import cpw.mods.fml.relauncher.SideOnly;
//
//public abstract class BlockCropBase extends BlockCrops{
//
//	protected IIcon[] icons;
//
//	
//	public BlockCropBase(){
//		super();
//		
//	}
//	
//	@Override
//    @SideOnly(Side.CLIENT)
//    public IIcon getIcon(int side, int meta)
//    {
//        if (meta < 7)
//        {
//            if (meta == 6)
//            {
//                meta = 5;
//            }
//
//            //ビットシフトでここは０～３になる
//            return this.icons[meta >> 1];
//        }
//        else
//        {
//            return this.icons[3];
//        }
//    }
//    
//	@Override
//    @SideOnly(Side.CLIENT)
//    public void registerBlockIcons(IIconRegister p_149651_1_)
//    {
//        this.icons = new IIcon[4];
//
//        for (int i = 0; i < this.icons.length; ++i)
//        {
//            this.icons[i] = p_149651_1_.registerIcon(this.getTextureName() + "_stage_" + i);
//        }
//    }
//    
//    /**
//     * 種を設定
//     */
//	@Override
//    protected Item func_149866_i()
//    {
//        return this.getSeedItem();
//    }
//
//    /**
//     * 作物を設定
//     */
//	@Override
//    protected Item func_149865_P()
//    {
//        return this.getCropItem();
//    }
//	
//	abstract public Item getSeedItem();
//	abstract public Item getCropItem();
//}
