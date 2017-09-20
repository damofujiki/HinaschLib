package mods.hinasch.lib.debuff;

import mods.hinasch.lib.util.Statics;
import net.minecraft.potion.Potion;

public class PotionFear extends Potion{

	protected PotionFear(boolean isBadEffectIn, int liquidColorIn) {
		super(true, Statics.COLOR_NONE);
		this.setPotionName("hinasch.potion.fear");

	}

}
