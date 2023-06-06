package com.github.dragoni7.rpgskillable.common.effects;

import java.util.UUID;

import com.elenai.feathers.Feathers;
import com.elenai.feathers.attributes.FeathersAttributes;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraftforge.fml.ModList;

public class EffectEndurance extends MobEffect {
	
	private static final UUID MAX_FEATHERS_BONUS = UUID.fromString("4e8c93c1-163e-401b-aace-aa4109fe0a5f");

	protected EffectEndurance() {
		super(MobEffectCategory.BENEFICIAL, 0X717a96);
		if (ModList.get().isLoaded(Feathers.MODID)) {
			this.addAttributeModifier(FeathersAttributes.MAX_FEATHERS.get(), MAX_FEATHERS_BONUS.toString(), 2.0f, AttributeModifier.Operation.ADDITION);
		}
	}
}
