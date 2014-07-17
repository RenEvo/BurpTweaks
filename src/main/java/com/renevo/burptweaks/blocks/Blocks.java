package com.renevo.burptweaks.blocks;

import com.renevo.burptweaks.BurpTweaksMod;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;

public class Blocks {

	public static Block activator;
	
	// TODO: Add the custom item renderer
	// http://greyminecraftcoder.blogspot.com/2013/08/rendering-inventory-items.html
	// https://github.com/TheGreyGhost/ItemRendering/blob/master/src/TestItemRendering/blocks/ItemBlockNumberedFaces1Renderer.java
	// not sure i need to goto that detail, but who knows....
	
	public static void registerBlocks() {
		if (BurpTweaksMod.config.getActivatorBlockEnabled()) {
			activator = new BlockActivator();
			GameRegistry.registerBlock(activator, "activator");
		}
	}
}
