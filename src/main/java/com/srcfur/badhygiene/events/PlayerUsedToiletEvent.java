package com.srcfur.badhygiene.events;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;

public class PlayerUsedToiletEvent extends PlayerEvent {
    public PlayerUsedToiletEvent(Player player) {
        super(player);
    }
    public PlayerUsedToiletEvent(Player player, BlockPos pos, Level l){
        super(player);
        level = l;
        this.player = player;
        position = pos;
        entity = l.getBlockEntity(pos);
    }
    private Player player;
    private BlockPos position;
    private BlockEntity entity;
    private Level level;
}
