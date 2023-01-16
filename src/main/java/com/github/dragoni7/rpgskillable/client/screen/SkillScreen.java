package com.github.dragoni7.rpgskillable.client.screen;

import com.github.dragoni7.rpgskillable.Config;
import com.github.dragoni7.rpgskillable.RpgSkillable;
import com.github.dragoni7.rpgskillable.client.screen.button.SkillButton;
import com.github.dragoni7.rpgskillable.common.capabilities.SkillModel;
import com.github.dragoni7.rpgskillable.common.skill.Skill;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;

public class SkillScreen extends Screen {

	public static final ResourceLocation RESOURCES = new ResourceLocation(RpgSkillable.MODID, "textures/gui/skill_screen.png");
	private static final MutableComponent MENU_NAME = Component.translatable("container.skills");
	
	public SkillScreen() {
		super(MENU_NAME);
	}

	@Override
	protected void init() {
		int left = (width - 162) / 2;
		int top = (height - 128) / 2;

		for (int i = 0; i < 6; i++) {
			int x = left + i % 2 * 83;
			int y = top + i / 2 * 36;
			
			this.addRenderableWidget(new SkillButton(x, y, Skill.values()[i]));
		}
	}
	
    @Override
    public void render(PoseStack stack, int mouseX, int mouseY, float partialTicks)
    {
    	SkillModel model = SkillModel.get();
    	MutableComponent skillsTitle = Component.literal("Skills: " + model.getTotalLevel() + "/" + Config.getMaxLevelTotal());
    	RenderSystem.setShaderTexture(0, RESOURCES);
    	RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
        int left = (width - 176) / 2;
        int top = (height - 166) / 2;
        
        renderBackground(stack);
        
        blit(stack, left, top, 0, 0, 176, 166);
        font.draw(stack, skillsTitle, width / 2 - font.width(skillsTitle) / 2, top + 6, 0x3F3F3F);
        
        super.render(stack, mouseX, mouseY, partialTicks);
    }
	
	
    @Override
    public boolean isPauseScreen()
    {
        return false;
    }

}
