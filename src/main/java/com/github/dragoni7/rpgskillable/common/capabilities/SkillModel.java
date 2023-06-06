package com.github.dragoni7.rpgskillable.common.capabilities;

import java.util.HashMap;
import java.util.Map;

import com.github.dragoni7.rpgskillable.Config;
import com.github.dragoni7.rpgskillable.common.effects.RpgSkillableEffects;
import com.github.dragoni7.rpgskillable.common.skill.Requirement;
import com.github.dragoni7.rpgskillable.common.skill.Skill;
import com.github.dragoni7.rpgskillable.common.util.CalculateAttributeValue;
import com.google.common.collect.Multimap;

import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.capabilities.AutoRegisterCapability;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.registries.ForgeRegistries;

@AutoRegisterCapability
public class SkillModel implements INBTSerializable<CompoundTag> {
	
	public static final MutableComponent MAIN_HAND_WARNING = Component.translatable("overlay.main_hand_message");
	public static final MutableComponent OFF_HAND_WARNING = Component.translatable("overlay.off_hand_message");
	public static final MutableComponent GENERIC_WARNING = Component.translatable("overlay.default_message");

	public static final Map<Skill, MobEffect> EFFECT_BY_SKILL = Map.of(
			Skill.DEXTERITY, RpgSkillableEffects.DEXTERITY.get(), Skill.ENDURANCE, RpgSkillableEffects.ENDURANCE.get(),
			Skill.INTELLIGENCE, RpgSkillableEffects.INTELLIGENCE.get(), Skill.MIND, RpgSkillableEffects.MIND.get(),
			Skill.STRENGTH, RpgSkillableEffects.STRENGTHENED.get(), Skill.VIGOR, RpgSkillableEffects.VIGOR.get());

	public static final int EFFECT_DURATION = Integer.MAX_VALUE;
	
	private static final int UPDATE_INTERVAL = 1200; // 1 min

	private int[] skillLevels = new int[] { 1, 1, 1, 1, 1, 1 };
	
	private int totalLevel = 6;
	
	private int updateTick = 0;

	public int getSkillLevel(Skill skill) {
		return skillLevels[skill.index];
	}
	
	public int getTotalLevel() {
		return totalLevel;
	}
	
	public boolean underMaxTotal() {
		return totalLevel < Config.getMaxLevelTotal();
	}

	public void setSkillLevel(Skill skill, int level, ServerPlayer player) {
		skillLevels[skill.index] = level;
		totalLevel += level - skillLevels[skill.index];

		addEffects(player, skill);
	}

	public void increaseSkillLevel(Skill skill, ServerPlayer player) {
		skillLevels[skill.index]++;
		totalLevel++;
		
		addEffects(player, skill);
	}

	public void resetSkills(ServerPlayer player) {
		this.skillLevels = new int[] { 1, 1, 1, 1, 1, 1, 1, 1 };
		totalLevel = 6;
		// clear positive skill effects
		for (MobEffect effect : EFFECT_BY_SKILL.values()) {
			if (player.hasEffect(effect)) {
				player.removeEffect(effect);
			}
		}
	}
	
	private void addEffects(ServerPlayer player, Skill skill) {
		
		int amp = determineEffectAmpAmount(skill);
		
		if (amp != -1) {
			MobEffect effect = EFFECT_BY_SKILL.get(skill);

			if (player.hasEffect(effect)) {
				player.removeEffect(effect);
			}

			player.addEffect(new MobEffectInstance(effect, EFFECT_DURATION, amp, false, false, true));
		}
	}

	public int determineEffectAmpAmount(Skill skill) {
		int skillLevel = getSkillLevel(skill);
		int levelPerEffect = Config.getLevelPerEffect();
		int amp = (int) Math.floor(skillLevel / levelPerEffect);
		
		return amp == 0 ? -1 : amp - 1;
	}
	
	public void updateEffectsOnTick(ServerPlayer player) {
		
		updateTick++;
		
		if (updateTick == UPDATE_INTERVAL) {
			updateEffects(player);
			updateTick = 0;
		}
	}

	public void updateEffects(ServerPlayer player) {
		
		for (Skill skill : EFFECT_BY_SKILL.keySet()) {

			int amp = determineEffectAmpAmount(skill);
			MobEffect effect = EFFECT_BY_SKILL.get(skill);
			MobEffectInstance instance = new MobEffectInstance(EFFECT_BY_SKILL.get(skill), EFFECT_DURATION, amp,
					false, false, true);

			if (amp != -1) {
				if (player.hasEffect(effect)) {
					if (player.getEffect(effect).getAmplifier() >= amp) {
						// refresh or downgrade effect
						player.removeEffect(effect);
						player.addEffect(instance);
					} else {
						// update effect
						player.removeEffect(effect);
						player.addEffect(instance);
					}
				} else {
					// add the effect back
					player.addEffect(instance);
				}
			} else {
				player.removeEffect(effect);
			}
		}
	}

	public boolean canUseItem(Player player, ItemStack item) {
		return canUse(player, ForgeRegistries.ITEMS.getKey(item.getItem()));
	}

	public boolean canUseBlock(Player player, Block block) {
		return canUse(player, ForgeRegistries.BLOCKS.getKey(block));
	}

	public boolean canUseEntity(Player player, Entity entity) {
		return canUse(player, ForgeRegistries.ENTITY_TYPES.getKey(entity.getType()));
	}

	public boolean canUseItemInSlot(Player player, ItemStack itemStack, EquipmentSlot slot) {
		
		ResourceLocation itemLoc = ForgeRegistries.ITEMS.getKey(itemStack.getItem());
		
		// check if blacklisted
		if (isBlacklisted(itemLoc)) {
			return true;
		}
		
		// check override skill locks
		Requirement[] requirements = Config.getItemRequirements(itemLoc);

		if (requirements != null) {
			for (Requirement requirement : requirements) {

				if (getSkillLevel(requirement.getSkill()) < requirement.getLevel()) {

					if (player instanceof ServerPlayer) {
						displayUnmetRequirementMessage((ServerPlayer) player);
					}

					return false;
				}
			}
		}
		// check attribute skill locks if not overriden
		else if (Config.getIfUseAttributeLocks()) {

			Multimap<Attribute, AttributeModifier> attributeModifiers = itemStack.getAttributeModifiers(slot);

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

						if (!(SkillModel.get(player).getSkillLevel(requirement.getSkill()) >= finalAmount)) {
							
							if (player instanceof ServerPlayer) {
								if (player instanceof ServerPlayer) {
									displayUnmetRequirementMessage(slot, (ServerPlayer) player);
								}
							}
							
							return false;
						}
					}
				}
			}
		}
		
		// check for enchantment attribute locks
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
								
								// check if player can use enchanted item
								double levelRequirement = enchantRequirement.getLevel();
								int enchantLevel = enchantRequirements.get(requirementsPerEnchant);
								
								double finalValue = enchantLevel == 1 ? levelRequirement : levelRequirement + (enchantLevel * Config.getEnchantmentRequirementIncrease());
								
								if (!(SkillModel.get(player).getSkillLevel(enchantRequirement.getSkill()) >= finalValue)) {
									
									if (player instanceof ServerPlayer) {
										displayUnmetRequirementMessage(slot, (ServerPlayer) player);
									}
									
									return false;
								}
							}
						}
					}
				}
			}
		}
		
		return true;
	}

	private boolean canUse(Player player, ResourceLocation resource) {
		
		// check if blacklisted
		if (isBlacklisted(resource))  {
			return true;
		}

		Requirement[] requirements = Config.getItemRequirements(resource);

		if (requirements != null) {
			for (Requirement requirement : requirements) {

				if (getSkillLevel(requirement.getSkill()) < requirement.getLevel()) {

					if (player instanceof ServerPlayer) {
						displayUnmetRequirementMessage((ServerPlayer) player);
					}

					return false;
				}
			}
		}

		return true;
	}
	
	private void displayUnmetRequirementMessage(EquipmentSlot slot, ServerPlayer player) {
		switch (slot) {
		case MAINHAND: {
			player.displayClientMessage(
					MAIN_HAND_WARNING.withStyle(ChatFormatting.ITALIC, ChatFormatting.DARK_RED), true);
			break;
		}
		case OFFHAND: {
			player.displayClientMessage(
					OFF_HAND_WARNING.withStyle(ChatFormatting.ITALIC, ChatFormatting.DARK_RED), true);
			break;
		}
		default: {
			player.displayClientMessage(
					GENERIC_WARNING.withStyle(ChatFormatting.ITALIC, ChatFormatting.DARK_RED), true);
		}
		}
	}
	
	private void displayUnmetRequirementMessage(ServerPlayer player) {
		player.displayClientMessage(GENERIC_WARNING.withStyle(ChatFormatting.ITALIC, ChatFormatting.DARK_RED), true);
	}

	public static SkillModel get(Player player) {
		return player.getCapability(SkillCapability.SKILL_MODEL).orElseThrow(() -> new IllegalArgumentException(
				"Player " + player.getName().getString() + " does not have a Skill Model"));		
	}
	
	@OnlyIn(Dist.CLIENT)
	public static SkillModel get() {
		return Minecraft.getInstance().player.getCapability(SkillCapability.SKILL_MODEL)
				.orElseThrow(() -> new IllegalArgumentException("Player does not have a Skill Model"));
	}

	public void copyForRespawn(SkillModel oldStore) {
		this.deserializeNBT(oldStore.serializeNBT());
	}
	
	public static boolean isBlacklisted(ResourceLocation loc) {
		return Config.getBlacklist().contains(loc.toString());
	}

	@Override
	public CompoundTag serializeNBT() {
		CompoundTag tag = new CompoundTag();
		tag.putInt("vigor", skillLevels[0]);
		tag.putInt("strength", skillLevels[1]);
		tag.putInt("mind", skillLevels[2]);
		tag.putInt("dexterity", skillLevels[3]);
		tag.putInt("endurance", skillLevels[4]);
		tag.putInt("intelligence", skillLevels[5]);
		tag.putInt("totalLevel", totalLevel);
		return tag;
	}

	@Override
	public void deserializeNBT(CompoundTag nbt) {
		skillLevels[0] = nbt.getInt("vigor");
		skillLevels[1] = nbt.getInt("strength");
		skillLevels[2] = nbt.getInt("mind");
		skillLevels[3] = nbt.getInt("dexterity");
		skillLevels[4] = nbt.getInt("endurance");
		skillLevels[5] = nbt.getInt("intelligence");
		totalLevel = nbt.getInt("totalLevel");
	}

}
