package com.renevo.burptweaks.integration;

import java.lang.reflect.Method;
import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import com.renevo.burptweaks.BurpTweaksMod;

import cpw.mods.fml.common.Loader;

public class ExNihiloIntegration implements IModIntegration {

	private static Class<?> sieveRegistry;
	private static Method registerSieveReward;
	
	@Override
	public void preInitialization() {

		
	}

	@Override
	public void initialization() {

		
	}

	@Override
	public void postInitialization() {
		if (Loader.isModLoaded("Railcraft") && BurpTweaksMod.config.enableRailcraftSieveRecipes()) {
			ArrayList<ItemStack> saltPeters = OreDictionary.getOres("dustSaltpeter");
			ArrayList<ItemStack> sulfurs = OreDictionary.getOres("dustSulfur");
			
			if (!saltPeters.isEmpty()) {
				// add the sieve result
				registerSieve(Blocks.sand, 0, saltPeters.get(0).getItem(), saltPeters.get(0).getItemDamage(), 64); // same as jungle saplings
			}
			
			if (!sulfurs.isEmpty()) { 
				// add the sieve result
				Block netherSand = Block.getBlockFromName("exnihilo:exnihilo.gravel_nether");
				if (netherSand != null) {
					registerSieve(netherSand, 0, sulfurs.get(0).getItem(), sulfurs.get(0).getItemDamage(), 128); // same as ancient spores
				} else {
					registerSieve(Blocks.soul_sand, 0, sulfurs.get(0).getItem(), sulfurs.get(0).getItemDamage(), 128); // same as ancient spores
				}
			}
		}
	}
	
	private void registerSieve(Block source, int sourceMeta, Item output, int outputMeta, int rarity) {
		try {
			if (sieveRegistry == null) {
				sieveRegistry = Class.forName("exnihilo.registries.SieveRegistry");
			}
			
			if (registerSieveReward == null) {
				registerSieveReward = sieveRegistry.getMethod("register", new Class[] { Block.class, int.class, Item.class, int.class, int.class });
			}
			
			registerSieveReward.invoke((Object)null, new Object[] { source, sourceMeta, output, outputMeta, rarity });
			
			BurpTweaksMod.log.info("Added Sieve Rewards for " + source.getUnlocalizedName() + " to " + output.getUnlocalizedName());
			
		} catch (Throwable ex) {
			BurpTweaksMod.log.error("Unable to add sieve recipe for " + source.getUnlocalizedName(), ex);
		}
	}
	
}
