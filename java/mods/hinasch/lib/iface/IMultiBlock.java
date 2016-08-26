package mods.hinasch.lib.iface;

import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

/**
 * リマインダー
 * 
 *
 */
@Deprecated
public interface IMultiBlock {

	//public IconHelper helper = new IconHelper();
	public int damageDropped(int par1);
	public void getSubBlocks(Item par1, CreativeTabs par2CreativeTabs, List par3List);
    //public void registerBlockIcons(IIconRegister iconregister);

//    public class IconHelper{
//
//    	public void registerIcons(IIconRegister iconregister,String domain,String[] iconnames,IIcon[] icons){
//    		for(int i=0;i<icons.length;i++){
//    			icons[i] = iconregister.registerIcon(domain+":"+iconnames[i]);
//    		}
//    	}
//    }
}
