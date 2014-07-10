package com.renevo.burptweaks.pipes;

import java.util.LinkedList;

import cpw.mods.fml.relauncher.*;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import buildcraft.api.core.*;
import buildcraft.api.transport.*;
import buildcraft.core.inventory.Transactor;
import buildcraft.transport.*;
import buildcraft.transport.pipes.events.PipeEventItem;

public class PipeItemsInsertion extends Pipe<PipeTransportItems> {

	public PipeItemsInsertion(Item item) {
		super(new PipeTransportItems(), item);
		
		transport.allowBouncing = true;
	}

	@Override
	public int getIconIndex(ForgeDirection arg0) {
		return 0;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIconProvider getIconProvider() {
		return PipeIconProvider.INSTANCE;
	}
	
	public void eventHandler(PipeEventItem.FindDest event) {

		LinkedList<ForgeDirection> inventories = new LinkedList<ForgeDirection>();
		
		for (ForgeDirection dir : event.destinations) {
			if (dir != event.item.input) { 
				if (container.pipe.outputOpen(dir)) {
					TileEntity te = container.getTile(dir);
					
					if (te != null && te instanceof IInventory && Transactor.getTransactorFor(te).add(event.item.getItemStack(), dir.getOpposite(), false).stackSize > 0 ) {
						inventories.add(dir);
					}
				}
			}
		}
		
		// if we found stuff, use it, otherwise, fall through to normal pipe behavior
		if (!inventories.isEmpty()) {
			event.destinations.clear();
			event.destinations.addAll(inventories);
		}
	}
}
