//package mods.hinasch.lib.particle;
//
//import net.minecraft.client.particle.IParticleFactory;
//import net.minecraft.client.particle.Particle;
//import net.minecraft.world.World;
//
//public class ParticleBubble extends CustomParticle{
//
//    protected ParticleBubble(World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn){
//		super(worldIn, zSpeedIn, zSpeedIn, zSpeedIn, zSpeedIn, zSpeedIn, zSpeedIn);
//        this.motionX = this.motionX * 0.009999999776482582D + xSpeedIn;
//        this.motionY = this.motionY * 0.009999999776482582D + ySpeedIn;
//        this.motionZ = this.motionZ * 0.009999999776482582D + zSpeedIn;
//        this.posX += (double)((this.rand.nextFloat() - this.rand.nextFloat()) * 0.05F);
//        this.posY += (double)((this.rand.nextFloat() - this.rand.nextFloat()) * 0.05F);
//        this.posZ += (double)((this.rand.nextFloat() - this.rand.nextFloat()) * 0.05F);
//        this.particleRed = this.particleGreen = this.particleBlue = 1.0F;
//        this.particleMaxAge = (int)(8.0D / (Math.random() * 0.8D + 0.2D)) + 4;
//
//
//        this.setParticleTexture(ParticleTypeWrapper.Particles.BUBBLE.getTextureAtlasSplite());
//	}
//
//
//
//    public static class Factory implements IParticleFactory{
//
//		@Override
//		public Particle getEntityFX(int particleID, World worldIn, double xCoordIn, double yCoordIn, double zCoordIn,
//				double xSpeedIn, double ySpeedIn, double zSpeedIn, int... p_178902_15_) {
//			// TODO 自動生成されたメソッド・スタブ
//			return new ParticleBubble(worldIn, zSpeedIn, zSpeedIn, zSpeedIn, zSpeedIn, zSpeedIn, zSpeedIn);
//		}
//
//    }
//}
