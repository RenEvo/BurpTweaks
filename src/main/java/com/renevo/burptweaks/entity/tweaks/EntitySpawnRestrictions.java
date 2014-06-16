package com.renevo.burptweaks.entity.tweaks;

import java.util.Arrays;

import com.renevo.burptweaks.BurpTweaksMod;

import net.minecraft.block.Block;
import net.minecraft.util.MathHelper;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.event.entity.living.LivingSpawnEvent.CheckSpawn;
import cpw.mods.fml.common.eventhandler.Event.Result;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class EntitySpawnRestrictions {

	public EntitySpawnRestrictions() {
		BurpTweaksMod.log.info("BurpTweaks Spawn Restrictions Enabled");
	}
	
	@SubscribeEvent
	public void entitySpawn(CheckSpawn event) {
		if (event.entity == null)
			return;
		
		int x = MathHelper.floor_double(event.entity.posX);
		int y = MathHelper.floor_double(event.entity.boundingBox.minY);
		int z = MathHelper.floor_double(event.entity.posZ);
		
		// get the block under the entity, where they would spawn on
		Block spawnBlock = event.entity.worldObj.getBlock(x, y - 1, z);
		
		if (!canMobSpawnOnBlock(event.entity, spawnBlock)) {
			if (BurpTweaksMod.config.getDebugDump()) {
				BurpTweaksMod.log.warn("Stopping Spawn of Entity: " + net.minecraft.entity.EntityList.getEntityString(event.entity));
			}
			
			event.setResult(Result.DENY);
		}
	}
	
	/*
	 * Determines if an entity can spawn on the specified block based on configuration
	 * yes... you can technically block paintings from spawning, but since they never fire this event, it would be kind of pointless
	 */
	public boolean canMobSpawnOnBlock(net.minecraft.entity.Entity mob, net.minecraft.block.Block block) {
		// just don't....
		if (mob == null || block == null) { 
			return true;
		}
			
		// get the names
		String mobId = net.minecraft.entity.EntityList.getEntityString(mob);
		String blockName = net.minecraft.block.Block.blockRegistry.getNameForObject(block);
		
		// we aren't restricting
		if (!BurpTweaksMod.config.mobSpawnRules.containsKey(mobId)) { 
			return true;
		}
		
		// get the block name restrictions
		Property mobRules = BurpTweaksMod.config.mobSpawnRules.get(mobId);
		String[] blocks = mobRules.getStringList();
		
		// exists in configuration, but no restrictions set - assume they want to disable all
		if (blocks == null || blocks.length == 0) {
			return false;
		}
		
		// return if the block list contains the block it is filtering on
		return Arrays.asList(blocks).contains(blockName);
	}
}
