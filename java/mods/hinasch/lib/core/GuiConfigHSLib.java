package mods.hinasch.lib.core;

import mods.hinasch.lib.config.ConfigGuiSettings;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.config.GuiConfig;

public class GuiConfigHSLib extends GuiConfig{
	public GuiConfigHSLib(GuiScreen parentScreen) {
		super(parentScreen, ConfigGuiSettings.getConfigElements(HSLib.configFile, Configuration.CATEGORY_GENERAL), HSLib.MODID, "config.hslib", false,
				false, "HS Library Configuration", ConfigGuiSettings.getTitleLine2(HSLib.configFile));
		// TODO 自動生成されたコンストラクター・スタブ
	}
}
