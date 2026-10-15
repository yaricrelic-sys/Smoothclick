package com.sneakclick;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ViewportEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

@OnlyIn(Dist.CLIENT)
public class SneakHandler {
    private double currentSneakOffset = 0.0;

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;
        if (!SneakClickConfig.INSTANCE.sneakEnabled.get()) return;

        Minecraft mc = Minecraft.getInstance();
        LocalPlayer player = mc.player;
        if (player == null) return;

        boolean isSneaking = player.isShiftKeyDown();
        double target = isSneaking ? SneakClickConfig.INSTANCE.sneakHeight.get() : 0.0;
        double speed = SneakClickConfig.INSTANCE.sneakSpeed.get();

        if (Math.abs(currentSneakOffset - target) > 0.001) {
            double diff = target - currentSneakOffset;
            currentSneakOffset += diff * speed * 0.2;
        } else {
            currentSneakOffset = target;
        }
    }

    @SubscribeEvent
    public void onCameraSetup(ViewportEvent.ComputeCameraAngles event) {
        if (!SneakClickConfig.INSTANCE.sneakEnabled.get()) return;

        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null) return;

        double vanillaOffset = mc.player.isShiftKeyDown() ? 0.3 : 0.0;
        double correction = vanillaOffset - currentSneakOffset;
        event.setPitch(event.getPitch() + (float)(correction * 20));
    }
}
