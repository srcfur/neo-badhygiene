package com.srcfur.badhygiene.items;

import com.srcfur.badhygiene.BadHygiene;
import com.srcfur.badhygiene.blocks.ModBlocks;
import com.srcfur.badhygiene.data.HygieneDataTypes;
import com.srcfur.badhygiene.fluids.ModFluids;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.*;
import net.minecraft.world.level.ItemLike;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.ModifyDefaultComponentsEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

@Mod(BadHygiene.MODID)
public class ModItems {
    public ModItems(IEventBus modbus){
        ITEMS.register(modbus);
        modbus.register(this);
    }
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.createItems(BadHygiene.MODID);

    public static final DeferredHolder<Item, BucketItem> URINE_BUCKET = ITEMS.register( "urine_bucket",
            ()->new BucketItem(ModFluids.URINE_STILL.get(), new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1)));

    public static final DeferredHolder<Item, LaxativeCookie> LAXATIVE_COOKIE = ITEMS.register("laxative_cookie",
            ()->new LaxativeCookie(new Item.Properties().food(new FoodProperties(
                    1,1,true,1, Optional.empty(), List.of()
            ))));

    public static final DeferredHolder<Item, BlockItem> WASHING_MACHINE = ITEMS.register("washing_machine",
            ()->new BlockItem(ModBlocks.WASHING_MACHINE.get(), new Item.Properties()));

    @SubscribeEvent // on the mod event bus
    public void modifyComponents(ModifyDefaultComponentsEvent event) {
        // Sets the component on melon seeds
        List<ItemLike> GoodFoods = List.of(
                Items.APPLE,
                Items.MELON_SLICE,
                Items.CARROT,
                Items.BEETROOT,
                Items.MUSHROOM_STEW,
                Items.RABBIT_STEW,
                Items.BEETROOT_SOUP
        );
        List<ItemLike> BadFoods = List.of(
                Items.BREAD,
                Items.POTATO,
                Items.BEEF,
                Items.PORKCHOP,
                Items.MUTTON,
                Items.TROPICAL_FISH,
                Items.COD,
                Items.SALMON,
                Items.RABBIT,
                Items.CHICKEN,
                Items.COOKIE,
                Items.ROTTEN_FLESH,
                Items.SUSPICIOUS_STEW,
                Items.POISONOUS_POTATO,
                Items.GOLDEN_APPLE,
                Items.ENCHANTED_GOLDEN_APPLE,
                Items.GOLDEN_CARROT
        );
        List<ItemLike> HeavyFood = List.of(
                Items.BREAD,
                Items.COOKED_BEEF,
                Items.COOKED_CHICKEN,
                Items.COOKED_MUTTON,
                Items.COOKED_RABBIT,
                Items.GOLDEN_APPLE,
                Items.ENCHANTED_GOLDEN_APPLE
        );
        GoodFoods.forEach(food -> {
            event.modify(food, builder ->
                    builder.set(HygieneDataTypes.HYGIENE_DIET_EFFECT.get(), 5)
            );
        });
        BadFoods.forEach(food -> {
            event.modify(food, builder ->
                    builder.set(HygieneDataTypes.HYGIENE_DIET_EFFECT.get(), -5)
            );
        });
        BuiltInRegistries.ITEM.forEach(item -> {
            AtomicInteger nutrition = new AtomicInteger();
            nutrition.set(9);
            if(!HeavyFood.contains(item)){
                ItemStack stack = new ItemStack(item);
                //Skip if it's already set!
                if(stack.get(HygieneDataTypes.HYGIENE_BOWEL_FILL.get()) != null){
                    return;
                }
                nutrition.set(stack.getOrDefault(DataComponents.FOOD, new FoodProperties(0, 0, true, 0, Optional.empty(), List.of())).nutrition());
            }
            if(nutrition.get() == 0)
                return;
            event.modify(item, builder -> {
                builder.set(HygieneDataTypes.HYGIENE_BOWEL_FILL.get(), Math.max(nutrition.get() / 3, 1));
            });
        });
    }
}
