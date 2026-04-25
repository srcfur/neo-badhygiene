package com.srcfur.badhygiene.mixin.client;

import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.screens.Screen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(Screen.class)
public interface ScreenExposer {
    @Accessor("font")
    public Font badhygeine$getFont();
}
