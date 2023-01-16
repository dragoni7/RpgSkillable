package com.github.dragoni7.rpgskillable.common.effects;

import java.util.UUID;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class EffectStrengthened extends MobEffect {
	
	private static final UUID ATTACK_DAMAGE_UUID = UUID.fromString("85bd9362-0076-41e2-87ca-f23e2a7cbbad");

	protected EffectStrengthened() {
		super(MobEffectCategory.BENEFICIAL, 0Xb3785f);
		this.addAttributeModifier(Attributes.ATTACK_DAMAGE, ATTACK_DAMAGE_UUID.toString(), 1.0f, AttributeModifier.Operation.ADDITION);
	}
}
