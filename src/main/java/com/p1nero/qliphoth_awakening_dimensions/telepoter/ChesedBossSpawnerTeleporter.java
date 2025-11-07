package com.p1nero.qliphoth_awakening_dimensions.telepoter;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.portal.PortalInfo;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.util.ITeleporter;
import org.jetbrains.annotations.Nullable;

import java.util.function.Function;

public class ChesedBossSpawnerTeleporter implements ITeleporter {

    @Override
    public Entity placeEntity(Entity entity, ServerLevel currentWorld, ServerLevel destinationWorld, float yaw, Function<Boolean, Entity> repositionEntity) {
        return repositionEntity.apply(false);
    }

    @Override
    public @Nullable PortalInfo getPortalInfo(Entity entity, ServerLevel destinationLevel, Function<ServerLevel, PortalInfo> defaultPortalInfo) {

        BlockPos[] corners = {
                new BlockPos(48, 62, -48),
                new BlockPos(-48, 62, -48),
                new BlockPos(48, 62, 48),
                new BlockPos(-48, 62, 48)
        };

        for (BlockPos corner : corners) {
            if (!destinationLevel.getBlockState(corner).is(Blocks.BEDROCK)) {
                BlockPos teleportPos = new BlockPos(corner.getX(), 64, corner.getZ());
                return new PortalInfo(teleportPos.getCenter(), Vec3.ZERO, entity.getYRot(), entity.getXRot());
            }
        }

        return new PortalInfo(entity.position(), Vec3.ZERO, entity.getYRot(), entity.getXRot());
    }

}