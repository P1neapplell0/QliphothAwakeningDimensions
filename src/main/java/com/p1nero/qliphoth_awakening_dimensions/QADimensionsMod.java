package com.p1nero.qliphoth_awakening_dimensions;

import com.finderfeed.fdbosses.init.BossItems;
import com.mojang.logging.LogUtils;
import com.p1nero.qliphoth_awakening_dimensions.placement.QADPlacementTypes;
import com.p1nero.qliphoth_awakening_dimensions.worldgen.QADDimensions;
import com.p1nero.qliphoth_awakening_dimensions.worldgen.QADWorldGenProvider;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
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
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.portal.DimensionTransition;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.neoforged.neoforge.event.entity.living.LivingEntityUseItemEvent;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;
import org.slf4j.Logger;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Mod(QADimensionsMod.MOD_ID)
public class QADimensionsMod {

    public static final String MOD_ID = "qliphoth_awakening_dimensions";
    private static final Logger LOGGER = LogUtils.getLogger();

    public QADimensionsMod(ModContainer modContainer, IEventBus bus) {
        bus.addListener(this::qad$dataSetup);
        NeoForge.EVENT_BUS.addListener(this::qad$onItemUse);
        NeoForge.EVENT_BUS.addListener(this::qad$onToolTip);
        QADPlacementTypes.STRUCTURE_PLACEMENT_TYPES.register(bus);
        modContainer.registerConfig(ModConfig.Type.COMMON, QADConfig.SPEC);
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

            if (itemStack.is(BossItems.EYE_OF_CHESED.get())) {
                teleportToChesedDimension(entity);
            } else if (itemStack.is(BossItems.EYE_OF_MALKUTH.get())) {
                teleportToMalkuthDimension(entity);
            } else {
                return;
            }
            if (entity instanceof ServerPlayer player) {
                player.getCooldowns().addCooldown(itemStack.getItem(), QADConfig.teleportEyeCooldown);
                player.connection.send(new ClientboundSoundPacket(BuiltInRegistries.SOUND_EVENT.wrapAsHolder(SoundEvents.PORTAL_TRAVEL), SoundSource.PLAYERS, player.getX(), player.getY(), player.getZ(), 1.0F, 1.0F, player.getRandom().nextInt()));
            }
        }
    }

    public static void teleportToChesedDimension(LivingEntity entity) {
        MinecraftServer minecraftServer = entity.getServer();
        if(minecraftServer == null) {
            return;
        }
        ServerLevel level = minecraftServer.getLevel(QADDimensions.CHESED_LEVEL_KEY);
        if (level != null) {
            Vec3 targetPosition = Vec3.ZERO;
            BlockPos[] corners = {
                    new BlockPos(48, 62, -48),
                    new BlockPos(-48, 62, -48),
                    new BlockPos(48, 62, 48),
                    new BlockPos(-48, 62, 48)
            };

            for (BlockPos corner : corners) {
                if (!level.getBlockState(corner).is(Blocks.BEDROCK)) {
                    targetPosition = corner.atY(64).getCenter();
                    break;
                }
            }
            entity.changeDimension(new DimensionTransition(level, targetPosition, Vec3.ZERO, entity.getYRot(), entity.getXRot(), DimensionTransition.PLAY_PORTAL_SOUND));
        }
    }

    public static void teleportToMalkuthDimension(LivingEntity entity) {
        MinecraftServer minecraftServer = entity.getServer();
        if(minecraftServer == null) {
            return;
        }
        ServerLevel level = minecraftServer.getLevel(QADDimensions.MALKUTH_LEVEL_KEY);
        if (level != null) {
            Vec3 targetPosition = Vec3.ZERO;
            BlockPos[] corners = {
                    new BlockPos(40, 63, -40),
                    new BlockPos(-40, 63, -40),
                    new BlockPos(40, 63, 40),
                    new BlockPos(-40, 63, 40)
            };

            for (BlockPos corner : corners) {
                if (level.getBlockState(corner).is(Blocks.BLACKSTONE)) {
                    targetPosition = corner.atY(66).getCenter();
                    break;
                }
            }
            entity.changeDimension(new DimensionTransition(level, targetPosition, Vec3.ZERO, entity.getYRot(), entity.getXRot(), DimensionTransition.PLAY_PORTAL_SOUND));
        }
    }

}
