package com.renevo.burptweaks.client.pipes;

import net.minecraftforge.client.MinecraftForgeClient;
import buildcraft.transport.TransportProxyClient;

public class PipeProxy extends com.renevo.burptweaks.pipes.PipeProxy {

	@Override
	public void postInitialization() {
		super.postInitialization();
		
		if (pipeInsertion != null) {
			MinecraftForgeClient.registerItemRenderer(pipeInsertion, TransportProxyClient.pipeItemRenderer);
		}
	}
}
