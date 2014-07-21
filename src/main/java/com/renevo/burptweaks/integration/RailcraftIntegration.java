package com.renevo.burptweaks.integration;

import mods.railcraft.api.crafting.IRockCrusherRecipe;
import mods.railcraft.api.crafting.RailcraftCraftingManager;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import com.renevo.burptweaks.BurpTweaksMod;

import cpw.mods.fml.common.Loader;

public class RailcraftIntegration implements IModIntegration {

	@Override
	public void preInitialization() {

		
	}

	@Override
	public void initialization() {

		
	}

	@Override
	public void postInitialization() {
		if (Loader.isModLoaded("exnihilo") && BurpTweaksMod.config.enableRockCrusherOreDustRecipes()) {

			// add sand to dust
			Block blockDust = Block.getBlockFromName("exnihilo:dust");
			if (blockDust != null) {
				IRockCrusherRecipe recipe = RailcraftCraftingManager.rockCrusher.createNewRecipe(new ItemStack(Blocks.sand), false, false);
				recipe.addOutput(new ItemStack(blockDust), 1F);
				recipe.addOutput(new ItemStack(blockDust), .1F); // 10% chance of doubling up on dusts
				
				BurpTweaksMod.log.info("Added Sand to Dust Rock Crusher Recipe");
			}
			
			// add netherrack to netherrack gravel 
			Block blockNetherGravel = Block.getBlockFromName("exnihilo:exnihilo.gravel_nether");
		    if (blockNetherGravel != null) {
				IRockCrusherRecipe recipe = RailcraftCraftingManager.rockCrusher.createNewRecipe(new ItemStack(Blocks.netherrack), false, false);
				recipe.addOutput(new ItemStack(blockNetherGravel), 1F);
				recipe.addOutput(new ItemStack(Items.redstone), .666F);
				recipe.addOutput(new ItemStack(Items.gold_nugget), .1F);
				
				BurpTweaksMod.log.info("Added Netherrack to Nether Gravel Rock Crusher Recipe");
			}
			
			Block blockEnderGravel = Block.getBlockFromName("exnihilo:exnihilo.gravel_ender");
			if (blockEnderGravel != null) {
				IRockCrusherRecipe recipe = RailcraftCraftingManager.rockCrusher.createNewRecipe(new ItemStack(Blocks.end_stone), false, false);
				recipe.addOutput(new ItemStack(blockEnderGravel), 1F);
				recipe.addOutput(new ItemStack(Items.glowstone_dust), .666F);
				
				BurpTweaksMod.log.info("Added Endstone to Ender Gravel Rock Crusher Recipe");
			}
			
			// add ore processing tree - pulled from ex nihilo's orelist
			addExNihiloOreTree("iron");
			addExNihiloOreTree("gold");
			addExNihiloOreTree("copper");
			addExNihiloOreTree("tin");
			addExNihiloOreTree("silver");
			addExNihiloOreTree("lead");
			addExNihiloOreTree("nickel");
			addExNihiloOreTree("platinum");
			addExNihiloOreTree("aluminum");
			addExNihiloOreTree("osmium");
		}
	}

	private void addExNihiloOreTree(String name) {
		// start backwards, it is easier....
		Item itemPowdered = null;
		Block blockDust = null;
		
		if (Item.itemRegistry.containsKey("exnihilo:exnihilo." + name + "_powdered")) {
			itemPowdered = (Item)Item.itemRegistry.getObject("exnihilo:exnihilo." + name + "_powdered");
		}
		if (Block.blockRegistry.containsKey("exnihilo:" + name + "_dust")) {
			blockDust = Block.getBlockFromName("exnihilo:" + name + "_dust");
		}
		
		if (itemPowdered == null || blockDust == null) {
			return; // item does not exist
		}
		
		Item itemCrushed = null;
		Block blockSand = null;
		
		if (Item.itemRegistry.containsKey("exnihilo:exnihilo." + name + "_crushed")) {
			itemCrushed = (Item)Item.itemRegistry.getObject("exnihilo:exnihilo." + name + "_crushed");
		}
		if (Block.blockRegistry.containsKey("exnihilo:" + name + "_sand")) {
			blockSand = Block.getBlockFromName("exnihilo:" + name + "_sand");
		}
		
		if (itemCrushed == null || blockSand == null) {
			return; // item does not exist
		}
		
		Item itemGravel = null;
		Block blockGravel = null;
		
		if (Item.itemRegistry.containsKey("exnihilo:" + name + "_gravel")) {
			itemGravel = (Item)Item.itemRegistry.getObject("exnihilo:" + name + "_gravel");
		}
		if (Block.blockRegistry.containsKey("exnihilo:" + name + "_gravel")) {
			blockGravel = Block.getBlockFromName("exnihilo:" + name + "_gravel");
		}
		
		if (itemGravel == null || blockGravel == null) {
			return; // item does not exist
		}
		
		Block blockNetherGravel = null;

		if (Block.blockRegistry.containsKey("exnihilo:nether_" + name + "_gravel")) {
			blockNetherGravel = Block.getBlockFromName("exnihilo:nether_" + name + "_gravel");
		}

		Block blockEnderGravel = null;

		if (Block.blockRegistry.containsKey("exnihilo:ender_" + name + "_gravel")) {
			blockEnderGravel = Block.getBlockFromName("exnihilo:ender_" + name + "_gravel");
		}
		
		IRockCrusherRecipe recipe = null;
		
		// sand to dust
		recipe = RailcraftCraftingManager.rockCrusher.createNewRecipe(new ItemStack(blockSand), false, false);
		recipe.addOutput(new ItemStack(blockDust), 1F);
		recipe.addOutput(new ItemStack(blockDust), 1F);
		recipe.addOutput(new ItemStack(itemPowdered), .5F);
		recipe.addOutput(new ItemStack(itemPowdered), .25F);
		BurpTweaksMod.log.info("Added " + name + " sand to " + name + " dust Rock Crusher Recipe");
		
		// gravel to sand
		recipe = RailcraftCraftingManager.rockCrusher.createNewRecipe(new ItemStack(blockGravel), false, false);
		recipe.addOutput(new ItemStack(blockSand), 1F);
		recipe.addOutput(new ItemStack(itemCrushed), .5F);
		recipe.addOutput(new ItemStack(itemCrushed), .25F);
		BurpTweaksMod.log.info("Added " + name + " gravel to " + name + " sand Rock Crusher Recipe");
		
		// nether gravel to sand
		if (blockNetherGravel != null) { 
			recipe = RailcraftCraftingManager.rockCrusher.createNewRecipe(new ItemStack(blockNetherGravel), false, false);
			recipe.addOutput(new ItemStack(blockSand), 1F);
			recipe.addOutput(new ItemStack(itemCrushed), .5F);
			recipe.addOutput(new ItemStack(itemCrushed), .25F);
			BurpTweaksMod.log.info("Added nether " + name + " gravel to " + name + " sand Rock Crusher Recipe");
		}
		
		// ender gravel to sand
		if (blockEnderGravel != null) { 
			recipe = RailcraftCraftingManager.rockCrusher.createNewRecipe(new ItemStack(blockEnderGravel), false, false);
			recipe.addOutput(new ItemStack(blockSand), 1F);
			recipe.addOutput(new ItemStack(itemCrushed), .5F);
			recipe.addOutput(new ItemStack(itemCrushed), .25F);
			BurpTweaksMod.log.info("Added ender " + name + " gravel to " + name + " sand Rock Crusher Recipe");
		}
	}
}
