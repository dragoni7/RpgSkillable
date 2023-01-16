package com.github.dragoni7.rpgskillable.common.commands;

import com.github.dragoni7.rpgskillable.Config;
import com.github.dragoni7.rpgskillable.common.capabilities.SkillModel;
import com.github.dragoni7.rpgskillable.common.network.SyncToClient;
import com.github.dragoni7.rpgskillable.common.skill.Skill;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import net.minecraft.commands.Commands;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.server.command.EnumArgument;

public class SetCommand {
	
    static LiteralArgumentBuilder<CommandSourceStack> register()
    {
        return Commands.literal("set")
            .then(Commands.argument("player", EntityArgument.player())
            .then(Commands.argument("skill", EnumArgument.enumArgument(Skill.class))
            .then(Commands.argument("level", IntegerArgumentType.integer(1, Config.getMaxLevel()))
            .executes(SetCommand::execute))));
    }
    
    // Execute Command
    
    private static int execute(CommandContext<CommandSourceStack> context) throws CommandSyntaxException
    {
        ServerPlayer player = EntityArgument.getPlayer(context, "player");
        Skill skill = context.getArgument("skill", Skill.class);
        int level = IntegerArgumentType.getInteger(context, "level");
        
        SkillModel.get(player).setSkillLevel(skill, level, player);
        SyncToClient.send(player);
        
        return 1;
    }

}
