package mods.hinasch.lib.util;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

/**
 * クリエイティブタブ関係
 * @author damofujiki
 *
 */
public class SimpleCreativeTab extends CreativeTabs {

	protected Item iconItem;
	protected ItemStack iconItemStack;
	public SimpleCreativeTab(String unlname) {
		super(unlname);
		// TODO 自動生成されたコンストラクター・スタブ
	}

	public CreativeTabs setIconItem(Item par1){
		this.iconItem = par1;
		return this;
	}
	@Override
	public Item getTabIconItem() {
		// TODO 自動生成されたメソッド・スタブ
		return this.iconItem;
	}

	@Override
    public ItemStack getIconItemStack()
    {
        if(this.iconItemStack!=null){
        	return this.iconItemStack;
        }
        return super.getIconItemStack();
    }
    
	public CreativeTabs setIconItemStack(ItemStack is){
		this.iconItemStack = is;
		return this;
	}
	
	/**
	 * アイコンを設定しないとヌルで落ちる。
	 * @param unlname
	 * @return
	 */
	public static SimpleCreativeTab createSimpleTab(String unlname){
		return new SimpleCreativeTab(unlname);
	}
	
	public static void setIconItemToTab(CreativeTabs tab,Item par1){
		((SimpleCreativeTab)tab).setIconItem(par1);
	}
	public static void setIconItemStackToTab(CreativeTabs tab,ItemStack par1){
		((SimpleCreativeTab)tab).setIconItemStack(par1);
	}
}
