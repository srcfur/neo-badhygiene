package com.srcfur.badhygiene.mixin;

import com.srcfur.badhygiene.api.HygieneAPI;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(FoodData.class)
public class FoodDataMixin {
    @Shadow
    private float exhaustionLevel;

    @Inject(at = @At("HEAD"), method = "tick")
    private void badhygeine$tick(Player serverPlayer, CallbackInfo ci){
        if(this.exhaustionLevel > 4.0f){
            HygieneAPI.setBladderLevel(serverPlayer, HygieneAPI.getBladderLevel(serverPlayer) + 1);
        }
    }
}
