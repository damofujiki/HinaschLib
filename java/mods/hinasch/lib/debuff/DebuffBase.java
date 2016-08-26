package mods.hinasch.lib.debuff;

import java.util.List;

import com.google.common.collect.Lists;
import com.mojang.realmsclient.util.Pair;

import mods.hinasch.lib.client.ClientHelper;
import mods.hinasch.lib.core.HSLib;
import mods.hinasch.lib.particle.ParticleTypeWrapper;
import mods.hinasch.lib.primitive.NameAndNumber;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;

public abstract class DebuffBase extends NameAndNumber<ResourceLocation>{

	protected final ParticleTypeWrapper PARTICLE_DEFAULT = new ParticleTypeWrapper(EnumParticleTypes.SPELL);

	protected BuffIconWrapper iconBase;
	protected BuffIconWrapper iconOverlay;
	/** 画面上に表示するか*/
	protected boolean isDisplayDebuff;
	/** 効果中のパーティクル*/
	protected ParticleTypeWrapper particle;

	/** Attributeと関連づける場合*/
	protected List<Pair<IAttribute,AttributeModifier>> attributeModifier = Lists.newArrayList();

	protected int id;

//	protected ItemIconBuff.EnumIconType iconType;

	protected DebuffBase(int num,String nameEn){
		super(new ResourceLocation(nameEn),nameEn);
		this.id = num;
//		this.particle = -1;


		this.isDisplayDebuff = true;

//		Unsaga.debuffs.putObject(number, this);
	}

	@Override
	public String toString(){
		return "ID"+this.key+":"+this.name;
	}

	public int getID(){
		return this.id;
	}
	public boolean isBadEffect(){
		return true;
	}
	/** デバフ初期化関連*/
	public DebuffBase getDebuffFromNBT(NBTTagCompound comp){
		return HSLib.debuffRegistry.getObjectById(comp.getInteger("number"));
//		return DebuffRegistry.getInstance().getDebuff(comp.getInteger("number"));
	}

	/** デバフ初期化関連*/
	public int getRemainFromNBT(NBTTagCompound comp){
		return comp.getInteger("remain");
	}
	/**
	 * デバフを表示するかどうか。
	 * @return
	 */
	public boolean isDisplayDebuff(){
		return this.isDisplayDebuff;
	}

	/**
	 * LivingDebuffのイニシャライズ用、ワールドロード時などに使われる
	 * @param strs
	 * @return
	 */
	public DebuffEffectBase initLivingDebuff(NBTTagCompound comp){
		DebuffBase debuff = HSLib.debuffRegistry.getObjectById(comp.getInteger("number"));//.getInstance().getDebuff(comp.getInteger("number"));
		int remain = comp.getInteger("remain");
		return this.createLivingDebuff(remain);
	}

	public DebuffEffectBase createLivingDebuff(int remain){

		return new DebuffEffectBase(this,remain);
	}

	/** パーティクルを返す。設定してなければデフォルトを返す*/
	public ParticleTypeWrapper getParticleNumber(){
		if(particle!=null){
			return particle;
		}
		return PARTICLE_DEFAULT;
	}

	public DebuffBase setParticle(ParticleTypeWrapper par1){
		this.particle = par1;
		return this;
	}

	public DebuffBase setBuffIconBase(Item icon){
		this.iconBase = new BuffIconWrapper(icon);
		return this;
	}

	public DebuffBase setBuffIconBase(ItemIconBuff.IconType icon){
		this.iconBase = new BuffIconWrapper(icon);
		return this;
	}

	public DebuffBase setBuffIconOverlay(Item icon){
		this.iconOverlay = new BuffIconWrapper(icon);
		return this;
	}

	public DebuffBase setBuffIconOverlay(ItemIconBuff.IconType icon){
		this.iconOverlay = new BuffIconWrapper(icon);
		return this;
	}

	/**
	 * AttributeModifierとの紐付けを指定（Attributeのタイプ(Shared～にある)、AttributeModifier本体）
	 * @param ia
	 * @param par1
	 * @return
	 */
	public DebuffBase addAttributeModifier(IAttribute ia,AttributeModifier par1){
		this.attributeModifier.add(Pair.of(ia, par1));
		return this;
	}

	public List<Pair<IAttribute,AttributeModifier>> getModifiers(){
		return this.attributeModifier;
	}
//	public AttributeModifier getAttributeModifier(){
//		return  this.attributeModifier!=null ? this.attributeModifier.second() : null;
//	}
//
//	public IAttribute getAttributeType(){
//		return this.attributeModifier!=null ? this.attributeModifier.first() : null;
//	}




	@Override
	public Class getParentClass() {
		// TODO 自動生成されたメソッド・スタブ
		return this.getClass();
	}


	public TextureAtlasSprite getDebuffIconBase(){
		if(this.iconBase!=null){
			return this.iconBase.getIcon();
		}
		return ClientHelper.getTextureAtlasSprite(HSLib.itemIconBuff, 0);
	}

	public TextureAtlasSprite getDebuffIconOverlay(){

		return this.iconOverlay != null ? this.iconOverlay.getIcon() : null;
	}


//	public ItemIconBuff.EnumIconType getIconType(){
//		return this.iconType!=null ? this.iconType : ItemIconBuff.EnumIconType.DUMMY;
//	}
//
//	public Debuff setDebuffIcon(ItemIconBuff.EnumIconType icon){
//		this.iconType = icon;
//		return this;
//	}
}
