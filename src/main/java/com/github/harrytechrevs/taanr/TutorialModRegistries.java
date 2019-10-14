package com.github.harrytechrevs.taanr;

import org.apache.logging.log4j.Logger;

import com.github.harrytechrevs.taanr.init.TaanrFoodList;
import com.github.harrytechrevs.taanr.init.TutorialArmorMaterials;
import com.github.harrytechrevs.taanr.init.TutorialBlocks;
import com.github.harrytechrevs.taanr.init.TutorialEntities;
import com.github.harrytechrevs.taanr.init.TutorialItems;
import com.github.harrytechrevs.taanr.init.TutorialToolMaterials;
import com.github.harrytechrevs.taanr.items.CustomAxeItem;
import com.github.harrytechrevs.taanr.items.CustomPickaxeItem;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityType;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.BlockItem;
import net.minecraft.item.HoeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ShovelItem;
import net.minecraft.item.SwordItem;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
public class TutorialModRegistries 
{
	public static final ItemGroup TUTORIAL = TutorialMod.TUTORIAL;
	public static final Logger LOGGER = TutorialMod.LOGGER;
	public static final String MODID = TutorialMod.MODID;
	
	@SubscribeEvent
	public static void registerItems(final RegistryEvent.Register<Item> event)
	{
		event.getRegistry().registerAll
		(
			
				
			TutorialItems.tutorial_item = new Item(new Item.Properties().group(TUTORIAL)).setRegistryName(location("tutorial_item")),
			TutorialItems.taanrblock_item = new Item(new Item.Properties().group(TUTORIAL)).setRegistryName(location("taanrblock_item")),
			TutorialItems.taanressense_item = new Item(new Item.Properties().group(TUTORIAL)).setRegistryName(location("taanressense_item")),
			TutorialItems.taanrpod_item = new Item(new Item.Properties().group(TUTORIAL).food(TaanrFoodList.taanrfood)).setRegistryName(location("taanrpod_item")),
		
			TutorialItems.tutorial_axe = new CustomAxeItem(TutorialToolMaterials.tutorial, -1.0f, 6.0f, new Item.Properties().group(TUTORIAL)).setRegistryName(location("tutorial_axe")),
			TutorialItems.tutorial_hoe = new HoeItem(TutorialToolMaterials.tutorial, 6.0f, new Item.Properties().group(TUTORIAL)).setRegistryName(location("tutorial_hoe")),
			TutorialItems.tutorial_pickaxe = new CustomPickaxeItem(TutorialToolMaterials.tutorial, -2, 6.0f, new Item.Properties().group(TUTORIAL)).setRegistryName(location("tutorial_pickaxe")),
			TutorialItems.tutorial_shovel = new ShovelItem(TutorialToolMaterials.tutorial, -3.0f, 6.0f, new Item.Properties().group(TUTORIAL)).setRegistryName(location("tutorial_shovel")),
			TutorialItems.tutorial_sword = new SwordItem(TutorialToolMaterials.tutorial, 0, 6.0f, new Item.Properties().group(TUTORIAL)).setRegistryName(location("tutorial_sword")),
		
			TutorialItems.taanrenchanted_sword = new SwordItem(TutorialToolMaterials.tutorial, 9, 9.0f, new Item.Properties().group(TUTORIAL)).setRegistryName(location("taanrenchanted_sword")),	
			TutorialItems.taanr_hammernine = new CustomPickaxeItem(TutorialToolMaterials.tutorial, -2, 6.0f, new Item.Properties().group(TUTORIAL)).setRegistryName(location("taanr_hammernine")),
			
			TutorialItems.tutorial_helmet = new ArmorItem(TutorialArmorMaterials.tutorial, EquipmentSlotType.HEAD, new Item.Properties().group(TUTORIAL)).setRegistryName(location("tutorial_helmet")),
			TutorialItems.tutorial_chestplate = new ArmorItem(TutorialArmorMaterials.tutorial, EquipmentSlotType.CHEST, new Item.Properties().group(TUTORIAL)).setRegistryName(location("tutorial_chestplate")),
			TutorialItems.tutorial_leggings = new ArmorItem(TutorialArmorMaterials.tutorial, EquipmentSlotType.LEGS, new Item.Properties().group(TUTORIAL)).setRegistryName(location("tutorial_leggings")),
			TutorialItems.tutorial_boots = new ArmorItem(TutorialArmorMaterials.tutorial, EquipmentSlotType.FEET, new Item.Properties().group(TUTORIAL)).setRegistryName(location("tutorial_boots")),
		
			TutorialItems.taanr_cloudhelmet = new ArmorItem(TutorialArmorMaterials.tutorial, EquipmentSlotType.HEAD, new Item.Properties().group(TUTORIAL)).setRegistryName(location("taanr_cloudhelmet")),
			TutorialItems.taanr_cloudboots = new ArmorItem(TutorialArmorMaterials.tutorial, EquipmentSlotType.FEET, new Item.Properties().group(TUTORIAL)).setRegistryName(location("taanr_cloudboots")),
			
			
			TutorialItems.tutorial_block = new BlockItem(TutorialBlocks.tutorial_block, new Item.Properties().group(TUTORIAL)).setRegistryName(TutorialBlocks.tutorial_block.getRegistryName()),
			TutorialItems.taanr_block = new BlockItem(TutorialBlocks.taanr_block, new Item.Properties().group(TUTORIAL)).setRegistryName(TutorialBlocks.taanr_block.getRegistryName()),
			TutorialItems.taanressense_block = new BlockItem(TutorialBlocks.taanressense_block, new Item.Properties().group(TUTORIAL)).setRegistryName(TutorialBlocks.taanressense_block.getRegistryName()),
			
			TutorialItems.tutorial_ore = new BlockItem(TutorialBlocks.tutorial_ore, new Item.Properties().group(TUTORIAL)).setRegistryName(TutorialBlocks.tutorial_ore.getRegistryName()),
			TutorialItems.tutorial_ore_nether = new BlockItem(TutorialBlocks.tutorial_ore_nether, new Item.Properties().group(TUTORIAL)).setRegistryName(TutorialBlocks.tutorial_ore_nether.getRegistryName()),
			TutorialItems.tutorial_ore_end = new BlockItem(TutorialBlocks.tutorial_ore_end, new Item.Properties().group(TUTORIAL)).setRegistryName(TutorialBlocks.tutorial_ore_end.getRegistryName())

		);
		
		TutorialEntities.registerEntitySpawnEggs(event);
		
		LOGGER.info("Items registered.");
	}
	
	@SubscribeEvent
	public static void registerBlocks(final RegistryEvent.Register<Block> event)
	{
		event.getRegistry().registerAll
		(
	
			TutorialBlocks.tutorial_block = new Block(Block.Properties.create(Material.IRON).hardnessAndResistance(2.0f, 3.0f).lightValue(5).sound(SoundType.METAL)).setRegistryName(location("tutorial_block")),
	
			TutorialBlocks.taanr_block = new Block(Block.Properties.create(Material.ROCK).hardnessAndResistance(2.0f, 3.0f).lightValue(10).sound(SoundType.WET_GRASS)).setRegistryName(location("taanr_block")),
			TutorialBlocks.taanressense_block = new Block(Block.Properties.create(Material.WOOL).hardnessAndResistance(2.0f, 3.0f).lightValue(10).sound(SoundType.WET_GRASS)).setRegistryName(location("taanressense_block")),

			TutorialBlocks.tutorial_ore = new Block(Block.Properties.create(Material.ROCK).hardnessAndResistance(2.0f, 3.0f).lightValue(5).sound(SoundType.METAL)).setRegistryName(location("tutorial_ore")),
			TutorialBlocks.tutorial_ore_nether = new Block(Block.Properties.create(Material.ROCK).hardnessAndResistance(2.0f, 3.0f).lightValue(5).sound(SoundType.METAL)).setRegistryName(location("tutorial_ore_nether")),
			TutorialBlocks.tutorial_ore_end = new Block(Block.Properties.create(Material.ROCK).hardnessAndResistance(2.0f, 3.0f).lightValue(5).sound(SoundType.METAL)).setRegistryName(location("tutorial_ore_end"))
		);
		
		LOGGER.info("Blocks registered.");
	}
	
	@SubscribeEvent
	public static void registerEntities(final RegistryEvent.Register<EntityType<?>> event)
	{
		event.getRegistry().registerAll
		(
			TutorialEntities.tutorial_entity
		);
		
		TutorialEntities.registerEntityWorldSpawns();
	}
	

	
	public static ResourceLocation location(String name)
	{
		return new ResourceLocation(MODID, name);
	}
}	

































