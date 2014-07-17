package com.renevo.burptweaks.client;

import com.renevo.burptweaks.CommonProxy;

public class ClientProxy extends CommonProxy  {

	@Override
	public void preInitialization() {
		super.preInitialization();
	}
	
	@Override
	public void initialization() {
		super.initialization();
	}
	
	@Override
	public void postInitialization() {
		super.postInitialization();
		
		if (pipeProxy != null) { 
			pipeProxy.registerRenderers();
		}
	}
	
}
