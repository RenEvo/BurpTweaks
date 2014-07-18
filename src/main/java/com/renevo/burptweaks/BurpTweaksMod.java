package com.renevo.burptweaks;

import org.apache.logging.log4j.Logger;

import com.renevo.burptweaks.lib.*;

import cpw.mods.fml.common.*;
import cpw.mods.fml.common.Mod.*;
import cpw.mods.fml.common.event.*;

@Mod(modid = Constants.MOD_ID, version = Constants.MOD_VERSION, name = Constants.MOD_NAME)
public class BurpTweaksMod
{
    @Instance(Constants.MOD_ID)
    public static BurpTweaksMod instance;
    
    @SidedProxy(clientSide = "com.renevo.burptweaks.client.ClientProxy", serverSide="com.renevo.burptweaks.CommonProxy")
    public static CommonProxy proxy;
    
    public static Config config = null;
    
    public static Logger log = null;
    
    @EventHandler
    public void preInitialization(FMLPreInitializationEvent event) {
    	log = event.getModLog();
    	config = Config.load(event.getSuggestedConfigurationFile());
    	
    	proxy.preInitialization();
    }
    
    @EventHandler
    public void initialization(FMLInitializationEvent event) {
    	proxy.initialization();
    	
    }
    
    @EventHandler
    public void postInitialization(FMLPostInitializationEvent event) {
    	proxy.postInitialization();
    	
    }
}
