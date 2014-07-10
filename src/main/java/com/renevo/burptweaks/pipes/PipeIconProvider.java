package com.renevo.burptweaks.pipes;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import buildcraft.api.core.IIconProvider;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;

public class PipeIconProvider implements IIconProvider {

	public static IIconProvider INSTANCE = new PipeIconProvider();
	
	public enum TYPE {
		PipeItemsInsertion("pipeItemsInsertion");
		
		public static final TYPE[] VALUES = values();
		
		private final String iconTag;
		private IIcon icon;
		
		private TYPE(String iconTag) {
			this.iconTag = iconTag;
		}
		
		private void registerIcon(IIconRegister iconRegister) {
			icon = iconRegister.registerIcon("burptweaks:" + iconTag);
		}
		
		public IIcon getIcon() {
			return icon;
		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int pipeIconIndex) { 
		if (pipeIconIndex == -1) { 
			return null;
		}
		
		return TYPE.VALUES[pipeIconIndex].icon;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister iconRegister) {
		for (TYPE type : TYPE.VALUES) {
			type.registerIcon(iconRegister);
		}
	}
}
