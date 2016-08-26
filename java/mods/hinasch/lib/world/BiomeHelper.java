package mods.hinasch.lib.world;

import java.util.List;

import com.google.common.collect.Lists;

import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.common.BiomeManager.BiomeEntry;
import net.minecraftforge.common.BiomeManager.BiomeType;

public class BiomeHelper {

	public static void registerBiome(BiomeType type,Biome biome){
		BiomeManager.addBiome(type, new BiomeEntry(biome,10));
		BiomeManager.addSpawnBiome(biome);
		BiomeManager.addStrongholdBiome(biome);
	}

	public static void addVillageBiome(Biome biome){
		BiomeManager.addVillageBiome(biome, true);
	}


	public static List<BiomeDictionary.Type> getBiomeTypeList(Biome biome){
		return Lists.newArrayList(BiomeDictionary.getTypesForBiome(biome));
	}
}
