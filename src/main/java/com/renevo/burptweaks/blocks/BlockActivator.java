package com.renevo.burptweaks.blocks;

import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockPistonBase;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityDispenser;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Facing;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.util.FakePlayerFactory;

/*
 * Block that will "right click" the block in front of it when a redstone signal is applied.
 */
public class BlockActivator extends Block {

    @SideOnly(Side.CLIENT)
    protected IIcon iconTop;
    @SideOnly(Side.CLIENT)
    protected IIcon iconFrontHorizontal;
    @SideOnly(Side.CLIENT)
    protected IIcon iconFrontVertical;
    
	protected BlockActivator() {
		super(Material.rock);

		this.setCreativeTab(CreativeTabs.tabRedstone);
		this.setHardness(3.5F);
		this.setStepSound(Block.soundTypePiston);
		this.setBlockName("activator");
		this.setBlockTextureName("activator");
	}
	
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int metadata)
    {
        int facing = metadata & 7;
        return side == facing ? (facing != 1 && facing != 0 ? this.iconFrontHorizontal : this.iconFrontVertical) : (facing != 1 && facing != 0 ? (side != 1 && side != 0 ? this.blockIcon : this.iconTop) : this.iconTop);
    }

    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iconRegister)
    {
        this.blockIcon = iconRegister.registerIcon("furnace_side");
        this.iconTop = iconRegister.registerIcon("furnace_top");
        this.iconFrontHorizontal = iconRegister.registerIcon(com.renevo.burptweaks.lib.Constants.MOD_ID + ":" + this.getTextureName() + "_front_horizontal");
        this.iconFrontVertical = iconRegister.registerIcon(com.renevo.burptweaks.lib.Constants.MOD_ID + ":" +this.getTextureName() + "_front_vertical");
    }

	@Override
    public int tickRate(World world)
    {
        return 4;
    }
    
    @Override
    public void onBlockAdded(World world, int x, int y, int z)
    {
        super.onBlockAdded(world, x, y, z);
        this.setFacing(world, x, y, z);
    }
    
    @Override
    public void updateTick(World world, int x, int y, int z, Random random)
    {
        if (world.isRemote) {
        	return;
        }

    	// find out if we have a block in front of us
        int metadata = world.getBlockMetadata(x, y, z);
        EnumFacing enumFacing = net.minecraft.block.BlockDispenser.func_149937_b(metadata);
        
		Block facingBlock = world.getBlock(x + enumFacing.getFrontOffsetX(), y + enumFacing.getFrontOffsetY(), z + enumFacing.getFrontOffsetZ());

		TileEntity rearBlock = world.getTileEntity(x + (enumFacing.getFrontOffsetX() * -1), y + (enumFacing.getFrontOffsetY() * -1), z + (enumFacing.getFrontOffsetZ() * -1));
		
		EntityPlayer player = FakePlayerFactory.getMinecraft((WorldServer)world);
		
		// if there is an inventory behind us, use it....
		if (rearBlock != null && rearBlock instanceof IInventory) {
			IInventory inventory = (IInventory)rearBlock;
			for (int slot = 0; slot < inventory.getSizeInventory(); slot++) {
				if (inventory.getStackInSlot(slot) != null) {
					player.setCurrentItemOrArmor(0, inventory.getStackInSlot(slot));
					break;
				}
			}
		}
		
		// activate it (right click)
        if (facingBlock != null && facingBlock.onBlockActivated(world, x + enumFacing.getFrontOffsetX(), y + enumFacing.getFrontOffsetY(), z + enumFacing.getFrontOffsetZ(), player, 0, 0, 0, 0)) {
        	// if we did, play the sound, cause it's cool
    		world.playAuxSFX(1001, x, y, z, 0);
        }
        
        player.setCurrentItemOrArmor(0, null);
    }
    
    @Override
    public void onNeighborBlockChange(World world, int x, int y, int z, Block p_149695_5_)
    {
        boolean powered = world.isBlockIndirectlyGettingPowered(x, y, z) || world.isBlockIndirectlyGettingPowered(x, y + 1, z);
        int metadata = world.getBlockMetadata(x, y, z);
        boolean activated = (metadata & 8) != 0;

        if (powered && !activated)
        {
        	world.scheduleBlockUpdate(x, y, z, this, this.tickRate(world));
        	world.setBlockMetadataWithNotify(x, y, z, metadata | 8, 4);
        }
        else if (!powered && activated)
        {
        	world.setBlockMetadataWithNotify(x, y, z, metadata & -9, 4);
        }
    }
    
    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase placer, ItemStack stack)
    {
        int facing = BlockPistonBase.determineOrientation(world, x, y, z, placer);
        world.setBlockMetadataWithNotify(x, y, z, facing, 2);
    }
    
    private void setFacing(World worldObject, int x, int y, int z)
    {
        if (!worldObject.isRemote)
        {
            Block block = worldObject.getBlock(x, y, z - 1);
            Block block1 = worldObject.getBlock(x, y, z + 1);
            Block block2 = worldObject.getBlock(x - 1, y, z);
            Block block3 = worldObject.getBlock(x + 1, y, z);
            byte facing = 3;

            if (block.func_149730_j() && !block1.func_149730_j())
            {
            	facing = 3;
            }

            if (block1.func_149730_j() && !block.func_149730_j())
            {
            	facing = 2;
            }

            if (block2.func_149730_j() && !block3.func_149730_j())
            {
            	facing = 5;
            }

            if (block3.func_149730_j() && !block2.func_149730_j())
            {
            	facing = 4;
            }

            worldObject.setBlockMetadataWithNotify(x, y, z, facing, 2);
        }
    }
}
