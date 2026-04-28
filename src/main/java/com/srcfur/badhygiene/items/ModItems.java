package com.srcfur.badhygiene.items;

import com.srcfur.badhygiene.BadHygiene;
import com.srcfur.badhygiene.fluids.ModFluids;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.createItems(BadHygiene.MODID);

    public static final DeferredHolder<Item, BucketItem> URINE_BUCKET = ITEMS.register( "urine_bucket",
            ()->new BucketItem(ModFluids.URINE_STILL.get(), new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1)
                    .setId(ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(BadHygiene.MODID, "urine_bucket")))));
}
