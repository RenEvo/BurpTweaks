package com.renevo.burptweaks.blocks;

import com.renevo.burptweaks.BurpTweaksMod;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

public class Blocks {

	public static Block activator;
	
	// TODO: Add the custom item renderer
	// http://greyminecraftcoder.blogspot.com/2013/08/rendering-inventory-items.html
	// https://github.com/TheGreyGhost/ItemRendering/blob/master/src/TestItemRendering/blocks/ItemBlockNumberedFaces1Renderer.java
	// not sure i need to goto that detail, but who knows....
	
	public static void registerBlocks() {
		if (BurpTweaksMod.config.getActivatorBlockEnabled()) {
			BurpTweaksMod.log.info("Enabling Activator Block");

			activator = new BlockActivator();
			GameRegistry.registerBlock(activator, "activator");
			
			GameRegistry.addRecipe(new ItemStack(activator), 
					new Object[] { 
						"#R#", "#P#", "#C#", 
						'R', net.minecraft.init.Items.repeater,
						'P', net.minecraft.init.Blocks.piston,
						'C', net.minecraft.init.Items.comparator,
						'#', net.minecraft.init.Blocks.cobblestone 
					});
			
			GameRegistry.addRecipe(new ItemStack(activator), 
					new Object[] { 
						"#C#", "#P#", "#R#", 
						'R', net.minecraft.init.Items.repeater,
						'P', net.minecraft.init.Blocks.piston,
						'C', net.minecraft.init.Items.comparator,
						'#', net.minecraft.init.Blocks.cobblestone 
					});
		}
	}
}
