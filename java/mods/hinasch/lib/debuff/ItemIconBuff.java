package mods.hinasch.lib.debuff;

import java.util.List;

import com.google.common.collect.Lists;

import mods.hinasch.lib.client.ClientHelper;
import mods.hinasch.lib.iface.IIconItem;
import mods.hinasch.lib.primitive.ListHelper;
import mods.hinasch.lib.primitive.ListHelper.ToStrFunction;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.item.Item;
import net.minecraft.util.registry.RegistrySimple;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemIconBuff extends Item implements IIconItem{

	protected static final String[] iconnames = new String[]{"buff_buffbase","buff_debuffbase","buff_downbase","buff_upbase","buff_lockbase","buff_shieldbase","buff_cooling"};
	protected static final String[] iconnamesOverlay = new String[]{"buff_fire","buff_water","buff_earth","buff_wood","buff_metal"};
//	protected IIcon[] iconsBase;
//	protected IIcon[] iconsOverlay;
//	public IconHelper helper = new IconHelper();

	public ItemIconBuff(){
//		this.iconsBase = new IIcon[iconnames.length];
//		this.iconsOverlay = new IIcon[iconnamesOverlay.length];
	}
//    @SideOnly(Side.CLIENT)
//    @Override
//    public void registerIcons(IIconRegister p_94581_1_)
//    {
//        this.itemIcon = p_94581_1_.registerIcon(this.getIconString());
//
//        this.helper.registerIcons(p_94581_1_, Unsaga.DOMAIN, iconnames, iconsBase);
//        this.helper.registerIcons(p_94581_1_, Unsaga.DOMAIN, iconnamesOverlay, iconsOverlay);
//    }
	@SideOnly(Side.CLIENT)
	public static void registerModels(){
//		for(int i=0;i<iconnames.length;i++){
//			ClientHelper.registerModelMeser(Unsaga.items.iconBuff, i, Unsaga.modelHelper.getNewModelResource(iconnames[i]	, "inventory"));
//		}
//		for(int i=0;i<iconnamesOverlay.length;i++){
//			ClientHelper.registerModelMeser(Unsaga.items.iconBuff, 100+i, Unsaga.modelHelper.getNewModelResource(iconnamesOverlay[i]	, "inventory"));
//		}
	}
    public TextureAtlasSprite getBaseIcon(int num){
    	return ClientHelper.getTextureAtlasSprite(this, num);
    }
    public TextureAtlasSprite getOverlayIcon(int num){
    	return ClientHelper.getTextureAtlasSprite(this, 100+num);
    }


    public final static RegistrySimple<Integer,IconType> iconRegistry  = new RegistrySimple();
    public final static IconType BUFF_BASE = put(new IconType("iconBuff",0));
    public final static IconType COOLING = put(new IconType("cooling",1));
    public final static IconType SHIELD = put(new IconType("shieldBase",2));
    public final static IconType BUFF_UP = put(new IconType("upBase",3));
    public final static IconType LOCKED = put(new IconType("lockBase",4));
    public final static IconType FIRE = put(new IconType("fire",5));
    public final static IconType WOOD = put(new IconType("wood",6));
    public final static IconType WATER = put(new IconType("water",7));
    public final static IconType GOLD = put(new IconType("gold",8));
    public final static IconType EARTH = put(new IconType("earth",9));
    public final static IconType DEBUFF_BASE = put(new IconType("debuffBase",10));
    public final static IconType DOWNBASE = put(new IconType("downBase",11));

    public static IconType put(IconType par1){
    	iconRegistry.putObject(par1.meta, par1);
    	return par1;
    }

	public static List<String> getJsonNames(){
		return ListHelper.stream(iconRegistry.getKeys()).mapToString(new ToStrFunction<Integer>(){

			@Override
			public String apply(Integer key) {
				IconType icon = iconRegistry.getObject(key);
				return icon.getJsonName();
			}}).getList();

	}

    public static class IconType{

    	private final String jsonname;
    	private final int meta;


    	public IconType(String name,int meta){

    		this.jsonname = name;
    		this.meta = meta;
    	}

		public String getJsonName(){
			if(this==BUFF_BASE){
				return this.jsonname;
			}
			return "iconBuff."+this.jsonname;
		}

		public int getMeta(){
			return this.meta;
		}
    }


    @Deprecated
	public static enum EnumIconType{
		BUFF_BASE("iconBuff",0),COOLING("cooling",1),SHIELD("shieldBase",2),BUFF_UP("upBase",3),LOCKED("lockBase",4),
		FIRE("fire",5),WOOD("wood",6),WATER("water",7),GOLD("gold",8),EARTH("earth",9),DEBUFF_BASE("debuffBase",10),
		DOWNBASE("downBase",11);
		private final String jsonname;
		private final int meta;
		public final static EnumIconType[] LOOKUP  = new EnumIconType[values().length];
		static{
			EnumIconType[] types= EnumIconType.values();
			int index = 0;
			for(EnumIconType type:types){
				LOOKUP[index] = type;
				index += 1;
			}
		}

		public static List<EnumIconType> getLookUp(){
			return Lists.newArrayList(LOOKUP);
		}

		private EnumIconType(String jsonname,int meta){
			this.jsonname = jsonname;
			this.meta = meta;
		}


		public String getJsonName(){
			if(this==BUFF_BASE){
				return this.jsonname;
			}
			return "iconBuff."+this.jsonname;
		}

		public int getMeta(){
			return this.meta;
		}

		public static List<String> getJsonNames(){
			return ListHelper.stream(Lists.newArrayList(LOOKUP)).mapToString(new ToStrFunction<EnumIconType>(){

				@Override
				public String apply(EnumIconType input) {
					return input.getJsonName();
				}}).getList();

		}
	}
}
