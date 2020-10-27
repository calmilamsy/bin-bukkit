package org.bukkit.mixin;

import net.minecraft.block.Door;
import net.minecraft.level.Level;
import org.bukkit.event.block.BlockRedstoneEvent;
import org.bukkit.util.mixin.CraftLevel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Door.class)
public abstract class MixinDoor {

    @Shadow public abstract void method_837(Level arg, int i, int j, int k, boolean flag);

    @Inject(method = "onAdjacentBlockUpdate(Lnet/minecraft/level/Level;IIII)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/level/Level;hasRedstonePower(III)Z"), cancellable = true)
    private void cb1(Level world, int i, int j, int k, int id, CallbackInfo ci) {
        org.bukkit.World bworld = ((CraftLevel) world).getWorld();
        org.bukkit.block.Block block = bworld.getBlockAt(i, j, k);
        org.bukkit.block.Block blockTop = bworld.getBlockAt(i, j + 1, k);

        int power = block.getBlockPower();
        int powerTop = blockTop.getBlockPower();
        if (powerTop > power) power = powerTop;
        int oldPower = (world.getTileMeta(i, j, k) & 4) > 0 ? 15 : 0;

        if (oldPower == 0 ^ power == 0) {
            BlockRedstoneEvent eventRedstone = new BlockRedstoneEvent(block, oldPower, power);
            ((CraftLevel) world).getServer().getPluginManager().callEvent(eventRedstone);

            this.method_837(world, i, j, k, eventRedstone.getNewCurrent() > 0);
        }

        ci.cancel();
    }
}
