package mods.hinasch.lib.item;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import mods.hinasch.lib.world.WorldHelper;
import mods.hinasch.lib.world.XYZPos;
import net.minecraft.block.BlockFence;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityList.EntityEggInfo;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;




/**
 * エンティティの卵を追加
 *
 *
 */
public class ItemCustomEntityEgg extends Item implements IItemColor{
	public static class CustomEggInfo{

		public EntityEggInfo eggInfo;
		public String name;
		public Class<?extends Entity> entity;

		public CustomEggInfo(Class<?extends Entity> clazz,EntityEggInfo egginfo,String name){
			this.entity = clazz;
			this.eggInfo = egginfo;
			this.name = name;
		}
	}

	/**
	 * Spawns the creature specified by the egg's type in the location specified by the last three parameters.
	 * Parameters: world, entityID, x, y, z.
	 */
	public static Entity spawnCreature(World par0World, Entity entity, XYZPos pos)
	{


		if (entity instanceof EntityLiving)
		{
			EntityLiving entityliving = (EntityLiving)entity;
			entity.setLocationAndAngles(pos.dx, pos.dy, pos.dz, MathHelper.wrapDegrees(par0World.rand.nextFloat() * 360.0F), 0.0F);
			entityliving.rotationYawHead = entityliving.rotationYaw;
			entityliving.renderYawOffset = entityliving.rotationYaw;
			entityliving.onInitialSpawn(par0World.getDifficultyForLocation(new BlockPos(entity)),(IEntityLivingData)null);
//			System.out.println(entityliving);
			if(entityliving instanceof ISpawnOption){
				entityliving = (EntityLiving) ((ISpawnOption)entityliving).preSpawn(par0World, entity, pos);
			}
			par0World.spawnEntityInWorld(entity);
			entityliving.playLivingSound();
		}else{
			if(entity instanceof Entity){
				entity.setLocationAndAngles(pos.dx, pos.dy, pos.dz, MathHelper.wrapDegrees(par0World.rand.nextFloat() * 360.0F), 0.0F);
				if(entity instanceof ISpawnOption){
					entity = ((ISpawnOption)entity).preSpawn(par0World, entity, pos);
				}
				par0World.spawnEntityInWorld(entity);
			}
		}



		return entity;

	}




	protected Map<Integer,CustomEggInfo> eggMap;

	public ItemCustomEntityEgg()
	{

		this.eggMap = new HashMap();
		this.setHasSubtypes(true);

	}

	public void addMaping(int num,Class<?extends Entity> clazz,String name,int color1,int color2){
		EntityEggInfo eggInfo = new EntityEggInfo(name,color1,color2);
		this.eggMap.put(num, new CustomEggInfo(clazz,eggInfo,name));

	}
	@SideOnly(Side.CLIENT)
	@Override
	public int getColorFromItemstack(ItemStack stack, int tintIndex) {

		EntityList.EntityEggInfo entityegginfo = (EntityList.EntityEggInfo)this.eggMap.get(Integer.valueOf(stack.getItemDamage())).eggInfo;
		return entityegginfo != null ? (tintIndex == 0 ? entityegginfo.primaryColor : entityegginfo.secondaryColor) : 16777215;

	}

	public Map<Integer,CustomEggInfo> getEggMap(){
		return this.eggMap;
	}



	public Entity getEntityFromClass(World world,Class<?extends Entity> clazz){
		System.out.println(clazz.getName()+"を配置しようとしています");
		try {
			Constructor cons = clazz.getConstructor(World.class);
			Object obj = cons.newInstance(world);
			Entity entity = (Entity)obj;
			return entity;
		} catch (SecurityException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
			e.getCause().printStackTrace();
		}
		return null;
	}

	public String getStringFromID(int num){
		return this.eggMap.get(num).name;
	}

	/**
	 * returns a list of items with the same ID, but different meta (eg: dye returns 16 items)
	 */
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item item, CreativeTabs tab, List list)
	{
		Iterator iterator = EntityList.ENTITY_EGGS.values().iterator();

		for(Iterator<Integer> ite=this.eggMap.keySet().iterator();ite.hasNext();){
			int num = ite.next();
			list.add(new ItemStack(item,1,num));
		}

	}

	public String getUnlocalizedName(ItemStack par1ItemStack)
	{
		return super.getUnlocalizedName() + this.eggMap.get(par1ItemStack.getItemDamage()).name;
	}

	//	/**
	//	 * Gets an icon index based on an item's damage value and the given render pass
	//	 */
	//	@SideOnly(Side.CLIENT)
	//	public IIcon getIconFromDamageForRenderPass(int par1, int par2)
	//	{
	//		return Items.spawn_egg.getIconFromDamageForRenderPass(par1, par2);
	//		//return par2 > 0 ? this.theIcon : super.getIconFromDamageForRenderPass(par1, par2);
	//	}

	/**
	 * Called whenever this item is equipped and the right mouse button is pressed. Args: itemStack, world, entityPlayer
	 */
	@Override
	public ActionResult<ItemStack> onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer, EnumHand hand)
	{
		if (par2World.isRemote)
		{
			return new ActionResult(EnumActionResult.PASS, par1ItemStack);
		}
		else
		{
			RayTraceResult movingobjectposition = this.rayTrace(par2World, par3EntityPlayer, true);

			if (movingobjectposition == null)
			{
				return new ActionResult(EnumActionResult.PASS, par1ItemStack);
			}
			else
			{
				if (movingobjectposition.typeOfHit == RayTraceResult.Type.BLOCK)
				{
					BlockPos bp = movingobjectposition.getBlockPos();
					int i = bp.getX();
					int j = bp.getY();
					int k =bp.getZ();

					if (!par2World.canMineBlockBody(par3EntityPlayer, bp))
					{
						return new ActionResult(EnumActionResult.PASS, par1ItemStack);
					}

					if (!par3EntityPlayer.canPlayerEdit(bp, movingobjectposition.sideHit, par1ItemStack))
					{
						return new ActionResult(EnumActionResult.FAIL, par1ItemStack);
					}

					if (par2World.getBlockState(bp) instanceof BlockLiquid)
					{
						Entity entity = this.getEntityFromClass(par2World, this.eggMap.get(par1ItemStack.getItemDamage()).entity);//spawnCreature(par3World, par1ItemStack.getItemDamage(), (double)par4 + 0.5D, (double)par5 + d0, (double)par6 + 0.5D);
						XYZPos pos = new XYZPos((double)i, (double)j, (double)k);
						this.spawnCreature(par2World, entity, pos);
						if (entity != null)
						{
							if (entity instanceof EntityLivingBase && par1ItemStack.hasDisplayName())
							{
								((EntityLiving)entity).setCustomNameTag(par1ItemStack.getDisplayName());
							}

							if (!par3EntityPlayer.capabilities.isCreativeMode)
							{
								--par1ItemStack.stackSize;
							}
						}
						return new ActionResult(EnumActionResult.SUCCESS,par1ItemStack);
					}
				}

				return new ActionResult(EnumActionResult.PASS, par1ItemStack);
			}
		}
	}

	//	@SideOnly(Side.CLIENT)
	//	public void registerIcons(IIconRegister par1IconRegister)
	//	{
	//		super.registerIcons(par1IconRegister);
	//		this.theIcon = par1IconRegister.registerIcon(this.getIconString() + "_overlay");
	//	}

	/**
	 * Callback for item usage. If the item does something special on right clicking, he will have one of those. Return
	 * True if something happen and false if it don't. This is for ITEMS, not BLOCKS
	 */
	@Override
	public EnumActionResult onItemUse(ItemStack stack, EntityPlayer playerIn, World par3World, BlockPos pos, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ)
	{
		if (WorldHelper.isClient(par3World))
		{
			return EnumActionResult.SUCCESS;
		}
		else
		{
			IBlockState state = par3World.getBlockState(pos);
			BlockPos pos2 = pos.offset(side);

			double d0 = 0.0D;

			if (side==EnumFacing.UP && state instanceof BlockFence)
			{
				d0 = 0.5D;
			}

			Entity entity = this.getEntityFromClass(par3World, this.eggMap.get(stack.getItemDamage()).entity);//spawnCreature(par3World, par1ItemStack.getItemDamage(), (double)par4 + 0.5D, (double)par5 + d0, (double)par6 + 0.5D);
			XYZPos pos3 =(new XYZPos(pos2)).addDouble(0.5D, d0, 0.5D);
			System.out.println(entity);
			System.out.println(pos3);
			this.spawnCreature(par3World, entity, new XYZPos(pos3));

			if (entity != null)
			{
				if (entity instanceof EntityLivingBase && stack.hasDisplayName())
				{
					((EntityLiving)entity).setCustomNameTag(stack.getDisplayName());
				}

				if (!playerIn.capabilities.isCreativeMode)
				{
					--stack.stackSize;
				}
			}

			return EnumActionResult.SUCCESS;
		}
	}



	public static interface ISpawnOption{
		public Entity preSpawn(World world,Entity entity,XYZPos pos);
	}
}
