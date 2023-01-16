package com.github.dragoni7.rpgskillable.common.effects;

import java.util.UUID;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class EffectDexterity extends MobEffect {
	
	private static final UUID ATTACK_SPEED_UUID = UUID.fromString("98148feb-b87b-41ef-8d42-0e08620b4adf");
	private static final UUID MOVE_SPEED_UUID = UUID.fromString("d25ae12d-1311-46ab-80c1-63ef8f8bcdd8");

	protected EffectDexterity() {
		super(MobEffectCategory.BENEFICIAL, 0Xeddf82);
		
		this.addAttributeModifier(Attributes.ATTACK_SPEED, ATTACK_SPEED_UUID.toString(), 0.15f, AttributeModifier.Operation.ADDITION);
		this.addAttributeModifier(Attributes.MOVEMENT_SPEED, MOVE_SPEED_UUID.toString(), 0.1f, AttributeModifier.Operation.MULTIPLY_BASE);
	}
}
