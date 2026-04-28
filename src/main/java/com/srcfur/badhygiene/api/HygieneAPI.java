package com.srcfur.badhygiene.api;

import com.srcfur.badhygiene.attachments.HygienePlayerAttachment;
import com.srcfur.badhygiene.attributes.HygieneAttributes;
import com.srcfur.badhygiene.BadHygiene;
import net.minecraft.resources.Identifier;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.event.tick.ServerTickEvent;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class HygieneAPI {

    public static final Identifier BLADDER_FULL_SLOW_DOWN_ID = Identifier.fromNamespaceAndPath(BadHygiene.MODID, "bladderfull");
    public static final Identifier DIRTY_VULNERABILITY_ID = Identifier.fromNamespaceAndPath(BadHygiene.MODID, "vulnerable");
    public static final Identifier WEAK_BLADDER_HEALTH_ID = Identifier.fromNamespaceAndPath(BadHygiene.MODID, "weakbladder");
    public static final int MAX_CLEAN_STAT = 100;
    public static final double DIRTY_HEALTH_MULTIPLIER = -0.6;
    private static List<Function<Player, Boolean>> event_player_wetting = new ArrayList<Function<Player, Boolean>>();

    public static int getBladderLevel(@NotNull Player p){ return p.getData(HygienePlayerAttachment.BLADDER_LEVEL); }
    public static void setBladderLevel(@NotNull Player p, int i){ p.setData(HygienePlayerAttachment.BLADDER_LEVEL, i); }
    public static int getContinence(@NotNull Player p){ return p.getData(HygienePlayerAttachment.CONTINENCE_LEVEL); }
    public static void setContinence(@NotNull Player p, int i){ p.setData(HygienePlayerAttachment.CONTINENCE_LEVEL, i); }
    public static int getCleanliness(@NotNull Player p) { return p.getData(HygienePlayerAttachment.CLEAN_LEVEL); }
    public static void setCleanliness(@NotNull Player p, int i) { p.setData(HygienePlayerAttachment.CLEAN_LEVEL, i); }
    public static void impactCleanliness(@NotNull Player p, int i) {
        setCleanliness(p, Math.clamp(getCleanliness(p) - i, 0, MAX_CLEAN_STAT));
        if(getCleanliness(p) < 30){
            double percentage = 1 - (getCleanliness(p) / 30.0);
            AttributeInstance vuln = p.getAttribute(Attributes.MAX_HEALTH);
            if(vuln != null){
                vuln.addOrReplacePermanentModifier(
                        new AttributeModifier(DIRTY_VULNERABILITY_ID, DIRTY_HEALTH_MULTIPLIER * percentage, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL)
                );
            }
        }
    }

    ///Returns as a percentage number (ie: 85.00%)
    public static float getBladderFullness(@NotNull Player p) { return ((float)getBladderLevel(p) / (float)getCalculatedContinence(p)) * 100; }
    ///Returns the amount the player should be slowed down by when getBladderCriticalThreshold is reached
    public static double getPlayerFullBladderSlowdown(@NotNull  Player p) { return -0.3; }
    ///Returns how much of the bladder needs to be filled up before being considered critical
    public static double getBladderCriticalThreshold(@NotNull Player p) { return 85; }
    public static int getBladderToFluidUnits(int bladder) { return bladder * 30; }
    public static int selfWettingHygieneImpact(@NotNull MinecraftServer server, @NotNull Player currentPlayer) { return 35; }
    /// Registers a function to be ran when a player wets themselves, if returned true then their accident was caught
    public static void registerWettingEvent(Function<Player, Boolean> func){
        event_player_wetting.add(func);
    }
    /// Will test through all events seeing if player's accident has been caught. If any return true then their accident has been caught.
    public static boolean testWettingCaught(@NotNull Player p){
        boolean result = false;
        for(Function<Player, Boolean> func : event_player_wetting){
            result = func.apply(p) || result;
        }
        return result;
    }


    public static int getCalculatedContinence(@NotNull Player currentPlayer){
        AttributeInstance incontinence = currentPlayer.getAttribute(HygieneAttributes.CONTINENCE);
        if(incontinence != null) {
            //Maybe some dumb math shit here, but shouldn't :P
            return (int) Math.round(getContinence(currentPlayer) * incontinence.getValue());
        }
        return getContinence(currentPlayer);
    }

    /// Performed on every player in the game, every tick!
    public static void ServerPlayerTick(@NotNull MinecraftServer server, @NotNull Player currentPlayer){
        if(getBladderLevel(currentPlayer) > getCalculatedContinence(currentPlayer)){
            setBladderLevel(currentPlayer, 0);
            if(!testWettingCaught(currentPlayer)){
                HygieneAPI.impactCleanliness(currentPlayer, selfWettingHygieneImpact(server, currentPlayer));
            }
        }
        AttributeInstance movementspeed = currentPlayer.getAttribute(Attributes.MOVEMENT_SPEED);
        //Advice to anyone wanting to inject into any of the following below. Look into inject the Add / Remove
        //functions of the AttributeInstance. Alternatively inject here and do our checks but like... earlier :3
        if(movementspeed != null){
            if(getBladderFullness(currentPlayer) > getBladderCriticalThreshold(currentPlayer)){
                movementspeed.addOrReplacePermanentModifier(
                        new AttributeModifier(BLADDER_FULL_SLOW_DOWN_ID, getPlayerFullBladderSlowdown(currentPlayer), AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL)
                );
            }else{
                if(movementspeed.hasModifier(BLADDER_FULL_SLOW_DOWN_ID)){
                    movementspeed.removeModifier(BLADDER_FULL_SLOW_DOWN_ID);
                }
            }
        }
        AttributeInstance vulnerability = currentPlayer.getAttribute(Attributes.MAX_HEALTH);
        if(vulnerability != null){
            if(getCleanliness(currentPlayer) > 30 && vulnerability.hasModifier(DIRTY_VULNERABILITY_ID)){
                vulnerability.removeModifier(DIRTY_VULNERABILITY_ID);
            }
        }

        if(currentPlayer.isInWaterOrRain()){
            HygieneAPI.impactCleanliness(currentPlayer, -1);
        }else{
            if(HygieneAPI.getCleanliness(currentPlayer) == MAX_CLEAN_STAT && !currentPlayer.hasEffect(MobEffects.REGENERATION)){
                currentPlayer.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 100));
                //This is mainly a balance thing, so you don't get infinite regeneration for not being peed (or shot :P)
                HygieneAPI.impactCleanliness(currentPlayer, 1);
            }
        }
    }
    /// Something something, does all the checks this mod does by default every tick :3
    public static void ServerTick(ServerTickEvent.Pre event){
        MinecraftServer server = event.getServer();
        String[] playerNames = server.getPlayerNames();
        for (int i = 0; i < server.getPlayerCount(); i++){
            String currentPlayerName = playerNames[i];
            ServerPlayer currentPlayer = server.getPlayerList().getPlayerByName(currentPlayerName);
            if(currentPlayer != null){
                ServerPlayerTick(server, currentPlayer);
            }
        }
    }
}