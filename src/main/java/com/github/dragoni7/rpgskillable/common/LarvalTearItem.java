package com.github.dragoni7.rpgskillable.common;

import java.util.List;

import javax.annotation.Nullable;

import com.github.dragoni7.rpgskillable.common.capabilities.SkillModel;
import com.github.dragoni7.rpgskillable.common.network.SyncToClient;

import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

public class LarvalTearItem extends Item {

	public LarvalTearItem(Properties properties) {
		super(properties);
	}
	
	@Override
	public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
		ItemStack itemstack = player.getItemInHand(hand);
		
		if (player instanceof ServerPlayer) {
			
			SkillModel model = SkillModel.get((ServerPlayer) player);
			player.giveExperienceLevels(model.getTotalLevel());
			model.resetSkills((ServerPlayer) player);
			SyncToClient.send(player);
			
			itemstack.shrink(1);
			return InteractionResultHolder.consume(itemstack);
		}
		
		return InteractionResultHolder.fail(itemstack);
	}

	@Override
	public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> component, TooltipFlag flag) {
		super.appendHoverText(stack, level, component, flag);
		component.add(Component.translatable("rpgskillable.tooltip.larval_tear"));
	}

}
