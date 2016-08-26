package mods.hinasch.lib.client;

import mods.hinasch.lib.DebugLog;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.TextureStitchEvent;

/**
 * 
 * @author http://minecraftjp.info/modding/index.php/%E3%83%91%E3%83%BC%E3%83%86%E3%82%A3%E3%82%AF%E3%83%AB%E3%81%AE%E8%BF%BD%E5%8A%A0
 *
 */
@Deprecated
public abstract class ParticleHandler {
	
	protected String modid;
	protected String[] iconNames;
	protected TextureAtlasSprite[] iicons;
	public DebugLog logger;
	public static ParticleHandler INSTANCE;
	
	protected ParticleHandler(){
		
	}
	protected ParticleHandler(String modid,String... iconnames){
		this.modid  = modid;
		this.iconNames = iconnames;
	}
	

//	public ParticleHandler setIconnames(String... icons){
//		this.iiconNames = icons;
//		return this;
//	}
//	
//	public ParticleHandler setPath(String path){
//		this.path = path;
//		return this;
//	}
//	public static ParticleHandler getInstance(){
//		if(INSTANCE==null){
//			INSTANCE = new ParticleHandler();
//		}
//		return INSTANCE;
//	}
	//@SubscribeEvent
	
	public void test(){
//		IIconCreator register = new IIconCreator(){
//
//			@Override
//			public void registerSprites(TextureMap iconRegistry) {
//				iconRegistry.registerSprite(location)
//				
//			}};
	}
	
	abstract public void registerParticlesEvent(TextureStitchEvent.Pre e);
//{
//		if(e.map.getTextureType()==1){
//			
//			this.getInstance().registerIcons(e.map);
//		}
//	}
	
	

	public void registerIcons(TextureMap par1IconRegister) {
		iicons = new TextureAtlasSprite[iconNames.length];
		for(int i = 0; i < iicons.length; ++i) {
			ResourceLocation rl = new ResourceLocation(modid,"items/particles/"+iconNames[i]);
			iicons[i] = par1IconRegister.registerSprite(rl);
			par1IconRegister.setTextureEntry(modid+":"+iconNames[i], iicons[i]);
			if(this.logger!=null){
				this.logger.log("register particle:"+iicons[i]);
				if(iicons[i]!=null){
					this.logger.log("done!");
				}else{
					this.logger.log("error!");
				}
			}
		}
	}
	

	public TextureAtlasSprite getIcon(int num) {
		return iicons[num];
	}
}
