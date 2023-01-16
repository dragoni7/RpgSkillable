package com.github.dragoni7.rpgskillable.common.skill;

public class Requirement {
	
	private final Skill skill;
	private double level;
	
	public Requirement(Skill skill, double level) {
		this.skill = skill;
		this.level = level;
	}
	
	public Skill getSkill() {
		return skill;
	}
	
	public double getLevel() {
		return level;
	}

}
