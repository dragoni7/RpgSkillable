package com.github.dragoni7.rpgskillable.common.effects;

import java.util.UUID;

import com.hollingsworth.arsnouveau.ArsNouveau;
import com.hollingsworth.arsnouveau.api.perk.PerkAttributes;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraftforge.fml.ModList;

public class EffectMind extends MobEffect {
	
	private static final UUID MAX_MANA_BONUS = UUID.fromString("4e9c93c1-162e-401b-aace-aa4109fd0a5f");

	protected EffectMind() {
		super(MobEffectCategory.BENEFICIAL, 0X373f8c);
		if (ModList.get().isLoaded(ArsNouveau.MODID)) {
			this.addAttributeModifier(PerkAttributes.MAX_MANA_BONUS.get(), MAX_MANA_BONUS.toString(), 0.1f, AttributeModifier.Operation.MULTIPLY_TOTAL);
		}
	}

}
