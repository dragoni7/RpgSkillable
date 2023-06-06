package com.github.dragoni7.rpgskillable;

import java.util.ArrayList;
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
	private static final ForgeConfigSpec CLIENT_CONFIG_SPEC;
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
	
	private static final ForgeConfigSpec.ConfigValue<List<? extends String>> OVERRIDE_SKILL_LOCKS;
	private static final ForgeConfigSpec.ConfigValue<List<? extends String>> ENCHANT_SKILL_LOCKS;
	private static final ForgeConfigSpec.ConfigValue<List<? extends String>> ATTRIBUTE_SKILL_LOCKS;
	private static final ForgeConfigSpec.ConfigValue<List<? extends String>> IGNORED;
	
	private static final ForgeConfigSpec.ConfigValue<List<? extends Integer>> INV_TAB_OFFSET;
	private static final ForgeConfigSpec.ConfigValue<List<? extends Integer>> SKILL_TAB_OFFSET;
	private static final ForgeConfigSpec.BooleanValue CREATIVE_HIDDEN;
	
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
	
	private static final Map<String, Requirement[]> overrideSkillLocks = new HashMap<>();
	private static final Map<String, Requirement[]> enchantSkillLocks = new HashMap<>();
	private static final Map<String, Requirement[]> attributeSkillLocks = new HashMap<>();
	private static final List<String> ignored = new ArrayList<>();
	
	private static final List<Integer> invTabOffset = new ArrayList<>();
	private static final List<Integer> skillTabOffset = new ArrayList<>();
	private static boolean creativeHidden;
	
	static {
		
        ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();
    
        builder.comment("Reset all skills to 1 when a player dies.");
        DEATH_RESET = builder.define("deathReset", false);
        
        builder.comment("Whether the player should be inflicted with negative effects while wielding items with unmet requirements, or to cancel the attack and change equipment events with unmet requirements. Curios are always dropped.");
        EFFECT_DETRIMENT = builder.define("effectDetriment", true);
        
        builder.comment("Initial cost for upgrading a skill, in levels.");
        STARTING_COST = builder.defineInRange("startingCost", 5, 0, 10);
        
        builder.comment("Increase to cost per level obtained. (use 0 for constant cost).");
        COST_INCREASE = builder.defineInRange("costIncrease", 1, 0, 10);
        
        builder.comment("Maximum level each skill can be upgraded to.");
        MAXIMUM_LEVEL = builder.defineInRange("maximumLevel", 31, 2, Integer.MAX_VALUE);
        
        builder.comment("Maximum levels the player can have. This is the sum of all skill levels.");
        MAXIMUM_LEVEL_TOTAL = builder.defineInRange("maximumLevelTotal", 66, 6, Integer.MAX_VALUE);
        
        builder.comment("How many levels are required per level of positive skill effect. Example: setting this to 6 will grant a level of positive effect every 6 levels in that skill.");
        EFFECT_PER_LEVEL = builder.defineInRange("effectPerLevel", 6, 1, 100);
        
        builder.comment("skill requirements for blocks, items, or riding entities. Can be used to override items affected by attribute skill locks Ex. override skill lock of a sword to a specific value.", "Format: mod:id skill:level", "Valid skills: vigor, endurance, strength, dexterity, mind, intelligence");
        OVERRIDE_SKILL_LOCKS = builder.defineList("overrideSkillLocks", Arrays.asList(
        		"ars_nouveau:scribes_table intelligence:2",
        		"ars_nouveau:alteration_table intelligence:3",
        		"ars_nouveau:imbuement_chamber intelligence:2",
        		// Ars Glyphs:
        		"ars_nouveau:glyph_invisibility mind:4",
        		"ars_nouveau:glyph_phantom_block mind:2",
        		"ars_nouveau:glyph_fortune mind:4",
        		"ars_nouveau:glyph_wither intelligence:4",
        		"ars_nouveau:glyph_toss intelligence:3",
        		"ars_nouveau:glyph_cut intelligence:3",
        		"ars_nouveau:glyph_grow mind:4",
        		"ars_nouveau:glyph_summon_wolves intelligence:2",
        		"ars_nouveau:glyph_name mind:2",
        		"ars_nouveau:glyph_dampen intelligence:3",
        		"ars_nouveau:glyph_gravity intelligence:3",
        		"ars_nouveau:glyph_firework intelligence:2",
        		"ars_nouveau:glyph_split intelligence:4",
        		"ars_nouveau:glyph_snare intelligence:2",
        		"ars_nouveau:glyph_toss intelligence:1",
        		"ars_nouveau:glyph_crush intelligence:2",
        		"ars_nouveau:glyph_evaporate intelligence:1",
        		"ars_nouveau:glyph_conjure_water mind:1",
        		"ars_nouveau:glyph_lightning intelligence:3",
        		"ars_nouveau:glyph_pickup mind:1",
        		"ars_nouveau:glyph_smelt mind:1",
        		"ars_nouveau:glyph_launch mind:2",
        		"ars_nouveau:glyph_rotate mind:1",
        		"ars_nouveau:glyph_blink mind:4",
        		"ars_nouveau:glyph_ignite intelligence:2",
        		"ars_nouveau:glyph_amplify intelligence:3",
        		"ars_nouveau:glyph_bounce mind:2",
        		"ars_nouveau:glyph_heal mind:3",
        		"ars_nouveau:glyph_glide mind:4",
        		"ars_nouveau:glyph_leap mind:3",
        		"ars_nouveau:glyph_fangs intelligence:4",
        		"ars_nouveau:glyph_exchange mind:3",
        		"ars_nouveau:glyph_fell mind:3",
        		"ars_nouveau:glyph_sensitive mind:2",
        		"ars_nouveau:glyph_redstone_signal mind:1",
        		"ars_nouveau:glyph_duration_down mind:2",
        		"ars_nouveau:glyph_intangible mind:4",
        		"ars_nouveau:glyph_ender_inventory mind:3",
        		"ars_nouveau:glyph_interact mind:1",
        		"ars_nouveau:glyph_place_block mind:1",
        		"ars_nouveau:glyph_harvest mind:1",
        		"ars_nouveau:glyph_knockback intelligence:2",
        		"ars_nouveau:glyph_freeze intelligence:2",
        		"ars_nouveau:glyph_wall mind:4",
        		"ars_nouveau:glyph_summon_steed intelligence:2",
        		"ars_nouveau:glyph_summon_vex intelligence:4",
        		"ars_nouveau:glyph_aoe intelligence:4",
        		"ars_nouveau:glyph_decelerate mind:2",
        		"ars_nouveau:glyph_extend_time mind:3",
        		"ars_nouveau:glyph_orbit mind:4",
        		"ars_nouveau:glyph_accelerate mind:4",
        		"ars_nouveau:glyph_summon_undead intelligence:3",
        		"ars_nouveau:glyph_summon_rune mind:2",
        		"ars_nouveau:glyph_flare intelligence:2",
        		"ars_nouveau:glyph_explosion intelligence:4",
        		"ars_nouveau:glyph_craft mind:1",
        		"ars_nouveau:glyph_wind_shear intelligence:2",
        		"ars_nouveau:glyph_sense_magic mind:2",
        		"ars_nouveau:glyph_pull intelligence:2",
        		"ars_nouveau:glyph_pierce intelligence:4",
        		"ars_nouveau:glyph_slowfall mind:4",
        		"ars_nouveau:glyph_extract mind:3",
        		"ars_nouveau:glyph_delay mind:2",
        		"ars_nouveau:glyph_cold_snap intelligence:3",
        		"ars_nouveau:glyph_summon_decoy intelligence:4",
        		"ars_nouveau:glyph_infuse mind:3",
        		"ars_nouveau:glyph_hex intelligence:4",
        		"ars_nouveau:glyph_underfoot mind:2",
        		"ars_nouveau:glyph_linger mind:4",
        		"ars_nouveau:glyph_light mind:2",
        		"ars_nouveau:glyph_dispel mind:4",
        		"ars_nouveau:apprentice_spell_book intelligence:2",
        		"ars_nouveau:archmage_spell_book intelligence:4",
        		"ars_nouveau:enchanters_sword intelligence:5",
        		"ars_nouveau:enchanters_shield intelligence:5",
        		"ars_nouveau:enchanters_bow intelligence:4",
        		"ars_nouveau:spell_crossbow intelligence:6",
        		"ars_nouveau:caster_tome intelligence:4",
        		"ars_nouveau:wand intelligence:4",
        		"ars_nouveau:enchanters_mirror intelligence:2",
        		"ars_nouveau:belt_of_levitation mind:10",
        		"ars_nouveau:belt_of_unstable_gifts mind:10",
        		"minecraft:enchanted_golden_apple mind:10",
        		"minecraft:enchanting_table intelligence:3",
        		"minecraft:brewing_stand intelligence:2",
        		"minecraft:potion mind:2",
        		"minecraft:lingering_potion mind:2",
        		"minecraft:splash_potion intelligence:2",
        		"minecraft:shield vigor:5",
        		"minecraft:crossbow dexterity:6",
        		"minecraft:trident dexterity:6",
        		"minecraft:bow dexterity:4",
        		"minecraft:elytra dexterity:10",
        		"minecraft:horse dexterity:2",
        		"minecraft:donkey dexterity:2",
        		"minecraft:mule dexterity:2",
        		"minecraft:strider dexterity:3"),
        		obj -> true);
        
        builder.comment("Skill requirements for enchantments. Ideally this should be different than the base item's skill locks to prevent overlap.", "Format: mod:id skill:level", "Valid skills: vigor, endurance, strength, dexterity, mind, intelligence");
        ENCHANT_SKILL_LOCKS = builder.defineList("enchantSkillLocks", Arrays.asList(
        		"minecraft:protection mind:2",
        		"minecraft:efficiency mind:2",
        		"minecraft:silk_touch mind:3",
        		"minecraft:unbreaking mind:3",
        		"minecraft:fortune mind:3",
        		"minecraft:luck_of_the_sea mind:3",
        		"minecraft:lure mind:2",
        		"minecraft:mending mind:4",
        		"minecraft:fire_protection mind:2",
        		"minecraft:feather_falling mind:2",
        		"minecraft:blast_protection mind:2",
        		"minecraft:projectile_protection mind:2",
        		"minecraft:respiration mind:2",
        		"minecraft:aqua_affinity mind:2",
        		"minecraft:thorns mind:2",
        		"minecraft:depth_strider mind:2",
        		"minecraft:frost_walker mind:2",
        		"minecraft:soul_speed mind:2",
        		"minecraft:swift_sneak mind:2",
        		"minecraft:sharpness mind:3",
        		"minecraft:smite intelligence:2",
        		"minecraft:bane_of_arthropods intelligence:2",
        		"minecraft:knockback intelligence:2",
        		"minecraft:fire_aspect intelligence:2",
        		"minecraft:looting mind:3",
        		"minecraft:sweeping_edge intelligence:3",
        		"minecraft:unbreaking mind:3",
        		"minecraft:power intelligence:2",
        		"minecraft:punch intelligence:2",
        		"minecraft:flame intelligence:2",
        		"minecraft:infinity mind:4",
        		"minecraft:loyalty mind:3",
        		"minecraft:impaling intelligence:3",
        		"minecraft:riptide mind:4",
        		"minecraft:channeling mind:2",
        		"minecraft:multishot intelligence:3",
        		"minecraft:quick_charge mind:2",
        		"minecraft:piercing intelligence:2",
        		"feathers:lightweight mind:3",
        		"ars_nouveau:mana_boost mind:3",
        		"ars_nouveau:mana_regen mind:3",
        		"ars_nouveau:reactive intelligence:3"),
        		obj -> true);
        
        builder.comment("How much to increase enchantment requirement per enchantment level.");
        ENCHANT_LEVEL_INCREASE = builder.defineInRange("enchantLevelIncrease", 1, 1, 100);
        
        builder.comment("Use the attribute locks list to lock item usage based on their attributes.");
        USE_ATTRIBUTE_LOCKS = builder.define("useAttributeLocks", true);
        
        builder.comment("Skill requirements for attributes. Used if useAttributeLocks is true. Items affected by this will be overriden by the skill locks list above.", "Format: attribute skill:level_required_per_attribute_level", "Valid skills: vigor, endurance, strength, dexterity, mind, intelligence");
        ATTRIBUTE_SKILL_LOCKS = builder.defineList("attributeSkillLocks", Arrays.asList("generic.attack_damage strength:0.5", "generic.armor endurance:0.8", "generic.attack_speed dexterity:2", "generic.movement_speed dexterity:1", "generic.armor_toughness vigor:1", "ars_nouveau.perk.mana_regen mind:2", "ars_nouveau.perk.spell_damage intelligence:0.5", "ars_nouveau.perk.flat_max_mana mind:0.1", "attack_range dexterity:2"), obj -> true);
        
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
        
        builder.comment("Requirements blacklist. Entries in here will not have any skill locks applied.", "Format: mod_id:item");
        IGNORED = builder.defineList("ignored", Arrays.asList("minecraft:wooden_axe"), obj -> true);
        
        CONFIG_SPEC = builder.build();
        
        builder = new ForgeConfigSpec.Builder();
        
        builder.comment("x and y offsets for the inventory tabs. Offsets start from the top left corner of the inventory screen. Default: [56, -27] [25, -27]");
        INV_TAB_OFFSET = builder.defineList("inv_tab_offset", Arrays.asList(25,-27), obj -> true);
        SKILL_TAB_OFFSET = builder.defineList("skill_tab_offset", Arrays.asList(56,-27), obj -> true);
        builder.comment("Should the skill tab be hidden while in creative mode?");
        CREATIVE_HIDDEN = builder.define("creativeHidden", true);
        
        CLIENT_CONFIG_SPEC = builder.build();
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
		
        for (String line : OVERRIDE_SKILL_LOCKS.get())
        {
            String[] entry = line.split(" ");
            Requirement[] requirements = new Requirement[entry.length - 1];
            
            for (int i = 1; i < entry.length; i++)
            {
                String[] req = entry[i].split(":");
                
                requirements[i - 1] = new Requirement(Skill.valueOf(req[0].toUpperCase()), Double.parseDouble(req[1]));
            }
            
            overrideSkillLocks.put(entry[0], requirements);
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
        
        for (String line : IGNORED.get())
        {   
            ignored.add(line);
        }
	}
	
	public static void loadClient() {
		
		invTabOffset.addAll(INV_TAB_OFFSET.get());
		skillTabOffset.addAll(SKILL_TAB_OFFSET.get());
		creativeHidden = CREATIVE_HIDDEN.get();
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
		return overrideSkillLocks.get(key.toString());
	}
	
	public static Requirement[] getEnchantmentRequirements(ResourceLocation key) {
		return enchantSkillLocks.get(key.toString());
	}
	
	public static Requirement[] getAttributeRequirements(String name) {
		return attributeSkillLocks.get(name);
	}
	
	public static List<String> getBlacklist() {
		return ignored;
	}

	public static ForgeConfigSpec getConfig() {
		return CONFIG_SPEC;
	}
	
	public static int getInvXOffset() {
		return invTabOffset.get(0);
	}
	
	public static int getInvYOffset() {
		return invTabOffset.get(1);
	}
	
	public static int getSkillXOffset() {
		return skillTabOffset.get(0);
	}
	
	public static int getSkillYOffset() {
		return skillTabOffset.get(1);
	}
	
	public static boolean getIfCreativeHidden() {
		return creativeHidden;
	}
	
	public static ForgeConfigSpec getClientConfig() {
		return CLIENT_CONFIG_SPEC;
	}
}
