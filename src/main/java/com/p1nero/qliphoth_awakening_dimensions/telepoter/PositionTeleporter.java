package com.p1nero.qliphoth_awakening_dimensions.telepoter;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.portal.PortalInfo;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.util.ITeleporter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Function;

public class PositionTeleporter implements ITeleporter {
    @NotNull
    public BlockPos pos;

    public PositionTeleporter(@NotNull BlockPos pos) {
        this.pos = pos;
    }

    @Override
    public Entity placeEntity(Entity entity, ServerLevel currentWorld, ServerLevel destinationWorld, float yaw, Function<Boolean, Entity> repositionEntity) {
        return repositionEntity.apply(false);
    }

    @Override
    public @Nullable PortalInfo getPortalInfo(Entity entity, ServerLevel destinationLevel, Function<ServerLevel, PortalInfo> defaultPortalInfo) {
        PortalInfo pos;
        while (!destinationLevel.getBlockState(this.pos).is(Blocks.AIR)){
            this.pos = this.pos.above();
        }
        pos = new PortalInfo(this.pos.getCenter(), Vec3.ZERO, entity.getYRot(), entity.getXRot());
        return pos;
    }
}