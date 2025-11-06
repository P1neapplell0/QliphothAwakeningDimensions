package com.p1nero.qliphoth_awakening_dimensions;

import com.finderfeed.fdbosses.init.BossItems;
import com.mojang.logging.LogUtils;
import com.p1nero.qliphoth_awakening_dimensions.placement.QADPlacementTypes;
import com.p1nero.qliphoth_awakening_dimensions.telepoter.ChesedBossSpawnerTeleporter;
import com.p1nero.qliphoth_awakening_dimensions.telepoter.MalkuthBossSpawnerTeleporter;
import com.p1nero.qliphoth_awakening_dimensions.worldgen.QADDimensions;
import com.p1nero.qliphoth_awakening_dimensions.worldgen.QADWorldGenProvider;
import net.minecraft.ChatFormatting;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundSoundPacket;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Mod(QADimensionsMod.MOD_ID)
public class QADimensionsMod {

    public static final String MOD_ID = "qliphoth_awakening_dimensions";
    private static final Logger LOGGER = LogUtils.getLogger();

    public QADimensionsMod(FMLJavaModLoadingContext context) {
        IEventBus bus = context.getModEventBus();
        bus.addListener(this::qad$dataSetup);
        MinecraftForge.EVENT_BUS.addListener(this::qad$onItemUse);
        MinecraftForge.EVENT_BUS.addListener(this::qad$onToolTip);
        QADPlacementTypes.STRUCTURE_PLACEMENT_TYPES.register(bus);
        context.registerConfig(ModConfig.Type.COMMON, QADConfig.SPEC);
    }

    private void qad$dataSetup(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput packOutput = generator.getPackOutput();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();
        generator.addProvider(event.includeServer(), new QADWorldGenProvider(packOutput, lookupProvider));
    }

    private void qad$onToolTip(ItemTooltipEvent event) {
        if (!QADConfig.enableTeleportEye) {
            return;
        }
        if (List.of(BossItems.EYE_OF_CHESED.get(), BossItems.EYE_OF_MALKUTH.get()).contains(event.getItemStack().getItem())) {
            event.getToolTip().add(Component.translatable("tip.qliphoth_awakening_dimensions.enter").withStyle(ChatFormatting.GRAY));
        }
    }

    private void qad$onItemUse(LivingEntityUseItemEvent event) {
        if (!QADConfig.enableTeleportEye) {
            return;
        }
        LivingEntity entity = event.getEntity();
        if (entity.level().isClientSide) {
            return;
        }
        if (entity.isShiftKeyDown()) {
            ItemStack itemStack = event.getItem();
            MinecraftServer minecraftServer = entity.level().getServer();
            if (minecraftServer == null) {
                return;
            }
            if (entity instanceof Player player && player.getCooldowns().isOnCooldown(itemStack.getItem())) {
                return;
            }

            if(itemStack.is(BossItems.EYE_OF_CHESED.get())) {
                ServerLevel level = minecraftServer.getLevel(QADDimensions.CHESED_LEVEL_KEY);
                if(level != null) {
                    entity.changeDimension(level, new ChesedBossSpawnerTeleporter());
                }
            } else if(itemStack.is(BossItems.EYE_OF_MALKUTH.get())) {
                ServerLevel level = minecraftServer.getLevel(QADDimensions.MALKUTH_LEVEL_KEY);
                if(level != null) {
                    entity.changeDimension(level, new MalkuthBossSpawnerTeleporter());
                }
            } else {
                return;
            }
            if (entity instanceof ServerPlayer player) {
                player.getCooldowns().addCooldown(itemStack.getItem(), QADConfig.teleportEyeCooldown);
                player.connection.send(new ClientboundSoundPacket(BuiltInRegistries.SOUND_EVENT.wrapAsHolder(SoundEvents.PORTAL_TRAVEL), SoundSource.PLAYERS, player.getX(), player.getY(), player.getZ(), 1.0F, 1.0F, player.getRandom().nextInt()));
            }
        }
    }

}
