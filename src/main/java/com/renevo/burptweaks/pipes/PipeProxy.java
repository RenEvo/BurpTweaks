package com.renevo.burptweaks.pipes;

import com.renevo.burptweaks.BurpTweaksMod;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import buildcraft.core.CreativeTabBuildCraft;
import buildcraft.transport.BlockGenericPipe;
import buildcraft.transport.ItemPipe;
import buildcraft.transport.Pipe;
import buildcraft.transport.PipeToolTipManager;
import buildcraft.transport.TransportProxyClient;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.MinecraftForgeClient;

public class PipeProxy {

	public static Item pipeInsertion;
		
	public void postInitialization() {
		if (BurpTweaksMod.config.enableInsertionPipe()) {
			com.renevo.burptweaks.BurpTweaksMod.log.info("Creating Insertion Pipe");
			
			pipeInsertion = createPipe(PipeItemsInsertion.class);
			GameRegistry.addRecipe(new ItemStack(pipeInsertion, 8), new Object[] { "#X#", 'X', Blocks.glass, '#', Blocks.lapis_block });
		}
	}
	
	private Item createPipe(Class<? extends Pipe> pipeClass) {
		ItemPipe pipe = BlockGenericPipe.registerPipe(pipeClass, CreativeTabBuildCraft.PIPES);
		pipe.setUnlocalizedName(pipeClass.getSimpleName());
		
		return pipe;
	}
}
