package mods.hinasch.lib.debuff;

import com.google.common.base.Optional;

import mods.hinasch.lib.client.ClientHelper;
import mods.hinasch.lib.core.HSLib;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.item.Item;

public class BuffIconWrapper{
	protected Item item;
	protected Optional<ItemIconBuff.IconType> iconNumber = Optional.absent();

	public enum EnumIconWrapperSelector {OVERLAY,BASE};

	public BuffIconWrapper(ItemIconBuff.IconType iconnum){
		this.iconNumber = Optional.of(iconnum);
	}

	public BuffIconWrapper(Item item){
		this.item = item;
	}

	public TextureAtlasSprite getIcon(){
		if(this.item!=null){
			return ClientHelper.getTextureAtlasSprite(this.item);
		}
		if(iconNumber.isPresent()){
			return ClientHelper.getTextureAtlasSprite(HSLib.itemIconBuff, iconNumber.get().getMeta());
		}

		return null;
	}
}
