package com.github.dragoni7.rpgskillable.common.network;

import java.util.function.Supplier;

import com.github.dragoni7.rpgskillable.Config;
import com.github.dragoni7.rpgskillable.RpgSkillable;
import com.github.dragoni7.rpgskillable.common.capabilities.SkillModel;
import com.github.dragoni7.rpgskillable.common.skill.Skill;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

public class RequestLevelUp {

	private final int skill;

	public RequestLevelUp(Skill skill) {
		this.skill = skill.index;
	}

	public RequestLevelUp(FriendlyByteBuf buf) {
		skill = buf.readInt();
	}

	public void encode(FriendlyByteBuf buf) {
		buf.writeInt(skill);
	}

	public void handle(Supplier<NetworkEvent.Context> context) {
		context.get().enqueueWork(() -> {
			ServerPlayer player = context.get().getSender();
			SkillModel skillModel = SkillModel.get(player);
			Skill skill = Skill.values()[this.skill];

			int cost = Config.getStartCost() + (skillModel.getSkillLevel(skill) - 1) * Config.getCostIncrease();

			if (skillModel.getSkillLevel(skill) < Config.getMaxLevel() && skillModel.underMaxTotal() && (player.isCreative() || player.experienceLevel >= cost)) {
				if (!player.isCreative())
					player.giveExperienceLevels(-cost);
				skillModel.increaseSkillLevel(skill, player);

				SyncToClient.send(player);
			}
		});

		context.get().setPacketHandled(true);
	}

	public static void send(Skill skill) {
		RpgSkillable.NETWORK.sendToServer(new RequestLevelUp(skill));
	}

}
