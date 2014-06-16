package com.renevo.burptweaks.lib;

import java.util.Iterator;

import net.minecraft.block.Block;
import net.minecraft.item.Item;

import com.renevo.burptweaks.BurpTweaksMod;

public final class Debug {

	public static void DebugDump() {
		// dump item names
		for (Iterator i = Item.itemRegistry.getKeys().iterator(); i.hasNext();) {
			BurpTweaksMod.log.info("Item: " + i.next());
		}
		
		// dump block names
		for (Iterator i = Block.blockRegistry.getKeys().iterator(); i.hasNext();) {
			BurpTweaksMod.log.info("Block: " + i.next());
		}
		
		// dump mob names
		for (Iterator i = net.minecraft.entity.EntityList.classToStringMapping.values().iterator(); i.hasNext();) {
			BurpTweaksMod.log.info("Mob: " + i.next());
		}
	}
}
