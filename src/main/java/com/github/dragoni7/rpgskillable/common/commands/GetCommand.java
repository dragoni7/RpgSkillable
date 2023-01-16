package com.github.dragoni7.rpgskillable.common.commands;

import com.github.dragoni7.rpgskillable.common.capabilities.SkillModel;
import com.github.dragoni7.rpgskillable.common.skill.Skill;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import net.minecraft.commands.Commands;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.server.command.EnumArgument;

public class GetCommand {
	
    static LiteralArgumentBuilder<CommandSourceStack> register()
    {
        return Commands.literal("get")
            .then(Commands.argument("player", EntityArgument.player())
            .then(Commands.argument("skill", EnumArgument.enumArgument(Skill.class))
            .executes(GetCommand::execute)));
    }
    
    // Execute Command
    
    private static int execute(CommandContext<CommandSourceStack> context) throws CommandSyntaxException
    {
        ServerPlayer player = EntityArgument.getPlayer(context, "player");
        Skill skill = context.getArgument("skill", Skill.class);
        int level = SkillModel.get(player).getSkillLevel(skill);
        context.getSource().sendSuccess(Component.literal(skill.displayName).append(" " + level), true);
        
        return level;
    }

}
