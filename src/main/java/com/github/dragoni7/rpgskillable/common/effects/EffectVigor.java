package com.github.dragoni7.rpgskillable.common.effects;

import java.util.UUID;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class EffectVigor extends MobEffect {
	
	private static final UUID MAX_HEALTH_UUID = UUID.fromString("ae00882e-3a08-438d-ba57-25a85729dcee");

	protected EffectVigor() {
		super(MobEffectCategory.BENEFICIAL, 0Xe86dcf);
		this.addAttributeModifier(Attributes.MAX_HEALTH, MAX_HEALTH_UUID.toString(), 4.0f, AttributeModifier.Operation.ADDITION);
	}

}
