package net.endgineer.curseoftheabyss.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;

import net.endgineer.curseoftheabyss.CurseOfTheAbyss;
import net.endgineer.curseoftheabyss.common.CurseProvider;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

public class RecordCommand {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("abyss").requires(stack -> { return stack.hasPermission(2); })
            .then(Commands.literal("record")
                .then(Commands.literal("start").executes(stack -> recordStart(stack.getSource())))
                .then(Commands.literal("stop").executes(stack -> recordStop(stack.getSource())))
            )
        );
    }

    public static int recordStart(CommandSourceStack stack) {
        if(stack.getEntity() instanceof ServerPlayer player) {
            stack.sendSuccess(() -> Component.translatable("commands."+CurseOfTheAbyss.MODID+".record.start"), true);
            player.getCapability(CurseProvider.CURSE).ifPresent(curse -> {});
        } else {
            stack.sendFailure(Component.translatable("commands."+CurseOfTheAbyss.MODID+".failed.console"));
        }

        return Command.SINGLE_SUCCESS;
    }

    public static int recordStop(CommandSourceStack stack) {
        if(stack.getEntity() instanceof ServerPlayer player) {
            stack.sendSuccess(() -> Component.translatable("commands."+CurseOfTheAbyss.MODID+".record.stop"), true);
            player.getCapability(CurseProvider.CURSE).ifPresent(curse -> {});
        } else {
            stack.sendFailure(Component.translatable("commands."+CurseOfTheAbyss.MODID+".failed.console"));
        }

        return Command.SINGLE_SUCCESS;
    }
}
