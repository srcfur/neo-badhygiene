package com.srcfur.badhygiene.bedwetting;

import com.srcfur.badhygiene.BadHygiene;
import com.srcfur.badhygiene.api.HygieneAPI;
import com.srcfur.badhygiene.mixin.FoodDataExposer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.event.entity.player.PlayerWakeUpEvent;

import java.util.Optional;

public class wakeupaccident {
    public static void onPlayerWakeup(PlayerWakeUpEvent event){
        if(event.getEntity().level().isClientSide()){
            return;
        }
        Player spe = event.getEntity();
        int addedExhaust = spe.getRandom().nextInt(10, 40);
        spe.getFoodData().addExhaustion(addedExhaust);
        //We do this so we don't have to widen exhaustion for the second!
        for(int i = 0; i < addedExhaust / 3; i++){
            spe.getFoodData().tick((ServerPlayer) spe);
            HygieneAPI.setBladderLevel(spe, HygieneAPI.getBladderLevel(spe) + 2);
        }
        HygieneAPI.setBladderLevel(spe, Math.round( Math.max(HygieneAPI.getBladderLevel(spe), HygieneAPI.getCalculatedContinence(spe) * 0.8f) ));
        BadHygiene.LOGGER.info(Integer.toString( HygieneAPI.getBladderLevel(spe)));
        if(HygieneAPI.getBladderLevel(spe) > HygieneAPI.getCalculatedContinence(spe) * HygieneAPI.getBladderCriticalThreshold(spe) * 0.01f){
            boolean stopAccident = false;
            if(!stopAccident){
                HygieneAPI.ServerPlayerPeeOnSelf(spe);
            }
        }
    }
}
