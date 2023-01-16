package com.github.dragoni7.rpgskillable.client;

import org.lwjgl.glfw.GLFW;

import com.github.dragoni7.rpgskillable.RpgSkillable;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;


@Mod.EventBusSubscriber(modid = RpgSkillable.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class KeyBind {

	public final static KeyMapping OPEN_SKILL_SCREEN = new KeyMapping("key.rpgskillable.open_skill_screen", GLFW.GLFW_KEY_EQUAL, "key.category.rpgskillable.general");
	
	@SubscribeEvent
	public static void onRegisterKeyMapping(RegisterKeyMappingsEvent event) {
		event.register(OPEN_SKILL_SCREEN);
	}
}
