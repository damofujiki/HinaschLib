//package mods.hinasch.lib.particle;
//
//import net.minecraft.client.particle.IParticleFactory;
//import net.minecraft.client.particle.Particle;
//import net.minecraft.world.World;
//
//public class ParticleLeaves extends CustomParticle{
//
//	public ParticleLeaves(World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn,
//			double ySpeedIn, double zSpeedIn) {
//		super(worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn);
//		  this.setParticleTexture(ParticleTypeWrapper.Particles.LEAVES.getTextureAtlasSplite());
//	}
//
//    public static class Factory implements IParticleFactory{
//
//		@Override
//		public Particle getEntityFX(int particleID, World worldIn, double xCoordIn, double yCoordIn, double zCoordIn,
//				double xSpeedIn, double ySpeedIn, double zSpeedIn, int... p_178902_15_) {
//			// TODO 自動生成されたメソッド・スタブ
//			return new ParticleLeaves(worldIn, zSpeedIn, zSpeedIn, zSpeedIn, zSpeedIn, zSpeedIn, zSpeedIn);
//		}
//
//    }
//}
