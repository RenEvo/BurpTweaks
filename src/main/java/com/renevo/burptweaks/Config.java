package com.renevo.burptweaks;

import java.io.File;

import net.minecraftforge.common.config.*;

public class Config {

	private Configuration configuration = null;
	
	private Property disableEndermanGriefing;
	
	public static Config load(File configFile) {
		Config result = new Config();
		
		result.configuration = new Configuration(configFile);
		result.configuration.load();
		
		result.disableEndermanGriefing = result.configuration.get(com.renevo.burptweaks.lib.Constants.CONFIG_CATEGORY_TWEAKS, "Disable Enderman Griefing", true);
		result.disableEndermanGriefing.comment = "Disables Enderman from picking up any blocks.";

		if (result.configuration.hasChanged()) {
			result.configuration.save();
		}
		
		return result;
	}
	
	public boolean getDisableEndermanGriefing() {
		return disableEndermanGriefing.getBoolean(true);
	}
}
