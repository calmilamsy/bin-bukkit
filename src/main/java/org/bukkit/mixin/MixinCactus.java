package org.bukkit.mixin;

import net.minecraft.block.Cactus;
import net.minecraft.entity.EntityBase;
import net.minecraft.entity.Living;
import net.minecraft.level.Level;
import org.bukkit.event.entity.EntityDamageByBlockEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.util.mixin.CraftLevel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Cactus.class)
public class MixinCactus {

    @Inject(method = "onEntityCollision(Lnet/minecraft/level/Level;IIILnet/minecraft/entity/EntityBase;)V", at = @At(value = "HEAD"), cancellable = true)
    private void cb1(Level world, int i, int j, int k, EntityBase entity, CallbackInfo ci) {
        if (entity instanceof Living) {
            org.bukkit.block.Block damager = ((CraftLevel) world).getWorld().getBlockAt(i, j, k);
            org.bukkit.entity.Entity damagee = (entity == null) ? null : entity.getBukkitEntity();

            EntityDamageByBlockEvent event = new EntityDamageByBlockEvent(damager, damagee, EntityDamageEvent.DamageCause.CONTACT, 1);
            ((CraftLevel) world).getServer().getPluginManager().callEvent(event);

            if (!event.isCancelled()) {
                entity.damage(null, event.getDamage());
            }
            return;
        }

    }
}
