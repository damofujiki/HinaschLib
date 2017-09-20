package mods.hinasch.lib.debuff;

import java.util.Iterator;
import java.util.List;

import com.google.common.collect.Lists;
import com.mojang.realmsclient.util.Pair;

import mods.hinasch.lib.core.HSLib;
import mods.hinasch.lib.iface.INBTWritable;
import mods.hinasch.lib.network.PacketSyncDebuffNew;
import mods.hinasch.lib.network.PacketUtil;
import mods.hinasch.lib.particle.PacketParticle;
import mods.hinasch.lib.world.WorldHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;



public class DebuffEffectBase implements INBTWritable{

	protected DebuffBase debuff;
	protected int remain;

	//これを継承してステートにもできる。initとtostringを上書きする。

	/**
	 * DebuffEffectでもよかったかも…
	 * 新しいStateなど作る時はtoStringを上書きする。
	 *
	 * @param par1 元となるDebuff
	 * @param par2 時間
	 */
	public DebuffEffectBase(DebuffBase par1,int par2){
		this.debuff = par1;
		this.remain = par2;


	}

	public DebuffBase getDebuff(){
		return this.debuff;
	}

	public int getRemaining(){
		return this.remain;
	}


	/**
	 * 毎秒の処理。
	 * @param living
	 */
	public void updateRemaining(EntityLivingBase living) {
	}

	/**
	 * Tickごとに。
	 * @param living
	 */
	public void updateRemainingByTick(EntityLivingBase living){
		HSLib.logger.trace(this.debuff.getName()+" remain:"+this.remain);
		this.remain -= 1;
		if(this.remain<=0){
			this.remain = 0;
		}




		if(this.debuff.getParticleNumber()!=null){
			if(living.getRNG().nextInt(4)<=1){
				HSLib.core().getParticlePacketSender().sendToAllAround(PacketParticle.toEntity(debuff.getParticleNumber(), living, 3), PacketUtil.getTargetPointNear(living));

			}
		}


	}

	/**
	 * デバフの効果が切れたかどうかのフラグ。
	 * デバフが消されるかどうかの判定に使う。
	 * @return
	 */
	public boolean isExpired(){

		if(this.remain<=0){
//			Unsaga.debug(this.debuff.name+" is expired.");

			return true;
		}

		return false;
	}

	@Override  //toStringとは別に作ってもいいかも
	public String toString(){
		return this.debuff.key+":"+this.remain;
	}

	public DebuffBase getDebuff(NBTTagCompound nbt){
		return HSLib.core().debuffRegistry.getObjectById(nbt.getInteger("number"));
//		return DebuffRegistry.getInstance().getDebuff(nbt.getInteger("number"));
	}

	public static DebuffEffectBase buildFromNBT(NBTTagCompound data){
//		String[] strs = data.split(":");
		DebuffBase debuff = HSLib.core().debuffRegistry.getObjectById(data.getInteger("number"));
//		DebuffBase debuff = Unsaga.debuffs.getDebuff(data.getInteger("number"));
		DebuffEffectBase output;
		try{
			output = debuff.initLivingDebuff(data);
		}catch(Exception e){
			e.printStackTrace();
			output = null;
		}

		HSLib.logger.trace("restroreing... buildFromNBT "+debuff.getName());
//		Unsaga.debug(output.debuff.name+"復元");
		return output;

	}


	protected String buildSaveString(Object... strs){
		List<Object> list = Lists.newArrayList(strs);
		StringBuilder saveString = new StringBuilder();
		for(Iterator<Object> ite=list.iterator();ite.hasNext();){
			String str = ite.next().toString();
			saveString.append(str);
			if(ite.hasNext()){
				saveString.append(":");
			}
		}

		return new String(saveString);

	}

	//TODO : このへん要改変
	public void onExpiredEvent(EntityLivingBase living) {
		HSLib.logger.trace(this.debuff.getName()+" is expired.");
		if(!this.debuff.attributeModifier.isEmpty()){
			for(Pair<IAttribute,AttributeModifier> entry:this.debuff.attributeModifier){
				IAttributeInstance entityAttribute = living.getEntityAttribute(entry.first());
				if(entityAttribute!=null){
					if(entityAttribute.getModifier(entry.second().getID())!=null){
						entityAttribute.removeModifier(entry.second());
//						Unsaga.debug("おわりしました："+living.getEntityAttribute(this.debuff.getAttributeType()).getAttributeValue());
					}
				}
			}
		}


			//			living.getEntityAttribute(this.debuff.getAttributeType()).removeModifier(this.debuff.getAttributeModifier());
			//			Unsaga.debug("おわりしました："+living.getEntityAttribute(this.debuff.getAttributeType()).getAttributeValue());


		//TODO:デバフの終了を通知
		if(living instanceof EntityPlayer){

		}
		if(WorldHelper.isServer(living.worldObj)){
//			PacketSyncDebuff psd = new PacketSyncDebuff(living.getEntityId(),this.debuff.number);
			HSLib.core().getPacketDispatcher().sendToAll(PacketSyncDebuffNew.syncEnd(living, getDebuff()));
//			Unsaga.packetDispatcher.sendToAll(psd);
		}
		//		if(living.worldObj.isRemote){
		//			PacketSyncDebuff psd = new PacketSyncDebuff(living.getEntityId(),this.debuff.number);
		//			Unsaga.packetPipeline.sendToServer(psd);
		//		}EntityPlayer


	}

	/**
	 * デバフが復元、作成された時に呼び出される
	 * @param living
	 */
	public void onInitEvent(EntityLivingBase living) {
		if(WorldHelper.isServer(living.worldObj)){
//			Unsaga.debug("デバフをクライアントへ送ります",this.getClass());
//			PacketSyncDebuff psd = new PacketSyncDebuff(living.getEntityId(),this.debuff.number,this.remain);
			HSLib.core().getPacketDispatcher().sendToAll(PacketSyncDebuffNew.syncStart(living, this));
//			Unsaga.packetDispatcher.sendToAll(psd);
		}

		if(!this.debuff.getModifiers().isEmpty()){
			for(Pair<IAttribute,AttributeModifier> entry:this.debuff.getModifiers()){
				IAttributeInstance entityAttribute = living.getEntityAttribute(entry.first());
				if(entityAttribute!=null){
					if(entityAttribute.getModifier(entry.second().getID())==null){
						entityAttribute.applyModifier(entry.second());
//						Unsaga.debug(this.debuff.getAttributeModifier().getName()+"アプライしました："+living.getEntityAttribute(this.debuff.getAttributeType()).getAttributeValue());
					}
				}
			}

		}

	}

	/**
	 * パケットからの同期に使用
	 * @param remain
	 */
	public void syncRemain(int remain){
		this.remain = remain;
	}



	@Override
	public void writeToNBT(NBTTagCompound stream) {
		stream.setInteger("number", this.debuff.getID());
		stream.setInteger("remain", this.remain);
	}
}
