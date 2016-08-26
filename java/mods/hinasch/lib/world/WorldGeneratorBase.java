package mods.hinasch.lib.world;

import java.util.Random;

import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkGenerator;
import net.minecraft.world.chunk.IChunkProvider;


public abstract class WorldGeneratorBase implements net.minecraftforge.fml.common.IWorldGenerator{

	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world,
			IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
		switch(world.provider.getDimension()){
		case -1:
			generateNether(world, random, chunkX * 16, chunkZ * 16);
			break;
		case 0:
			if(this.arrowGenerationInOverworld()){
				generateOverworld(world, random, chunkX * 16, chunkZ * 16);
			}else{
				this.generateOverworldAlways(world, random, chunkX * 16, chunkZ * 16);
			}
			break;
		case 1:
			generateEnd(world, random, chunkX * 16, chunkZ * 16);
			break;
		default:
			generateOverworld(world, random, chunkX * 16, chunkZ * 16);
			break;
		}

	}

	/**
	 * 地上での生成を許すか許さないか。
	 * 地上世界の保護目的.
	 *
	 * @return
	 */
	public boolean arrowGenerationInOverworld(){
		return true;
	}

	/**
	 * arrowGenerationInOverWorldがfalseの場合こちらが呼ばれる。なんでそうしたのか忘れた。
	 * ちなみに地上ネザーエンド意外はgenerateOverworldが呼ばれる。
	 * @param world
	 * @param random
	 * @param i
	 * @param j
	 */
	public void generateOverworldAlways(World world, Random random, int i, int j){

	}
	public abstract void generateNether(World world, Random random, int i, int j);
	public abstract void generateOverworld(World world, Random random, int i, int j);
	public abstract void generateEnd(World world, Random random, int i, int j);

}
