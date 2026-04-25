package com.srcfur.badhygiene.blocks;

import com.srcfur.badhygiene.BadHygiene;
import com.srcfur.badhygiene.blocks.custom.AbstractToiletBlock;
import com.srcfur.badhygiene.blocks.custom.ToiletBlock;
import com.srcfur.badhygiene.blocks.entities.AbstractToiletBlockEntity;
import com.srcfur.badhygiene.blocks.entities.ToiletBlockEntity;
import com.srcfur.badhygiene.blocks.entities.WoodenLatrineEntity;
import com.srcfur.badhygiene.fluids.ModFluids;
import com.srcfur.badhygiene.items.ModItems;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.neoforged.neoforge.capabilities.BlockCapability;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Function;
import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(BadHygiene.MODID);

    //Toilets :3
    //BLOCKS is a DeferredRegister.Blocks
    //public static final DeferredBlock<Block> TOILET = register("toilet", () -> new ToiletBlock(
    //        BlockBehaviour.Properties.of().noOcclusion()
    //));
    //public static final DeferredBlock<Block> WOODEN_LATRINE = register("wooden_latrine", () -> new ToiletBlock(
    //        BlockBehaviour.Properties.of().noOcclusion()
    //));
    //public static final Block TOILET = register("toilet", ToiletBlock::new, BlockBehaviour.Properties.of().sound(SoundType.GLASS).noOcclusion(), true);
    //public static final DeferredBlock<Potty> WOODEN_LATRINE = register("wooden_latrine", LatrineBlock::new, BlockBehaviour.Properties.of().sound(SoundType.WOOD).noOcclusion(), true);

    public static final RegisteredPotty<AbstractToiletBlock, AbstractToiletBlockEntity> TOILET = RegisteredPotty.registerToilet("toilet",
            ToiletBlock.class,
            BlockBehaviour.Properties.of().noOcclusion(),
            ToiletBlockEntity.class);
    public static final RegisteredPotty<AbstractToiletBlock, AbstractToiletBlockEntity> WOODEN_LATRINE = RegisteredPotty.registerToilet("wooden_latrine",
            ToiletBlock.class,
            BlockBehaviour.Properties.of().noOcclusion(),
            WoodenLatrineEntity.class);

    public static final DeferredBlock<Block> URINE = BLOCKS.register("urine", () -> new LiquidBlock(
            ModFluids.URINE_FLOWING.get(),
            BlockBehaviour.Properties.ofFullCopy(Blocks.WATER)
    ));

    public static DeferredBlock<Block> register(String name, Supplier<? extends Block> supplier){
        DeferredBlock<Block> block = BLOCKS.register(name, supplier);
        ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties().stacksTo(64)));
        return block;
    }
    public static void initialize() {
    }
}