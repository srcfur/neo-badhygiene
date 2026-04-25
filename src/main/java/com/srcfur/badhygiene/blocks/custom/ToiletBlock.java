package com.srcfur.badhygiene.blocks.custom;

import com.mojang.serialization.MapCodec;
import com.srcfur.badhygiene.blocks.RegisteredPotty;
import com.srcfur.badhygiene.blocks.entities.AbstractToiletBlockEntity;
import com.srcfur.badhygiene.blocks.entities.ToiletBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ToiletBlock extends AbstractToiletBlock {
    public ToiletBlock(Properties properties) {
        super(properties);
    }
    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return simpleCodec(ToiletBlock::new);
    }
    public ToiletBlock(Properties properties, RegisteredPotty<AbstractToiletBlock, AbstractToiletBlockEntity> potty){
        super(properties, potty);
    }
}
