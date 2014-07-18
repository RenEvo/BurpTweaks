package com.renevo.burptweaks.entity;

import com.renevo.burptweaks.BurpTweaksMod;
import com.renevo.burptweaks.entity.ai.EntityAIWanderTweaked;

import net.minecraft.entity.*;
import net.minecraft.entity.ai.EntityAITasks.EntityAITaskEntry;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.boss.*;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.passive.*;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class EntityMovementHandler {

	public EntityMovementHandler() {
		BurpTweaksMod.log.info("BurpTweaks Mob Wandering Tweaks Enabled");
	}
	
	@SubscribeEvent
	public void entitySpawning(EntityJoinWorldEvent event) {
		if (event.entity instanceof EntityLiving) {
			updateLivingAI((EntityLiving)event.entity);
		}
	}
	
	/*
	 * Will remove the existing wander task, and replace with one that doesn't include issues with moving away from the mob
	 */
	private void updateLivingAI(EntityLiving entity) {
		EntityAITaskEntry wanderTask = null;
		
		for (Object task: entity.tasks.taskEntries) {
			EntityAITaskEntry taskEntry = (EntityAITaskEntry)task;
			
			if (taskEntry.action instanceof EntityAIWander) {
				wanderTask = taskEntry;
				break;
			}
		}
		
		// not a wonderer, ya a wonderer, it goes around and around and around....
		if (wanderTask == null) {
			return;			
		}
		
		// remove the "broken" one
		entity.tasks.taskEntries.remove(wanderTask);
		
		// get a movement speed
		float moveSpeed = 1.0F;
		
		// some slower entities, this totally doesn't cover modded mobs, so they will just use the 1.0 default
		if (entity instanceof EntityCreeper || entity instanceof EntityOcelot) {
			moveSpeed = 0.8F;
		} else if (entity instanceof EntityHorse) {
			moveSpeed = 0.7F;
		} else if (entity instanceof EntityIronGolem || entity instanceof EntityVillager) {
			moveSpeed = 0.6F;
		}
		
		if (BurpTweaksMod.config.enableDebug()) {
			BurpTweaksMod.log.info("Changing AI Task for Entity: " + entity + " with movement speed " + moveSpeed);
		}
		
		// set the new AI with the existing priority
		entity.tasks.addTask(((EntityAITaskEntry)wanderTask).priority, new EntityAIWanderTweaked((EntityCreature)entity, moveSpeed));
	}
}
