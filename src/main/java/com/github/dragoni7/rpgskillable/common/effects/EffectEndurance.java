package com.github.dragoni7.rpgskillable.common.effects;

import com.elenai.feathers.Feathers;
import com.elenai.feathers.api.FeathersHelper;
import com.elenai.feathers.capability.PlayerFeathersProvider;
import com.elenai.feathers.networking.FeathersMessages;
import com.elenai.feathers.networking.packet.FeatherSyncSTCPacket;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.fml.ModList;

public class EffectEndurance extends MobEffect {
	
	int regenTick = 0;

	protected EffectEndurance() {
		super(MobEffectCategory.BENEFICIAL, 0X717a96);
	}
	
	@Override
	public void applyEffectTick(LivingEntity entity, int amplifier) {
		
		regenTick++;
		// adds 1 endurance feathers every interval. Mimics feather regen but slower.
		if (regenTick == 120) {
			addEnduranceFeather(entity, amplifier);
			regenTick = 0;
		}
	}
	
	public boolean isDurationEffectTick(int duration, int amplifier) {
        return duration > 0;
    }

    public String getDescriptionId() {
        return "rpgskillable.effect.endurance";
    }
    
    private void addEnduranceFeather(LivingEntity target, int amplifier) {
    	if(ModList.get().isLoaded(Feathers.MODID)) {
			if (target instanceof ServerPlayer player) {
				player.getCapability(PlayerFeathersProvider.PLAYER_FEATHERS).ifPresent(f -> {
					int enduranceFeathers = f.getEnduranceFeathers();
					if (enduranceFeathers <= (amplifier + 1) * 2) {
						f.setEnduranceFeathers(1 + enduranceFeathers);
					}
					FeathersMessages.sendToPlayer(new FeatherSyncSTCPacket(f.getFeathers(), FeathersHelper.getPlayerWeight(player), f.getEnduranceFeathers()), player);
				});
			}
		}
    }

}
