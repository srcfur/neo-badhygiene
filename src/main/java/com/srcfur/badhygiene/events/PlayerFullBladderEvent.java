package com.srcfur.badhygiene.events;

import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.ICancellableEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;

/// Cancel this event to disable the slowdown of having a full bladder!
public class PlayerFullBladderEvent extends PlayerEvent implements ICancellableEvent {
    public PlayerFullBladderEvent(Player player) {
        super(player);
    }
}
