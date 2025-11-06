package com.p1nero.qliphoth_awakening_dimensions.command;

import com.finderfeed.fdlib.FDLib;
import com.mojang.brigadier.CommandDispatcher;
import com.p1nero.qliphoth_awakening_dimensions.QADimensionsMod;
import com.p1nero.qliphoth_awakening_dimensions.telepoter.ChesedBossSpawnerTeleporter;
import com.p1nero.qliphoth_awakening_dimensions.telepoter.MalkuthBossSpawnerTeleporter;
import com.p1nero.qliphoth_awakening_dimensions.worldgen.QADDimensions;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = QADimensionsMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class QADCommands {

    @SubscribeEvent
    public static void registerCommands(RegisterCommandsEvent event) {
        CommandDispatcher<CommandSourceStack> dispatcher = event.getDispatcher();
        dispatcher.register(Commands.literal(FDLib.MOD_ID)
                .then(Commands.literal("chesed_dim")
                        .requires((commandSourceStack) -> commandSourceStack.hasPermission(2))
                        .executes(commandContext -> {
                            if(commandContext.getSource().getPlayer() != null) {
                                commandContext.getSource().getPlayer().changeDimension(commandContext.getSource().getServer().getLevel(QADDimensions.CHESED_LEVEL_KEY), new ChesedBossSpawnerTeleporter());
                            }
                            return 0;
                        }))
                .then(Commands.literal("malkuth_dim")
                        .requires((commandSourceStack) -> commandSourceStack.hasPermission(2))
                        .executes(commandContext -> {
                            if(commandContext.getSource().getPlayer() != null) {
                                commandContext.getSource().getPlayer().changeDimension(commandContext.getSource().getServer().getLevel(QADDimensions.MALKUTH_LEVEL_KEY), new MalkuthBossSpawnerTeleporter());
                            }
                            return 0;
                        }))
        );
    }
}