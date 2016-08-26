//package mods.hinasch.lib.block;
//
//import mods.hinasch.lib.container.inventory.InventoryHandler;
//import mods.hinasch.lib.tileentity.TileEntityCustomChestBase;
//import mods.hinasch.lib.world.XYZPos;
//import net.minecraft.block.BlockContainer;
//import net.minecraft.block.material.Material;
//import net.minecraft.block.state.IBlockState;
//import net.minecraft.entity.player.EntityPlayer;
//import net.minecraft.inventory.IInventory;
//import net.minecraft.item.ItemStack;
//import net.minecraft.tileentity.TileEntity;
//import net.minecraft.util.EnumFacing;
//import net.minecraft.util.EnumHand;
//import net.minecraft.util.math.BlockPos;
//import net.minecraft.world.World;
//
//public class BlockCustomChestBase extends BlockContainer{
//
//	public boolean isItemSpread = true;
//
//	protected BlockCustomChestBase(Material p_i45386_1_) {
//		super(p_i45386_1_);
//		// TODO 自動生成されたコンストラクター・スタブ
//	}
//
//	@Override
//	public TileEntity createNewTileEntity(World var1, int var2) {
//		// TODO 自動生成されたメソッド・スタブ
//		return null;
//	}
//
//	@Override
//    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ)
//    {
//
//
//        if (worldIn.isRemote)
//        {
//            return true;
//        }
//        else
//        {
//        	TileEntity te = worldIn.getTileEntity(pos);
//        	openGUI(worldIn, te,playerIn,pos);
//
//            return true;
//        }
//    }
//
//	public void openGUI(World world,TileEntity te,EntityPlayer ep,BlockPos pos){
//    	if(te instanceof TileEntityCustomChestBase){
//            IInventory iinventory = (IInventory)te;
//
//                    if (iinventory != null)
//                    {
//                        ep.displayGUIChest(iinventory);
//                    }
//    	}
//	}
//
//    @Override
//    public void breakBlock(World worldIn, BlockPos pos, IBlockState state)
//    {
//        TileEntityCustomChestBase tileentitychest = (TileEntityCustomChestBase)worldIn.getTileEntity(pos);
//
//        if (tileentitychest != null && this.isItemSpread)
//        {
//
//        	InventoryHandler uinv = new InventoryHandler(tileentitychest);
//        	uinv.spreadChestContents(worldIn, tileentitychest, new XYZPos(pos));
//
//        }
//
//        super.breakBlock(worldIn, pos, state);
//    }
//}
