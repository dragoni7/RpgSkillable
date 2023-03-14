package com.github.dragoni7.rpgskillable.client.screen;

import com.github.dragoni7.rpgskillable.Config;
import com.github.dragoni7.rpgskillable.client.screen.button.TabButton;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class InventoryTabs {
    
    @SubscribeEvent
    public void onScreenInit(ScreenEvent.Init event)
    {
        Screen screen = event.getScreen();
        if (screen instanceof InventoryScreen || screen instanceof CreativeModeInventoryScreen || screen instanceof SkillScreen)
        {
            boolean creativeOpen = screen instanceof CreativeModeInventoryScreen;
            
            if (Config.getIfCreativeHidden() && creativeOpen) {
            	return;
            }
            
            boolean skillsOpen = screen instanceof SkillScreen;
            int x = (screen.width - (creativeOpen ? 195 : 176)) / 2 - 28;
            int y = (screen.height - (creativeOpen ? 136 : 166)) / 2;
            
            event.addListener(new TabButton(x + Config.getInvXOffset(), y + Config.getInvYOffset(), TabButton.TabType.INVENTORY, !skillsOpen));
            event.addListener(new TabButton(x + Config.getSkillXOffset(), y + Config.getSkillYOffset(), TabButton.TabType.SKILLS, skillsOpen));
        }
    }
}
