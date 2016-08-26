package mods.hinasch.lib.world;

import java.util.List;
import java.util.Set;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import mods.hinasch.lib.world.EnvironmentalManager.EnvironmentalCondition.Type;
import mods.hinasch.unsaga.UnsagaMod;
import mods.hinasch.unsaga.util.addon.hac.HeatAndClimateAgent;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;

/** minecraftに環境？体調？の概念を入れる、まだあついさむいぐらいしか概念がない*/
public class EnvironmentalManager {

	/**
	 * 環境に関するやつ。
	 *アンサガmodでの自動回復や就寝時に使う。
	 *
	 */
	public static class EnvironmentalCondition{
		public static enum Type {COLD("condition.cold"),HOT("condition.hot"),SAFE("condition.safe"),HUMID("condition.humid");

			String name;
			private Type(String name){
				this.name = name;
			}
			public String getName() {
				return name;
			}
		}
		/**
		 * 通常の環境
		 * @return
		 */
		public static EnvironmentalCondition getSafeEnvironment(float temp,float humid){
			return new EnvironmentalCondition(false,Type.SAFE, temp, humid);
		}

		public boolean isHarsh;

		float temp;
		float humid;
		public Type result;;
		public EnvironmentalCondition(boolean isHarsh,Type en,float temp,float humid){
			this.isHarsh = isHarsh;
			this.result = en;
			if(this.result==null){
				this.result = Type.SAFE;
			}
			this.temp = temp;
			this.humid = humid;
		}
		public float getHumid() {
			return humid;
		}

		public float getTemp() {
			return temp;
		}

		public Type getType() {
			return result;
		}
	}
	public static class EventContainer{

		EntityLivingBase living;
		float humid;
		float temp;
		XYZPos pos;
		Biome biome;
		World world;
		public EventContainer(float humid, float temp, XYZPos pos, Biome biome, World world,EntityLivingBase living) {
			super();
			this.humid = humid;
			this.temp = temp;
			this.pos = pos;
			this.biome = biome;
			this.world = world;
			this.living = living;
		}
		public Biome getBiome() {
			return biome;
		}
		public float getHumid() {
			return humid;
		}
		public EntityLivingBase getLiving() {
			return living;
		}
		public XYZPos getPos() {
			return pos;
		}
		public float getTemp() {
			return temp;
		}
		public World getWorld() {
			return world;
		}
		public void setHumid(float humid) {
			this.humid = humid;
		}
		public void setTemp(float temp) {
			this.temp = temp;
		}
	}

	public static interface IEventHook{

		public void onCheckEnvironmental(EventContainer container);
	}

	public static List<IEventHook> hooks = Lists.newArrayList();
	public static Set<Block> hotBlocks = Sets.newHashSet(Blocks.LIT_FURNACE,Blocks.FIRE,Blocks.LAVA,Blocks.FLOWING_LAVA);
//	public static final WorldHelper.CustomCheckWH checkFurnace = new CustomCheckWH(){
//		@Override
//		public boolean apply(World parent,XYZPos pos,Block block,ScanHelper scan){
//			if(hotBlocks.contains(WorldHelper.getBlock(parent,scan.getXYZPos()))){
//				return true;
//			}
//			return false;
//		}
//	};

	public static EnvironmentalCondition getCondition(World w,XYZPos pos,Biome biome,EntityLivingBase living){

		if(UnsagaMod.externalAPI.isLoadedClimateAPI()){
			return HeatAndClimateAgent.getCondition(w, pos, biome, living);
		}
		List biomeList = BiomeHelper.getBiomeTypeList(biome);

		float temp = biome.getFloatTemperature(pos);
		float humid = biome.getRainfall();
		//boolean isNight = world.getBlockLightValue(pos.x,pos.y, pos.z) < 7;
		List<XYZPos> list = WorldHelper.findNear(w, pos, 5, 3,(world,posIn,scan)->{

			if(!scan.isAirBlock()){
				if(hotBlocks.contains(scan.getBlock())){
					return true;
				}
				if(scan.getBlockState().getMaterial()==Material.LAVA){
					return true;
				}
				if(scan.getBlockState().getMaterial()==Material.FIRE){
					return true;
				}
			}
			return false;
		});
		if(list.size()>=1){
			if(list.size()>=2){
				temp += 0.5F;
			}else{
				temp += 0.3F;
			}

		}

		if(!w.canBlockSeeSky(pos)){
			temp -= 0.1F;
		}
		if(w.getWorldInfo().getWorldTime()>13000){

			temp -= 0.3F;

		}
		if(w.isRaining()){
			humid += 0.5F;
			temp -= 0.5F;
		}


		for(IEventHook hook:hooks){
			EventContainer container = new EventContainer(humid, temp, pos, biome, w, living);
			hook.onCheckEnvironmental(container);
			humid = container.getHumid();
			temp = container.getTemp();
		}
//		if(biomeList.contains(BiomeDictionary.Type.COLD)){
//			if(w.isAABBInMaterial(HSLibs.getBounding(pos, 3.0D, 3.0D), Material.lava)){
//				baseTemp += 0.5F;
//			}
//			if(w.isAABBInMaterial(HSLibs.getBounding(pos, 3.0D, 3.0D), Material.fire)){
//				baseTemp += 0.5F;
//			}
//			if(WorldHelper.findNear(w,Blocks.lit_furnace, pos, 7, checkFurnace)!=null){
//				baseTemp += 0.5F;
//			}
//
//
//			return new EnvironmentalCondition(true,Type.COLD);
//		}
//		if(biomeList.contains(BiomeDictionary.Type.HOT)){
//			if(biomeList.contains(BiomeDictionary.Type.DRY) && !w.isDaytime()){
//				return new EnvironmentalCondition(false,null);
//			}else{
//				return new EnvironmentalCondition(true,Type.HOT);
//			}
//		}

//		if(biomeList.contains(BiomeDictionary.Type.WET)){
//			return new EnvironmentalCondition(true,Type.HUMID);
//		}
//		if(!biomeList.contains(BiomeDictionary.Type.DRY)){
//			if(w.isRaining()){
//				return new EnvironmentalCondition(true,Type.HUMID);
//			}
//		}
//		if(w.isAABBInMaterial(HSLibs.getBounding(pos, 3.0D, 3.0D), Material.lava)){
//			return new EnvironmentalCondition(true,Type.HOT);
//		}
		if(temp>=2.0F){
			return new EnvironmentalCondition(true,Type.HOT,temp,humid);
		}
		if(temp<=0.1F){
			return new EnvironmentalCondition(true,Type.COLD,temp,humid);
		}
		if(humid>=0.85F && temp>=0.7F){
			return new EnvironmentalCondition(true,Type.HUMID,temp,humid);
		}
		return EnvironmentalCondition.getSafeEnvironment(temp, humid);
	}
}
