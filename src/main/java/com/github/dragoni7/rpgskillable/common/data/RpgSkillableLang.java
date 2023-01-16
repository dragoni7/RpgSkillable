package com.github.dragoni7.rpgskillable.common.data;

import com.github.dragoni7.rpgskillable.RpgSkillable;
import com.github.dragoni7.rpgskillable.common.effects.RpgSkillableEffects;

import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.LanguageProvider;

public class RpgSkillableLang extends LanguageProvider  {

	public RpgSkillableLang(DataGenerator gen, String locale) {
		super(gen, RpgSkillable.MODID, locale);
	}

	@Override
	protected void addTranslations() {
		
		// item
		add(RpgSkillable.LARVAL_TEAR.get(), "Larval Tear");
		add("rpgskillable.tooltip.larval_tear", "Resets all skills on use. Returns xp levels equal to the total amount of levels one had");
		
		// effects
		add(RpgSkillableEffects.ATROPHY.get(), "Atrophy");
		add(RpgSkillableEffects.ENCUMBERED.get(), "Encumbered");
		add(RpgSkillableEffects.DEXTERITY.get(), "Dexterity");
		add(RpgSkillableEffects.ENDURANCE.get(), "Endurance");
		add(RpgSkillableEffects.INTELLIGENCE.get(), "Intelligence");
		add(RpgSkillableEffects.MIND.get(), "Mind");
		add(RpgSkillableEffects.STRENGTHENED.get(), "Strengthened");
		add(RpgSkillableEffects.VIGOR.get(), "Vigor");
		
		// UI
		add("container.skills", "Skills");
		add("tooltip.requirements", "Requirements");
		add("key.rpgskillable.open_skill_screen", "Open Skills");
		add("key.category.rpgskillable.general", "RpgSkillable");
		add("overlay.main_hand_message", "Your right arm struggles to wield the item");
		add("overlay.off_hand_message", "Your left arm struggles to wield the item");
		add("overlay.default_message", "You lack the skills to use this!");
		
		// skills
		add("skill.vigor", "Vigor");
		add("skill.endurance", "Endurance");
		add("skill.strength", "Strength");
		add("skill.mind", "Mind");
		add("skill.dexterity", "Dex");
		add("skill.intelligence", "Int");
		
		// JEED
		add("effect.rpgskillable.atrophy.description", "Severly reduces attack speed, damage, and spell damage.");
		add("effect.rpgskillable.encumbered.description", "Severly reduces movespeed and prevents jumping. Drains feathers.");
		add("effect.rpgskillable.vigor.description", "Increases max health by 4 per level.");
		add("effect.rpgskillable.strengthened.description", "Increases attack damage by 1 per level. Also decreases attack speed by 0.1 per level.");
		add("effect.rpgskillable.endurance.description", "Adds regenerating endurance feathers per level");
		add("effect.rpgskillable.dexterity.description", "Increases attack speed by 0.15 and increases movespeed per level.");
		add("effect.rpgskillable.intelligence.description", "Increases spell damage by 1 per level.");
		add("effect.rpgskillable.mind.description", "Increases mana by 20% per level.");
	}

}
