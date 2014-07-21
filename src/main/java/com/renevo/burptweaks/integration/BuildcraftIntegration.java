package com.renevo.burptweaks.integration;

import com.renevo.burptweaks.BurpTweaksMod;
import com.renevo.burptweaks.CommonProxy;
import com.renevo.burptweaks.pipes.PipeProxy;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BuildcraftIntegration implements IModIntegration {

    private PipeProxy pipeProxy;
	
	@Override
	public void preInitialization() {
		// create the correct pipeProxy for the current side
		Side side = FMLCommonHandler.instance().getSide();

		if (side == Side.CLIENT){
			pipeProxy = new com.renevo.burptweaks.client.pipes.PipeProxy();
		} else {
			pipeProxy = new com.renevo.burptweaks.pipes.PipeProxy();
		}
	}

	@Override
	public void initialization() {
		
	}
	
	@Override
	public void postInitialization() {
		pipeProxy.postInitialization();
		
		if (BurpTweaksMod.config.enableLavaFuelCombustion()) {
			// value pulled from legacy buildcraft configuration for lava
			// https://github.com/BuildCraft/BuildCraft/blob/master/common/buildcraft/BuildCraftEnergy.java#L264
			buildcraft.api.fuels.IronEngineFuel.addFuel("lava", 1, 20000);
		}
	}
}
