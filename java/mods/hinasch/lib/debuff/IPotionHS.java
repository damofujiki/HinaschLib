package mods.hinasch.lib.debuff;

import java.util.List;

import com.mojang.realmsclient.util.Pair;

import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

public interface IPotionHS {

	public Potion getPotion();
	public void init(PotionEffect effect);
	public DebuffBase addAttributeModifier(IAttribute ia,AttributeModifier par1);
	public List<Pair<IAttribute,AttributeModifier>> getModifiers();
}
