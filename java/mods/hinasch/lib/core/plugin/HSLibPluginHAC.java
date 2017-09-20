package mods.hinasch.lib.core.plugin;

import defeatedcrow.hac.api.climate.ClimateAPI;
import defeatedcrow.hac.api.climate.DCHeatTier;
import defeatedcrow.hac.api.climate.DCHumidity;
import defeatedcrow.hac.api.climate.IClimate;
import mods.hinasch.lib.world.EnvironmentalManager.EnvironmentalCondition;
import mods.hinasch.lib.world.EnvironmentalManager.EnvironmentalCondition.Type;
import mods.hinasch.lib.world.XYZPos;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;

public class HSLibPluginHAC {

	public EnvironmentalCondition getCondition(World w,XYZPos pos,Biome biome,EntityLivingBase living){
		IClimate climate = ClimateAPI.calculator.getClimate(w, pos, new int[]{3,3,3});
		DCHeatTier heatTier = climate.getHeat();
		DCHumidity humid = climate.getHumidity();
		float temp = (float)heatTier.getTemp() / 25.0F;
		float hum = (float)humid.getID();
		if(heatTier.getTemp()>=DCHeatTier.HOT.getTemp()){
			return new EnvironmentalCondition(true,Type.HOT,temp,hum);
		}
		if(heatTier.getTemp()<=DCHeatTier.COLD.getTemp()){
			return new EnvironmentalCondition(true,Type.COLD,temp,hum);
		}
		if(heatTier.getTemp()>=DCHeatTier.HOT.getTemp() && humid==DCHumidity.WET){
			return new EnvironmentalCondition(true,Type.HUMID,temp,hum);
		}
		return EnvironmentalCondition.getSafeEnvironment(temp,hum);
	}
}
