package mods.hinasch.lib.util;

import java.util.HashMap;

import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.common.registry.VillagerRegistry.VillagerProfession;

public class Statics {

	public static final String VILLAGER_FARMER = "farmer";
	public static final String VILLAGER_LIBRARIAN = "librarian";
	public static final String VILLAGER_PRIEST = "priest";
	public static final String VILLAGER_BLACKSMITH = "smith";
	public static final String VILLAGER_BUTCHER = "butcher";

	public static boolean checkProfession(VillagerProfession prof,String comp){
		return prof.getRegistryName().getResourcePath().equals(comp);
	}

	/** amount = base + modifier */
	public static final int OPERATION_INCREMENT = 0;
	/** amount = base + (base * modifier)*/
	public static final int OPERATION_INC_MULTIPLED = 1;
	/** amount = base * modifier*/
	public static final int OPERATION_MULTIPLE = 2;

	public static final int COLOR_NONE = 16777215;

//	public static final String soundXP = "random.orb";
//	public static final String soundAnvilUse = "random.anvil_use";
//	public static final String soundEnderPortal = "mob.endermen.portal";
//	public static final String soundExplode = "random.explode";
//	public static final String soundShoot = "mob.wither.shoot";
//	public static final String soundFireBall = "mob.ghast.fireball";
//	public static final String soundBow = "random.bow";
//	public static final String soundGolemHit =  "mob.irongolem.hit";

	public static final String particlePortal = "portal";
	public static final String particleHeart = "heart";
	public static final String particleHappy = "happyVillager";
	public static final String particleMobSpell = "mobSpell";
	public static final String particleSpell = "spell";
	public static final String particleLava = "lava";
	public static final String particleFlame = "flame";
	public static final String particleCloud = "cloud";
	public static final String particleExplode = "explode";
	public static final String particleBubble = "bubble";
	public static final String particleReddust = "reddust";
	public static final String particleCrit = "crit";
	public static final String particleInstantSpell = "instantSpell";
	public static HashMap<Integer,String> particleMap;
	public static HashMap<Integer,SoundEvent> soundMap;

	public static final float HARDNESS_OBSIDIAN = 50.0F;
	public static final float HARDNESS_STONE = 1.5F;
	/**
	 * hardness CobbleStone,Planks,etc...
	 */
	public static final float HARDNESS_ORE = 3.0F;
	public static final float HARDNESS_COBBLE_STONE = 2.0F;
	public static final float HARDNESS_GRASS = 0.8F;
	public static final float HARDNESS_DIRT = 0.5F;
	public static final float resistanceCS = 10.0F;
	public static final float resistancePlanks = 5.0F;


	public static final int entityRenderUpdateFreq_Arrow = 20;
	public static final int entityRenderUpdateFreq_Normal = 5;
	public static final int entityRenderTrackingRange = 250;





	public static ItemStack getBoneMeal(int stack){
		return new ItemStack(Items.DYE,stack,15);
	}
	public static ItemStack getLapis(int stack){
		return new ItemStack(Items.DYE,stack,4);
	}
	public static ItemStack getIncSac(int stack){
		return new ItemStack(Items.DYE,stack,0);
	}
	public static ItemStack getCocoa(int stack){
		return new ItemStack(Items.DYE,stack,3);
	}
	public static int getParticleNumber(String key){
		for(Integer num:particleMap.keySet()){
			if(key.equals(particleMap.get(num))){
				return num;
			}
		}
		return 1;
	}
	static{
		particleMap = new HashMap();
		particleMap.put(1, "portal");
		particleMap.put(2, "heart");
		particleMap.put(3, "happyVillager");
		particleMap.put(4, "mobSpell");
		particleMap.put(5, "spell");
		particleMap.put(6, "lava");
		particleMap.put(7, "flame");
		particleMap.put(8, "cloud");
		particleMap.put(9, "explode");
		particleMap.put(10,particleBubble);
		particleMap.put(11,particleReddust);
		particleMap.put(12, particleCrit);
		soundMap = new HashMap();
		soundMap.put(1, SoundEvents.ENTITY_ENDERMEN_TELEPORT);
		soundMap.put(2, SoundEvents.ENTITY_GHAST_SHOOT);
		soundMap.put(3, SoundEvents.ENTITY_GENERIC_EXPLODE);
		soundMap.put(4, SoundEvents.BLOCK_ANVIL_USE);
	}
}
