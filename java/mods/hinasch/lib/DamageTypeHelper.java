package mods.hinasch.lib;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;

public enum DamageTypeHelper {


	MOB("mob"),PLAYER("player"),ARROW("arrow"),UNKNOWN("unknown");


	final String str;
	private DamageTypeHelper(String str){

		this.str = str;
	}

	public String getString(){
		return this.str;
	}

	public static DamageTypeHelper fromEntity(Entity entity){
		if(entity instanceof EntityPlayer){
			return PLAYER;
		}
		if(entity instanceof EntityArrow){
			return ARROW;
		}
		if(entity instanceof EntityLivingBase){
			return MOB;
		}
		return UNKNOWN;

	}
}
