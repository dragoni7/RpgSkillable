package com.github.dragoni7.rpgskillable.common.effects;

import com.github.dragoni7.rpgskillable.RpgSkillable;
import net.minecraft.world.effect.MobEffect;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class RpgSkillableEffects {
	
	public static final DeferredRegister<MobEffect> MOB_EFFECTS = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, RpgSkillable.MODID);
	
	public static final RegistryObject<MobEffect> ENCUMBERED = MOB_EFFECTS.register("encumbered", () -> new EffectEncumbered());
	public static final RegistryObject<MobEffect> ATROPHY = MOB_EFFECTS.register("atrophy", () -> new EffectAtrophy());
	
	public static final RegistryObject<MobEffect> ENDURANCE = MOB_EFFECTS.register("endurance", () -> new EffectEndurance());
	public static final RegistryObject<MobEffect> VIGOR = MOB_EFFECTS.register("vigor", () -> new EffectVigor());
	public static final RegistryObject<MobEffect> DEXTERITY = MOB_EFFECTS.register("dexterity", () -> new EffectDexterity());
	public static final RegistryObject<MobEffect> STRENGTHENED = MOB_EFFECTS.register("strengthened", () -> new EffectStrengthened());
	public static final RegistryObject<MobEffect> MIND = MOB_EFFECTS.register("mind", () -> new EffectMind());
	public static final RegistryObject<MobEffect> INTELLIGENCE = MOB_EFFECTS.register("intelligence", () -> new EffectIntelligence());

}
