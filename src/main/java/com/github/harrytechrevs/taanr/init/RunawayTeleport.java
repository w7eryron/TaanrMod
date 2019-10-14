package com.github.harrytechrevs.taanr.init;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.dimension.DimensionType;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.RightClickBlock;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class RunawayTeleport {


	// be sure you place and sleep in a bed, otherwise you will be sent here: 
		public static int locPreviousX = 0; 
		public static int locPreviousY = 220; 
		public static int locPreviousZ = 40; 
	// and probably respawn from a very long fall

		@SubscribeEvent
		public void runaway(RightClickBlock event){
			
			PlayerEntity playir = event.getPlayer(); 
			
			DimensionType currentWorldDimensionType = event.getWorld().dimension.getType();
			
		Item myClickingItem = playir.getHeldItemMainhand().getItem(); 
	
			if(myClickingItem == TutorialItems.tutorial_helmet){
				locPreviousX = playir.getPosition().getX();
				locPreviousY = playir.getPosition().getY() + 1;
				locPreviousZ = playir.getPosition().getZ();
				playir.sendMessage(new StringTextComponent("   Ruynnin Home, Eh? ").applyTextStyle(TextFormatting.GOLD)  );
				playir.setPositionAndUpdate(playir.getBedLocation(currentWorldDimensionType).getX(), playir.getBedLocation(currentWorldDimensionType).getY(), playir.getBedLocation(currentWorldDimensionType).getZ() );
			}
			
		
			
			else {  if (myClickingItem == TutorialItems.tutorial_boots){
				// teleport to previous teleport location ... 
				playir.sendMessage(new StringTextComponent("Going back for more?   ya-HOOOO!! ").applyTextStyle(TextFormatting.RED));
					
			
				playir.setPositionAndUpdate( locPreviousX , locPreviousY, locPreviousZ );
				}
		}
	}	
	
}
