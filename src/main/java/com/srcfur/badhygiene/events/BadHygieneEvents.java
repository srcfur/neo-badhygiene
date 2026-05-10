package com.srcfur.badhygiene.events;

import com.srcfur.badhygiene.BadHygiene;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.common.NeoForge;

public class BadHygieneEvents {
    public static void SendBedwettingEvent(Player plr){
        NeoForge.EVENT_BUS.post(new BedWettingEvent(plr));
    }
    public static void SendPlayerUsedToiletEvent(Player plr, BlockPos toilet){
        NeoForge.EVENT_BUS.post(new PlayerUsedToiletEvent(plr, toilet, plr.level()));
    }
}
