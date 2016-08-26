package mods.hinasch.lib;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.google.common.collect.Lists;

import mods.hinasch.lib.RecipeUtil.Recipe.Shaped;
import mods.hinasch.lib.RecipeUtil.Recipe.Shapeless;
import mods.hinasch.lib.core.HSLib;
import mods.hinasch.lib.item.OreDict;
import mods.hinasch.lib.primitive.Tuple;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;



public class RecipeUtil {
	public static enum Type {CHANGEABLE};


	public static List<String> getOreRecipe(ItemStack is){
		int[] ids = OreDictionary.getOreIDs(is);
		List<String> listOut = new ArrayList();
		for(int id:ids){
			listOut.add(OreDictionary.getOreName(id));
		}
		return listOut;

	}

	public static java.util.Optional<ItemStack> getSmeltFrom(final ItemStack in){
		return FurnaceRecipes.instance().getSmeltingList().keySet().stream().filter(input -> in.getItem()==input.getItem() && in.getItemDamage()==input.getItemDamage()).findFirst();
	}

	public static class RecipeItemSelector extends Tuple<ItemStack,OreDict>{

		public RecipeItemSelector(ItemStack f, OreDict s) {
			super(f, s);
			// TODO 自動生成されたコンストラクター・スタブ
		}

		public static RecipeItemSelector of (ItemStack f, OreDict s){
			return new RecipeItemSelector(f,s);
		}
	}
	public static List<RecipeItemSelector> getRequireItemsFromRecipe(IRecipe recipe){
		//System.out.println(recipe);
		//		List<Tuple<ItemStack,OreDict>> itemsList = Lists.newArrayList();
		if(recipe instanceof ShapelessRecipes){
			List<ItemStack> isList = ((ShapelessRecipes)recipe).recipeItems;
			return isList.stream().map(input -> RecipeItemSelector.of(input, null)).collect(Collectors.toList());
		}
		if(recipe instanceof ShapedRecipes){
			ItemStack[] isarray = ((ShapedRecipes)recipe).recipeItems;

			return Lists.<ItemStack>newArrayList(isarray).stream().map( is ->RecipeItemSelector.of(is, null)).collect(Collectors.toList());
		}
		if(recipe instanceof ShapelessOreRecipe){



			List<RecipeItemSelector> list1 = (((ShapelessOreRecipe)recipe).getInput()).stream().filter(obj -> obj instanceof ItemStack)
					.map(obj -> RecipeItemSelector.of((ItemStack)obj, null)).collect(Collectors.toList());
			List<RecipeItemSelector> list2 = (((ShapelessOreRecipe)recipe).getInput()).stream().filter(obj -> obj instanceof String)
					.map(obj -> RecipeItemSelector.of(null, new OreDict((String)obj))).collect(Collectors.toList());
			List<RecipeItemSelector> rt = Lists.newArrayList();
			rt.addAll(list1);
			rt.addAll(list2);
			return rt;

		}
		if(recipe instanceof ShapedOreRecipe){
			Object[] isarray = ((ShapedOreRecipe)recipe).getInput();
			List<Object> objList = Lists.newArrayList(isarray);
			objList.stream().flatMap(obj ->{
				//					UnsagaMod.logger.trace("recipe", input.getClass());
				List<RecipeItemSelector> rt = Lists.newArrayList();
				if(obj instanceof List){
					//						UnsagaMod.logger.trace("shaped", "array");
					rt.addAll(convertListTypeObject(obj));
				}
				if(obj instanceof ItemStack){
					//						UnsagaMod.logger.trace("shaped", "itemstack");
					List<RecipeItemSelector> list = Lists.newArrayList(RecipeItemSelector.of((ItemStack)obj, null));
					rt.addAll(list);
				}

				//					if(objs!=null){
				//						Object[] objarray = (Object[]) objs;
				//						return ListHelper.stream(objarray).map(new Function<Object,Tuple<ItemStack,OreDict>>(){
				//
				//							@Override
				//							public Tuple<ItemStack, OreDict> apply(Object input) {
				//								UnsagaMod.logger.trace("shaped", input);
				//								if(input instanceof ItemStack){
				//									return Tuple.of((ItemStack)input,null);
				//								}
				//								if(input instanceof String){
				//									return Tuple.of(null, new OreDict((String) input));
				//								}
				//								return null;
				//							}}
				//						).getList();
				//					}

				return rt.stream();
			});
		}
		HSLib.logger.trace("recipeutil", "どのレシピにも合致しない？");
		return Lists.newArrayList();
	}

	private static List<RecipeItemSelector> convertListTypeObject(Object in){
		List<Object> list = (List)in;
		List<RecipeItemSelector> rt = Lists.newArrayList();
		List<RecipeItemSelector> list1 = list.stream().filter(input -> input instanceof ItemStack)
		.map(input -> RecipeItemSelector.of((ItemStack)input, null)).collect(Collectors.toList());
		List<RecipeItemSelector> list2 = list.stream().filter(input -> input instanceof String)
		.map(input -> RecipeItemSelector.of(null, new OreDict((String)input))).collect(Collectors.toList());
		rt.addAll(list1);
		rt.addAll(list2);
		return rt;
	}
	public static void addShapelessRecipe(ItemStack output,Shapeless materials){
		if(materials.isRequireOreRecipe){
			GameRegistry.addRecipe(getShapelssOreRecipe(output, materials));
			return;
		}
		GameRegistry.addShapelessRecipe(output, materials.getObj());
	}

	public static void addShapedRecipe(ItemStack output,Shaped recipe){
		if(recipe.isRequireOreRecipe){
			GameRegistry.addRecipe(getShapedOreRecipe(output, recipe));
			return;
		}
		//UtilCollection.printArray(recipe.getObj(), CompressedBlocks.MODID);
		GameRegistry.addShapedRecipe(output, recipe.getObj());
	}

	public static ShapedOreRecipe getShapedOreRecipe(ItemStack output,Shaped recipe){

		return new ShapedOreRecipe(output,recipe.getObj());
	}

	public static ShapelessOreRecipe getShapelssOreRecipe(ItemStack output,Shapeless recipe){
		return new ShapelessOreRecipe(output,recipe.getObj());
	}

	public static Shaped getShaped(String recipe1,String recipe2,String recipe3){
		return new Shaped(recipe1,recipe2,recipe3);
	}

	/**
	 * Shaped,Shapelessを保持するクラス。
	 *
	 *
	 */
	public static class Recipe{
		protected Object[] recipe;

		protected boolean isRequireOreRecipe = false;

		public Recipe setRequireOreRecipe(boolean par1){
			this.isRequireOreRecipe = par1;
			return this;
		}

		public boolean isRequireOreRecipe(){
			return this.isRequireOreRecipe;
		}
		public static class Shapeless extends Recipe{

			/**
			 * 不定形レシピ。
			 * @param inputs
			 */
			public Shapeless(Object... inputs) {
				super(inputs);

			}


			/**
			 * Block,Item,ItemStackが追加できる。
			 * @param item
			 * @return
			 */
			public Shapeless addAsociation(Object item){
				List list = null;
				if(this.recipe!=null){
					list = Lists.newArrayList(this.recipe);
				}else{
					list = new ArrayList();
				}

				list.add(item);
				this.recipe = list.toArray(new Object[this.recipe.length]);
				return this;
			}

			@Override
			public <T> Shapeless getChangedRecipe(T obj){
				Object[] reci = this.recipe.clone();
				for(int i=0;i<reci.length;i++){
					if(reci[i]==RecipeUtil.Type.CHANGEABLE){
						reci[i] = obj;
					}
				}
				return new Shapeless(reci);
			}
		}

		/**
		 * 定形レシピ。
		 *"FKF",'F',new ItemStack...
		 *
		 */
		public static class Shaped extends Recipe{
			public Shaped(Object... inputs) {
				super(inputs);

			}

			public Shaped addAsociation(char chr,ItemStack is){
				List list = null;
				if(this.recipe!=null){
					list = Lists.newArrayList(this.recipe);
				}else{
					list = new ArrayList();
				}

				list.add(chr);
				list.add(is);
				this.recipe = list.toArray(new Object[this.recipe.length]);
				return this;
			}
			/**
			 * ObjectにEnum{CHANGEABLE]を放り込んでおくと、
			 * このメソッドを使って入れ替えレシピが作れる。
			 * ただしString(鉱石辞書名）を入れる時は
			 * setRequireOreRecipeしとかないと落ちる。
			 * @param <T>
			 */
			@Override
			public <T> Shaped getChangedRecipe(T obj){
				Object[] reci = this.recipe.clone();
				for(int i=0;i<reci.length;i++){
					if(reci[i]==RecipeUtil.Type.CHANGEABLE){
						reci[i] = obj;
					}
				}
				return new Shaped(reci);
			}
		}


		public static Shapeless getShapelss(Object... inputs){
			return new Shapeless(inputs);
		}

		public Recipe(Object... recipes){
			this.recipe = recipes;
		}

		public Object[] getObj(){
			return this.recipe;
		}

		@Override
		public String toString(){
			StringBuilder builder = new StringBuilder();
			for(Object obj:this.recipe){
				boolean flag = false;
				if(obj instanceof String){
					builder.append("[String]"+((String)obj).toString());
					flag = true;
				}
				if(obj instanceof Character){
					builder.append("[Character]"+((Character)obj).toString());
					flag = true;
				}

				if(!flag){
					builder.append(obj.toString());
				}

				builder.append("/");
			}

			return new String(builder);
		}
		public <T> Recipe getChangedRecipe(T obj){
			Object[] reci = this.recipe.clone();
			for(int i=0;i<reci.length;i++){
				if(reci[i]==RecipeUtil.Type.CHANGEABLE){
					reci[i] = obj;
				}
			}
			return new Recipe(reci);
		}
	}
	/**
	 * レシピの例を保持するクラス。
	 *
	 *
	 */
	public static class DefaultRecipes{

		public static Shaped getPickaxe(){
			Shaped obj = new Shaped("III"," S "," S ",
					Character.valueOf('S'),Items.STICK,
					Character.valueOf('I'),RecipeUtil.Type.CHANGEABLE);
			return obj;
		}

		public static Shaped getFilled(){
			Shaped obj = new Shaped("SSS","SSS","SSS",
					Character.valueOf('S'),RecipeUtil.Type.CHANGEABLE);
			return obj;
		}

		public static Shapeless getOne(){
			Shapeless obj = new Shapeless(RecipeUtil.Type.CHANGEABLE);
			return obj;
		}
	}
}
