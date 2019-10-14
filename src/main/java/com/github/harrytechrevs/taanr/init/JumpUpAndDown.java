package com.github.harrytechrevs.taanr.init;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraftforge.event.entity.living.LivingEvent.LivingJumpEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class JumpUpAndDown {

	@SubscribeEvent
	public void makeJumpHigher(LivingJumpEvent event){
		if (!(event.getEntity() instanceof PlayerEntity)){
			return;
		}
		if (event.getEntity().isSneaking()){
			return;
		}
	//	if (event.getEntity().getArmorInventoryList().iterator().next().equals(ArmorItem.getItemById(3))) {
	//		return;
	//	}
		if (!(event.getEntityLiving().getItemStackFromSlot(EquipmentSlotType.FEET).getDisplayName().getString().startsWith("C") )) {
			return;
			}
	
		event.getEntity().setMotion(event.getEntity().getMotion().x * 1.3, event.getEntity().getMotion().y * 2.5, event.getEntity().getMotion().z * 1.3) ;   
	}
	
	@SubscribeEvent
	public void noFallDamage(LivingFallEvent event){
		
		if (!(event.getEntity() instanceof PlayerEntity)){
			return;
		}
		event.setCanceled(true);
	}
	
}
