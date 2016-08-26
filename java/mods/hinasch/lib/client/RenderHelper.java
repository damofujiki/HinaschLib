package mods.hinasch.lib.client;

import org.lwjgl.opengl.GL11;

import mods.hinasch.lib.world.XYZPos;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;


public class RenderHelper {

	FontRenderer fontRenderer;
	RenderManager renderManager;

	protected RenderHelper(FontRenderer f,RenderManager r){

		this.fontRenderer = f;
		this.renderManager = r;
	}

	public static RenderHelper create(FontRenderer f,RenderManager r){
		return new RenderHelper(f, r);
	}
	/*
	 *
	 * renderPos = ワールド座標でなく画面上のレンダー座標。
	 */
	@SideOnly(Side.CLIENT)
	public void renderStringAt(Entity entity, String str, XYZPos renderPos, int dist,int color)
	{

	    double d3 = entity.getDistanceSqToEntity(renderManager.renderViewEntity);

	    Tessellator tessellator = Tessellator.getInstance();
	    double x = renderPos.dx;
	    double y = renderPos.dy;
	    double z = renderPos.dz;
	    if (d3 <= (double)(dist * dist))
	    {
	        FontRenderer fontrenderer = fontRenderer;
	        float f = 1.6F;
	        float f1 = 0.016666668F * f;
	        GL11.glPushMatrix();
	        GL11.glTranslatef((float)x + 0.0F, (float)y + entity.height + 0.5F, (float)z);
	        GL11.glNormal3f(0.0F, 1.0F, 0.0F);
	        GL11.glRotatef(-renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
	        GL11.glRotatef(renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
	        GL11.glScalef(-f1, -f1, f1);
	        GL11.glDisable(GL11.GL_LIGHTING);
	        GL11.glDepthMask(false);
	        GL11.glDisable(GL11.GL_DEPTH_TEST);
	        GL11.glEnable(GL11.GL_BLEND);
	        OpenGlHelper.glBlendFunc(770, 771, 1, 0);
	        VertexBuffer worldRenderer = Tessellator.getInstance().getBuffer();
	        byte b0 = 0;


	        GL11.glDisable(GL11.GL_TEXTURE_2D);
	        int j = fontrenderer.getStringWidth(str) / 2;
//	        worldRenderer.startDrawingQuads();
	        worldRenderer.begin(7, DefaultVertexFormats.POSITION_TEX_NORMAL); //これがないとヌルぽ
	        worldRenderer.color(0.0F, 0.0F, 0.0F, 0.25F);
	        worldRenderer.pos((double)(-j - 1), (double)(-1 + b0), 0.0D);
	        worldRenderer.pos((double)(-j - 1), (double)(8 + b0), 0.0D);
	        worldRenderer.pos((double)(j + 1), (double)(8 + b0), 0.0D);
	        worldRenderer.pos((double)(j + 1), (double)(-1 + b0), 0.0D);
			tessellator.draw();
	        GL11.glEnable(GL11.GL_TEXTURE_2D);
	        fontrenderer.drawString(str, -fontrenderer.getStringWidth(str) / 2, b0, color);
	        GL11.glEnable(GL11.GL_DEPTH_TEST);
	        GL11.glDepthMask(true);
	        fontrenderer.drawString(str, -fontrenderer.getStringWidth(str) / 2, b0, -1);
	        GL11.glEnable(GL11.GL_LIGHTING);
	        GL11.glDisable(GL11.GL_BLEND);
	        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	        GL11.glPopMatrix();
	    }
	}

//	@SideOnly(Side.CLIENT)
//	public static void spawnParticleHappy(World par0World, int par1, int par2, int par3, int par4,Random itemRand)
//	{
//		IBlockState block = par0World.getBlockState(par1, par2, par3);
//
//		if (par4 == 0)
//		{
//			par4 = 15;
//		}
//
//		//Block block = i1 > 0 && i1 < Block.blocksList.length ? Block.blocksList[i1] : null;
//
//		if (block != null)
//		{
//			block.setBlockBoundsBasedOnState(par0World, par1, par2, par3);
//
//			for (int j1 = 0; j1 < par4; ++j1)
//			{
//				double d0 = itemRand.nextGaussian() * 0.02D;
//				double d1 = itemRand.nextGaussian() * 0.02D;
//				double d2 = itemRand.nextGaussian() * 0.02D;
//				par0World.spawnParticle("happyVillager", (double)((float)par1 + itemRand.nextFloat()), (double)par2 + (double)itemRand.nextFloat() * block.getBlockBoundsMaxY(), (double)((float)par3 + itemRand.nextFloat()), d0, d1, d2);
//			}
//		}
//		else
//		{
//			for (int j1 = 0; j1 < par4; ++j1)
//			{
//				double d0 = itemRand.nextGaussian() * 0.02D;
//				double d1 = itemRand.nextGaussian() * 0.02D;
//				double d2 = itemRand.nextGaussian() * 0.02D;
//				par0World.spawnParticle("happyVillager", (double)((float)par1 + itemRand.nextFloat()), (double)par2 + (double)itemRand.nextFloat() * 1.0f, (double)((float)par3 + itemRand.nextFloat()), d0, d1, d2);
//			}
//		}
//	}

	public static void putSmokeParticle(World world,double x,double y,double z,int sx,int sy,int sz){
		double d0 = (double)((float)sx + world.rand.nextFloat());
		double d1 = (double)((float)sy + world.rand.nextFloat());
		double d2 = (double)((float)sz + world.rand.nextFloat());
		double d3 = d0 - x;
		double d4 = d1 - y;
		double d5 = d2 - z;
		double d6 = (double)MathHelper.sqrt_double(d3 * d3 + d4 * d4 + d5 * d5);
		d3 /= d6;
		d4 /= d6;
		d5 /= d6;
		double d7 = 0.5D / (d6 / (double)1.0D+ 0.1D);
		d7 *= (double)(world.rand.nextFloat() * world.rand.nextFloat() + 0.3F);
		d3 *= d7;
		d4 *= d7;
		d5 *= d7;
		world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0, d1, d2, d3, d4, d5);
	}
	//    public static MovingObjectPosition getMovingObjectPosition(World par1World, EntityPlayer par2EntityPlayer, boolean par3)
	//    {
	//        float f = 1.0F;
	//        float f1 = par2EntityPlayer.prevRotationPitch + (par2EntityPlayer.rotationPitch - par2EntityPlayer.prevRotationPitch) * f;
	//        float f2 = par2EntityPlayer.prevRotationYaw + (par2EntityPlayer.rotationYaw - par2EntityPlayer.prevRotationYaw) * f;
	//        double d0 = par2EntityPlayer.prevPosX + (par2EntityPlayer.posX - par2EntityPlayer.prevPosX) * (double)f;
	//        double d1 = par2EntityPlayer.prevPosY + (par2EntityPlayer.posY - par2EntityPlayer.prevPosY) * (double)f + 1.62D - (double)par2EntityPlayer.yOffset;
	//        double d2 = par2EntityPlayer.prevPosZ + (par2EntityPlayer.posZ - par2EntityPlayer.prevPosZ) * (double)f;
	//        Vec3 vec3 = par1World.getWorldVec3Pool().getVecFromPool(d0, d1, d2);
	//        float f3 = MathHelper.cos(-f2 * 0.017453292F - (float)Math.PI);
	//        float f4 = MathHelper.sin(-f2 * 0.017453292F - (float)Math.PI);
	//        float f5 = -MathHelper.cos(-f1 * 0.017453292F);
	//        float f6 = MathHelper.sin(-f1 * 0.017453292F);
	//        float f7 = f4 * f5;
	//        float f8 = f3 * f5;
	//        double d3 = 5.0D;
	//        if (par2EntityPlayer instanceof EntityPlayerMP)
	//        {
	//            d3 = ((EntityPlayerMP)par2EntityPlayer).theItemInWorldManager.getBlockReachDistance();
	//        }
	//        Vec3 vec31 = vec3.addVector((double)f7 * d3, (double)f6 * d3, (double)f8 * d3);
	//        return par1World.rayTraceBlocks_do_do(vec3, vec31, par3, !par3);
	//    }

}
