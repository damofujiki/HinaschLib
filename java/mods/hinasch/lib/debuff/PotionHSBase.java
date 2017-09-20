package mods.hinasch.lib.debuff;

import java.util.List;

import com.google.common.collect.Lists;
import com.mojang.realmsclient.util.Pair;

import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.potion.PotionEffect;

public abstract class PotionHSBase implements IPotionHS{

	/** Attributeと関連づける場合*/
	protected List<Pair<IAttribute,AttributeModifier>> attributeModifier = Lists.newArrayList();


	@Override
	public void init(PotionEffect effect) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public DebuffBase addAttributeModifier(IAttribute ia, AttributeModifier par1) {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	public List<Pair<IAttribute, AttributeModifier>> getModifiers() {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

}
