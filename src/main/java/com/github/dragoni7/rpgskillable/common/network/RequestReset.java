package com.github.dragoni7.rpgskillable.common.network;

import java.util.function.Supplier;

import com.github.dragoni7.rpgskillable.RpgSkillable;
import com.github.dragoni7.rpgskillable.common.capabilities.SkillModel;
import com.github.dragoni7.rpgskillable.common.skill.Skill;

import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

public class RequestReset {
	
	public void handle(Supplier<NetworkEvent.Context> context) {
		context.get().enqueueWork(() -> {
			
			ServerPlayer player = context.get().getSender();
			SkillModel skillModel = SkillModel.get(player);
			
			if (!player.isCreative()) {
				player.giveExperienceLevels(skillModel.getTotalLevel());
			}
			
			skillModel.resetSkills(player);

			SyncToClient.send(player);
		});

		context.get().setPacketHandled(true);
	}

	public static void send(Skill skill) {
		RpgSkillable.NETWORK.sendToServer(new RequestLevelUp(skill));
	}

}
