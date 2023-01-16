package com.github.dragoni7.rpgskillable.common.skill;

public enum Skill {
	
	VIGOR(0, "skill.vigor"),
	STRENGTH(1, "skill.strength"),
	MIND(2, "skill.mind"),
	DEXTERITY(3, "skill.dexterity"),
	ENDURANCE(4, "skill.endurance"),
	INTELLIGENCE(5, "skill.intelligence");
	
	public final int index;
	public final String displayName;
	
	Skill(int index, String displayName) {
		this.index = index;
		this.displayName = displayName;
	}
}
