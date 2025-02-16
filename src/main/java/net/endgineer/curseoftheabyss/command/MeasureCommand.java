package net.endgineer.curseoftheabyss.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import net.endgineer.curseoftheabyss.CurseOfTheAbyss;
import net.endgineer.curseoftheabyss.common.CurseProvider;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

public class MeasureCommand {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("abyss").requires(stack -> { return stack.hasPermission(2); })
            .then(Commands.literal("measure")
                .then(Commands.literal("start").then(Commands.argument("player", EntityArgument.player())).executes(stack -> measureStart(stack)))
                .then(Commands.literal("stop").then(Commands.argument("player", EntityArgument.player())).executes(stack -> measureStop(stack)))
            )
        );
    }

    public static int measureStart(CommandContext<CommandSourceStack> stack) {
        CommandSourceStack source = stack.getSource();
        ServerPlayer player;

        try {
            player = EntityArgument.getPlayer(stack, "player");
            player.getCapability(CurseProvider.CURSE).ifPresent(curse -> {
                if(curse.startMeasuring(player)) {
                    source.sendSuccess(() -> Component.translatable("commands."+CurseOfTheAbyss.MODID+".measure.start.success", player.getName()), true);
                } else {
                    source.sendFailure(Component.translatable("commands."+CurseOfTheAbyss.MODID+".measure.start.failure", player.getName()));
                }
            });
        } catch(CommandSyntaxException e) {
            source.sendFailure(Component.translatable("commands."+CurseOfTheAbyss.MODID+".all.failure.player_dne"));
        }
        
        return Command.SINGLE_SUCCESS;
    }

    public static int measureStop(CommandContext<CommandSourceStack> stack) {
        CommandSourceStack source = stack.getSource();
        ServerPlayer player;

        try {
            player = EntityArgument.getPlayer(stack, "player");
            player.getCapability(CurseProvider.CURSE).ifPresent(curse -> {
                if(curse.stopMeasuring(player)) {
                    source.sendSuccess(() -> Component.translatable("commands."+CurseOfTheAbyss.MODID+".measure.stop.success", player.getName()), true);
                } else {
                    source.sendFailure(Component.translatable("commands."+CurseOfTheAbyss.MODID+".measure.stop.failure", player.getName()));
                }
            });
        } catch(CommandSyntaxException e) {
            source.sendFailure(Component.translatable("commands."+CurseOfTheAbyss.MODID+".all.failure.player_dne"));
        }
        
        return Command.SINGLE_SUCCESS;
    }
}
