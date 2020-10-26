package org.bukkit.mixin;

import net.minecraft.block.Button;
import net.minecraft.entity.player.PlayerBase;
import net.minecraft.level.Level;
import org.bukkit.block.Block;
import org.bukkit.event.block.BlockRedstoneEvent;
import org.bukkit.util.mixin.CraftLevel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;

@Mixin(Button.class)
public class MixinButton {

    @Inject(method = "method_1608(Lnet/minecraft/level/Level;IIILnet/minecraft/entity/player/PlayerBase;)Z", at = @At(value = "INVOKE", target = "Lnet/minecraft/level/Level;setTileMeta(IIII)V"), cancellable = true)
    private void cb1(Level world, int i, int j, int k, PlayerBase arg1, CallbackInfoReturnable<Boolean> cir) {
        int l = world.getTileMeta(i, j, k);
        int i1 = l & 7;
        int j1 = 8 - (l & 8);
        org.bukkit.block.Block block = ((CraftLevel) world).getWorld().getBlockAt(i, j, k);
        int old = (j1 != 8) ? 1 : 0;
        int current = (j1 == 8) ? 1 : 0;

        BlockRedstoneEvent eventRedstone = new BlockRedstoneEvent(block, old, current);
        ((CraftLevel) world).getServer().getPluginManager().callEvent(eventRedstone);

        if ((eventRedstone.getNewCurrent() > 0) != (j1 == 8)) {
            cir.setReturnValue(true);
        }

        world.setTileMeta(i, j, k, i1 + j1);
    }

    @Inject(method = "onScheduledTick(Lnet/minecraft/level/Level;IIILjava/util/Random;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/level/Level;setTileMeta(IIII)V"), cancellable = true)
    private void cb2(Level world, int i, int j, int k, Random rand, CallbackInfo ci) {
        int l = world.getTileMeta(i, j, k);
        Block block = ((CraftLevel) world).getWorld().getBlockAt(i, j, k);

        BlockRedstoneEvent eventRedstone = new BlockRedstoneEvent(block, 1, 0);
        ((CraftLevel) world).getServer().getPluginManager().callEvent(eventRedstone);

        if (eventRedstone.getNewCurrent() > 0) ci.cancel();

        world.setTileMeta(i, j, k, l & 7);
    }
}
