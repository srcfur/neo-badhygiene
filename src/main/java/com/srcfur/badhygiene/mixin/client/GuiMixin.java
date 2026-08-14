package com.srcfur.badhygiene.mixin.client;

import com.srcfur.badhygiene.BadHygiene;
import com.srcfur.badhygiene.api.HygieneAPI;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Gui.class)
public class GuiMixin {
    private static final ResourceLocation BOWEL_WARNING = ResourceLocation.fromNamespaceAndPath(BadHygiene.MODID, "bowel_warning");
    @Inject(method = "renderVehicleHealth", at = @At("HEAD"))
    private void onRender(GuiGraphics guiGraphics, CallbackInfo ci) {
        var minecraft = Minecraft.getInstance();
        if (!minecraft.options.hideGui && minecraft.getCameraEntity() instanceof Player && minecraft.gameMode != null && minecraft.gameMode.canHurtPlayer()) {

            int x = minecraft.getWindow().getGuiScaledWidth() - 30;
            int y = 12;
            if(minecraft.getCameraEntity().level().getLevelData().getGameTime() % 20 > 10 && HygieneAPI.getBowelLevel((Player)minecraft.getCameraEntity()) > 30)
                guiGraphics.blitSprite(BOWEL_WARNING, x, y, 18, 36);

        }
    }
}