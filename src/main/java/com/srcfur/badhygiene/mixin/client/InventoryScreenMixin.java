package com.srcfur.badhygiene.mixin.client;

import com.srcfur.badhygiene.BadHygiene;
import com.srcfur.badhygiene.api.HygieneAPI;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.awt.*;

@Mixin(InventoryScreen.class)
public class InventoryScreenMixin {
    private static final ResourceLocation BLADDER_BAR_ID = ResourceLocation.fromNamespaceAndPath(BadHygiene.MODID, "textures/gui/bladderbar.png");
    @Inject(at = @At("HEAD"), method = "renderBg")
    private void renderBackground(GuiGraphics guiGraphics, float f, int i, int j, CallbackInfo info) {
        // (coords) X, Y, (MAP) U, V, (SampleSize) K, I, (ReferenceSize) M, N
        ContainerScreenExposer exposer = (ContainerScreenExposer) this;
        ScreenExposer screen = (ScreenExposer) this;
        //Urine Bar
        guiGraphics.blit(BLADDER_BAR_ID, exposer.badhygeine$getLeftPos() - 58, exposer.badhygeine$getTopPos() + 1, 0.0F, 0.0F, 56, 20, 56, 20);
        guiGraphics.drawCenteredString(screen.badhygeine$getFont(), "Bladder", exposer.badhygeine$getLeftPos() - 29, exposer.badhygeine$getTopPos() + 3, -1);
        //Hygeine Bar
        guiGraphics.blit(BLADDER_BAR_ID, exposer.badhygeine$getLeftPos() - 58, exposer.badhygeine$getTopPos() + 21, 0.0F, 0.0F, 56, 20, 56, 20);
        guiGraphics.drawCenteredString(screen.badhygeine$getFont(), "Hygiene", exposer.badhygeine$getLeftPos() - 29, exposer.badhygeine$getTopPos() + 23, -1);
        //Do math on bar fullness
        Player local = Minecraft.getInstance().player;
        if(local != null) {
            float percentagefilled = HygieneAPI.getBladderFullness(local) / 100f;
            int barpixelwidth = Math.clamp(Math.round(percentagefilled * 50), 0, 50);
            guiGraphics.fill(exposer.badhygeine$getLeftPos() - 55,exposer.badhygeine$getTopPos() + 12, exposer.badhygeine$getLeftPos() - 55 + barpixelwidth,exposer.badhygeine$getTopPos() + 18, Color.YELLOW.getRGB());

            percentagefilled = (float) HygieneAPI.getCleanliness(local) / 100;
            barpixelwidth = Math.clamp(Math.round(percentagefilled * 50), 0, 50);
            guiGraphics.fill(exposer.badhygeine$getLeftPos() - 55,exposer.badhygeine$getTopPos() + 32, exposer.badhygeine$getLeftPos() - 55 + barpixelwidth,exposer.badhygeine$getTopPos() + 38, Color.BLUE.getRGB());
        }
    }
}