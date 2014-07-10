package com.renevo.burptweaks;

import java.util.Iterator;
import com.renevo.burptweaks.lib.Debug;
import com.renevo.burptweaks.pipes.PipeProxy;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraftforge.common.MinecraftForge;

public class CommonProxy {

	protected PipeProxy pipeProxy = new PipeProxy();
	
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
		
		if (BurpTweaksMod.config.getBabyJumpNerfed()) { 
			MinecraftForge.EVENT_BUS.register(new com.renevo.burptweaks.entity.EntityAgeableJumpHandler());
		}
		
		pipeProxy.createPipes();
		
		if (BurpTweaksMod.config.getDebugDump()) {
			Debug.DebugDump();
		}

	}
}
