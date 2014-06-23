package com.renevo.burptweaks.entity;

import net.minecraft.entity.passive.EntityAnimal;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;

import com.renevo.burptweaks.BurpTweaksMod;
import com.renevo.burptweaks.entity.ai.EntityAIPassiveEater;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class EntityAnimalEatingHandler {
	
	public EntityAnimalEatingHandler() {
		BurpTweaksMod.log.info("BurpTweaks Animal Ground Eating Enabled");
	}
	
	@SubscribeEvent
	public void entitySpawning(EntityJoinWorldEvent event) {
		if (event.entity instanceof EntityAnimal) {
			EntityAnimal animal = (EntityAnimal)event.entity;
			
			animal.tasks.addTask(0, new EntityAIPassiveEater(animal));
		}
	}
}
