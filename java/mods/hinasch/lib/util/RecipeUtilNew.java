package mods.hinasch.lib.util;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import jline.internal.Preconditions;
import mods.hinasch.lib.item.OreDict;
import mods.hinasch.lib.primitive.Triplet;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Tuple;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

public class RecipeUtilNew {

	public static abstract class Recipe{
		ItemStack output;

		public ItemStack getOutput(){
			return this.output;
		}

		public Recipe setOutput(ItemStack is){
			this.output = is;
			return this;
		}

		public void register(){
			Preconditions.checkNotNull(getOutput());
		}
		public abstract Object[] buildObject();

		public void clear(){
			this.output = null;
		}

	}

	public static class RecipeShaped extends Recipe{

		String[] base;



		Map<Character,Triplet<ItemStack,Item,OreDict>> map = Maps.newHashMap();
		/**
		 * ex. "x x","xxx","x x"
		 * @param s1
		 * @param s2
		 * @param s3
		 */
		public RecipeShaped setBase(String... strs){
			this.base = strs;
			return this;

		}

		public static RecipeShaped create(){
			return new RecipeShaped();
		}
		@Override
		public void clear(){
			super.clear();
			base = null;
			map = Maps.newHashMap();
		}
		public RecipeShaped addAssociation(char c,ItemStack is){
			map.put(c,Triplet.of(is, null,null));
			return this;
		}
		public RecipeShaped addAssociation(char c,Item item){
			map.put(c, Triplet.of(null,item,null));
			return this;
		}

		public RecipeShaped addAssociation(char c,String item){
			map.put(c, Triplet.of(null,null,new OreDict(item)));
			return this;
		}

		public boolean isOreRecipe(){
			return map.values().stream().anyMatch(in -> in.third()!=null);

		}
		@Override
		public Object[] buildObject() {
			List<Object> list = Lists.newArrayList();
			list.addAll(Arrays.stream(base).limit(3).collect(Collectors.toList()));
			map.entrySet().stream().forEach(in ->{
				list.add(in.getKey());
				if(in.getValue().first!=null){
					list.add(in.getValue().first);
				}
				if(in.getValue().second!=null){
					list.add(in.getValue().second);
				}
				if(in.getValue().third()!=null){
					list.add(in.getValue().third().getOreString());
				}
			});
			return list.toArray(new Object[list.size()]);
		}

		@Override
		public void register(){
			super.register();
			if(this.isOreRecipe()){
				ShapedOreRecipe oreRecipe = new ShapedOreRecipe(this.getOutput(),this.buildObject());
				GameRegistry.addRecipe(oreRecipe);
			}else{
				GameRegistry.addShapedRecipe(this.getOutput(), this.buildObject());
			}

		}
	}
	public static class RecipeShapeless extends Recipe{

		List<Tuple<ItemStack,OreDict>> recipeItems = Lists.newArrayList();

		public static RecipeShapeless create(){
			return new RecipeShapeless();
		}
		public RecipeShapeless addRecipeItem(ItemStack is){
			this.recipeItems.add(new Tuple<ItemStack,OreDict>(is,null));
			return this;
		}
		public RecipeShapeless addRecipeOre(String is){
			this.recipeItems.add(new Tuple<ItemStack,OreDict>(null,new OreDict(is)));
			return this;
		}
		@Override
		public void clear(){
			super.clear();
			recipeItems = Lists.newArrayList();
		}
		@Override
		public Object[] buildObject() {
			List<Object> list = Lists.newArrayList();

			recipeItems.stream().forEach(in ->{
				if(in.getFirst()!=null){
					list.add(in.getFirst());

				}
				if(in.getSecond()!=null){
					list.add(in.getSecond().getOreString());
				}
			});
//			list.addAll(recipeItems.stream().map(in -> (Object)in.getFirst()).collect(Collectors.toList()));
//			if(this.isOreRecipe()){
//				list.addAll(recipeDicts.stream().map(in -> (Object)in.getOreString()).collect(Collectors.toList()));
//			}
			return list.toArray(new Object[list.size()]);
		}

		public boolean isOreRecipe(){
			return recipeItems.stream().anyMatch(in -> in.getSecond()!=null);

		}
		@Override
		public void register(){
			super.register();
			if(this.isOreRecipe()){
				ShapelessOreRecipe oreRecipe = new ShapelessOreRecipe(this.getOutput(),this.buildObject());
				GameRegistry.addRecipe(oreRecipe);
			}else{
				GameRegistry.addShapelessRecipe(this.getOutput(), this.buildObject());
			}

		}
	}

	public static class Recipes{
		/** B=box*/
		public static final String[] CUBE_MIDDLE = new String[]{"BB","BB"};
		/** B=box*/
		public static final String[] FILLED = new String[]{"BBB","BBB","BBB"};
		/** B=box*/
		public static final String[] CHEST = new String[]{"BBB","B B","BBB"};
		/** I=ingot S=stick*/
		public static final String[] PICKAXE = new String[]{"III"," S "," S "};
	}
}
