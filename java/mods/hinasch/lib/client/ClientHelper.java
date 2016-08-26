package mods.hinasch.lib.client;

import java.util.Iterator;
import java.util.List;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;

import mods.hinasch.lib.core.HSLib;
import mods.hinasch.lib.world.XYZPos;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.ItemModelMesher;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;


@SideOnly(Side.CLIENT)
public class ClientHelper {

	@SideOnly(Side.CLIENT)
	public static RayTraceResult getMouseOver(){
		return Minecraft.getMinecraft().objectMouseOver;
	}

	@SideOnly(Side.CLIENT)
	public static World getWorld(){

		return Minecraft.getMinecraft().theWorld;
	}

	@SideOnly(Side.CLIENT)
	public static WorldClient getWorldClient(){

		return Minecraft.getMinecraft().theWorld;
	}
	@SideOnly(Side.CLIENT)
	public static void registerKeyBinding(KeyBinding... kies){

		for(KeyBinding key:kies){
			ClientRegistry.registerKeyBinding(key);
		}
	}

	public static String getCurrentLanguage(){
		return FMLClientHandler.instance().getCurrentLanguage();
	}

	public static void bindTextureToTextureManager(ResourceLocation resource){
		Minecraft.getMinecraft().getTextureManager().bindTexture(resource);
	}
	public static Render getEntityRenderer(Class<? extends Entity> clazz){
		Render render = (RenderLiving) Minecraft.getMinecraft().getRenderManager().entityRenderMap.get(clazz);
		return render;
	}
	public static GuiScreen getCurrentGui(){
		return Minecraft.getMinecraft().currentScreen;
	}
	public static ItemModelMesher getModelMesher(){
		return Minecraft.getMinecraft().getRenderItem().getItemModelMesher();
	}
	@SideOnly(Side.CLIENT)
	public static EntityPlayer getPlayer(){
		return Minecraft.getMinecraft().thePlayer;
	}


	public static Minecraft getMC(){
		return Minecraft.getMinecraft();
	}


	public static FontRenderer getFontRendererFromMCInstance(){

		return getMC().fontRendererObj;
	}

	public static TextureManager getRenderEngine(){

		return getMC().renderEngine;
	}
	public static void spawnParticle(World world,EnumParticleTypes particle,XYZPos pos,XYZPos vel){
		world.spawnParticle(particle, pos.dx, pos.dy, pos.dz, vel.dx, vel.dy, vel.dz);
	}

	/**
	 * JSONモデルネームを追加する。デフォルトはアイテムのunlocalizednameになっている。
	 * @param item
	 * @param MODID
	 * @param files
	 */
	public static void addToModelBakeryWithVariantName(Item item,final String MODID,String... files){
//		String[] filenames = new String[files.length];

		List<ModelResourceLocation> fileList = Lists.newArrayList(files).stream().map(filename ->{
			String suffix;
			if(MODID.endsWith(":")){
				suffix = "";
			}else{
				suffix = ":";
			}
			return new ModelResourceLocation(MODID+suffix+filename,"inventory");
		}).collect(Collectors.toList());

		ModelResourceLocation[] filenames = fileList.toArray(new ModelResourceLocation[fileList.size()]);
		ModelBakery.registerItemVariants(item, filenames);
	}


	public static void addToModelBakeryWithVariantName(Item item,final String MODID,List<String> files){
		addToModelBakeryWithVariantName(item, MODID, files.toArray(new String[files.size()]));
	}
	/**
	 * レンダーモデルJSONを指定。あらかじめvariantを登録したものしか参照されないので注意。
	 * （ちなみにモデルJSONが見つからない場合は立方体の市松、モデルはあるがテクスチャがない場合は四角市松）
	 *
	 * @param item
	 * @param intface　ItemMeshDefinitionのインターフェイスがついた無名クラスを登録
	 */
	public static void registerModelMeser(Item item,ItemMeshDefinition intface){
		System.out.println("register item renderer:"+item);
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(item, intface);
	}


	/**
	 * 1.7のアイコン取得のかわり。
	 * @param item
	 * @return
	 */
	public static TextureAtlasSprite getTextureAtlasSprite(Item item){
		return Minecraft.getMinecraft().getRenderItem().getItemModelMesher().getParticleIcon(item);
	}


	public static TextureAtlasSprite getTextureAtlasSprite(Item item,int meta){
		return Minecraft.getMinecraft().getRenderItem().getItemModelMesher().getParticleIcon(item,meta);
	}
	/**
	 * 手持ちのレンダーモデルJSONを指定。とくに何もない場合はこれ。
	 * ちなみに仕様として使用回数ありのアイテムでない場合はsubtypeとdamageがそのまま一致するっぽい。
	 * 使用回数ありの場合は基本的にsubtype 0だけが参照される。
	 * @param item
	 * @param subtype
	 * @param resource
	 */
	public static void registerModelMeser(Item item,int subtype,ModelResourceLocation resource){
		HSLib.logger.trace("register item renderer:"+item+" meta:"+subtype+" variant:"+resource.getResourcePath());
			Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(item, subtype,resource);


	}


	public interface ModelResourceFunction<V> extends BiFunction<V,Integer,ModelResourceLocation>{

	}
	public static <V> void registerWithSubTypes(Item item,Iterator<V> collection,ModelResourceFunction<V> func){
		int index = 0;
		for(Iterator<V> ite=collection;ite.hasNext();){
			V elm = ite.next();
			ClientHelper.registerModelMeser(item, index, func.apply(elm,index));
			index += 1;
		}
	}


	public static void registerColorItem(Item item){
		if(item instanceof IItemColor){
			IItemColor handler = (IItemColor) item;
			FMLClientHandler.instance().getClient().getItemColors().registerItemColorHandler(handler,item);
		}else{
			HSLib.logger.get().info("can't cast item "+item+" to IItemColor?");
		}
	}


	public static void registerColorItem(Block block){
		if(block instanceof IItemColor){
			IItemColor handler = (IItemColor) block;
			FMLClientHandler.instance().getClient().getItemColors().registerItemColorHandler(handler,block);

		}else{
			HSLib.logger.get().info("can'r cast block "+block+" to IItemColor?");
		}
	}

	public static void registerColorBlock(Block block){
		Preconditions.checkNotNull(block);
		Preconditions.checkArgument(block instanceof IBlockColor,"can'r cast block "+block+" to IBlockColor?");
		HSLib.logger.trace("registering Block Color Handler", block);
		if(block instanceof IBlockColor){
			IBlockColor handler = (IBlockColor) block;
			FMLClientHandler.instance().getClient().getBlockColors().registerBlockColorHandler(handler,block);

		}
	}
	public static void registerModelMesherAsStream(){

	}
	/**
	 * モデルを返す時に使う。
	 * @param MODID
	 * @param jaison
	 * @param type "inventory"などがある。調査中
	 * @return
	 */
	public static ModelResourceLocation getNewModelResource(String MODID,String jaison,String type){
		return new ModelResourceLocation(MODID+":"+jaison,type);
	}

	public static class PluralVariantsModelFactory{

		Item item;
		ModelHelper agent;
		List<String> variantNames;

		public static PluralVariantsModelFactory create(ModelHelper agent,Item item){
			return new PluralVariantsModelFactory(agent,item);
		}

		public PluralVariantsModelFactory(ModelHelper agent,Item item) {
			super();
			this.item = item;
			this.agent = agent;
		}

		public PluralVariantsModelFactory create(Item item){
			return new PluralVariantsModelFactory(this.agent,item);
		}
		public Item getItem() {
			return item;
		}

		public PluralVariantsModelFactory prepareVariants(List<String> variants){
			agent.addVariant(item,variants);
			this.variantNames = variants;
			return this;
		}

		public <T> PluralVariantsModelFactory attach(Iterator<T> col,ModelResourceFunction<T> func){
			ClientHelper.registerWithSubTypes(item, col, func);
			return this;
		}


		/**
		 * 登録したvariantsの順番でsubtypesに順番に当てはめる場合はこっち
		 * @return
		 */
		public PluralVariantsModelFactory attach(){
			ClientHelper.registerWithSubTypes(item, this.variantNames.iterator(), (name,subtype) ->agent.getNewModelResource(name, "inventory"));

			return this;
		}

		public PluralVariantsModelFactory colorItem(){
			ClientHelper.registerColorItem(item);
			return this;
		}

	}

	public static class ModelHelper{
		public final String MODID;

		public ModelHelper(String MODID){
			this.MODID = MODID;
		}
		public void registerModelMesher(Item item,ItemMeshDefinition def){
			ClientHelper.registerModelMeser(item, def);
		}
		public void registerModelMesher(Item item,int subtype,String json){
			ClientHelper.registerModelMeser(item, subtype,getNewModelResource(json,"inventory"));
		}


		public void registerModelMesher(Item item,int subtype,ModelResourceLocation resource){
			ClientHelper.registerModelMeser(item, subtype,resource);
		}

		public void registerModelMesher(Item item,int subtype,String json,String type){
			ClientHelper.registerModelMeser(item, subtype,getNewModelResource(json,type));
		}

		public void registerModels(Item[] items,int subtype,Function<Integer,String> func){
			for(int i=0;i<items.length;i++){
				ClientHelper.registerModelMeser(items[i], subtype, getNewModelResource(func.apply(i),"inventory"));
			}
		}
		public ModelResourceLocation getNewModelResource(String jaison,String type){
			return ClientHelper.getNewModelResource(MODID, jaison, type);
		}

		public void addVariant(Item item,String... files){
			ClientHelper.addToModelBakeryWithVariantName(item, MODID, files);
		}

		public void addVariant(Item item,List<String> files){
			ClientHelper.addToModelBakeryWithVariantName(item, MODID, files);
		}

		public void registerVariants(Item item,ModelResourceLocation... resources){

			ModelBakery.registerItemVariants(item, resources);
		}

	}
//	public static void renderBlockItem(RenderBlocks renderer,Block p_147800_1_,int meta){
//		Tessellator tessellator = Tessellator.instance;
//        p_147800_1_.setBlockBoundsForItemRender();
//        renderer.setRenderBoundsFromBlock(p_147800_1_);
//        GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
//        GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
//        tessellator.startDrawingQuads();
//        tessellator.setNormal(0.0F, -1.0F, 0.0F);
//        renderer.renderFaceYNeg(p_147800_1_, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSideAndMetadata(p_147800_1_, 0, meta));
//        tessellator.draw();
//
////        if (flag && this.useInventoryTint)
////        {
////            k = p_147800_1_.getRenderColor(p_147800_2_);
////            f2 = (float)(k >> 16 & 255) / 255.0F;
////            f3 = (float)(k >> 8 & 255) / 255.0F;
////            float f4 = (float)(k & 255) / 255.0F;
////            GL11.glColor4f(f2 * p_147800_3_, f3 * p_147800_3_, f4 * p_147800_3_, 1.0F);
////        }
//
//        tessellator.startDrawingQuads();
//        tessellator.setNormal(0.0F, 1.0F, 0.0F);
//        renderer.renderFaceYPos(p_147800_1_, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSideAndMetadata(p_147800_1_, 1, meta));
//        tessellator.draw();
////
////        if (flag && this.useInventoryTint)
////        {
////            GL11.glColor4f(p_147800_3_, p_147800_3_, p_147800_3_, 1.0F);
////        }
//
//        tessellator.startDrawingQuads();
//        tessellator.setNormal(0.0F, 0.0F, -1.0F);
//        renderer.renderFaceZNeg(p_147800_1_, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSideAndMetadata(p_147800_1_, 2, meta));
//        tessellator.draw();
//        tessellator.startDrawingQuads();
//        tessellator.setNormal(0.0F, 0.0F, 1.0F);
//        renderer.renderFaceZPos(p_147800_1_, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSideAndMetadata(p_147800_1_, 3, meta));
//        tessellator.draw();
//        tessellator.startDrawingQuads();
//        tessellator.setNormal(-1.0F, 0.0F, 0.0F);
//        renderer.renderFaceXNeg(p_147800_1_, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSideAndMetadata(p_147800_1_, 4, meta));
//        tessellator.draw();
//        tessellator.startDrawingQuads();
//        tessellator.setNormal(1.0F, 0.0F, 0.0F);
//        renderer.renderFaceXPos(p_147800_1_, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSideAndMetadata(p_147800_1_, 5, meta));
//        tessellator.draw();
//        GL11.glTranslatef(0.5F, 0.5F, 0.5F);
//	}

//    public static boolean renderXSqWithOffset(RenderBlocks renderer,Block p_147746_1_, int p_147746_2_, int p_147746_3_, int p_147746_4_,double offsetX,double offsetY,double offsetZ)
//    {
//        Tessellator tessellator = Tessellator.getInstance();
//        tessellator.setBrightness(p_147746_1_.getMixedBrightnessForBlock(renderer.blockAccess, p_147746_2_, p_147746_3_, p_147746_4_));
//        int l = p_147746_1_.colorMultiplier(renderer.blockAccess, p_147746_2_, p_147746_3_, p_147746_4_);
//        float f = (float)(l >> 16 & 255) / 255.0F;
//        float f1 = (float)(l >> 8 & 255) / 255.0F;
//        float f2 = (float)(l & 255) / 255.0F;
//
//        if (EntityRenderer.anaglyphEnable)
//        {
//            float f3 = (f * 30.0F + f1 * 59.0F + f2 * 11.0F) / 100.0F;
//            float f4 = (f * 30.0F + f1 * 70.0F) / 100.0F;
//            float f5 = (f * 30.0F + f2 * 70.0F) / 100.0F;
//            f = f3;
//            f1 = f4;
//            f2 = f5;
//        }
//
//        tessellator.setColorOpaque_F(f, f1, f2);
//        double d1 = (double)p_147746_2_;
//        double d2 = (double)p_147746_3_;
//        double d0 = (double)p_147746_4_;
//        long i1;
//
//        if (p_147746_1_ == Blocks.tallgrass)
//        {
//            i1 = (long)(p_147746_2_ * 3129871) ^ (long)p_147746_4_ * 116129781L ^ (long)p_147746_3_;
//            i1 = i1 * i1 * 42317861L + i1 * 11L;
//            d1 += ((double)((float)(i1 >> 16 & 15L) / 15.0F) - 0.5D) * 0.5D;
//            d2 += ((double)((float)(i1 >> 20 & 15L) / 15.0F) - 1.0D) * 0.2D;
//            d0 += ((double)((float)(i1 >> 24 & 15L) / 15.0F) - 0.5D) * 0.5D;
//        }
//        else if (p_147746_1_ == Blocks.red_flower || p_147746_1_ == Blocks.yellow_flower)
//        {
//            i1 = (long)(p_147746_2_ * 3129871) ^ (long)p_147746_4_ * 116129781L ^ (long)p_147746_3_;
//            i1 = i1 * i1 * 42317861L + i1 * 11L;
//            d1 += ((double)((float)(i1 >> 16 & 15L) / 15.0F) - 0.5D) * 0.3D;
//            d0 += ((double)((float)(i1 >> 24 & 15L) / 15.0F) - 0.5D) * 0.3D;
//        }
//
//        IIcon iicon = renderer.getBlockIconFromSideAndMetadata(p_147746_1_, 0, renderer.blockAccess.getBlockMetadata(p_147746_2_, p_147746_3_, p_147746_4_));
//        renderer.drawCrossedSquares(iicon, d1+offsetX, d2+offsetY, d0+offsetZ, 1.0F);
//        return true;
//    }

	public static RayTraceResult getMouseOverLongReach(){
		return getMouseOverLongReach(20.0D);
	}

	@SideOnly(Side.CLIENT)
	public static RayTraceResult getMouseOverLongReach(double distance){

			Entity pointedEntity = null;
			Entity newPointedEntity = null;
			EntityLivingBase pointedEntityLiving = null;
			RayTraceResult objectMouseOver = null;
			//Entity pointedEntity = null;


			Minecraft mc = Minecraft.getMinecraft();
			Entity renderViewEntity = mc.getRenderViewEntity();
			 if (renderViewEntity != null)
		        {
		            if (ClientHelper.getWorld() != null)
		            {
		            	newPointedEntity = null;
		                double d0 = distance;//(double)mc.playerController.getBlockReachDistance();
		                objectMouseOver = renderViewEntity.rayTrace(d0, 1.0F);
		                double d1 = d0;
		                Vec3d vec3 = renderViewEntity.getPositionEyes(1.0F);

	//	                if (mc.playerController.extendedReach())
	//	                {
	//	                    d0 = 20.0D;
	//	                    d1 = 20.0D;
	//	                }
	//	                else
	//	                {
	////	                    if (d0 > 3.0D)
	////	                    {
	////	                        d1 = 3.0D;
	////	                    }
	//
	//	                    d0 = d1;
	//	                }

		                if (objectMouseOver != null)
		                {
		                    d1 = objectMouseOver.hitVec.distanceTo(vec3);
		                }


		                Vec3d vec31 = renderViewEntity.getLook(1.0F);
		                Vec3d vec32 = vec3.addVector(vec31.xCoord * d0, vec31.yCoord * d0, vec31.zCoord * d0);
		                pointedEntity = null;
		                Vec3d vec33 = null;
		                float f1 = 1.0F;




		                List list = ClientHelper.getWorld().getEntitiesWithinAABBExcludingEntity(renderViewEntity, renderViewEntity.getEntityBoundingBox().addCoord(vec31.xCoord * d0, vec31.yCoord * d0, vec31.zCoord * d0).expand((double)f1, (double)f1, (double)f1));
		                double d2 = d1;

		                for (int i = 0; i < list.size(); ++i)
		                {
		                    Entity entity = (Entity)list.get(i);

		                    if (entity.canBeCollidedWith())
		                    {
		                        float f2 = entity.getCollisionBorderSize();
		                        AxisAlignedBB axisalignedbb = entity.getEntityBoundingBox().expand((double)f2, (double)f2, (double)f2);
		                        RayTraceResult RayTraceResult = axisalignedbb.calculateIntercept(vec3, vec32);

		                        if (axisalignedbb.isVecInside(vec3))
		                        {
		                            if (0.0D < d2 || d2 == 0.0D)
		                            {
		                                pointedEntity = entity;
		                                vec33 = RayTraceResult == null ? vec3 : RayTraceResult.hitVec;
		                                d2 = 0.0D;
		                            }
		                        }
		                        else if (RayTraceResult != null)
		                        {
		                            double d3 = vec3.distanceTo(RayTraceResult.hitVec);

		                            if (d3 < d2 || d2 == 0.0D)
		                            {
		                                if (entity == mc.getRenderViewEntity().getRidingEntity() && !entity.canRiderInteract())
		                                {
		                                    if (d2 == 0.0D)
		                                    {
		                                        pointedEntity = entity;
		                                        vec33 = RayTraceResult.hitVec;
		                                    }
		                                }
		                                else
		                                {
		                                    pointedEntity = entity;
		                                    vec33 = RayTraceResult.hitVec;
		                                    d2 = d3;
		                                }
		                            }
		                        }
		                    }
		                }

		                if (pointedEntity != null && (d2 < d1 || objectMouseOver == null))
		                {
		                    objectMouseOver = new RayTraceResult(pointedEntity, vec33);

		                    if (pointedEntity instanceof EntityLivingBase || pointedEntity instanceof EntityItemFrame)
		                    {
		                    	newPointedEntity = pointedEntity;
		                    }
		                }
		            }
		        }

			return objectMouseOver;
		}

}
