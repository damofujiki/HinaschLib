package mods.hinasch.lib.item;

import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

import mods.hinasch.lib.iface.BiConsumer;
import mods.hinasch.lib.util.UtilNBT;
import mods.hinasch.lib.world.WorldHelper;
import mods.hinasch.lib.world.XYZPos;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ItemUtil {

	public static EntityItem getEntityItem(ItemStack tablet,XYZPos pos,World world){
		return new EntityItem(world,pos.dx,pos.dy,pos.dz,tablet);
	}
	public static void dropItem(ItemStack dropItemStack,EntityPlayer ep){
		if(dropItemStack!=null){
			ep.entityDropItem(dropItemStack, 0.1F);

		}
	}

	public static void dropBlockAsItem(World world,XYZPos pos,IBlockState state,int fortune){
		dropBlockAsItem(world, pos, state.getBlock(),state, fortune);
	}

	public static void dropBlockAsItem(World world,XYZPos pos,Block block,IBlockState state,int fortune){
		block.dropBlockAsItem(world, pos, state, fortune);
	}

	public static void dropItem(final EntityPlayer ep,final float offSetY,Stream<ItemStack> itemList){

		itemList.filter(is -> is!=null).forEach(is -> ep.entityDropItem(is, offSetY));
	}
	/**
	 * nullチェックあり。
	 * @param world
	 * @param itemstack
	 * @param x
	 * @param y
	 * @param z
	 */
	public static void dropItem(World world,ItemStack itemstack,double x,double y,double z){

		Random rand = world.rand;
		if(itemstack!=null){
			EntityItem item = new EntityItem(world, x,y,z,itemstack);
			item.setDefaultPickupDelay();
			if(WorldHelper.isServer(world)){
				world.spawnEntityInWorld(item);
			}
		}

		return;
	}

	public static void dropItem(World world,ItemStack itemstack,XYZPos xyz){
		dropItem(world, itemstack, xyz.dx, xyz.dy, xyz.dz);
		return;
	}

	/**
	 * 秒をポーションタイムに変換
	 * @param sec
	 * @return
	 */
	public static int getPotionTime(int sec){
		return sec*20;
	}

	public static void removePotionEffects(EntityLivingBase living,Potion... potions){
		for(Potion potion:potions){
			living.removePotionEffect(potion);
		}
	}

	public static void addPotionIfLiving(Entity entity,PotionEffect potionEffect){
		if(entity instanceof EntityLivingBase){
			EntityLivingBase el = (EntityLivingBase)entity;
			el.addPotionEffect(potionEffect);
		}
	}

	@Deprecated
	public static boolean isSameClass(ItemStack is,Class _class){

			if(_class.isInstance(is.getItem())){
				return true;
			}
			if(is.getItem().getClass()==_class){
				return true;
			}

		return false;

	}

	@Deprecated
	public static boolean hasItemInstance(EntityLivingBase player,Class _class){
		if(player.getHeldItemMainhand()!=null){
			if(_class.isInstance(player.getHeldItemMainhand().getItem())){
				return true;
			}
			if(player.getHeldItemMainhand().getItem().getClass()==_class){
				return true;
			}
		}
		return false;

	}

    public static void saveItemStacksToItemNBT(ItemStack binder,ItemStack[] maps){
    	UtilNBT.initNBTIfNotInit(binder);
    	NBTTagCompound nbt = binder.getTagCompound();
    	NBTTagList tagList = UtilNBT.newTagList();
    	UtilNBT.writeItemStacksToNBTTag(tagList, maps);
    	nbt.setTag("items", tagList);
    	binder.setTagCompound(nbt);
    }

    public static ItemStack[] loadItemStacksFromItemNBT(ItemStack is,int max){
    	UtilNBT.initNBTIfNotInit(is);
    	if(UtilNBT.hasKey(is, "items")){
    		NBTTagList tagList = UtilNBT.getTagList(is.getTagCompound(), "items");
    		return UtilNBT.getItemStacksFromNBT(tagList, max);
    	}
    	return null;
    }

    public static void clearItemStacksInItemNBT(ItemStack is,int max){
    	ItemStack[] newStacks = new ItemStack[max];
    	saveItemStacksToItemNBT(is,newStacks);
    }

    public static float getRandomPitch(Random rand){
    	return  0.4F / rand.nextInt()* 0.4F + 0.8F;
    }

    @Deprecated
    public static void registerItems(Item[] items,List<String> names){
    	int index = 0;
    	for(Item item:items){
    		GameRegistry.registerItem(item, names.get(index));
    		index += 1;
    	}
    }


    @Deprecated
    public static BiConsumer<ItemStack,EntityPlayer> getItemDropConsumer(){
    	return new BiConsumer<ItemStack,EntityPlayer>(){

			@Override
			public void accept(ItemStack left, EntityPlayer right) {
				ItemUtil.dropItem(left, right);

			}};
    }


}
