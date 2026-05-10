package com.srcfur.badhygiene.events;

import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;

public class BedWettingEvent extends PlayerEvent {
    public BedWettingEvent(Player player) {
        super(player);
    }

}
