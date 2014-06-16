package com.renevo.burptweaks.entity.monster.tweaks;

import java.util.*;

import com.renevo.burptweaks.*;

public class EntityEndermanTweaks {

	public static void disableEndermanGriefing() {
		
		BurpTweaksMod.log.info("Disabling Enderman Griefing");
		
		// set them all to no
		for (Iterator i = net.minecraft.block.Block.blockRegistry.iterator(); i.hasNext();) {
			net.minecraft.block.Block currentBlock = (net.minecraft.block.Block)i.next();
			
			// TODO: implement a white-list?
			net.minecraft.entity.monster.EntityEnderman.setCarriable(currentBlock, false);
		}
		
	}
}
