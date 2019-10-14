package com.github.harrytechrevs.taanr;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.github.harrytechrevs.taanr.client.renders.TutorialRenderRegistry;
import com.github.harrytechrevs.taanr.config.Config;
import com.github.harrytechrevs.taanr.init.HammerNine;
import com.github.harrytechrevs.taanr.init.JumpUpAndDown;
import com.github.harrytechrevs.taanr.init.RunawayTeleport;
import com.github.harrytechrevs.taanr.world.gen.OreGeneration;

import net.minecraft.item.ItemGroup;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLPaths;

@Mod("taanr")
public class TutorialMod 
{
	public static TutorialMod instance;
	public static final String MODID = "taanr";
	public static final Logger LOGGER = LogManager.getLogger(MODID);
	public static final ItemGroup TUTORIAL = new TutorialItemGroup();
	
	public TutorialMod() 
	{
		instance = this;
		
		ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, Config.SERVER, "taanr-server.toml");
		ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, Config.CLIENT, "taanr-client.toml");
		
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::clientRegistries);
		
		Config.loadConfig(Config.CLIENT, FMLPaths.CONFIGDIR.get().resolve("taanr-client.toml").toString());
		Config.loadConfig(Config.SERVER, FMLPaths.CONFIGDIR.get().resolve("taanr-server.toml").toString());
		
		MinecraftForge.EVENT_BUS.register(this);
	}
	
	private void setup(final FMLCommonSetupEvent event)
	{
		OreGeneration.setupOreGeneration();
		
		MinecraftForge.EVENT_BUS.register(new RunawayTeleport()); 
		
		MinecraftForge.EVENT_BUS.register(new JumpUpAndDown());
	// 	FMLCommonLaunchHandler.instance().bus().register(new JumpUpAndDown());
		
		MinecraftForge.EVENT_BUS.register(new HammerNine());	
		
		LOGGER.info("Setup method registered.");
	}
	
	private void clientRegistries(final FMLClientSetupEvent event)
	{
		TutorialRenderRegistry.registryEntityRenders();
		LOGGER.info("clientRegistries method registered.");
	}
}
