package com.github.dragoni7.rpgskillable;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.github.dragoni7.rpgskillable.common.skill.Requirement;
import com.github.dragoni7.rpgskillable.common.skill.Skill;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.ForgeConfigSpec;

public class Config {

	private static final ForgeConfigSpec CONFIG_SPEC;
	private static final ForgeConfigSpec.BooleanValue DEATH_RESET;
	private static final ForgeConfigSpec.BooleanValue EFFECT_DETRIMENT;
	private static final ForgeConfigSpec.BooleanValue USE_ATTRIBUTE_LOCKS;
	private static final ForgeConfigSpec.IntValue STARTING_COST;
	private static final ForgeConfigSpec.IntValue COST_INCREASE;
	private static final ForgeConfigSpec.IntValue MAXIMUM_LEVEL;
	private static final ForgeConfigSpec.IntValue MAXIMUM_LEVEL_TOTAL;
	private static final ForgeConfigSpec.IntValue ENCHANT_LEVEL_INCREASE;
	private static final ForgeConfigSpec.DoubleValue VIGOR_OMIT;
	private static final ForgeConfigSpec.DoubleValue STRENGTH_OMIT;
	private static final ForgeConfigSpec.DoubleValue ENDURANCE_OMIT;
	private static final ForgeConfigSpec.DoubleValue INT_OMIT;
	private static final ForgeConfigSpec.DoubleValue DEX_OMIT;
	private static final ForgeConfigSpec.DoubleValue MIND_OMIT;
	private static final ForgeConfigSpec.IntValue EFFECT_PER_LEVEL;
	private static final ForgeConfigSpec.ConfigValue<List<? extends String>> SKILL_LOCKS;
	private static final ForgeConfigSpec.ConfigValue<List<? extends String>> ENCHANT_SKILL_LOCKS;
	private static final ForgeConfigSpec.ConfigValue<List<? extends String>> ATTRIBUTE_SKILL_LOCKS;
	
	private static boolean deathReset;
	private static boolean effectDetriment;
	private static boolean useAttributeLocks;
	private static int startingCost;
	private static int costIncrease;
	private static int maximumLevel;
	private static int maximumLevelTotal;
	private static int enchantLevelIncrease;
	private static double vigorOmit;
	private static double strengthOmit;
	private static double enduranceOmit;
	private static double intOmit;
	private static double dexOmit;
	private static double mindOmit;
	private static int effectPerLevel;
	private static final Map<String, Requirement[]> skillLocks = new HashMap<>();
	private static final Map<String, Requirement[]> enchantSkillLocks = new HashMap<>();
	private static final Map<String, Requirement[]> attributeSkillLocks = new HashMap<>();
	
	static {
		
        ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();
    
        builder.comment("Reset all skills to 1 when a player dies.");
        DEATH_RESET = builder.define("deathReset", false);
        
        builder.comment("Whether the player should be inflicted with negative effects while wielding items with unmet requirements, or to cancel the attack and change equipment events with unmet requirements. Curios are always dropped.");
        EFFECT_DETRIMENT = builder.define("effectDetriment", true);
        
        builder.comment("Starting cost of upgrading to level 2, in levels.");
        STARTING_COST = builder.defineInRange("startingCost", 5, 0, 10);
        
        builder.comment("Amount of levels added to the cost with each upgrade (use 0 for constant cost).");
        COST_INCREASE = builder.defineInRange("costIncrease", 1, 0, 10);
        
        builder.comment("Maximum level each skill can be upgraded to.");
        MAXIMUM_LEVEL = builder.defineInRange("maximumLevel", 31, 2, 100);
        
        builder.comment("Maximum levels the player can have. This is the sum of all skill levels.");
        MAXIMUM_LEVEL_TOTAL = builder.defineInRange("maximumLevelTotal", 66, 6, 100);
        
        builder.comment("How many levels are required per level of positive skill effect. Setting this to 6 will grant a level of positive effect per 6 skill levels.");
        EFFECT_PER_LEVEL = builder.defineInRange("effectPerLevel", 6, 1, 100);
        
        builder.comment("Manually set skill requirements for blocks, items, or riding entities. Can be used alongside attribute skill locks, though overlap should be avoided.", "Format: mod:id skill:level", "Valid skills: vigor, endurance, strength, dexterity, mind, intelligence");
        SKILL_LOCKS = builder.defineList("skillLocks", Arrays.asList(
        		"ars_nouveau:scribes_table intelligence:10",
        		"ars_nouveau:alteration_table intelligence:10",
        		"ars_nouveau:imbuement_chamber intelligence:10",
        		// Ars Glyphs:
        		"ars_nouveau:glyph_invisibility mind:20",
        		"ars_nouveau:glyph_phantom_block mind:10",
        		"ars_nouveau:glyph_fortune mind:20",
        		"ars_nouveau:glyph_wither intelligence:30",
        		"ars_nouveau:glyph_toss intelligence:20",
        		"ars_nouveau:glyph_cut intelligence:20",
        		"ars_nouveau:glyph_grow mind:10",
        		"ars_nouveau:glyph_summon_wolves intelligence:10",
        		"ars_nouveau:glyph_name mind:10",
        		"ars_nouveau:glyph_dampen intelligence:20",
        		"ars_nouveau:glyph_gravity intelligence:20",
        		"ars_nouveau:glyph_firework intelligence:10",
        		"ars_nouveau:glyph_split intelligence:30",
        		"ars_nouveau:glyph_snare intelligence:10",
        		"ars_nouveau:glyph_toss intelligence:5",
        		"ars_nouveau:glyph_crush intelligence:20",
        		"ars_nouveau:glyph_evaporate intelligence:10",
        		"ars_nouveau:glyph_conjure_water mind:10",
        		"ars_nouveau:glyph_lightning intelligence:30",
        		"ars_nouveau:glyph_pickup mind:10",
        		"ars_nouveau:glyph_smelt mind:10",
        		"ars_nouveau:glyph_launch mind:20",
        		"ars_nouveau:glyph_rotate mind:10",
        		"ars_nouveau:glyph_blink mind:30",
        		"ars_nouveau:glyph_ignite intelligence:10",
        		"ars_nouveau:glyph_amplify intelligence:10",
        		"ars_nouveau:glyph_bounce mind:10",
        		"ars_nouveau:glyph_heal mind:20",
        		"ars_nouveau:glyph_glide mind:30",
        		"ars_nouveau:glyph_leap mind:20",
        		"ars_nouveau:glyph_fangs intelligence:30",
        		"ars_nouveau:glyph_exchange mind:20",
        		"ars_nouveau:glyph_fell mind:20",
        		"ars_nouveau:glyph_sensitive mind:10",
        		"ars_nouveau:glyph_redstone_signal mind:10",
        		"ars_nouveau:glyph_duration_down mind:20",
        		"ars_nouveau:glyph_intangible mind:30",
        		"ars_nouveau:glyph_ender_inventory mind:20",
        		"ars_nouveau:glyph_interact mind:10",
        		"ars_nouveau:glyph_place_block mind:10",
        		"ars_nouveau:glyph_harvest mind:10",
        		"ars_nouveau:glyph_knockback intelligence:10",
        		"ars_nouveau:glyph_freeze intelligence:10",
        		"ars_nouveau:glyph_wall mind:30",
        		"ars_nouveau:glyph_summon_steed intelligence:10",
        		"ars_nouveau:glyph_summon_vex intelligence:30",
        		"ars_nouveau:glyph_aoe intelligence:20",
        		"ars_nouveau:glyph_decelerate mind:20",
        		"ars_nouveau:glyph_extend_time mind:20",
        		"ars_nouveau:glyph_orbit mind:30",
        		"ars_nouveau:glyph_accelerate mind:20",
        		"ars_nouveau:glyph_summon_undead intelligence:30",
        		"ars_nouveau:glyph_summon_rune mind:10",
        		"ars_nouveau:glyph_flare intelligence:20",
        		"ars_nouveau:glyph_explosion intelligence:20",
        		"ars_nouveau:glyph_craft mind:10",
        		"ars_nouveau:glyph_wind_shear intelligence:20",
        		"ars_nouveau:glyph_sense_magic mind:20",
        		"ars_nouveau:glyph_pull intelligence:20",
        		"ars_nouveau:glyph_pierce intelligence:20",
        		"ars_nouveau:glyph_slowfall mind:20",
        		"ars_nouveau:glyph_extract mind:20",
        		"ars_nouveau:glyph_delay mind:10",
        		"ars_nouveau:glyph_cold_snap intelligence:20",
        		"ars_nouveau:glyph_summon_decoy intelligence:30",
        		"ars_nouveau:glyph_infuse mind:20",
        		"ars_nouveau:glyph_hex intelligence:30",
        		"ars_nouveau:glyph_underfoot mind:10",
        		"ars_nouveau:glyph_linger mind:30",
        		"ars_nouveau:glyph_light mind:10",
        		"ars_nouveau:glyph_dispel mind:20",
        		"ars_nouveau:apprentice_spell_book intelligence:10",
        		"ars_nouveau:archmage_spell_book intelligence:20",
        		"ars_nouveau:enchanters_sword intelligence:10",
        		"ars_nouveau:enchanters_shield intelligence:10",
        		"ars_nouveau:enchanters_bow intelligence:10",
        		"ars_nouveau:spell_crossbow intelligence:10",
        		"ars_nouveau:caster_tome intelligence:10",
        		"ars_nouveau:wand intelligence:10",
        		"ars_nouveau:enchanters_mirror intelligence:10",
        		"ars_nouveau:belt_of_levitation mind:30",
        		"ars_nouveau:belt_of_unstable_gifts mind:30",
        		"minecraft:enchanted_golden_apple mind:20",
        		"minecraft:enchanting_table intelligence:10",
        		"minecraft:brewing_stand intelligence:15",
        		"minecraft:potion mind:5",
        		"minecraft:lingering_potion mind:5",
        		"minecraft:splash_potion intelligence:5",
        		"minecraft:shield vigor:5",
        		"minecraft:crossbow dexterity:10",
        		"minecraft:trident dexterity:10",
        		"minecraft:bow dexterity:10",
        		"minecraft:elytra dexterity:20",
        		"minecraft:horse dexterity:10",
        		"minecraft:donkey dexterity:10",
        		"minecraft:mule dexterity:10",
        		"minecraft:strider dexterity:15"),
        		obj -> true);
        
        builder.comment("Skill requirements for enchantments. Ideally this should be different than the base item's skill locks to prevent overlap.", "Format: mod:id skill:level", "Valid skills: vigor, endurance, strength, dexterity, mind, intelligence");
        ENCHANT_SKILL_LOCKS = builder.defineList("enchantSkillLocks", Arrays.asList(
        		"minecraft:protection mind:8",
        		"minecraft:efficiency mind:8",
        		"minecraft:silk_touch mind:10",
        		"minecraft:unbreaking mind:8",
        		"minecraft:fortune mind:8",
        		"minecraft:luck_of_the_sea mind:15",
        		"minecraft:lure mind:8",
        		"minecraft:mending mind:15",
        		"minecraft:fire_protection mind:8",
        		"minecraft:feather_falling mind:8",
        		"minecraft:blast_protection mind:8",
        		"minecraft:projectile_protection mind:8",
        		"minecraft:respiration mind:10",
        		"minecraft:aqua_affinity mind:15",
        		"minecraft:thorns mind:8",
        		"minecraft:depth_strider mind:8",
        		"minecraft:frost_walker mind:8",
        		"minecraft:soul_speed mind:8",
        		"minecraft:swift_sneak mind:8",
        		"minecraft:sharpness mind:8",
        		"minecraft:smite intelligence:8",
        		"minecraft:bane_of_arthropods intelligence:8",
        		"minecraft:knockback intelligence:8",
        		"minecraft:fire_aspect intelligence:8",
        		"minecraft:looting mind:8",
        		"minecraft:sweeping_edge intelligence:8",
        		"minecraft:unbreaking mind:8",
        		"minecraft:power intelligence:8",
        		"minecraft:punch intelligence:8",
        		"minecraft:flame intelligence:8",
        		"minecraft:infinity mind:15",
        		"minecraft:loyalty mind:8",
        		"minecraft:impaling intelligence:8",
        		"minecraft:riptide mind:8",
        		"minecraft:channeling mind:8",
        		"minecraft:multishot intelligence:15",
        		"minecraft:quick_charge mind:8",
        		"minecraft:piercing intelligence:8",
        		"feathers:lightweight mind:8",
        		"ars_nouveau:mana_boost mind:8",
        		"ars_nouveau:mana_regen mind:8",
        		"ars_nouveau:reactive intelligence:8"),
        		obj -> true);
        
        builder.comment("How much to increase enchantment requirement per enchantment level.");
        ENCHANT_LEVEL_INCREASE = builder.defineInRange("enchantLevelIncrease", 4, 1, 100);
        
        builder.comment("Use the attribute locks list to lock item usage based on their attributes and skill level. Requirements are checked for attributes first before manually set skill locks.");
        USE_ATTRIBUTE_LOCKS = builder.define("useAttributeLocks", true);
        
        builder.comment("Skill requirements for attributes. Used if useAttributeLocks is true.", "Format: attribute skill:level_required_per_attribute_level", "Valid skills: vigor, endurance, strength, dexterity, mind, intelligence");
        ATTRIBUTE_SKILL_LOCKS = builder.defineList("attributeSkillLocks", Arrays.asList("generic.attack_damage strength:1", "generic.armor endurance:2", "generic.attack_speed dexterity:5", "generic.movement_speed dexterity:2", "generic.armor_toughness vigor:3", "ars_nouveau.perk.mana_regen mind:5", "ars_nouveau.perk.spell_damage intelligence:1", "ars_nouveau.perk.flat_max_mana mind:0.1", "attack_range dexterity:4"), obj -> true);
        
        builder.comment("Attribute values under this number will be omitted from vigor skill locks.");
        VIGOR_OMIT = builder.defineInRange("vigorOmit", 0.0, 0.0, 100.0);
        
        builder.comment("Attribute values under this number will be omitted from strength skill locks.");
        STRENGTH_OMIT = builder.defineInRange("strengthOmit", 5.0, 0.0, 100.0);

        builder.comment("Attribute values under this number will be omitted from endurance skill locks.");
        ENDURANCE_OMIT = builder.defineInRange("enduranceOmit", 3.0, 0.0, 100.0);
        
        builder.comment("Attribute values under this number will be omitted from intelligence skill locks.");
        INT_OMIT = builder.defineInRange("intOmit", 0.0, 0.0, 100.0);
        
        builder.comment("Attribute values under this number will be omitted from dexterity skill locks.");
        DEX_OMIT = builder.defineInRange("dexOmit", 1.6, 0.0, 100.0);
        
        builder.comment("Attribute values under this number will be omitted from mind skill locks.");
        MIND_OMIT = builder.defineInRange("mindOmit", 0.0, 0.0, 100.0);
        
        CONFIG_SPEC = builder.build();
	}
	
	public static void load() {
		deathReset = DEATH_RESET.get();
		effectDetriment = EFFECT_DETRIMENT.get();
		useAttributeLocks = USE_ATTRIBUTE_LOCKS.get();
		startingCost = STARTING_COST.get();
		costIncrease = COST_INCREASE.get();
		enchantLevelIncrease = ENCHANT_LEVEL_INCREASE.get();
		maximumLevel = MAXIMUM_LEVEL.get();
		maximumLevelTotal = MAXIMUM_LEVEL_TOTAL.get();
		vigorOmit = VIGOR_OMIT.get();
		strengthOmit = STRENGTH_OMIT.get();
		enduranceOmit = ENDURANCE_OMIT.get();
		intOmit = INT_OMIT.get();
		dexOmit = DEX_OMIT.get();
		mindOmit = MIND_OMIT.get();
		effectPerLevel = EFFECT_PER_LEVEL.get();
		
        for (String line : SKILL_LOCKS.get())
        {
            String[] entry = line.split(" ");
            Requirement[] requirements = new Requirement[entry.length - 1];
            
            for (int i = 1; i < entry.length; i++)
            {
                String[] req = entry[i].split(":");
                
                requirements[i - 1] = new Requirement(Skill.valueOf(req[0].toUpperCase()), Double.parseDouble(req[1]));
            }
            
            skillLocks.put(entry[0], requirements);
        }
        
        for (String line : ENCHANT_SKILL_LOCKS.get())
        {
            String[] entry = line.split(" ");
            Requirement[] requirements = new Requirement[entry.length - 1];
            
            for (int i = 1; i < entry.length; i++)
            {
                String[] req = entry[i].split(":");
                
                requirements[i - 1] = new Requirement(Skill.valueOf(req[0].toUpperCase()), Double.parseDouble(req[1]));
            }
            
            enchantSkillLocks.put(entry[0], requirements);
        }
        
        for (String line : ATTRIBUTE_SKILL_LOCKS.get())
        {
            String[] entry = line.split(" ");
            Requirement[] requirements = new Requirement[entry.length - 1];
            
            for (int i = 1; i < entry.length; i++)
            {
                String[] req = entry[i].split(":");
                
                requirements[i - 1] = new Requirement(Skill.valueOf(req[0].toUpperCase()), Double.parseDouble(req[1]));
            }
            
            attributeSkillLocks.put(entry[0], requirements);
        }
	}

	public static boolean getDeathReset() {
		return deathReset;
	}
	
	public static boolean getWhetherEffectDetriment() {
		return effectDetriment;
	}
	
	public static boolean getIfUseAttributeLocks() {
		return useAttributeLocks;
	}

	public static int getStartCost() {
		return startingCost;
	}

	public static int getCostIncrease() {
		return costIncrease;
	}

	public static int getMaxLevel() {
		return maximumLevel;
	}
	
	public static int getMaxLevelTotal() {
		return maximumLevelTotal;
	}
	
	public static int getEnchantmentRequirementIncrease() {
		return enchantLevelIncrease;
	}
	
	public static int getLevelPerEffect() {
		return effectPerLevel;
	}
	
	public static double getSkillOmitLevel(Skill skill) {
		switch(skill) {
		case VIGOR : {
			return vigorOmit;
			}
		case STRENGTH : {
			return strengthOmit;
		}
		case ENDURANCE : {
			return enduranceOmit;
		}
		case INTELLIGENCE : {
			return intOmit;
		}
		case DEXTERITY : {
			return dexOmit;
		}
		case MIND : {
			return mindOmit;
		}
		default :
			return 0;
		}
	}

	public static Requirement[] getItemRequirements(ResourceLocation key) {
		return skillLocks.get(key.toString());
	}
	
	public static Requirement[] getEnchantmentRequirements(ResourceLocation key) {
		return enchantSkillLocks.get(key.toString());
	}
	
	public static Requirement[] getAttributeRequirements(String name) {
		return attributeSkillLocks.get(name);
	}

	public static ForgeConfigSpec getConfig() {
		return CONFIG_SPEC;
	}
}
