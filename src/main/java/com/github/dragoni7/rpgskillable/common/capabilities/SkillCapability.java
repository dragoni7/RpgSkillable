package com.github.dragoni7.rpgskillable.common.capabilities;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;

public class SkillCapability {
	
	public static Capability<SkillModel> SKILL_MODEL = CapabilityManager.get(new CapabilityToken<SkillModel>(){});

}
