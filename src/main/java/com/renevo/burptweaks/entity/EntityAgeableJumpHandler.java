package com.renevo.burptweaks.entity;

import net.minecraft.entity.EntityAgeable;
import net.minecraftforge.event.entity.living.LivingEvent.LivingJumpEvent;

import com.renevo.burptweaks.BurpTweaksMod;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class EntityAgeableJumpHandler {
	
	public EntityAgeableJumpHandler() {
		BurpTweaksMod.log.info("BurpTweaks Baby Jump Nerf Enabled");
	}
	
	@SubscribeEvent
	public void entityJump(LivingJumpEvent event) {
		if (!(event.entityLiving instanceof EntityAgeable)) {
			return;
		}
		
		EntityAgeable agedEntity = (EntityAgeable)event.entityLiving;
		
		if (agedEntity.isChild()) {
			agedEntity.motionY = 0.20999999999999999D;
		}
	}
}
