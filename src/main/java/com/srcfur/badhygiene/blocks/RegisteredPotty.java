package com.srcfur.badhygiene.blocks;

import com.srcfur.badhygiene.BadHygiene;
import com.srcfur.badhygiene.blocks.custom.AbstractToiletBlock;
import com.srcfur.badhygiene.blocks.custom.ToiletBlock;
import com.srcfur.badhygiene.blocks.entities.AbstractToiletBlockEntity;
import com.srcfur.badhygiene.blocks.entities.ToiletBlockEntity;
import com.srcfur.badhygiene.items.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.RegistryBuilder;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class RegisteredPotty<T extends AbstractToiletBlock, E extends AbstractToiletBlockEntity> {
    public static ArrayList<RegisteredPotty<AbstractToiletBlock, AbstractToiletBlockEntity>> allRegisteredPotties = new ArrayList<>();
    public DeferredBlock<T> BLOCK;
    public DeferredHolder<Item, BlockItem> ITEM;
    public DeferredHolder<BlockEntityType<?>, BlockEntityType<? extends AbstractToiletBlockEntity>> ENTITY;

    public static RegisteredPotty<AbstractToiletBlock, AbstractToiletBlockEntity> registerToilet(String name, Class<? extends AbstractToiletBlock> block, BlockBehaviour.Properties properties, Class<? extends AbstractToiletBlockEntity> entity){
        RegisteredPotty<AbstractToiletBlock, AbstractToiletBlockEntity> returnable = new RegisteredPotty<>();
        Constructor<? extends AbstractToiletBlock> blockcon;
        try{
            blockcon = block.getConstructor(BlockBehaviour.Properties.class, RegisteredPotty.class);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
        returnable.BLOCK = ModBlocks.BLOCKS.register(name, ()-> {
            try {
                return blockcon.newInstance(properties, returnable);
            } catch (InstantiationException e) {
                throw new RuntimeException(e);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            } catch (InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        });                                                                      //Register Block


        returnable.ITEM = ModItems.ITEMS.register(name, () -> new BlockItem(returnable.BLOCK.get(), new Item.Properties().stacksTo(64)));     //Register item!


        Constructor<? extends AbstractToiletBlockEntity> con;
        BlockEntityType.BlockEntitySupplier<? extends AbstractToiletBlockEntity> besupp;
        try {
           con = entity.getConstructor(BlockEntityType.class, BlockPos.class, BlockState.class);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
        besupp = (pos, state) -> {
            try {
                return con.newInstance(returnable.ENTITY.get(), pos, state);
            } catch (InstantiationException e) {
                throw new RuntimeException(e);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            } catch (InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        };
        returnable.ENTITY = ModBlockEntities.BLOCK_ENTITY.register(name,
                ()-> {
                    return BlockEntityType.Builder.of(
                            besupp,
                            returnable.BLOCK.get()
                    ).build(null);
                });
        allRegisteredPotties.add(returnable);
        return returnable;
    }
}
