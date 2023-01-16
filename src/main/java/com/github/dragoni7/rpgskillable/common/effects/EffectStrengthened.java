package com.github.dragoni7.rpgskillable.common.effects;

import java.util.UUID;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class EffectStrengthened extends MobEffect {
	
	private static final UUID ATTACK_SPEED_UUID = UUID.fromString("98148feb-b87b-41ef-8d42-0e08620b4adf");
	private static final UUID ATTACK_DAMAGE_UUID = UUID.fromString("85bd9362-0076-41e2-87ca-f23e2a7cbbad");

	protected EffectStrengthened() {
		super(MobEffectCategory.BENEFICIAL, 0Xb3785f);
		this.addAttributeModifier(Attributes.ATTACK_SPEED, ATTACK_SPEED_UUID.toString(), -0.1f, AttributeModifier.Operation.ADDITION);
		this.addAttributeModifier(Attributes.ATTACK_DAMAGE, ATTACK_DAMAGE_UUID.toString(), 1.0f, AttributeModifier.Operation.ADDITION);
	}
}
