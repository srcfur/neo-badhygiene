package com.srcfur.badhygiene.events;

import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.Event;
import net.neoforged.bus.api.ICancellableEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;

/// Cancel this event to mark that the mess was handled!
public class PlayerMessingEvent extends PlayerEvent implements ICancellableEvent {
    public PlayerMessingEvent(Player player) {
        super(player);
    }
}
