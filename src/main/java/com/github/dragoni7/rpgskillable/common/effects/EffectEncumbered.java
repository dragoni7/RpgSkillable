package com.github.dragoni7.rpgskillable.common.effects;

import java.util.UUID;

import com.elenai.feathers.Feathers;
import com.elenai.feathers.api.FeathersHelper;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.fml.ModList;

public class EffectEncumbered extends MobEffect {
	
	private static final UUID MOVE_SPEED_UUID = UUID.fromString("a51acd4e-5233-49e0-8eb9-0c9df9ba9424");
	private static final UUID JUMP_UUID = UUID.fromString("53d72df5-11fd-4db5-8cc4-e4584f03be25");

	protected EffectEncumbered() {
		super(MobEffectCategory.NEUTRAL, 0X373a42);
		this.addAttributeModifier(Attributes.MOVEMENT_SPEED, MOVE_SPEED_UUID.toString(), -0.45f, AttributeModifier.Operation.MULTIPLY_BASE);
		this.addAttributeModifier(Attributes.JUMP_STRENGTH, JUMP_UUID.toString(), -0.9f, AttributeModifier.Operation.MULTIPLY_BASE);
	}
	
	@Override
	public void applyEffectTick(LivingEntity entity, int amplifier) {
		
		Vec3 motion = entity.getDeltaMovement();
		
		if (entity instanceof ServerPlayer) {
			if (isMoving(motion)) {
				
				// prevent jumping / flying
				if (motion.y > 0.0D) {
					entity.setDeltaMovement(motion.subtract(0.0D, motion.y / 2.0D, 0.0D));
				}
				
				// drain feathers while moving.
				if (ModList.get().isLoaded(Feathers.MODID)) {
					FeathersHelper.spendFeathers((ServerPlayer) entity, 1);
				}
			}
		}
	}
	
	public boolean isDurationEffectTick(int duration, int amplifier) {
        return duration > 0;
    }

    public String getDescriptionId() {
        return "rpgskillable.effect.encumbered";
    }
    
    private boolean isMoving(Vec3 motion) {
    	return motion.x > 0 || motion.y > 0 || motion.z > 0;
    }

}
