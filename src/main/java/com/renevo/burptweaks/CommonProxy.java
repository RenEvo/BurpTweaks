package com.renevo.burptweaks;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.renevo.burptweaks.integration.*;
import com.renevo.burptweaks.lib.*;

import cpw.mods.fml.common.Loader;
import net.minecraftforge.common.MinecraftForge;

public class CommonProxy {

	private List<IModIntegration> modIntegrations = new ArrayList<IModIntegration>();
	
	public void preInitialization() {
		com.renevo.burptweaks.blocks.Blocks.registerBlocks();
		
		if (Loader.isModLoaded("BuildCraft|Transport")) {
			modIntegrations.add(new BuildcraftIntegration());
		}
		
		for (Iterator<IModIntegration> i = modIntegrations.iterator(); i.hasNext();) {
			i.next().preInitialization();
		}
	}
	
	public void initialization() {
		for (Iterator<IModIntegration> i = modIntegrations.iterator(); i.hasNext();) {
			i.next().initialization();
		}
	}
	
	public void postInitialization() {
		if (BurpTweaksMod.config.disableEndermanGriefing()) {
			com.renevo.burptweaks.entity.monster.EntityEndermanTweaks.disableEndermanGriefing();
		}
				
		if (BurpTweaksMod.config.enableMobSpawnRules()) {
			MinecraftForge.EVENT_BUS.register(new com.renevo.burptweaks.entity.EntitySpawnRestrictions());
		}

		if (BurpTweaksMod.config.enableMobWandering()) { 
			MinecraftForge.EVENT_BUS.register(new com.renevo.burptweaks.entity.EntityMovementHandler());
		}
		
		if (BurpTweaksMod.config.enableShouldAnimalsEatFood()) {
			MinecraftForge.EVENT_BUS.register(new com.renevo.burptweaks.entity.EntityAnimalEatingHandler());
		}
		
		if (BurpTweaksMod.config.enableBabyJumpNerf()) { 
			MinecraftForge.EVENT_BUS.register(new com.renevo.burptweaks.entity.EntityAgeableJumpHandler());
		}
		
		for (Iterator<IModIntegration> i = modIntegrations.iterator(); i.hasNext();) {
			i.next().postInitialization();
		}

		if (BurpTweaksMod.config.enableDebug()) {
			Debug.DebugDump();
		}

	}
}
