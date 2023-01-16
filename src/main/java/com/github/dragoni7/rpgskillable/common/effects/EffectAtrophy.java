package com.github.dragoni7.rpgskillable.common.effects;

import java.util.UUID;

import com.hollingsworth.arsnouveau.ArsNouveau;
import com.hollingsworth.arsnouveau.api.perk.PerkAttributes;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraftforge.fml.ModList;

public class EffectAtrophy extends MobEffect {
	
	private static final UUID ATTACK_DAMAGE_UUID = UUID.fromString("64ba85a9-01dc-4231-b541-1b284d50d3ac");
	private static final UUID ATTACK_SPEED_UUID = UUID.fromString("70a8d7ff-3854-45f8-82dc-b4ecec83e3b7");
	private static final UUID SPELL_DAMAGE = UUID.fromString("d8ad0968-3fff-4557-a6f1-f4c487564407");
	
	protected EffectAtrophy() {
		super(MobEffectCategory.NEUTRAL, 0Xf0f0b1);
		this.addAttributeModifier(Attributes.ATTACK_DAMAGE, ATTACK_DAMAGE_UUID.toString(), -0.75f, AttributeModifier.Operation.MULTIPLY_TOTAL);
		this.addAttributeModifier(Attributes.ATTACK_SPEED, ATTACK_SPEED_UUID.toString(), -0.95f, AttributeModifier.Operation.MULTIPLY_TOTAL);
		
		if (ModList.get().isLoaded(ArsNouveau.MODID)) {
			this.addAttributeModifier(PerkAttributes.SPELL_DAMAGE_BONUS.get(), SPELL_DAMAGE.toString(), -0.75f, AttributeModifier.Operation.MULTIPLY_TOTAL);
		}
	}
	
	public boolean isDurationEffectTick(int duration, int amplifier) {
        return duration > 0;
    }

    public String getDescriptionId() {
        return "rpgskillable.effect.atrophy";
    }
}
