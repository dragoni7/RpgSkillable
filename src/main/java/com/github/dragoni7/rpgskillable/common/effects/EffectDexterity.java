package com.github.dragoni7.rpgskillable.common.effects;

import java.util.UUID;

import com.elenai.feathers.Feathers;
import com.elenai.feathers.attributes.FeathersAttributes;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraftforge.fml.ModList;

public class EffectDexterity extends MobEffect {
	
	private static final UUID ATTACK_SPEED_UUID = UUID.fromString("98148feb-b87b-41ef-8d42-0e08620b4adf");
	private static final UUID MOVE_SPEED_UUID = UUID.fromString("d25ae12d-1311-46ab-80c1-63ef8f8bcdd8");
	private static final UUID REGEN_FEATHERS_BONUS = UUID.fromString("4e8c93c1-163e-301b-bbce-aa4109fe0a5f");

	protected EffectDexterity() {
		super(MobEffectCategory.BENEFICIAL, 0Xeddf82);
		
		this.addAttributeModifier(Attributes.ATTACK_SPEED, ATTACK_SPEED_UUID.toString(), 0.15f, AttributeModifier.Operation.ADDITION);
		this.addAttributeModifier(Attributes.MOVEMENT_SPEED, MOVE_SPEED_UUID.toString(), 0.1f, AttributeModifier.Operation.MULTIPLY_BASE);
		
		if (ModList.get().isLoaded(Feathers.MODID)) {
			this.addAttributeModifier(FeathersAttributes.FEATHER_REGEN.get(), REGEN_FEATHERS_BONUS.toString(), 0.5f, AttributeModifier.Operation.ADDITION);
		}
	}
}
