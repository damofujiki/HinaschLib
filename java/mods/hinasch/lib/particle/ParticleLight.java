//package mods.hinasch.lib.particle;
//
//import net.minecraft.client.particle.IParticleFactory;
//import net.minecraft.client.particle.Particle;
//import net.minecraft.world.World;
//
//public class ParticleLight extends CustomParticle{
//
//	public ParticleLight(World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn,
//			double ySpeedIn, double zSpeedIn) {
//		super(worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn);
//		  this.setParticleTexture(ParticleTypeWrapper.Particles.LIGHT.getTextureAtlasSplite());
//	}
//
//
//    public static class Factory implements IParticleFactory{
//
//		@Override
//		public Particle getEntityFX(int particleID, World worldIn, double xCoordIn, double yCoordIn, double zCoordIn,
//				double xSpeedIn, double ySpeedIn, double zSpeedIn, int... p_178902_15_) {
//			// TODO 自動生成されたメソッド・スタブ
//			return new ParticleLight(worldIn, zSpeedIn, zSpeedIn, zSpeedIn, zSpeedIn, zSpeedIn, zSpeedIn);
//		}
//
//    }
//}
