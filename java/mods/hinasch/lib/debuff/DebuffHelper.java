package mods.hinasch.lib.debuff;

import java.util.HashSet;
import java.util.Set;

import com.google.common.base.Optional;
import com.google.common.collect.Sets;

import mods.hinasch.lib.core.HSLib;
import mods.hinasch.lib.debuff.capability.ICustomDebuff;
import net.minecraft.entity.EntityLivingBase;

public class DebuffHelper {

	/**
	 * 単純なデバフ追加。Buff,StateはaddLivingDebuffを使う
	 * @param living
	 * @param debuff
	 * @param remain
	 */
	@Deprecated
	public static void addDebuff(EntityLivingBase living,DebuffBase debuff,int remain){
//		ExtendedLivingData.addDebuff(living, debuff, remain);
	}

	public static boolean hasCapability(EntityLivingBase living){
		return living.hasCapability(HSLib.CAP_DEBUFF, null);
	}

	public static ICustomDebuff getCapability(EntityLivingBase living){
		ICustomDebuff capa = living.getCapability(HSLib.CAP_DEBUFF, null);
		return capa;
	}
	public static void addLivingDebuff(EntityLivingBase living,DebuffEffectBase livdebuff){
		if(hasCapability(living)){
			ICustomDebuff capa = getCapability(living);
			if(!capa.hasDebuff(livdebuff.getDebuff())){
				capa.getDebuffs().add(livdebuff);
				livdebuff.onInitEvent(living);
			}else{
				removeDebuff(living,livdebuff.getDebuff());
				capa.getDebuffs().add(livdebuff);
				livdebuff.onInitEvent(living);
			}
		}

//		if(capa!=null){
//
//		}
//		ExtendedLivingData dataLiving =  getData(living);
//		if(!dataLiving.hasDebuff(livdebuff.getDebuff())){
//			dataLiving.debuffs.add(livdebuff);
//			livdebuff.onInitEvent(living);
//		}else{
//			removeDebuff(living,livdebuff.getDebuff());
//			dataLiving.debuffs.add(livdebuff);
//			livdebuff.onInitEvent(living);
//		}
	}

	public static void removeDebuff(EntityLivingBase living,DebuffBase debuff){
		ICustomDebuff capa = living.getCapability(HSLib.CAP_DEBUFF, null);
		if(capa!=null){
			if(capa.getDebuffs().isEmpty()){
				return;
			}
			Set<DebuffEffectBase> removes = new HashSet();
			for(DebuffEffectBase ldebuff:capa.getDebuffs()){
				if(ldebuff.getDebuff().key==debuff.key){

					removes.add(ldebuff);
				}
			}

			//同期エラーを防ぐ
			if(!removes.isEmpty()){
				for(DebuffEffectBase remov:removes){

					capa.getDebuffs().remove(remov);
				}
			}
		}
//		ExtendedLivingData ldata =  getData(living);
//
//		if(ldata.debuffs.isEmpty())return;
//		Set<LivingDebuffBase> removes = new HashSet();
//		for(LivingDebuffBase ldebuff:ldata.debuffs){
//			if(ldebuff.getDebuff().number==debuff.number){
//
//				removes.add(ldebuff);
//			}
//		}
//
//		//同期エラーを防ぐ
//		if(!removes.isEmpty()){
//			for(LivingDebuffBase remov:removes){
//
//				ldata.debuffs.remove(remov);
//			}
//		}
	}

	public static boolean hasDebuff(EntityLivingBase living,DebuffBase debuff){

		ICustomDebuff capaDebuff = living.getCapability(HSLib.CAP_DEBUFF, null);
		if(capaDebuff!=null){
			if(capaDebuff.getDebuffs().isEmpty()){
				return false;
			}
			for(DebuffEffectBase ldebuff:capaDebuff.getDebuffs()){
				if(ldebuff.getDebuff().key==debuff.key){
					return true;
				}
			}
		}

//		ExtendedLivingData ldata = getData(living);
//		if(ldata.debuffs.isEmpty())return false;
//		for(LivingDebuffBase ldebuff:ldata.debuffs){
//			if(ldebuff.getDebuff().number==debuff.number){
//				return true;
//			}
//		}

	return false;
	}

	public static Set<DebuffEffectBase> getActiveDebuffs(EntityLivingBase living){
		if(hasCapability(living)){
			ICustomDebuff capaDebuff = living.getCapability(HSLib.CAP_DEBUFF, null);
			return capaDebuff.getDebuffs();
		}
		return Sets.newHashSet();
	}
	public static Optional<DebuffEffectBase> getLivingDebuff(EntityLivingBase living,DebuffBase debuff){
		ICustomDebuff capaDebuff = living.getCapability(HSLib.CAP_DEBUFF, null);
		if(capaDebuff!=null){
			if(capaDebuff.getDebuffs().isEmpty()){
				return Optional.absent();
			}
			for(DebuffEffectBase ldebuff:capaDebuff.getDebuffs()){
				if(ldebuff.getDebuff().key==debuff.key){
					return Optional.of(ldebuff);
				}
			}
		}

//		ExtendedLivingData ldata =  getData(living);
//		if(ldata.debuffs.isEmpty())return Optional.absent();
//		for(LivingDebuffBase ldebuff:ldata.debuffs){
//			if(ldebuff.getDebuff().number==debuff.number){
//				return Optional.of(ldebuff);
//			}
//		}

	return Optional.absent();
	}


//	public static int getModifierAttackBuff(EntityLivingBase living){
//		int amount = 0;
//		if(getLivingDebuff(living, Unsaga.debuffs.powerup).isPresent()){
//			LivingBuff buff = (LivingBuff)getLivingDebuff(living, Unsaga.debuffs.powerup).get();
//			amount += buff.amp;
//		}
//		return amount;
//	}



}
