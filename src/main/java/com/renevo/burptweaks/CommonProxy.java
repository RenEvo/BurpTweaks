package com.renevo.burptweaks;

public class CommonProxy {

	public void preInitialization() {
		BurpTweaksMod.log.info("Pre-Initialization");
	}
	
	public void initialization() {
		BurpTweaksMod.log.info("Initialization");
	}
	
	public void postInitialization() {
		BurpTweaksMod.log.info("Post-Initialization");
		
		if (BurpTweaksMod.config.getDisableEndermanGriefing()) {
			com.renevo.burptweaks.entity.monster.tweaks.EntityEndermanTweaks.disableEndermanGriefing();
		}
			
	}
}
