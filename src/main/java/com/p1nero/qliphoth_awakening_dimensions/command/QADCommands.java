package com.p1nero.qliphoth_awakening_dimensions.command;

import com.finderfeed.fdlib.FDLib;
import com.mojang.brigadier.CommandDispatcher;
import com.p1nero.qliphoth_awakening_dimensions.QADimensionsMod;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.RegisterCommandsEvent;

@EventBusSubscriber(modid = QADimensionsMod.MOD_ID)
public class QADCommands {

    @SubscribeEvent
    public static void registerCommands(RegisterCommandsEvent event) {
        CommandDispatcher<CommandSourceStack> dispatcher = event.getDispatcher();
        dispatcher.register(Commands.literal(FDLib.MOD_ID)
                .then(Commands.literal("chesed_dim")
                        .requires((commandSourceStack) -> commandSourceStack.hasPermission(2))
                        .executes(commandContext -> {
                            if(commandContext.getSource().getPlayer() != null) {
                                QADimensionsMod.teleportToChesedDimension(commandContext.getSource().getPlayer());
                            }
                            return 0;
                        }))
                .then(Commands.literal("malkuth_dim")
                        .requires((commandSourceStack) -> commandSourceStack.hasPermission(2))
                        .executes(commandContext -> {
                            if(commandContext.getSource().getPlayer() != null) {
                                QADimensionsMod.teleportToMalkuthDimension(commandContext.getSource().getPlayer());
                            }
                            return 0;
                        }))
                .then(Commands.literal("geburah_dim")
                        .requires((commandSourceStack) -> commandSourceStack.hasPermission(2))
                        .executes(commandContext -> {
                            if(commandContext.getSource().getPlayer() != null) {
                                QADimensionsMod.teleportToGeburahDimension(commandContext.getSource().getPlayer());
                            }
                            return 0;
                        }))
        );
    }
}