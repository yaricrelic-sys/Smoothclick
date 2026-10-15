package com.sneakclick;

import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.TridentItem;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.lwjgl.glfw.GLFW;

@OnlyIn(Dist.CLIENT)
public class ClickHandler {
    private long lastClick = 0;
    private boolean toggled = false;
    private boolean lastKeyState = false;

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;
        if (!SneakClickConfig.INSTANCE.clickerEnabled.get()) return;

        Minecraft mc = Minecraft.getInstance();
        LocalPlayer player = mc.player;
        if (player == null || mc.screen != null) return;

        String keyName = SneakClickConfig.INSTANCE.activationKey.get();
        int keyCode = getKeyCode(keyName);
        long window = mc.getWindow().getWindow();
        boolean keyDown = GLFW.glfwGetKey(window, keyCode) == GLFW.GLFW_PRESS;

        boolean active;
        if (SneakClickConfig.INSTANCE.holdMode.get()) {
            active = keyDown;
        } else {
            if (keyDown && !lastKeyState) toggled = !toggled;
            active = toggled;
        }
        lastKeyState = keyDown;

        if (!active) return;

        if (SneakClickConfig.INSTANCE.weaponOnly.get()) {
            ItemStack held = player.getMainHandItem();
            boolean isWeapon = held.getItem() instanceof SwordItem
                || held.getItem() instanceof AxeItem
                || held.getItem() instanceof TridentItem;
            if (!isWeapon) return;
        }

        int cps = SneakClickConfig.INSTANCE.cps.get();
        long interval = 1000L / cps;
        long now = System.currentTimeMillis();
        if (now - lastClick < interval) return;
        lastClick = now;

        KeyMapping.click(mc.options.keyAttack.getKey());
    }

    private int getKeyCode(String name) {
        if (name == null || name.isEmpty()) return GLFW.GLFW_KEY_R;
        char c = name.toUpperCase().charAt(0);
        if (c >= 'A' && c <= 'Z') {
            return GLFW.GLFW_KEY_A + (c - 'A');
        }
        return GLFW.GLFW_KEY_R;
    }
}
