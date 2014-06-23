package com.renevo.burptweaks;

import java.util.Iterator;
import com.renevo.burptweaks.lib.Debug;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraftforge.common.MinecraftForge;

public class CommonProxy {

	public void preInitialization() {

	}
	
	public void initialization() {

	}
	
	public void postInitialization() {
		if (BurpTweaksMod.config.getDisableEndermanGriefing()) {
			com.renevo.burptweaks.entity.monster.EntityEndermanTweaks.disableEndermanGriefing();
		}
				
		if (BurpTweaksMod.config.getEnableMobSpawnRules()) {
			MinecraftForge.EVENT_BUS.register(new com.renevo.burptweaks.entity.EntitySpawnRestrictions());
		}

		if (BurpTweaksMod.config.getMobWandering()) { 
			MinecraftForge.EVENT_BUS.register(new com.renevo.burptweaks.entity.EntityMovementHandler());
		}
		
		if (BurpTweaksMod.config.getShouldAnimalsEatFood()) {
			MinecraftForge.EVENT_BUS.register(new com.renevo.burptweaks.entity.EntityAnimalEatingHandler());
		}
		
		if (BurpTweaksMod.config.getDebugDump()) {
			Debug.DebugDump();
		}

	}
}
