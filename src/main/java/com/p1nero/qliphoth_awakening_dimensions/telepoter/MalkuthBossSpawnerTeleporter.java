package com.p1nero.qliphoth_awakening_dimensions.telepoter;

import com.finderfeed.fdbosses.content.entities.malkuth_boss.malkuth_boss_spawner.MalkuthBossSpawner;
import com.finderfeed.fdbosses.init.BossEntities;
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
                new BlockPos(40, 63, -40),
                new BlockPos(-40, 63, -40),
                new BlockPos(40, 63, 40),
                new BlockPos(-40, 63, 40)
        };

        for (BlockPos corner : corners) {
            if (destinationLevel.getBlockState(corner).is(Blocks.BLACKSTONE)) {
                BlockPos teleportPos = new BlockPos(corner.getX(), 66, corner.getZ());
                return new PortalInfo(teleportPos.getCenter(), Vec3.ZERO, entity.getYRot(), entity.getXRot());
            }
        }

        return new PortalInfo(entity.position(), Vec3.ZERO, entity.getYRot(), entity.getXRot());
    }
}