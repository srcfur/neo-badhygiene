package com.srcfur.badhygiene.blocks;

import com.srcfur.badhygiene.BadHygiene;
import com.srcfur.badhygiene.blocks.entities.ToiletBlockEntity;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.Set;
import java.util.function.Supplier;

public class ModBlockEntities {
    //Entities
    public static DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY = DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, BadHygiene.MODID);
    //public static final Supplier<BlockEntityType<ToiletBlockEntity>> TOILET_ENTITY = BLOCK_ENTITY.register(
    //        "toilet_entity",
    //        ()->BlockEntityType.Builder.of(
    //                ToiletBlockEntity::new,
    //                ModBlocks.TOILET.get()
    //        ).build(null));
    public static void initialize() {

    }
}
