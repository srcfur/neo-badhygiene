package com.srcfur.badhygiene.mixin.bedwetting;

import com.mojang.datafixers.util.Either;
import com.srcfur.badhygiene.api.HygieneAPI;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Unit;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Player.class)
public abstract class bedwettingplayermixin {
    @Inject(at=@At(value = "HEAD"), method = "startSleepInBed", cancellable = true)
    public void BadHygiene$Bedwetting$startSleepInBed(BlockPos pos, CallbackInfoReturnable<Either<Player.BedSleepingProblem, Unit>> ci) {
        Player plr = (Player)((Object)this);
        if(HygieneAPI.getBladderLevel(plr) > HygieneAPI.getCalculatedContinence(plr) * HygieneAPI.getBladderCriticalThreshold(plr) * 0.01f){
            plr.sendOverlayMessage(Component.literal("I've gotta pee :<"));
            ci.setReturnValue(Either.left(Player.BedSleepingProblem.OTHER_PROBLEM));
        }
    }
}
