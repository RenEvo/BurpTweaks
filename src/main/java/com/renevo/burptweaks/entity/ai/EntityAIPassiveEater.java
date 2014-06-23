package com.renevo.burptweaks.entity.ai;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.item.ItemStack;

public class EntityAIPassiveEater extends EntityAIBase {

	EntityAnimal animal;
	
	public EntityAIPassiveEater(EntityAnimal animal) {
		this.animal = animal;
		this.setMutexBits(1);
	}
	
	@Override
	public boolean shouldExecute() {
		return isValidEater();
	}
	
	private boolean isValidEater() {
		return animal.getHealth() > 0 && !animal.isChild() && !animal.isInLove() && animal.getGrowingAge() == 0 && isTamed();
	}
	
	private boolean isTamed() {
		if (animal instanceof EntityTameable) {
			return ((EntityTameable)animal).isTamed();
		}
		
		return true;
	}
	
    @Override
    public boolean continueExecuting() {
        return isValidEater();
    }
	
	
    @Override
    public void startExecuting() {
        // try to eat some food...
    
    	if (!isValidEater()) {
    		return;
    	}
    	
    	List nearbyItems = animal.worldObj.getEntitiesWithinAABBExcludingEntity(animal, animal.boundingBox.expand(2.0D, 1.0D, 2.0D));
    	
    	if (nearbyItems == null || nearbyItems.size() == 0) {
    		return;
    	}
    	
    	boolean ate = false;
    	
    	for (Iterator i = nearbyItems.iterator(); i.hasNext();) {
    		Entity entity = (Entity)i.next();
    		
    		if (entity instanceof EntityItem) {
    			EntityItem eItem = (EntityItem)entity;
    			if (!animal.isBreedingItem(eItem.getEntityItem())) { 
    				continue;
    			}
    			
    			ate = true;
    			eItem.getEntityItem().stackSize--;
    			
    			if (eItem.getEntityItem().stackSize <= 0) { 
    				eItem.setDead();
    			}
    		}
    		
    		if (ate) {
    			break;
    		}
    	}
    	
    	if (ate) {
    		// this method sets them in the love status - the param is the player, used for achievements, null checks exist (looking at 1.7.2 minecraft)
    		animal.func_146082_f(null);
    		
    		// play a burp sound, cause they just ate and stuff
			animal.worldObj.playSoundAtEntity(animal, "minecraft:random.burp", 0.25F, animal.worldObj.rand.nextFloat() * 0.1F + 0.9F);
    	}
    }

}
