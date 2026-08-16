package com.srcfur.badhygiene.mixin.client;

import com.srcfur.badhygiene.BadHygiene;
import com.srcfur.badhygiene.api.HygieneAPI;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Player;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.awt.*;

@Mixin(InventoryScreen.class)
public abstract class InventoryScreenMixin {
    @Unique
    private static final Identifier BLADDER_BAR_ID = Identifier.fromNamespaceAndPath(BadHygiene.MODID, "textures/gui/bladderbar.png");
    @Inject(at = @At(value = "TAIL"), method = "extractBackground")
    private void BadHygiene$extractBackground(GuiGraphicsExtractor guiGraphics, int x, int y, float j, CallbackInfo info) {
        // (coords) X, Y, (MAP) U, V, (SampleSize) K, I, (ReferenceSize) M, N
        InventoryScreen current = (InventoryScreen)((Object)this);
        //Urine Bar
        guiGraphics.blit(RenderPipelines.GUI_TEXTURED, BLADDER_BAR_ID, current.getLeftPos() - 58, current.getTopPos() + 1, 0, 0, 56, 20, 56, 20);
        guiGraphics.centeredText(current.getFont(), "Bladder", current.getLeftPos() - 29, current.getTopPos() + 3, Color.WHITE.getRGB());
        //Hygeine Bar
        guiGraphics.blit(RenderPipelines.GUI_TEXTURED, BLADDER_BAR_ID, current.getLeftPos() - 58, current.getTopPos() + 23, 0, 0, 56, 20, 56, 20);
        guiGraphics.centeredText(current.getFont(), "Hygiene", current.getLeftPos() - 29, current.getTopPos() + 25, Color.WHITE.getRGB());
        //Do math on bar fullness
        Player local = Minecraft.getInstance().player;
        if(local != null) {
            float percentagefilled = HygieneAPI.getBladderFullness(local) / 100f;
            int barpixelwidth = Math.clamp(Math.round(percentagefilled * 50), 0, 50);
            guiGraphics.fill(current.getLeftPos() - 55,current.getTopPos() + 12, current.getLeftPos() - 55 + barpixelwidth,current.getTopPos() + 18, Color.YELLOW.getRGB());

            percentagefilled = (float) HygieneAPI.getCleanliness(local) / 100;
            barpixelwidth = Math.clamp(Math.round(percentagefilled * 50), 0, 50);
            guiGraphics.fill(current.getLeftPos() - 55,current.getTopPos() + 34, current.getLeftPos() - 55 + barpixelwidth,current.getTopPos() + 40, Color.BLUE.getRGB());
        }
    }
}