//package mods.hinasch.lib.particle;
//
//import java.util.Map;
//
//import com.google.common.collect.Maps;
//
//import net.minecraft.client.particle.IParticleFactory;
//
//public class CustomParticles {
//
//
//	static Map<String,IParticleFactory> map = Maps.newHashMap();
//
//
//	static{
//		map.put("bubble", new ParticleBubble.Factory());
//		map.put("leaves", new ParticleLeaves.Factory());
//		map.put("stone", new ParticleStone.Factory());
//		map.put("light", new ParticleLight.Factory());
//	}
//
//	public static IParticleFactory getParticleFactory(String name){
//		return map.get(name);
//	}
//}
