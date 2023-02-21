package com.github.dragoni7.rpgskillable.client;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.github.dragoni7.rpgskillable.Config;
import com.github.dragoni7.rpgskillable.common.capabilities.SkillModel;
import com.github.dragoni7.rpgskillable.common.skill.Requirement;
import com.github.dragoni7.rpgskillable.common.util.CalculateAttributeValue;
import com.google.common.collect.Multimap;

import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.ForgeRegistries;

public class Tooltip {

	@SubscribeEvent
	public void onTooltipDisplay(ItemTooltipEvent event) {
		
		if (Minecraft.getInstance().player != null) {
			
			ItemStack itemStack = event.getItemStack();
			List<Component> tooltips = event.getToolTip();
			SkillModel skillModel = SkillModel.get();
			
			// check if blacklisted
			if (skillModel.isBlacklisted(ForgeRegistries.ITEMS.getKey(itemStack.getItem()))) {
				return;
			}
			
			// add attribute lock tooltips.
			if (Config.getIfUseAttributeLocks()) {
				addAttributeRestrictionTooltips(tooltips, EquipmentSlot.MAINHAND, itemStack, skillModel);
				addAttributeRestrictionTooltips(tooltips, EquipmentSlot.OFFHAND, itemStack, skillModel);
				addAttributeRestrictionTooltips(tooltips, EquipmentSlot.CHEST, itemStack, skillModel);
				addAttributeRestrictionTooltips(tooltips, EquipmentSlot.FEET, itemStack, skillModel);
				addAttributeRestrictionTooltips(tooltips, EquipmentSlot.HEAD, itemStack, skillModel);
				addAttributeRestrictionTooltips(tooltips, EquipmentSlot.LEGS, itemStack, skillModel);
			}
			
			// add manually set skill lock tooltips.
			Requirement[] requirements = Config.getItemRequirements(ForgeRegistries.ITEMS.getKey(itemStack.getItem()));
			
			if (requirements != null) {
				for (Requirement requirement : requirements) {
					
					ChatFormatting color = skillModel.getSkillLevel(requirement.getSkill()) >= requirement.getLevel() ? ChatFormatting.GREEN : ChatFormatting.RED;
					tooltips.add(Component.translatable(requirement.getSkill().displayName).append(" " + requirement.getLevel()).withStyle(color));
				}
			}
			
			// add enchant skill lock tooltips.
			if (itemStack.isEnchanted()) {
				Map<Enchantment, Integer> enchants = EnchantmentHelper.getEnchantments(itemStack);
				Map<Requirement[], Integer> enchantRequirements = new HashMap<>();
				
				if (!enchants.isEmpty()) {
					
					// collect requirements for each enchantment on the item.
					for (Enchantment enchant : enchants.keySet()) {
						
						// only replace requirements if enchant level is higher
						int enchantLevel = enchants.get(enchant);
						Integer oldValue = enchantRequirements.put(Config.getEnchantmentRequirements(ForgeRegistries.ENCHANTMENTS.getKey(enchant)), enchantLevel);
						
						if (oldValue != null && oldValue.intValue() > enchantLevel) {
							enchantRequirements.put(Config.getEnchantmentRequirements(ForgeRegistries.ENCHANTMENTS.getKey(enchant)), oldValue);
						}
					}
					
					// if the item contains enchants with requirements
					if (!enchantRequirements.isEmpty() && enchantRequirements != null) {
						for (Requirement[] requirementsPerEnchant : enchantRequirements.keySet()) {
							if (requirementsPerEnchant != null) {
								for (Requirement enchantRequirement : requirementsPerEnchant) {
									
									// add enchantment requirement tooltip
									double levelRequirement = enchantRequirement.getLevel();
									int enchantLevel = enchantRequirements.get(requirementsPerEnchant);
									
									int finalValue = (int) (enchantLevel == 1 ? Math.round(levelRequirement) : Math.round(levelRequirement + (enchantLevel * Config.getEnchantmentRequirementIncrease())));
									
									ChatFormatting color = skillModel.getSkillLevel(enchantRequirement.getSkill()) >= finalValue ? ChatFormatting.GREEN : ChatFormatting.RED;
									tooltips.add(Component.translatable(enchantRequirement.getSkill().displayName).append(" " + finalValue).withStyle(color));
								}
							}
						}
					}
				}
			}
		}
	}
	
	private void addAttributeRestrictionTooltips(List<Component> tooltips, EquipmentSlot slot, ItemStack stack, SkillModel skillModel) {
		
		Multimap<Attribute, AttributeModifier> attributeModifiers = stack.getAttributeModifiers(slot);
		
		for (Attribute a : attributeModifiers.keys()) {
			
			// for vanilla attributes
			String attributeID = a.getDescriptionId().replaceAll("attribute.name.", "").trim();
			
			Requirement[] attributeRequirements = Config.getAttributeRequirements(attributeID);
			
			if (attributeRequirements != null) {
				
				double attributeValue = CalculateAttributeValue.get(a, attributeModifiers.get(a));
				
				for (Requirement requirement : attributeRequirements) {
					int finalAmount = (int) Math.round(requirement.getLevel() * attributeValue);
					// if item is omitted
					if (attributeValue <= Config.getSkillOmitLevel(requirement.getSkill())) {
						continue;
					}
					
					ChatFormatting color = skillModel.getSkillLevel(requirement.getSkill()) >= finalAmount ? ChatFormatting.GREEN : ChatFormatting.RED;
					tooltips.add(Component.translatable(requirement.getSkill().displayName).append(" " + (finalAmount)).withStyle(color));
				}
			}
		}
	}
}
