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

public class MalkuthBossSpawnerTeleporter implements ITeleporter {

    @Override
    public @Nullable PortalInfo getPortalInfo(Entity entity, ServerLevel destinationLevel, Function<ServerLevel, PortalInfo> defaultPortalInfo) {
        BlockPos[] corners = {
                new BlockPos(10, 64, 0),
                new BlockPos(-10, 64, 0),
                new BlockPos(0, 64, 10),
                new BlockPos(0, 64, -10)
        };

        for (BlockPos corner : corners) {
            if (destinationLevel.getBlockState(corner).is(Blocks.BLACKSTONE) && !destinationLevel.getBlockState(corner.above()).is(Blocks.POLISHED_BLACKSTONE_SLAB)) {
                BlockPos teleportPos = new BlockPos(corner.getX(), 66, corner.getZ());
                return new PortalInfo(teleportPos.getCenter(), Vec3.ZERO, entity.getYRot(), entity.getXRot());
            }
        }

        return new PortalInfo(entity.position(), Vec3.ZERO, entity.getYRot(), entity.getXRot());
    }
}