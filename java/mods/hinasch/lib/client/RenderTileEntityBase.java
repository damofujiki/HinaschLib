//package mods.hinasch.lib.client;
//
//import org.lwjgl.opengl.GL11;
//
//import mods.hinasch.lib.tileentity.TileEntityMultiFacing;
//import net.minecraft.block.Block;
//import net.minecraft.client.model.ModelBase;
//import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
//import net.minecraft.entity.Entity;
//import net.minecraft.tileentity.TileEntity;
//import net.minecraft.util.ResourceLocation;
//import net.minecraft.util.math.BlockPos;
//import net.minecraft.world.World;
//
//public class RenderTileEntityBase extends TileEntitySpecialRenderer{
//
//
//	protected ModelBase model;
//	public RenderTileEntityBase(){
//		this.model = this.getModel();
//	}
//
//	public ModelBase getModel(){
//		return null;
//	}
//
//	/**
//	 * これとgetDomain()を合わせてリソースローケーションを作る。
//	 * @return
//	 */
//	public String getTextureName(){
//		return "";
//	}
//
//	/**
//	 * ドメイン名だけ返せば良い。
//	 * @return
//	 */
//	public String getDomain(){
//		return "";
//	}
//
//	public ResourceLocation getTextureLocation(){
//		return new ResourceLocation(this.getDomain(),this.getTextureName());
//	}
//	@Override
//	public void renderTileEntityAt(TileEntity var1, double var2, double var4,
//			double var6, float var8,int var9) {
//
//		GL11.glPushMatrix();
//		GL11.glTranslatef((float)var2,(float)var4,(float)var6);
//		//GL11.glScalef((float)var8, (float)var8, (float)var8);
//		if(var1.getClass()==this.getTileEntityClass()){
//			renderTileEntity(var1, var1.getWorld() ,var1.getPos(), this.getBlock());
//
//		}
//		GL11.glPopMatrix();
//	}
//
//	public Class<? extends TileEntity> getTileEntityClass(){
//		return null;
//	}
//
//	public Block getBlock(){
//		return null;
//	}
//    public void renderTileEntity(TileEntity tl, World world, BlockPos pos, Block block) {
//
//    	GL11.glPushMatrix();
//    	GL11.glDisable(GL11.GL_CULL_FACE);
//    	this.bindTexture(this.getTextureLocation());
//    	GL11.glTranslatef(0.5F, 1.5F, 0.5F);
//        int dir = 0;
//        if(tl instanceof TileEntityMultiFacing){
//
//            switch(((TileEntityMultiFacing) tl).getOrientation()){
//            case NORTH:
//            	dir = 2;
//            	break;
//            case SOUTH:
//            	dir = 4;
//            	break;
//            case WEST:
//            	dir = 3;
//            	break;
//            case EAST:
//            	dir = 1;
//            	break;
//            default:
//            	dir = 0;
//            	break;
//            }
//
//        }
//
//    	GL11.glRotatef(-180, 1F, 0F, 0F); //反転
//    	GL11.glRotatef(dir*(-90F), 0F, 1.0F, 0F);
//
//    	this.preRender(tl, world, pos.getX(), pos.getY(), pos.getZ(), block);
//    	this.renderModel(tl, world, pos.getX(), pos.getY(), pos.getZ(), block);
//
//    	GL11.glPopMatrix();
//    }
//
//    public void renderModel(TileEntity tl, World world, int i, int j, int k, Block block){
//    	model.render((Entity)null, i, j, k, 0.0F, 0.0F, 0.0625F);
//    }
//    public void preRender(TileEntity tl, World world, int i, int j, int k, Block block){
//
//    }
//}
