package mods.hinasch.lib.block;

import java.util.Iterator;
import java.util.Random;
import java.util.function.Function;
import java.util.function.Supplier;

import com.google.common.base.Optional;

import jline.internal.Preconditions;
import mods.hinasch.lib.primitive.NameAndNumberAndID;
import mods.hinasch.lib.util.Statics;
import net.minecraft.block.Block;
import net.minecraft.block.BlockOre;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;

public class BlockOreBase extends BlockOre{


	BlockOreProperty prop;
	Supplier<Random> supplier;

	public BlockOreBase(BlockOreProperty prop,Supplier<Random> supplier){
		Preconditions.checkNotNull(prop);
		this.prop = prop;
		this.supplier = supplier;
		this.setHardness(Statics.HARDNESS_ORE);
		this.setResistance(5.0F);
		this.setHarvestLevel("pickaxe", this.prop.getHarvestLevel());
	}

	public Random getRandomInstance(){
		return this.supplier.get();
	}
	@Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
		return !this.prop.getInsideItem().isPresent() ? Item.getItemFromBlock(this) : this.prop.getInsideItem().get().getItem();
		//return Unsaga.blockOreData.containerItem.get(blockDataIndex)==-1? Item.getItemFromBlock(this) : Unsaga.items.materials;
	}

	@Override
    public int damageDropped(IBlockState state)
    {
//		if(this.prop.getInsideItemData().isPresent()){
////			Unsaga.debug(this.oreData,this.oreData.getInsideItemData().get());
//		}

		return !this.prop.getInsideItem().isPresent() ? 0 : this.prop.getInsideItem().get().getId();
		//return Unsaga.blockOreData.containerItem.get(blockDataIndex)==-1? par1 : Unsaga.blockOreData.containerItem.get(blockDataIndex);
	}


	@Override
    public int quantityDropped(IBlockState state, int fortune, Random random)
    {
		Item drop = this.getItemDropped(state, random, fortune);
		if (fortune > 0 && this != Block.getBlockFromItem(drop))
		{
			int j = random.nextInt(fortune + 2) - 1;

			if (j < 0)
			{
				j = 0;
			}

			return this.quantityDropped(random) * (j + 1);
		}
		else
		{
			return this.quantityDropped(random);
		}
	}

	@Override
    public int getExpDrop(IBlockState state, net.minecraft.world.IBlockAccess world, BlockPos pos, int fortune)
    {
		if (this.getItemDropped(state, this.getRandomInstance(), fortune) != Item.getItemFromBlock(this))
		{
			int j1 = 0;

			if (this.prop.getExp()!=-1)
			{
				j1 = MathHelper.getRandomIntegerInRange(this.getRandomInstance(), 2, 5);
			}
			return j1;
		}

		return 0;
	}

	@Override
    public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }


	@Override
    public boolean isFullCube(IBlockState state)
    {
        return false;
    }

	@Override
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer()
    {
		//半透明にしたい場合はトランスクルーエントに
        return BlockRenderLayer.CUTOUT;
    }

	public static void registerSmeltsAndDicts(Iterator<? extends IOreProperty> list,Function<IOreProperty,Block> blockSupplier){
		list.forEachRemaining(in ->{
			if(in.getSmelted().isPresent()){
				Item item = in.getItem();
				int meta = in.getSmelted().get().getMeta();
				ItemStack  smelted = new ItemStack(item,1,meta);
				float exp = in.getExp();
				FurnaceRecipes.instance().addSmeltingRecipeForBlock(blockSupplier.apply(in),smelted, exp);
			}

			OreDictionary.registerOre(in.getOreDictID(),blockSupplier.apply(in));
		});
	}
	public static interface IOreProperty{


		public ResourceLocation getKey();
		public String getOreDictID();
		public Optional<? extends NameAndNumberAndID> getSmelted();
		public Optional<? extends NameAndNumberAndID> getInsideItemData();
		public int getHarvestLevel();
		public Item getItem();
		public float getExp();
	}

	public static final int HARVEST_BY_DIA = 3;
	public static final int HARVEST_BY_IRON = 2;
	public static final int HARVEST_BY_STONE = 1;
	public static final int HARVEST_BY_ALL = 0;
}
