package com.github.dragoni7.rpgskillable.common.effects;

import java.util.UUID;

import com.elenai.feathers.Feathers;
import com.elenai.feathers.api.FeathersHelper;
import com.elenai.feathers.capability.PlayerFeathersProvider;
import com.elenai.feathers.networking.FeathersMessages;
import com.elenai.feathers.networking.packet.FeatherSyncSTCPacket;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraftforge.fml.ModList;

public class EffectEndurance extends MobEffect {
	
	private static final UUID ARMOR_TOUGHNESS_UUID = UUID.fromString("87168667-830c-41e0-9448-8f889cbaf262");

	protected EffectEndurance() {
		super(MobEffectCategory.BENEFICIAL, 0X717a96);
		
		this.addAttributeModifier(Attributes.ARMOR_TOUGHNESS, ARMOR_TOUGHNESS_UUID.toString(), 0.5f, AttributeModifier.Operation.ADDITION);
	}
	
	@Override
	public void addAttributeModifiers(LivingEntity target, AttributeMap map, int amplifier) {
		if(ModList.get().isLoaded(Feathers.MODID)) {
			if (target instanceof ServerPlayer player) {
				player.getCapability(PlayerFeathersProvider.PLAYER_FEATHERS).ifPresent(f -> {
					f.setEnduranceFeathers((amplifier+1) * 3);
					FeathersMessages.sendToPlayer(new FeatherSyncSTCPacket(f.getFeathers(), FeathersHelper.getPlayerWeight(player), f.getEnduranceFeathers()), player);
				});
			}
		}
		
		super.addAttributeModifiers(target, map, amplifier);
	}
	
	@Override
    public void removeAttributeModifiers(LivingEntity target, AttributeMap map, int amplifier) {
		if(ModList.get().isLoaded(Feathers.MODID)) {
	    	if(target instanceof ServerPlayer player) {
	    		player.getCapability(PlayerFeathersProvider.PLAYER_FEATHERS).ifPresent(f -> {
					f.setEnduranceFeathers(0);
					FeathersMessages.sendToPlayer(new FeatherSyncSTCPacket(f.getFeathers(), FeathersHelper.getPlayerWeight(player), f.getEnduranceFeathers()), player);
	    		});
	    	}
		}
		
    	super.removeAttributeModifiers(target, map, amplifier);
    }
	
	public boolean isDurationEffectTick(int duration, int amplifier) {
        return duration > 0;
    }

    public String getDescriptionId() {
        return "rpgskillable.effect.endurance";
    }

}
