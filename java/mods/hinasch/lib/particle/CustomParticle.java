//package mods.hinasch.lib.particle;
//
//import mods.hinasch.lib.core.HSLib;
//import net.minecraft.client.Minecraft;
//import net.minecraft.client.particle.Particle;
//import net.minecraft.client.renderer.GlStateManager;
//import net.minecraft.client.renderer.Tessellator;
//import net.minecraft.client.renderer.VertexBuffer;
//import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
//import net.minecraft.entity.Entity;
//import net.minecraft.util.ResourceLocation;
//import net.minecraft.util.math.MathHelper;
//import net.minecraft.world.World;
//import net.minecraftforge.fml.relauncher.Side;
//import net.minecraftforge.fml.relauncher.SideOnly;
//
//@SideOnly(Side.CLIENT)
//public class CustomParticle extends Particle{
//
//
//	private static ResourceLocation loc = new ResourceLocation(HSLib.MODID, "textures/particles/custom.png");
//
//	public CustomParticle(World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn,
//			double ySpeedIn, double zSpeedIn) {
//		super(worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn);
//		// TODO 自動生成されたコンストラクター・スタブ
//	}
////    @Override
////    public int getFXLayer()
////    {
////        return Statics.FX_LAYER_BLOCKS_MAP;
////    }
//
//    public void moveEntity(double x, double y, double z)
//    {
//        this.setEntityBoundingBox(this.getEntityBoundingBox().offset(x, y, z));
//        this.resetPositionToBB();
//    }
//  @Override
//  public void renderParticle(VertexBuffer worldRenderer, Entity e, float f1, float f2, float f3, float f4, float f5, float f6) {
//
//	  HSLib.logger.trace(this.getClass().getName(), "call");
//      Minecraft.getMinecraft().getTextureManager().bindTexture(loc);
//      GlStateManager.enableBlend();
//      GlStateManager.blendFunc(770, 771);
//      worldRenderer.begin(7, DefaultVertexFormats.PARTICLE_POSITION_TEX_COLOR_LMAP);
//      super.renderParticle(worldRenderer, e, f1, f2, f3, f4, f5, f6);
//      Tessellator.getInstance().draw();
//      GlStateManager.disableBlend();
//      GlStateManager.enableLighting();
//  }
//  @Override
//  public int getFXLayer() {
//      return 3; // THE IMPORTANT PART
//  }
//	public void setParticleTextureIndex(int x,int y){
//	this.particleTextureIndexX = x;
//	this.particleTextureIndexY = y;
//}
//
//    public int getBrightnessForRender(float p_189214_1_)
//    {
//        float f = ((float)this.particleAge + p_189214_1_) / (float)this.particleMaxAge;
//        f = MathHelper.clamp_float(f, 0.0F, 1.0F);
//        int i = super.getBrightnessForRender(p_189214_1_);
//        int j = i & 255;
//        int k = i >> 16 & 255;
//        j = j + (int)(f * 15.0F * 16.0F);
//
//        if (j > 240)
//        {
//            j = 240;
//        }
//
//        return j | k << 16;
//    }
//
//    public void onUpdate()
//    {
//        this.prevPosX = this.posX;
//        this.prevPosY = this.posY;
//        this.prevPosZ = this.posZ;
//
//        if (this.particleAge++ >= this.particleMaxAge)
//        {
//            this.setExpired();
//        }
//
//        this.moveEntity(this.motionX, this.motionY, this.motionZ);
//        this.motionX *= 0.9599999785423279D;
//        this.motionY *= 0.9599999785423279D;
//        this.motionZ *= 0.9599999785423279D;
//
//        if (this.isCollided)
//        {
//            this.motionX *= 0.699999988079071D;
//            this.motionZ *= 0.699999988079071D;
//        }
//    }
//}
