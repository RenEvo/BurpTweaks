package com.renevo.burptweaks;

import java.io.File;
import java.util.*;

import net.minecraft.block.Block;
import net.minecraftforge.common.config.*;

public class Config {

	private Configuration configuration = null;
	
	private Property disableEndermanGriefing;
	private Property changeMobWanderingAI;
	private Property animalsEatNearbyFood;
	
	private Property debugDump;
	
	public Map<String, Property> mobSpawnRules;
	
	public static Config load(File configFile) {
		Config result = new Config();
		
		result.configuration = new Configuration(configFile);
		result.configuration.load();
		
		// general
		result.debugDump = result.configuration.get(com.renevo.burptweaks.lib.Constants.CONFIG_CATEGORY_GENERAL, "Debug Dump", false);
		result.debugDump.comment = "If set to true, will output all block, item, and mob names to the log - useful for the dynamic settings below";
		
		// tweaks
		result.disableEndermanGriefing = result.configuration.get(com.renevo.burptweaks.lib.Constants.CONFIG_CATEGORY_TWEAKS, "Disable Enderman Griefing", false);
		result.disableEndermanGriefing.comment = "Disables Enderman from picking up any blocks.";

		result.changeMobWanderingAI = result.configuration.get(com.renevo.burptweaks.lib.Constants.CONFIG_CATEGORY_TWEAKS, "Mob Wandering", false);
		result.changeMobWanderingAI.comment = "Changes the Mob AI for wandering to remove the age check, this allows mobs to move when further away from the player";
		
		result.animalsEatNearbyFood = result.configuration.get(com.renevo.burptweaks.lib.Constants.CONFIG_CATEGORY_TWEAKS, "Hungry Animals", false);
		result.animalsEatNearbyFood.comment = "Animals will eat nearby breeding food on the ground";
				
		// mob spawn rules
		ConfigCategory spawnRules = result.configuration.getCategory(com.renevo.burptweaks.lib.Constants.CONFIG_CATEGORY_SPAWNRULES);
		result.configuration.addCustomCategoryComment(com.renevo.burptweaks.lib.Constants.CONFIG_CATEGORY_SPAWNRULES, "Add custom mob spawning restrictions\r\nExample:\r\nS:Slime <\r\n    minecraft:stone\r\n    minecraft:grass\r\n    minecraft:dirt\r\n    minecraft:gravel\r\n    minecraft:hardened_clay\r\n    minecraft:stained_hardened_clay\r\n    minecraft:sand\r\n    minecraft:lit_pumpkin\r\n    minecraft:pumpkin\r\n    minecraft:pumpkin_stem\r\n    minecraft:sandstone\r\n    >\r\n\r\nLeave empty to prevent all spawning:\r\nS:Bat <\r\n    >\r\n\r\nAnd finally, delete the setting to use normal behaviour\r\n\r\nNOTE: This will not ADD additionally spawning, this will only prevent spawning when a mob tries to spawn.\r\nNOTE: This does not effect forced mob spawning (spawner, cursed earth, eggs, etc...)");
		result.mobSpawnRules = spawnRules.getValues();
		
		if (result.configuration.hasChanged()) {
			result.configuration.save();
		}
		
		return result;
	}
	
	public boolean getMobWandering() {
		return changeMobWanderingAI.getBoolean(false);
	}
	
	public boolean getDisableEndermanGriefing() {
		return disableEndermanGriefing.getBoolean(false);
	}

	public boolean getShouldAnimalsEatFood() {
		return animalsEatNearbyFood.getBoolean(false);
	}
	
	public boolean getEnableMobSpawnRules() { 
		return mobSpawnRules.size() > 0;
	}
	
	public boolean getDebugDump() {
		return debugDump.getBoolean(false);
	}
}
