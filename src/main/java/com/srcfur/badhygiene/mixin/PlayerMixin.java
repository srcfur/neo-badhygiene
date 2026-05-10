package com.srcfur.badhygiene.mixin;

import com.srcfur.badhygiene.api.HygieneAPI;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Player.class)
public abstract class PlayerMixin {
    @Shadow
    public abstract boolean isInvulnerableTo(ServerLevel level, DamageSource source);

    @Inject(at=@At("TAIL"), method="actuallyHurt")
    public void BadHygiene$actuallyHurtServer(ServerLevel level, DamageSource source, float dmg, CallbackInfo ci){
        if(!this.isInvulnerableTo(level, source)){
            //Another really roundabout way of getting the player >:(
            HygieneAPI.impactCleanliness((Player)((Object)this), Math.round(dmg));
        }
    }
}
