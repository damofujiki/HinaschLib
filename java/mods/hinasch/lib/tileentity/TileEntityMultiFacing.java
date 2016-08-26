//package mods.hinasch.lib.tileentity;
//
//import mods.hinasch.lib.block.BlockDirectionalBase;
//import net.minecraft.entity.EntityLivingBase;
//import net.minecraft.entity.player.EntityPlayer;
//import net.minecraft.item.ItemStack;
//import net.minecraft.nbt.NBTTagCompound;
//import net.minecraft.network.NetworkManager;
//import net.minecraft.network.Packet;
//import net.minecraft.network.play.server.SPacketUpdateTileEntity;
//import net.minecraft.tileentity.TileEntity;
//import net.minecraft.util.EnumFacing;
//import net.minecraft.util.math.BlockPos;
//import net.minecraft.world.World;
//
//public class TileEntityMultiFacing extends TileEntity{
//
//    protected String orientation;
//    public static enum EnumFacingSetting {VERTICAL,HORIZONTAL,ALL};
//    protected static final String NBTKEY = "orientationName";
//
//    public TileEntityMultiFacing(){
//    	this.setOrientation(EnumFacing.NORTH);
//    }
//    @Override
//	public Packet getDescriptionPacket() {
//		NBTTagCompound compound = new NBTTagCompound();
//		this.writeToNBT(compound);
//        return new SPacketUpdateTileEntity(this.getPos(), 0, compound);
//	}
//
//
//	public EnumFacing getOrientation(){
//    	return EnumFacing.byName(this.orientation);
//    }
//	@Override
//    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt)
//    {
//		this.orientation = pkt.getNbtCompound().getString(NBTKEY);
//
//	}
//
//    @Override
//    public void readFromNBT(NBTTagCompound nbt)
//    {
//		super.readFromNBT(nbt);
//        if(nbt.hasKey(NBTKEY)){
//        	this.setOrientation(nbt.getString(NBTKEY));
//        }else{
//        	this.setOrientation(EnumFacing.NORTH.getName());
//        }
//    }
//
//
//	protected void setOrientation(String par1){
//    	this.orientation = par1;
//
//    }
//
//	public void setOrientation(EnumFacing face){
//		this.orientation = face.getName();
//	}
//
//	@Override
//    public void writeToNBT(NBTTagCompound nbt)
//    {
//		super.writeToNBT(nbt);
//        nbt.setString(NBTKEY, this.orientation);
//    }
//
//
//    public static void onBlockPlacedBy(World world,BlockPos pos, EntityLivingBase el, ItemStack is,EnumFacingSetting setting)
//    {
//
//    	EnumFacing ori = BlockDirectionalBase.determineOrientation(world, pos, el,true );
//
//    	switch(setting){
//    	case VERTICAL:
//    		ori = BlockDirectionalBase.getVerticalDirectionFromPitch(el.rotationPitch);
//    		break;
//    	case HORIZONTAL:
//    		break;
//    	case ALL:
//        	if(el instanceof EntityPlayer){
//        		EntityPlayer ep = (EntityPlayer) el;
//        		if(ep.isSneaking()){
//        			ori = BlockDirectionalBase.getVerticalDirectionFromPitch(ep.rotationPitch);
//        		}
//        	}
//        	break;
//    	}
//
//    	TileEntity te = world.getTileEntity(pos);
//    	if(te instanceof TileEntityMultiFacing){
//    		TileEntityMultiFacing multiFace = (TileEntityMultiFacing)te;
//    		multiFace.setOrientation(ori);
//    	}
//    }
//}
