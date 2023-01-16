package com.github.dragoni7.rpgskillable;

import com.github.dragoni7.rpgskillable.client.KeyHandler;
import com.github.dragoni7.rpgskillable.client.Tooltip;
import com.github.dragoni7.rpgskillable.client.screen.InventoryTabs;
import com.github.dragoni7.rpgskillable.common.CuriosCompat;
import com.github.dragoni7.rpgskillable.common.EventHandler;
import com.github.dragoni7.rpgskillable.common.commands.Commands;
import com.github.dragoni7.rpgskillable.common.effects.RpgSkillableEffects;
import com.github.dragoni7.rpgskillable.common.network.RequestLevelUp;
import com.github.dragoni7.rpgskillable.common.network.SyncToClient;
import com.mojang.logging.LogUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;
import top.theillusivec4.curios.Curios;

import java.util.Optional;

import org.slf4j.Logger;

@Mod(RpgSkillable.MODID)
public class RpgSkillable
{
    public static final String MODID = "rpgskillable";
    public static final Logger LOGGER = LogUtils.getLogger();
    public static SimpleChannel NETWORK;
    
    public RpgSkillable()
    {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        RpgSkillableEffects.MOB_EFFECTS.register(modEventBus);
        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(this::clientSetup);
        
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.getConfig());
    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {	
    	Config.load();
    	int ID = 0;
    	NETWORK = NetworkRegistry.newSimpleChannel(new ResourceLocation("rpgskillable", "main_channel"), () -> "1.0", s -> true, s -> true);
    	NETWORK.registerMessage(ID++, SyncToClient.class, SyncToClient::encode, SyncToClient::new, SyncToClient::handle, Optional.of(NetworkDirection.PLAY_TO_CLIENT));
    	NETWORK.registerMessage(ID++, RequestLevelUp.class, RequestLevelUp::encode, RequestLevelUp::new, RequestLevelUp::handle, Optional.of(NetworkDirection.PLAY_TO_SERVER));
    	
    	MinecraftForge.EVENT_BUS.register(new EventHandler());
    	MinecraftForge.EVENT_BUS.register(new Commands());
    	
    	// Curios Compat
    	
    	if (ModList.get().isLoaded(Curios.MODID)) {
    		MinecraftForge.EVENT_BUS.register(new CuriosCompat());
    	}
    }
    
    private void clientSetup(final FMLClientSetupEvent event) {
    	MinecraftForge.EVENT_BUS.register(new InventoryTabs());
    	MinecraftForge.EVENT_BUS.register(new KeyHandler());
    	MinecraftForge.EVENT_BUS.register(new Tooltip());
    }
}

