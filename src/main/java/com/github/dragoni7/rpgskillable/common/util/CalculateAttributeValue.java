package com.github.dragoni7.rpgskillable.common.util;

import java.util.Collection;

import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;

public class CalculateAttributeValue {
	
	public static double get(Attribute a, Collection<AttributeModifier> modifiers) {
		
		double d0 = a.getDefaultValue();
		
		// determine item attribute value. Based off AttributeInstance.calculateValue()
		for (AttributeModifier modifier : modifiers.stream().filter(m -> m.getOperation() == AttributeModifier.Operation.ADDITION).toList()) {
			d0 += modifier.getAmount();
		}
		
		double d1 = d0;
		
		for (AttributeModifier modifier : modifiers.stream().filter(m -> m.getOperation() == AttributeModifier.Operation.MULTIPLY_BASE).toList()) {
			d1 += d0 * modifier.getAmount();
		}
		
		for (AttributeModifier modifier : modifiers.stream().filter(m -> m.getOperation() == AttributeModifier.Operation.MULTIPLY_TOTAL).toList()) {
			d1 *= 1.0D + modifier.getAmount();
		}
		
		return a.sanitizeValue(d1);
	}

}
