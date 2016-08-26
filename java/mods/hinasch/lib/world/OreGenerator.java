package mods.hinasch.lib.world;

import java.util.Random;

import com.google.common.base.Predicate;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenMinable;


/**
 * ワールド生成で使う。
 *
 *
 */
public class OreGenerator {

	public int dens;
	public int chance;
	public int min;
	public int max;
	public final Block block;
	public Block target;
	public boolean isEnabled;

	public static OreGenerator create(Block block,boolean isEnabled){
		return new OreGenerator(block,isEnabled);
	}
	public OreGenerator(Block block,boolean isEnabled){
		this.block = block;
		this.target = Blocks.STONE;
		this.isEnabled = isEnabled;
	}

	public OreGenerator setMinMax(int min,int max){
		this.min = min;
		this.max = max;
		return this;
	}
	/**
	 * 鉱脈のできる度合い？
	 * @param chance
	 * @return
	 */
	public OreGenerator setGenerateChance(int chance){
		this.chance = chance;
		return this;
	}

	/**
	 * 鉱脈の濃さ？
	 * @param dens
	 * @return
	 */
	public OreGenerator setDensity(int dens){
		this.dens = dens;
		return this;
	}

	public OreGenerator setTargetBlock(Block block){
		this.target = block;
		return this;
	}
    public void genStandardOre1(World world,Random rand,XYZPos chunkPos)
    {
    	if(this.isEnabled){
            for (int l = 0; l < chance; ++l)
            {
                int firstBlockXCoord = chunkPos.getX() + rand.nextInt(16);
                int firstBlockYCoord = rand.nextInt(max - min) + min;
                int firstBlockZCoord =chunkPos.getZ() + rand.nextInt(16);
                IBlockState state = this.block.getDefaultState();
                (new WorldGenMinable(state, dens)).generate(world, rand, new BlockPos(firstBlockXCoord, firstBlockYCoord, firstBlockZCoord));
            }
    	}

    }

    public void genStandardOre1WithMeta(World world,Random rand,XYZPos chunkPos,IBlockState state,Predicate<IBlockState> predicate)
    {
    	if(this.isEnabled){
            for (int l = 0; l < chance; ++l)
            {
                int firstBlockXCoord = chunkPos.getX() + rand.nextInt(16);
                int firstBlockYCoord = rand.nextInt(max - min) + min;
                int firstBlockZCoord =chunkPos.getZ() + rand.nextInt(16);
                (new WorldGenMinable(state,dens,predicate)).generate(world, rand, new BlockPos(firstBlockXCoord, firstBlockYCoord, firstBlockZCoord));
            }
    	}

    }
}
