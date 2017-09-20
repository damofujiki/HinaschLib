package mods.hinasch.lib.util;



import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;

import org.apache.commons.lang3.tuple.Pair;
import org.lwjgl.input.Keyboard;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import io.netty.buffer.ByteBuf;
import mods.hinasch.lib.DebugLog;
import mods.hinasch.lib.client.IGuiAttribute;
import mods.hinasch.lib.core.HSLib;
import mods.hinasch.lib.iface.IIntSerializable;
import mods.hinasch.lib.network.PacketOpenGui;
import mods.hinasch.lib.primitive.Tuple;
import mods.hinasch.lib.world.XYZPos;
import net.minecraft.block.Block;
import net.minecraft.block.BlockOre;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.INpc;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.boss.EntityDragonPart;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.Capability.IStorage;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.internal.FMLNetworkHandler;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;




/**
 * どこにわければいいのかわからんのをつっこんでおく
 *
 *
 */
public class HSLibs {

	public static HSLibs instance;
	public static String JPKEY = "ja_JP";

	public static class FLAG_SETBLOCK{
		public static final int NORMAL = 3;
	}


	public static final EnumFacing[] UPDOWN = {EnumFacing.DOWN,EnumFacing.UP};
	public static final EnumFacing[] WE = {EnumFacing.WEST,EnumFacing.EAST};
	public static final EnumFacing[] NS = {EnumFacing.SOUTH,EnumFacing.NORTH};
	public static final EnumFacing[] AROUND = {EnumFacing.SOUTH,EnumFacing.NORTH,EnumFacing.DOWN,EnumFacing.UP,EnumFacing.DOWN,EnumFacing.UP};


	public static final ComparatorRL SORT_RESOURCE_LOCATION = new ComparatorRL();

	public static class ComparatorRL implements Comparator<ResourceLocation>{

		@Override
		public int compare(ResourceLocation o1, ResourceLocation o2) {
			return String.CASE_INSENSITIVE_ORDER.compare(o1.getResourcePath(), o2.getResourcePath());
		}

	}
	/**
	 * InputEvent.KeyInputEvent
	 * @param event
	 */
	@Deprecated
	public static void registerKeyEvent(Object event){
		FMLCommonHandler.instance().bus().register(event);
	}
	public static <T extends IIntSerializable> T fromMeta(Collection<T> values,int meta){
		for(T elm:values){
			if(elm.getMeta()==meta){
				return elm;
			}
		}
		return null;
	}
	public static <T extends IIntSerializable> T fromMeta(T[] values,int meta){
		return fromMeta(Lists.newArrayList(values),meta);
	}
	/**
	 * BlockのgetState～だとメタIDがブロックIDの上位１２bitに入って帰ってくる?
	 *
	 * ここではメタIDだけ返す（上位12bitを右へ12bitシフト）
	 * @return
	 */
	public static int getMetaIDFromBlock(IBlockState state){
		return Block.getStateId(state) >> 12;
	}
	/**
	 * check out lwjql.input.Keyboard
	 * @param name
	 * @param key
	 * @param modid
	 * @return
	 */
	public static KeyBinding getNewKeyBinding(String name,int key,String modid){
		return new KeyBinding(name,key,modid);

	}



	@SideOnly(Side.CLIENT)
	public static void openGuiFromClient(IGuiAttribute gui,XYZPos pos,NBTTagCompound... args){
		HSLib.core().getPacketDispatcher().sendToServer(PacketOpenGui.create(gui, pos, args));
	}

//	public static EnumParticleTypes addEnumParticleType(String enumName,String pName,String pID,boolean par3){
//		return EnumHelper.add(EnumParticleTypes.class, enumName, pName,pID,par3);
//	}
	public static void openGui(EntityPlayer ep,Object mod,int guinumber,World world,XYZPos pos){
		FMLNetworkHandler.openGui(ep, mod, guinumber, ep.worldObj,pos.getX(),pos.getY(),pos.getZ());
	}
	/**
	 * ノックバックさせる。
	 * @param attacker
	 * @param hitent
	 * @param str ノックバックの強さ
	 */
	public static void knockBack(EntityLivingBase attacker,EntityLivingBase hitent,double str){
		double d0 = attacker.posX - hitent.posX;
		double d1;

		for (d1 = attacker.posZ - hitent.posZ; d0 * d0 + d1 * d1 < 1.0E-4D; d1 = (Math.random() - Math.random()) * 0.01D)
		{
			d0 = (Math.random() - Math.random()) * str;
		}
		((EntityLiving)hitent).knockBack(attacker, 0	, d0, d1);

	}

	public static boolean isCreativeMode(EntityPlayer ep){
		return ep.capabilities.isCreativeMode;
	}

//    public void knockBack(EntityLivingBase target,Entity attacker, float p_70653_2_, double p_70653_3_, double p_70653_5_)
//    {
//    	Random rand = target.getRNG();
//        if (rand.nextDouble() >= target.getEntityAttribute(SharedMonsterAttributes.knockbackResistance).getAttributeValue())
//        {
//        	target.isAirBorne = true;
//            float f1 = MathHelper.sqrt_double(p_70653_3_ * p_70653_3_ + p_70653_5_ * p_70653_5_);
//            float f2 = 0.4F;
//            target.motionX /= 2.0D;
//            target.motionY /= 2.0D;
//            target.motionZ /= 2.0D;
//            target.motionX -= p_70653_3_ / (double)f1 * (double)f2;
//            target.motionY += (double)f2;
//            target.motionZ -= p_70653_5_ / (double)f1 * (double)f2;
//
//            if (target.motionY > 0.4000000059604645D)
//            {
//            	target.motionY = 0.4000000059604645D;
//            }
//        }
//    }
//
	public static void knockBack(EntityLivingBase attacker,EntityLivingBase hitent){
		knockBack(hitent, hitent, 0.01D);
	}
	public static List<String> getOreNames(int[] ids){
		List<String> list = new ArrayList();
		for(int id:ids){
			list.add(OreDictionary.getOreName(id));
		}
		return list;
	}
	public static List<String> getOreNames(ItemStack is){

		return Arrays.stream(OreDictionary.getOreIDs(is)).mapToObj(id ->OreDictionary.getOreName(id)).collect(Collectors.toList());

	}

	public static boolean containsDirections(EnumFacing side,EnumFacing... directions){
		for(EnumFacing dir:directions){
			if(side==dir){
				return true;
			}
		}
		return false;
	}

	public static Item getItemFromBlock(Block block){
		ItemStack is = new ItemStack(block,1);
		System.out.println("getitemfromblock:"+is.getItem());
		return is.getItem();
	}
	public static boolean isEntityStopped(Entity entity){

		System.out.println(entity.posX + entity.posY + entity.posZ);
		return entity.posX + entity.posY + entity.posZ <= 0.00001D;
	}

//	public static void sendDescriptionPacketToAllPlayer(World world,TileEntity te){
//		if(!world.isRemote){
//			for(Object obj:world.playerEntities){
//				EntityPlayerMP ep = (EntityPlayerMP)obj;
//				ep.getServerWorld().sendPacketToServer(te.getUpdatePacket());
//			}
//		}
//	}

	public static boolean notNull(Object... objs){
		for(Object obj:objs){
			if(obj==null){
				return false;
			}
		}
		return true;
	}


	protected static class PairCompare extends Tuple<EntityLivingBase,EntityLivingBase>{

		public PairCompare(EntityLivingBase f, EntityLivingBase s) {
			super(f, s);
			// TODO 自動生成されたコンストラクター・スタブ
		}

		public EntityLivingBase getOwner(){
			return this.first;
		}

		public EntityLivingBase getComparing(){
			return this.second;
		}


	}
	public static boolean isSameTeam(EntityLivingBase owner,EntityLivingBase livingToCompare){
		if(owner!=null && livingToCompare!=null){

			Predicate<PairCompare> compareTameable = input ->{
				if(input.getComparing() instanceof EntityTameable){
					EntityTameable tamed = (EntityTameable) input.getComparing();
					return tamed.getOwner()!=null ? tamed.getOwner()==input.getOwner() : false;
				}
				return false;


			};
			Predicate<PairCompare> compareHorse = new Predicate<PairCompare>(){

				@Override
				public boolean apply(PairCompare input) {
					if(input.getComparing() instanceof EntityHorse){
						EntityHorse horse = (EntityHorse) input.getComparing();
						UUID uuidHorseOwner = horse.getOwnerUniqueId();
						UUID uuidOwner = input.getOwner().getUniqueID();
						if(uuidHorseOwner!=null && uuidOwner!=null){
							return uuidHorseOwner!=null ? uuidHorseOwner==uuidOwner : false;
						}

					}
					return false;
				}
			};

			Predicate<PairCompare> isSameTeam = input ->  input.getComparing().isOnSameTeam(input.getOwner());

			return Predicates.or(compareTameable,compareHorse,isSameTeam).apply(new PairCompare(owner, livingToCompare));
		}

		return false;
	}
	public static <T> boolean listContains(Collection<T> c,T... elements){
		int var1 = 0;
		for(T element:elements){
			if(c.contains(element)){
				var1 += 1;
			}
		}
		if(var1>=c.size()){
			return true;
		}
		return false;
	}

	public static <T> boolean instanceOf(T par1,Collection<Class<? extends T>> classes){
		//int var1 = 0;

		for(Class cls:classes){
			//System.out.println(par1.getClass().getSimpleName()+":"+cls.getSimpleName());
			if(cls.isInstance(par1) || par1.getClass()==cls){

				return true;
			}
		}

		return false;
	}


//	@Deprecated
//	public static boolean isArrowInGround(EntityArrow arrow,boolean debug){
//		if(arrow instanceof EntityArrowCustom){
//			return ((EntityArrowCustom) arrow).isInGround();
//		}
//		Class arrowClass = arrow.getClass();
//		try {
//			//String fieldName = debug ? "inGround" : "field_70254_i";
//			Field f = ReflectionHelper.findField(arrowClass, "field_70254_i","i","inGround");//ReflectionHelper.findField(arrowClass, "inGround","field_70254_i","i");
//
//			//Field f = arrowClass.getDeclaredField("inGround");
//
//			return f.getBoolean(arrow);
//		} catch (SecurityException e) {
//			e.printStackTrace();
//		} catch (IllegalArgumentException e) {
//			e.printStackTrace();
//		} catch (IllegalAccessException e) {
//			e.printStackTrace();
//		}
//		return false;
//	}

//	@Deprecated
//	public static int getArrowTickInGround(EntityArrow arrow,boolean debug){
//
//		if(arrow instanceof EntityArrowCustom){
//			return ((EntityArrowCustom) arrow).getTicksInGround();
//		}
//		Class arrowClass = arrow.getClass();
//		try {
//			//String fieldName = debug ? "ticksInGround" : "field_70252_j";
//			Field f = ReflectionHelper.findField(arrowClass, "ticksInGround","field_70252_j","j");
//			//Field f = arrowClass.getDeclaredField("ticksInGround");
//
//			return f.getInt(arrow);
//		} catch (SecurityException e) {
//			e.printStackTrace();
//		} catch (IllegalArgumentException e) {
//			e.printStackTrace();
//		} catch (IllegalAccessException e) {
//			e.printStackTrace();
//		}
//		return 0;
//	}


	public static Method getMethod(Object target,Class clazz,String[] names,Class[] types){
		Method m = ReflectionHelper.findMethod(clazz, target, names,types);
		return m;
	}
	public static Object getField(Object target,Class clazz,String... names){
		Field f = ReflectionHelper.findField(clazz, names);
		Object obj = null;
		try {
			obj = f.get(target);
		} catch (IllegalArgumentException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		return obj;
	}

	public static void setField(Object target,Class clazz,Object value,String... names){
		Field f = ReflectionHelper.findField(clazz, names);
		try {
			f.set(target, value);
		} catch (IllegalArgumentException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
	}
	public static boolean isOreBlock(IBlockState blockdata){

		if(blockdata.getBlock() instanceof BlockOre ){
			return true;
		}
		if(blockdata.getBlock()==Blocks.REDSTONE_ORE || blockdata.getBlock()==Blocks.LIT_REDSTONE_ORE ){
			return true;
		}
		ItemStack is = new ItemStack(blockdata.getBlock(),1,Block.getStateId(blockdata));
		if(is!=null){
			int[] oreids = OreDictionary.getOreIDs(is);
			for(int oreid:oreids){
				if(OreDictionary.getOreName(oreid).contains("ore")){
					return true;
				}
			}
		}


		return false;
	}

	public static boolean canBreakAndEffectiveBlock(World world,EntityLivingBase ep,String toolclass,XYZPos pos){
		IBlockState blockdata = world.getBlockState(pos);
		int harvestLevel = blockdata.getBlock().getHarvestLevel(blockdata);
		if(ep.getHeldItemMainhand()==null){
			return false;
		}

		int toolHarvestLevel = ep.getHeldItemMainhand().getItem().getHarvestLevel(ep.getHeldItemMainhand(), toolclass);
		//Unsaga.debug(blockdata.toString());
		//Unsaga.debug("harvestlevel:"+harvestLevel+" toolHArvestLevel:"+toolHarvestLevel);
		//Unsaga.debug(toolclass+"effective:"+blockdata.blockObj.isToolEffective(toolclass, blockdata.metadata));
		boolean flag1 = harvestLevel<=toolHarvestLevel;
		boolean flag2 = blockdata.getBlock().isToolEffective(toolclass, blockdata);
		boolean flag3 = blockdata.getBlock().getBlockHardness(blockdata, world, pos)>0;
		if(blockdata.getBlock()==Blocks.REDSTONE_ORE || blockdata.getBlock()==Blocks.LIT_REDSTONE_ORE ){
			flag2 = true;
		}
		return flag1 && flag2 && flag3;
	}

	public static Set<EntityPlayer> findPlayerFromBatllers(DamageSource ds,EntityLivingBase victim){
		if(victim instanceof EntityPlayer){
			if(ds.getEntity() instanceof EntityPlayer){
				return Sets.newHashSet((EntityPlayer) ds.getEntity(),(EntityPlayer)victim);
			}else{
				return Sets.newHashSet((EntityPlayer) victim);
			}

		}
		if(ds.getEntity() instanceof EntityPlayer){
			return Sets.newHashSet((EntityPlayer) ds.getEntity());
		}

		return Sets.newHashSet();
	}

	public static void broadcastToPlayers(Set<EntityPlayer> eps,String mes){
		for(EntityPlayer ep:eps){
			ChatHandler.sendChatToPlayer(ep, mes);
		}
	}
	public static float getEntityAttackDamage(EntityLivingBase living){
		return (float)living.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue();
	}

	public static boolean isHardBlock(Block block){
		if(block==Blocks.BEDROCK){
			return true;
		}
		if(block==Blocks.OBSIDIAN){
			return true;
		}
		return false;
	}


	public static float getAttackValue(EntityLivingBase living){
		return (float) living.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue();
	}

	/**
	 * 機能せず
	 * @param world
	 * @param enemy
	 * @return
	 */
	@Deprecated
	public static EntityDragonPart getEntityPartFromEntityDragon(World world,EntityDragon enemy){
		EntityDragonPart part = null;
		AxisAlignedBB bb = enemy.getEntityBoundingBox().expand(3.0D, 3.0D, 3.0D);
		List<EntityDragonPart> list = world.getEntitiesWithinAABB(EntityDragonPart.class, bb);
		if(!list.isEmpty() && list!=null){
			part = list.get(0);
		}
		return part;
	}

	public static boolean isServer(World world){
		return !world.isRemote;
	}
	public static boolean checkAroundMaterial(IBlockAccess world,XYZPos pos,Material material){
		for(EnumFacing face:AROUND){
			IBlockState state = world.getBlockState(pos.offset(face));
			if(state.getMaterial()==material){
				return true;
			}
		}
		return false;
	}

//	public static String getToolMaterialNameFromTool(Item par1){
//		if(par1 instanceof ItemSword){
//			return ((ItemSword)par1).getToolMaterialName();
//		}
//		if(par1 instanceof ItemTool){
//			return ((ItemTool)par1).getToolMaterialName();
//		}
//		if(par1 instanceof ItemArmor){
//			return ((ItemArmor)par1).getArmorMaterial().toString();
//		}
//		if(par1 instanceof ItemHoe){
//			return ((ItemHoe)par1).getToolMaterialName();
//		}
//		return null;
//	}

	public static boolean isEntityLittleMaidAvatar(EntityLivingBase entity){
		if(entity==null)return false;
		String clsname = entity.getClass().getSimpleName();
		if(clsname.equals("LMM_EntityLittleMaidAvatar")){
			return true;
		}
		if(clsname.equals("LMM_EntityLittleMaid")){
			return true;
		}
		return false;
	}

	public static boolean isEntityLittleMaidAvatar(EntityLivingBase entity,DebugLog logger){
		if(entity==null)return false;
		String clsname = entity.getClass().getSimpleName();
		if(clsname.equals("LMM_EntityLittleMaidAvatar")){
			logger.log("entity is LMMAVATAR");
			return true;
		}
		if(clsname.equals("LMM_EntityLittleMaid")){
			logger.log("entity is LMMLittleMaid");
			return true;
		}
		logger.log("entity is not LMMAVATAR or LittleMAID",clsname);
		return false;
	}

//	public static Optional<IExtendedEntityProperties> getExtendedData(String key,Entity target){
//		NBTTagCompound nbt = target.getEntityData();
//		if(nbt.hasKey(key)){
//			NBTTagList tagList = UtilNBT.getTagList(nbt, key);
//		}
//
////		if(target.getExtendedProperties(key)!=null){
////			return Optional.of(target.getExtendedProperties(key));
////		}
//		return Optional.absent();
//	}


	/** Keyboardクラスを見てチェック*/
	public static boolean checkShiftKeyCombo(int key){
		return GuiScreen.isShiftKeyDown() && Keyboard.isKeyDown(key);
	}
	public static boolean canTranslate(String key){
		return I18n.hasKey(key);
	}
	public static String translateKey(String key,Object... obj){
		if(obj==null || (obj!=null && obj.length==0)){
			return I18n.format(key, new Object[0]);
		}
		return I18n.format(key, obj);
	}
	public static boolean isEntityLittleMaidAndFortune(EntityLiving entity){
		if(isEntityLittleMaidAvatar(entity)){
			if(entity.getRNG().nextInt(3)==0){
				return true;
			}
		}
		return false;
	}

	public static EntityLiving getLMMFromAvatar(EntityLivingBase entity){
		if(entity==null)return null;
			Class avatarcls = entity.getClass();
			try {
				Field lmmfield = avatarcls.getDeclaredField("avatar");
				try {
					EntityLiving el = (EntityLiving)lmmfield.get(entity);
					return el;
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchFieldException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		return null;
	}
	public static boolean isSide(int side){

		if(side!=0 && side!=1){
			return true;
		}
		return false;
	}


	public static int exceptZero(int par1){

		if(par1==0){
			return 1;
		}


		return par1;

	}

	public static float exceptZero(float par1,float cut){
		if(par1==0){
			return cut;
		}
		return par1;
	}

//	public static XYZPos getSideHitPos(XYZPos xyz,MovingObjectPosition mop){
//		XYZPos newxyz = xyz;
//
//		if (mop.sideHit == 0)
//		{
//			newxyz.y--;
//		}
//
//		if (mop.sideHit == 1)
//		{
//			newxyz.y++;
//		}
//
//		if (mop.sideHit == 2)
//		{
//			newxyz.z--;
//		}
//
//		if (mop.sideHit == 3)
//		{
//			newxyz.z++;
//		}
//
//		if (mop.sideHit == 4)
//		{
//			newxyz.x--;
//		}
//
//		if (mop.sideHit == 5)
//		{
//			newxyz.x++;
//		}
//		return newxyz;
//	}



	public static void log(String par1,boolean debug){
		if(debug){
			HSLib.logger.trace(par1);

		}
	}




//	public static void spawnEntity(World world,Entity entity,XYZPos xyz){
//		entity.setPosition(xyz.dx, xyz.dy, xyz.dz);
//		if(!world.isRemote){
//			world.spawnEntityInWorld(entity);
//		}
//
//	}



	//	public static int getDamageFromColorName(String var1){
	//		int result = -1;
	//		for(int i=0;i<ItemDye.dyeColorNames.length;i++){
	//			if(ItemDye.dyeColorNames[i].equals(var1)){
	//				result = i;
	//			}
	//		}
	//		System.out.println(result);
	//		return result;
	//	}

	public static AxisAlignedBB getBounding(XYZPos xyz,double range,double rangeY){
		return getBounding(xyz.dx,xyz.dy,xyz.dz,range,rangeY);
	}
	public static AxisAlignedBB getBounding(int x,int y,int z,double range,double rangeY){


		AxisAlignedBB aabb = new AxisAlignedBB((double)x-range,(double)y-rangeY , (double)z-range,
				(double)x+range, (double)y+rangeY, (double)z+range);
		return aabb;
	}


	public static AxisAlignedBB getBounding(double x,double y,double z,double range,double rangeY){

		AxisAlignedBB aabb = new AxisAlignedBB(x-range,y-rangeY , z-range,
				x+range, y+rangeY, z+range);
		return aabb;
	}

	public static String joinCollection(Collection<?> col,String d){
		return col.stream().map(obj -> obj.toString()).collect(Collectors.joining(d));
	}
	public static boolean isEnemy(Entity par1,Entity player){
		if(par1!=player && !(par1 instanceof EntityTameable) && !(par1 instanceof INpc)){
			if(par1 instanceof EntityLivingBase){
				return true;
			}

		}
		return false;
	}


	public static void registerOres(List<Pair<String,ItemStack>> pairs){
		for(Pair<String,ItemStack> pair:pairs){
			OreDictionary.registerOre(pair.getLeft(), pair.getRight());
		}
	}
	public static boolean isWood(String id){
		boolean var1 = false;
		//		if(id==Blocks.log){
		//			var1 = true;
		//		}
		int[] oreids = OreDictionary.getOreIDs(new ItemStack(Block.getBlockFromName(id)));
		for(Integer oreid:oreids){
			if(OreDictionary.getOreName(oreid)!=null){
				if(OreDictionary.getOreName(oreid).equals("logWood")){
					var1 = true;
				}
			}
		}

		return var1;
	}

	public static void registerEvent(Object par1){
		MinecraftForge.EVENT_BUS.register(par1);
	}


	/**
	 * NBTにセーブするタイプのcapabilityは必ずpreinitでやること。
	 * @param type
	 * @param storage
	 * @param defaultimplimentation
	 */
	public static <T> void registerCapability(Class<T> type,IStorage<T> storage,Class<? extends T> defaultimplimentation){
		CapabilityManager.INSTANCE.register(type, storage, defaultimplimentation);
	}

	public static <T> void registerCapability(Class<T> type,IStorage<T> storage,Callable<? extends T> factory){
		CapabilityManager.INSTANCE.register(type, storage, factory);
	}
//	public static boolean isEnemy(EntityLivingBase en){
//		if(en instanceof IMob || en instanceof EntityMob){
//			return true;
//		}
//		return false;
//	}
//
//	public static boolean isEnemy(EntityPlayer ep,EntityLivingBase en){
//		if(ep == en)return false;
//		if(en instanceof IMob || en instanceof EntityMob){
//			return true;
//		}
//		return false;
//	}

	public static String getCurrentLang(){
		return Minecraft.getMinecraft().gameSettings.language;
	}




	public static List<Integer> readIntListFromBuf(ByteBuf buf){
		int length = buf.readInt();
		List<Integer> list = new ArrayList();
		for(int i=0;i<length;i++){
			list.add(buf.readInt());
		}
		return list;
	}
	public static void writeIntListToBuf(ByteBuf buf,List<Integer> intList){
		buf.writeInt(intList.size());
		for(int abn:intList){
			buf.writeInt(abn);
		}
	}

//
//	/**
//	 * return enchant:Level Pair map
//	 * @param is
//	 * @return
//	 */
//	public static Map<Enchantment,Integer> getEnchantments(ItemStack is){
//		Map map = EnchantmentHelper.getEnchantments(is);
//		Map<Enchantment,Integer> newMap = new HashMap();
//		for(Iterator<Integer> ite=map.keySet().iterator();ite.hasNext();){
//			int id = ite.next();
//			int lv = (Integer) map.get(id);
//			newMap.put(Enchantment.getEnchantmentById(id), lv);
//		}
//		return newMap;
//	}

//	public static void setEnchantments(ItemStack is,Map<Enchantment,Integer> map){
//		Map<Integer,Integer> newmap = new HashMap();
//		for(Enchantment encha:map.keySet()){
//			newmap.put(encha.effectId, map.get(encha));
//		}
//		EnchantmentHelper.setEnchantments(newmap, is);
//	}


}
