package mods.hinasch.lib.particle;

import java.util.List;
import java.util.stream.Collectors;

import com.google.common.collect.Lists;

import mods.hinasch.lib.client.ClientHelper;
import mods.hinasch.lib.core.HSLib;
import mods.hinasch.lib.iface.IIntSerializable;
import mods.hinasch.lib.primitive.XY;
import mods.hinasch.lib.util.HSLibs;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.EnumParticleTypes;

/** バニラのparticleとこのmodのparticleどちらも包めるラッパー*/
public class ParticleTypeWrapper {


	//public static enum EnumUnsagaParticles {BUBBLE,LEAVES,STONE};
	protected EnumParticleTypes typeVanilla;
	protected Particles typeUnsaga;

	public ParticleTypeWrapper(EnumParticleTypes type){
		this.typeVanilla = type;
	}

	public ParticleTypeWrapper(Particles type){
		this.typeUnsaga = type;
	}

	public int getParticleID(){
		if(this.typeVanilla!=null){
			return this.typeVanilla.getParticleID();
		}
		if(this.typeUnsaga!=null){
			return typeUnsaga.getMeta();
		}
		return -1;
	}

	public boolean isVanillaParticle(){
		return this.typeVanilla!=null;
	}

//	public static Pair<Integer,Integer> getParticleIndexFromID(int particleID){
//		Pair<Integer,Integer> pair = Pair.of(0, 0);
//		for(EnumUnsagaParticles type:EnumUnsagaParticles.LOOKUP){
//			if(type.getParticleID()==particleID){
//				pair = Pair.of(type.getIndexX(),type.getIndexY());
//			}
//		}
//		return pair;
//	}


	public static enum Particles implements IIntSerializable{
		DUMMY("dummy",0,XY.of(0, 0)),BUBBLE("bubble",200,XY.of(0, 2)),LEAVES("leaves",201,XY.of(0, 0)),STONE("stone",202,XY.of(1, 0)),LIGHT("light",203,XY.of(2, 3));

		public static List<String> getJsonNames(){
			return Lists.newArrayList(Particles.values()).stream().map(in -> "iconParticle."+in.getParticleName()).sorted().collect(Collectors.toList());
		}
		private Particles(String name, int meta,XY location) {
			this.name = name;
			this.meta = meta;
			this.location = location;
		}

		public XY getParticleIndex(){
			return this.location;
		}
		private final XY location;
		private final String name;
		private int meta;
//		private final int particleID;
//		private final int indexX;
//		private final int indexY;
////		private final int iconMeta;
////		private final String jsonName;
//		public static final EnumUnsagaParticles[] LOOKUP;
//		static{
//			EnumUnsagaParticles[] types = values();
//			LOOKUP = new EnumUnsagaParticles[values().length];
//			int index = 0;
//			for(EnumUnsagaParticles type:types){
//				LOOKUP[index] = type;
//				index += 1;
//			}
//		}
//		private EnumUnsagaParticles(int id,int x,int y){
//			this.particleID = id;
//			this.indexX = x;
//			this.indexY = y;
////			this.iconMeta = meta;
////			this.jsonName = jsonname;
//		}
//
//		public int getParticleID(){
//			return this.particleID;
//		}
//		public int getIndexX(){
//			return this.indexX;
//		}
//
//		public int getIndexY(){
//			return this.indexY;
//		}

		public TextureAtlasSprite getTextureAtlasSplite(){
			HSLib.logger.trace(this.getParticleName(), "particleのtextureをセット");
			int itemmeta = this.getMeta() - 200;
			HSLib.logger.trace(this.getParticleName(), ClientHelper.getTextureAtlasSprite(HSLib.itemParticleIcon, itemmeta));
			return ClientHelper.getTextureAtlasSprite(HSLib.itemParticleIcon, itemmeta);
		}
		public static Particles fromMeta(int meta){
			return HSLibs.fromMeta(Particles.values(), meta);
		}
		public String getParticleName(){
			return this.name;
		}
		@Override
		public int getMeta() {
			// TODO 自動生成されたメソッド・スタブ
			return meta;
		}

//		public static String[] getJsonNames(){
//			String[] array1 = new String[LOOKUP.length];
//			int index = 0;
//			for(EnumUnsagaParticles type:LOOKUP){
//				array1[index] = type.getJsonName();
//				index+=1;
//			}
//			return array1;
//
//		}

	}
}
