package mods.hinasch.lib.primitive;

import com.mojang.realmsclient.util.Pair;

import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemArmor.ArmorMaterial;

public class ArmorToolEnumPair extends Pair<ToolMaterial,ArmorMaterial>{

	protected ArmorToolEnumPair(ToolMaterial first, ArmorMaterial second) {
		super(first, second);
		// TODO 自動生成されたコンストラクター・スタブ
	}

	public static ArmorToolEnumPair of(ToolMaterial t,ArmorMaterial a){
		return new ArmorToolEnumPair(t,a);
	}
}
