package com.p1nero.qliphoth_awakening_dimensions;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;

@Mod.EventBusSubscriber(modid = QADimensionsMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class QADConfig {
    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();

    private static final ForgeConfigSpec.BooleanValue ENABLE_TELEPORT_EYE = BUILDER.define("enable_teleport_eye", true);
    private static final ForgeConfigSpec.IntValue TELEPORT_EYE_COOLDOWN = BUILDER.defineInRange("teleport_eye_cooldown", 600, 0, Integer.MAX_VALUE);

    static final ForgeConfigSpec SPEC = BUILDER.build();

    public static boolean enableTeleportEye;
    public static int teleportEyeCooldown;

    @SubscribeEvent
    public static void qad$onConfigLoad(ModConfigEvent event) {
        enableTeleportEye = ENABLE_TELEPORT_EYE.get();
        teleportEyeCooldown = TELEPORT_EYE_COOLDOWN.get();
    }

}
